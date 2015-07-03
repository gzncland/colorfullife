package com.projectd.colorfullife.view.game;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SoundManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class WindowMenu extends WindowBase {
	/**��������ť*/
	public SpriteButton btnMenu;
	
	/**����*/
	Sprite2D bg;
	
	/**[���ɼ�]�رհ�ť*/
	public SpriteButton btnClose;
	
	/**[���ɼ�]���ر��ⰴť*/
	public SpriteButton btnReturnToTitle;
	
	/**BGM��ť*/
	public SpriteButton btnBGMSwitcher;
	/**SE��ť*/
	public SpriteButton btnSESwitcher;
	/**DITHER��ť*/
	public SpriteButton btnDialogSwitcher;
	/**DITHER��ť*/
	public SpriteButton btnDitherSwitcher;
	/**�������ر�־*/
	public boolean isHidding;
	
	/**���˵�����*/
	public WindowMenu() {
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_menu),0,0,1024,512);
		bg.setLocation(0, -512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnClose.setLocation(0, -96);
		btnMenu = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),416,0,144,64);
        btnMenu.setLocation(720,0);
		
		btnBGMSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnBGMSwitcher.setLocation(752,-400);
		
		btnSESwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnSESwitcher.setLocation(752,-304);
		
		btnDitherSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnDitherSwitcher.setLocation(752,-208);
		
		btnDialogSwitcher = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),816,32,96,48);
		btnDialogSwitcher.setLocation(752,-112);
		
		btnReturnToTitle = new SpriteButton(CacheManager.black.texture,0,0,112,48);
		btnReturnToTitle.setLocation(452, -512);
		btnReturnToTitle.alpha = 0;
		
	}
	
	/**����*/
	public void update(float deltaTime){
		int deltaValue = SystemManager.deltaTimex100;
		while (deltaValue  > 0) {
			deltaValue -= 1;
			if(isHidding){
				if(bg.getY() > -576){
					offset(0, -32);
				}
			}else if(isCloseing){
				if(bg.getY() > -512){
					offset(0, -32);
				}else{
					isOpening = false;
					isCloseing = false;
				}
			}else if(isOpening){
				if(bg.getY() < 0){
					offset(0, 32);
				}
			}else if(bg.getY() < -512){
				offset(0, 32);
			}
		}
	}
	/**�򿪴���*/
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
	/**�رմ���*/
	public void close(){
		isCloseing = true;
	}
	/**����ƫ��*/
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnMenu.offset(setX, setY);
		btnClose.offset(setX, setY);
		btnSESwitcher.offset(setX, setY);
		btnBGMSwitcher.offset(setX, setY);
		btnDitherSwitcher.offset(setX, setY);
		btnDialogSwitcher.offset(setX, setY);
		btnReturnToTitle.offset(setX, setY);
	}
	/**������������*/
	public void setLocation(int setX,int setY) {
		bg.setLocation(setX, setY);
		btnMenu.setLocation(setX, setY);
		btnClose.setLocation(setX, setY);
		btnSESwitcher.setLocation(setX, setY);
		btnBGMSwitcher.setLocation(setX, setY);
		btnDitherSwitcher.setLocation(setX, setY);
		btnDialogSwitcher.setLocation(setX, setY);
	}
	/**�ͷ���Դ*/
	@Override
	public void dispose() {
		
	}
	/**�Ӻ�̨�ָ�*/
	@Override
	public void resume() {
		

	}
	/**�ػ�*/
	public void onDrawFrame(){
		SpriteBatch.draw(btnMenu);
		if(isOpening){
			SpriteBatch.draw(bg);
			SpriteBatch.draw(btnSESwitcher);
			SpriteBatch.draw(btnBGMSwitcher);
			SpriteBatch.draw(btnDitherSwitcher);
			SpriteBatch.draw(btnDialogSwitcher);
			SpriteBatch.draw(btnReturnToTitle);
		}
	}
}
