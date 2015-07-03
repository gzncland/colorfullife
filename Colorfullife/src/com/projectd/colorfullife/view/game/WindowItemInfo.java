package com.projectd.colorfullife.view.game;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowItemInfo extends WindowBase {
	Sprite2D bg;
	Sprite2D windowBg;
	Sprite2D itemIco;
	TextField txtItemInfo;
	public SpriteButton btnBack;
	public SpriteButton btnConfirm;
	public int itemIndex;
	TextField txtItemName;
	public WindowItemInfo(){
		bg = new Sprite2D(CacheManager.black.texture,
			new RectF(0, 0, 1024, 512),
			new RectF(0, 0, 1, 1)
		);
		bg.alpha = 0.8f;
		
		windowBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game), 0, 640, 432, 208);
		windowBg.setLocation(288, 144);
		btnBack = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game), 608, 640, 144, 80);
		btnBack.setLocation(160,432);
		
		btnConfirm = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game), 608, 720, 144, 80);
		btnConfirm.setLocation(720,432);
		
		itemIco = CacheManager.getEventIcoById(0, 128, 128);
		itemIco.setLocation(310, 160);
		
		txtItemInfo = new TextField("", 256, 128);
		txtItemInfo.setColor(Color.BLACK);
		txtItemInfo.setLocation(440, 200);
		txtItemInfo.setAlign(Alignment.ALIGN_CENTER);
		
		txtItemName = new TextField("", 256, 64);
		txtItemName.setSize(26);
		txtItemName.setColor(Color.BLACK);
		txtItemName.setAlign(Alignment.ALIGN_CENTER);
		txtItemName.setLocation(440, 158);
	}
	
	public void onDrawFrame(){
		if(isOpening == false){
			return;
		}
		SpriteBatch.draw(bg);
		SpriteBatch.draw(windowBg);
		SpriteBatch.draw(btnBack);
		SpriteBatch.draw(btnConfirm);
		SpriteBatch.draw(itemIco);
		SpriteBatch.drawText(txtItemName);
		SpriteBatch.drawText(txtItemInfo);
	}
	
	@Override
	public void dispose() {
		txtItemName.dispose();
		txtItemInfo.dispose();
	}

	@Override
	public void resume() {
		txtItemName.resume();
		txtItemInfo.resume();
	}

	public void open(int setItemIndex) {
		isOpening = true;
		itemIndex = setItemIndex;
		int x = 128 * (DataManager.items.get(setItemIndex).spId % 16);
		int y = 128 * (DataManager.items.get(setItemIndex).spId / 16);
		itemIco.setSrcRectLocation(x, y);
		txtItemInfo.text = DataManager.items.get(setItemIndex).info;
		txtItemName.text = DataManager.items.get(setItemIndex).name;
	}

}
