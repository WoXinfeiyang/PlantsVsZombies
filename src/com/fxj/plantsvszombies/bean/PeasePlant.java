package com.fxj.plantsvszombies.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

import com.fxj.plantsvszombies.base.AttackPlant;
import com.fxj.plantsvszombies.base.Bullet;
import com.fxj.plantsvszombies.utils.CommonUtils;

/**豆类植物,AttackPlant攻击类型植物子类*/
public class PeasePlant extends AttackPlant {

	
	public PeasePlant() {
		super("image/plant/pease/p_2_01.png");
		baseAction();
	}

	@Override
	public Bullet createBullet() {
		if(this.bullets.size()<1){/*证明之前没有创建子弹*/
			final Pease pease=new Pease();
			pease.setPosition(CCNode.ccp(this.getPosition().x+20, this.getPosition().y+40));
			this.getParent().addChild(pease);
			
			pease.setDieListener(new DieListener() {
				
				@Override
				public void die() {
					bullets.remove(pease);
					
				}
			});
			
			this.bullets.add(pease);
			
			pease.move();
		}
		
		return null;
	}
	
	/*原地不动的基本动作*/
	@Override
	public void baseAction() {
		CCAction animate=CommonUtils.getAnimate("image/plant/pease/p_2_%02d.png",8,true);
		this.runAction(animate);
	}

}
