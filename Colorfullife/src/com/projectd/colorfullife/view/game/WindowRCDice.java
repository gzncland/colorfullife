package com.projectd.colorfullife.view.game;

import java.util.ArrayList;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class WindowRCDice extends WindowBase {
	/**投骰子按钮*/
	public SpriteButton btnGo;
	
	public ArrayList<SpriteButton> btnDices = new ArrayList<SpriteButton>();
	
	/**背景*/
	Sprite2D bg;
	
	/**[不可见]关闭按钮*/
	public SpriteButton btnClose;
	
	/**正在隐藏标志*/
	public boolean isHidding;
	
	public WindowRCDice(){
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_rc_dice),0,0,1024,512);
		bg.setLocation(0, 512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnClose.setLocation(0, 512);
		
		btnGo = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),272,0,144,80);
	    btnGo.setLocation(720,432);
	    
	    for (int i = 0; i < 6; i++) {
	    	SpriteButton btnDice = new SpriteButton(CacheManager.black.texture,
				new RectF(0,0,160,160),
				new RectF(0,0,1,1)
			);
	    	int x = 216 + i % 3 * 206;
	    	int y = 144 + (i / 3) * 192 + 512;
	    	btnDice.setLocation(x, y);
	    	btnDices.add(btnDice);
	    	btnDice.alpha = 0.5f;
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
		isHidding = false;
		isOpening = true;
		isCloseing = false;
	}
	/**关闭窗口*/
	public void close(){
		isCloseing = true;
	}
	/**整体偏移*/
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnGo.offset(setX, setY);
		btnClose.offset(setX, setY);
		for (SpriteButton dice : btnDices) {
			dice.offset(setX, setY);
		}
	}
	/**整体设置坐标*/
	public void setLocation(int setX,int setY) {
		bg.setLocation(setX, setY);
		btnGo.setLocation(setX, setY);
		btnClose.setLocation(setX, setY);
		for (SpriteButton dice : btnDices) {
			dice.setLocation(setX, setY);
		}
	}
	/**释放资源*/
	@Override
	public void dispose() {
		
	}
	/**从后台恢复*/
	@Override
	public void resume() {
		
	}
	/**重绘*/
	public void onDrawFrame(){
		SpriteBatch.draw(btnGo);
		if(isOpening){
			SpriteBatch.draw(bg);
		}
//		for (SpriteButton diceButton : btnDices) {
//			SpriteBatch.draw(diceButton);
//		}
	}

}
