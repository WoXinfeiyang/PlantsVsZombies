package com.fxj.plantsvszombies;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.fxj.plantsvszombies.layer.FightLayer;
import com.fxj.plantsvszombies.layer.WelcomeLayer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private CCDirector director;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*����һ��CCGLSurfaceView����*/
		CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);	
		setContentView(surfaceView);
		
		this.director=CCDirector.sharedDirector();/*ͨ������ģʽ�õ�һ��CCDirector*/	
		this.director.attachInView(surfaceView);/*�����߳�*/
		this.director.setDisplayFPS(true);/*������ʾ֡��*/
		this.director.setScreenSize(480,320);/*������Ļ�ֱ���*/
		/*���ú�����ʾ*/
		this.director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);/**/
	
		CCScene scene=CCScene.node();/*����һ������*/
		/*�ڳ���������һ��ͼ��*/
		scene.addChild(new FightLayer());
		
		this.director.runWithScene(scene);/*��������һ������*/
		
	}



}
