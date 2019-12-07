package org.zhuzhenxi.test.lamda;

/**
 * @AUTHOR Zhu zhenxi
 * @CREATE 2018-05-01-11:58
 **/
public class AppleGreenColorPredicate implements ApplePredicate {
	@Override
	public boolean test(Apple apple) {
		return "green".equals(apple.getColor());
	}
}
