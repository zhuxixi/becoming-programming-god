> 本文是 JDK8 ThreadPoolExcutor JAVA DOC 的中文翻译
### 核心线程数和最大线程数

ThreadPoolExecutor会根据你设置的核心线程数和最大线程数来调整线程池的大小。
* 当你调用threadPoolExecutor.execute(Runnable)时，如果池中线程数小于核心线程数，threadPoolExecutor会创建一个新的线程，即使其他的核心线程此时是**空闲的**。<br>

>举个栗子：
> 1. 你设置的核心线程数=5，当前核心线程数=0。
> 2. 此时你调用threadPoolExecutor.execute(Runnable)向线程池提交了一个任务A。
> 3. 线程池接收到任务A，此时当前核心线程数0<5，所以线程池创建了Thread 1。
> 4. Thread 1很快将任务A执行完毕，此时Thread 1处于空闲状态(idle)
> 5. 然后你又调用threadPoolExecutor.execute(Runnable)向线程池提交了一个任务B。
> 6. 线程池接收到任务B，此时当前核心线程数1<5，所以线程池创建了Thread 2。

* 如果池中线程数大于核心线程数但小于最大线程数，只有在任务队列已满时，threadPoolExecutor才会创建新的线程。
* 当你设置**核心线程数等于最大线程数**，你就创建了一个定长线程池。
* 当你将线程池的最大线程数设置为Integer.MAX_VALUE，你等于让线程池处理无限并发量的任务。
* 一般情况下，核心线程数和最大线程数都通过构造器来设置，但是他们其实也可以通过Setter方法来动态地设置。

### 请求式的创建线程池
默认情况下，核心线程只有当任务提交到threadPoolExecutor时才会创建，但其实也可以通过调用以下方法来动态创建核心线程：
1.  prestartCoreThread()  创建一个核心线程
2.  prestartAllCoreThreads() 创建所有的核心线程

> 举个栗子
 如果你初始化线程池时使用非空的任务队列(队列中已经有了一些任务)，可以使用上述方法提前初始化线程池中的线程

### 线程新建策略
线程都是使用**ThreadFactory**创建的。如果没有特别指定自定义的ThreadFactory，默认使用Executors.defaultThreadFactory()，这样所有的线程都属于同一个ThreadGroup，而且所有线程都有相同的优先级，而且都不是守护线程。如果提供一个自定义的ThreadFactory，你可以修改线程名，thread group，优先级和守护线程标志。如果ThreadFacotry创建线程失败并且返回了null，threadPoolExecutor不会报错，但是不会执行任务。
> 下面一段英文我没有很好地理解，有大神看到可以解答一下
Threads should possess the "modifyThread" RuntimePermission. If worker threads or other threads using the pool do not possess this permission, service may be degraded: configuration changes may not take effect in a timely manner, and a shutdown pool may remain in a state in which termination is possible but not completed.

暂时先这样翻译：

线程必须要处理 "modifyThread" RuntimePermission。如果池中的工作线程或其他线程无法处理这种权限，服务可能会被降级：配置变更可能不会被及时处理，而且一个线程池可能一直保持着关闭中的状态。

### 线程存活时间
如果池中线程数大于核心线程数，那么多余的线程在他们空闲时间大于keepAliveTime时会被销毁。这其实就是在系统压力很低的情况下节省资源啦。如果以后线程池后面又接了新任务，新的线程也会再次被创建。这个参数也可以通过setter方法来动态设置。如果你将keepAliveTime设置成Long.MAX_VALUE就等于禁用了次参数。默认情况下，线程的过期时间只有在线程数大于核心线程数时才会起作用。但是allowCoreThreadTimeOut(boolean)可以将keepAliveTime也设置为对核心线程数有效。

### 任务排队策略
任意一个阻塞队列都可以用来保存提交到线程池的任务。队列的使用效果和线程大小是相关联的，如下所示：
* 如果池中线程数<核心线程数，threadPoolExecutor会新建一个线程。
* 如果池中线程数>=核心线程数，threadPoolExecutor会将任务丢进任务队列。
* 如果队列已满，threadPoolExecutor会创建一个新的线程，除非池线程数=最大线程数，否则任务会被拒绝
有三种标准任务排队策略，如下所示：

1. 直接转手。这种策略就是直接将任务给线程池，不再保存在队列中。例如SynchronousQueue，这种排队策略适合同时处理几个有互相依赖的任务。直接转手策略通常需要无界的最大线程数来保证不会丢弃任务，因为存在那种任务提交速度大于处理速度的情况。
2. 无界队列。使用无界队列，如果核心线程一直都在工作，那么新任务会一直保存在队列中，而且池中线程数永远都不会大于核心线程数(不明白的看上面)，最大线程数这个参数也不会起任何作用。这种策略适合处理那种每个任务彼此之间都没有依赖关系的场景。例如，一个基于Tomcat的Java Web 服务接收到的请求量陡增，使用队列来做一下缓冲。不过使用无界队列，任务太多可能会撑爆内存。
3. 有界队列。使用有限的最大线程数配合有界队列不会产生系统资源耗尽的情况，但是更加难以控制和调试。队列长度和最大线程如何设置是需要权衡的，使用队列过长，线程数太小，会降低CPU使用率、系统资源使用率和上下文切换，但是会人为的降低吞吐量。如果任务经常阻塞，线程数可能还是会超出合法值。队列长度小，一般就需要更大的线程池，CPU利用率会比较高，但是线程调度开销也很高，同样，也会降低吞吐量。


### 任务丢弃策略
如果线程池已经关闭、或者线程池使用了有限的最大线程数或有界队列，而且已经满了，那么新的任务会被拒绝。在这两种情况下，execute方法会调用RejectedExecutionHandler.rejectedExecution(Runnable, ThreadPoolExecutor)来处理被拒绝的请求，以下四种处理器已经被预先定义好：
1. ThreadPoolExecutor.AbortPolicy抛出一个RejectedExecutionException异常
2. ThreadPoolExecutor.CallerRunsPolicy 让调用者自己执行任务
3. ThreadPoolExecutor.DiscardPolicy 什么也不做，相当于直接就丢弃了任务
4.  ThreadPoolExecutor.DiscardOldestPolicy 将任务队列中的第一个任务丢弃，将当前任务放入队列，如果再次放入队列也失败了，那就再循环一次。

> 丢弃策略RejectedExecutionHandler是可以自定义的，针对特定场景，我们可以自己设计在特定场景下的丢弃策略

### Hook方法
> 所谓Hook(钩子)方法，就是一个类预留了几个方法给子类重写，方便子类做一些自定义的操作。可以理解为父类就是一个窗帘杆，子类就是窗帘，窗帘杆上的钩子我已经给你留好了，你只要把窗帘上的环挂上去就能用

ThreadPoolExecutor提供了可被子类重写的beforeExecute(Thread, Runnable) 和 afterExecute(Runnable, Throwable) 方法。这两个方法用来做一些预处理操作，例如初始化TheadLocal变量，做一些统计，打打日志。
terminated()方法也可以被重写，当ThreadPoolExecutor被终止时可以做一些处理。如果hook或者callback方法抛出了一些异常，内部的工作线程可能会失败并且突然终止。


