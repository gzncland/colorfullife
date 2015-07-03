package com.projectd.colorfullife.scene;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

import com.projectd.colorfullife.R;

import com.projectd.colorfullife.AIManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.EventManager;
import com.projectd.colorfullife.FileManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SoundManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.GameManager.GameState;
import com.projectd.colorfullife.view.game.SpritesetMap;
import com.projectd.colorfullife.view.game.WindowDialog;
import com.projectd.colorfullife.view.game.WindowDialogData;
import com.projectd.colorfullife.view.game.WindowEvent;
import com.projectd.colorfullife.view.game.WindowEventSelect;
import com.projectd.colorfullife.view.game.WindowGame;
import com.projectd.colorfullife.view.game.WindowItemBox;
import com.projectd.colorfullife.view.game.WindowItemInfo;
import com.projectd.colorfullife.view.game.WindowMenu;
import com.projectd.colorfullife.view.game.WindowMessage;
import com.projectd.colorfullife.view.game.WindowOppoments;
import com.projectd.colorfullife.view.game.WindowPlayerCard;
import com.projectd.colorfullife.view.game.WindowRCDice;
import com.projectd.colorfullife.view.game.WindowScoreBoard;
import com.projectd.colorfullife.view.game.WindowSelectOppoment;
import com.projectd.colorfullife.view.game.WindowDialogData.WindowDialogDisplayType;
import com.projectd.framework.CacheBase;
import com.projectd.framework.SceneBase;
import com.projectd.framework.SystemBase;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteDie;



public class SceneGame extends SceneBase {
	WindowScoreBoard scoreWindow;
	WindowGame gameWindow;
	WindowItemBox itemBoxWindow;
	WindowMenu menuWindow;
	WindowPlayerCard playerCardWindow;
	WindowRCDice diceWindow;
	WindowDialog dialogWindow;
	WindowOppoments oppomentsWindow;
	SpritesetMap spritesetMap;
	SpriteDie dice;
	WindowMessage messageWindow;
	WindowEvent eventWindow;
	WindowEventSelect selectWindow;
	WindowItemInfo itemBoxInfoWindow;
	WindowSelectOppoment selectOppomentWindow;
	
	
	
