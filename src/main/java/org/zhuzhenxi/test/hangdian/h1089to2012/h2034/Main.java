package org.zhuzhenxi.test.hangdian.h1089to2012.h2034;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int m = in.nextInt();
            int n = in.nextInt();
            if (n>100||n<0||m>100||m<0){
                continue;
            }
            if (m==0&&n==0){
                continue;
            }
            List<Integer> a = new LinkedList<>();
            List<Integer> b = new LinkedList<>();
            for (int i = 0; i < m; i++) {
                a.add(in.nextInt());
            }

            for (int i = 0; i < n; i++) {
                b.add(in.nextInt());
            }

//            a.removeIf(a1 -> b.contains(a1));
            for (Iterator<Integer> it = a.iterator();it.hasNext();){
                Integer current = it.next();
                if (b.contains(current)){
                    it.remove();
                }
            }
            if (a.size() == 0) {
                System.out.println("NULL");
                continue;
            }
            a.sort(Comparator.comparing(Integer::intValue));
            for (Iterator<Integer> it = a.iterator(); it.hasNext(); ) {
                Integer current = it.next();
                System.out.print(current);
//                if (it.hasNext()){
                    System.out.print(" ");
//                }
            }
            System.out.println();

        }
    }
}
