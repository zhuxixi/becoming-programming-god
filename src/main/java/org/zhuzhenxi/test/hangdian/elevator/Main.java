package org.zhuzhenxi.test.hangdian.elevator;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * @Author:Zhuxixi
 * @Description:
 * @Date:Create in  2018/12/11 8:49 PM
 */
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        while (scanner.hasNext()){
            String next = scanner.nextLine();
            String[] inputArray = next.split(" ");
            int head = Integer.parseInt(inputArray[0]);
            if (head==0){
                continue;
            }
            int cost = 0;
            int currentFloor = 0;
            for (int i = 1; i < head+1; i++) {
                int destination = Integer.parseInt(inputArray[i]);
                if (destination>currentFloor){
                    while (destination-currentFloor>0){
                        cost = up(cost);
                        currentFloor++;
                    }
                    cost = stay(cost);
                    continue;
                }
                while (currentFloor-destination>0){
                    cost = down(cost);
                    currentFloor--;
                }
                cost = stay(cost);
            }
            System.out.println(cost);
        }
    }

    /**
     * 下楼需要4秒
     * @param cost
     * @return
     */
    private static int down(int cost){
        cost+=4;
        return cost;
    }

    /**
     * 上楼需要6秒
     * @param cost
     * @return
     */
    private static int up(int cost){
        cost+=6;
        return cost;
    }

    /**
     * 停留需要5秒
     * @param cost
     * @return
     */
    private static int stay(int cost){
        cost+=5;
        return cost;
    }
}
