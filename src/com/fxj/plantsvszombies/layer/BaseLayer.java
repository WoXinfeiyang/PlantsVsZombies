package com.fxj.plantsvszombies.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class BaseLayer extends CCLayer {
	
	/**��Ļ�ߴ��С*/
	protected CGSize winSize;

	/**ͼ�����*/
	public BaseLayer() {
		super();
		// TODO Auto-generated constructor stub
		this.winSize=CCDirector.sharedDirector().getWinSize();
	}
	
}
