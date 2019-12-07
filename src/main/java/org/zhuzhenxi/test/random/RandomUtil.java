package org.zhuzhenxi.test.random;

import java.util.Random;

/**
 * 随机数生成类,用于各类测试
 */
public class RandomUtil {


    /**
     * 产生n位随机数
     * @return
     */
    public static long generateRandomNumber(int n){
        if(n<1){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }



    /**
     * 随机生成6位整数
     * @return
     */
    public static int generateRandomNumber(){
        Random random = new Random();
        int x = random.nextInt(899999);
        x = x+100000;
        return x;
    }
    /**
     * 返回随机字符串，同时包含数字、大小写字母
     * @param len 字符串长度，不能小于3
     * @return String 随机字符串
     */
    public static String generateRandomString(int len){
        if(len < 3){
            throw new IllegalArgumentException("字符串长度不能小于3");
        }
        //数组，用于存放随机字符
        char[] chArr = new char[len];
        //为了保证必须包含数字、大小写字母
        chArr[0] = (char)('0' + StdRandom.uniform(0,10));
        chArr[1] = (char)('A' + StdRandom.uniform(0,26));
        chArr[2] = (char)('a' + StdRandom.uniform(0,26));


        char[] codes = { '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z','a','b','c','d',
                'e','f','g','h','i','j','k','l','m','n',
                'o','p','q','r','s','t','u','v','w','x',
                'y','z'};
        //charArr[3..len-1]随机生成codes中的字符
        for(int i = 3; i < len; i++){
            chArr[i] = codes[StdRandom.uniform(0,codes.length)];
        }

        //将数组chArr随机排序
        for(int i = 0; i < len; i++){
            int r = i + StdRandom.uniform(len - i);
            char temp = chArr[i];
            chArr[i] = chArr[r];
            chArr[r] = temp;
        }

        return new String(chArr);
    }


}
