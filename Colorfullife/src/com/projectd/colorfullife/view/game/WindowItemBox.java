package com.projectd.colorfullife.view.game;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowItemBox extends WindowBase {
	/**道具栏按钮*/
	public SpriteButton btnItemBox;
	public ArrayList<SpriteButton> btnSelects = new ArrayList<SpriteButton>();
	/**背景*/
	Sprite2D bg;
	
	/**[不可见]关闭按钮*/
	public SpriteButton btnClose;
	
	/**正在隐藏标志*/
	public boolean isHidding;
	
	/**道具栏窗口*/
	public TextField[] itemNumber = new TextField[6];
	public WindowItemBox() {
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_itembox),0,0,1024,512);
		bg.setLocation(0, 512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnClose.setLocation(0, 512);
		btnItemBox = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),128,0,144,80);
        btnItemBox.setLocation(160,432);
        
        for (int i = 0; i < 6; i++) {
	    	SpriteButton btnSelect = new SpriteButton(CacheManager.black.texture,
				new RectF(0,0,160,160),
				new RectF(0,0,1,1)
			);
	    	int x = 216 + i % 3 * 208;
	    	int y = 144 + (i / 3) * 192 + 512;
	    	btnSelect.setLocation(x, y);
	    	btnSelect.alpha = 0.5f;
	    	btnSelects.add(btnSelect);
	    	
			itemNumber[i] = new TextField("0",32,32);
			itemNumber[i].setLocation(x + 114,y + 120);
			itemNumber[i].setAlign(Alignment.ALIGN_CENTER);
			itemNumber[i].setSize(24);
			itemNumber[i].setColor(Color.BLACK);
		}
	}
	
	/**更新*/
	public void update(float deltaTime){
		int deltaValue = SystemManager.deltaTimex100;
		while (deltaValue  > 0) {
			deltaValue -= 1;
			if(isHidding){
				if(bg.getY() < 608){
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
				offset(0, -8);
			}
		}
		
	}
	/**打开窗口*/
	public void open(){
		isOpening = true;
		isCloseing = false;
		refresh();
	}
	/**关闭窗口*/
	public void close(){
		isCloseing = true;
	}
	/**整体偏移*/
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnItemBox.offset(setX, setY);
		btnClose.offset(setX, setY);
		for (SpriteButton btnSelect : btnSelects) {
			btnSelect.offset(setX, setY);
		}
		for(int i = 0; i < 6; i++){
			itemNumber[i].offset(setX,setY);
		}
	}
	public void refresh() {
		for(int i = 0; i < 6; i++){
			itemNumber[i].text = String.valueOf(GameManager.players.get(GameManager.activePlayerIndex).items[i]);
		}
	}
	/**整体设置坐标*/
	public void setLocation(int setX,int setY) {
		bg.setLocation(setX, setY);
		btnItemBox.setLocation(setX, setY);
		btnClose.setLocation(setX, setY);
		for (SpriteButton btnSelect : btnSelects) {
			btnSelect.setLocation(setX, setY);
		}
		for(int i = 0; i < 6; i++){
			itemNumber[i].setLocation(setX,setY);
		}
	}
	/**释放资源*/
	@Override
	public void dispose() {
		for(int i = 0;i < 6;i++){	
			itemNumber[i].dispose();
		}
	}
	/**从后台恢复*/
	@Override
	public void resume() {
		for(int i = 0;i < 6;i++){	
			itemNumber[i].resume();
		}

	}
	/**重绘*/
	public void onDrawFrame(){
		SpriteBatch.draw(btnItemBox);
		if(isOpening){
			SpriteBatch.draw(bg);
			for(int i = 0;i < 6;i++){	
				SpriteBatch.drawText(itemNumber[i]);
			}
		}
		
	}
}
