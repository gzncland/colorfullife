package com.projectd.colorfullife.view;

import android.graphics.Color;
import android.graphics.RectF;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowJobSelect extends WindowBase {
	public boolean isOpening;
	Sprite2D windowTitle;
	Sprite2D bg;
	TextField eventTitle;
	TextField eventContent;
	public SpriteButton[] btnSelections = new SpriteButton[3];
	public int selectIndex = -1;
	
	TextField[] selectionTitles = new TextField[3];
	TextField[] selectionInfos = new TextField[3];
	Sprite2D[] selectionSprites = new Sprite2D[3];

	public int[] careerId = new int[3];
	
	public WindowJobSelect() {

		windowTitle = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),
	    		0,368,640,144
			);
		windowTitle.setLocation(192, 18);
			
		bg = new Sprite2D(CacheManager.black.texture,
				new RectF(0, 0, 1024, 512),
				new RectF(0, 0, 1, 1)
		);
		bg.alpha = 0.8f;
		
		 eventTitle = new TextField("", 512, 64);
		 eventTitle.setColor(Color.BLACK);
		 eventTitle.setSize(28);
		 eventTitle.setLocation(232, 40);
		 eventContent = new TextField("请选择一个职业开始你的职业生涯", 512, 32);
		 eventContent.setColor(0xff424e52);
		 eventContent.setSize(24);
		 eventContent.setLocation(232, 80);
		 
		 /*
		 selectionATitle = new TextField("A", 256, 32);
		 selectionATitle.setAlign(Alignment.ALIGN_CENTER);
		 selectionATitle.setColor(Color.BLACK);
		 selectionATitle.setSize(22);
		 selectionATitle.setLocation(232, 320);
		 
		 selectionAInfo = new TextField("A", 512, 32);
		 selectionAInfo.setAlign(Alignment.ALIGN_CENTER);
		 selectionAInfo.setColor(0xff424e52);
		 selectionAInfo.setSize(16);
		 selectionAInfo.setLocation(168, 352);
		 
		 selectionASprite = CacheManager.getCareerIcoById(1, 128, 128);
		 selectionASprite.setLocation(232, 192);
		 
		 btnSelection[0] = new SpriteButton(
				 CacheManager.getTextureById(R.drawable.ui_game), 
				 800,32,208,256
			);
		 btnSelection[0].setLocation(192, 160);
		 
		 selectionBTitle = new TextField("B", 128, 32);
		 selectionBTitle.setAlign(Alignment.ALIGN_CENTER);
		 selectionBTitle.setColor(Color.BLACK);
		 selectionBTitle.setSize(22);
		 selectionBTitle.setLocation(448, 320);
		 
		 
		 selectionBInfo = new TextField("B", 256, 32);
		 selectionBInfo.setAlign(Alignment.ALIGN_CENTER);
		 selectionBInfo.setColor(0xff424e52);
		 selectionBInfo.setSize(16);
		 selectionBInfo.setLocation(384, 352);
		 
		 selectionBSprite = CacheManager.getCareerIcoById(1, 128, 128);
		 selectionBSprite.setLocation(448, 192);
		 
		 btnSelection[1] = new SpriteButton(
				 CacheManager.getTextureById(R.drawable.ui_game), 
				 800,32,208,256
			);
		 btnSelection[1].setLocation(408, 160);
		 
		 selectionCTitle = new TextField("C", 128, 32);
		 selectionCTitle.setAlign(Alignment.ALIGN_CENTER);
		 selectionCTitle.setColor(Color.BLACK);
		 selectionCTitle.setSize(22);
		 selectionCTitle.setLocation(664, 320);
		 
		 selectionCInfo = new TextField("C", 256, 32);
		 selectionCInfo.setAlign(Alignment.ALIGN_CENTER);
		 selectionCInfo.setColor(0xff424e52);
		 selectionCInfo.setSize(16);
		 selectionCInfo.setLocation(598, 352);
		 
		 selectionCSprite = CacheManager.getCareerIcoById(1, 128, 128);
		 selectionCSprite.setLocation(664, 192);
		 
		 btnSelection[2] = new SpriteButton(
				 CacheManager.getTextureById(R.drawable.ui_game), 
				 800,32,208,256
			);
		 btnSelection[2].setLocation(624, 160);
		 */
		 for (int i = 0; i < 3; i++) {
			 TextField selectionTitle = new TextField("", 256, 32);
			 selectionTitle.setColor(Color.BLACK);
			 selectionTitle.setSize(26);
			 selectionTitle.setLocation(256, 192 + i * 112);
			 selectionTitles[i] = selectionTitle;
			 
			 TextField selectionInfo = new TextField("", 512, 32);
			 selectionInfo.setColor(0xff424e52);
			 selectionInfo.setSize(24);			 
			 selectionInfo.setLocation(256, 224 + i * 112);
			 selectionInfos[i] = selectionInfo;
			 
			 Sprite2D selectionIco = CacheManager.getCareerIcoById(0, 64, 64);
			 selectionIco.setLocation(256, 192 + i * 112);
			 selectionSprites[i] = selectionIco;
			 
			 SpriteButton btnSelection = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),  0,512,560,112);
			 btnSelections[i] = btnSelection;
			 btnSelections[i].setLocation(228,168 + i * 112);
		 }
	}
	
	public void onDrawFrame() {
		if(isOpening == false){
			return;
		}
		SpriteBatch.draw(bg);

		SpriteBatch.draw(windowTitle);
		
		for (int i = 0; i < 3; i++) {		
			SpriteBatch.draw(btnSelections[i]);
			//SpriteBatch.draw(selectionSprites[i]);
			SpriteBatch.drawText(selectionTitles[i]);
			SpriteBatch.drawText(selectionInfos[i]);
		}
		
		SpriteBatch.drawText(eventTitle);
		SpriteBatch.drawText(eventContent);
		
	}
	@Override
	public void dispose() {
		eventTitle.dispose();
		eventContent.dispose();
		for (int i = 0; i < 3; i++) {
			selectionTitles[i].dispose();
			selectionInfos[i].dispose();
		}
	}

	@Override
	public void resume() {
		eventTitle.resume();
		eventContent.resume();
		for (int i = 0; i < 3; i++) {
			selectionTitles[i].resume();
			selectionInfos[i].resume();
		}
	}

	public void open(GamePlayer setPlayer) {

		eventTitle.text = setPlayer.characterData.name + "顺利地从大学毕业了";
		eventContent.text = "请选择一个职业开始你的职业生涯时期.";
		//	eventTitle.text = setPlayer.characterData.name + "很遗憾你的学分不足,没有毕业";

		isOpening = true;
		for (int i = 0; i < 3; i++) {
			int value = GameManager.seed.nextInt(8);
			careerId[i] = value;
			selectionTitles[i].text = DataManager.careers.get(value).name;
			selectionInfos[i].text = DataManager.careers.get(value).info;
			int x = DataManager.careers.get(value).spid % 4;
			int y = DataManager.careers.get(value).spid / 4;
			selectionSprites[i].setSrcRectLocation(x * 128, y * 128);
		}
	}

}