	public SceneGame(int setEra) {	
		itemBoxWindow = new WindowItemBox();
		gameWindow = new WindowGame();
		dialogWindow = new WindowDialog();
		scoreWindow = new WindowScoreBoard();
		menuWindow = new WindowMenu();
		playerCardWindow = new WindowPlayerCard();
		diceWindow = new WindowRCDice();
		spritesetMap = new SpritesetMap(setEra);
		eventWindow = new WindowEvent();
		selectWindow = new WindowEventSelect();
		oppomentsWindow = new WindowOppoments();
		itemBoxInfoWindow = new WindowItemInfo();
		messageWindow = new WindowMessage();
		selectOppomentWindow = new WindowSelectOppoment();
		dice = new SpriteDie();
		dice.setLocation(480, 352);
		EventManager.startGame();
		
	}
	@Override
	public void onDrawFrame() {
		spritesetMap.onDrawFrame();
		if(GameManager.state == GameState.TURN_DICE || GameManager.state == GameState.TURN_DIECD){
			SpriteBatch.draw(dice);
		}
		gameWindow.onDrawFrame();
		oppomentsWindow.onDrawFrame();
		playerCardWindow.onDrawFrame();
		
		itemBoxWindow.onDrawFrame();
		scoreWindow.onDrawFrame();
		menuWindow.onDrawFrame();
		diceWindow.onDrawFrame();
		dialogWindow.onDrawFrame();		
		eventWindow.onDrawFrame();
		selectWindow.onDrawFrame();
		itemBoxInfoWindow.onDrawFrame();
		selectOppomentWindow.onDrawFrame();
		messageWindow.onDrawFrame();
	}
	
	
	@Override
	public void update(float deltaTime) {
		messageWindow.update(deltaTime);
		itemBoxWindow.update(deltaTime);
		scoreWindow.update(deltaTime);
		menuWindow.update(deltaTime);
		
		if(messageWindow.isOpening){
			return;
		}
		if(GameManager.messages.size() > 0){
			messageWindow.open(GameManager.messages.remove(0));
		}
		dialogWindow.update(deltaTime);
		if(dialogWindow.isOpening){
			return;
		}
		
		eventWindow.update(deltaTime);
		if(eventWindow.isOpening){
			return;
		}
		
		diceWindow.update(deltaTime);
		
		if(selectOppomentWindow.isOpening){
			if(selectOppomentWindow.selectIndex != -1){
				//减少物品
				GameManager.getActivePlayer().items[1] -= 1;
				//关闭窗口
				selectOppomentWindow.isOpening = false;
				itemBoxInfoWindow.isOpening = false;
				itemBoxWindow.close();
				scoreWindow.isHidding = false;
				menuWindow.isHidding = false;
				diceWindow.isHidding = false;
				//换位
				int selIndex = selectOppomentWindow.selectIndex;
				int oppoIndex = GameManager.getActivePlayer().opponents.get(selIndex).mapIndex;
				GameManager.getActivePlayer().opponents.get(selIndex).mapIndex = GameManager.getActivePlayer().mapIndex;
				GameManager.getActivePlayer().mapIndex = oppoIndex;
				messageWindow.open("和" + GameManager.getActivePlayer().opponents.get(selIndex).characterData.name + "交换了位置");
				WindowDialogDisplayType displayType = WindowDialogDisplayType.OPPOMENT_A;
				switch (selIndex) {
				case 0:
					displayType = WindowDialogDisplayType.OPPOMENT_A;
					break;
				case 1:
					displayType = WindowDialogDisplayType.OPPOMENT_B;
					break;
				case 2:
					displayType = WindowDialogDisplayType.OPPOMENT_C;
					break;
				}
				dialogWindow.open("你干了什么?!", displayType);
				int index = GameManager.getActivePlayer().mapIndex;
				int newX = DataManager.tiles.get(index).mapX;
				int newY = DataManager.tiles.get(index).mapY;
				spritesetMap.moveCameraTo(newX, newY);
			}
		}
		
		//更新事件处理器
		EventManager.update(deltaTime);	
		
		
		//推送新对话
		if(GameManager.dialogs.size() > 0){
			WindowDialogData dialog = GameManager.dialogs.remove(0);
			spritesetMap.moveCameraTo(dialog.moveCameraX, dialog.moveCameraY);
			dialogWindow.open(dialog.dialogText,dialog.displayType);
			return;
		}

		//更新地图
		spritesetMap.update(deltaTime);
		//下一玩家回合判定
		//updateEventNextPlayer();

		switch (GameManager.state) {
		case GAME_LOADED:
			float a = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapX;
			float b = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapY;
			spritesetMap.moveCameraTo(a, b);
			oppomentsWindow.refresh(GameManager.getActivePlayer());
			playerCardWindow.refresh(GameManager.getActivePlayer());
			scoreWindow.refresh();
			EventManager.waitPlayer();
			break;
		case GAME_START:
			EventManager.startTurn();
			break;
		case TURN_START:
			updateTurnStart();
			break;
		case TURN_WAIT:
			if (EventManager.isRunning == false) {
				if(GameManager.getActivePlayer().isAi){
					EventManager.dice();
					return;
				}
			}
			break;
		case TURN_DICE:
			if (EventManager.isRunning == false) {
				dice.setIndex(GameManager.moveCount);
				EventManager.diecd(GameManager.moveCount + 1);
				GameManager.moveCount = -1;
			}else{
				float x = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapX;
				float y = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapY;
				spritesetMap.moveCameraTo(x, y);
				dice.update(deltaTime);
			}
			break;
		case TURN_DIECD:
			if (EventManager.isRunning == false) {
				EventManager.move();
			}
			break;
		case TURN_MOVE:
			float x = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapX;
			float y = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapY;
			spritesetMap.moveCameraTo(x, y);
			break;
		case TURN_MOVED:
			if (EventManager.isRunning == false) {
				EventManager.exeEvent();
				if(EventManager.activeEvent != null){
					if(EventManager.activeEvent.selectionEvents.size() > 0){
						EventManager.selectIndex = -1;
						if(GameManager.getActivePlayer().isAi){
							EventManager.selectIndex = AIManager.getSelection(GameManager.getActivePlayer(), EventManager.activeEvent.selectionEvents);
							GameManager.state = GameState.TURN_AI_SELECT;
						}else{
							SoundManager.playSystemSE(R.raw.open);
							selectWindow.open(EventManager.activeEvent);
							GameManager.state = GameState.TURN_SELECT;
						}		
					}else{			
						SoundManager.playSystemSE(R.raw.open);
						eventWindow.open(EventManager.spId, EventManager.titleString, EventManager.newString);
						GameManager.state = GameState.TURN_EVENT;
					}
				}else{
					EventManager.endTurn();
				}
			}
			break;	
		case TURN_EVENT:
			if (EventManager.isRunning == false) {
				if(eventWindow.isOpening == false){
					EventManager.endTurn();
				}
			}
			break;
		case TURN_AI_SELECT:
			if(EventManager.selectIndex != -1){
				EventManager.execSelectEvent();
				SoundManager.playSystemSE(R.raw.open);
				eventWindow.open(EventManager.spId, EventManager.titleString, EventManager.newString);
			}else if (eventWindow.isOpening == false) {
				
				EventManager.endTurn();
			}
			break;
		case TURN_SELECT:
			if(selectWindow.selectIndex != -1 && selectWindow.isOpening){
				EventManager.selectIndex = selectWindow.selectIndex;
				selectWindow.isOpening = false;
				SoundManager.playSystemSE(R.raw.open);
				EventManager.execSelectEvent();
				eventWindow.open(EventManager.spId, EventManager.titleString, EventManager.newString);
			}else if (selectWindow.isOpening == false) {
				if (eventWindow.isOpening == false) {
					EventManager.endTurn();
				}
			} 
			break;
		case TURN_END:
			GameManager.getActivePlayer().careerRefresh();
			oppomentsWindow.refresh(GameManager.getActivePlayer());
			playerCardWindow.refresh(GameManager.getActivePlayer());
			if (EventManager.isRunning == false) {
				if(GameManager.getActivePlayer().rushCount > 0){
					EventManager.startTurn();
				}else{
					updateEventNextPlayer();
				}
			}
			break;
		case GAME_END:
			break;
		case NONE:
			break;
		default:
			break;
		}
	}

