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
	/**��ս���к�*/
	private int num;

	
	public FightLine(int num) {
		super();
		this.num = num;
		
		CCScheduler.sharedScheduler().schedule("attackPlant",this,0.2f,false);
		
		CCScheduler.sharedScheduler().schedule("createBullet",this,0.2f,false);
		
		CCScheduler.sharedScheduler().schedule("attackZombies",this,0.2f,false);
	}
	
	/**��ʬ����ֲ�﷽��*/
	public void attackPlant(float t){
		if(this.zombiesList.size()>0&&this.plantsList.size()>0){/*��֤��ǰ���ϼ��н�ʬ����ֲ��*/
			for(Zombies zombies:this.zombiesList){
				CGPoint point=zombies.getPosition();
				int row=(int) (point.x/46-1);/*��ȡ��ʬ���ڵ���*/
				
				Plant plant=this.plantsList.get(row);
				if(plant!=null){
					zombies.attack(plant);
				}
				
			}
		}
	}

	/**������ֲ������ӵ�*/
	public void createBullet(float t){
		if(this.zombiesList.size()>0&&this.attackPlantsList.size()>0){
			
			for(AttackPlant attackPlant:this.attackPlantsList){
				attackPlant.createBullet();
			}
		}
	}
	/**��������ֲ�﹥����ʬ*/
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
	
	/**��ս���н�ʬ����*/
	private List<Zombies> zombiesList=new CopyOnWriteArrayList<Zombies>();
	/**��ս����ֲ�Ｏ��,Map������Integer��ʾֲ��������*/
	private Map<Integer,Plant> plantsList=new HashMap<Integer, Plant>();
	/**��������ֲ�Ｏ��*/
	private List<AttackPlant> attackPlantsList=new ArrayList<AttackPlant>();
	
	
	/**���ս������ӽ�ʬ*/
	public void addZombies(final Zombies zombies) {
		this.zombiesList.add(zombies);
		
		zombies.setDieListener(new DieListener() {
			
			@Override
			public void die() {
				zombiesList.remove(zombies);

			}
		});
		
	}
	
	/**���ս�������ֲ��*/
	public void addPlant(final Plant plant) {
		
		this.plantsList.put(plant.getRow(),plant);
		
		/*�������ֲ����һ���������͵�ֲ��,��ӵ���������ֲ��ļ�����*/
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
	
	/**�ж϶�ս����ĳ���Ƿ����ֲ��*/
	public boolean containsRow(int rowNum) {
		// TODO Auto-generated method stub
		return this.plantsList.containsKey(rowNum);
	}
}
