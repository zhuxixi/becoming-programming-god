# 2 Ergonomics(工程学)

## 前言
Ergonomics是JVM和垃圾回收调优的过程。JVM提供平台决定的默认垃圾回收器、heap size、和运行时编译器。其中行为决定的调整是根据Java程序的行为来动态进行的。

这一节描述了这些默认回收器和基于行为的调优。先了解这些默认配置再去调整细节。
## Garbage Collector, Heap, and Runtime Compiler Default Selections
服务器级别的机器被定义为如下配置：
* 2或2个以上的物理CPU核数
* 2或2GB以上的物理内存

在服务器级别的物理机上，有下面几个默认配置：
* 吞吐量优先的垃圾回收器
* 初始化堆内存空间是物理机物理内存的1/64，最大1GB
* 最大堆内存空间是物理机内存的1/4，最大1GB
* Server runtime compiler
对于64位系统的初始堆和最大堆的细节，可以看The Parallel Collector的Default Heap Size小节。

除了32位Windows操作系统之外，其他的所有平台都有服务器级的JVM定义。
下表表示了不同平台的不同的runtime compiler。

|  Platform   |Operating System  | Default | Default if Server-Class |
|  :--------: | :------------:   | :------:| :--------------------:  |
| i586        | Linux            |Client   | Server                  |  
| i586        | Windows          |Client   | Client                  |  
|SPARC(64-bit)| Solaris          |Server   | Server                  |  
| AMD(64-bit) | Linux            |Server   | Server                  |  
| AMD(64-bit) | Windows          |Server   | Server                  |  

## Behavior-Based Tuning
对于Parallel Collector，Java SE提供了两个回收器参数来达到想要的效果：
* 最大停顿时间
* 应用吞吐量

具体可以看The Parallel Colletor章节。(其他的回收器没有这两个参数)。注意这些行为不一定会经常遇到。
应用需要一个足够大的堆，能够承载所有存活对象。另外，减小堆的大小可能会达不到调优的效果

## Maximum Pause Time Goal
暂停时间就是应用运行期垃圾回收器暂停应用去回收内存空间的时间，这时应用是不可用的。最大停顿时间
就是去限制这些停顿的最大时间。