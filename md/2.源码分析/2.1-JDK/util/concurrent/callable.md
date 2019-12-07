# Callable
## 概述
Callable接口代表一个任务，它返回一个结果或者会抛出一个异常。实现类会定义一个call方法，它没有参数。这个Callable接口和Runnable接口很相似，它们都是用来定义一个可能会在另一个线程执行的任务。**但是Runnable接口，没有返回任务执行结果，而且无法抛出一个checked exception。**

The Executors class contains utility methods to convert from other common forms to Callable classes.
## 方法
V call()throws Exception  
计算一个结果, 如果任务失败就抛出一个异常.