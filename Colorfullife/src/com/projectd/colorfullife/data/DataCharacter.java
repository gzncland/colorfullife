package com.projectd.colorfullife.data;

import java.util.ArrayList;

public class DataCharacter {
	
	
	/**名称*/
	public String name = "";
	public ArrayList<String> oppomentGoodEvent = new ArrayList<String>();
	
	public ArrayList<String> oppomentBadEvent = new ArrayList<String>();
	
	public ArrayList<String> badEvent = new ArrayList<String>();
	
	public ArrayList<String> goodEvent = new ArrayList<String>();
	
	public ArrayList<String> turnStart = new ArrayList<String>();
	
	public ArrayList<String> turnEnd = new ArrayList<String>();

	public ArrayList<String> turnSkip = new ArrayList<String>();
	/**头像ID*/
	public int faceid;
	/**最大HP*/
	public int maxHp;
	/**初始金钱*/
	public int startCash;
	/**行走图ID*/
	public int characterid;
	
	/**特性 学霸*/
	public boolean isStudyKing;
	/**特性 肌肉男*/
	public boolean isMuscleMan;
	
	public String info;
	
	public String note;
}
