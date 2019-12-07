
## 基础知识
* [Executor](https://juejin.im/post/5da133ba5188252a923a8cc3)
* [Callable](https://juejin.im/post/5da18914f265da5ba46f51c0)
* [Runnable](https://juejin.im/post/5da1803a518825663830fc56)
* [Future](https://juejin.im/post/5da4261bf265da5b873c6b93)

## 正文

### 相关内容
父接口：
* [Executor](https://juejin.im/post/5da133ba5188252a923a8cc3)  

子接口:
* ScheduledExecutorService  

实现类:
* AbstractExecutorService
* ForkJoinPool
* ScheduledThreadPoolExecutor
* ThreadPoolExecutor

### 概述

ExecutorService提供了一些方法，包括管理取消任务和可以使用Future来追踪那些异步执行的任务结果。ExecutorService是可以被关闭的，**当然这会导致它无法接受新的任务**。   
ExecutorService提供了两个不同的方法来关闭自己：  
 * shutdown()方法允许之前提交的任务在自己关闭之前继续被执行
 * shutdownNow()方法会禁止等待中的任务，而且会尝试终止执行中的任务。

接近终止时，executor没有任何激活的任务，没有任何等待执行的任务，也不会有新的任务提交。一个不再使用的ExecutorService应该被关闭，这样可以回收部分资源。

submit方法调用了父接口的Executor.execute(Runnable)方法，创建了一个Future对象，可以用来取消或等待计算结束。invokeAny和invokeAll方法主要用于批量执行任务的场景，批量执行一批任务，分别等待至少一个任务结束或者全部任务结束。对于这些方法，ExecutorCompletionService 可以用来写一些自定义的变种。

Executors方法提供了一些工厂方法相关的工厂方法。

用例：
下面的例子使用线程池处理收到的请求，这其实是一个简易的网络服务。它使用了预定义的工厂方法：
``Executors.newFixedThreadPool(int)``
 ```
 class NetworkService implements Runnable {
   private final ServerSocket serverSocket;
   private final ExecutorService pool;

   public NetworkService(int port, int poolSize)
       throws IOException {
     serverSocket = new ServerSocket(port);
     pool = Executors.newFixedThreadPool(poolSize);
   }

   public void run() { // run the service
     try {
       for (;;) {
         pool.execute(new Handler(serverSocket.accept()));
       }
     } catch (IOException ex) {
       pool.shutdown();
     }
   }
 }

 class Handler implements Runnable {
   private final Socket socket;
   Handler(Socket socket) { this.socket = socket; }
   public void run() {
     // read and service request on socket
   }
 }
```
如下的方法通过两步来关闭ExecutorService，第一步调用shutdown来拒收新的任务，然后调用shutdownNow，如果需要的话，取消所有处于等待中的任务：
 ```
 void shutdownAndAwaitTermination(ExecutorService pool) {
   pool.shutdown(); // Disable new tasks from being submitted
   try {
     // Wait a while for existing tasks to terminate
     if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
       pool.shutdownNow(); // Cancel currently executing tasks
       // Wait a while for tasks to respond to being cancelled
       if (!pool.awaitTermination(60, TimeUnit.SECONDS))
           System.err.println("Pool did not terminate");
     }
   } catch (InterruptedException ie) {
     // (Re-)Cancel if current thread also interrupted
     pool.shutdownNow();
     // Preserve interrupt status
     Thread.currentThread().interrupt();
   }
 }
```
内存一致性影响：在将Runnable或Callable任务提交给ExecutorService之前，线程中的操作发生在该任务执行的任何操作之前，而该操作又发生在通过Future.get（）检索结果之前。

## 方法

### boolean awaitTermination(long timeout, TimeUnit unit)

关闭自己之前会等待所有任务都执行完毕，此时方法会一直阻塞，直到阻塞时间大于超时时间，或者当前线程被中断

Parameters:
timeout - 等待最大时长  
unit - 等待时间单位  

Returns:  
* executor正常关闭就返回true
* 正常关闭之前到了超时时间  

Throws:  
InterruptedException - 执行时线程被中断

### <T> List<Future<T>>	invokeAll(Collection<? extends Callable<T>> tasks)

执行给定的批量任务，当所有任务都执行完毕时会返回一个包含所有任务结果Future的list集合。集合中的所有元素的Future.isDone()方法的调用结果都是true。但是要注意，一个已完成的任务可能是正常结束，也可能是抛了异常。如果给定的任务集合在任务执行中被更改，可能会导致这个方法的返回值可能会变成未定义的。**所以任务列表在提交到这个方法时不要做任何的修改！**

Type Parameters:  
T - the type of the values returned from the tasks

Parameters:  
tasks - 任务列表

Returns:  
一个Future集合用来表示任务的执行结果，集合顺序与给定的任务集合是一致的，而且集合中的future的状态都是已完成

Throws:  
InterruptedException - 如果等待时被中断，这回导致未执行的任务会被取消  
NullPointerException - 如果入参任务集合中的某个元素是null  
RejectedExecutionException - 如何任意的任务无法被执行

### <T> List<Future<T>>	invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)

此方法和上面的方法基本是一样的，唯一的区别是：**上面的方法执行完所有给定任务后才返回，此方法是执行所有任务，但是如果执行时间超过了超时时间，也会强制返回结果**

Type Parameters:  
T - the type of the values returned from the tasks  

Parameters:  
tasks - 任务列表  
timeout - 超时时间  
unit - 时间单位  
  
Returns:  
与上面的方法唯一的区别在于，**如果执行批量任务没有报超时，那么返回结果的所有future都是已完成状态，否则会存在一些未完成的future**

Throws:
InterruptedException - 如果等待时被中断，这回导致未执行的任务会被取消  
NullPointerException - 如果入参任务集合中的某个元素是null  
RejectedExecutionException - 如何任意的任务无法被执行

### <T> T	invokeAny(Collection<? extends Callable<T>> tasks)

执行给定的任务，返回成功完成的某一个任务的结果，任意一个在超时时间之前完成的都可以。在接近返回时(包括正常返回和异常返回)，那些没有完成的任务会被取消。如果给定的任务集合在任务执行中被更改，可能会导致这个方法的返回值可能会变成未定义的。**所以任务列表在提交到这个方法时不要做任何的修改！**

Type Parameters:
T - the type of the values returned from the tasks

Parameters:
tasks - 任务列表  

Returns:
返回任务中的其中之一 

Throws:
InterruptedException - 如果等待时被中断，这回导致未执行的任务会被取消  
NullPointerException - 如果入参任务集合中的某个元素是null  
RejectedExecutionException - 如何任意的任务无法被执行 
TimeoutException - 如果超时时间到了，但是没有任何一个任务完成
ExecutionException - 如果没有任务执行成功  
IllegalArgumentException - 如果集合为空  

### <T> T	invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)

执行给定的任务，返回成功完成的某一个任务的结果，任意一个在超时时间之前完成的都可以。在接近返回时(包括正常返回和异常返回)，那些没有完成的任务会被取消。如果给定的任务集合在任务执行中被更改，可能会导致这个方法的返回值可能会变成未定义的。**所以任务列表在提交到这个方法时不要做任何的修改！**

Type Parameters:  
T - the type of the values returned from the tasks  
 
Parameters:  
tasks - 任务列表  
timeout - 超时时间  
unit - 时间单位   

Returns:  
返回任务中的其中之一  

Throws:
InterruptedException - 如果等待时被中断，这回导致未执行的任务会被取消  
NullPointerException - 如果入参任务集合中的某个元素是null  
RejectedExecutionException - 如何任意的任务无法被执行 
TimeoutException - 如果超时时间到了，但是没有任何一个任务完成
ExecutionException - 如果没有任务执行成功

### boolean	isShutdown()

如果此executor已经被关闭，那么就返回true

Returns:
如果此executor已经被关闭，那么就返回true

### boolean	isTerminated()

如果关闭之前所有任务都已完成就返回true。这个方法在调用shutdown或shutdownNow之前永远都不会返回true。

Returns:  
如果关闭之前所有任务都已完成就返回true。

### void	shutdown()

调用此方法后，之前提交的任务会被执行，但是任何新任务都不会被执行。当Executor已经是关闭状态后再调用此方法不会任何附加的效果。这个方法不会等待历史提交的任务去完成执行。如果要那样请用awaitTermination。(这个描述不是很明朗，需要写测试类验证)

Throws:  
SecurityException - if a security manager exists and shutting down this ExecutorService may manipulate threads that the caller is not permitted to modify because it does not hold RuntimePermission("modifyThread"), or the security manager's checkAccess method denies access.

### List<Runnable>	shutdownNow()

尝试关闭所有激活的任务，立即停止那些正在等待中的任务，然后返回一个包含所有等待执行的任务列表。这个方法不会等待执行中的任务直到执行完毕，如果要那样请用awaitTermination。

Returns:
还没来得及执行的任务列表  

Throws:  
SecurityException - if a security manager exists and shutting down this ExecutorService may manipulate threads that the caller is not permitted to modify because it does not hold RuntimePermission("modifyThread"), or the security manager's checkAccess method denies access.

### <T> Future<T>	submit(Callable<T> task)

提交一个带返回值的任务(Callable)而且返回一个future对象来代表任务结果。如果任务执行成功，future.get方法会返回结果。
  下面的代码可以让你立即阻塞在等待任务完成的阶段：   
``result = exec.submit(aCallable).get(); ``

> 下面的翻译还有待学习，学习完后会补充

Note: The Executors class includes a set of methods that can convert some other common closure-like objects, for example, PrivilegedAction to Callable form so they can be submitted.


Type Parameters:  
T - the type of the task's result  

Parameters:
task - 等待执行的任务

Returns:
任务结果future
  
Throws:
RejectedExecutionException - 如果任务无法被执行
NullPointerException - 如果参数为null

### Future<?>	submit(Runnable task)

提交一个Runnable任务，这个任务没有返回值，但是ExecutorService给它封装了一个Future，这个Future的get方法在任务执行成功后会返回一个null，也就是说，**当你get到null的时候代表任务执行成功了**。

Parameters:  
task - 任务

Returns:  
任务结果future对象 

Throws:  
RejectedExecutionException - 任务无法被接受
NullPointerException - 参数为空

### <T> Future<T>	submit(Runnable task, T result)

提交一个Runnable任务，这个任务没有返回值，但是ExecutorService给它封装了一个Future，这个Future的get方法在任务执行成功后会返回你传进来的result，也就是说，**当你get到result的时候代表任务执行成功了**。

Type Parameters:  
T - the type of the result

Parameters:  
task - 任务  
result - 任务结果，在任务完成时future可以get到  

Returns:  
任务结果future对象 

Throws:
RejectedExecutionException - 任务无法被接受  
NullPointerException - 参数为空

