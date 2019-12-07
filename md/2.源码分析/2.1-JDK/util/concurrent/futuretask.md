# FutureTask 全解

## 基础知识
* [Runnable]()
* [Future<V>]()
* [RunnableFuture<V>]()
## Java Doc 正文
这个类代表一个可取消的异步计算。它提供了Future接口的基本实现，提供了方法去启动和取消一个计算过程，也可以去判断计算是否执行完毕，最终
获取计算结果。计算结果只有在计算执行完毕后才能获取。get方法在计算完毕前会一直阻塞。如果计算完毕，这个计算就无法取消或者重新执行了(除非计算是通过
runAndRest()方法调用的)。一个FutureTask对象可以用来装饰Callable或Runnable对象。还有，因为FutureTask实现了Runnable，所以可以作为一个
任务提交给Executor去执行。
另外，这个class还提供了一些protected方法，如果需要继承FutureTask类去做一些个性化的子类，这些方法可以重写。
## 构造器
### public FutureTask(Callable<V> callable)
  使用Callable对象创建FutureTask
### public FutureTask(Runnable runnable,V result)
  使用Runnable和一个指定结果来初始化一个FutureTask，当任务成功执行完毕时，会返回指定的计算结果

## 方法
> Future和RunnableFuture的方法不再详细介绍，请看基础知识
### protected void done()
当调用FutureTask的Cancel时会调用这个方法，FutureTask允许其子类做一些处理。
这个方法默认什么都不做。
### protected void set(V v)
当FutureTask的run方法被调用，计算结果会通过这个set方法去设置，FutureTask允许其子类重写这个方法
做一些个性化的操作，例如打打日志什么的。这个方法会调用done()方法。
### protected void setException(Throwable t)
当调用FutureTask的run方法或runAndReset方法抛出异常时，FutureTask会调用setException去设置异常，允许其子类做一些处理。
这个方法也会调用done方法
### protected boolean runAndReset()
执行计算过程时不去设置它的结果，而且将这个FutureTask对象重置成初始状态。如果这个计算遇到了异常或者被取消，那就没办法重置了。
这个方法主要用于那些需要多次执行的场景，例如缓存延时双删。
这个方法在FutureTask中没有调用，子类可以根据情况选择重写run方法去调用runAndReset

