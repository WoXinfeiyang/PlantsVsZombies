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
		/*创建一个CCGLSurfaceView对象*/
		CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);	
		setContentView(surfaceView);
		
		this.director=CCDirector.sharedDirector();/*通过单例模式得到一个CCDirector*/	
		this.director.attachInView(surfaceView);/*开启线程*/
		this.director.setDisplayFPS(true);/*设置显示帧率*/
		this.director.setScreenSize(480,320);/*设置屏幕分辨率*/
		/*设置横屏显示*/
		this.director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);/**/
	
		CCScene scene=CCScene.node();/*创建一个场景*/
		/*在场景中运行一个图层*/
		scene.addChild(new FightLayer());
		
		this.director.runWithScene(scene);/*导演运行一个场景*/
		
	}



}
