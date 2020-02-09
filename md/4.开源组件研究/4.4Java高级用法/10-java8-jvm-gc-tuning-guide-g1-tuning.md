# 10 Garbage-First Garbage Collector Tuning G1收集器调优
这一节讲如何调优G1收集器。

就像上一节说的，G1是一个多region的，也是一个分代的垃圾收集器。在JVM启动时设置region的大小。这个region大小可以从1MB变化到32MB，取决于heap size。目标时为了不要超过2048个region。eden，survivor和tenured区都是这些小region的逻辑集合，而且这些小region在内存地址上可能也不是连续的。

G1收集器会尝试去满足某个停顿时间。在young gc时，G1收集器会调整young区大小来满足停顿时间的目标。

在混合GC时，G1 GC通过目标混合回收数量、region中存活对象百分比和空间最大浪费百分比来动态调整tenured区的数量。

G1 GC将多个region中的存活对象copy到一个或多个新的region中，这样可以等于对内存空间做了压缩，就减少堆内存的碎片。这么做的目标尽可能去
回收堆内存空间，尽可能地去回收那些包含大量可回收
The goal is to reclaim as much heap space as possible, starting with those regions that contain the most reclaimable space, while attempting to not exceed the pause time goal (garbage first).
