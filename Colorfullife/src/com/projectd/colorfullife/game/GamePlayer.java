package com.projectd.colorfullife.game;

import java.util.ArrayList;

import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.data.DataCharacter;

public class GamePlayer {
	public boolean isMarried;

	public  boolean isFakeEnd;

	public  boolean isEnd;

	public int characterIndex;
	
	public DataCharacter characterData;
	/**额外的行动次数*/
	public int rushCount;
	/**学分*/
	public int credit;
	/**就业协会的多选*/
	public boolean isMoreSl;
	/**成就点数*/
	public int achievementPoint;
	/**体力*/
	private int hp = 10;
	/**最大体力*/
	public int maxHp = 10;
	/**当前走到的坐标*/
	public int mapIndex;	
	/**对手玩家*/
	public ArrayList<GamePlayer> opponents = new ArrayList<GamePlayer>();	
	/**职业编号*/
	public int careerId = -1;
	/**工资*/
	public int salary = 2000;
	/**损失的hp*/
	public int hpLost = 0;
	/**损失的钱*/
	public int cashLost = 0;
	/**现金*/
	public int cash;
	/**投资的钱*/
	public int investMoney = 0;
	/**养老保险*/
	public boolean lifeInsurance;
	/**车险*/
	public boolean autoInsurance;
	
	public boolean isAi;
	
	public int stopCount;
	
	public int[] items = new int[6];
	
	public int slowCount;

	public GamePlayer(int setCharacterDataIndex) {
		characterIndex = setCharacterDataIndex;
		characterData = DataManager.characters.get(setCharacterDataIndex);
		maxHp = characterData.maxHp;
		hp = characterData.maxHp;
		cash = characterData.startCash;
	}
	/**刷新职业效果*/
	public void careerRefresh() {
		if(GameManager.age == 1){
			if(careerId == 1){
				if(hpLost > 0){
					cash += hpLost * 100;
					GameManager.messages.add("通过职业技能获得了现金" + hpLost * 100 + "元");
					hpLost = 0;
				}
			}else if (careerId == 6) {
				if(cashLost > 0){
					int apCount = 0;
					while(cashLost >= 1000){
						achievementPoint += 1;
						cashLost -= 1000;
						apCount += 1;
					}
					if(apCount > 0){
						GameManager.messages.add("通过职业技能获得了成就点数" + apCount);
						apCount = 0;
					}
					
				}			
			}
		}
	}


	
	public int getHp() {
		return hp;
	}
	public void setHp(int setHp) {
		if(careerId == 1){
			hp = maxHp;
			return;
		}
		hp = setHp;
		if(hp <= 0){
			hp = 0;
		}else if (hp > maxHp) {
			hp = maxHp;
		}
	}
}
