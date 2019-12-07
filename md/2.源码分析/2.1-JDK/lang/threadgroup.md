## 基础知识 
* [Thread](https://juejin.im/post/5da5b1836fb9a04e054d9680)  
* [Thread.UncaughtExceptionHandler](https://juejin.im/post/5da7d169f265da5b7637465a)

## 正文
ThreadGroup是Thread.UncaughtExceptionHandler的一个实现类，一个线程组代表一组线程。而且，一个线程组还可能包含其他的线程组。这些线程组组成了一棵树，除了最初的线程组。一个线程可以访问他自己的线程组信息，但是不能去方法它父线程组或其他线程组的信息。

## 构造器

### public ThreadGroup(String name)
构造一个新的线程组。这个新线程组的父亲就是当前线程的线程组。

Parameters:  
name - 线程组名称  
Throws:  
SecurityException - checkAccess方法可能会抛出异常
### public ThreadGroup(ThreadGroup parent,String name)
使用指定的父线程组对象，创建新的线程组，并指定名称。

Parameters:  
parent - 父线程组
name - 新线程组的名称  
Throws:  
NullPointerException - if the thread group argument is null.  
SecurityException - if the current thread cannot create a thread in the specified thread group.

## 方法
### public final String getName()
返回线程组名。  
### public final ThreadGroup getParent()
返回父线程组。只有最顶级的线程组的父线程组是null。  
Throws:  
SecurityException - if the current thread cannot modify this thread group.
### public final int getMaxPriority()
返回这个线程组的最大优先级。这个线程组中的线程优先级不能超过这个值。 
### public final boolean isDaemon()
检测这个线程组是不是一个守护线程组。当组内的守护线程已停止，守护线程组会自动销毁。  
Returns 
如果这个线程组是一个守护线程组就返回true。
### public boolean isDestroyed()
检查这个线程组是否已经销毁。  
Returns:  
如果已经销毁就返回true
### public final void setDaemon(boolean daemon)
修改守护线程标志。  
Parameters:  
daemon - true：标记这个线程组是守护线程组; false：就是个普通的线程组。
### public final void setMaxPriority(int pri)
设置线程组的最大优先级。线程组中的线程如果有更大的优先级，也不会起作用，主要取决于线程组的最大优先级。
参数必须在[Thread.MIN_PRIORITY,Thread.MAX_PRIORITY]这个范围内，否则也不会起作用。这个方法会递归的调用，设置当前线程组和它的子组的优先级。实际是否起作用，取决于pri参数和当前线程组的父线程组的优先级的大小，取两者之间小的那个值。  
Parameters:  
pri - 新的线程优先级
### public final boolean parentOf(ThreadGroup g)
检查这个线程组是否是参数线程组的祖先。
### public final void checkAccess()
检查当前线程是否有权限修改这个线程组。
### public int activeCount()
返回当前线程组和线程子组的活跃线程数(估算值)。这个方法主要用于调试和监控。
### public int enumerate(Thread[] list)
拷贝当前线程组和线程子组的所有活跃线程到参数数组中，并返回线程数。调用这个方法与调用enumerate(list, true)是等价的。  
Parameters:  
list - 用于存放线程的数组 
Returns:  
线程数
### public int enumerate(Thread[] list,boolean recurse)
拷贝当前线程组和线程子组的所有活跃线程到参数数组中，并返回线程数。如果recurese=true，这个方法会递归的枚举所有线程子组，把它们的活跃线程也算进结果中。如果数组长度太小，那么多余的线程会被忽略。
这个方法最好仅用于debug或者监控场景。

Parameters:  
list - 线程数组，用于存放所有活跃线程的引用  
recurse - true：递归地枚举所有线程子组的情况   
Returns:
线程数量
### public int activeGroupCount()
返回活跃线程组和它的子组，这是一个预估的值。递归地迭代这个线程组的所有线程子组。
### public int enumerate(ThreadGroup[] list)
将活跃的线程组拷贝至list数组，和enumerate(list, true)是等价的，具体看上面
### public int enumerate(ThreadGroup[] list,boolean recurse)
一个意思
### public final void interrupt()
打断这个线程组的所有线程。这个方法会对这个线程组中的所有线程和所有线程子组的线程调用interrupt方法。
### public final void destroy()
销毁这个线程组和所有线程子组。这个线程组必须为空，也就是说组内的所有线程都已经stop了。  
Throws:  
IllegalThreadStateException - 如果线程组不为空，或者线程组已经被销毁了
### public void list()
打印这个线程组的信息到标准输出。这个方法只用于debug模式。
### public void uncaughtException(Thread t,Throwable e)
这个方法就不多说，ThreadGroup是Thread.UncaughtExceptionHandler接口的实现类，所以这个方法其实是ThreadGroup的一个核心方法。具体请看Thread.UncaughtExceptionHandler的帖子。
### public String toString()
不多说了，就是打印一下信息。

## 源码解读

```
package java.lang;

import java.io.PrintStream;
import java.util.Arrays;
import sun.misc.VM;

/**
 * 
 * ThreadGroup 实现了 Thread.UncaughtExceptionHandler
 */
public class ThreadGroup implements Thread.UncaughtExceptionHandler {
    //父线程组的引用，一般就是当前线程的线程组
    private final ThreadGroup parent;
    //线程组名称
    String name;
    //最大优先级
    int maxPriority;
    //销毁标记
    boolean destroyed;
    //守护线程组标记
    boolean daemon;
    //没什么用
    boolean vmAllowSuspension;
    //未start的线程数
    int nUnstartedThreads = 0;
    int nthreads;
    //组内线程数组
    Thread threads[];
    
    int ngroups;
    //线程子组
    ThreadGroup groups[];

    /**
     * 创建一个空线程组，这个方法是private的，一般人无法调用，这个方法是JVM创建系统线程组时调用的
     */
    private ThreadGroup() {     // called from C code
        this.name = "system";
        this.maxPriority = Thread.MAX_PRIORITY;
        this.parent = null;
    }

    /**
     * 使用当前线程的线程组作为父线程组，创建一个指定名称的线程组
     */
    public ThreadGroup(String name) {
        this(Thread.currentThread().getThreadGroup(), name);
    }

    /**
     * 创建一个线程组，父线程组使用参数传递的线程组
     */
    public ThreadGroup(ThreadGroup parent, String name) {
        this(checkParentAccess(parent), parent, name);
    }
    
    /**
     * 创建一个线程组，父线程组使用参数传递的线程组
     */
    private ThreadGroup(Void unused, ThreadGroup parent, String name) {
        this.name = name;
        this.maxPriority = parent.maxPriority;
        this.daemon = parent.daemon;
        this.vmAllowSuspension = parent.vmAllowSuspension;
        this.parent = parent;
        //父线程组将这个线程组，add到父线程组的线程子组数组中
        parent.add(this);
    }

    /*
     * 校验是否有权限
     */
    private static Void checkParentAccess(ThreadGroup parent) {
        parent.checkAccess();
        return null;
    }

    /**
     * 返回这个线程组的名称
     */
    public final String getName() {
        return name;
    }

    /**
     * 返回这个线程组的父线程组
     */
    public final ThreadGroup getParent() {
        if (parent != null)
            parent.checkAccess();
        return parent;
    }

    /**
     * 返回最大优先级
     */
    public final int getMaxPriority() {
        return maxPriority;
    }

    /**
     * 返回这个线程组的守护线程标志
     */
    public final boolean isDaemon() {
        return daemon;
    }

    /**
     * 判断这个线程组是否已经被销毁
     */
    public synchronized boolean isDestroyed() {
        return destroyed;
    }

    /**
     * 设置线程组的守护线程标志
     */
    public final void setDaemon(boolean daemon) {
        checkAccess();
        this.daemon = daemon;
    }

    /**
     * 设置线程组的最大优先级
     */
    public final void setMaxPriority(int pri) {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            //参数要符合规范
            if (pri < Thread.MIN_PRIORITY || pri > Thread.MAX_PRIORITY) {
                return;
            }
            //优先级要和父线程组的优先级比较一下，取最小的值
            maxPriority = (parent != null) ? Math.min(pri, parent.maxPriority) : pri;
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        //注意看这里，其实他把所有的线程组的优先级都设置了一遍，这个方法是递归的
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            groupsSnapshot[i].setMaxPriority(pri);
        }
    }

    /**
     * 判断当前线程组是不是参数线程组的父亲
     */
    public final boolean parentOf(ThreadGroup g) {
        for (; g != null ; g = g.parent) {
            if (g == this) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断有没有权限
     */
    public final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkAccess(this);
        }
    }

    /**
     * 拿到子线程组的快照，然后递归的计算活跃线程数
     */
    public int activeCount() {
        int result;
        // Snapshot sub-group data so we don't hold this lock
        // while our children are computing.
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            if (destroyed) {
                return 0;
            }
            result = nthreads;
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            result += groupsSnapshot[i].activeCount();
        }
        return result;
    }

    /**
     * 拷贝活跃线程，一般用于监控,递归的拷贝子线程组
     */
    public int enumerate(Thread list[]) {
        checkAccess();
        return enumerate(list, 0, true);
    }

    /**
     * 拷贝活跃线程，一般用于监控,recurse=true就递归的拷贝子线程组
     */
    public int enumerate(Thread list[], boolean recurse) {
        checkAccess();
        return enumerate(list, 0, recurse);
    }
    /**
     * 拷贝活跃线程，一般用于监控,recurse=true就递归的拷贝子线程组
     */
    private int enumerate(Thread list[], int n, boolean recurse) {
        int ngroupsSnapshot = 0;
        ThreadGroup[] groupsSnapshot = null;
        synchronized (this) {
            if (destroyed) {
                return 0;
            }
            int nt = nthreads;
            if (nt > list.length - n) {
                nt = list.length - n;
            }
            for (int i = 0; i < nt; i++) {
                if (threads[i].isAlive()) {
                    list[n++] = threads[i];
                }
            }
            if (recurse) {
                ngroupsSnapshot = ngroups;
                if (groups != null) {
                    groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
                } else {
                    groupsSnapshot = null;
                }
            }
        }
        if (recurse) {
            for (int i = 0 ; i < ngroupsSnapshot ; i++) {
                n = groupsSnapshot[i].enumerate(list, n, true);
            }
        }
        return n;
    }

    /**
     * 返回活跃线程组数量，只要线程组没有被销毁，就算作一个活跃线程组
     * 递归的计算所有子线程组
     */
    public int activeGroupCount() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            if (destroyed) {
                return 0;
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        int n = ngroupsSnapshot;
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            n += groupsSnapshot[i].activeGroupCount();
        }
        return n;
    }

    /**
     * 拷贝当前线程组的所有线程子组和他们的后代线程组到参数数组中
     */
    public int enumerate(ThreadGroup list[]) {
        checkAccess();
        return enumerate(list, 0, true);
    }

    /**
     * 拷贝当前线程组的所有线程子组到参数数组中，recurse=true就把他们的
     * 后代也拷贝
     */
    public int enumerate(ThreadGroup list[], boolean recurse) {
        checkAccess();
        return enumerate(list, 0, recurse);
    }

    private int enumerate(ThreadGroup list[], int n, boolean recurse) {
        int ngroupsSnapshot = 0;
        ThreadGroup[] groupsSnapshot = null;
        synchronized (this) {
            if (destroyed) {
                return 0;
            }
            int ng = ngroups;
            if (ng > list.length - n) {
                ng = list.length - n;
            }
            if (ng > 0) {
                System.arraycopy(groups, 0, list, n, ng);
                n += ng;
            }
            if (recurse) {
                ngroupsSnapshot = ngroups;
                if (groups != null) {
                    groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
                } else {
                    groupsSnapshot = null;
                }
            }
        }
        if (recurse) {
            for (int i = 0 ; i < ngroupsSnapshot ; i++) {
                n = groupsSnapshot[i].enumerate(list, n, true);
            }
        }
        return n;
    }

    /**
     * 过时了，别用，因为他调用了Thread的stop(也过时了)
     */
    @Deprecated
    public final void stop() {
        if (stopOrSuspend(false))
            Thread.currentThread().stop();
    }

    /**
     * 打断当前线程组中的所有线程，也会打点子线程组的所有线程
     */
    public final void interrupt() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            for (int i = 0 ; i < nthreads ; i++) {
                threads[i].interrupt();
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            groupsSnapshot[i].interrupt();
        }
    }

    /**
     * 挂起线程，别用，也过时了
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public final void suspend() {
        if (stopOrSuspend(true))
            Thread.currentThread().suspend();
    }

    /**
     * 别用，不过是private，也没法用
     */
    @SuppressWarnings("deprecation")
    private boolean stopOrSuspend(boolean suspend) {
        boolean suicide = false;
        Thread us = Thread.currentThread();
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot = null;
        synchronized (this) {
            checkAccess();
            for (int i = 0 ; i < nthreads ; i++) {
                if (threads[i]==us)
                    suicide = true;
                else if (suspend)
                    threads[i].suspend();
                else
                    threads[i].stop();
            }

            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++)
            suicide = groupsSnapshot[i].stopOrSuspend(suspend) || suicide;

        return suicide;
    }

    /**
     * 过时了
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public final void resume() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            for (int i = 0 ; i < nthreads ; i++) {
                threads[i].resume();
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {s
                groupsSnapshot = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            groupsSnapshot[i].resume();
        }
    }

    /**
     * 销毁线程组和所有线程子组，这个组里的线程必须都不活跃才可以
     */
    public final void destroy() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            if (destroyed || (nthreads > 0)) {
                throw new IllegalThreadStateException();
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
            if (parent != null) {
                destroyed = true;
                ngroups = 0;
                groups = null;
                nthreads = 0;
                threads = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i += 1) {
            groupsSnapshot[i].destroy();
        }
        if (parent != null) {
            parent.remove(this);
        }
    }

    /**
     * 把参数线程组添加到当前线程组
     */
    private final void add(ThreadGroup g){
        synchronized (this) {
            if (destroyed) {
                throw new IllegalThreadStateException();
            }
            if (groups == null) {
                groups = new ThreadGroup[4];
            } else if (ngroups == groups.length) {
                groups = Arrays.copyOf(groups, ngroups * 2);
            }
            groups[ngroups] = g;

            // This is done last so it doesn't matter in case the
            // thread is killed
            ngroups++;
        }
    }

    /**
     * 把特定的线程组从当前线程组中移除
     */
    private void remove(ThreadGroup g) {
        synchronized (this) {
            if (destroyed) {
                return;
            }
            for (int i = 0 ; i < ngroups ; i++) {
                if (groups[i] == g) {
                    ngroups -= 1;
                    System.arraycopy(groups, i + 1, groups, i, ngroups - i);
                    // Zap dangling reference to the dead group so that
                    // the garbage collector will collect it.
                    groups[ngroups] = null;
                    break;
                }
            }
            if (nthreads == 0) {
                notifyAll();
            }
            if (daemon && (nthreads == 0) &&
                (nUnstartedThreads == 0) && (ngroups == 0))
            {
                destroy();
            }
        }
    }


    /**
     * 未启动线程数+1，用于记录那些未启动的线程数。
     */
    void addUnstarted() {
        synchronized(this) {
            if (destroyed) {
                throw new IllegalThreadStateException();
            }
            nUnstartedThreads++;
        }
    }

    /**
     * 添加线程到这个线程组
     */
    void add(Thread t) {
        synchronized (this) {
            if (destroyed) {
                throw new IllegalThreadStateException();
            }
            if (threads == null) {
                threads = new Thread[4];
            } else if (nthreads == threads.length) {
                threads = Arrays.copyOf(threads, nthreads * 2);
            }
            threads[nthreads] = t;

            // This is done last so it doesn't matter in case the
            // thread is killed
            nthreads++;

            // The thread is now a fully fledged member of the group, even
            // though it may, or may not, have been started yet. It will prevent
            // the group from being destroyed so the unstarted Threads count is
            // decremented.
            nUnstartedThreads--;
        }
    }

    /**
     * 这个方法是Thread调用的，当Thread.start方法执行失败时会调用此方法
     * 他也会调用remove方法，remove会导致线程组的线程数组出现移动
     */
    void threadStartFailed(Thread t) {
        synchronized(this) {
            remove(t);
            nUnstartedThreads++;
        }
    }

    /**
     * 这个方法其实主要是Thread调用的，当Thread结束执行退出时，会调用线程组的
     * 这个方法，这样会导致线程组的线程数组出现移动，这其实是一个优化点
     */
    void threadTerminated(Thread t) {
        synchronized (this) {
            remove(t);

            if (nthreads == 0) {
                notifyAll();
            }
            if (daemon && (nthreads == 0) &&
                (nUnstartedThreads == 0) && (ngroups == 0))
            {
                destroy();
            }
        }
    }

    /**
     * 将线程从这个组内删除
     */
    private void remove(Thread t) {
        synchronized (this) {
            if (destroyed) {
                return;
            }
            for (int i = 0 ; i < nthreads ; i++) {
                //注意这个地方，当做线程的增减时，数组会做移动操作，比较慢
                if (threads[i] == t) {
                    System.arraycopy(threads, i + 1, threads, i, --nthreads - i);
                    // Zap dangling reference to the dead thread so that
                    // the garbage collector will collect it.
                    threads[nthreads] = null;
                    break;
                }
            }
        }
    }

    /**
     * 打印线程组到标准输出，主要用于debug
     */
    public void list() {
        list(System.out, 0);
    }
    void list(PrintStream out, int indent) {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            for (int j = 0 ; j < indent ; j++) {
                out.print(" ");
            }
            out.println(this);
            indent += 4;
            for (int i = 0 ; i < nthreads ; i++) {
                for (int j = 0 ; j < indent ; j++) {
                    out.print(" ");
                }
                out.println(threads[i]);
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            groupsSnapshot[i].list(out, indent);
        }
    }

    /**
     * 不多说了，去看Thread.UncaughtException
     */
    public void uncaughtException(Thread t, Throwable e) {
        //
        if (parent != null) {
            parent.uncaughtException(t, e);
        } else {
            Thread.UncaughtExceptionHandler ueh =
                Thread.getDefaultUncaughtExceptionHandler();
            if (ueh != null) {
                ueh.uncaughtException(t, e);
            } else if (!(e instanceof ThreadDeath)) {
                System.err.print("Exception in thread \""
                                 + t.getName() + "\" ");
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * 过时了，不多说
     */
    @Deprecated
    public boolean allowThreadSuspension(boolean b) {
        this.vmAllowSuspension = b;
        if (!b) {
            VM.unsuspendSomeThreads();
        }
        return true;
    }

    /**
     * 输出一个文字版描述
     */
    public String toString() {
        return getClass().getName() + "[name=" + getName() + ",maxpri=" + maxPriority + "]";
    }
}

```
## 总结
ok，源码看完发现其实也不是很难理解，但是你可能会和我一样有很多疑问，例如：
Tomcat作为一个Web容器，它在线程组这部分一定也有设计，那么是如何设计的呢？因为你看ThreadGroup的源码，你一定会发现这里面其实有个地方很耗费资源，那就是ThreadGroup的add和remove方法会对线程数组进行移位和拷贝，这样就很耗费资源。像Tomcat这种处理Http请求的场景，线程不断地创建和死亡，每次创建线程和线程销毁都要去对数组进行拷贝、移位，势必会对性能造成影响，如果使用链表可能会更好一些。而ThreadGroup这个类并没有给我们选择的余地，不知道阿里这种大厂会不会基于这个点去优化。后面我会去找下Tomcat的源码，单开一篇帖子去验证一下Tomcat的线程组是如何设计的。
>在一般的生产环境中，Tomcat的线程数在200左右时，一台2C4G配置的服务器CPU压力基本就已经拉满了，不知道这个线程组如果优化一下能有多少提升，可以想象一下，系统压力足够高时，线程数维持在200，请求响应速度极快，ThreadGroup不停的add和remove，这个性能损耗还是挺高的。优化的办法无非就是两个：1.重新编译JDK；2.使用ClassLoader技术动态替换JDK的class。ClassLoader我玩儿的不6，第二种不知道可不可行，但是可以试试，有大神看到也可以讨论下。
