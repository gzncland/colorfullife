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
	/**������ж�����*/
	public int rushCount;
	/**ѧ��*/
	public int credit;
	/**��ҵЭ��Ķ�ѡ*/
	public boolean isMoreSl;
	/**�ɾ͵���*/
	public int achievementPoint;
	/**����*/
	private int hp = 10;
	/**�������*/
	public int maxHp = 10;
	/**��ǰ�ߵ�������*/
	public int mapIndex;	
	/**�������*/
	public ArrayList<GamePlayer> opponents = new ArrayList<GamePlayer>();	
	/**ְҵ���*/
	public int careerId = -1;
	/**����*/
	public int salary = 2000;
	/**��ʧ��hp*/
	public int hpLost = 0;
	/**��ʧ��Ǯ*/
	public int cashLost = 0;
	/**�ֽ�*/
	public int cash;
	/**Ͷ�ʵ�Ǯ*/
	public int investMoney = 0;
	/**���ϱ���*/
	public boolean lifeInsurance;
	/**����*/
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
	/**ˢ��ְҵЧ��*/
	public void careerRefresh() {
		if(GameManager.age == 1){
			if(careerId == 1){
				if(hpLost > 0){
					cash += hpLost * 100;
					GameManager.messages.add("ͨ��ְҵ���ܻ�����ֽ�" + hpLost * 100 + "Ԫ");
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
						GameManager.messages.add("ͨ��ְҵ���ܻ���˳ɾ͵���" + apCount);
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
