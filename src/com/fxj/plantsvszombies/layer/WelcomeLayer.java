package com.fxj.plantsvszombies.layer;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.fxj.plantsvszombies.utils.CommonUtils;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

public class WelcomeLayer extends BaseLayer {
	
	/**点击开始进入游戏精灵对象*/
	private CCSprite enterGameSprite;

	public WelcomeLayer() {
		
		new AsyncTask<Void,Void,Void>() {
			
			/*在子线程中执行的后台耗时操作任务*/
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				SystemClock.sleep(4000);/*模拟后台耗时操作*/
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}
			
			/**子线程后台耗时操作任务完成后执行的操作*/
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				System.out.println("游戏加载准备工作完成!");
				enterGameSprite.setVisible(true);
				setIsTouchEnabled(true);
			}		
		}.execute();
		
		init();
	}
	
	/**初始化方法*/
	private void init() {
		this.setIsTouchEnabled(false);/*打开Touch触摸事件的监听*/
		loadLogo();
		
	}
	
	/**加载Logo*/
	private void loadLogo() {
		CCSprite logo=CCSprite.sprite("image/popcap_logo.png");
		logo.setAnchorPoint(0.5f,0.5f);/*修改瞄点*/
		logo.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(logo);
		
		CCHide ccHide=CCHide.action();/*隐藏动作*/
		CCDelayTime delayTime=CCDelayTime.action(1.0f);/*延时1秒钟*/
		CCSequence sequence=CCSequence.actions(delayTime, delayTime,ccHide,CCCallFunc.action(this,"loadWelcome"));		
		logo.runAction(sequence);
	}
	
	/**加载欢迎界面*/
	public void loadWelcome(){
		/**欢迎界面背景精灵对象*/
		CCSprite bkWelcome=CCSprite.sprite("image/welcome.jpg");
		bkWelcome.setAnchorPoint(0,0);
		this.addChild(bkWelcome);
		
		/**正在加载精灵对象*/
		CCSprite loadingSprite=CCSprite.sprite("image/loading/loading_01.png");
		loadingSprite.setPosition(winSize.width/2, 50);
		loadingSprite.setScale(1.5f);
		this.addChild(loadingSprite);
		
		CCAction loadingAnimation=CommonUtils.getAnimate("image/loading/loading_%02d.png",9,false);
		loadingSprite.runAction(loadingAnimation);
		
		enterGameSprite = CCSprite.sprite("image/loading/loading_start.png");
		enterGameSprite.setPosition(winSize.width/2, 50);
		enterGameSprite.setScale(1.5f);
		enterGameSprite.setVisible(false);/*设置可见性*/
		this.addChild(enterGameSprite);
		
	}
	
	/*当Touch触摸事件发生时回调该方法*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
//		System.out.println("屏幕被点击了!");
		/*将触摸点在Android系统下的坐标转换成cocos2d中的坐标*/
		CGPoint point=this.convertTouchToNodeSpace(event);
		/*点击开始进入游戏精灵对象所在矩形*/
		CGRect enterGameRect=this.enterGameSprite.getBoundingBox();
		if(CGRect.containsPoint(enterGameRect, point)){
//			System.out.println("点击开始选项被点击了!");
			CommonUtils.changeLayer(new MenuLayer());
		}
		
		return super.ccTouchesBegan(event);
	}

	
}
