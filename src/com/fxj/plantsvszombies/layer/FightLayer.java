package com.fxj.plantsvszombies.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import com.fxj.plantsvszombies.bean.PlantForShow;
import com.fxj.plantsvszombies.bean.ZombiesForShow;
import com.fxj.plantsvszombies.engine.GameCotroller;
import com.fxj.plantsvszombies.utils.CommonUtils;

/**对战图层精灵对象*/
public class FightLayer extends BaseLayer {
	
	/**CCTMXTiledMap地图对象*/
	private CCTMXTiledMap map;
	
	/**僵尸在公路上安放的坐标点*/
	private ArrayList<CGPoint> zombilesLocationPoints;
	
	/*定义2个容器*/
	/**玩家已选植物的容器(chose)*/
	private CCSprite selectedContainer;
	/**玩家可选植物容器(choose)*/
	private CCSprite selectableContainer;
	/**玩家已选植物容器标记*/
	public static final int TAG_SELECTEDCONTAINER=10;
	
	public FightLayer() {
		super();
		init();
	}

	private void init() {
		loadMap();
		parserMap();
		loadAndShowZombies();
		moveMap();
	}
	





	/**加载地图*/
	private void loadMap() {
		
		map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		map.setAnchorPoint(0.5f,0.5f);
		/*获取地图内容大小尺寸*/
		CGSize mapContentSize=map.getContentSize();
		map.setPosition(mapContentSize.width/2,mapContentSize.height/2);
		this.addChild(this.map);
	}

	/**解析地图上的点*/
	private void parserMap() {
		this.zombilesLocationPoints=CommonUtils.getMapPoint(map,"zombies");
		
//		for(int i=0;i<this.zombilesLocationPoints.size();i++){
//			CGPoint point=this.zombilesLocationPoints.get(i);
//			System.out.println("僵尸在公路上安放的坐标点:("+point.x+","+point.y+")");			
//		}		
	}
	
	/**加载并显示僵尸*/
	private void loadAndShowZombies() {
		for(int i=0;i<this.zombilesLocationPoints.size();i++){
			CGPoint point=this.zombilesLocationPoints.get(i);
			/*创建展示用僵尸对象*/
			ZombiesForShow zombiesForShow=new ZombiesForShow();
			zombiesForShow.setPosition(point);
			/*将展示用僵尸对象添加到地图上*/
			this.map.addChild(zombiesForShow);
		}		
	}
	
	/**移动地图*/
	private void moveMap() {
		/*地图宽度与屏幕宽度差值(地图宽度>屏幕宽度),正值*/
		int dx=(int) (this.map.getContentSize().width-winSize.width);
		CCMoveBy moveBy=CCMoveBy.action(3,ccp(-dx,0));
		CCSequence sequence=CCSequence.actions(CCDelayTime.action(4),moveBy,CCDelayTime.action(2),CCCallFunc.action(this,"loadContainer"));
		this.map.runAction(sequence);
	}
	
	/**加载两个容器*/
	public void loadContainer(){
		/*设置玩家已选植物容器*/
		this.selectedContainer=CCSprite.sprite("image/fight/chose/fight_chose.png");
		this.selectedContainer.setAnchorPoint(0,1.0f);
		this.selectedContainer.setPosition(0,winSize.height);
		this.addChild(selectedContainer,0,TAG_SELECTEDCONTAINER);
		
		/*设置玩家可选植物容器*/
		this.selectableContainer=CCSprite.sprite("image/fight/chose/fight_choose.png");
		this.selectableContainer.setAnchorPoint(0,0);
		this.selectableContainer.setPosition(0,0);
		this.addChild(selectableContainer);
		
		loadPlantForShow();
		
		startGame = CCSprite.sprite("image/fight/chose/fight_start.png");
		startGame.setPosition(this.selectableContainer.getContentSize().width/2,30);
		this.selectableContainer.addChild(startGame);
			
	}

	
	/**PlantForShow展示用植物集合对象*/
	private List<PlantForShow> plantForShowList;
	
	/**"一起摇摆吧"开始游戏精灵对象*/
	private CCSprite startGame;
	
	/**加载展示用植物*/
	private void loadPlantForShow() {
		
		this.plantForShowList=new ArrayList<PlantForShow>();
		
		for(int i=1;i<=9;i++){
			PlantForShow plantForShow=new PlantForShow(i);
			
			/*获取展示植物背景精灵对象*/
			CCSprite bkPlantForShowSprite=plantForShow.getBkPlantForShow();
			bkPlantForShowSprite.setPosition(16 + ((i - 1) % 4) * 54,175 - ((i - 1) / 4) * 59);
			this.selectableContainer.addChild(bkPlantForShowSprite);
			
			/*获取展示植物精灵对象*/
			CCSprite plantForShowSprite=plantForShow.getPlantForShow();
			plantForShowSprite.setPosition(16 + ((i - 1) % 4) * 54,175 - ((i - 1) / 4) * 59);
			this.selectableContainer.addChild(plantForShowSprite);			
			
			this.plantForShowList.add(plantForShow);
		}
				
		setIsTouchEnabled(true);/*打开Touch触摸开关*/
	}

	/**已选植物List集合*/
	private List<PlantForShow> selectedPlants=new CopyOnWriteArrayList<PlantForShow>();
	
