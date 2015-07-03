package com.projectd.colorfullife.view.game;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowEvent extends WindowBase{
	Sprite2D window;
	Sprite2D bg;
	TextField eventTitle;
	TextField eventContent;
	Sprite2D ico;
	private int count;
	public WindowEvent(){
		window = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game), 0, 144, 592, 224);
		window.setLocation(224, 144);
		
		ico = CacheManager.getEventIcoById(0, 150, 150);
		ico.setLocation(282, 164);
		
		bg = new Sprite2D(CacheManager.black.texture,
			new RectF(0, 0, 1024, 512),
			new RectF(0, 0, 1, 1)
		);
		bg.alpha = 0.8f;
		eventTitle = new TextField("", 512, 32);
		eventTitle.setSize(24);
		eventTitle.setColor(Color.BLACK);
		//eventTitle.setAlign(Alignment.ALIGN_CENTER);
		eventTitle.setLocation(308, 160);

		eventContent = new TextField("", 512, 128);		
		eventContent.setLocation(272,216);
		eventContent.setColor(Color.BLACK);
		eventContent.setSize(20);
	}
	
	public void onDrawFrame(){
		if(!isOpening){
			return;
		}
		SpriteBatch.draw(bg);
		//SpriteBatch.draw(ico);
		SpriteBatch.draw(window);
		SpriteBatch.drawText(eventTitle);
		SpriteBatch.drawText(eventContent);
		
	}
	
	public void open(int setIconId,String setTitle,String setInfo) {
		isOpening = true;
		count = 200;
		eventTitle.text = setTitle;
		eventContent.text = setInfo;
		int x = 128 * (setIconId % 16);
		int y = 128 * (setIconId / 16);
		ico.setSrcRectLocation(x, y);
	}
	@Override
	public void dispose() {
		eventTitle.dispose();
		eventContent.dispose();
		
	}		
	@Override
	public void resume() {
		eventTitle.resume();
		eventContent.resume();
	}


	public void update(float deltaTime) {
		if(count > 0){
			count -= SystemManager.deltaTimex100;
		}else{			
			isOpening = false;
		}
	}
}
