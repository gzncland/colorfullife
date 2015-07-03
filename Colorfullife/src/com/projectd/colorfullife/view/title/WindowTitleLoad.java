package com.projectd.colorfullife.view.title;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.FileManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.data.DataArchive;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowTitleLoad extends WindowBase {

	public SpriteButton btnLoad;
	public SpriteButton btnClose;
	public SpriteButton btnConfirm;
	
	Sprite2D bg;
	Sprite2D ageBar;
	Sprite2D [] playersFace = new Sprite2D[4];
	
	TextField [] playersCash = new TextField[4];
	TextField [] playersAchive = new TextField[4];
	TextField time;
	
	public boolean isHidding;
	
	public DataArchive setdata = new DataArchive();
	public WindowTitleLoad() {
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_continue),0,0,1024,512);
		bg.setLocation(0, 512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnConfirm = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),288,112,144,80);
		btnConfirm.setLocation(704, 944);
		btnClose.setLocation(0, 512);
		btnLoad = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),432,112,144,80);
        btnLoad.setLocation(560,432);
        time = new TextField("", 256, 64);
        time.setSize(22);
        time.setLocation(488, 966);
		for(int i = 0; i < 4; i++){
			playersFace[i] = CacheManager.getPlayerFaceById(1+i, 120, 120);
			playersFace[i].setLocation(200+i*168, 696);
			playersCash[i] = new TextField("", 64, 32);
			playersCash[i].setColor(Color.WHITE);
			playersCash[i].setAlign(Alignment.ALIGN_CENTER);
			playersCash[i].setLocation(244+i*168, 828);
			//playersCash[i].setLocation(244+i*168, 828);
			playersAchive[i] = new TextField("", 128, 64);
			playersAchive[i].setSize(60);
			playersAchive[i].setColor(Color.BLACK);
			playersAchive[i].setAlign(Alignment.ALIGN_OPPOSITE);
			playersAchive[i].setLocation(184+i*168, 864);
			playersAchive[i].alpha = 0.6f;
		}
		
		setdata = FileManager.loadGame();
		float width = 0;
		if(setdata != null){
			width = 8 + (float)setdata.dayCount / 75 * 192;
		}
		
		ageBar = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp), 672,48, (int) width,31);
		ageBar.setLocation(192, 469 + 512);
	}
	
	public void update(float deltaTime){
		int deltaValue = SystemManager.deltaTimex100;
		while (deltaValue  > 0) {
			deltaValue -= 1;
			if(isHidding){
				if(bg.getY() < 592){
					offset(0, 32);
				}
			}else if(isCloseing){
				if(bg.getY() < 512){
					offset(0, 32);
				}else{
					isOpening = false;
					isCloseing = false;
				}
			}else if(isOpening){
				if(bg.getY() > 0){
					offset(0, -32);
				}
			}else if(bg.getY() > 512){
				offset(0, -32);
			}
		}
		
	}
	
	/**整体偏移*/
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnLoad.offset(setX, setY);
		btnClose.offset(setX, setY);
		btnConfirm.offset(setX, setY);
		time.offset(setX, setY);
		ageBar.offset(setX, setY);
		for(int i = 0; i < 4; i++){
			playersFace[i].offset(setX, setY);
			playersCash[i].offset(setX, setY);
			playersAchive[i].offset(setX, setY);
		}
	}
	public void onDrawFrame() {
		SpriteBatch.draw(btnLoad);
		if(isOpening == true){
			SpriteBatch.draw(bg);
			SpriteBatch.draw(btnConfirm);
			SpriteBatch.drawText(time);
			for(int i = 0; i < 4; i++){
				SpriteBatch.draw(playersFace[i]);
				SpriteBatch.drawText(playersCash[i]);
				SpriteBatch.drawText(playersAchive[i]);
			}
		}
		SpriteBatch.draw(ageBar);
	}
	/**打开窗口*/
	public void open(){
		
		if(setdata == null){
			return;
		}
		isOpening = true;
		isCloseing = false;
		for(int i = 0; i < 4; i++){
			playersFace[i] = CacheManager.getPlayerFaceById(DataManager.characters.get(setdata.characterId[i]).faceid, 120, 120);
			playersFace[i].setLocation(200+i*168, 694);
			playersCash[i].text = String.valueOf(setdata.playersCash[i]);
			playersCash[i].setColor(Color.WHITE);
			playersCash[i].setAlign(Alignment.ALIGN_CENTER);
			//playersCash[i].setLocation(244+i*168, 828);
			playersCash[i].setSize(26);
			playersAchive[i].text = String.valueOf(setdata.achievementPoint[i]);
			playersAchive[i].setColor(Color.BLACK);
			playersAchive[i].alpha = 0.5f;
			playersAchive[i].setAlign(Alignment.ALIGN_OPPOSITE);
			//playersAchive[i].setLocation(128+i*168, 864);
		}
		time.text = setdata.saveTime.toString();
		
	}
	public void close(){
		isCloseing = true;
	}
	@Override
	public void dispose() {
		time.dispose();
		for(int i = 0; i < 4; i++){
			playersCash[i].dispose();
			playersAchive[i].dispose();
		}

	}

	@Override
	public void resume() {
		time.resume();
		for(int i = 0; i < 4; i++){
			playersCash[i].resume();
			playersAchive[i].resume();
		}

	}


}
