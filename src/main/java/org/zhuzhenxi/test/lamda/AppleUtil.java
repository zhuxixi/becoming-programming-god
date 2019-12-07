package org.zhuzhenxi.test.lamda;

import org.zhuzhenxi.test.lamda.Apple;
import org.zhuzhenxi.test.lamda.ApplePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @AUTHOR Zhu zhenxi
 * @CREATE 2018-05-01-11:36
 **/
public class AppleUtil {
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("green".equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}

	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate predicate) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (predicate.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (predicate.test(e)) {
				result.add(e);
			}
		}
		return result;
	}
}
