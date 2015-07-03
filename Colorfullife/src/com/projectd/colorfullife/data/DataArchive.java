package com.projectd.colorfullife.data;

public class DataArchive {
	/**…Ë∂®*/
	public boolean bgmIsMute;
	
	public boolean seIsMute;
	
	public boolean isShowDialog;
	
	public boolean isDisableDither;
	/**game*/
	public int activePlayerIndex;
	
	public int dayCount;
	
	public int age;
	
	public boolean nowRcDice ;
	
	public boolean nowSpeedUp;
	
	/**gameplayer*/
	public int[] playersCash = new int[4]; 
	
	public int[] playersStopCount = new int[4]; 
	
	public int[] playersRushCount = new int[4]; 
	
	public int[] playersCredit = new int[4]; 
	
	public int[] playersHp = new int[4]; 
	
	public int[] playersMaxHp = new int[4]; 
	
	public int[] playersSlowCount = new int[4]; 
	
	public int[] playersMapIndex = new int[4]; 
	
	public int[] achievementPoint = new int[4];
	
	public boolean[] isAi = new boolean[4];
	
	public boolean[] isMoreCareer = new boolean[4];
	
	public int[][] items = new int[4][6];
	
	public int[] characterId = new int[4];
	
	public int[]  careerId = new int[4];
	
	public int[]  salary = new int[4];
	
	public int[]  hplost = new int[4];
	
	public int[]  cashlost = new int[4];

	public int[]  invest = new int[4];
	
	public boolean[] isLI = new boolean[4];
	
	public boolean[] isAI = new boolean[4];
	
	public String saveTime;

	public boolean[] isFakeEnd = new boolean[4];

	public boolean[] isEnd = new boolean[4];
	
	public boolean[] isMarried = new boolean[4];
}
