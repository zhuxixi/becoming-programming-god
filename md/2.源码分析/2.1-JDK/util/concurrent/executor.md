>本文是Executor Java Doc的翻译
## 概述
这个接口是用来执行外部提交的Runnable任务的。**它解耦了任务提交和任务执行方式，以及线程创建和调度的细节**。**Executor一般用来替代显示地创建线程**  

举个例子，  

以前你是这样：
``Thread(new(RunnableTask())).start()``  

现在你最好这样
```
Executor executor = anExecutor;
executor.execute(new RunnableTask1());
executor.execute(new RunnableTask2());
...
```
不过，Executor接口并没有严格要求一定要**异步执行Runnable任务**。
可以参考下面的例子，你也可以直接在调用者的线程中直接执行Runnable方法:
```
 class DirectExecutor implements Executor {
   public void execute(Runnable r) {
     r.run();
   }
 }
```
 但是通常情况，最好还是在另一个线程去执行runnable：
 ```
 class ThreadPerTaskExecutor implements Executor {
   public void execute(Runnable r) {
     new Thread(r).start();
   }
 }
 ```
 
 许多Executor的实现都会加上一些功能限制，例如Runnable任务如何调度。
 如下所示，下面的Executor将提交的任务传递给一个二级Executor:
 ```
 class SerialExecutor implements Executor {
   final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
   final Executor executor;
   Runnable active;

   SerialExecutor(Executor executor) {
     this.executor = executor;
   }

   public synchronized void execute(final Runnable r) {
     tasks.offer(new Runnable() {
       public void run() {
         try {
           r.run();
         } finally {
           scheduleNext();
         }
       }
     });
     if (active == null) {
       scheduleNext();
     }
   }

   protected synchronized void scheduleNext() {
     if ((active = tasks.poll()) != null) {
       executor.execute(active);
     }
   }
 }
 ```
 Executor还有个子类ExecutorService，它提供更多的接口。ThreadPoolExecutor类提供了一个线程池实现。Executors类提供了很多方便的工厂方法。
 
Memory consistency effects: Actions in a thread prior to submitting a Runnable object to an Executor happen-before its execution begins, perhaps in another thread.

## 方法
void execute(Runnable command)  

在未来的某个时间点执行这个command对象。这个command任务可能会在一个新线程中执行，也可能会在一个池化的线程(某个线程池中的线程，可复用)中执行，甚至可能会在调用线程执行，具体是怎样的取决于Executor的实现逻辑。  
>下面就不翻译了，都能看懂

Parameters:  
command - the runnable task

Throws:  
RejectedExecutionException - if this task cannot be accepted for execution
NullPointerException - if command is null