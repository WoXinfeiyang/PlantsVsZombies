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

/**��սͼ�㾫�����*/
public class FightLayer extends BaseLayer {
	
	/**CCTMXTiledMap��ͼ����*/
	private CCTMXTiledMap map;
	
	/**��ʬ�ڹ�·�ϰ��ŵ������*/
	private ArrayList<CGPoint> zombilesLocationPoints;
	
	/*����2������*/
	/**�����ѡֲ�������(chose)*/
	private CCSprite selectedContainer;
	/**��ҿ�ѡֲ������(choose)*/
	private CCSprite selectableContainer;
	/**�����ѡֲ���������*/
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
	





	/**���ص�ͼ*/
	private void loadMap() {
		
		map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		map.setAnchorPoint(0.5f,0.5f);
		/*��ȡ��ͼ���ݴ�С�ߴ�*/
		CGSize mapContentSize=map.getContentSize();
		map.setPosition(mapContentSize.width/2,mapContentSize.height/2);
		this.addChild(this.map);
	}

	/**������ͼ�ϵĵ�*/
	private void parserMap() {
		this.zombilesLocationPoints=CommonUtils.getMapPoint(map,"zombies");
		
//		for(int i=0;i<this.zombilesLocationPoints.size();i++){
//			CGPoint point=this.zombilesLocationPoints.get(i);
//			System.out.println("��ʬ�ڹ�·�ϰ��ŵ������:("+point.x+","+point.y+")");			
//		}		
	}
	
	/**���ز���ʾ��ʬ*/
	private void loadAndShowZombies() {
		for(int i=0;i<this.zombilesLocationPoints.size();i++){
			CGPoint point=this.zombilesLocationPoints.get(i);
			/*����չʾ�ý�ʬ����*/
			ZombiesForShow zombiesForShow=new ZombiesForShow();
			zombiesForShow.setPosition(point);
			/*��չʾ�ý�ʬ������ӵ���ͼ��*/
			this.map.addChild(zombiesForShow);
		}		
	}
	
	/**�ƶ���ͼ*/
	private void moveMap() {
		/*��ͼ�������Ļ��Ȳ�ֵ(��ͼ���>��Ļ���),��ֵ*/
		int dx=(int) (this.map.getContentSize().width-winSize.width);
		CCMoveBy moveBy=CCMoveBy.action(3,ccp(-dx,0));
		CCSequence sequence=CCSequence.actions(CCDelayTime.action(4),moveBy,CCDelayTime.action(2),CCCallFunc.action(this,"loadContainer"));
		this.map.runAction(sequence);
	}
	
	/**������������*/
	public void loadContainer(){
		/*���������ѡֲ������*/
		this.selectedContainer=CCSprite.sprite("image/fight/chose/fight_chose.png");
		this.selectedContainer.setAnchorPoint(0,1.0f);
		this.selectedContainer.setPosition(0,winSize.height);
		this.addChild(selectedContainer,0,TAG_SELECTEDCONTAINER);
		
		/*������ҿ�ѡֲ������*/
		this.selectableContainer=CCSprite.sprite("image/fight/chose/fight_choose.png");
		this.selectableContainer.setAnchorPoint(0,0);
		this.selectableContainer.setPosition(0,0);
		this.addChild(selectableContainer);
		
		loadPlantForShow();
		
		startGame = CCSprite.sprite("image/fight/chose/fight_start.png");
		startGame.setPosition(this.selectableContainer.getContentSize().width/2,30);
		this.selectableContainer.addChild(startGame);
			
	}

	
	/**PlantForShowչʾ��ֲ�Ｏ�϶���*/
	private List<PlantForShow> plantForShowList;
	
	/**"һ��ҡ�ڰ�"��ʼ��Ϸ�������*/
	private CCSprite startGame;
	
	/**����չʾ��ֲ��*/
	private void loadPlantForShow() {
		
		this.plantForShowList=new ArrayList<PlantForShow>();
		
		for(int i=1;i<=9;i++){
			PlantForShow plantForShow=new PlantForShow(i);
			
			/*��ȡչʾֲ�ﱳ���������*/
			CCSprite bkPlantForShowSprite=plantForShow.getBkPlantForShow();
			bkPlantForShowSprite.setPosition(16 + ((i - 1) % 4) * 54,175 - ((i - 1) / 4) * 59);
			this.selectableContainer.addChild(bkPlantForShowSprite);
			
			/*��ȡչʾֲ�ﾫ�����*/
			CCSprite plantForShowSprite=plantForShow.getPlantForShow();
			plantForShowSprite.setPosition(16 + ((i - 1) % 4) * 54,175 - ((i - 1) / 4) * 59);
			this.selectableContainer.addChild(plantForShowSprite);			
			
			this.plantForShowList.add(plantForShow);
		}
				
		setIsTouchEnabled(true);/*��Touch��������*/
	}

	/**��ѡֲ��List����*/
	private List<PlantForShow> selectedPlants=new CopyOnWriteArrayList<PlantForShow>();
	
	/**�ڿ�ѡֲ��������ѡ��ֲ��ʱ�ļ�����־,true��ʾ����ѡ��ֲ��,���ڼ���״̬;false��ʾ���ڽ���ת̬,Ĭ��ֵΪfalse,*/
	boolean isLock=false;
	/**����*/
	public void unLock(){
	this.isLock=false;	
	}
	/**����ѡֲ���������Ƴ�ֲ��ʱ�ļ�����־,true��ʾ�����Ƴ�ֲ��,���ڼ���״̬;false��ʾ���ڽ���״̬*/
	boolean isDel=false;
	
