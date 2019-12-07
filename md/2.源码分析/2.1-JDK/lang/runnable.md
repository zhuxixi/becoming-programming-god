>本文除代码样例都是Java Doc的翻译
## 相关阅读
* FunctionalInterface(待施工),此注解表明这个接口支持lambda表达式和方法引用
## 概要
Runnable接口的实现类一般都是用来在一个线程中执行的。实现类必须定义一个run()方法。Thread接口就是这个接口的实现类，只要thread是激活状态，就会执行run方法。

## 方法

void run()
当一个对象实现了Runnable接口，意味着要使用它在另一个线程中执行这个方法。执行thread.start启动线程并会执行run方法。
See Also:
Thread.run()

## 注意
使用Runnable.run()方法执行业务逻辑，不会创建新的线程。需要使用Thread.start()才会创建一个新的线程;

## 代码样例

### 测试代码

```
/**
 * @author zhuzh
 * @date 2019.10.12
 */
public class RunnableExample {

    public static void main(String[] args){
        Runnable runner = () -> System.out.println(Thread.currentThread().getName());
        //直接调用run方法，不会启动新的线程
        runner.run();
        Thread thread = new Thread(runner);
        //直接调用thread.run也不会启动新的线程
        thread.run();
        //只有调用start才会启动新的线程
        thread.start();
    }
}
```
### 代码输出
```
main
main
Thread-0

Process finished with exit code 0
```
