## 基础知识
* [Runnable](https://juejin.im/post/5da1803a518825663830fc56)
* [Future](https://juejin.im/post/5da4261bf265da5b873c6b93)

## 正文

### 相关内容
Type Parameters:  
V - The result type returned by this Future's get method  

父接口:
* Future<V>
* Runnable

子接口:
* RunnableScheduledFuture<V>

实现类:
* FutureTask，最有代表性
* SwingWorker

### 概述
RunnableFuture接口继承了Runnable和Future两个接口，写到这里提醒一下大家，**Java中类是不可以多重继承的，但是接口可以多重继承的**。  Future一般是用来做为异步计算结果的，所以呢，RunnableFuture的实现类既是计算结果，同时也是一个执行任务，可以传递给Excutor。
## 方法

### void run()
继承自Runnable

### cancel, get, get, isCancelled, isDone
继承自Future