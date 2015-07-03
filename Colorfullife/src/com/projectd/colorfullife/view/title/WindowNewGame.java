package com.projectd.colorfullife.view.title;

import android.graphics.Color;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowNewGame extends WindowBase {

	public Sprite2D bg; 
	
	
	public SpriteButton [] btnCharacters = new SpriteButton[6];
	public SpriteButton btnCancel;
	public SpriteButton btnConfirm;
	public SpriteButton btnStarGame;
	public SpriteButton btnPlayerSwitcher;
	public SpriteButton btnNewGame;
	public SpriteButton btnExit;
	
    public Sprite2D selectPlayerBg;
	Sprite2D [] selectPlayer = new Sprite2D [4];
	Sprite2D players;
	
	public TextField playerName;
	public TextField playerInfo;
	public TextField playernote;
	public TextField [] selectplayer = new TextField[4];
	
	public boolean[] aiSelect = new boolean[4];

	public boolean isHidding;

	int oldIndex = -1;
	public int selectIndex;
	public WindowNewGame(){
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_bg), 0, 0, 1024, 512);
		bg.setLocation(0,512);
		players = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),0,0,672,112);
		players.setLocation(176, 640);
		selectPlayerBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),0,192,128,128);
		selectPlayerBg.setLocation(166,632);
		
		btnCancel = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),0,112,144,80);
		btnCancel.setLocation(192, 944);
		btnCancel.active = false;
		btnConfirm = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),288,112,144,80);
		btnConfirm.setLocation(704, 944);
		btnExit = new SpriteButton(CacheManager.black.texture,0,0,1024,96);
		btnExit.setLocation(0, 512);
		btnExit.alpha = 0;
		btnNewGame = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),576,112,144,80);
		btnNewGame.setLocation(400, 432);
		btnPlayerSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),816,0,144,48);
		btnPlayerSwitcher.setLocation(668, 866);
		btnStarGame = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),864,112,144,80);
		btnStarGame.setLocation(704, 944);
		btnStarGame.active = false;
		
		playerName = new TextField(DataManager.characters.get(0).name, 512, 32);
		playerName.setLocation(192,832);
		playerName.setColor(Color.BLACK);
		playerName.setSize(26);
		playerInfo = new TextField(DataManager.characters.get(0).info, 512, 128);
		playerInfo.setLocation(208, 872);
		playerInfo.setColor(Color.BLACK);
		playernote = new TextField(DataManager.characters.get(0).note, 512, 32);
		playernote.setLocation(208, 912);
		playernote.setColor(Color.BLUE);
		
		for(int i =0; i < 6; i++){
			btnCharacters[i] = new SpriteButton(CacheManager.black.texture,0,0,112,112);
			btnCharacters[i].setLocation(176 + i*112, 640);
			btnCharacters[i].alpha=0;
		}
		
		for(int i =0; i < 4; i++){
			selectplayer[i] = new TextField("玩家", 64, 32);
			selectplayer[i].setAlign(Alignment.ALIGN_CENTER);
			selectplayer[i].setColor(Color.BLACK);
		}

	}
	
	public void update(float deltaTime){
		if(btnStarGame.active){
			playerInfo.text= "点击[开始游戏]开始你多彩的人生吧!";
			oldIndex = -1;
		}else {
			if(oldIndex != selectIndex){
				oldIndex = selectIndex;
				playerName.text= DataManager.characters.get(selectIndex).name;
				playerInfo.text= DataManager.characters.get(selectIndex).info;
				playernote.text= DataManager.characters.get(selectIndex).note;
			}
		}
		
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
				}else{
					selectPlayerBg.setLocation(166 + selectIndex * 112, 120);
				}
			}else if(bg.getY() > 512){
				offset(0, -32);
			}
			
		}
		
	}
	public void onDrawFrame() {
		SpriteBatch.draw(btnNewGame);
		if(isOpening){
			SpriteBatch.draw(bg);
			if(btnCancel.active){
				SpriteBatch.draw(btnCancel);
			}
			if(btnConfirm.active){
				SpriteBatch.draw(btnConfirm);
			}
			if(btnStarGame.active){
				SpriteBatch.draw(btnStarGame);
				
			}else{
				SpriteBatch.draw(selectPlayerBg);
				SpriteBatch.drawText(playerName);
				
				SpriteBatch.drawText(playernote);
				SpriteBatch.draw(btnPlayerSwitcher);
			}		
			SpriteBatch.drawText(playerInfo);
			SpriteBatch.draw(players);
			SpriteBatch.draw(btnExit);
			
			for(int i =0; i < 6; i++){
				SpriteBatch.draw(btnCharacters[i]);
			}
			for(int i =0; i < 4; i++){
				SpriteBatch.drawText(selectplayer[i]);
			}
		}
		
	}
	
	public void open(){
		isOpening = true;
		isCloseing = false;
	}
	public void close(){
		isCloseing = true;
	}
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnCancel.offset(setX, setY);
		btnConfirm.offset(setX, setY);
		btnExit.offset(setX, setY);
		players.offset(setX, setY);
		btnNewGame.offset(setX, setY);
		playerName.offset(setX, setY);
		playerInfo.offset(setX, setY);
		playernote.offset(setX, setY);
		btnPlayerSwitcher.offset(setX, setY);
		selectPlayerBg.offset(setX, setY);
		btnStarGame.offset(setX, setY);
		for(int i = 0; i < 6;i++){
			btnCharacters[i].offset(setX, setY);
		}
		for(int i =0; i < 4; i++){
			selectplayer[i].offset(setX, setY);
		}
	}
	@Override
	public void dispose() {
		playerInfo.dispose();
		playerName.dispose();
		playernote.dispose();
		for(int i =0; i < 4; i++){
			selectplayer[i].dispose();
		}

	}

	@Override
	public void resume() {
		playerInfo.resume();
		playerName.resume();
		playernote.resume();
		for(int i =0; i < 4; i++){
			selectplayer[i].resume();
		}

	}

}
