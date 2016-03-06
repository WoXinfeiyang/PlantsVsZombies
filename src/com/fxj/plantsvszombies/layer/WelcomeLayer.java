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
	
	/**�����ʼ������Ϸ�������*/
	private CCSprite enterGameSprite;

	public WelcomeLayer() {
		
		new AsyncTask<Void,Void,Void>() {
			
			/*�����߳���ִ�еĺ�̨��ʱ��������*/
			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				SystemClock.sleep(4000);/*ģ���̨��ʱ����*/
				return null;
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}
			
			/**���̺߳�̨��ʱ����������ɺ�ִ�еĲ���*/
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				System.out.println("��Ϸ����׼���������!");
				enterGameSprite.setVisible(true);
				setIsTouchEnabled(true);
			}		
		}.execute();
		
		init();
	}
	
	/**��ʼ������*/
	private void init() {
		this.setIsTouchEnabled(false);/*��Touch�����¼��ļ���*/
		loadLogo();
		
	}
	
	/**����Logo*/
	private void loadLogo() {
		CCSprite logo=CCSprite.sprite("image/popcap_logo.png");
		logo.setAnchorPoint(0.5f,0.5f);/*�޸����*/
		logo.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(logo);
		
		CCHide ccHide=CCHide.action();/*���ض���*/
		CCDelayTime delayTime=CCDelayTime.action(1.0f);/*��ʱ1����*/
		CCSequence sequence=CCSequence.actions(delayTime, delayTime,ccHide,CCCallFunc.action(this,"loadWelcome"));		
		logo.runAction(sequence);
	}
	
	/**���ػ�ӭ����*/
	public void loadWelcome(){
		/**��ӭ���汳���������*/
		CCSprite bkWelcome=CCSprite.sprite("image/welcome.jpg");
		bkWelcome.setAnchorPoint(0,0);
		this.addChild(bkWelcome);
		
		/**���ڼ��ؾ������*/
		CCSprite loadingSprite=CCSprite.sprite("image/loading/loading_01.png");
		loadingSprite.setPosition(winSize.width/2, 50);
		loadingSprite.setScale(1.5f);
		this.addChild(loadingSprite);
		
		CCAction loadingAnimation=CommonUtils.getAnimate("image/loading/loading_%02d.png",9,false);
		loadingSprite.runAction(loadingAnimation);
		
		enterGameSprite = CCSprite.sprite("image/loading/loading_start.png");
		enterGameSprite.setPosition(winSize.width/2, 50);
		enterGameSprite.setScale(1.5f);
		enterGameSprite.setVisible(false);/*���ÿɼ���*/
		this.addChild(enterGameSprite);
		
	}
	
	/*��Touch�����¼�����ʱ�ص��÷���*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
//		System.out.println("��Ļ�������!");
		/*����������Androidϵͳ�µ�����ת����cocos2d�е�����*/
		CGPoint point=this.convertTouchToNodeSpace(event);
		/*�����ʼ������Ϸ����������ھ���*/
		CGRect enterGameRect=this.enterGameSprite.getBoundingBox();
		if(CGRect.containsPoint(enterGameRect, point)){
//			System.out.println("�����ʼѡ������!");
			CommonUtils.changeLayer(new MenuLayer());
		}
		
		return super.ccTouchesBegan(event);
	}

	
}
