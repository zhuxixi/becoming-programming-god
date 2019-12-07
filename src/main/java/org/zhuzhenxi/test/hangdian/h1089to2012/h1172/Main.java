package org.zhuzhenxi.test.hangdian.h1089to2012.h1172;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int lines = scanner.nextInt();
            List<GuessResult> guessResults = new ArrayList<>();
            while (lines>0&&scanner.hasNext()){
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                guessResults.add(new GuessResult(a,b,c));
                lines--;
            }
            guessResults.sort(Comparator.comparing(GuessResult::getRightLocation).reversed());
            Calculator cl = new Calculator(guessResults);
            cl.clearNoNeed();
            cl.tryToMakeSure();


        }
    }

    /**
     * 计算类
     * 6
     * 4815 2 1
     * 5716 1 0
     * 7842 1 0
     * 4901 0 0
     * 8585 3 3
     * 8555 3 2
     */
    private static class Calculator{

        /**
         * 原始池
         * 所有的四位数的每一位都是由0~9组成的
         */
        private List<Integer> originPool = new ArrayList<>();
        /**
         * 排除池
         * 如果正确数字数与正确数字位数都是0，说明这个四位数完全不吻合，丢到排除池中
         * 已经排除了 例子中 已经排除了 4901这四个数字
         */
        private List<Integer> noNeedPool = new ArrayList<>();
        /**
         * 可能池
         * 如果正确数字数>正确数字位数，那这几个数字就进入可能池
         * 当已经排除了 4901这四个数字后
         * 4815 2 1 经过排除池过滤，变为8 5 2 1，也就是说，8 5 这两个数字是最终结果中包含的数字，但是正确数字位数只有1，也就说现在
         * 还不太确定数字的位置，只能先把这两个数字放进可能池
         */
        private List<Integer> mayBePool = new ArrayList<>();
        private List<GuessResult> results = new ArrayList<>();

        private String resultStr = "Not sure";
        private int[] finalResult =new int[4];
        private boolean find = false;

        public Calculator(List<GuessResult> results1){
            originPool.add(0);
            originPool.add(1);
            originPool.add(2);
            originPool.add(3);
            originPool.add(4);
            originPool.add(5);
            originPool.add(6);
            originPool.add(7);
            originPool.add(8);
            originPool.add(9);
            results.addAll(results1);
        }


        /**
         * 如果正确数字数与正确数字位数都是0，说明这个四位数完全不吻合，丢到排除池中
         */
        public void clearNoNeed(){
            Iterator<GuessResult> it = results.iterator();
            while (it.hasNext()){
                GuessResult c  = it.next();
                if (c.getRightBit() == 0&&c.getRightLocation()==0){
                    int[] canClear = spilt(c.getGuess());
                    for (int i = 0; i < canClear.length; i++) {
                        noNeedPool.add(canClear[i]);
                        originPool.remove(canClear[i]);
                    }
                    it.remove();
                }
            }
        }

        public void tryToMakeSure(){
            for (int i = 0; i < results.size(); i++) {
                GuessResult guessResult = results.get(i);
                int[] nums = spilt(guessResult.getGuess());
                int filterNum = 0;
                for (int j = 0; j < nums.length; j++) {
                    if (!noNeedPool.contains(nums[j])){
                        filterNum+=1;
                        mayBePool.add(nums[j]);
                        //如果上一轮已经有搞定的数字
                        if (finalResult[j]!=0){
                            finalResult[j] = nums[j];

                        }
                    }
                }
                //如果去掉一些不要的数字，剩下的数字数与数字正确位数都一样，那就说明这几个位置的数字已经确定了
                boolean find = filterNum==guessResult.getRightBit()&&filterNum==guessResult.getRightLocation();
                if (!find){
                    for (int j = 0; j < finalResult.length; j++) {
                        finalResult[j] = 0;
                    }
                }

            }
        }

        /**
         * 把四位数字分割成四个字符串
         * @param num
         * @return
         */
        private int[] spilt(int num){
            int[] result = new int[4];
            result[0] = num/1000;
            result[1] = num%(result[0]*1000)/100;
            result[2] = num%(result[0]*1000+result[1]*100)/10;
            result[3] = num%(result[0]*1000+result[1]*100+result[2]*10);
            return result;
        }
    }

    private static class GuessResult{
        private int guess;
        private int rightBit;
        private int rightLocation;

        public GuessResult(int guess1,int rightBit1,int rightLocation1){
            guess = guess1;
            rightBit = rightBit1;
            rightLocation = rightLocation1;
        }

        public int getGuess() {
            return guess;
        }

        public void setGuess(int guess) {
            this.guess = guess;
        }

        public int getRightBit() {
            return rightBit;
        }

        public void setRightBit(int rightBit) {
            this.rightBit = rightBit;
        }

        public int getRightLocation() {
            return rightLocation;
        }

        public void setRightLocation(int rightLocation) {
            this.rightLocation = rightLocation;
        }
    }
}
