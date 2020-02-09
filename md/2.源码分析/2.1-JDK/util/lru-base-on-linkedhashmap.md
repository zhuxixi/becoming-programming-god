# һ������LinkedHashMap��LRU����

## ����֪ʶ
   * �̳�HashMap��
   * ʵ��Map�ӿ�
## Java Doc ����
����Map�ӿڵ�һ����ʽ��ϣ��ʵ�֣����ĵ���˳���ǿ���Ԥ��ġ����ʵ����HashMap�ǲ�ͬ�����ڲ�����һ��˫���������������е�entry��
����������˵���˳�����˳�����key�Ĳ���˳��ע�����˳�򲻻���Ϊkey���ظ�������ܵ�Ӱ�졣�ٸ����ӣ�
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
����δ���������:
```
1
2
3
4
```
���ʵ����HashMap��һ����ѡ���Ϊhashmap�ڲ�Ԫ��������ģ����ұ�TreeMapʱ�临�Ӷ�Ҫ�͡�
LinkedHashMap��������һ��Map�Ŀ���������Ԫ�ص�˳����ԭMap��ͬ��
```
    void foo(Map m) {
         Map copy = new LinkedHashMap(m);
         ...
     }

```
LinkedHashMap�������������ݿ��������������ܹ�����ԭ�е�˳��������á�
LinkedHashMap��������һ�����⹹����������һ���ڲ�Ԫ��˳���ܷ���Ӱ��ģ������ٷ��ʵ������ʣ�Ҳ���Ƿ���˳������Map�ر��ʺϹ���LRU���档
����put��putIfAbsent��get��getOrDefault��computeIfPresent��merge�������ᵼ�¶�ӦԪ�ص�һ��"����"��
replace����ֻ����Ԫ�ر��滻��Żᵼ��һ��Ԫ�ط��ʡ�
putAll������Բ���map�е�ÿһ��entry���һ��Ԫ�ط��ʡ�

�����ķ������������Ԫ�ط��ʡ�������ǣ�collection����Ĳ���������Ӱ���ڲ�map��Ԫ��˳��
removeEldestEntry�������Ա���дΪ�����µ�ӳ����뵽map�У��ɵ�ӳ���Զ�ɾ����
������ṩ���ҵ�Map��������������nullԪ�ء���HashMapһ�������Ļ�������ʱ��(add, contains and remove)���Ӷȶ��ǳ���ʱ��ģ���Ȼ�ˣ�����
��ϣ�㷨���ȵĽ�Ԫ�����ݷֲ���buckets�С�
����Ҫ���ڲ�����һ���������ܿ��ܱ�HashMap��΢��һ�㣬������һ����������LinkedHashMap����collection����ĵ��������ĵ�ʱ����map��size
�����ȣ��븺�������޹ء�����һ��HashMapʱ�����Ӱ���ȡ���ڸ������ӡ�
>HashMap�����븺��������أ������Ҫ����ѧϰ

LinkedHashMap��������Ӱ�����ڲ����ܵĲ�����initial capacity��load factor��
�����������Ķ�����HashMap��ͬ��ע�⣬Ȼ����ΪLinkedHashMapѡ�����initial capacity�ĸ����ñ�HashMapҪ�ͣ���Ϊ������capacity�޹ء�

ע�����ʵ���Ƿ�ͬ���ġ��������̻߳Ტ������LinkedHashMap����������һ���̻߳��޸�LinkedHashMap�Ľṹ����ô�����뱻ͬ������
����㲻���������÷�װ����wrapһ������ࡣ������ڳ�ʼ��ʱ�����ã�Ϊ�˱���ͻ����ͬ�����ʣ�
`   Map m = Collections.synchronizedMap(new LinkedHashMap(...));`

�ṹ�仯���κ���ӻ�ɾ��Ԫ�صĲ����������ڷ���˳���LinkedHashMap�У��κλ�ı�Ԫ��˳��Ĳ�����

�ڲ���˳���LinkedHashMap�������ı�key����Ӧ��value����һ���ṹ�仯���ڷ���˳���LinkedHashMap�У�����get�ǽṹ�仯��
Collection���𷽷����ص�iterator��ķ������ǿ���ʧ�ܵģ�
���iterator����֮��map�����˽ṹ�仯������iterator�Լ���remove������iterator���׳�ConcurrentModificationException��
ע�⣬�������ConcurrentModificationExceptionȥд�����Ǵ���ģ���Ϊ�����ڲ����޸ĵ�����¿��ܷ����ܶ����飬�޷����κα�֤��
## ������
### public LinkedHashMap(int initialCapacity,float loadFactor)
��ʼ��һ��ָ��initial capacity �� load factor�ĿյĲ���˳���LinkedHashMap��
Throws:
IllegalArgumentException - ���initialCapacity�Ǹ������� or ���������Ǹ���

### public LinkedHashMap(int initialCapacity)
��ʼ��һ��ָ��initial capacity �� Ĭ��0.75��load factor�ĿյĲ���˳���LinkedHashMap��
Throws:
IllegalArgumentException - ���initialCapacity�Ǹ���

### public LinkedHashMap()
��ʼ��һ��Ĭ��16initial capacity �� Ĭ��0.75��load factor�ĿյĲ���˳���LinkedHashMap��
### public LinkedHashMap(Map<? extends K,? extends V> m)
Constructs an insertion-ordered LinkedHashMap instance with the same mappings as the specified map. The LinkedHashMap instance is created with a default load factor (0.75) and an initial capacity sufficient to hold the mappings in the specified map.

Throws:
NullPointerException - ���m��null
### public LinkedHashMap(int initialCapacity,float loadFactor,boolean accessOrder)
Constructs an empty LinkedHashMap instance with the specified initial capacity, load factor and ordering mode.
Parameters:
initialCapacity - the initial capacity
loadFactor - the load factor
accessOrder - the ordering mode - true for access-order, false for insertion-order
Throws:
IllegalArgumentException - ���initialCapacity�Ǹ������� or ���������Ǹ���
## ����

## Դ�����

## ��������

## ��չ�Ķ�

## �ܽ�

