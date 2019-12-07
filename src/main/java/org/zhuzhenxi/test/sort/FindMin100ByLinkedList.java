package org.zhuzhenxi.test.sort;

import org.zhuzhenxi.test.file.FileUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 从十亿个数中找出从小大排序的第100个数
 * @author zhuzhenxi
 * @date 2018.11.20
 */
public class FindMin100ByLinkedList {


    public static void main(String[] args) {

        //维护一个最小的链表，二叉树我不会，学完了再优化成二叉树
        List<Integer> min100 = new LinkedList<>();
        long start = System.currentTimeMillis();
        //循环取出文件，一共1万个文件，每个文件10万个数，每个数都是一个6位随机数
        for (int i = 0; i < 10000; i++) {
            assertMin100(min100, 0);
        }
        long end = System.currentTimeMillis();
        System.out.println("找出10e个数中的从小到大排序的第100个数，耗时:"+(end-start));

        System.out.println("最小的100，集合大小:"+min100.size());
        for (Iterator<Integer> it = min100.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
    }

    private static void assertMin100(List<Integer> min100, int i) {
        //读取文件，转换为列表
        List<String> currentList = FileUtil.readFile("number" + i);
        for (String str:currentList) {
            int currentInteger = Integer.valueOf(str);
            //如果为空直接放
            if (min100.size() == 0) {
                min100.add(currentInteger);
                continue;
            }
            //不为空就需要比较
            if (min100.size() < 100) {
                whenMin100NotFull(min100, currentInteger);
                continue;
            }
            whenMin100Full(min100, currentInteger);
        }
    }

    /**
     * min100还没满的时候，比last大可以继续放，如果比last小就需要放前面，然后修剪一下
     * @param min100
     * @param current
     */
    private static void whenMin100NotFull(List<Integer> min100, Integer current) {

        LinkedList<Integer> min100_ = (LinkedList<Integer>) min100;
        //如果 从文件取出的数大于min100.last(这个是链表中最大的),直接放最后
        if (current>=min100_.getLast()){
            min100_.addLast(current);
            return;
        }
        //如果小于最大值,遍历min100，放到该放入的位置
        cutMin100(min100, current, min100_);

    }

    /**
     * min100满了的时候，比last大就扔掉，如果比last小就需要放前面，然后修剪一下
     * @param min100
     * @param current
     */
    private static void whenMin100Full(List<Integer> min100, Integer current) {

        LinkedList<Integer> min100_ = (LinkedList<Integer>) min100;
        //如果 从文件取出的数大于min100.last(这个是链表中最大的),直接放最后
        if (current>=min100_.getLast()){
            return;
        }

        //如果小于最大值,遍历min100，放到该放入的位置
        cutMin100(min100, current, min100_);

    }

    /**
     * 修剪min100，比last小就遍历一下，放到该放的位置，然后修剪一下
     * @param min100
     * @param current
     * @param min100_
     */
    private static void cutMin100(List<Integer> min100, Integer current, LinkedList<Integer> min100_) {
        //如果小于最大值,倒叙遍历min100，放到该放入的位置
        for (ListIterator<Integer> it = min100.listIterator(); it.hasNext(); ) {
            Integer curIt = it.next();
            if (current <= curIt) {
                it.previous();
                it.add(current);
                break;
            }
        }

        if (min100_.size() > 100) {
            min100_.removeLast();
        }

    }

}
