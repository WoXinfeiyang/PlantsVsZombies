package com.fxj.plantsvszombies.base;

import org.cocos2d.types.CGPoint;

/**僵尸基类*/
public abstract class Zombies extends BaseElement {
	/**生命值*/
	protected int life=50;
	/**攻击力*/
	protected int attack=10;
	/**僵尸移动速度*/
	protected int speed=20;
	
	/**僵尸运动的起点*/
	protected CGPoint startPoint;
	/**僵尸运动的终点*/
	protected CGPoint endPoint;
	
	
	public Zombies(String filepath) {
		super(filepath);
		setScale(0.5f);
		setAnchorPoint(0.5f,0);
	}
	
	/**僵尸移动方法,Zombies中的抽象方法*/
	public abstract void move();
	
	/**僵尸攻击目标方法,Zombies中的抽象方法
	 * @param element:攻击对象
	 * */
	public abstract void attack(BaseElement element);
	
	/**
	 * 僵尸被攻击方法,Zombies中的抽象方法
	 * @param attack:攻击值
	 * */
	public abstract void attacked(int attack);
}
