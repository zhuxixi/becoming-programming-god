package org.zhuzhenxi.test.hangdian.h1089to2012.h1205;

import java.util.*;

public class Main2 {


    private static FatBoy fatBoy = new FatBoy();


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int nums = sc.nextInt();
            while (nums>0&&sc.hasNext()){
                //多少种糖果
                int kinds = sc.nextInt();
                int sugarAmount = 0;
                Map<Integer,Integer> sugars = new HashMap<>(kinds);
                //每种糖果多少个
                for (int i = 0; i < kinds; i++) {
                    int howManySugar = sc.nextInt();
                    sugarAmount += howManySugar;
                    sugars.put(i,howManySugar);
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
        private Map<Integer,Integer> buckets;
        private int lastEatSugarKind = -1;
        private int sugarAmount = 0;

        public FatBoy(){

        }

        public void prepareToEat(Map<Integer,Integer> buckets1,int sugarAmount1){
            buckets = buckets1;
            sugarAmount = sugarAmount1;
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
                for (Map.Entry<Integer,Integer> entry:buckets.entrySet()){
                    int currentIndex = entry.getKey();
                    int sugarNums = entry.getValue();
                    if (currentIndex==lastEatSugarKind){
                        return "No";
                    }

                    if (!canEat(sugarNums)){
                        continue;
                    }

                    entry.setValue(sugarNums-1);
                    sugarAmount -=1;
                    lastEatSugarKind = currentIndex;

                }

            }
            return "Yes";
        }

    }

    public static boolean canEat(int sugarNum){
        if (sugarNum>0){
            return true;
        }
        return false;
    }
}
