package com.projectd.colorfullife.view.game;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowOppoments extends WindowBase {
	public Sprite2D oppomentsFace[] = new Sprite2D[3];
	public TextField oppomentsCash[] = new TextField[3];
	public TextField oppomentsName[] = new TextField[3];
	Sprite2D oppmentsBg;
	Sprite2D [] stopIcon;
	public WindowOppoments(){
		oppmentsBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),0,80,416,64);
	    oppmentsBg.setLocation(304, 448);
		for (int i = 0; i < 3; i++) {
			oppomentsFace[i] = CacheManager.getPlayerFaceById(0, 44, 44);
			oppomentsFace[i].setLocation(330 + i * 120, 458);
			oppomentsCash[i] = new TextField("", 128, 32);
			oppomentsCash[i].setColor(Color.BLACK);
			oppomentsCash[i].setLocation(310 + i * 120, 488);
			oppomentsCash[i].setSize(14);
			oppomentsCash[i].setAlign(Alignment.ALIGN_OPPOSITE);
			
			oppomentsName[i] = new TextField("name", 128, 32);
			oppomentsName[i].setColor(Color.BLACK);
			oppomentsName[i].setLocation(380 + i * 120, 462);
			oppomentsName[i].setSize(14);
		}
		stopIcon = new Sprite2D [3];
	    for (int i = 0; i < 3; i++) {
	        stopIcon [i] = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game), new RectF(0,0,16,16),  new RectF(32,0,64,32));
	        stopIcon [i].alpha = 0;
	        stopIcon [i].setLocation(370 + i*120, 496);
		}
	}
	
	public void refresh(GamePlayer setPlayer) {
		for (int i = 0; i < 3; i++) {
			int x = CacheManager.PLAYER_FACE_WIDTH * (setPlayer.opponents.get(i).characterData.faceid % 4);
			int y = CacheManager.PLAYER_FACE_HEIGHT * (setPlayer.opponents.get(i).characterData.faceid / 4);
			oppomentsFace[i].setSrcRectLocation(x, y);
			oppomentsCash[i].text = "гд" + setPlayer.opponents.get(i).cash;
			oppomentsName[i].text = setPlayer.opponents.get(i).characterData.name;
			if(GameManager.getActivePlayer().opponents.get(i).stopCount > 0){
				stopIcon[i].alpha = 1;
			}else {
				stopIcon[i].alpha = 0;
			}
		}
		
	}
	
	public void onDrawFrame(){
		SpriteBatch.draw(oppmentsBg);
		for (int i = 0; i < 3; i++) {
			SpriteBatch.draw(oppomentsFace[i]);
			SpriteBatch.drawText(oppomentsCash[i]);
			SpriteBatch.drawText(oppomentsName[i]);
			SpriteBatch.draw(stopIcon[i]);
		}
	}
	
	@Override
	public void dispose() {
		for (int i = 0; i < 3; i++) {
			oppomentsCash[i].dispose();
			oppomentsName[i].dispose();
		}
	}

	@Override
	public void resume() {
		for (int i = 0; i < 3; i++) {
			oppomentsCash[i].resume();
			oppomentsName[i].resume();
		}
	}

}
