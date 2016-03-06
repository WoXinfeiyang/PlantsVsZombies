package com.fxj.plantsvszombies.base;


/**植物抽象基类*/
public abstract class Plant extends BaseElement {
	
	/**生命值*/
	protected int life=100;
	/**行号*/
	protected int line;
	/**列号*/
	protected int row;
	
	
	
	public Plant(String filepath) {
		super(filepath);
		setScale(0.65f);
		setAnchorPoint(0.5f,0);
	}
	/**
	 * 植物被攻击
	 * @param value---攻击值
	 * */
	public void attacked(int value){
		this.life-=value;
		if(this.life<=0){
			this.destory();
		}
	}
	
	/**获取生命值*/
	public int getLife(){
		return this.life;
	}
	/**获取行号*/
	public int getLine() {
		return line;
	}
	
	/**设置行号*/
	public void setLine(int line) {
		this.line = line;
	}
	
	/**获取列号*/
	public int getRow() {
		return row;
	}

	/**设置列号*/
	public void setRow(int row) {
		this.row = row;
	}

	
}