	/**在可选植物容器中选择植物时的加锁标志,true表示正在选择植物,处于加锁状态;false表示处于解锁转态,默认值为false,*/
	boolean isLock=false;
	/**解锁*/
	public void unLock(){
	this.isLock=false;	
	}
	/**在已选植物容器中移出植物时的加锁标志,true表示正在移出植物,处于加锁状态;false表示处于解锁状态*/
	boolean isDel=false;
	
	/*重写Touch事件发生时的回调方法*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		/*将Touch事件发生点在android系统下坐标转换成cocos2d系统下坐标*/
		CGPoint point=this.convertTouchToNodeSpace(event);
		
		/*当游戏开始了处理Touch触摸事件*/
		if(GameCotroller.isStart){
			GameCotroller.getInstance().handlerTouch(point);
			return super.ccTouchesBegan(event);
		}
		
		/**已选植物容器对应的矩形 */
		CGRect selectedBoundingBox=this.selectedContainer.getBoundingBox();
		/**可选植物容器对应的矩形*/
		CGRect selectableBoundingBox=this.selectableContainer.getBoundingBox();
		
		/*当Touch触摸点位于可选植物容器对应矩形范围内*/
		if(CGRect.containsPoint(selectableBoundingBox, point))
		{
			/*当选中可选植物容器中的"一起摇滚吧"开始游戏按钮*/
			if(CGRect.containsPoint(this.startGame.getBoundingBox(),point))
			{
				System.out.println("“一起摇滚吧”按钮被点击了!");
				readyGame();
			}
			else
			{
				/*当已选植物容器中数量少于5且处于解锁状态*/
				if(this.selectedPlants.size()<5&&!isLock){
					/*遍历展示植物集合中每个元素的矩形范围与每个Touch触摸点比较*/
					for(PlantForShow plant:this.plantForShowList){
						
						/*展示植物集合中每个元素矩形范围*/
					 	CGRect plantRect=plant.getPlantForShow().getBoundingBox();
						
					 	/*当Touch触摸点在展示植物集合中每个元素矩形范围内*/
						if(CGRect.containsPoint(plantRect,point)){
//							System.out.println("可选植物容器中当前植物被选中!");
							this.isLock=true;/*将加锁标志置为true*/
							CCMoveTo moveTo=CCMoveTo.action(0.5f,ccp(75+this.selectedPlants.size()*53,255));
							CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this,"unLock"));
							plant.getPlantForShow().runAction(sequence);
							this.selectedPlants.add(plant);
							
						}
						
					}
					
				}
			}
			
		}
		/*当Touch触摸点位于已选植物容器内*/
		else if(CGRect.containsPoint(selectedBoundingBox, point))
		{
			
//			for(int i=0;i<this.selectedPlants.size();i++){
			for(PlantForShow plant:this.selectedPlants){
			
//				PlantForShow plant=this.selectedPlants.get(i);
				CGRect plantRect=plant.getPlantForShow().getBoundingBox();
				if(CGRect.containsPoint(plantRect, point)){
					this.isDel=true;/*移出植物加锁状态*/
					CCMoveTo moveToSelectableContainer=CCMoveTo.action(0.5f,plant.getBkPlantForShow().getPosition());
					plant.getPlantForShow().runAction(moveToSelectableContainer);
					this.selectedPlants.remove(plant);
					continue;/*跳出本次循环*/
				}
				if(this.isDel){
					CCMoveBy moveBy=CCMoveBy.action(0.5f, ccp(-53, 0));
					plant.getPlantForShow().runAction(moveBy);
				}
			}			
		}
		

		
		return super.ccTouchesBegan(event);
	}
	
	/**正式开始游戏前的准备工作*/
	private void readyGame() {

		this.selectedContainer.setScale(0.65f);
		for(PlantForShow plant:this.selectedPlants){
			plant.getPlantForShow().setScale(0.65f);
			
			plant.getPlantForShow().setPosition(plant.getPlantForShow().getPosition().x*0.65f,
					plant.getPlantForShow().getPosition().y
					+ (CCDirector.sharedDirector().getWinSize().height - plant
					.getPlantForShow().getPosition().y)
					* 0.35f	
					);
			this.addChild(plant.getPlantForShow());
			
		}
		
		this.selectableContainer.removeSelf();/*移出可选植物容器*/
		
		
		int dx=(int) (this.map.getContentSize().width-winSize.width);
		CCMoveBy mapMoveBy=CCMoveBy.action(1,ccp(dx,0));
		CCSequence sequence=CCSequence.actions(mapMoveBy,CCCallFunc.action(this,"playTipsBeforGame"));
		this.map.runAction(sequence);
	}
	
	/**游戏开始之前的文字提示精灵对象*/
	private CCSprite tipsBeforGame;
	
	/**在尤溪开始之前播放文字提示*/
	public void playTipsBeforGame(){
		this.tipsBeforGame=CCSprite.sprite("image/fight/startready_01.png");
		this.tipsBeforGame.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(this.tipsBeforGame);
		
		CCAction animate=CommonUtils.getAnimate("image/fight/startready_%02d.png",3,false);
		CCSequence sequence=CCSequence.actions((CCAnimate)animate,CCCallFunc.action(this,"startGame"));
		this.tipsBeforGame.runAction(sequence);	
	}
	
	public void startGame(){
		this.tipsBeforGame.removeSelf();/*移出文字提示精灵对象*/
		GameCotroller gameCotroller=GameCotroller.getInstance();
		gameCotroller.startGame(map, selectedPlants);
	}
}
