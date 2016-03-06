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
	/**��Ϸ������*/
	public GameCotroller() {
	}
	
	private static GameCotroller cotroller=new GameCotroller();
	
	/**��ȡһ��GameCotroller��Ϸ����������*/
	public static GameCotroller getInstance(){
		return cotroller;
	}
	
	/**��Ϸ�Ƿ�ʼ��־*/
	public static boolean isStart;
	
	/**��Ϸ��ͼ,���ⲿ������ȡ*/
	private CCTMXTiledMap map;
	
	/**��ѡֲ�Ｏ��*/
	private List<PlantForShow> selectedPlants;
	
	/**��ս�м���*/
	private static List<FightLine> lines; 
	
	/**��ʬ�˶������յ㼯��*/
	private List<CGPoint> roadPoints;
	/**ֲ�ﰲ�ŵ㼯*/
	CGPoint[][] towers = new CGPoint[5][9];

	/**������CCProgressTimer����*/
	private CCProgressTimer progressTimer;
	/**����������ֵ*/
	private float progressValue=0;
	
	
	static{
		lines=new ArrayList<FightLine>();
		for(int i=0;i<5;i++){
			FightLine fightLine=new FightLine(i);
			lines.add(fightLine);
		}
	}
	
	/**
	 * ������Ϸ
	 * @param map---CCTMXTiledMap��ͼ����
	 * @param selectedPlants---��ѡֲ�Ｏ��
	 * */
	public void startGame(CCTMXTiledMap map,List<PlantForShow> selectedPlants){
		this.isStart=true;
		this.map=map;
		this.selectedPlants=selectedPlants;
//		System.out.println("selectedPlants��ѡֲ�Ｏ�ϴ�С:"+selectedPlants.size());
		loadMap();
		
		CCScheduler.sharedScheduler().schedule("addZombies",this,4,false);
		
		progress();
	}
	


	/**������ͼ*/
	private void loadMap() {
		/*������ͼ�Ͻ�ʬ��ʼ��ֹ�˶��㼯*/
		this.roadPoints=CommonUtils.getMapPoint(this.map,"road");
		/*����ֲ�ﰲ�ŵ㼯��*/
		for(int i=1;i<=5;i++){
			List<CGPoint> mapPoints=CommonUtils.getMapPoint(map,String.format("tower%02d",i));
			for(int j=0;j<mapPoints.size();j++){
				towers[i-1][j]=mapPoints.get(j);
			}
		}
		
		
	}

	/**��Ӵ�����˶����յ�Ľ�ʬ*/
	public void addZombies(float t){
		Random random=new Random();
		int lineNum=random.nextInt(5);/*����0~5([0,5])int���������*/

		/*����һ��������ʬ����*/
		PrimaryZombies primaryZombies=new PrimaryZombies(this.roadPoints.get(lineNum*2),this.roadPoints.get(lineNum*2+1));
		
		this.map.addChild(primaryZombies);
		
		lines.get(lineNum).addZombies(primaryZombies);
		
		this.progressValue+=5;
		this.progressTimer.setPercentage(progressValue);
	}
	
	/**���ؽ�����*/
	private void progress() {
		/*����������*/
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
		/*���ý�������λ��*/
		this.progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width-80,13);
		this.map.getParent().addChild(progressTimer);/*��ͼ������ӽ�����*/
		this.progressTimer.setScale(0.6f);
		
		/*���ȵ���ʽ--�����������ӵ���ʽ*/
		this.progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);/*���ý���������*/
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
	
	/**���ѡ��׼����ֲ��ֲ��*/
	private PlantForShow selectPlant;
	/**���ŵ�ֲ��*/
	private Plant installPlant;


	
	/**����Ϸ��ʼ������Ļ�ϵ�Touch�����¼�����*/
	public void handlerTouch(CGPoint point) {
		/**��ȡ�����ѡֲ������*/
		CCSprite selectedContainer=(CCSprite) map.getParent().getChildByTag(FightLayer.TAG_SELECTEDCONTAINER);
		
		/*����ҵ����ѡֲ������ʱ*/
		if(CGRect.containsPoint(selectedContainer.getBoundingBox(),point))
		{
			/*ȷ��selectPlant����Ϊ��*/
			if(this.selectPlant!=null){
				
				this.selectPlant.getPlantForShow().setOpacity(255);/*����͸����*/
				this.selectPlant=null;
			}
			
			System.out.println("handlerTouch----selectPlant="+selectPlant+",selectedPlants��ѡֲ������:"+selectedPlants.size());
			
			for(PlantForShow plant:this.selectedPlants){
				/*��ѡֲ�Ｏ����ÿ��PlantForShowԪ�ض�Ӧ�ľ���*/
				CGRect plantRect=plant.getPlantForShow().getBoundingBox();
				if(CGRect.containsPoint(plantRect, point)){
					this.selectPlant=plant;
					this.selectPlant.getPlantForShow().setOpacity(150);
					/*��ȡ���ѡ��׼����ֲ��ֲ��PlantForShow�����ID*/
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
		else/*����ҵ������ֲ��*/
		{
			if(this.selectPlant!=null){
				/*�к�*/
				int rowNum=(int) (point.x/46)-1;
				/*�к�*/
				int lineNum=(int) ((CCDirector.sharedDirector().getWinSize().height-point.y)/54)-1;
			
				if(rowNum>=0&&rowNum<=8&&lineNum>=0&&lineNum<=4){
					this.installPlant.setLine(lineNum);/*����ֲ����к�*/
					this.installPlant.setRow(rowNum);/*����ֲ����к�*/
					
					/*����ֲ��*/
					this.installPlant.setPosition(towers[lineNum][rowNum]);
				
					FightLine fightLine=lines.get(lineNum);
					
					/*�жϵ�ǰ���Ƿ��Ѿ������ֲ�� �������� �Ͳ����������*/
					if(!fightLine.containsRow(rowNum)){
						/*��ֲ���¼������ս����*/
						System.out.println("ֲ���ѱ���ӵ���ս����,��ս�����к�lineNum="+lineNum);
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
