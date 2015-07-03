package com.projectd.colorfullife;

import java.util.ArrayList;

import com.projectd.colorfullife.data.DataSelectionEvent;
import com.projectd.colorfullife.game.GamePlayer;

public class AIManager {

	public static int getSelection(GamePlayer setAiPlayer, ArrayList<DataSelectionEvent> selectionEvents){
		int rank[] = {0,0,0};
		for (int i = 0; i < selectionEvents.size(); i++) {
			//�Ƿ����ִ��ѡ��
			if(selectionEvents.get(i).isSelectable(setAiPlayer)){
				switch (selectionEvents.get(i).spEventId) {
				case 1:
					if(setAiPlayer.isMoreSl){
						break;
					}
					return 1;
				case 2:
				case 3:
					if(setAiPlayer.cash > 4500){
						return 1;
					}else if(setAiPlayer.cash > 2500){
						return 0;
					}
					return 2;
				case 4:
				case 5:
					if(setAiPlayer.cash > 2500){
						if(setAiPlayer.lifeInsurance){
							return 1;
						}else if (setAiPlayer.autoInsurance) {
							return 0;
						}
					}
					return 2;	
				case 6:
				case 7:
					if(SystemManager.OLD_ERA_DAYMAX - GameManager.dayCount > 5){
						if(setAiPlayer.cash > 2000){
							return 0;
						}else {
							return 2;
						}
					}
					return 1;
				}
				//���Լ�MAXHP
				if(selectionEvents.get(i).hpMaxGet > 0){
					rank[i] += selectionEvents.get(i).rushCount * 10;
				}
				//�гɾ�
				else if(selectionEvents.get(i).apGet > 0){
					rank[i] += selectionEvents.get(i).rushCount * 10;
				}
				//�ж���Ļغ�
				else if(selectionEvents.get(i).rushCount > 0){
					rank[i] += selectionEvents.get(i).rushCount * 5;
				//���Ի��ѧ��,��ѧ�ֲ�δ����������
				}else if(selectionEvents.get(i).creditGet > 0 && setAiPlayer.credit < SystemManager.CREDIT_LIMTED){
					rank[i] += selectionEvents.get(i).creditGet * 4;
				}
				//���Ի�ý�Ǯ
				else if(selectionEvents.get(i).cashGet > 0){
					rank[i] += selectionEvents.get(i).cashGet * 3;
				}
				//���Ի����Ʒ
//				else if(selectionEvents.get(i).itemGet > 0){
//					rank[i] += 8;
//				}
				//���Ի��HP
				else if(selectionEvents.get(i).hpGet > 0){
					if(selectionEvents.get(i).hpGet > 15){
						rank[i] += 10;
					}else{
						rank[i] += selectionEvents.get(i).hpGet;
					}
					if(setAiPlayer.getHp() < 3){
						rank[i] *= 2;
					}
				}
				if(GameManager.age != 2){
					//�����Ѵ�����Ǯ,�����½�
					if(selectionEvents.get(i).cashCost * 100 > setAiPlayer.cash / 2){
						rank[i] /= 2;
					}
				}
				//�����Ĵ���HP,�����½�
				if(selectionEvents.get(i).hpCost > setAiPlayer.getHp() / 2){
					rank[i] /= 2;
				}
			}else{
				//�޷�ִ�����ѡ��ĳ���
				rank[i] = -99;
			}
		}
		if(rank[0] > rank[1]){
			if(rank[0] > rank[2]){
				return 0;
			}else{
				return 2;
			}
		}else {
			if(rank[1] > rank[2]){
				return 1;
			}
		}
		return 2;
	}
}
