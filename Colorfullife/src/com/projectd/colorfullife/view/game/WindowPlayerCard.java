package com.projectd.colorfullife.view.game;

import android.graphics.Color;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowPlayerCard extends WindowBase {
	Sprite2D playerFace;
	public SpriteButton bg;
	TextField txtName;
	TextField txtPlayerAP;
	TextField txtPlayerCash;
	
	Sprite2D playerHpBar;
	Sprite2D stopIcon;
	
	public WindowPlayerCard(){
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game), 560,0,256,112);
		bg.setLocation(160, 0);
		
		playerFace = CacheManager.getPlayerFaceById(0, 80, 80);
		playerFace.setLocation(176, 13);
		
		playerHpBar = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game), 816,0,160,32);
		playerHpBar.setLocation(258, 53);
		playerHpBar.setScale(1, 1);
		
		stopIcon = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),32,0,32,32);
		stopIcon.setLocation(244, 80);
		stopIcon.alpha = 0;
		
		txtName = new TextField("name", 128, 64);
		txtName.setSize(26);
		txtName.setColor(Color.BLACK);
		txtName.setLocation(260, 0);
		
		txtPlayerAP = new TextField("99", 64, 64);
		txtPlayerAP.setSize(48);
		txtPlayerAP.setColor(Color.BLACK);
		txtPlayerAP.alpha = 0.5f;
		txtPlayerAP.setAlign(Alignment.ALIGN_OPPOSITE);
		txtPlayerAP.setLocation(344,8);
		
		txtPlayerCash = new TextField("£¤99999", 128, 32);
		txtPlayerCash.setSize(20);
		txtPlayerCash.setColor(Color.BLACK);
		txtPlayerCash.setAlign(Alignment.ALIGN_OPPOSITE);
		txtPlayerCash.setLocation(220,32);
	}
	
	/**ÖØ»æ*/
	public void onDrawFrame(){
		SpriteBatch.draw(bg);
		SpriteBatch.draw(playerFace);
		SpriteBatch.draw(playerHpBar);
		SpriteBatch.drawText(txtPlayerAP);
		SpriteBatch.drawText(txtPlayerCash);
		SpriteBatch.drawText(txtName);
		SpriteBatch.draw(stopIcon);
	}
	
	public void refresh(GamePlayer setPlayer) {
		float bar = (float)setPlayer.getHp() / setPlayer.maxHp; 
		float x = setPlayer.characterData.faceid % 4 * CacheManager.PLAYER_FACE_WIDTH;
		float y = setPlayer.characterData.faceid / 4 * CacheManager.PLAYER_FACE_HEIGHT;
		playerHpBar.setScale(bar, 1);
		txtName.text = String.valueOf(setPlayer.characterData.name);
		txtPlayerAP.text = String.valueOf(setPlayer.achievementPoint);
		txtPlayerCash.text = "£¤" + String.valueOf(setPlayer.cash);
		playerFace.setSrcRectLocation(x, y);
		if(setPlayer.stopCount > 0){
			stopIcon.alpha = 1;
		}else {
			stopIcon.alpha = 0;
		}
	}
	
	@Override
	public void dispose() {
		txtPlayerAP.dispose();
		txtPlayerCash.dispose();
		txtName.dispose();
	}

	@Override
	public void resume() {
		txtPlayerAP.resume();
		txtPlayerCash.resume();
		txtName.resume();
	}

}
