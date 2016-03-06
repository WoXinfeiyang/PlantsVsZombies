package com.fxj.plantsvszombies.base;

import org.cocos2d.nodes.CCSprite;

/**对战元素抽象基类*/
public abstract class BaseElement extends CCSprite {

	public interface DieListener
	{
		void die();
	}
	
	DieListener dieListener;
	/**设置死亡监听器*/
	public void setDieListener(DieListener dieListener) {
		this.dieListener = dieListener;
	}
	
	public BaseElement(String filepath) {
		super(filepath);
		// TODO Auto-generated constructor stub
	}
	
	/**原地不动的基本动作*/
	public abstract void baseAction();
	
	/**销毁*/
	public void destory(){
		if(this.dieListener!=null){
			this.dieListener.die();
		}
		this.removeSelf();
	}
	
 
	
}
