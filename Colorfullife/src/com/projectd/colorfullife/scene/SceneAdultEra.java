package com.projectd.colorfullife.scene;

import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.game.GamePlayer;

public class SceneAdultEra extends SceneGame {
	public SceneAdultEra(){
		super(1);
	}
	@Override
	public void update(float deltaTime) {
		if(GameManager.dayCount == SystemManager.ADULT_ERA_DAYMAX){
			for (GamePlayer player : GameManager.players) {
				player.cash += player.investMoney;
			}
			SystemManager.newScene = new SceneRetire();
			return;
		}		
		super.update(deltaTime);
	}
}
