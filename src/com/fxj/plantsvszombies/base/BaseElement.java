package com.fxj.plantsvszombies.base;

import org.cocos2d.nodes.CCSprite;

/**��սԪ�س������*/
public abstract class BaseElement extends CCSprite {

	public interface DieListener
	{
		void die();
	}
	
	DieListener dieListener;
	/**��������������*/
	public void setDieListener(DieListener dieListener) {
		this.dieListener = dieListener;
	}
	
	public BaseElement(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	
	/**ԭ�ز����Ļ�������*/
	public abstract void baseAction();
	
	/**����*/
	public void destory(){
		if(this.dieListener!=null){
			this.dieListener.die();
		}
		this.removeSelf();
	}
	
 
	
}
