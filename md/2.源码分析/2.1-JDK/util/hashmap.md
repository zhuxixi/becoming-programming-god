# HashMapԴ�����

## ����֪ʶ

* AbstractMap
* Map<K,V>
* Cloneable
* Serializable

## Java Doc ����
����һ��Map�ӿڵĹ�ϣ��ʵ�֡����ʵ���ṩ�����е�map����������nullֵ��null����(HashMap���Hashtable����һ����������HashMap�����̰߳�ȫ��
��������null��)����಻�ᱣ֤map��Ԫ�ص�˳�� ����Ԫ�ص�˳�򲻻�һֱ���ֲ��䣬����������Ԫ�ص���ӣ�˳��ͱ仯�ˡ�

����hash�㷨�㹻���㣬û�й�ϣ��ͻ��������ϣ���е�ÿ��Ԫ�ض���ɢ�ڲ�ͬ�Ĺ�ϣͰ��ʱ����ôhashMap��һЩ������������O(1)���Ӷȵģ����һ��(get��put)��
�����������ϵĺ�ʱ��HashMap��capacity������size�����ȡ���ˣ�������������ܶ�����˵����Ҫ����ô��Ҫ��initial capacity����̫��(���߸�����������̫��)
��
> �������ԭ��

HashMap������������Ӱ��������: initial capacity�� load factor(��������)
capacity���ǹ�ϣ���Ͱ��������initial capacity���ǹ�ϣ���ڴ���ʱ��capacity(�ϻ�)��
load factor����������ϣ���ˮλ��Ȼ���Զ���������capacity����entry����������`load factor * current capacity`ʱ����ϣ��ͻ�rehash(
���е��ڲ����ݶ����ؽ�)���Թ�ϣ�����2���ĳ��ȡ�

һ����˵��Ĭ�ϵ�load factor(.75)��ʱ��Ϳռ䶼�����ŵġ�������ֵ�ܴ󣬻���ٿռ��˷ѣ����ǻ���߲��ҵ�����(�����HashMap�ķ���������Ӱ�죬����get��put)��
HashMapҪ�������������load factorӦ����map��ʼ��ʱ��Ҫȥ���ǣ��������ܼ���rehash�Ĵ�������Ϊ���Ǻܺķ����ܵġ�
���initial capacity ����entry���ֵ���� load factor(`initial capacity> entry count /load factor`)����ô��Զ�����ᷢ��rehash��

�����Ҫ��������ӳ���ϵ��HashMap����ʼ��HashMapʱҪָ�����㹻��capacity���������Ա���rehash��������Ҫע���һ�㣬����ܶ�key����ͬ����hashCode()
���ͻή�͹�ϣ������ܣ���Ϊͬ����hashCode()����ɹ�ϣ��ͻ��������ϣ����˻�������
**���ʵ�ֲ����̰߳�ȫ��**��������̲߳�������hash map������������һ���߳��޸�hashMap�����ݣ�������������ͬ��������

�����ʹ�� Collections.synchronizedMap����������һ��ͬ����Map�������������ڳ�ʼ��mapʱ������:

  `Map m = Collections.synchronizedMap(new HashMap(...));`
������map�ڲ�Ԫ����iterator����֮�����仯(key������value���˲���)������iterator�Լ���ɾ������֮�⣬�����ķ������û��׳�ConcurrentModificationException��
���̣���Բ����޸ĵ������iterator�ǿ���ʧ�ܵġ�
�㲻���������ConcurrentModificationException�쳣ȥ�������ܣ���Ϊ���ǲ��ɿ��ģ�����Ҫ�Ӹ�����ȥ���������⡣
HashMap�� Java ���Ͽ�ܵĳ�Ա֮һ��

## ������

## ����

## Դ�����

## ��������

## ��չ�Ķ�
* LinkedHashMap
* PrinterStateReasons

## �ܽ�

