package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.colorfullife.view.BgAnimation;
import com.projectd.colorfullife.view.WindowScoreBoardRetire;
import com.projectd.colorfullife.view.game.WindowMessage;
import com.projectd.framework.SceneBase;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class SceneRetire extends SceneBase {

	WindowScoreBoardRetire scoreBoardRetireWindow;
	BgAnimation ps;
	WindowMessage messageWindow;
	int count;
	SpriteButton btnConfirm;
	public SceneRetire(){
		super();
		ps = new BgAnimation(1);
		messageWindow = new WindowMessage();
		scoreBoardRetireWindow = new WindowScoreBoardRetire();
		scoreBoardRetireWindow.open();
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
		scoreBoardRetireWindow.onDrawFrame();
		
		SpriteBatch.draw(btnConfirm);
		messageWindow.onDrawFrame();
	}

	@Override
	public void update(float deltaTime) {
		ps.update(deltaTime);
		messageWindow.update(deltaTime);
		if(messageWindow.isOpening){
			return;
		}
		if(GameManager.messages.size() > 0){
			messageWindow.open(GameManager.messages.remove(0));
		}		
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
			scoreBoardRetireWindow.refresh(0);
			break;
		case 150:
			scoreBoardRetireWindow.refresh(1);
			break;
		case 240:
			scoreBoardRetireWindow.refresh(2);
			break;
		case 330:
			scoreBoardRetireWindow.refresh(3);
			break;
		case 420:
			btnConfirm.active = true;
			break;
		case 450:
			gotoOldEra();
			break;
		case 500:
			ps.isFinish = true;
			break;
		case 600:
			SystemManager.newScene = new SceneOldEra();
			break;
		}
	}

	private void gotoOldEra() {
		for (GamePlayer player : GameManager.players) {
			boolean max = true;
			for (GamePlayer oppoment : player.opponents) {
				if(player.cash < oppoment.cash){
					max = false;
					break;
				}
			}
			player.mapIndex = SystemManager.TILEMAX[1] + 1;
			if(max){
				player.achievementPoint += 5;
				GameManager.messages.add(player.characterData.name + "获得了5个成就点数");
			}
		}
		GameManager.activePlayerIndex = 0;
		GameManager.age = 2;
		
	}
	@Override
	public void pause() {
		

	}

	@Override
	public void dispose(GL11 gl) {
	

	}

	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {


	}

	@Override
	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		if(btnConfirm.isPressed(touchX, touchY)){
			btnConfirm.active = false;
			scoreBoardRetireWindow.close();
		}

	}

	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {


	}

	@Override
	public void resume() {
	

	}

	@Override
	public void touchUpdatePointerDown(MotionEvent e) {
	

	}

	@Override
	public void touchUpdatePointerUp(MotionEvent e) {
		

	}

	@Override
	public void updateOnce() {
	

	}

}
