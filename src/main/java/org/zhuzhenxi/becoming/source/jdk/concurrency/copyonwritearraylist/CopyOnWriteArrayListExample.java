package org.zhuzhenxi.becoming.source.jdk.concurrency.copyonwritearraylist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhuzh
 * @date 2019.10.09
 */
public class CopyOnWriteArrayListExample {


    private static CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();



    public static void main(String[] args) {

        subListCheck();


    }

    /**
     * 截取子列表后不能修改原列表，否则对子列表的操作会抛出异常
     */
    private static void subListCheck() {
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(3);
        List<Integer> a = copyOnWriteArrayList.subList(2,3);

        copyOnWriteArrayList.add(4);
        System.out.println(a.get(0));
    }

}
