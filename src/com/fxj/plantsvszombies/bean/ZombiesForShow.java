package com.fxj.plantsvszombies.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

import com.fxj.plantsvszombies.utils.CommonUtils;

import android.graphics.Bitmap;

/**展示用的僵尸类*/
public class ZombiesForShow extends CCSprite {

	public ZombiesForShow() {
		super("image/zombies/zombies_1/shake/z_1_01.png");
		setScale(0.5f);
		setAnchorPoint(0.5f,0);
		CCAction animate=CommonUtils.getAnimate("image/zombies/zombies_1/shake/z_1_%02d.png",2,true);
		this.runAction(animate);
	}
	
}
