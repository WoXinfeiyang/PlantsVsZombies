package com.fxj.plantsvszombies.base;


/**ֲ��������*/
public abstract class Plant extends BaseElement {
	
	/**����ֵ*/
	protected int life=100;
	/**�к�*/
	protected int line;
	/**�к�*/
	protected int row;
	
	
	
	public Plant(String filepath) {
		super(filepath);
		setScale(0.65f);
		setAnchorPoint(0.5f,0);
	}
	/**
	 * ֲ�ﱻ����
	 * @param value---����ֵ
	 * */
	public void attacked(int value){
		this.life-=value;
		if(this.life<=0){
			this.destory();
		}
	}
	
	/**��ȡ����ֵ*/
	public int getLife(){
		return this.life;
	}
	/**��ȡ�к�*/
	public int getLine() {
		return line;
	}
	
	/**�����к�*/
	public void setLine(int line) {
		this.line = line;
	}
	
	/**��ȡ�к�*/
	public int getRow() {
		return row;
	}

	/**�����к�*/
	public void setRow(int row) {
		this.row = row;
	}

	
}
