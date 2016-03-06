package com.fxj.plantsvszombies.layer;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

import com.fxj.plantsvszombies.utils.CommonUtils;

public class MenuLayer extends BaseLayer {

	public MenuLayer() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		/*菜单界面背景精灵对象*/
		CCSprite bkMenu=CCSprite.sprite("image/menu/main_menu_bg.jpg");
		bkMenu.setAnchorPoint(0.0f,0.0f);
		this.addChild(bkMenu);
		
		/*创建菜单项*/
		/*菜单项默认背景精灵对象*/
		CCSprite normalSprite=CCSprite.sprite("image/menu/start_adventure_default.png");
		/*菜单型按下背景精灵对象*/
		CCSprite pressedSprite=CCSprite.sprite("image/menu/start_adventure_press.png");		
		CCMenuItem menuItem=CCMenuItemSprite.item(normalSprite, pressedSprite,this,"menuItemClick");
		CCMenu menu=CCMenu.menu(menuItem);
		menu.setScale(0.5f);
		menu.setPosition(winSize.width/2-25,winSize.height/2-110);
		menu.setRotation(4.5f);
		this.addChild(menu);
	}

 	public void menuItemClick(Object object)
	{
		System.out.println("“开始冒险吧”菜单项被点击了!");
		CommonUtils.changeLayer(new FightLayer());
	}
	
}
