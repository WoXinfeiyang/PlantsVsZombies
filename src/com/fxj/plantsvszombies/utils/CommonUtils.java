package com.fxj.plantsvszombies.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;

/**常用工具类*/
public class CommonUtils {
	
	/**
	 * 切换图层,本质实际上是切换运行一个新的场景
	 * @param newLayer---一个新的图层
	 * */
	public static void changeLayer(CCLayer newLayer){
		CCScene scene=CCScene.node();/*创建一个场景*/
		scene.addChild(newLayer);
		CCFlipXTransition transition=CCFlipXTransition.transition(2,scene,0);
		CCDirector.sharedDirector().replaceScene(transition);		
	}
	
	/**创建序列帧动画
	 * @param formatePath---格式化路径
	 * @param num---序列帧数量
	 * @param isForerver---是否永不停止的循环
	 * @return
	 * */
	public static CCAction getAnimate(String formatePath,int num,boolean isForerver)
	{
		/**精灵帧集合*/
		ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
		
		/*创建精灵帧并向精灵帧集合中添加*/
		for(int i=1;i<=num;i++){
			CCSpriteFrame  spriteFrame=CCSprite.sprite(String.format(formatePath,i)).displayedFrame();
			frames.add(spriteFrame);
		}
		
		CCAnimation anim=CCAnimation.animation("",0.2F, frames);
		
		if(isForerver){
			CCAnimate animate=CCAnimate.action(anim);
			CCRepeatForever repeatForever=CCRepeatForever.action(animate);
			return repeatForever;
		}else{
			CCAnimate animate=CCAnimate.action(anim,false);
			return animate;
		}
	}
	
	/**
	 * 解析并获取地图上的点集
	 * */
	public static ArrayList<CGPoint> getMapPoint(CCTMXTiledMap map,String name)
	{
		ArrayList<CGPoint> points=new ArrayList<CGPoint>();
		CCTMXObjectGroup objectGroup=map.objectGroupNamed(name);
		ArrayList<HashMap<String,String>> objects=objectGroup.objects;
		
		for(HashMap<String,String> object:objects){
			int x=Integer.parseInt(object.get("x"));
			int y=Integer.parseInt(object.get("y"));
			CGPoint point=CCNode.ccp(x, y);
			points.add(point);
		}		
		return points;
	}
}