	private void updateTurnStart() {
		String showText = "";
		//TODO: SE START
		
		float a = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapX;
		float b = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapY;
		spritesetMap.moveCameraTo(a, b);
		if(GameManager.getActivePlayer().rushCount > 0){
			GameManager.getActivePlayer().rushCount -= 1;
			showText = GameManager.getActivePlayer().characterData.name + "的回合(额外的回合)";
			EventManager.waitPlayer();
		}else{
			showText = GameManager.getActivePlayer().characterData.name + "的回合";
			//发薪日
			if(GameManager.age == 1){
				if(GameManager.dayCount % 5 == 0){
					showText += ",获得工资" + GameManager.getActivePlayer().salary;
					GameManager.getActivePlayer().cash += GameManager.getActivePlayer().salary;				
					if(GameManager.getActivePlayer().careerId == 2){
						GameManager.messages.add("通过职业技能获得一个额外的回合");
						GameManager.getActivePlayer().rushCount += 1;
					}else if (GameManager.getActivePlayer().careerId == 3) {
						int itemIndex = GameManager.seed.nextInt(6);
						GameManager.messages.add("通过职业技能获得道具:" + DataManager.items.get(itemIndex).name);
						GameManager.getActivePlayer().items[itemIndex] += 1;
					}else if (GameManager.getActivePlayer().careerId == 5) {
						GameManager.messages.add("通过职业技能获得一个成就点数");
						GameManager.getActivePlayer().achievementPoint += 1;
					}
				}
				
			}else if(GameManager.age == 2) {
				if(GameManager.getActivePlayer().lifeInsurance){
					if(GameManager.getActivePlayer().isEnd == false && GameManager.getActivePlayer().isFakeEnd == false){
						GameManager.getActivePlayer().cash += 100;
						showText += "养老保险领取了100元";
					}
				}
			}
			
			
			if(GameManager.getActivePlayer().isEnd){
				EventManager.skipPlayer();
			}else if (GameManager.getActivePlayer().isFakeEnd) {
				GameManager.getActivePlayer().achievementPoint += 1;
				EventManager.skipPlayer();
			}else if(GameManager.getActivePlayer().stopCount == 0){
				EventManager.waitPlayer();
			}else{
				
				GameManager.getActivePlayer().stopCount -= 1;
				EventManager.skipPlayer();
			}
			
		}
		oppomentsWindow.refresh(GameManager.getActivePlayer());
		playerCardWindow.refresh(GameManager.getActivePlayer());
		scoreWindow.refresh();
		messageWindow.open(showText);
	}
	private boolean updateEventNextPlayer() {
		GameManager.nextPlayer();
		EventManager.startTurn();
		return true;
	}
	@Override
	public void pause() {

	}

