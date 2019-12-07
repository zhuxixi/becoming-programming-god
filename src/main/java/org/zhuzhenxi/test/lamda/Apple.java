package org.zhuzhenxi.test.lamda;

import lombok.Data;

/**
 * @AUTHOR Zhu zhenxi
 * @CREATE 2018-05-01-11:21
 **/
@Data
public class Apple {

	private String id;
	private String color;
	private Integer weight;

	public Apple(String id,String color,int weight){
		this.id=id;
		this.color=color;
		this.weight = weight;
	}
}
