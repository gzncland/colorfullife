package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.view.BgAnimation;
import com.projectd.colorfullife.view.WindowScoreBoardFinal;
import com.projectd.colorfullife.view.WindowScoreBoardGraduate;
import com.projectd.colorfullife.view.WindowScoreBoardOld;
import com.projectd.framework.SceneBase;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class ScenceFinal extends SceneBase {

	WindowScoreBoardOld scoreBoardOldWindow;
	WindowScoreBoardFinal scoreBoardFinalWindow;
	BgAnimation ps;
	int count;
	SpriteButton btnConfirm;
	public ScenceFinal(){
		super();
		ps = new BgAnimation(1);
		scoreBoardOldWindow = new WindowScoreBoardOld();
		scoreBoardOldWindow.open();
		scoreBoardFinalWindow = new WindowScoreBoardFinal();
		scoreBoardFinalWindow.close();
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
		scoreBoardOldWindow.onDrawFrame();
		scoreBoardFinalWindow.onDrawFrame();
		SpriteBatch.draw(btnConfirm);

	}

	@Override
	public void update(float deltaTime) {
		ps.update(deltaTime);
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
			scoreBoardOldWindow.refresh(0);
			break;
		case 150:
			scoreBoardOldWindow.refresh(1);
			break;
		case 240:
			scoreBoardOldWindow.refresh(2);
			break;
		case 330:
			scoreBoardOldWindow.refresh(3);
			break;
		case 420:
			btnConfirm.active = true;
			break;
		case 600:
			ps.isFinish = true;
			break;
		case 700:
			SystemManager.newScene = new SceneLogo();
			break;
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(GL11 gl) {
		scoreBoardFinalWindow.dispose();
		scoreBoardOldWindow.dispose();

	}

	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		if(btnConfirm.isPressed(touchX, touchY)){
			scoreBoardFinalWindow.open();
			btnConfirm.active = false;
			scoreBoardOldWindow.close();
		}

	}

	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		scoreBoardFinalWindow.resume();
		scoreBoardOldWindow.resume();

	}

	@Override
	public void touchUpdatePointerDown(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUpdatePointerUp(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOnce() {
		// TODO Auto-generated method stub

	}

}
