package org.zhuzhenxi.test.leetcode;

public class Reverse {
    public static void main(String[] args){
        System.out.println(reverse(901000));
    }
    public static int reverse(int x) {
        boolean negative = false;
        if(x<0){
            negative = true;
            x = 0-x;
        }

        String a = x+"";
        char[] ac = a.toCharArray();
        int length = ac.length;
        for(int i = 0;i<length/2;i++){
            char cu = ac[i];
            ac[i] = ac[length-i-1];
            ac[length-i-1] = cu;
        }
        int newx =0;
        try{

            newx = Integer.parseInt(new String(ac));
        }catch(Exception e){
            return 0;
        }

        if(negative){
            newx = 0- newx;
        }
        return newx;

    }
}
