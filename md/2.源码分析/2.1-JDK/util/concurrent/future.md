>本文翻译自Java Doc

## 正文
### 相关接口

Type Parameters:  
V - 代表这个接口提供的get方法的返回值类型

所有的子接口:  
* Response<T>
* RunnableFuture<V>
* RunnableScheduledFuture<V>
* ScheduledFuture<V>

所有的实现类:  
* CompletableFuture
* CountedCompleter
* ForkJoinTask
* FutureTask
* RecursiveAction
* RecursiveTask
* SwingWorker

### 概述
Future代表一个异步的计算结果。它提供了一些方法来检查Future的计算是否完毕，你可以等待计算完毕直到你得到计算结果。当Future的计算结束时，你可以通过get()方法来获取计算结果。其他的方法可以去判断计算是否完成，或者是否已被取消。如果一个计算已经完成，那么这个计算就无法取消了。必要时，实现类可以将get方法设计成阻塞的，只有计算成功才会返回结果。调用cancel方法可以取消计算。如果计算尚未完成，你也可以将get方法的返回值设置为null。  

一个简单的用例：
```
 interface ArchiveSearcher { String search(String target); }
 
 class App {
   ExecutorService executor = ...
   ArchiveSearcher searcher = ...
   void showSearch(final String target) throws InterruptedException {
     Future<String> future = executor.submit(new Callable<String>() {
         public String call() {
             return searcher.search(target);
         }});
     displayOtherThings(); // do other things while searching
     try {
       displayText(future.get()); // use future
     } catch (ExecutionException ex) { 
       cleanup(); 
       return; 
     }
   }
 }
 ```
``FutureTask``类实现了Future和Runnable接口，所以可以做为Executor.execute方法的参数。

所以上面的样例代码可以简化为如下代码：
```
FutureTask<String> future =
   new FutureTask<String>(new Callable<String>() {
     public String call() {
       return searcher.search(target);
   }});
executor.execute(future);
```
 
 >下面内存一致性影响这个不太理解，有大神看到可以帮忙翻译一下
 
Memory consistency effects: Actions taken by the asynchronous computation happen-before actions following the corresponding Future.get() in another thread.

## 方法

### boolean cancel(boolean mayInterruptIfRunning)
尝试取消任务。如果这个任务已经结束或者已经被取消，那么此方法会调用失败，当然了，也可能因为一些其他原因导致取消失败。如果取消成功，而且这个任务还未开始执行之前就调用了cancel方法，那么这么任务应该再也不会执行。如果这个任务已经开始执行了，那么是否应该中断线程来取消任务的执行，是由mayInterruptIfRunning这个参数决定执行的。只要这个方法被执行过一次，后续所有对isDone()方法的调用永远都返回true。后续所有对isCancelled() 方法的调用永远也都返回true。

Parameters:  
mayInterruptIfRunning - 如果执行任务的线程应该被终止，那么就传true ; 否则在进行中的任务会允许其继续执行

Returns:  
如果任务无法被取消就会返回false，这种情况一般都是因为任务已经执行完毕了。其他情况会返回true。

### boolean isCancelled()
如果任务正常执行之前被取消，那么返回true.

Returns:  
任务执行前取消成功会返回true

### boolean isDone()
如果任务已经完成，那么返回true。但是任务已完成有很多种因素，例如正常终止，任务失败并抛出异常，或者被取消，这些情况下该方法都会返回true。

Returns:  
任务已完成时会返回true

### V get() throws InterruptedException,ExecutionException
如果有必要的话，在计算完毕之前此方法会一直阻塞，计算完毕后会返回结果。

Returns:  
返回计算结果

Throws:  
CancellationException - 如果任务已经被取消  
ExecutionException - 如果计算过程中抛出了异常  
InterruptedException - 如果等待的线程被中断

### V get(long timeout,TimeUnit unit)throws InterruptedException,ExecutionException,TimeoutException
如果必要的话，在计算完毕之前此方法会阻塞一段时间，直到阻塞时间大于参数指定时间，如果一切正常会返回结果

Parameters:  
timeout - 最大的等待时间  
unit - 时间单位

Returns:  
计算结果

Throws:  
CancellationException - 如果任务已经被取消  
ExecutionException - 如果计算过程中抛出了异常  
InterruptedException - 如果等待的线程被中断  
TimeoutException - 如果等待时间超时
