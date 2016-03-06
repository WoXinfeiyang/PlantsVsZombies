package com.fxj.plantsvszombies.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.fxj.plantsvszombies.base.Plant;
import com.fxj.plantsvszombies.bean.Nut;
import com.fxj.plantsvszombies.bean.PeasePlant;
import com.fxj.plantsvszombies.bean.PlantForShow;
import com.fxj.plantsvszombies.bean.PrimaryZombies;
import com.fxj.plantsvszombies.layer.FightLayer;
import com.fxj.plantsvszombies.utils.CommonUtils;


public class GameCotroller {
	/**游戏控制器*/
	public GameCotroller() {
	}
	
	private static GameCotroller cotroller=new GameCotroller();
	
	/**获取一个GameCotroller游戏控制器单例*/
	public static GameCotroller getInstance(){
		return cotroller;
	}
	
	/**游戏是否开始标志*/
	public static boolean isStart;
	
	/**游戏地图,从外部函数获取*/
	private CCTMXTiledMap map;
	
	/**已选植物集合*/
	private List<PlantForShow> selectedPlants;
	
	/**对战行集合*/
	private static List<FightLine> lines; 
	
	/**僵尸运动的起终点集合*/
	private List<CGPoint> roadPoints;
	/**植物安放点集*/
	CGPoint[][] towers = new CGPoint[5][9];

	/**进度条CCProgressTimer对象*/
	private CCProgressTimer progressTimer;
	/**进度条进度值*/
	private float progressValue=0;
	
	
	static{
		lines=new ArrayList<FightLine>();
		for(int i=0;i<5;i++){
			FightLine fightLine=new FightLine(i);
			lines.add(fightLine);
		}
	}
	
	/**
	 * 启动游戏
	 * @param map---CCTMXTiledMap地图对象
	 * @param selectedPlants---已选植物集合
	 * */
	public void startGame(CCTMXTiledMap map,List<PlantForShow> selectedPlants){
		this.isStart=true;
		this.map=map;
		this.selectedPlants=selectedPlants;
//		System.out.println("selectedPlants已选植物集合大小:"+selectedPlants.size());
		loadMap();
		
		CCScheduler.sharedScheduler().schedule("addZombies",this,4,false);
		
		progress();
	}
	


	/**解析地图*/
	private void loadMap() {
		/*解析地图上僵尸开始终止运动点集*/
		this.roadPoints=CommonUtils.getMapPoint(this.map,"road");
		/*解析植物安放点集合*/
		for(int i=1;i<=5;i++){
			List<CGPoint> mapPoints=CommonUtils.getMapPoint(map,String.format("tower%02d",i));
			for(int j=0;j<mapPoints.size();j++){
				towers[i-1][j]=mapPoints.get(j);
			}
		}
		
		
	}

	/**添加从起点运动到终点的僵尸*/
	public void addZombies(float t){
		Random random=new Random();
		int lineNum=random.nextInt(5);/*产生0~5([0,5])int类型随机数*/

		/*产生一个初级僵尸对象*/
		PrimaryZombies primaryZombies=new PrimaryZombies(this.roadPoints.get(lineNum*2),this.roadPoints.get(lineNum*2+1));
		
		this.map.addChild(primaryZombies);
		
		lines.get(lineNum).addZombies(primaryZombies);
		
		this.progressValue+=5;
		this.progressTimer.setPercentage(progressValue);
	}
	
	/**加载进度条*/
	private void progress() {
		/*创建进度条*/
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
		/*设置进度条的位置*/
		this.progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width-80,13);
		this.map.getParent().addChild(progressTimer);/*向图层中添加进度条*/
		this.progressTimer.setScale(0.6f);
		
		/*进度的样式--从右往左增加的形式*/
		this.progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);/*设置进度条类型*/
		this.progressTimer.setPercentage(progressValue);

		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		map.getParent().addChild(sprite);
		sprite.setScale(0.6f);
		CCSprite name = CCSprite.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
		map.getParent().addChild(name);
		name.setScale(0.6f);
	}
	
	/**玩家选择准备种植的植物*/
	private PlantForShow selectPlant;
	/**安放的植物*/
	private Plant installPlant;


	
	/**当游戏开始后处理屏幕上的Touch触摸事件方法*/
	public void handlerTouch(CGPoint point) {
		/**获取玩家已选植物容器*/
		CCSprite selectedContainer=(CCSprite) map.getParent().getChildByTag(FightLayer.TAG_SELECTEDCONTAINER);
		
		/*当玩家点击已选植物容器时*/
		if(CGRect.containsPoint(selectedContainer.getBoundingBox(),point))
		{
			/*确保selectPlant对象为空*/
			if(this.selectPlant!=null){
				
				this.selectPlant.getPlantForShow().setOpacity(255);/*设置透明度*/
				this.selectPlant=null;
			}
			
			System.out.println("handlerTouch----selectPlant="+selectPlant+",selectedPlants已选植物数量:"+selectedPlants.size());
			
			for(PlantForShow plant:this.selectedPlants){
				/*已选植物集合中每个PlantForShow元素对应的矩形*/
				CGRect plantRect=plant.getPlantForShow().getBoundingBox();
				if(CGRect.containsPoint(plantRect, point)){
					this.selectPlant=plant;
					this.selectPlant.getPlantForShow().setOpacity(150);
					/*获取玩家选择准备种植的植物PlantForShow对象的ID*/
					int id=this.selectPlant.getId();
					
					switch(id)
					{
					case 1:
						this.installPlant=new PeasePlant();
						break;
					case 4:
						this.installPlant=new Nut();
					default:
						break;
					}
				}
			}
			
		}
		else/*当玩家点击安放植物*/
		{
			if(this.selectPlant!=null){
				/*列号*/
				int rowNum=(int) (point.x/46)-1;
				/*行号*/
				int lineNum=(int) ((CCDirector.sharedDirector().getWinSize().height-point.y)/54)-1;
			
				if(rowNum>=0&&rowNum<=8&&lineNum>=0&&lineNum<=4){
					this.installPlant.setLine(lineNum);/*设置植物的行号*/
					this.installPlant.setRow(rowNum);/*设置植物的列号*/
					
					/*安放植物*/
					this.installPlant.setPosition(towers[lineNum][rowNum]);
				
					FightLine fightLine=lines.get(lineNum);
					
					/*判断当前列是否已经添加了植物 如果添加了 就不能再添加了*/
					if(!fightLine.containsRow(rowNum)){
						/*把植物记录到了行战场中*/
						System.out.println("植物已被添加到行战场中,行战场的行号lineNum="+lineNum);
						fightLine.addPlant(this.installPlant);
						this.map.addChild(installPlant);
					}	
				}
				this.installPlant=null;
				this.selectPlant.getPlantForShow().setOpacity(255);
				this.selectPlant=null;
			}
		}
		
	}
	
}
