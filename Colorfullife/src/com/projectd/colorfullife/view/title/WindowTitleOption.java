package com.projectd.colorfullife.view.title;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SoundManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class WindowTitleOption extends WindowBase {
	/**道具栏按钮*/
	public SpriteButton btnOption;
	
	/**背景*/
	Sprite2D bg;
	
	/**[不可见]关闭按钮*/
	public SpriteButton btnClose;
	
	/**BGM按钮*/
	public SpriteButton btnBGMSwitcher;
	/**SE按钮*/
	public SpriteButton btnSESwitcher;
	/**DITHER按钮*/
	public SpriteButton btnDialogSwitcher;
	/**DITHER按钮*/
	public SpriteButton btnDitherSwitcher;
	/**正在隐藏标志*/
	public boolean isHidding;
	
	/**主菜单窗口*/
	public WindowTitleOption() {
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_options),0,0,1024,512);
		bg.setLocation(0, 512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnClose.setLocation(0, 512);
		btnOption = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),720,112,144,80);
        btnOption.setLocation(720,432);
		
		btnBGMSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnBGMSwitcher.setLocation(752,656);
		
		btnSESwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnSESwitcher.setLocation(752,752);
		
		btnDitherSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnDitherSwitcher.setLocation(752,848);
		
		btnDialogSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnDialogSwitcher.setLocation(752,948);

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
		btnOption.offset(setX, setY);
		btnClose.offset(setX, setY);
		btnSESwitcher.offset(setX, setY);
		btnBGMSwitcher.offset(setX, setY);
		btnDitherSwitcher.offset(setX, setY);
		btnDialogSwitcher.offset(setX, setY);
	}
	/**打开窗口*/
	public void open(){
		isOpening = true;
		isCloseing = false;
		if(SoundManager.bgmIsMute == false){
			btnBGMSwitcher.setSrcRectLocation(816, 32);
		}else{
			btnBGMSwitcher.setSrcRectLocation(912, 32);
		}
		if(SoundManager.seIsMute == false){
			btnSESwitcher.setSrcRectLocation(816, 32);
		}else{
			btnSESwitcher.setSrcRectLocation(912, 32);
		}
		if(SystemManager.isDisableDither == false){
			btnDitherSwitcher.setSrcRectLocation(816, 32);
		}else{
			btnDitherSwitcher.setSrcRectLocation(912, 32);
		}
		if(SystemManager.isShowDialog){
			btnDialogSwitcher.setSrcRectLocation(816, 32);
		}else{
			btnDialogSwitcher.setSrcRectLocation(912, 32);
		}
	}
	public void close(){
		isCloseing = true;
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
		SpriteBatch.draw(btnOption);
		if(isOpening){
			SpriteBatch.draw(bg);
			SpriteBatch.draw(btnSESwitcher);
			SpriteBatch.draw(btnBGMSwitcher);
			SpriteBatch.draw(btnDitherSwitcher);
			SpriteBatch.draw(btnDialogSwitcher);
		}
	}
}
