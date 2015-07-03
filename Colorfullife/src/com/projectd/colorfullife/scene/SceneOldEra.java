package com.projectd.colorfullife.scene;

import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;

public class SceneOldEra extends SceneGame {

	public SceneOldEra() {
		super(2);
	}
	@Override
	public void update(float deltaTime) {	
		if(GameManager.dayCount == SystemManager.OLD_ERA_DAYMAX){
			SystemManager.newScene = new ScenceFinal();
			return;
		}		
		super.update(deltaTime);
	}

}
