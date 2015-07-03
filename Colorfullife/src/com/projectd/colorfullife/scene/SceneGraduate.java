package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.colorfullife.view.BgAnimation;
import com.projectd.colorfullife.view.WindowJobSelect;
import com.projectd.colorfullife.view.WindowScoreBoardGraduate;
import com.projectd.colorfullife.view.game.WindowMessage;
import com.projectd.framework.SceneBase;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class SceneGraduate extends SceneBase{
	
	WindowScoreBoardGraduate scoreBoardWindow;
	BgAnimation ps;
	int count;
	private SpriteButton btnConfirm;
	WindowJobSelect jobSelectWindow;
	WindowMessage aiJobSelectWindow;
	public SceneGraduate() {
		super();
		ps = new BgAnimation(1);
		scoreBoardWindow = new WindowScoreBoardGraduate();
		aiJobSelectWindow = new WindowMessage();
		aiJobSelectWindow.isOpening = false;
		scoreBoardWindow.open();
		jobSelectWindow = new WindowJobSelect();
		//aiJobSelectWindow.isOpening = false;
		btnConfirm = new SpriteButton(
				CacheManager.getTextureById(R.drawable.ui_game),
				608,800,144,80
			);
		btnConfirm.active = false;
		btnConfirm.setLocation(704, 512);
	}
	
	@Override
	public void onDrawFrame() {
		ps.onDrawFrame();
		scoreBoardWindow.onDrawFrame();
		jobSelectWindow.onDrawFrame();
		aiJobSelectWindow.onDrawFrame();
		SpriteBatch.draw(btnConfirm);
	}

	@Override
	public void update(float deltaTime) {
		if(jobSelectWindow.selectIndex != -1){
			GameManager.getActivePlayer().careerId = jobSelectWindow.careerId[jobSelectWindow.selectIndex];
			jobSelectWindow.selectIndex = -1;
		}
		
		aiJobSelectWindow.update(deltaTime);
		ps.update(deltaTime);
//		if (count > 301) {
//			if(scoreBoardWindow.bg.getX() < -1024){
//				scoreBoardWindow.isOpening = false;
//			}else {
//				scoreBoardWindow.offset(-112, 0);
//			}
//		}else if(count > 0 && scoreBoardWindow.bg.getX() > 128){
//			scoreBoardWindow.offset(-112, 0);
//		}
		
		if(aiJobSelectWindow.isOpening){
			return;
		}
		if(jobSelectWindow.isOpening){
			return;
		}
		if(GameManager.messages.size() > 0){
			aiJobSelectWindow.open(GameManager.messages.remove(0));
		}
		
		
//		if(count > 329){
//			count += 1;
//		}else
			if(btnConfirm.active == false){
			count += 1;
			if(btnConfirm.getY() < 512){
				btnConfirm.offset(0, 8);
			}
		}else {
			if(btnConfirm.getY() > 432){
				btnConfirm.offset(0, -8);
			}
			
		}
		
		switch (count){
			case 60:
				scoreBoardWindow.refresh(0);
				break;
			case 90:
				scoreBoardWindow.refresh(1);
				break;
			case 120:
				scoreBoardWindow.refresh(2);
				break;
			case 150:
				scoreBoardWindow.refresh(3);
				break;
				
			case 200:
				scoreBoardWindow.refreshGradute(0);
				break;
			case 210:
				scoreBoardWindow.refreshGradute(1);
				break;
			case 220:
				scoreBoardWindow.refreshGradute(2);
				break;
			case 230:
				scoreBoardWindow.refreshGradute(3);
				break;
			case 300:
			btnConfirm.active = true;
				break;
			case 360:
				GameManager.activePlayerIndex = 0;
				if(GameManager.players.get(0).isAi){
					if(GameManager.players.get(0).credit >= SystemManager.CREDIT_LIMTED){
						int careerId = GameManager.seed.nextInt(8);
						GameManager.players.get(0).careerId = careerId;
						aiJobSelectWindow.open(GameManager.players.get(0).characterData.name + "选择了职业生涯:" + DataManager.careers.get(careerId).name);
					}else {
						GameManager.players.get(0).careerId = 8;
						aiJobSelectWindow.open(GameManager.players.get(0).characterData.name + "选择了其他职业工作");
					}
					
				}else{
					if(GameManager.players.get(0).credit < SystemManager.CREDIT_LIMTED){
						aiJobSelectWindow.open(GameManager.players.get(0).characterData.name + "选择了其他职业工作");
					}else{
						jobSelectWindow.open(GameManager.players.get(0));
						GameManager.players.get(0).careerId = 8;
					}
				}
				break;
			case 370:
				GameManager.activePlayerIndex = 1;
				if(GameManager.players.get(1).isAi){
					if(GameManager.players.get(1).credit >= SystemManager.CREDIT_LIMTED){
						int careerId = GameManager.seed.nextInt(8);
						GameManager.players.get(1).careerId = careerId;
						aiJobSelectWindow.open(GameManager.players.get(1).characterData.name + "选择了职业生涯:" + DataManager.careers.get(careerId).name);
					}else {
						GameManager.players.get(1).careerId = 8;
						aiJobSelectWindow.open(GameManager.players.get(1).characterData.name + "选择了其他职业工作");
					}
				}else{
					if(GameManager.players.get(1).credit < SystemManager.CREDIT_LIMTED){
						aiJobSelectWindow.open(GameManager.players.get(1).characterData.name + "选择了其他职业工作");
					}else{
						jobSelectWindow.open(GameManager.players.get(1));
						GameManager.players.get(1).careerId = 8;
					}
				}
				break;
			case 380:
				GameManager.activePlayerIndex = 2;
				if(GameManager.players.get(2).isAi){
					if(GameManager.players.get(2).credit >= SystemManager.CREDIT_LIMTED){
						int careerId = GameManager.seed.nextInt(8);
						GameManager.players.get(2).careerId = careerId;
						aiJobSelectWindow.open(GameManager.players.get(2).characterData.name + "选择了职业生涯:" + DataManager.careers.get(careerId).name);
					}else {
						GameManager.players.get(2).careerId = 8;
						aiJobSelectWindow.open(GameManager.players.get(2).characterData.name + "选择了其他职业工作");
					}
				}else{
					if(GameManager.players.get(2).credit < SystemManager.CREDIT_LIMTED){
						aiJobSelectWindow.open(GameManager.players.get(2).characterData.name + "选择了其他职业工作");
					}else{
						jobSelectWindow.open(GameManager.players.get(2));
						GameManager.players.get(2).careerId = 8;
					}
				}
				break;
			case 390:
				GameManager.activePlayerIndex = 3;
				if(GameManager.players.get(3).isAi){
					if(GameManager.players.get(3).credit >= SystemManager.CREDIT_LIMTED){
						int careerId = GameManager.seed.nextInt(8);
						GameManager.players.get(3).careerId = careerId;
						aiJobSelectWindow.open(GameManager.players.get(3).characterData.name + "选择了职业生涯:" + DataManager.careers.get(careerId).name);
					}else {
						GameManager.players.get(3).careerId = 8;
						aiJobSelectWindow.open(GameManager.players.get(3).characterData.name + "选择了其他职业工作");
					}
				}else {
					if(GameManager.players.get(3).credit < SystemManager.CREDIT_LIMTED){
						GameManager.players.get(3).careerId = 8;
						aiJobSelectWindow.open(GameManager.players.get(3).characterData.name + "选择了其他职业工作");
					}else{
						jobSelectWindow.open(GameManager.players.get(3));
						GameManager.players.get(3).careerId = 8;
					}
				}
				break;
			case 400:
				gotoAdultEra();
				break;
			case 450:
				ps.isFinish = true;
				break;
			case 600:
				SystemManager.newScene = new SceneAdultEra();
				break;
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void dispose(GL11 gl) {
		scoreBoardWindow.dispose();
		jobSelectWindow.dispose();
		aiJobSelectWindow.dispose();
	}

	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {
		
	}


	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		if(btnConfirm.isPressed(touchX, touchY)){
			btnConfirm.active = false;
			scoreBoardWindow.close();
		}
		for(int i =0;i < 3;i++){
			if(jobSelectWindow.btnSelections[i].isPressed(touchX, touchY)){
				jobSelectWindow.selectIndex = i;
				jobSelectWindow.isOpening = false;
			}
		}
	}

	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {
		
	}

	@Override
	public void resume() {
		scoreBoardWindow.resume();
		aiJobSelectWindow.resume();
		jobSelectWindow.resume();
	}

	@Override
	public void touchUpdatePointerDown(MotionEvent e) {
		
	}

	@Override
	public void touchUpdatePointerUp(MotionEvent e) {
		
	}

	@Override
	public void updateOnce() {
		//TODO:BGM
	}

	void gotoAdultEra(){
		for (int i = 0; i < 4; i++) {
			//金融
			if(GameManager.players.get(i).careerId == 4){
				GameManager.players.get(i).cash += 2000;
			//销售
			}else if (GameManager.players.get(i).careerId == 7) {
				GameManager.players.get(i).salary += 500;
			}else if (GameManager.players.get(i).careerId == 0) {
				GameManager.players.get(i).setHp(99);
			}
		}
		for (GamePlayer player : GameManager.players) {
			boolean max = true;
			for (GamePlayer oppoment : player.opponents) {
				if(player.credit < oppoment.credit){
					max = false;
					break;
				}
			}
			player.mapIndex = SystemManager.TILEMAX[0]+1;
			if(max){
				player.achievementPoint += 5;
				GameManager.messages.add(player.characterData.name + "获得了5个成就点数");
			}
		}
		GameManager.activePlayerIndex = 0;
		GameManager.age = 1;
		
	}
}
