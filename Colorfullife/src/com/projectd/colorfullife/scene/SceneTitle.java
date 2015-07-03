package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;


import android.view.MotionEvent;

import com.projectd.colorfullife.GameManager.GameState;
import com.projectd.colorfullife.EventManager;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.FileManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SoundManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.view.BgAnimation;
import com.projectd.colorfullife.view.title.WindowNewGame;
import com.projectd.colorfullife.view.title.WindowRules;
import com.projectd.colorfullife.view.title.WindowTitleLoad;
import com.projectd.colorfullife.view.title.WindowTitleOption;
import com.projectd.framework.CacheBase;
import com.projectd.framework.SceneBase;
import com.projectd.framework.SystemBase;

public class SceneTitle extends SceneBase {

	BgAnimation bg;
	

	
	WindowRules rulesWindow;
	WindowNewGame newGameWindow;
	WindowTitleOption titleOptionWindow;
	WindowTitleLoad titleLoadWindow;

	boolean newGameFlag;

	public int [] characterSelectIndex = new int[4];
	private int playerIndex = 0;
	float alphaCount = 0;



	private boolean loadGameFlag;
	

	public SceneTitle(){

		 bg = new BgAnimation(0);

		 rulesWindow = new WindowRules();

		 newGameWindow = new WindowNewGame(); 
		 titleOptionWindow = new WindowTitleOption();
		 titleLoadWindow = new WindowTitleLoad();

		 
		 
		 rulesWindow.isHidding = true;
		 
		 
	
	}
	@Override
	public void onDrawFrame() {       
        bg.onDrawFrame();

        newGameWindow.onDrawFrame();
        titleOptionWindow.onDrawFrame();
        titleLoadWindow.onDrawFrame();
        rulesWindow.onDrawFrame();
	}

