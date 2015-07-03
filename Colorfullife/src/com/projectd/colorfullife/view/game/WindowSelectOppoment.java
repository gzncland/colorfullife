package com.projectd.colorfullife.view.game;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowSelectOppoment extends WindowBase {
	Sprite2D bg;
	
	public ArrayList<SpriteButton> btnOppoments = new ArrayList<SpriteButton>();
	ArrayList<Sprite2D> spFaces = new ArrayList<Sprite2D>();
	ArrayList<TextField> txtNames = new ArrayList<TextField>();

	public int selectIndex;

	public SpriteButton btnCancel;
	
	public WindowSelectOppoment() {
		bg = new Sprite2D(CacheManager.black.texture,
				new RectF(0, 0, 1024, 512),
				new RectF(0, 0, 1, 1)
		);
		bg.alpha = 0.8f;
		btnCancel = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game), 608, 640, 144, 80);
		btnCancel.setLocation(160,432);
		for (int i = 0; i < 3; i++) {
	
			SpriteButton btnOppoment = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game), 432, 640, 176, 208);
			btnOppoment.setLocation(192 + i * 224, 160);
			btnOppoments.add(btnOppoment);
			
			Sprite2D spFace = CacheManager.getPlayerFaceById(0, 128, 128);
			spFace.setLocation(217 + i * 224, 182.8f);
			spFaces.add(spFace);
			
			TextField txtName = new TextField("", 128, 32);
			txtName.setColor(Color.BLACK);
			txtName.setLocation(216 + i * 224, 314);
			txtName.setAlign(Alignment.ALIGN_CENTER);
			txtNames.add(txtName);
		}
	}
	
	public void open(GamePlayer setPlayer){
		selectIndex = -1;
		for (int i = 0; i < setPlayer.opponents.size(); i++) {
			int id = setPlayer.opponents.get(i).characterData.faceid;
			int x = CacheManager.PLAYER_FACE_WIDTH * (id % 4);
			int y = CacheManager.PLAYER_FACE_HEIGHT * (int)(id / 4);
			spFaces.get(i).setSrcRectLocation(x, y);
			txtNames.get(i).text = setPlayer.opponents.get(i).characterData.name;
		}
		isOpening = true;
	}
	
	@Override
	public void dispose() {
		for (TextField txt : txtNames) {
			txt.dispose();
		}
	}

	@Override
	public void resume() {
		for (TextField txt : txtNames) {
			txt.resume();
		}
	}

	public void onDrawFrame() {
		if(isOpening == false){
			return;
		}
		SpriteBatch.draw(bg);
		SpriteBatch.draw(btnCancel);
		for (SpriteButton btnsp : btnOppoments) {
			SpriteBatch.draw(btnsp);
		}
		for (Sprite2D spface : spFaces) {
			SpriteBatch.draw(spface);
		}
		for (TextField txt : txtNames) {
			SpriteBatch.drawText(txt);
		}
	}

}
