package org.zhuzhenxi.test.lamda;

/**
 * @AUTHOR Zhu zhenxi
 * @CREATE 2018-05-01-11:57
 **/
public class AppleHeavyWeightPredicate implements ApplePredicate {
	@Override
	public boolean test(Apple apple) {
		return apple.getWeight()>5;
	}
}
