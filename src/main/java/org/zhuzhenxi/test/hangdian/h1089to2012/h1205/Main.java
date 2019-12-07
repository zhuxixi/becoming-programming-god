package org.zhuzhenxi.test.hangdian.h1089to2012.h1205;

import java.util.*;

public class Main {


    private static FatBoy fatBoy = new FatBoy();
    private static List<SugarBucket> sugars = new LinkedList<>();


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int nums = sc.nextInt();
            while (nums>0&&sc.hasNext()){
                //多少种糖果
                int kinds = sc.nextInt();
                int sugarAmount = 0;
                //每种糖果多少个
                for (int i = 0; i < kinds; i++) {
                    int howManySugar = sc.nextInt();
                    sugarAmount += howManySugar;
                    sugars.add(new SugarBucket(howManySugar,i));
                }
                //准备好糖果，要吃了
                fatBoy.prepareToEat(sugars,sugarAmount);
                //开吃
                System.out.println(fatBoy.startEating());
                //吃完后还原数据
                fatBoy.shit();
                sugars.clear();
                nums--;
            }
        }
    }


    private static class FatBoy{
        private List<SugarBucket> buckets;
        private int lastEatSugarKind = -1;
        private int sugarAmount = 0;

        public FatBoy(){

        }

        public void prepareToEat(List<SugarBucket> buckets1,int sugarAmount1){
            buckets = buckets1;
            sugarAmount = sugarAmount1;
            buckets.sort(Comparator.comparing(SugarBucket::getNums).reversed());
        }

        public void shit(){
            buckets = null;
            sugarAmount = 0;
            lastEatSugarKind = -1;
        }
        /**
         *
         * @return
         */
        public String startEating(){
            while (sugarAmount>0){
                ListIterator<SugarBucket> it = buckets.listIterator();
                while (it.hasNext()){
                    SugarBucket bucket = it.next();
                    if (bucket.getIndex()==lastEatSugarKind){
                        return "No";
                    }
                    //吃光了
                    if (bucket.eat()){
                        it.remove();
                        continue;
                    }
                    sugarAmount -=1;
                    lastEatSugarKind = bucket.getIndex();
                }
            }
            return "Yes";
        }

    }

    /**
     * 糖果桶
     */
    private static class SugarBucket{
        private int index = 0;
        private int nums = 0;

        public SugarBucket(int many,int index1){
            nums = many;
            index = index1;
        }

        public int getNums() {
            return nums;
        }

        public int getIndex() {
            return index;
        }

        /**
         * 吃一个糖果，
         * @return 如果吃光了返回true,没吃完就返回false
         */
        public boolean eat(){
            if (nums>0){
                nums-=1;
                return false;
            }
            return true;
        }
    }
}
