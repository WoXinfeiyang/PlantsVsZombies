package com.fxj.plantsvszombies.base;

/**�ӵ�����*/
public abstract class Bullet extends Product {
	
	/**������*/
	protected int attack=10;
	/**�ƶ��ٶ�*/
	protected int speed=100;
	
	
	public Bullet(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void baseAction() {
		// TODO Auto-generated method stub
		
	}
	/**�ӵ��ƶ�����*/
	public abstract void move();

	/**��ȡ������*/
	public int getAttack() {
		return attack;
	}

	/**���ù�����*/
	public void setAttack(int attack) {
		this.attack = attack;
	}

	
	
	
}
