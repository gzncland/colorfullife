package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.SceneBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;

public class SceneLogo extends SceneBase {

	private Sprite2D logo;
	private int count;

	public SceneLogo(){
		logo = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_logo),0,0,1024,512);
	}
	@Override
	public void onDrawFrame() {
		SpriteBatch.draw(logo);
	}

	@Override
	public void update(float deltaTime) {
		count += SystemManager.deltaTimex100;
		if(count > 60){
			SystemManager.newScene = new SceneTitle();
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose(GL11 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

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
