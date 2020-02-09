# 一个基于LinkedHashMap的LRU缓存

## 基础知识
   * 继承HashMap类
   * 实现Map接口
## Java Doc 正文
这是Map接口的一个链式哈希表实现，它的迭代顺序是可以预测的。这个实现与HashMap是不同于它内部持有一个双端链表，连接了所有的entry。
这个链表定义了迭代顺序，这个顺序就是key的插入顺序。注意插入顺序不会因为key的重复插入而受到影响。举个例子：
```
public class LinkedHashMapTest {
    public static void main(String[] args){
        LinkedHashMap<Integer,Integer> a = new LinkedHashMap<>();
        a.put(1,1);
        a.put(2,2);
        a.put(3,3);
        a.put(4,4);
        a.put(1,1);
        for (Map.Entry<Integer,Integer> entry:a.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
```
这个段代码的输出是:
```
1
2
3
4
```
这个实现是HashMap的一个备选项，因为hashmap内部元素是无序的，而且比TreeMap时间复杂度要低。
LinkedHashMap可以生成一个Map的拷贝，而且元素的顺序与原Map相同：
```
    void foo(Map m) {
         Map copy = new LinkedHashMap(m);
         ...
     }

```
LinkedHashMap可以用来做数据拷贝，而且数据能够保留原有的顺序，这很有用。
LinkedHashMap还可以用一种特殊构造器来创造一个内部元素顺序受访问影响的，从最少访问到最大访问，也就是访问顺序。这种Map特别适合构造LRU缓存。
调用put，putIfAbsent，get，getOrDefault，computeIfPresent和merge方法都会导致对应元素的一次"访问"。
replace方法只有在元素被替换后才会导致一次元素访问。
putAll方法会对参数map中的每一个entry造成一次元素访问。

其他的方法都不会造成元素访问。尤其的是，collection级别的操作都不会影响内部map的元素顺序。
removeEldestEntry方法可以被重写为：当新的映射插入到map中，旧的映射自动删除。
这个类提供左右的Map操作，而且允许null元素。像HashMap一样，它的基本操作时间(add, contains and remove)复杂度都是常量时间的，当然了，假设
哈希算法均匀的将元素数据分布在buckets中。
由于要再内部构建一个链表，性能可能比HashMap稍微低一点，不过有一个特例：对LinkedHashMap进行collection级别的迭代，消耗的时间与map的size
成正比，与负载因子无关。迭代一个HashMap时间会更加昂贵，取决于负载因子。
>HashMap迭代与负载因子相关，这个需要深入学习

LinkedHashMap有两个会影响它内部性能的参数：initial capacity和load factor。
这两个参数的定义与HashMap相同。注意，然而，为LinkedHashMap选择过高initial capacity的副作用比HashMap要低，因为迭代与capacity无关。

注意这个实现是非同步的。如果多个线程会并发访问LinkedHashMap，而且最少一个线程会修改LinkedHashMap的结构，那么它必须被同步化。
如果你不加锁，就用封装类来wrap一下这个类。这最好在初始化时就做好，为了避免突发非同步访问：
`   Map m = Collections.synchronizedMap(new LinkedHashMap(...));`

结构变化是任何添加或删除元素的操作，或者在访问顺序的LinkedHashMap中，任何会改变元素顺序的操作。

在插入顺序的LinkedHashMap，仅仅改变key所对应的value不是一个结构变化。在访问顺序的LinkedHashMap中，仅有get是结构变化。
Collection级别方法返回的iterator类的方法都是快速失败的：
如果iterator创建之后map发生了结构变化，除了iterator自己的remove方法，iterator会抛出ConcurrentModificationException。
注意，依靠这个ConcurrentModificationException去写程序是错误的，因为程序在并发修改的情况下可能发生很多事情，无法做任何保证。
## 构造器
### public LinkedHashMap(int initialCapacity,float loadFactor)
初始化一个指定initial capacity 和 load factor的空的插入顺序的LinkedHashMap。
Throws:
IllegalArgumentException - 如果initialCapacity是负数或者 or 复杂因子是负数

### public LinkedHashMap(int initialCapacity)
初始化一个指定initial capacity 和 默认0.75的load factor的空的插入顺序的LinkedHashMap。
Throws:
IllegalArgumentException - 如果initialCapacity是负数

### public LinkedHashMap()
初始化一个默认16initial capacity 和 默认0.75的load factor的空的插入顺序的LinkedHashMap。
### public LinkedHashMap(Map<? extends K,? extends V> m)
Constructs an insertion-ordered LinkedHashMap instance with the same mappings as the specified map. The LinkedHashMap instance is created with a default load factor (0.75) and an initial capacity sufficient to hold the mappings in the specified map.

Throws:
NullPointerException - 如果m是null
### public LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder)
Constructs an empty LinkedHashMap instance with the specified initial capacity, load factor and ordering mode.
Parameters:
initialCapacity - the initial capacity
loadFactor - the load factor
accessOrder - the ordering mode - true for access-order, false for insertion-order
Throws:
IllegalArgumentException - 如果initialCapacity是负数或者 or 复杂因子是负数
## 方法

## 源码解析

## 样例代码

## 扩展阅读

## 总结

