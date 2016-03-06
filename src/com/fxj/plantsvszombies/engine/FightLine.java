package com.fxj.plantsvszombies.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.types.CGPoint;

import com.fxj.plantsvszombies.base.AttackPlant;
import com.fxj.plantsvszombies.base.BaseElement.DieListener;
import com.fxj.plantsvszombies.base.Bullet;
import com.fxj.plantsvszombies.base.Plant;
import com.fxj.plantsvszombies.base.Zombies;
import com.fxj.plantsvszombies.bean.PrimaryZombies;

public class FightLine {
	/**对战行行号*/
	private int num;

	
	public FightLine(int num) {
		super();
		this.num = num;
		
		CCScheduler.sharedScheduler().schedule("attackPlant",this,0.2f,false);
		
		CCScheduler.sharedScheduler().schedule("createBullet",this,0.2f,false);
		
		CCScheduler.sharedScheduler().schedule("attackZombies",this,0.2f,false);
	}
	
	/**僵尸攻击植物方法*/
	public void attackPlant(float t){
		if(this.zombiesList.size()>0&&this.plantsList.size()>0){/*保证当前行上既有僵尸又有植物*/
			for(Zombies zombies:this.zombiesList){
				CGPoint point=zombies.getPosition();
				int row=(int) (point.x/46-1);/*获取僵尸所在的列*/
				
				Plant plant=this.plantsList.get(row);
				if(plant!=null){
					zombies.attack(plant);
				}
				
			}
		}
	}

	/**攻击型植物产生子弹*/
	public void createBullet(float t){
		if(this.zombiesList.size()>0&&this.attackPlantsList.size()>0){
			
			for(AttackPlant attackPlant:this.attackPlantsList){
				attackPlant.createBullet();
			}
		}
	}
	/**攻击类型植物攻击僵尸*/
	public void attackZombies(float t){
		if(this.zombiesList.size()>0&&this.attackPlantsList.size()>0){
			
			for(Zombies zombies:this.zombiesList){
				float x=zombies.getPosition().x;
				float left=x-20;
				float right=x+20;
				
				for(AttackPlant attackPlant:this.attackPlantsList){
					List<Bullet> bullets=attackPlant.getBullets();
					for(Bullet bullet:bullets){
						float bulletX=bullet.getPosition().x;
						if(bulletX>left&&bulletX<right){
							zombies.attacked(bullet.getAttack());
							
//							bullet.removeSelf();
							bullet.setVisible(false);
							bullet.setAttack(0);
						}
					}
				}
			}
			
		}

	}
	
	/**行战场中僵尸集合*/
	private List<Zombies> zombiesList=new CopyOnWriteArrayList<Zombies>();
	/**行战场中植物集合,Map集合中Integer表示植物所在列*/
	private Map<Integer,Plant> plantsList=new HashMap<Integer, Plant>();
	/**攻击类型植物集合*/
	private List<AttackPlant> attackPlantsList=new ArrayList<AttackPlant>();
	
	
	/**向对战行中添加僵尸*/
	public void addZombies(final Zombies zombies) {
		this.zombiesList.add(zombies);
		
		zombies.setDieListener(new DieListener() {
			
			@Override
			public void die() {
				zombiesList.remove(zombies);

			}
		});
		
	}
	
	/**向对战行中添加植物*/
	public void addPlant(final Plant plant) {
		
		this.plantsList.put(plant.getRow(),plant);
		
		/*如果发现植物是一个攻击类型的植物,添加到攻击类型植物的集合中*/
		if(plant instanceof AttackPlant){
			this.attackPlantsList.add((AttackPlant) plant);
		}
		
		plant.setDieListener(new DieListener() {
			
			@Override
			public void die() {
				plantsList.remove(plant.getRow());
				
				if(plant instanceof AttackPlant){
					attackPlantsList.remove((AttackPlant)plant);
				}				
			}
		});
		
	}
	
	/**判断对战行上某列是否包含植物*/
	public boolean containsRow(int rowNum) {
		// TODO Auto-generated method stub
		return this.plantsList.containsKey(rowNum);
	}
}
