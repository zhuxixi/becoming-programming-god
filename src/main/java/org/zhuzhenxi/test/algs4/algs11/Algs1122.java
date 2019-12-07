package org.zhuzhenxi.test.algs4.algs11;

public class Algs1122 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9,10,11,12};
        int key = 3;
        System.out.println(rankRecursion(key,a,0,a.length,1));
    }

    public static int rank(int key, int[] a)
    {
        int lo = 0;
        System.out.println("起始 lo="+lo);
        int hi = a.length - 1;
        System.out.println("起始 hi="+hi);
        System.out.println("进入循环");

        while (lo <= hi)
        {
            int mid = lo + (hi - lo) / 2;
            System.out.println("当前mid="+mid);
            if (key < a[mid]){
                System.out.println("小了！key<a[mid],a[mid]="+a[mid]);
                hi = mid - 1;
                System.out.println("小了！hi变了，hi="+hi);
            }
            else if (key > a[mid]) {
                System.out.println("大了！key>a[mid],a[mid]="+a[mid]);
                lo = mid + 1;
                System.out.println("大了！lo变了，lo="+lo);

            }
            else {
                System.out.println("找到啦！阿拉啦！mid="+mid);
                return mid;
            }
        }
        return -1;
    }

    public static int rankRecursion(int key, int[] a,int low,int high,int depth) {
        System.out.println("当前 low=" + low);
        System.out.println("当前 high=" + high);
        System.out.println("当前 递归深度=" + depth);
        if (low >= high) {
            return -1;
        }
        depth += 1;
        int mid = low + (high - low) / 2;
        System.out.println("当前mid=" + mid);
        if (key < a[mid]) {
            System.out.println("小了！key<a[mid],a[mid]=" + a[mid]);
            high = mid - 1;
            System.out.println("小了！high变了，high=" + high);
            return rankRecursion(key, a, low, high, depth);
        }
        if (key > a[mid]) {
            System.out.println("大了！key>a[mid],a[mid]=" + a[mid]);
            low = mid + 1;
            System.out.println("大了！low变了，low=" + low);
            return rankRecursion(key, a, low, high, depth);
        }

        System.out.println("找到啦！阿拉啦！mid=" + mid);
        return mid;


    }
}
