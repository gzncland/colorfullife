package com.projectd.colorfullife.view;

import java.util.ArrayList;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;

public class BgAnimation {
	//ArrayList<Sprite2D> clouds = new ArrayList<Sprite2D>();
	Sprite2D bg;
	Sprite2D bottom;
	ArrayList<Sprite2D> fgSprites = new ArrayList<Sprite2D>();
	ArrayList<Sprite2D> bgSprites = new ArrayList<Sprite2D>();
	ArrayList<Sprite2D> rails = new ArrayList<Sprite2D>();
	Sprite2D car;
	Sprite2D wheel1;
	Sprite2D wheel2;
	
	Sprite2D title;
	
	public boolean isFinish;
	private float value = 0;
	private boolean drawTitle;
	public BgAnimation(int setType) {
		switch (setType) {
		case 0:
			initializeMorningSpset();
			drawTitle = true;
			break;
		case 1:
			initializeMorningSpset();
			drawTitle = false;
			break;
		}
		
		title = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				0,0,624,144
			);
		title.setLocation(176, 144);
	}
/*
	private void initializeNightSpset() {
		bg = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				new RectF(0, 0, 1024, 512), 
				new RectF(992, 0, 1008 , 512)
			);
		bottom = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				new RectF(0, 0, 1024, 48), 
				new RectF(896, 0, 928, 48)
			);
		bottom.setLocation(0, 464);
		car = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				160,224,320,64
			);
		car.setLocation(160, 409);
		
		wheel1 = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				160,288,32,32
			);
		wheel1.setLocation(193f, 437);
		
		wheel2 = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				160,288,32,32
			);
		wheel2.setLocation(278f, 437);
		
		for (int i = 0; i < 9; i++) {
			Sprite2D rail = new Sprite2D(
					CacheManager.getTextureById(R.drawable.sp_title),
					0,240,128,48
				);
			rail.setLocation(i * 128, 424);
			rails.add(rail);
		}
		
		for (int i = 0; i < 16; i++) {
			Sprite2D tree = new Sprite2D(
					CacheManager.getTextureById(R.drawable.sp_title),
					0,384,208,128
				);
			tree.alpha = 0.5f + (float)(0.5 * Math.random());
			tree.setLocation(i * 64, (float) (340 + Math.random() * 64));
			fgSprites.add(tree);
		}
	}
*/
	private void initializeMorningSpset() {
		bg = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				new RectF(0, 0, 1024, 512), 
				new RectF(976, 0, 992, 512)
			);
		bottom = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				new RectF(0, 0, 1024, 48), 
				new RectF(0, 480, 16, 512)
			);
		bottom.setLocation(0, 480);
		
		car = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				0,144,160,48
			);
		car.setLocation(160, 440);
		
		wheel1 = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				160,144,32,32
			);
		wheel1.setLocation(180, 458);
		
		wheel2 = new Sprite2D(
				CacheManager.getTextureById(R.drawable.sp_title),
				160,144,32,32
			);
		wheel2.setLocation(262f, 458);
		for (int i = 0; i < 9; i++) {
			Sprite2D rail = new Sprite2D(
					CacheManager.getTextureById(R.drawable.sp_title),
					192,144,128,32
				);
			rail.setLocation(i * 128, 452);
			rails.add(rail);
		}
		for (int i = 0; i < 6; i++) {
			Sprite2D hill = new Sprite2D(
					CacheManager.getTextureById(R.drawable.sp_title),
					0,320,720,160
				);
			hill.alpha = 0.15f + (float)(0.35 * Math.random());
			hill.setLocation(i * 256, (float) (320 + Math.random() * 128));
			bgSprites.add(hill);
		}
		for (int i = 0; i < 9; i++) {
			Sprite2D tree = new Sprite2D(
					CacheManager.getTextureById(R.drawable.sp_title),
					640,0,128,176
				);
			tree.alpha = 0.5f + (float)(0.5 * Math.random());
			tree.setAngle((float) ((0.5 - Math.random()) * 10));
			tree.setLocation((float) (i * 128 + (0.5 - Math.random()) * 128) , (float) (320 + Math.random() * 64));
			fgSprites.add(tree);
		}
//		for (int i = 0; i < 6; i++) {
//			Sprite2D cloud = new Sprite2D(
//					CacheManager.getTextureById(R.drawable.sp_title),
//					576,0,192,96
//				);
//			//cloud.alpha = 0.5f + (float)(0.5 * Math.random());
//			cloud.setLocation(i * 204, (float) (-32 + Math.random() * 64));
//			cloud.setScale((float) (0.5 + Math.random()));
//			clouds.add(cloud);
//		}
	}
	
	//TODO GC
	public void update(float deltaTime) {
		wheel1.setAngle(wheel1.getAngle() + deltaTime * 500);
		wheel2.setAngle(wheel2.getAngle() + deltaTime * 500);
		for (Sprite2D rail : rails) {
			rail.offset(-2, 0);
			if(rail.getX() <= -128){
				rail.setLocation(1024, 452);
			}
		}
		
		for (Sprite2D bg : bgSprites) {
			bg.offset(-1, 0);
			if(bg.getX() <= -512){
				bg.offset(1408, 0);
			}
		}
		for (Sprite2D fg : fgSprites) {
			fg.offset(-2, 0);
			if(fg.getX() <= -128){
				fg.offset(1152, 0);
			}
		}/*
		for (Sprite2D cloud : clouds) {
			cloud.offset(-2, 0);
			if(cloud.getX() <= -128){
				cloud.offset(1152, 0);
			}
		}*/
		if(isFinish){
			car.offset(value, 0);
			wheel1.offset(value, 0);
			wheel2.offset(value, 0);
			value += 0.15;
		}
	}
	
	public void onDrawFrame() {
		SpriteBatch.draw(bg);
		
		for (Sprite2D bgsp : bgSprites) {
			SpriteBatch.draw(bgsp);
		}
		for (Sprite2D fgsp : fgSprites) {
			SpriteBatch.draw(fgsp);
		}
		/*
		for (Sprite2D cloud : clouds) {
			SpriteBatch.draw(cloud);
		}*/
		for (Sprite2D rail : rails) {
			SpriteBatch.draw(rail);
		}
		SpriteBatch.draw(car);
		SpriteBatch.draw(wheel1);
		SpriteBatch.draw(wheel2);
		SpriteBatch.draw(bottom);
		if(drawTitle){
			SpriteBatch.draw(title);
		}
	}
}