## 源码解析
```
package java.util.concurrent;
import java.util.concurrent.locks.LockSupport;

public class FutureTask<V> implements RunnableFuture<V> {


    /**
     * 这个任务的运行状态，初始化时为NEW。只有set，setException, and cancel方法能够转换这个任务的运行状态。 
     * 在计算过程中，运行状态可能是COMPLETING或INTERRUPTING。
     * 以下是可能的状态转移过程:
     * NEW -> COMPLETING -> NORMAL
     * NEW -> COMPLETING -> EXCEPTIONAL
     * NEW -> CANCELLED
     * NEW -> INTERRUPTING -> INTERRUPTED
     */
    private volatile int state;
    private static final int NEW          = 0;
    private static final int COMPLETING   = 1;
    private static final int NORMAL       = 2;
    private static final int EXCEPTIONAL  = 3;
    private static final int CANCELLED    = 4;
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED  = 6;

    /** Callable引用，run方法执行完毕后会置为null */
    private Callable<V> callable;

    /** 任务的执行结果，可能是结果，也可能是一个异常，get方法会get到 */
    private Object outcome;

    /** 用来执行任务的线程引用 */
    private volatile Thread runner;

    /** 等待线程的堆栈，如果这个任务很多个线程都要调用get方法获取结果，那么计算完毕之前会在这里等待 */
    private volatile WaitNode waiters;

    /**
     * 返回结果或者抛出异常
     * @param s completed state value
     */
    @SuppressWarnings("unchecked")
    private V report(int s) throws ExecutionException {
        Object x = outcome;
        if (s == NORMAL)
            return (V)x;
        if (s >= CANCELLED)
            throw new CancellationException();
        throw new ExecutionException((Throwable)x);
    }


    /**
     * 构造方法
     */
    public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       
    }

    /**
     * 构造方法
     */
    public FutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
    }
    
    public boolean isCancelled() {
        return state >= CANCELLED;
    }

    public boolean isDone() {
        return state != NEW;
    }

    /**
     * 取消执行任务，参数控制是否打断执行
     * true:打断。false:不打断
     * 使用CAS修改运行状态。
     */
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!(state == NEW &&
              UNSAFE.compareAndSwapInt(this, stateOffset, NEW,
                  mayInterruptIfRunning ? INTERRUPTING : CANCELLED)))
            return false;
        try {    // in case call to interrupt throws exception
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null)
                        t.interrupt();
                } finally { // final state
                    UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED);
                }
            }
        } finally {
            finishCompletion();
        }
        return true;
    }

    /**
     * 获取结果，判断任务状态，如果还没执行完，就等待执行完毕。
     * 最后报告结果
     */
    public V get() throws InterruptedException, ExecutionException {
        int s = state;
        if (s <= COMPLETING)
            //awaitDone实现了get方法的阻塞，需要重点关注
            s = awaitDone(false, 0L);
        return report(s);
    }

    /**
     * 传递时间参数，get方法有了超时时间
     */
    public V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        if (unit == null)
            throw new NullPointerException();
        int s = state;
        if (s <= COMPLETING &&
            //这里调用了带时间参数的awaitDone方法
            (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING)
            throw new TimeoutException();
        return report(s);
    }

    /**
     * 不多说了，看上面的方法介绍
     */
    protected void done() { }

    /**
     * 设置结果，具体看上面的方法介绍
     * 最后注意它调用了finishCompletion方法
     */
    protected void set(V v) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = v;
            UNSAFE.putOrderedInt(this, stateOffset, NORMAL); // final state
            finishCompletion();
        }
    }

     /**
      * 设置异常，具体看上面的方法介绍
      * 最后注意它调用了finishCompletion方法
      */
    protected void setException(Throwable t) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = t;
            UNSAFE.putOrderedInt(this, stateOffset, EXCEPTIONAL); // final state
            finishCompletion();
        }
    }
    
    /**
     * run方法，判断线程状态，任务状态必须是NEW才可执行任务，否则没效果
     * 执行成功就set(Result) ，有异常就setException
     */
    public void run() {
        if (state != NEW ||
            !UNSAFE.compareAndSwapObject(this, runnerOffset,
                                         null, Thread.currentThread()))
            return;
        try {
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;
                try {
                    result = c.call();
                    ran = true;
                } catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }
                if (ran)
                    set(result);
            }
        } finally {
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            int s = state;
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
    }

    /**
     * 运行并重置，可以看这个任务执行成功后并没有调用set方法，所以任务的状态不会改变。
     * 只有任务出现异常才会改变任务状态。
     */
    protected boolean runAndReset() {
        if (state != NEW ||
            !UNSAFE.compareAndSwapObject(this, runnerOffset,
                                         null, Thread.currentThread()))
            return false;
        boolean ran = false;
        int s = state;
        try {
            Callable<V> c = callable;
            if (c != null && s == NEW) {
                try {
                    c.call(); // 看这里执行成功后并没有设置结果
                    ran = true;
                } catch (Throwable ex) {
                    setException(ex);//但是有异常时会设置异常
                }
            }
        } finally {
            // runner must be non-null until state is settled to
            // prevent concurrent calls to run()
            runner = null;
            // state must be re-read after nulling runner to prevent
            // leaked interrupts
            s = state;
            if (s >= INTERRUPTING)
                handlePossibleCancellationInterrupt(s);
        }
        return ran && s == NEW;
    }

    /**
     * 这方法我没太看懂
     * Ensures that any interrupt from a possible cancel(true) is only
     * delivered to a task while in run or runAndReset.
     */
    private void handlePossibleCancellationInterrupt(int s) {
        // It is possible for our interrupter to stall before getting a
        // chance to interrupt us.  Let's spin-wait patiently.
        if (s == INTERRUPTING)
            while (state == INTERRUPTING)
                Thread.yield(); // wait out pending interrupt

        // assert state == INTERRUPTED;

        // We want to clear any interrupt we may have received from
        // cancel(true).  However, it is permissible to use interrupts
        // as an independent mechanism for a task to communicate with
        // its caller, and there is no way to clear only the
        // cancellation interrupt.
        //
        // Thread.interrupted();
    }

    /**
     * 内部类，一个链表存储所有的等待线程
     */
    static final class WaitNode {
        volatile Thread thread;
        volatile WaitNode next;
        WaitNode() { thread = Thread.currentThread(); }
    }

    /**
     * 移除所有等待的线程，调用done()方法，然后将Callable置空
     */
    private void finishCompletion() {
        // assert state > COMPLETING;
        for (WaitNode q; (q = waiters) != null;) {
            if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
                for (;;) {
                    Thread t = q.thread;
                    if (t != null) {
                        q.thread = null;
                        LockSupport.unpark(t);
                    }
                    WaitNode next = q.next;
                    if (next == null)
                        break;
                    q.next = null; // unlink to help gc
                    q = next;
                }
                break;
            }
        }

        done();

        callable = null;        // to reduce footprint
    }

    /**
     * 阻塞调用线程，直到任务有结果
     */
    private int awaitDone(boolean timed, long nanos)
        throws InterruptedException {
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        WaitNode q = null;
        boolean queued = false;
        for (;;) {
            //如果线程被打断了，就删除waiter，抛异常
            if (Thread.interrupted()) {
                removeWaiter(q);
                throw new InterruptedException();
            }

            int s = state;
            //如果线程的状态>COMPLETING，说明现在执行完了
            if (s > COMPLETING) {
                if (q != null)
                    //线程引用置空，早点GC
                    q.thread = null;
                return s;
            }
            else if (s == COMPLETING) 
                Thread.yield();//yield一下，让出CPU
            else if (q == null)
                q = new WaitNode();//任务执行完毕之前这个线程询问时会创建节点
            else if (!queued)
                queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                                                     q.next = waiters, q);
            //如果有设置超时时间
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0L) {
                    removeWaiter(q);
                    return state;
                }
                //是用LockSupport来实现线程阻塞
                LockSupport.parkNanos(this, nanos);
            }
            else
                //没设置超时时间就永远阻塞
                LockSupport.park(this);
        }
    }

    /**
     * Tries to unlink a timed-out or interrupted wait node to avoid
     * accumulating garbage.  Internal nodes are simply unspliced
     * without CAS since it is harmless if they are traversed anyway
     * by releasers.  To avoid effects of unsplicing from already
     * removed nodes, the list is retraversed in case of an apparent
     * race.  This is slow when there are a lot of nodes, but we don't
     * expect lists to be long enough to outweigh higher-overhead
     * schemes.
     */
    private void removeWaiter(WaitNode node) {
        if (node != null) {
            node.thread = null;
            retry:
            for (;;) {          // restart on removeWaiter race
                for (WaitNode pred = null, q = waiters, s; q != null; q = s) {
                    s = q.next;
                    if (q.thread != null)
                        pred = q;
                    else if (pred != null) {
                        pred.next = s;
                        if (pred.thread == null) // check for race
                            continue retry;
                    }
                    else if (!UNSAFE.compareAndSwapObject(this, waitersOffset,
                                                          q, s))
                        continue retry;
                }
                break;
            }
        }
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long stateOffset;
    private static final long runnerOffset;
    private static final long waitersOffset;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> k = FutureTask.class;
            stateOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("state"));
            runnerOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("runner"));
            waitersOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("waiters"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}


```

## 样例代码
为了方便理解，我们写一段代码，使用FutureTask实现一个简单的需求：  
假如我有一个登录服务集群和一个控制台服务，其中登录服务一共有6个节点，
每个节点内部都有一个请求次数计数器，并开放了一个HTTP接口(queryInvokeCount)
供外界查询该
节点记录的请求次数；控制台服务每30秒调用一次登录服务所有节点的queryInvokeCount
接口来收集并汇总统计数据。  

针对这种场景，使用多线程和FutureTask，每次启动28个线程去分别去查询28个登录节点的
请求次数信息，所有线程执行完毕后汇总FutureTask的结果即可。
>其实这种场景真要用多线程也要使用线程池来做，不过我们主要为了演示FutureTask，所以
没关系。大家理解一下就好。

### 维护一个可靠的服务节点列表
实现这个场景的第一步，为了保持项目整洁，不引入第三方包，我们需要基于JDK和zookeeper watcher机制自己实现一个简单的服务注册发现的组件。
要求能够在服务集群发生变化时(集群重启，某个节点挂掉等等)立即刷新服务列表(server metadata list)








## 扩展阅读

## 总结

