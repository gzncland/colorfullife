package com.projectd.colorfullife.view.game;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowMessage extends WindowBase {
	Sprite2D windowBg;
	Sprite2D bg;
	TextField text;
	private int count;
	public WindowMessage(){
		bg = new Sprite2D(CacheManager.black.texture,
				new RectF(0, 0, 1024, 512),
				new RectF(0, 0, 1, 1)
			);
			bg.alpha = 0.8f;
		windowBg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),  0,512,560,112);
		windowBg.setLocation(224, 192);
		text = new TextField("", 512, 64);
		text.setLocation(256, 224);
		text.setSize(32);
	}
	public void update(float deltaTime) {
		if(count > 0){
			count -= SystemManager.deltaTimex100;
		}else {
			isOpening = false;
		}
	}
	
	public void onDrawFrame(){
		if(isOpening == false){
			return;
		}
		SpriteBatch.draw(bg);
		SpriteBatch.draw(windowBg);
		SpriteBatch.drawText(text);
	}
	
	public void open(String setText){
		count = 100;
		text.text = setText;
		isOpening = true;
	}
	
	@Override
	public void dispose() {
		text.dispose();
	}

	@Override
	public void resume() {
		text.resume();
	}

}