	/*��дTouch�¼�����ʱ�Ļص�����*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		/*��Touch�¼���������androidϵͳ������ת����cocos2dϵͳ������*/
		CGPoint point=this.convertTouchToNodeSpace(event);
		
		/*����Ϸ��ʼ�˴���Touch�����¼�*/
		if(GameCotroller.isStart){
			GameCotroller.getInstance().handlerTouch(point);
			return super.ccTouchesBegan(event);
		}
		
		/**��ѡֲ��������Ӧ�ľ��� */
		CGRect selectedBoundingBox=this.selectedContainer.getBoundingBox();
		/**��ѡֲ��������Ӧ�ľ���*/
		CGRect selectableBoundingBox=this.selectableContainer.getBoundingBox();
		
		/*��Touch������λ�ڿ�ѡֲ��������Ӧ���η�Χ��*/
		if(CGRect.containsPoint(selectableBoundingBox, point))
		{
			/*��ѡ�п�ѡֲ�������е�"һ��ҡ����"��ʼ��Ϸ��ť*/
			if(CGRect.containsPoint(this.startGame.getBoundingBox(),point))
			{
				System.out.println("��һ��ҡ���ɡ���ť�������!");
				readyGame();
			}
			else
			{
				/*����ѡֲ����������������5�Ҵ��ڽ���״̬*/
				if(this.selectedPlants.size()<5&&!isLock){
					/*����չʾֲ�Ｏ����ÿ��Ԫ�صľ��η�Χ��ÿ��Touch������Ƚ�*/
					for(PlantForShow plant:this.plantForShowList){
						
						/*չʾֲ�Ｏ����ÿ��Ԫ�ؾ��η�Χ*/
					 	CGRect plantRect=plant.getPlantForShow().getBoundingBox();
						
					 	/*��Touch��������չʾֲ�Ｏ����ÿ��Ԫ�ؾ��η�Χ��*/
						if(CGRect.containsPoint(plantRect,point)){
//							System.out.println("��ѡֲ�������е�ǰֲ�ﱻѡ��!");
							this.isLock=true;/*��������־��Ϊtrue*/
							CCMoveTo moveTo=CCMoveTo.action(0.5f,ccp(75+this.selectedPlants.size()*53,255));
							CCSequence sequence=CCSequence.actions(moveTo,CCCallFunc.action(this,"unLock"));
							plant.getPlantForShow().runAction(sequence);
							this.selectedPlants.add(plant);
							
						}
						
					}
					
				}
			}
			
		}
		/*��Touch������λ����ѡֲ��������*/
		else if(CGRect.containsPoint(selectedBoundingBox, point))
		{
			
//			for(int i=0;i<this.selectedPlants.size();i++){
			for(PlantForShow plant:this.selectedPlants){
			
//				PlantForShow plant=this.selectedPlants.get(i);
				CGRect plantRect=plant.getPlantForShow().getBoundingBox();
				if(CGRect.containsPoint(plantRect, point)){
					this.isDel=true;/*�Ƴ�ֲ�����״̬*/
					CCMoveTo moveToSelectableContainer=CCMoveTo.action(0.5f,plant.getBkPlantForShow().getPosition());
					plant.getPlantForShow().runAction(moveToSelectableContainer);
					this.selectedPlants.remove(plant);
					continue;/*��������ѭ��*/
				}
				if(this.isDel){
					CCMoveBy moveBy=CCMoveBy.action(0.5f, ccp(-53, 0));
					plant.getPlantForShow().runAction(moveBy);
				}
			}			
		}
		

		
		return super.ccTouchesBegan(event);
	}
	
	/**��ʽ��ʼ��Ϸǰ��׼������*/
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
		
		this.selectableContainer.removeSelf();/*�Ƴ���ѡֲ������*/
		
		
		int dx=(int) (this.map.getContentSize().width-winSize.width);
		CCMoveBy mapMoveBy=CCMoveBy.action(1,ccp(dx,0));
		CCSequence sequence=CCSequence.actions(mapMoveBy,CCCallFunc.action(this,"playTipsBeforGame"));
		this.map.runAction(sequence);
	}
	
	/**��Ϸ��ʼ֮ǰ��������ʾ�������*/
	private CCSprite tipsBeforGame;
	
	/**����Ϫ��ʼ֮ǰ����������ʾ*/
	public void playTipsBeforGame(){
		this.tipsBeforGame=CCSprite.sprite("image/fight/startready_01.png");
		this.tipsBeforGame.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(this.tipsBeforGame);
		
		CCAction animate=CommonUtils.getAnimate("image/fight/startready_%02d.png",3,false);
		CCSequence sequence=CCSequence.actions((CCAnimate)animate,CCCallFunc.action(this,"startGame"));
		this.tipsBeforGame.runAction(sequence);	
	}
	
	public void startGame(){
		this.tipsBeforGame.removeSelf();/*�Ƴ�������ʾ�������*/
		GameCotroller gameCotroller=GameCotroller.getInstance();
		gameCotroller.startGame(map, selectedPlants);
	}
}
