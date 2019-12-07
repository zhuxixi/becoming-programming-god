package org.zhuzhenxi.test.algs4.algs20;

import java.util.Arrays;

/**
 * 二叉堆堆是基于二叉树的，所以也符合一些二叉树的性质
 * 二叉树的性质
 *
 * 二叉树有以下几个性质：
 * 性质1：二叉树第i层上的结点数目最多为 2{i-1} (i≥1)。
 * 性质2：深度为k的二叉树至多有2{k}-1个结点(k≥1)。
 * 性质3：包含n个结点的二叉树的高度至少为log2 (n+1)。
 * 性质4：在任意一棵二叉树中，若终端结点的个数为n0，度为2的结点数为n2，则n0=n2+1。
 * @author zhuzhenxi
 * @date 2019.04.24
 */
public class AliHeap {

    public static void main(String[] args) {
        AliHeap heap = new AliHeap();
        for (int i = 1; i < 8; i++) {
            heap.insert(i);
            System.out.println(Arrays.toString(heap.getA()));

        }

        heap.printHeap();
    }

    /**
     * 存数据的数据结构
     */
    private int[] a;

    private int realLength = 1;

    public int[] getA() {
        return a;
    }

    /**
     * 无参的构造方法
     * 默认初始化数组大小，10
     */
    public AliHeap(){
        a = new int[17];
    }

    /**
     * 初始化大小
     * @param size capacity
     */
    public AliHeap(int size){
        a = new int[size];
    }

    /**
     * 直接初始化入参
     * @param a1
     */
    public AliHeap(int[] a1){
        a = a1;
    }

    public void insert(int a1){
        a[realLength] = a1;
        swim(realLength++);
    }

    /**
     * 返回最大元素
     * @return
     */
    public int max(){
        return 0;
    }

    /**
     * 删除并返回最大元素
     * @return
     */
    public int delMax(){
        int max = a[1];
        exch(1,realLength);
        a[realLength] = 0;
        sink(1);
        return max;
    }

    /**
     * 是否为空
     */
    public boolean isEmpty(){
        if (a == null){
            return true;
        }
        return a.length==0;
    }

    public int size(){
        if (a==null){
            return 0;
        }
        return a.length;
    }

    /**
     * 关键方法，上浮
     * @param k
     */
    private void swim(int k)
    {
        while (k > 1 && a[k/2]<a[k])
        {
            exch(k, k / 2);
            k = k/2;
        }
    }
    private void printHeap(){
        if (isEmpty()){
            System.out.println("空堆");
            return;
        }
        System.out.println("数组实际大小:"+(realLength-1));

        Double highd = Math.log(realLength-1)/Math.log(2);
        System.out.println("当前堆double高度:"+highd);
        highd = Math.ceil(highd);
        int high = highd.intValue();
        System.out.println("当前堆高度:"+high);
        int allNodes = power2Value(high)-1;
        System.out.println("总节点数:"+allNodes);

        int i = 1;
        while (i<=high){
            //当前行的节点数
            int[] locations = new int[1];
            locations[0] = allNodes/2;
            //拿到需要输出数字的位置
            int[] locations2 =  getFillLine(i,1,locations);
            String[] blankLine = getBlankLine(allNodes);
            int m=0;
            for (int j = power2Value(i-1); j < power2Value(i); j++) {
                int currentNodeValue = a[j];
                blankLine[locations2[m++]] = String.valueOf(currentNodeValue);
            }
            if (i!=1){
                String[] lines = Arrays.copyOf(blankLine,blankLine.length);
                boolean leftOrRight = false;
                for (int j = 0; j < lines.length; j++) {
                    if (" ".equals(lines[j])){
                        continue;
                    }
                    leftOrRight = !leftOrRight;

                    if (leftOrRight){
                        lines[j] = "/";

                        continue;
                    }
                    lines[j] = "\\";
                }
                printLine(lines);
            }
            printLine(blankLine);
            i++;
        }
    }

    private void printLine(String[] line){
        for (int i = 0; i < line.length; i++) {
            System.out.print(line[i]);

        }
        System.out.println();
    }

    private int[] getFillLine(int height,int i,int[] locations){
        if (i==height) {
            return locations;
        }
        int[] news = new int[locations.length*2];
        int k = 0;
        for (int j = 0; j < locations.length; j++) {
            int left = locations[j] - 1;
            int right = locations[j] + 1 ;
            news[k] = left;
            news[k + 1] = right;
            k += 2;
        }

        return getFillLine(height, i+1, news);
    }


    private String[] getBlankLine(int allNodes){
        String[] lines = new String[allNodes];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = " ";
        }
        return lines;
    }

    /**
     * 计算2的powers幂
     * @param powers
     * @return
     */
    private int power2Value(int powers){
        if (powers==0){
            return 1;
        }
        return 2*power2Value(powers-1);
    }

    /**
     * 当前层的节点数
     * @param currentStep
     * @return
     */
    private int currentStepNodes(int currentStep){
        return power2Value(currentStep-1);
    }

    private void exch(int k, int i) {
        int temp = a[i];
        a[i] = a[k];
        a[k] = temp;
    }

    private void sink(int k)
    {
        while (2*k <= realLength)
        {
            int j = 2*k;
            if (j < realLength && a[j]<a[j+1]) {
                j++;
            }
            if (!(a[k]<a[j])) {
                break;
            }

            exch(j, k);
            k = j;
        }
    }

}
