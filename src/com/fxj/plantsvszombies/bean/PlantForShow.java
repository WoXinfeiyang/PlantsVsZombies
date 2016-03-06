package com.fxj.plantsvszombies.bean;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.nodes.CCSprite;

public class PlantForShow {
	/**使用Map模拟一个数据库*/
	static Map<Integer,HashMap<String,String>> db;
	
	static 
	{
		db=new HashMap<Integer, HashMap<String,String>>();
		String format="image/fight/chose/choose_default%02d.png";
		for(int i=1;i<=9;i++){
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("path",String.format(format, i));
			map.put("sunValue",50+"");
			db.put(i,map);
		}
	}
	
	
	/*==========================*/
	/**展示植物ID*/
	int id;
	/**展示植物(前景)*/
	CCSprite plantForShow;
	/**展示植物背景*/
	CCSprite bkPlantForShow;
	
	/**展示用植物*/
	public PlantForShow(int id) {
		this.id = id;
		HashMap<String,String> hashMap=db.get(this.id);
//		System.out.println("数据库大小:"+db.size());
		String path=hashMap.get("path");
		
		this.plantForShow=CCSprite.sprite(path);
		this.plantForShow.setAnchorPoint(0,0);
		
		this.bkPlantForShow=CCSprite.sprite(path);
		this.bkPlantForShow.setOpacity(150);/*设置半透明*/
		this.bkPlantForShow.setAnchorPoint(0,0);			
	}
	
	/**获取展示植物ID*/
	public int getId() {
		return id;
	}
	
	/**获取展示植物*/
	public CCSprite getPlantForShow() {
		return plantForShow;
	}
	
	/**获取展示植物背景*/
	public CCSprite getBkPlantForShow() {
		return bkPlantForShow;
	}
	
}
