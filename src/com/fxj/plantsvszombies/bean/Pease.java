package com.fxj.plantsvszombies.bean;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.fxj.plantsvszombies.base.Bullet;

/**������*/
public class Pease extends Bullet {

	
	public Pease() {
		super("image/fight/bullet.png");
		setScale(0.65f);
	}

	/*�ӵ��ƶ�����*/
	@Override
	public void move() {
		/*��ǰ�ӵ���λ��*/
		CGPoint currentPoint=getPosition();
		CGPoint targetPoint=ccp(CCDirector.sharedDirector().getWinSize().width,currentPoint.y);
		/*�˶�ʱ��*/
		float t=CGPointUtil.distance(currentPoint, targetPoint)/speed;
		CCMoveTo moveTo=CCMoveTo.action(t, targetPoint);
		CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this, "destory"));
		this.runAction(sequence);
	}

}