	@Override
	public void dispose(GL11 gl) {
		scoreWindow.dispose();
		gameWindow.dispose();
		itemBoxWindow.dispose();
		menuWindow.dispose();
		playerCardWindow.dispose();
		diceWindow.dispose();
		dialogWindow.dispose();
		eventWindow.dispose();
		oppomentsWindow.dispose();
		selectWindow.dispose();
		itemBoxInfoWindow.dispose();
		messageWindow.dispose();
		selectOppomentWindow.dispose();	
	}

		
	
	@Override
	public void resume() {
		scoreWindow.resume();
		gameWindow.resume();
		itemBoxWindow.resume();
		menuWindow.resume();
		playerCardWindow.resume();
		diceWindow.dispose();
		dialogWindow.resume();
		eventWindow.resume();
		selectWindow.resume();
		oppomentsWindow.resume();
		itemBoxInfoWindow.resume();
		messageWindow.resume();
		selectOppomentWindow.resume();
	}
	
	@Override
	public void touchUpdateDown(MotionEvent e, float touchX, float touchY) {
		//事件管理器正在运行
		if(EventManager.isRunning){
			return;
		}
		oldTouchX = touchX;
		oldTouchY = touchY;
	}

	@Override
	public void touchUpdateUp(MotionEvent e, float touchX, float touchY) {
		//事件管理器正在运行
		if(EventManager.isRunning){
			return;
		}
		if(GameManager.getActivePlayer().isAi){
			return;
		}
		if(dialogWindow.isOpening){
			return;
		}
		if(eventWindow.isOpening){
			return;
		}
		if(messageWindow.isOpening){
			return;
		}
//		if(spritesetMap.isMoving){
//			return;
//		}
		//换位棒选择窗口
		if(selectOppomentWindow.isOpening){
			for (int i = 0; i < 3; i++) {
				if(selectOppomentWindow.btnOppoments.get(i).isPressed(touchX, touchY)){
					selectOppomentWindow.selectIndex = i;
					return;
				}
			}
			if (selectOppomentWindow.btnCancel.isPressed(touchX, touchY)) {
				selectOppomentWindow.isOpening = false;
				SoundManager.playSystemSE(R.raw.close);
			}
			return;
		}
		//物品确认画面
		if(itemBoxInfoWindow.isOpening){
			if(itemBoxInfoWindow.btnBack.isPressed(touchX, touchY)){
				itemBoxInfoWindow.isOpening = false;
				SoundManager.playSystemSE(R.raw.cancel);
			}else if(itemBoxInfoWindow.btnConfirm.isPressed(touchX, touchY)){
				SoundManager.playSystemSE(R.raw.close);
				itemBoxInfoWindow.isOpening = false;
				int index = itemBoxInfoWindow.itemIndex;
				switch (index) {
				//遥控骰子
				case 0:
					if(GameManager.nowRcDice){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你已经使用这个道具了");
						return;
					}
					if(GameManager.getActivePlayer().slowCount > 0){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你正在使用时间沙漏中");
						return;
					}
					dialogWindow.open("嘿嘿,开个挂", WindowDialogDisplayType.PLAYER);
					SoundManager.playSystemSE(R.raw.open);
					scoreWindow.isHidding = true;
					itemBoxWindow.isHidding = true;
					menuWindow.isHidding = true;
					GameManager.nowRcDice = true;
					diceWindow.open();
					break;
				//换位棒
				case 1:
					//return不会执行物品减少语句
					SoundManager.playSystemSE(R.raw.open);
					selectOppomentWindow.open(GameManager.getActivePlayer());
					return;
				//涡轮
				case 2:
					if(GameManager.getActivePlayer().slowCount > 0){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你正在使用时间沙漏中");
						return;
					}
					if(GameManager.nowSpeedUp){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你已经使用这个道具了");
						return;
					}else {
						GameManager.nowSpeedUp = true;
						scoreWindow.isHidding = false;
						menuWindow.isHidding = false;
						diceWindow.isHidding = false;
					}
					break;
				//超级药水
				case 3:
					GameManager.getActivePlayer().rushCount += 2;
					GameManager.getActivePlayer().stopCount += 1;
					scoreWindow.isHidding = false;
					menuWindow.isHidding = false;
					diceWindow.isHidding = false;
					dialogWindow.open("珍惜时间", WindowDialogDisplayType.PLAYER);
					break;
				//时间沙漏
				case 4:
					if(GameManager.getActivePlayer().slowCount > 0){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你已经使用这个道具了");
					}
					if(GameManager.nowSpeedUp){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你正在使用急速涡轮中");
						return;
					}
					if(GameManager.nowRcDice){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你正在使用遥控骰子中");
						return;
					}
					GameManager.getActivePlayer().slowCount += 3;
					scoreWindow.isHidding = false;
					menuWindow.isHidding = false;
					diceWindow.isHidding = false;
					dialogWindow.open("慢慢来", WindowDialogDisplayType.PLAYER);
					break;
				//大汉堡
				case 5:
					if(GameManager.getActivePlayer().getHp() == GameManager.getActivePlayer().maxHp){
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("你的体力已经达到上限");
						return;
					}
					scoreWindow.isHidding = false;
					menuWindow.isHidding = false;
					diceWindow.isHidding = false;
					dialogWindow.open("满状态复活!", WindowDialogDisplayType.PLAYER);
					GameManager.getActivePlayer().setHp(GameManager.getActivePlayer().maxHp);
					break;
				}
				GameManager.getActivePlayer().items[index] -= 1;
				messageWindow.open("使用了" + DataManager.items.get(index).name);			
				itemBoxWindow.refresh();
				playerCardWindow.refresh(GameManager.getActivePlayer());
				itemBoxWindow.close();
				SoundManager.playSystemSE(R.raw.confirm);
				
			}
			return;
		}
		//主菜单窗口
		if(menuWindow.isOpening){
			if (menuWindow.btnReturnToTitle.isPressed(touchX, touchY)) {
				
				FileManager.saveGame(GameManager.saveToDataArchive());
				SystemBase.newScene = new SceneTitle();
				SoundManager.playSystemSE(R.raw.confirm);
			}
			if(menuWindow.btnBGMSwitcher.isPressed(touchX, touchY)){
				if(SoundManager.bgmIsMute){
					SoundManager.mp.setVolume(1, 1);
					SoundManager.bgmIsMute = false;
					menuWindow.btnBGMSwitcher.setSrcRectLocation(816, 32);
				}else{
					SoundManager.mp.setVolume(0, 0);
					SoundManager.bgmIsMute = true;
					menuWindow.btnBGMSwitcher.setSrcRectLocation(912, 32);
					
				}
				SoundManager.playSystemSE(R.raw.switcher);
				return;
			}
			if(menuWindow.btnSESwitcher.isPressed(touchX, touchY)){
				if(SoundManager.seIsMute){
					SoundManager.seIsMute = false;
					menuWindow.btnSESwitcher.setSrcRectLocation(816, 32);
				}else{
					menuWindow.btnSESwitcher.setSrcRectLocation(912, 32);
					SoundManager.seIsMute = true;
					
				}
				SoundManager.playSystemSE(R.raw.switcher);
				return;
			}
			if(menuWindow.btnDitherSwitcher.isPressed(touchX, touchY)){
				if(SystemManager.isDisableDither){
					SystemManager.isDisableDither = false;
					CacheBase.gl.glEnable(GL11.GL_DITHER);
					menuWindow.btnDitherSwitcher.setSrcRectLocation(816, 32);
				}else{
					SystemManager.isDisableDither = true;
					CacheBase.gl.glDisable(GL11.GL_DITHER);
					menuWindow.btnDitherSwitcher.setSrcRectLocation(912, 32);
				}
				SoundManager.playSystemSE(R.raw.switcher);
				return;
			}
			if(menuWindow.btnDialogSwitcher.isPressed(touchX, touchY)){
				if(SystemManager.isShowDialog){
					menuWindow.btnDialogSwitcher.setSrcRectLocation(912, 32);
					SystemManager.isShowDialog = false;
				}else {
					menuWindow.btnDialogSwitcher.setSrcRectLocation(816, 32);
					SystemManager.isShowDialog = true;
				}		
				SoundManager.playSystemSE(R.raw.switcher);
				return;
			}
		}
		//物品窗口
		if(itemBoxWindow.isOpening){
			for (int i = 0; i < 6; i++) {
				if(itemBoxWindow.btnSelects.get(i).isPressed(touchX, touchY)){
					if(GameManager.getActivePlayer().items[i] > 0){
						SoundManager.playSystemSE(R.raw.open);
						itemBoxInfoWindow.open(i);
					}else{
						SoundManager.playSystemSE(R.raw.cancel);
						messageWindow.open("道具数量为0哦");
					}
					return;
				}
			}
		}
		//事件选择窗口
		if(selectWindow.isOpening){
			for (int i = 0; i < selectWindow.btnSelections.size(); i++) {
				if(selectWindow.btnSelections.get(i).isPressed(touchX, touchY)){
					selectWindow.selectIndex = i;
					SoundManager.playSystemSE(R.raw.confirm);
				}
			}
			return;
		}
		
		//遥控骰子窗口
		if(diceWindow.isOpening){
			for (int i = 0; i < 6; i++) {
				if(diceWindow.btnDices.get(i).isPressed(touchX, touchY)){
					EventManager.dice(i);
					diceWindow.close();
					scoreWindow.isHidding = false;
					itemBoxWindow.isHidding = false;
					menuWindow.isHidding = false;
					return;
				}
			}
		}
		
		if(scoreWindow.btnClose.isPressed(touchX, touchY)){
			scoreWindow.close();
			itemBoxWindow.isHidding = false;
			menuWindow.isHidding = false;
			diceWindow.isHidding = false;
			SoundManager.playSystemSE(R.raw.close);
		}else if(itemBoxWindow.btnClose.isPressed(touchX, touchY)){
			itemBoxWindow.close();
			scoreWindow.isHidding = false;
			menuWindow.isHidding = false;
			diceWindow.isHidding = false;
			SoundManager.playSystemSE(R.raw.close);
		}else if(menuWindow.btnClose.isPressed(touchX, touchY)){
			menuWindow.close();
			scoreWindow.isHidding = false;
			itemBoxWindow.isHidding = false;
			diceWindow.isHidding = false;
			SoundManager.playSystemSE(R.raw.close);
		}else if(diceWindow.btnClose.isPressed(touchX, touchY)){
			diceWindow.close();
			scoreWindow.isHidding = false;
			itemBoxWindow.isHidding = false;
			menuWindow.isHidding = false;
			SoundManager.playSystemSE(R.raw.close);
		}
		
		else if(scoreWindow.btnDay.isPressed(touchX, touchY)){
			scoreWindow.open();
			itemBoxWindow.isHidding = true;
			menuWindow.isHidding = true;
			diceWindow.isHidding = true;
			SoundManager.playSystemSE(R.raw.open);
		}else if(itemBoxWindow.btnItemBox.isPressed(touchX, touchY)){
			itemBoxWindow.open();
			scoreWindow.isHidding = true;
			menuWindow.isHidding = true;
			diceWindow.isHidding = true;
			SoundManager.playSystemSE(R.raw.open);
		}else if(menuWindow.btnMenu.isPressed(touchX, touchY)){
			menuWindow.open();
			scoreWindow.isHidding = true;
			itemBoxWindow.isHidding = true;
			diceWindow.isHidding = true;
			SoundManager.playSystemSE(R.raw.open);
		}else if(diceWindow.btnGo.isPressed(touchX, touchY)){
			if(GameManager.nowRcDice){
				diceWindow.open();
				scoreWindow.isHidding = true;
				itemBoxWindow.isHidding = true;
				menuWindow.isHidding = true;
				SoundManager.playSystemSE(R.raw.open);
			}else{
				if(GameManager.getActivePlayer().slowCount > 0){
					//时间沙漏
					GameManager.getActivePlayer().slowCount -= 1;
					EventManager.dice(0);
				}else {
					//正常投骰子
					EventManager.dice();
				}
				
			}
		}
		if(playerCardWindow.bg.isPressed(touchX,touchY)){
			float x = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapX;
			float y = DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).mapY;
			spritesetMap.moveCameraTo(x, y);
		}
	}
	float oldTouchX = 0;
	float oldTouchY = 0;
	float oldDistance = 0;
	
	@Override
	public void touchUpdateMove(MotionEvent e, float touchX, float touchY) {
		if(SystemBase.touchCoolDown > 0){
			return;
		}
		//事件管理器正在运行
		if(EventManager.isRunning){
			return;
		}
		if(eventWindow.isOpening){
			return ;
		}
		if(itemBoxWindow.isOpening){
			return;
		}
		if(diceWindow.isOpening){
			return;
		}
		if(scoreWindow.isOpening){
			return;
		}
		if(menuWindow.isOpening){
			return;
		}
		if (GameManager.state == GameState.TURN_WAIT ) {
			if(spritesetMap.isMoving == false){
				float offsetX = touchX - oldTouchX;
				float offsetY = touchY - oldTouchY;
				spritesetMap.offset(-offsetX, -offsetY);
				oldTouchX = touchX;
				oldTouchY = touchY;
				//SystemManager.touchCoolDown = 10;
			}	
		}
	}
	@Override
	public void touchUpdatePointerDown(MotionEvent e) {
		//事件管理器正在运行
		if(EventManager.isRunning){
			return;
		}
		if(eventWindow.isOpening){
			return ;
		}
		if(itemBoxWindow.isOpening){
			return;
		}
		float distanceX = e.getX(0) - e.getX(1);
		float distanceY = e.getY(0) - e.getY(1);
		oldDistance = (float) Math.sqrt(distanceX * distanceX - distanceY * distanceY);
//		isZoom = true;
		oldTouchX = e.getX(0);
		oldTouchY = e.getY(0);
		SystemBase.touchCoolDown = 40;
	}
	@Override
	public void touchUpdatePointerUp(MotionEvent e) {

		//事件管理器正在运行
		if(EventManager.isRunning){
			return;
		}
		if(eventWindow.isOpening){
			return ;
		}
		if(itemBoxWindow.isOpening){
			return;
		}
		float distanceX = e.getX(0) - e.getX(1);
		float distanceY = e.getY(0) - e.getY(1);
		float distance = (float) Math.sqrt(distanceX * distanceX - distanceY * distanceY);
		if(oldDistance > distance){
			spritesetMap.zoomOut();	
		}else if (oldDistance < distance) {
			spritesetMap.zoomIn();
		}
//		isZoom = false;
		oldDistance = distance;
		SystemBase.touchCoolDown = 40;
	}
	@Override
	public void updateOnce() {
		SoundManager.playBGM(R.raw.colorfulliffe, true);
	}

	

}
