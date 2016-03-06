package com.fxj.plantsvszombies.base;

/**子弹基类*/
public abstract class Bullet extends Product {
	
	/**攻击力*/
	protected int attack=10;
	/**移动速度*/
	protected int speed=100;
	
	
	public Bullet(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void baseAction() {
		// TODO Auto-generated method stub
		
	}
	/**子弹移动方法*/
	public abstract void move();

	/**获取攻击力*/
	public int getAttack() {
		return attack;
	}

	/**设置攻击力*/
	public void setAttack(int attack) {
		this.attack = attack;
	}

	
	
	
}
