package org.zhuzhenxi.test.hangdian.h1089to2012.h2036;


import java.util.Scanner;

class Main{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            int n=sc.nextInt();
            if(n==0){
                break;
            }
            int x[]=new int[n];	//存放x坐标
            int y[]=new int[n];	//存放y坐标
            for(int i=0;i<n;i++){
                x[i]=sc.nextInt();
                y[i]=sc.nextInt();
            }
            double area=0;
            for(int i=0;i<n;i++){
                int t=i+1;	//t为下一个顶点
                if(t==n){		//当i到第n个顶点时，令t也就是下一个顶点为第一个
                    t=0;
                }
                area+=(x[i]*y[t]-x[t]*y[i])/2.0;	//三角形向量计算公式
            }
            area=Math.abs(area);	//当面积为负时，使其为正
            System.out.println(String.format("%.1f",area));
        }
    }
}
