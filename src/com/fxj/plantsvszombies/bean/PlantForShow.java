package com.fxj.plantsvszombies.bean;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.nodes.CCSprite;

public class PlantForShow {
	/**ʹ��Mapģ��һ�����ݿ�*/
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
	/**չʾֲ��ID*/
	int id;
	/**չʾֲ��(ǰ��)*/
	CCSprite plantForShow;
	/**չʾֲ�ﱳ��*/
	CCSprite bkPlantForShow;
	
	/**չʾ��ֲ��*/
	public PlantForShow(int id) {
		this.id = id;
		HashMap<String,String> hashMap=db.get(this.id);
//		System.out.println("���ݿ��С:"+db.size());
		String path=hashMap.get("path");
		
		this.plantForShow=CCSprite.sprite(path);
		this.plantForShow.setAnchorPoint(0,0);
		
		this.bkPlantForShow=CCSprite.sprite(path);
		this.bkPlantForShow.setOpacity(150);/*���ð�͸��*/
		this.bkPlantForShow.setAnchorPoint(0,0);			
	}
	
	/**��ȡչʾֲ��ID*/
	public int getId() {
		return id;
	}
	
	/**��ȡչʾֲ��*/
	public CCSprite getPlantForShow() {
		return plantForShow;
	}
	
	/**��ȡչʾֲ�ﱳ��*/
	public CCSprite getBkPlantForShow() {
		return bkPlantForShow;
	}
	
}
