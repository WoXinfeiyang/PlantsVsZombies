package com.fxj.plantsvszombies.base;

/**防御类型植物基类*/
public abstract class DefancePlant extends Plant {

	public DefancePlant(String filepath) {
		super(filepath);
		life=200;
	}
}
