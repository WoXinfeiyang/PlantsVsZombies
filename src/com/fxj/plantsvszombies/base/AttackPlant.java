package com.fxj.plantsvszombies.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**��������ֲ�����*/
public abstract class AttackPlant extends Plant {

	/**����,Bullet�ӵ�������*/
	protected List<Bullet> bullets=new CopyOnWriteArrayList<Bullet>();
	
	public AttackPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	/**�����������ӵ�����*/
	public abstract Bullet createBullet();

	/**��ȡ����*/
	public List<Bullet> getBullets(){
		return this.bullets;
	}
}
