package com.fxj.plantsvszombies.bean;

import org.cocos2d.actions.base.CCAction;

import com.fxj.plantsvszombies.base.DefancePlant;
import com.fxj.plantsvszombies.utils.CommonUtils;

public class Nut extends DefancePlant {

	/**土豆(继承自防御类型植物基类)*/
	public Nut() {
		super("image/plant/nut/p_3_01.png");
		baseAction();
	}

	@Override
	public void baseAction() {
		CCAction animate=CommonUtils.getAnimate("image/plant/nut/p_3_%02d.png",11,true);
		this.runAction(animate);
	}

}
