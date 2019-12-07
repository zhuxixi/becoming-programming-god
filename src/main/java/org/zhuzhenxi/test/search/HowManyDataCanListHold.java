package org.zhuzhenxi.test.search;

import java.util.ArrayList;
import java.util.List;

public class HowManyDataCanListHold {

    public static class Something{
        private int a;
        public Something(){
        }
        public Something(int b){
            a = b;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    public static void main(String[] args){
        List<Something> test = new ArrayList<>();
        test.add(new Something(1));
        test.add(new Something(2));
        test.add(new Something(3));
        for (Something a:test){
            if (a.getA()==2){
                test.remove(a);
            }
        }

        System.out.println(test);

    }


}
