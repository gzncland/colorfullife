package com.projectd.colorfullife.view.game;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.colorfullife.view.SpritePlayer;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;

public class SpritesetMap {
	ArrayList<SpritePlayer> players = new ArrayList<SpritePlayer>();
	
	Sprite2D[][] maps; 
	public SpritesetMap(int setEra){
		String eraName = null;
		switch (setEra) {
		case 0:
			maps = new Sprite2D[SystemManager.STUDY_TILES_ROW_COUNT][SystemManager.STUDY_TILES_COLUMN_COUNT];
			eraName = "map_student_";
			break;
		 case 1:
			 maps = new Sprite2D[SystemManager.ADULT_TILES_ROW_COUNT][SystemManager.ADULT_TILES_COLUMN_COUNT];
			 eraName = "map_adult_";
			 break;
		 case 2:
			 maps = new Sprite2D[SystemManager.OLD_TILES_ROW_COUNT][SystemManager.OLD_TILES_COLUMN_COUNT]; 
			 eraName = "map_old_";
			 break;
		}
		for (int y = 0; y < maps.length; y++) {
			for (int x = 0; x < maps[y].length; x++) {
				try {
					
					Field field = R.drawable.class.getField(eraName + x + "_" + y);
					maps[y][x] = new Sprite2D(CacheManager.getTextureById(field.getInt(field)),0,0,512,512);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				maps[y][x].setLocation(x * 512, y * 512);
			}
		}
		for (GamePlayer player : GameManager.players) {
			SpritePlayer playerSprite  = CacheManager.getPlayerSprtieById(player.characterData.characterid, 64, 64);
			playerSprite.playerData = player;
			players.add(playerSprite);
		}
	}
	public void  onDrawFrame() {
		for (int y = 0; y < maps.length; y++) {
			for (int x = 0; x < maps[y].length; x++) {
//				if(maps[y][x].getX() < -1024){
//					continue;
//				}
//				if (maps[y][x].getX() > 1536) {
//					continue;
//				}
//				if (maps[y][x].getY() < -1024) {
//					continue;
//				}
//				if (maps[y][x].getY() > 1024) {
//					continue;
//				}
				SpriteBatch.draw(maps[y][x]);
			}
		}
		for (int i = 0; i < players.size(); i++) {
			SpriteBatch.draw(players.get(i));
			if(GameManager.activePlayerIndex == i){
				continue;
			}
		}
		for (int i = 0; i < players.size(); i++) {
			if(GameManager.activePlayerIndex == i){
				SpriteBatch.draw(players.get(i));
				break;
			}
		}
	}
	public boolean isMoving;
	public void offset(float setX,float setY) {
		isMoving = true;
		offsetX += setX;
		offsetY += setY;
	}
	
	public float offsetX = 0;
	public float offsetY = 0;
	private boolean isZoomIn = true;

	
	public void update(float deltaTime){
		if(isMoving){
			float tempX = maps[0][0].getX();
			float tempY = maps[0][0].getY();
			if(tempX > 128){
				if(offsetX < 0){
					offsetX = 0;
				}
			}else if (tempX < -768) {
				if(offsetX > 0){
					offsetX = 0;
				}
			}
			if(tempY > 32){
				if(offsetY < 0){
					offsetY = 0;
				}
			}else if (tempY < -1152) {
				if(offsetY > 0){
					offsetY = 0;
				}
			}
			for (int y = 0; y < maps.length; y++) {
				for (int x = 0; x < maps[y].length; x++) {
					maps[y][x].offset(-offsetX, -offsetY);
				}
			}
			for (Sprite2D player : players) {
				player.offset(-offsetX, -offsetY);
			}
			isMoving = false;
			offsetX = 0;
			offsetY = 0;
		}
	}
	
	
	public void moveCameraTo(float setX,float setY){
		for (int y = 0; y < maps.length; y++) {
			for (int x = 0; x < maps[y].length; x++) {
				maps[y][x].setLocation((x + 1) * 512 - setX, y * 512 - setY + 256);
				//maps[y][x].setLocation(x * 512 + 512 - setX, y * 512 + setY + 128);					
			}
		}
		for (int i = 0; i < players.size(); i++) {
			int x = 0;
			int y = 0;
			if(GameManager.activePlayerIndex == i){
				x = DataManager.tiles.get(players.get(i).playerData.mapIndex).mapX - 32;
				y = DataManager.tiles.get(players.get(i).playerData.mapIndex).mapY - 32;
			}else{
				x = DataManager.tiles.get(players.get(i).playerData.mapIndex).mapX;
				y = DataManager.tiles.get(players.get(i).playerData.mapIndex).mapY;
			}
			players.get(i).setLocation(x - setX + 512 , y - setY + 256);
		}

	}
	
	public void zoomOut(){
		if(isZoomIn){
			//float tempX = maps[9][0].getX();
			//float tempY = maps[9][0].getY();
			isZoomIn = false;
			for (int y = 0; y < maps.length; y++) {
				for (int x = 0; x < maps[y].length; x++) {
					maps[y][x].z = -200;		
					//maps[y][x].offset(tempX, tempY);
				}
			}
			//isNeedRefresh = true;
			for (Sprite2D player : players) {
				player.z = -200;
			}
		}
	}
	
	public void zoomIn(){
		if(isZoomIn == false){
			//float tempX = maps[9][0].getX();
			//float tempY = maps[9][0].getY();
			isZoomIn = true;
			for (int y = 0; y < maps.length; y++) {
				for (int x = 0; x < maps[y].length; x++) {
					maps[y][x].z = 0;			
					//maps[y][x].offset(tempX, tempY);
				}
			}
			//isNeedRefresh = true;
			for (Sprite2D player : players) {
				player.z = 0;
			}
		}
	}
	
}
