# AutoCloseable详解

## Java Doc 正文
代表一个对象在close之前可能持有某些资源(文件或socket)。如果对象是在try-with-resources代码块中声明的，
AutoCloseable对象的close()方法会被自动执行。这种构造方式保证了最快的资源释放，避免资源耗尽异常。
## 方法
### void close() throws Exception
关闭资源，放弃所有内在的资源。如果对象是在try-with-resources代码块中声明的，那么这个方法会自动被执行。
虽然这个接口的方法声明成抛出Exception异常，但是强烈推荐实现类抛出更详细的异常，或者不要要出异常。
需要注意实现类的close操作可能会失败。 强烈推荐优先放弃内部资源和内部标记资源已经关闭，尽量不去抛出异常。close方法不会执行
多次，所以要确保资源能够及时释放。此外，优先关闭内部资源可以降低资源封装带来的问题，假如一个资源包含了另一个资源，要依次关闭。

实现类也强烈建议不要抛出InterruptedException异常。这个异常和thread的 interrupt标志相关，而且线程运行时可能会出现一些迷惑行为。
这个接口的close方法虽然没有明确要求是幂等的，但是也推荐大家做成幂等的。
## 样例代码
写一段简单的测试代码来验证在try-with-resources代码块中声明AutoCloseable类，jvm会自动调用close方法

### AutoCloseable的实现类

```$xslt
import lombok.Data;

@Data
public class AutoCloseAbleTest implements AutoCloseable {

    private boolean closed = false;

    @Override
    public void close() throws IllegalArgumentException {
        closed = true;

        throw new IllegalArgumentException("测试抛出异常");
    }


    public void doSomething(){
        System.out.println("现在资源是开着的");
    }
}
```
### 测试类
```
public class AutoCloseAbleExample {

    /**
     * 使用try-with-resources模式声明资源
     * @param args
     */
    public static void main(String[] args){
        try(AutoCloseAbleTest test = new AutoCloseAbleTest()){
            test.doSomething();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }
}
```
### 执行结果
```$xslt
现在资源是开着的
java.lang.IllegalArgumentException: 测试抛出异常
	at org.zhuzhenxi.learning.lang.autocloseable.AutoCloseAbleTest.close(AutoCloseAbleTest.java:14)
	at org.zhuzhenxi.learning.lang.autocloseable.AutoCloseAbleExample.main(AutoCloseAbleExample.java:7)
```

