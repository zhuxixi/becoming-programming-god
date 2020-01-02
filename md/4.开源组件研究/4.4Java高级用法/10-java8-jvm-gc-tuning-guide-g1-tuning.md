# 10 Garbage-First Garbage Collector Tuning G1收集器调优
这一节讲如何调优G1收集器。

就像上一节说的，G1是一个多region的，也是一个分代的垃圾收集器。在JVM启动时设置region的大小。这个region大小可以从1MB变化到32MB，取决于heap size。目标时为了不要超过2048个region。eden，suvivor和tenured区都是这些小region的逻辑集合，而且这些小region在内存地址上可能也不是连续的。

G1收集器会尝试去满足某个停顿时间。在young gc时，G1收集器会调整young区大小来满足停顿时间的目标。

在混合GC时，G1 GC通过目标混合回收数量、region中存活对象百分比和空间最大浪费百分比来动态调整tenured区的数量。


