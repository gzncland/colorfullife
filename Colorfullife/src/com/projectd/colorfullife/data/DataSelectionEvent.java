package com.projectd.colorfullife.data;

import com.projectd.colorfullife.data.DataEvent.EventResultType;
import com.projectd.colorfullife.game.GamePlayer;

public class DataSelectionEvent {
	public int spId;
	public String name;
	public String info;
	public String message;
	public EventResultType result = EventResultType.RESULT_OTHER;
	
	public int hpCost;
	public int cashCost;
	public int stopCount;
	public int creditCost;
	public int spEventId;
	
	public int hpGet;
	public int hpMaxGet;
	public int cashGet;
	public int rushCount;
	public int itemGet;
	public int creditGet;
	public int apGet;
	public int salaryGet;
	public boolean isSelectable(GamePlayer setPlayer) {
		if(hpCost > setPlayer.getHp()){
			return false;
		}
		if(cashCost * 100 > setPlayer.cash && cashCost != 0){
			return false;
		}
		if(creditCost > setPlayer.credit){
			return false;
		}
		switch (spEventId) {
		case 1:
			if(setPlayer.isMoreSl){
				return false;
			}
			break;
		case 2:
			if(setPlayer.investMoney > 0){
				return false;
			}
			break;
		case 3:
			if(setPlayer.investMoney > 0){
				return false;
			}
			break;
		case 4:
			if(setPlayer.lifeInsurance){
				return false;
			}
			break;
		case 5:
			if(setPlayer.autoInsurance){
				return false;
			}
			break;
		}
		return true;
	}
}
