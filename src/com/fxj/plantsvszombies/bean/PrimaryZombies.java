package com.fxj.plantsvszombies.bean;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.fxj.plantsvszombies.base.BaseElement;
import com.fxj.plantsvszombies.base.Plant;
import com.fxj.plantsvszombies.base.Zombies;
import com.fxj.plantsvszombies.utils.CommonUtils;

/**������ʬ*/
public class PrimaryZombies extends Zombies {

	/***
	 * ������ʬ
	 * @param startPoint:��ʬ�˶������
	 * @param endPoint:��ʬ�˶����յ�
	 * */
	public PrimaryZombies(CGPoint startPoint,CGPoint endPoint)
	{
		super("image/zombies/zombies_1/walk/z_1_01.png");
		this.startPoint=startPoint;
		this.endPoint=endPoint;
		
		setPosition(startPoint);
		move();
	}
	
	@Override
	public void move() {
		/*��ʬ��·����֡����*/
		CCAction animate=CommonUtils.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png",7,true);
		this.runAction(animate);
		
		float t=CGPointUtil.distance(getPosition(),this.endPoint)/speed;
		CCMoveTo moveTo=CCMoveTo.action(t,this.endPoint);/*��ʬ�˶�����*/
		CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this,"endGame"));
		this.runAction(sequence);
	}
	
	/**������Ϸ*/
	public void endGame(){
//		CCSprite tipsForZombieWin=CCSprite.sprite("image/fight/ZombiesWon.jpg");
//		tipsForZombieWin.setPosition(CCDirector.sharedDirector().getWinSize().width/2,CCDirector.sharedDirector().getWinSize().height/2);
//		this.getParent().addChild(tipsForZombieWin);
		
		destory();
		System.out.println("������ʬ��������!");
		
	}
	
	/**��ʬ������Ŀ��-ֲ��*/
	private Plant targetPlant=null;
	
	/*��ʬ����Ŀ�귽��*/
	@Override
	public void attack(BaseElement element) {
		/*����ʬ������Ŀ����ֲ��ʱ*/
		if(element instanceof Plant){
			Plant plant=(Plant) element;/*����ǿ��ת��*/
			
			if(this.targetPlant==null){/*�����ʬ����Ŀ�껹û�б�������������ʬ����Ŀ��*/
				this.targetPlant=plant;
				stopAllActions();/*ֹͣ���ж���*/
				/*��ʬ����ֲ���ֲ������֡����*/
				CCAction animate=CommonUtils.getAnimate("image/zombies/zombies_1/attack/z_1_attack_%02d.png",10,true);
				this.runAction(animate);
				CCScheduler.sharedScheduler().schedule("attackPlant",this, 0.5f,false);
			}
		}

	}
	
	/*����ʬ����Ŀ��ʱ,������Ŀ�걻�����������*/
	public void attackPlant(float t){
		/*����ֲ�ﱻ��������*/
		this.targetPlant.attacked(attack);
		if(this.targetPlant.getLife()<=0){
			this.targetPlant.destory();
			this.targetPlant=null;/*����Ŀ��*/
				
			CCScheduler.sharedScheduler().unschedule("attackPlant", this);/*�Ƴ���ʱ����*/
			stopAllActions();
			move();
		}
		
	}
	
	/*��ʬ����������*/
	@Override
	public void attacked(int attack) {
		this.life-=attack;
		if(this.life<=0){
			destory();
		}

	}

	@Override
	public void baseAction() {
		// TODO Auto-generated method stub

	}

}
