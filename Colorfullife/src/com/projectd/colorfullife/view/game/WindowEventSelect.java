package com.projectd.colorfullife.view.game;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.data.DataEvent;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowEventSelect extends WindowBase {
	Sprite2D bg;
	
	Sprite2D eventBg;
	TextField eventTitle;
	TextField eventContent;

	public int selectIndex = -1;
	
	ArrayList<TextField> selectionTitles = new ArrayList<TextField>();
	ArrayList<TextField> selectionInfos = new ArrayList<TextField>();
	ArrayList<Sprite2D> selectionIcos = new ArrayList<Sprite2D>();
	public ArrayList<SpriteButton> btnSelections = new ArrayList<SpriteButton>();
	
	public WindowEventSelect(){
		eventBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),
	    		0,368,640,144
			);
		eventBg.setLocation(192, 18);
			
		bg = new Sprite2D(CacheManager.black.texture,
				new RectF(0, 0, 1024, 512),
				new RectF(0, 0, 1, 1)
		);
		bg.alpha = 0.8f;
		
		 eventTitle = new TextField("", 512, 64);
		 eventTitle.setColor(Color.BLACK);
		 eventTitle.setSize(28);
		 eventTitle.setLocation(232, 40);

		 eventContent = new TextField("", 512,32);
		 eventContent.setColor(0xff424e52);
		 eventContent.setLocation(232, 96);
		 eventContent.setSize(24);
		 
		 for (int i = 0; i < 3; i++) {
			 TextField selectionTitle = new TextField("", 512, 64);
			 selectionTitle.setColor(Color.BLACK);
			 selectionTitle.setSize(28);
			 selectionTitle.setLocation(256, 180 + i * 112);
			 selectionTitles.add(selectionTitle);
			 
			 TextField selectionInfo = new TextField("", 512, 32);
			 selectionInfo.setColor(0xff424e52);
			 selectionInfo.setSize(24);			 
			 selectionInfo.setLocation(256, 224 + i * 112);
			 selectionInfos.add(selectionInfo);
			 
			 Sprite2D selectionIco = CacheManager.getEventIcoById(0, 64, 64);
			 selectionIco.setLocation(256, 192 + i * 112);
			 selectionIcos.add(selectionIco);
			 
			 SpriteButton btnSelection = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),  0,512,560,112);
			 btnSelections.add(btnSelection);
			 btnSelections.get(i).setLocation(228,168 + i * 112);
		 }
		 
	}
	
	public void open(DataEvent setDataEvent) {
		isOpening = true;
		selectIndex = -1;
		eventTitle.text = setDataEvent.name;
		eventContent.text = setDataEvent.info;
		
		for (int i = 0; i < 3; i++) {
			if(setDataEvent.selectionEvents.get(i).isSelectable(GameManager.getActivePlayer())){
				btnSelections.get(i).active = true;
				btnSelections.get(i).alpha = 1f;
				selectionIcos.get(i).alpha = 1f;
				
			}else{
				btnSelections.get(i).active = false;
				btnSelections.get(i).alpha = 0.5f;
				selectionIcos.get(i).alpha = 0.5f;
			}
			selectionTitles.get(i).text = setDataEvent.selectionEvents.get(i).name;
			selectionInfos.get(i).text = setDataEvent.selectionEvents.get(i).info;
			int x = setDataEvent.selectionEvents.get(i).spId % 16;
			int y = setDataEvent.selectionEvents.get(i).spId / 16;
			selectionIcos.get(i).setSrcRectLocation(x * 128, y * 128);
		}
	}
	
	@Override
	public void dispose() {
		eventTitle.dispose();
		eventContent.dispose();
		for (TextField info : selectionInfos) {
			info.dispose();
		}
		for (TextField title : selectionTitles) {
			title.dispose();
		}
	}

	@Override
	public void resume() {
		eventTitle.resume();
		eventContent.resume();
		for (TextField info : selectionInfos) {
			info.resume();
		}
		for (TextField title : selectionTitles) {
			title.resume();
		}
	}


	public void onDrawFrame() {
		if(isOpening == false){
			return;
		}
		SpriteBatch.draw(bg);
		SpriteBatch.draw(eventBg);
		SpriteBatch.drawText(eventTitle);
		SpriteBatch.drawText(eventContent);
		
		
		for (SpriteButton sp : btnSelections) {
			SpriteBatch.draw(sp);
		}
		for (TextField info : selectionInfos) {
			SpriteBatch.drawText(info);
		}
		for (TextField title : selectionTitles) {
			SpriteBatch.drawText(title);
		}
//		for (Sprite2D sp : selectionIcos) {
//			SpriteBatch.draw(sp);
//		}
	}



}