	@Override
	public void update(float deltaTime) {
		bg.update(deltaTime);
		newGameWindow.selectPlayerBg.alpha = (float) Math.sin((alphaCount/180*Math.PI));
		alphaCount += SystemManager.deltaTimex100;
		if(alphaCount >= 160){
			alphaCount = 20;
		}
		newGameWindow.update(deltaTime);
		titleOptionWindow.update(deltaTime);
		titleLoadWindow.update(deltaTime);
		rulesWindow.update(deltaTime);
		
		while (deltaTime > 0) {
			deltaTime -= 0.011;
			if(newGameWindow.isOpening){
				if(newGameFlag){
					newGameFlag = false;
					newGameWindow.isCloseing = true;
					newGameWindow.isHidding = true;
					titleOptionWindow.isHidding = true;
					titleLoadWindow.isHidding = true;
					GameManager.initializeGame(characterSelectIndex);
					for (int j = 0; j < 4; j++) {
						GameManager.players.get(j).isAi = newGameWindow.aiSelect[j];
					}
					//SystemBase.newScene = new SceneGraduate();
					SystemBase.newScene = new SceneStudentEra();
				}
			}else if (titleLoadWindow.isOpening) {
				if(loadGameFlag){
					loadGameFlag = false;
					bg.isFinish = true;
					newGameWindow.isCloseing = true;
					newGameWindow.isHidding = true;
					titleOptionWindow.isHidding = true;
					titleLoadWindow.isHidding = true;
					GameManager.loadFromDataArchive(FileManager.loadGame());
					
					switch (GameManager.age) {
					case 0:
						SystemBase.newScene = new SceneStudentEra();
						break;
					case 1:
						SystemBase.newScene = new SceneAdultEra();
						break;
					case 2:
						SystemBase.newScene = new SceneOldEra();
						break;
					}
					GameManager.state = GameState.GAME_LOADED;
				}
			}
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void dispose(GL11 gl) {
		newGameWindow.dispose();
		titleLoadWindow.dispose();
	}

	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {
		

	}

	@Override
	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		if(rulesWindow.btnClose.isPressed(touchX, touchY)){
			SoundManager.playSystemSE(R.raw.close);
			rulesWindow.close();
			newGameWindow.isHidding = false;
			return;
		}
		if(newGameWindow.btnExit.isPressed(touchX, touchY)){
			SoundManager.playSystemSE(R.raw.close);
			newGameWindow.close();
			titleOptionWindow.isHidding = false;
			titleLoadWindow.isHidding = false;
			rulesWindow.isHidding = true;
		}
		if(newGameWindow.btnNewGame.isPressed(touchX, touchY)){
			//newPlayerWindow.isOpening = true;
			SoundManager.playSystemSE(R.raw.open);
			newGameWindow.open();
			titleOptionWindow.isHidding = true;
			titleLoadWindow.isHidding = true;
			rulesWindow.isHidding = false;
		}
		if(titleOptionWindow.btnClose.isPressed(touchX, touchY)){
			titleOptionWindow.close();
			SoundManager.playSystemSE(R.raw.close);
			newGameWindow.isHidding = false;
			titleLoadWindow.isHidding = false;
		}
		if(titleOptionWindow.btnOption.isPressed(touchX, touchY)){
			titleOptionWindow.open();
			SoundManager.playSystemSE(R.raw.open);
			newGameWindow.isHidding = true;
			titleLoadWindow.isHidding = true;
		}
		if(titleLoadWindow.btnClose.isPressed(touchX, touchY)){
			titleLoadWindow.close();
			SoundManager.playSystemSE(R.raw.close);
			newGameWindow.isHidding = false;
			titleOptionWindow.isHidding = false;
		}
		if(titleLoadWindow.btnLoad.isPressed(touchX, touchY)){
			if(titleLoadWindow.setdata == null){
				SoundManager.playSystemSE(R.raw.freeze);
				return;
			}
			titleLoadWindow.open();
			SoundManager.playSystemSE(R.raw.open);
			newGameWindow.isHidding = true;
			titleOptionWindow.isHidding = true;
		}
		
		//主菜单
		if(titleOptionWindow.isOpening){
			if(titleOptionWindow.btnBGMSwitcher.isPressed(touchX, touchY)){
				if(SoundManager.bgmIsMute){
					SoundManager.mp.setVolume(1, 1);
					SoundManager.bgmIsMute = false;
					titleOptionWindow.btnBGMSwitcher.setSrcRectLocation(816, 32);
				}else{
					SoundManager.mp.setVolume(0, 0);
					SoundManager.bgmIsMute = true;
					titleOptionWindow.btnBGMSwitcher.setSrcRectLocation(912, 32);
				}
				SoundManager.playSystemSE(R.raw.switcher);
				FileManager.saveOptions();
				return;
			}
			if(titleOptionWindow.btnSESwitcher.isPressed(touchX, touchY)){
				if(SoundManager.seIsMute){
					SoundManager.seIsMute = false;
					titleOptionWindow.btnSESwitcher.setSrcRectLocation(816, 32);
				}else{
					titleOptionWindow.btnSESwitcher.setSrcRectLocation(912, 32);
					SoundManager.seIsMute = true;
					
				}
				SoundManager.playSystemSE(R.raw.switcher);
				FileManager.saveOptions();
				return;
			}
			if(titleOptionWindow.btnDitherSwitcher.isPressed(touchX, touchY)){
				if(SystemManager.isDisableDither){
					SystemManager.isDisableDither = false;
					CacheBase.gl.glEnable(GL11.GL_DITHER);
					titleOptionWindow.btnDitherSwitcher.setSrcRectLocation(816, 32);
				}else{
					SystemManager.isDisableDither = true;
					CacheBase.gl.glDisable(GL11.GL_DITHER);
					titleOptionWindow.btnDitherSwitcher.setSrcRectLocation(912, 32);
				}
				SoundManager.playSystemSE(R.raw.switcher);
				FileManager.saveOptions();
				return;
			}
			if(titleOptionWindow.btnDialogSwitcher.isPressed(touchX, touchY)){
				if(SystemManager.isShowDialog){
					titleOptionWindow.btnDialogSwitcher.setSrcRectLocation(912, 32);
					SystemManager.isShowDialog = false;
				}else {
					titleOptionWindow.btnDialogSwitcher.setSrcRectLocation(816, 32);
					SystemManager.isShowDialog = true;
				}		
				SoundManager.playSystemSE(R.raw.switcher);	
				FileManager.saveOptions();
				return;
			}
			
		}
		//继续游戏
		if(titleLoadWindow.isOpening){
			if (titleLoadWindow.btnConfirm.isPressed(touchX, touchY)) {	
				SoundManager.playSystemSE(R.raw.close);	
				loadGameFlag = true;
			}
		}
		//新游戏
		if(newGameWindow.isOpening){
			if(rulesWindow.btnViewRules.isPressed(touchX, touchY)){
				rulesWindow.open();
				SoundManager.playSystemSE(R.raw.open);
				newGameWindow.isHidding = true;
			}
			
			if(playerIndex < 4){
				for (int i = 0; i < 6; i++){			
					if(newGameWindow.btnCharacters[i].isPressed(touchX, touchY)){
							newGameWindow.selectIndex = i;
							SoundManager.playSystemSE(R.raw.switcher);
					}
				}
				if(newGameWindow.btnPlayerSwitcher.isPressed(touchX, touchY)){
					if(newGameWindow.aiSelect[playerIndex]){
						newGameWindow.btnPlayerSwitcher.setSrcRectLocation(816, 0);
						newGameWindow.aiSelect[playerIndex] = false;
						SoundManager.playSystemSE(R.raw.switcher);
					}else{
						newGameWindow.btnPlayerSwitcher.setSrcRectLocation(672,0);
						newGameWindow.aiSelect[playerIndex] = true;
						SoundManager.playSystemSE(R.raw.switcher);
					}
				}
			}
			if(newGameWindow.btnCancel.isPressed(touchX, touchY)){
				newGameWindow.btnStarGame.active = false;
				newGameWindow.btnConfirm.active = true;
				playerIndex -= 1;
				newGameWindow.selectIndex = characterSelectIndex[playerIndex];
				newGameWindow.btnCharacters[characterSelectIndex[playerIndex]].active = true;
			    newGameWindow.selectplayer[playerIndex].alpha = 0;
			    //判断上一位玩家是否为AI
			    if(newGameWindow.aiSelect[playerIndex]){
					newGameWindow.btnPlayerSwitcher.setSrcRectLocation(672, 0);
				}else{
					newGameWindow.btnPlayerSwitcher.setSrcRectLocation(816, 0);
				}
			    if(playerIndex == 0){
			    	newGameWindow.btnCancel.active = false;
			    }else{
					newGameWindow.btnConfirm.active = true;
				}
			    SoundManager.playSystemSE(R.raw.cancel);
			}else if (newGameWindow.btnConfirm.isPressed(touchX, touchY)) {
				SoundManager.playSystemSE(R.raw.confirm);
				if(playerIndex == 0){
					newGameWindow.btnCancel.active = true;
				}
				characterSelectIndex[playerIndex] = newGameWindow.selectIndex;
				//激活下一玩家的电脑控制开关
				if(playerIndex < 3){
					newGameWindow.btnPlayerSwitcher.setSrcRectLocation(672, 0);
					newGameWindow.aiSelect[playerIndex+1] = true;
				}
				//显示玩家编号
				if(playerIndex < 4){
					newGameWindow.selectplayer[playerIndex].setLocation(200 + 112 * characterSelectIndex[playerIndex], 240);
					newGameWindow.selectplayer[playerIndex].text ="玩家" + String.valueOf(playerIndex + 1);
					newGameWindow.selectplayer[playerIndex].alpha = 1;
					newGameWindow.btnCharacters[characterSelectIndex[playerIndex]].active = false;
					for (int i = 0; i < 6; i++) {
						if(newGameWindow.btnCharacters[i].active){
							newGameWindow.selectIndex = i;
							break;
						}
					}
				}
				playerIndex += 1;
				if(playerIndex == 4){
					newGameWindow.btnConfirm.active = false;
					newGameWindow.btnStarGame.active = true;
				}
			}else if (newGameWindow.btnStarGame.isPressed(touchX, touchY)) {
				newGameWindow.close();
				SoundManager.playSystemSE(R.raw.close);
				rulesWindow.isHidding = true;
				newGameFlag = true;
				bg.isFinish = true;
			}
			return;
		}
	
	}

	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {
		
	}

	@Override
	public void resume() {
		//newPlayerWindow.resume();
		newGameWindow.resume();
		titleLoadWindow.resume();
	}
	@Override
	public void touchUpdatePointerDown(MotionEvent e) {
		
		
	}
	@Override
	public void touchUpdatePointerUp(MotionEvent e) {
		
		
	}
	@Override
	public void updateOnce() {
		SoundManager.playBGM(R.raw.colorfulliffe, true);
	}

}
