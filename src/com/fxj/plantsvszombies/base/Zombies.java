package com.fxj.plantsvszombies.base;

import org.cocos2d.types.CGPoint;

/**��ʬ����*/
public abstract class Zombies extends BaseElement {
	/**����ֵ*/
	protected int life=50;
	/**������*/
	protected int attack=10;
	/**��ʬ�ƶ��ٶ�*/
	protected int speed=20;
	
	/**��ʬ�˶������*/
	protected CGPoint startPoint;
	/**��ʬ�˶����յ�*/
	protected CGPoint endPoint;
	
	
	public Zombies(String filepath) {
		super(filepath);
		setScale(0.5f);
		setAnchorPoint(0.5f,0);
	}
	
	/**��ʬ�ƶ�����,Zombies�еĳ��󷽷�*/
	public abstract void move();
	
	/**��ʬ����Ŀ�귽��,Zombies�еĳ��󷽷�
	 * @param element:��������
	 * */
	public abstract void attack(BaseElement element);
	
	/**
	 * ��ʬ����������,Zombies�еĳ��󷽷�
	 * @param attack:����ֵ
	 * */
	public abstract void attacked(int attack);
}
