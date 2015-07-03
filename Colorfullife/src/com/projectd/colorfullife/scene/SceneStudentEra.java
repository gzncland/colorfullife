package com.projectd.colorfullife.scene;

import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;

public class SceneStudentEra extends SceneGame {
	
	public SceneStudentEra() {
		super(0);
	}
	
	@Override
	public void update(float deltaTime) {	
		if(GameManager.dayCount == SystemManager.STUDY_ERA_DAYMAX){
			SystemManager.newScene = new SceneGraduate();
			return;
		}		
		super.update(deltaTime);
	}
}
