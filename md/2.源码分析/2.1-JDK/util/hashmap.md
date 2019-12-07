# HashMap源码解析

## 基础知识

* AbstractMap
* Map<K,V>
* Cloneable
* Serializable

## Java Doc 正文
这是一个Map接口的哈希表实现。这个实现提供了所有的map操作，允许null值和null键。(HashMap类和Hashtable基本一样，区别是HashMap不是线程安全的
而且允许null。)这个类不会保证map中元素的顺序； 而且元素的顺序不会一直保持不变，可能随着新元素的添加，顺序就变化了。

假如hash算法足够优秀，没有哈希冲突，这样哈希表中的每个元素都分散在不同的哈希桶中时，那么hashMap的一些基本方法都是O(1)复杂度的，快的一笔(get和put)。
迭代整个集合的耗时与HashMap的capacity和它的size成正比。因此，如果迭代的性能对你来说很重要，那么不要将initial capacity设置太高(或者负载因子设置太低)
。
> 后面会解答原因

HashMap的两个参数会影响其性能: initial capacity和 load factor(负载因子)
capacity就是哈希表的桶的数量，initial capacity就是哈希表在创建时的capacity(废话)。
load factor用例测量哈希表的水位，然后自动增长它的capacity。当entry的数量超过`load factor * current capacity`时，哈希表就会rehash(
所有的内部数据都会重建)所以哈希表会有2倍的长度。

一般来说，默认的load factor(.75)在时间和空间都是最优的。如果这个值很大，会减少空间浪费，但是会提高查找的消耗(大多数HashMap的方法都会有影响，包括get和put)。
HashMap要保存的数据量和load factor应该在map初始化时就要去考虑，这样才能减少rehash的次数，因为这是很耗费性能的。
如果initial capacity 大于entry最大值除以 load factor(`initial capacity> entry count /load factor`)，那么永远都不会发生rehash。

如果你要保存许多的映射关系到HashMap，初始化HashMap时要指定好足够的capacity，这样可以避免rehash操作。需要注意的一点，如果很多key都有同样的hashCode()
，就会降低哈希表的性能，因为同样的hashCode()会造成哈希冲突，这样哈希表会退化成链表。
**这个实现不是线程安全的**。如果多线程并发访问hash map，而且最少有一个线程修改hashMap的数据，它必须先做好同步化处理。

你可以使用 Collections.synchronizedMap方法来制作一个同步的Map。这个方法最好在初始化map时就做好:

  `Map m = Collections.synchronizedMap(new HashMap(...));`
如果这个map内部元素在iterator创建之后发生变化(key增减，value变了不算)，除了iterator自己的删除方法之外，其他的方法调用会抛出ConcurrentModificationException。
隐刺，面对并发修改的情况，iterator是快速失败的。
你不能依赖这个ConcurrentModificationException异常去开发功能，因为这是不可靠的，还是要从根本上去处理并发问题。
HashMap是 Java 集合框架的成员之一。

## 构造器

## 方法

## 源码解析

## 样例代码

## 扩展阅读
* LinkedHashMap
* PrinterStateReasons

## 总结

