Java程序有大有小，从小的桌面程序到大型工业级服务端应用。为了支持如此广的应用范围，JVM提供多个垃圾回收器，每一种都可以满足不同的需求。选择哪种回收器，取决于不同的应用场景。为了达到性能适合应用场景，我们需要不断的调整回收器和回收器的性能参数。这篇文档提供了一些帮助信息。
一个垃圾回收器(GC)是一个内存管理工具。通过下面的操作，GC可以实现内存的自动管理：
* 在年轻代分配对象和将年老的对象提升到老年代
* 通过并发标记来发现老年代中存活的对象。当Java Heap区使用量超过默认阈值时，JVM会触发标记操作。详情参考Concurrent Mark Sweep (CMS) Collector and Garbage-First Garbage Collector.
* 使用parallel复制压缩存活对象来释放内存。详情参考The Parallel Collector and Garbage-First Garbage Collector

阿姆达尔定律表明很多工作量无法没完美的并行化；有些工作本身就是串行的，没有办法使用并行化来加速执行。Java也是如此。尤其是，java 1.4的jvm没有平行回收器，所以回收器在多处理器系统上的性能提升是相对的，不一定并发收集就特别好。
下图说明了gc时间消耗与cpu核数的关系。一个java应用在单核CPU的系统上运行时只花费1%的时间来做垃圾回收。
 The red line is an application spending only 1% of the time in garbage collection on a uniprocessor system. This translates to more than a 20% loss in throughput on systems with 32 processors. The magenta line shows that for an application at 10% of the time in garbage collection (not considered an outrageous amount of time in garbage collection in uniprocessor applications), more than 75% of throughput is lost when scaling up to 32 processors.