package com.projectd.colorfullife.data;

import java.util.ArrayList;

public class DataCharacter {
	
	
	/**����*/
	public String name = "";
	public ArrayList<String> oppomentGoodEvent = new ArrayList<String>();
	
	public ArrayList<String> oppomentBadEvent = new ArrayList<String>();
	
	public ArrayList<String> badEvent = new ArrayList<String>();
	
	public ArrayList<String> goodEvent = new ArrayList<String>();
	
	public ArrayList<String> turnStart = new ArrayList<String>();
	
	public ArrayList<String> turnEnd = new ArrayList<String>();

	public ArrayList<String> turnSkip = new ArrayList<String>();
	/**ͷ��ID*/
	public int faceid;
	/**���HP*/
	public int maxHp;
	/**��ʼ��Ǯ*/
	public int startCash;
	/**����ͼID*/
	public int characterid;
	
	/**���� ѧ��*/
	public boolean isStudyKing;
	/**���� ������*/
	public boolean isMuscleMan;
	
	public String info;
	
	public String note;
}
