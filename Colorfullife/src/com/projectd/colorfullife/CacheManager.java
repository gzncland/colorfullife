package com.projectd.colorfullife;

import java.lang.reflect.Field;

import javax.microedition.khronos.opengles.GL11;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.view.SpritePlayer;
import com.projectd.framework.CacheBase;
import com.projectd.framework.sprite.Sprite2D;

import android.content.res.Resources;
import android.graphics.RectF;

public class CacheManager extends CacheBase {
	public static void initialze(GL11 setGl,Resources setRes){
		res = setRes;
		
		textureIndex = 0;
		gl = setGl;
		gl.glGenTextures(CacheBase.MAX_TEXTURE_COUNT,  textureArray , 0);
		CacheBase.genBlackTexture();
		
//		decode(R.drawable.ui_title);
		decode(R.drawable.ui_final);
		decode(R.drawable.ui_game);
		decode(R.drawable.ui_game_scoreboard);
		decode(R.drawable.ui_itembox);
		decode(R.drawable.ui_end);
		decode(R.drawable.ui_graduate);
		decode(R.drawable.ui_retire);
		decode(R.drawable.ui_logo);
		decode(R.drawable.ui_menu);
		decode(R.drawable.ui_rc_dice);
		decode(R.drawable.ui_rule);
		
		decode(R.drawable.ui_title_newgame_bg);
		decode(R.drawable.ui_title_newgame_sp);
		decode(R.drawable.ui_title_options);
		decode(R.drawable.ui_title_continue);
		
		decode(R.drawable.sp_die);
		decode(R.drawable.sp_title);
		decode(R.drawable.sp_player);	
		decode(R.drawable.sp_playerface);
//		decode(R.drawable.sp_particle);
			
		decode(R.drawable.ico_career);
		decode(R.drawable.ico_event);
		
		for (int y = 0; y < SystemManager.STUDY_TILES_ROW_COUNT; y++) {
			for (int x = 0; x < SystemManager.STUDY_TILES_COLUMN_COUNT; x++) {
				try {
					Field field = R.drawable.class.getField("map_student_" + x + "_" + y);
					decode(field.getInt(field));
				} catch (NoSuchFieldException e) {			
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		for (int y = 0; y < SystemManager.ADULT_TILES_ROW_COUNT; y++) {
			for (int x = 0; x < SystemManager.ADULT_TILES_COLUMN_COUNT; x++) {
				try {
					Field field = R.drawable.class.getField("map_adult_" + x + "_" + y);
					decode(field.getInt(field));
				} catch (NoSuchFieldException e) {			
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		for (int y = 0; y < SystemManager.OLD_TILES_ROW_COUNT; y++) {
			for (int x = 0; x < SystemManager.OLD_TILES_COLUMN_COUNT; x++) {
				try {
					Field field = R.drawable.class.getField("map_old_" + x + "_" + y);
					decode(field.getInt(field));
				} catch (NoSuchFieldException e) {			
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private final static int PLAYER_WIDTH = 128;
	private final static int PLAYER_HEIGHT = 128;
	public static SpritePlayer getPlayerSprtieById(int setPlayerId, float setWidth, float setHeight) {
		int x = PLAYER_WIDTH * (setPlayerId % 4);
		int y = PLAYER_HEIGHT * (setPlayerId / 4);
		return new SpritePlayer(CacheManager.getTextureById(R.drawable.sp_player),new RectF(0, 0, setWidth, setHeight) , new RectF(x, y, x + PLAYER_WIDTH, y + PLAYER_HEIGHT));
		
	}
	
	public final static int PLAYER_FACE_WIDTH = 128;
	public final static int PLAYER_FACE_HEIGHT = 128;
	public static Sprite2D getPlayerFaceById(int setPlayerId, float setWidth, float setHeight) {
		int x = PLAYER_FACE_WIDTH * (setPlayerId % 4);
		int y = PLAYER_FACE_HEIGHT * (setPlayerId / 4);
		return new Sprite2D(CacheManager.getTextureById(R.drawable.sp_playerface),new RectF(0, 0, setWidth, setHeight) , new RectF(x, y, x + PLAYER_FACE_WIDTH, y + PLAYER_FACE_HEIGHT));
		
	}
	
	public final static int ICO_CAREER_WIDTH = 128;
	public final static int ICO_CAREER_HEIGHT = 128;
	public static Sprite2D getCareerIcoById(int setCareerId, float setWidth, float setHeight) {
		int x = ICO_CAREER_WIDTH * (setCareerId % 4);
		int y = ICO_CAREER_HEIGHT * (setCareerId / 4);
		return new Sprite2D(CacheManager.getTextureById(R.drawable.ico_career),new RectF(0, 0, setWidth, setHeight) , new RectF(x, y, x + ICO_CAREER_WIDTH, y + ICO_CAREER_HEIGHT));
		
	}
	
	public final static int ICO_EVENT_WIDTH = 128;
	public final static int ICO_EVENT_HEIGHT = 128;
	public static Sprite2D getEventIcoById(int setEventId, float setWidth, float setHeight) {
		int x = ICO_EVENT_WIDTH * (setEventId % 16);
		int y = ICO_EVENT_HEIGHT * (setEventId / 16);
		return new Sprite2D(CacheManager.getTextureById(R.drawable.ico_event),new RectF(0, 0, setWidth, setHeight) , new RectF(x, y, x + ICO_EVENT_WIDTH, y + ICO_EVENT_HEIGHT));
	}
	/**
	 * GL上下文丢失后重新绑定纹理
	 */
	public static void reset() {
		if(gl != null && res != null){
			initialze(gl,res);
		}
	}
}
