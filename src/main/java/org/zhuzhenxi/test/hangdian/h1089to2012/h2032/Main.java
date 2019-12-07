package org.zhuzhenxi.test.hangdian.h1089to2012.h2032;

import java.util.Scanner;

public class Main {


    public static final Floor ONE = new Floor();
    static {
        ONE.setLast(null);
        ONE.current = new int[1];
        ONE.current[0]=1;
    }
    public static final Floor TWO = new Floor();
    static {
        TWO.setLast(ONE);
        TWO.current = new int[2];
        TWO.current[0] = 1;
        TWO.current[1] = 1;
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int nums = in.nextInt();
            int j = 1;
            Floor last = TWO;
            while (j<nums+1){
                if (j==1){
                    ONE.print();
                    System.out.println();
                    j= j+1;
                    continue;
                }
                if (j==2){
                    TWO.print();
                    System.out.println();
                    j= j+1;
                    continue;
                }
                Floor current = new Floor(last);
                current.calculateCurrent();
                last = current;
                current.print();
                System.out.println();
                j= j+1;
            }
            System.out.println();

        }
    }

    private static class Floor{
        private Floor last;

        private int[] current;

        public Floor(){

        }

        public Floor(Floor last1){
            last = last1;
        }

        public void calculateCurrent(){
            int[] lastFloor = last.current;
            int[] currentFloor = new int[lastFloor.length+1];
            currentFloor[0]=1;
            currentFloor[currentFloor.length-1] = 1;
            for (int i = 1; i < currentFloor.length-1; i++) {
                currentFloor[i] = lastFloor[i-1]+lastFloor[i];
            }
            current = currentFloor;
        }

        public void print(){
            for (int i = 0; i < current.length; i++) {
                System.out.print(current[i]);
                if (i+1==current.length){
                    continue;
                }
                System.out.print(" ");
            }
        }


        public void setLast(Floor last) {
            this.last = last;
        }


    }
}
