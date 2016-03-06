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

/**初级僵尸*/
public class PrimaryZombies extends Zombies {

	/***
	 * 初级僵尸
	 * @param startPoint:僵尸运动的起点
	 * @param endPoint:僵尸运动的终点
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
		/*僵尸走路序列帧动画*/
		CCAction animate=CommonUtils.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png",7,true);
		this.runAction(animate);
		
		float t=CGPointUtil.distance(getPosition(),this.endPoint)/speed;
		CCMoveTo moveTo=CCMoveTo.action(t,this.endPoint);/*僵尸运动动作*/
		CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this,"endGame"));
		this.runAction(sequence);
	}
	
	/**结束游戏*/
	public void endGame(){
//		CCSprite tipsForZombieWin=CCSprite.sprite("image/fight/ZombiesWon.jpg");
//		tipsForZombieWin.setPosition(CCDirector.sharedDirector().getWinSize().width/2,CCDirector.sharedDirector().getWinSize().height/2);
//		this.getParent().addChild(tipsForZombieWin);
		
		destory();
		System.out.println("初级僵尸被销毁了!");
		
	}
	
	/**僵尸攻击的目标-植物*/
	private Plant targetPlant=null;
	
	/*僵尸攻击目标方法*/
	@Override
	public void attack(BaseElement element) {
		/*当僵尸攻击的目标是植物时*/
		if(element instanceof Plant){
			Plant plant=(Plant) element;/*类型强制转换*/
			
			if(this.targetPlant==null){/*如果僵尸攻击目标还没有被锁定则锁定僵尸攻击目标*/
				this.targetPlant=plant;
				stopAllActions();/*停止所有动作*/
				/*僵尸攻击植物吃植物序列帧动画*/
				CCAction animate=CommonUtils.getAnimate("image/zombies/zombies_1/attack/z_1_attack_%02d.png",10,true);
				this.runAction(animate);
				CCScheduler.sharedScheduler().schedule("attackPlant",this, 0.5f,false);
			}
		}

	}
	
	/*当僵尸攻击目标时,处理攻击目标被攻击相关事务*/
	public void attackPlant(float t){
		/*调用植物被攻击方法*/
		this.targetPlant.attacked(attack);
		if(this.targetPlant.getLife()<=0){
			this.targetPlant.destory();
			this.targetPlant=null;/*解锁目标*/
				
			CCScheduler.sharedScheduler().unschedule("attackPlant", this);/*移除定时任务*/
			stopAllActions();
			move();
		}
		
	}
	
	/*僵尸被攻击方法*/
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
