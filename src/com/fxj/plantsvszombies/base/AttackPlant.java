package com.fxj.plantsvszombies.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**攻击类型植物基类*/
public abstract class AttackPlant extends Plant {

	/**弹夹,Bullet子弹集合类*/
	protected List<Bullet> bullets=new CopyOnWriteArrayList<Bullet>();
	
	public AttackPlant(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	/**生产攻击型子弹方法*/
	public abstract Bullet createBullet();

	/**获取弹夹*/
	public List<Bullet> getBullets(){
		return this.bullets;
	}
}
