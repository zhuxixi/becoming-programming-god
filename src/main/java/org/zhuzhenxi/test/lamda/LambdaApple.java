package org.zhuzhenxi.test.lamda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LambdaApple {

    public static void main(String[] args) {

        //lambda表达式练习
        apple();
        runner();
    }



    /**
     * lambda表达式练习-apple
     */
    private static void apple(){
        List<Apple> appleList = Arrays.asList(new Apple("1","green",5),
                new Apple("2","yellow",6),
                new Apple("3","brown",10),
                new Apple("4","red",9));

        appleList.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(appleList);

        //愚蠢的方法
        List<Apple> filterGreenApples1 = AppleUtil.filterGreenApples(appleList);
        //使用策略设计模式
        List<Apple> filterGreenApples2 = AppleUtil.filterApples(appleList,new AppleGreenColorPredicate());
        //策略设计模式
        List<Apple> filterGreenApples3 = AppleUtil.filterApples(appleList,new AppleHeavyWeightPredicate());
        //匿名类
        List<Apple> filterGreenApples4 = AppleUtil.filterApples(appleList,new ApplePredicate(){
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        //lambda表达式
        List<Apple> filterGreenApples5 = AppleUtil.filterApples(appleList,(Apple apple)->("red".equals(apple.getColor())));

        //使用抽象方法lambda表达式
        List<Apple> filterGreenApples6 = AppleUtil.filter(appleList,(Apple apple)->("brown".equals(apple.getColor())));
        System.out.println(filterGreenApples1);
        System.out.println(filterGreenApples2);
        System.out.println(filterGreenApples3);
        System.out.println(filterGreenApples4);
        System.out.println(filterGreenApples5);
        System.out.println(filterGreenApples6);
    }

    /**
     * lambda表达式练习-apple
     */
    private static void runner(){
        Runnable r1 = ()-> System.out.println("Hello World 1");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };

        RunnerUtil.process(r1);
        RunnerUtil.process(r2);
        RunnerUtil.process(()->System.out.println("Hello World 3"));
    }
}
