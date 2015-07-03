package com.projectd.colorfullife;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.GameManager.GameState;
import com.projectd.colorfullife.data.DataEvent;
import com.projectd.colorfullife.data.DataSelectionEvent;
import com.projectd.colorfullife.data.DataEvent.EventResultType;
import com.projectd.colorfullife.data.DataEvent.EventTriggerType;
import com.projectd.colorfullife.view.game.WindowDialogData.WindowDialogDisplayType;


public class EventManager {
	public static DataEvent activeEvent = null;
	/**替换表达式后的消息*/
	public static String newString = null;
	/**事件窗口标题*/
	public static String titleString = null;
	/**事件窗口spid*/
	public static int spId = -1;
	
	
	/**选择项的选项*/
	public static int selectIndex;
	/**事件最终结果*/
	public static EventResultType result = EventResultType.RESULT_OTHER;
	
	public static boolean isRunning;
	protected static int _waitCount;
	private static int moveCount;
	
	
	/**清除*/
	private static void clear() {
		//_index = 0;
		//_event = null;
		_waitCount = 0;
		isRunning = false;
	}
	/**
	 * 更新
	 * @param	setMap 设置的地图
	 */
	public static void update(float deltaTime) {
		if (isRunning == false) {
			
		}else {
			if (_waitCount > 0) {
				_waitCount -= SystemManager.deltaTimex100;
				return;
			}
			if(GameManager.state == GameState.TURN_MOVE){
				if(moveCount > 0){
					moveCount -= 1;
					SoundManager.playSystemSE(R.raw.move);
					if(GameManager.getActivePlayer().mapIndex == SystemManager.TILEMAX[GameManager.age]){
						GameManager.getActivePlayer().mapIndex = SystemManager.TILESTART[GameManager.age];
					}else{
						GameManager.getActivePlayer().mapIndex += 1;
					}
					
					//触发了不可避事件
					if(DataManager.tiles.get(GameManager.getActivePlayer().mapIndex).event.triggerType == EventTriggerType.EVENT_AUTO){
						//剩余步数清零
						moveCount = 0;
					}
					_waitCount = 30;
					return;
				}else {
					//移动完毕
					GameManager.state = GameState.TURN_MOVED;
					return;
				}
			}
			clear();
		}
	}
	/**执行指令*/
//	private static boolean executeCommand() {
//		commandEnd();
//		return false;
//	}
//	private static void commandEnd() {
//		clear();
//	}
	public static void wait(int setWaitCount) {
		_waitCount = setWaitCount;
		isRunning = true;
	}
	
	public static void dice() {
		SoundManager.playSystemSE(R.raw.dice);
		GameManager.nowRcDice = false;
		GameManager.state = GameState.TURN_DICE;
		GameManager.moveCount = GameManager.seed.nextInt(6);
		_waitCount = 80;
		isRunning = true;
	}
	
	public static void dice(int i) {
		SoundManager.playSystemSE(R.raw.dice);
		GameManager.nowRcDice = false;
		GameManager.state = GameState.TURN_DICE;
		GameManager.moveCount = i;
		_waitCount = 80;
		isRunning = true;
	}
	public static void move() {
		GameManager.state = GameState.TURN_MOVE;
		//_waitCount = 30;
		isRunning = true;
	}
	public static void diecd(int setMoveCount) {
		EventManager.moveCount = setMoveCount;
		if(GameManager.nowSpeedUp){
			GameManager.nowSpeedUp = false;
			EventManager.moveCount = EventManager.moveCount * 2;
		}
		GameManager.state = GameState.TURN_DIECD;
		_waitCount = 30;
		isRunning = true;
	}
	
	public static void execSelectEvent() {
		_waitCount = 30;
		DataSelectionEvent selectionEvent = activeEvent.selectionEvents.get(selectIndex);
		//credit
		int credit = selectionEvent.creditGet - selectionEvent.creditCost;
		if(credit > 0){
			if(GameManager.getActivePlayer().characterData.isStudyKing){
				credit += 1;
			}
		}
		GameManager.getActivePlayer().credit += credit;
		//hp
		int hp = selectionEvent.hpGet - selectionEvent.hpCost;
		GameManager.getActivePlayer().setHp(GameManager.getActivePlayer().getHp() + hp);
		
		//cash
		int cash = selectionEvent.cashGet - selectionEvent.cashCost;
		GameManager.getActivePlayer().cash += cash * 100;
		
		//sc
		int sc = selectionEvent.stopCount;
		GameManager.getActivePlayer().stopCount += sc;
		
		//rc
		int rc = selectionEvent.rushCount;
		GameManager.getActivePlayer().rushCount += rc;	
		
		//ap
		int ap = selectionEvent.apGet;
		GameManager.getActivePlayer().achievementPoint += ap;
		
		//hpmax
		int hpmax = selectionEvent.hpMaxGet;
		GameManager.getActivePlayer().maxHp += hpmax;
		
		//item
		int item = selectionEvent.itemGet;
		if(item != -1){	
			GameManager.getActivePlayer().items[item] += 1;
		}
		
		int sl = selectionEvent.salaryGet;	
		GameManager.getActivePlayer().salary += sl * 100;
		
		//职业判定
		if(GameManager.getActivePlayer().careerId == 1){
			if(hp < 0){
				GameManager.getActivePlayer().hpLost -= hp; 
			}
		}
		if(GameManager.getActivePlayer().careerId == 6){
			if(cash < 0){	
				GameManager.getActivePlayer().cashLost -= cash * 100; 
			}
		}
		//TODO :SP ID
		switch (selectionEvent.spEventId) {
		case 1:
			GameManager.getActivePlayer().isMoreSl = true;
			break;
		case 2:
			GameManager.getActivePlayer().investMoney = 2500;
			break;
		case 3:
			GameManager.getActivePlayer().investMoney = 5000;
			break;
		case 4:
			GameManager.getActivePlayer().lifeInsurance = true;
			break;
		case 5:
			GameManager.getActivePlayer().autoInsurance = true;
			break;
		case 6:
			GameManager.getActivePlayer().isFakeEnd = true;
			GameManager.getActivePlayer().mapIndex = 110;
			break;
		case 7:
			GameManager.getActivePlayer().isEnd = true;			
			GameManager.getActivePlayer().mapIndex = 110;
			break;
		}
		EventManager.selectIndex = -1;
		EventManager.newString = selectionEvent.message;
		EventManager.titleString = selectionEvent.name;
		EventManager.spId = selectionEvent.spId;
		EventManager.result = selectionEvent.result;
	}

	public static void endTurn() {
		int size;
		String dialogText;
		String dialogOppomentText;
		_waitCount = 30;	
		if(activeEvent == null){
			size = GameManager.getActivePlayer().characterData.turnEnd.size();
			dialogText = GameManager.getActivePlayer().characterData.turnEnd.get(GameManager.seed.nextInt(size));
		}else{
			switch (result) {
			case RESULT_BAD:
				size = GameManager.getActivePlayer().characterData.badEvent.size();
				dialogText = GameManager.getActivePlayer().characterData.badEvent.get(GameManager.seed.nextInt(size));
				GameManager.addNewDialog(dialogText, WindowDialogDisplayType.PLAYER, GameManager.activePlayerIndex);
				//if(GameManager.seed.nextInt(3) == 0){
					
					switch (GameManager.seed.nextInt(3)) {
					case 0:
						size = GameManager.getActivePlayer().opponents.get(0).characterData.oppomentBadEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(0).characterData.oppomentBadEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_A, GameManager.activePlayerIndex);
						break;
					case 1:
						size = GameManager.getActivePlayer().opponents.get(1).characterData.oppomentBadEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(1).characterData.oppomentBadEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_B, GameManager.activePlayerIndex);
						break;
					case 2:
						size = GameManager.getActivePlayer().opponents.get(2).characterData.oppomentBadEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(2).characterData.oppomentBadEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_C, GameManager.activePlayerIndex);
						break;
					}
				//}
				break;
			case RESULT_GOOD:
				size = GameManager.getActivePlayer().characterData.goodEvent.size();
				dialogText = GameManager.getActivePlayer().characterData.goodEvent.get(GameManager.seed.nextInt(size));
				GameManager.addNewDialog(dialogText, WindowDialogDisplayType.PLAYER, GameManager.activePlayerIndex);
				//if(GameManager.seed.nextInt(3) == 0){
					switch (GameManager.seed.nextInt(3)) {
					case 0:
						size = GameManager.getActivePlayer().opponents.get(0).characterData.oppomentGoodEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(0).characterData.oppomentGoodEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_A, GameManager.activePlayerIndex);
						break;
					case 1:
						size = GameManager.getActivePlayer().opponents.get(1).characterData.oppomentGoodEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(1).characterData.oppomentGoodEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_B, GameManager.activePlayerIndex);
						break;
					case 2:
						size = GameManager.getActivePlayer().opponents.get(2).characterData.oppomentGoodEvent.size();
						dialogOppomentText = GameManager.getActivePlayer().opponents.get(2).characterData.oppomentGoodEvent.get(GameManager.seed.nextInt(size));
						GameManager.addNewDialog(dialogOppomentText, WindowDialogDisplayType.OPPOMENT_C, GameManager.activePlayerIndex);
						break;
					}
				//}
				break;
			default:
				size = GameManager.getActivePlayer().characterData.turnEnd.size();
				dialogText = GameManager.getActivePlayer().characterData.turnEnd.get(GameManager.seed.nextInt(size));
				GameManager.addNewDialog(dialogText, WindowDialogDisplayType.PLAYER, GameManager.activePlayerIndex);
				break;
			}
		}
		GameManager.state = GameState.TURN_END;
		isRunning = true;
		activeEvent = null;
		
	}
	public static void startTurn() {
		GameManager.state = GameState.TURN_START;
		if(GameManager.getActivePlayer().characterData.isMuscleMan){
			GameManager.getActivePlayer().setHp(GameManager.getActivePlayer().getHp() + 1);
		}
	}
	public static void startGame() {
		_waitCount = 60;
		GameManager.state = GameState.GAME_START;
		isRunning = true;
	}
	
	public static void waitPlayer() {
		_waitCount = 30;
		isRunning = true;
		int size = GameManager.getActivePlayer().characterData.turnStart.size();
		String message = GameManager.getActivePlayer().characterData.turnStart.get(GameManager.seed.nextInt(size));
		GameManager.addNewDialog(message, WindowDialogDisplayType.PLAYER, GameManager.activePlayerIndex);
		GameManager.state = GameState.TURN_WAIT;
	}
	public static void skipPlayer() {
		_waitCount = 30;
		isRunning = true;
		int size = GameManager.getActivePlayer().characterData.turnSkip.size();
		String message = GameManager.getActivePlayer().characterData.turnSkip.get(GameManager.seed.nextInt(size));
		GameManager.addNewDialog(message, WindowDialogDisplayType.PLAYER, GameManager.activePlayerIndex);
		if(GameManager.getActivePlayer().autoInsurance ){
			if(GameManager.getActivePlayer().isEnd == false && GameManager.getActivePlayer().isFakeEnd == false){
				GameManager.getActivePlayer().cash += 500;
				GameManager.messages.add("通过汽车保险获得了500元赔偿");
			}
		}
		GameManager.state = GameState.TURN_END;
	}
	
	public static void exeEvent() {
		_waitCount = 30;
		isRunning = true;
		int mapIndex = GameManager.getActivePlayer().mapIndex;
		activeEvent = DataManager.tiles.get(mapIndex).event;
		switch (activeEvent.type) {
		case EVENT_ITEM:
			execItemEvent();
			break;
		case EVENT_NORMAL:
			execNormalEvent(mapIndex);
			break;
		case EVENT_RANDOM:
			execRandomEvent();
			break;
		case EVENT_WEEKEND:
			execWeekendEvent();
			break;
		default:
			break;
		
		}
		
	}
	private static void execWeekendEvent() {
		activeEvent.selectionEvents.clear();
		switch (GameManager.age) {
		case 0:
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(0));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(1));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(2));
			break;
		case 1:
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(3));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(4));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(5));
			break;
		case 2:
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(6));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(7));
			activeEvent.selectionEvents.add(DataManager.weekendEvent.get(8));
			break;
		}
	}
	private static void execNormalEvent(int setMapIndex) {
		//结婚
		if(DataManager.tiles.get(setMapIndex).event.spEventId == 8){
			if(GameManager.getActivePlayer().isMarried){
				EventManager.titleString = "无事发生";
				//EventManager.spId = activeEvent.spId;
				EventManager.result = EventResultType.RESULT_OTHER;
				EventManager.newString = "你已经结果婚了,今天是你的结婚纪念日,好好的和你的爱人相处吧(没有任何影响)";
				return;
			}else {
				GameManager.getActivePlayer().isMarried = true;
			}
		}
		
		//credit
		int credit = DataManager.tiles.get(setMapIndex).event.creditMin;
		if(DataManager.tiles.get(setMapIndex).event.creditRand > 0){
			credit += GameManager.seed.nextInt(DataManager.tiles.get(setMapIndex).event.creditRand);
		}else if(DataManager.tiles.get(setMapIndex).event.creditRand < 0){
			credit -= GameManager.seed.nextInt(-DataManager.tiles.get(setMapIndex).event.creditRand);
		}
		//学霸
		if(credit > 0){
			if(GameManager.getActivePlayer().characterData.isStudyKing){
				credit += 1;
			}
		}
		GameManager.getActivePlayer().credit += credit;
		
		//hp
		int hp = DataManager.tiles.get(setMapIndex).event.hpMin;
		if(DataManager.tiles.get(setMapIndex).event.hpRand > 0){
			hp += GameManager.seed.nextInt(DataManager.tiles.get(setMapIndex).event.hpRand);
		}else if(DataManager.tiles.get(setMapIndex).event.hpRand < 0){
			hp -= GameManager.seed.nextInt(-DataManager.tiles.get(setMapIndex).event.hpRand);
		}
		GameManager.getActivePlayer().setHp(GameManager.getActivePlayer().getHp() + hp);
		
		//cash
		int cash = DataManager.tiles.get(setMapIndex).event.cashMin;
		if(DataManager.tiles.get(setMapIndex).event.cashRand > 0){
			cash += GameManager.seed.nextInt(DataManager.tiles.get(setMapIndex).event.cashRand);
		}else if(DataManager.tiles.get(setMapIndex).event.cashRand < 0){
			cash -= GameManager.seed.nextInt(-DataManager.tiles.get(setMapIndex).event.cashRand);
		}
		GameManager.getActivePlayer().cash += cash * 100;
		
		//itemget
		if(DataManager.tiles.get(setMapIndex).event.itemGet != -1){
			GameManager.getActivePlayer().items[DataManager.tiles.get(setMapIndex).event.itemGet] += 1;
		}
		
		//rushcount
		if(DataManager.tiles.get(setMapIndex).event.rushCount > 0){
			GameManager.getActivePlayer().rushCount += DataManager.tiles.get(setMapIndex).event.rushCount;
		}else{
			GameManager.getActivePlayer().stopCount += -DataManager.tiles.get(setMapIndex).event.rushCount;
		}
		
		//ap
		int ap = DataManager.tiles.get(setMapIndex).event.apMin;
		if(DataManager.tiles.get(setMapIndex).event.apRand > 0){
			ap += GameManager.seed.nextInt(DataManager.tiles.get(setMapIndex).event.apRand);
		}else if(DataManager.tiles.get(setMapIndex).event.apRand < 0){
			ap -= GameManager.seed.nextInt(-DataManager.tiles.get(setMapIndex).event.apRand);
		}
		GameManager.getActivePlayer().achievementPoint += ap;
		
		int sl = DataManager.tiles.get(setMapIndex).event.salary;		
		GameManager.getActivePlayer().salary += sl * 100;
		//职业判定
		if(GameManager.getActivePlayer().careerId == 1){
			if(hp < 0){
				GameManager.getActivePlayer().hpLost -= hp; 
			}
		}
		if(GameManager.getActivePlayer().careerId == 6){
			if(cash < 0){
				GameManager.getActivePlayer().cashLost -= cash * 100; 
			}
		}
		
		EventManager.newString = activeEvent.info;
		EventManager.newString = EventManager.newString.replaceAll("(\\{hp\\})", String.valueOf(Math.abs(hp)));
		EventManager.newString = EventManager.newString.replaceAll("(\\{cash\\})", String.valueOf(Math.abs(cash * 100)));
		EventManager.newString = EventManager.newString.replaceAll("(\\{credit\\})", String.valueOf(Math.abs(credit)));
		EventManager.newString = EventManager.newString.replaceAll("(\\{ap\\})", String.valueOf(Math.abs(ap)));
		
		EventManager.titleString = activeEvent.name;
		EventManager.spId = activeEvent.spId;
		EventManager.result = activeEvent.result;
	}
	private static void execItemEvent() {
		int index = GameManager.seed.nextInt(6);
		newString = activeEvent.info + DataManager.items.get(index).name;
		
		GameManager.getActivePlayer().items[index] += 1;
		
		EventManager.spId = activeEvent.spId;
		EventManager.titleString = activeEvent.name;
		EventManager.result = EventResultType.RESULT_GOOD;
	}
	private static void execRandomEvent() {
		int index = GameManager.seed.nextInt(DataManager.randomEvents.size());
		activeEvent = DataManager.randomEvents.get(index);
		
		//credit
		int credit = activeEvent.creditMin;
		if(activeEvent.creditRand > 0){
			credit += GameManager.seed.nextInt(activeEvent.creditRand);
		}else if(activeEvent.creditRand < 0){
			credit -= GameManager.seed.nextInt(-activeEvent.creditRand);
		}
		if(credit > 0){
			if(GameManager.getActivePlayer().characterData.isStudyKing){
				credit += 1;
			}
		}
		GameManager.getActivePlayer().credit += credit;
		
		//hp
		int hp = activeEvent.hpMin;
		if(activeEvent.hpRand > 0){
			hp += GameManager.seed.nextInt(activeEvent.hpRand);
		}else if(activeEvent.hpRand < 0){
			hp -= GameManager.seed.nextInt(-activeEvent.hpRand);
		}
		GameManager.getActivePlayer().setHp(GameManager.getActivePlayer().getHp() + hp);
		
		//cash
		int cash = activeEvent.cashMin;
		if(activeEvent.cashRand > 0){
			cash += GameManager.seed.nextInt(activeEvent.cashRand);
		}else if(activeEvent.cashRand < 0){
			cash -= GameManager.seed.nextInt(-activeEvent.cashRand);
		}
		GameManager.getActivePlayer().cash += cash * 100;
		
		//itemget
		if(activeEvent.itemGet != -1){
			GameManager.getActivePlayer().items[activeEvent.itemGet] += 1;
		}
		
		//rushcount
		if(activeEvent.rushCount > 0){
			GameManager.getActivePlayer().rushCount += activeEvent.rushCount;
		}else{
			GameManager.getActivePlayer().stopCount += -activeEvent.rushCount;
		}
		
		//ap
		int ap = activeEvent.apMin;
		if(activeEvent.apRand > 0){
			ap += GameManager.seed.nextInt(activeEvent.apRand);
		}else if(activeEvent.apRand < 0){
			ap -= GameManager.seed.nextInt(-activeEvent.apRand);
		}
		GameManager.getActivePlayer().achievementPoint += ap;
		
		//职业判定
		if(GameManager.getActivePlayer().careerId == 1){
			if(hp < 0){
				GameManager.getActivePlayer().hpLost -= hp; 
			}
		}
		if(GameManager.getActivePlayer().careerId == 6){
			if(cash < 0){
				GameManager.getActivePlayer().cashLost -= cash * 100; 
			}
		}
		
		EventManager.spId = activeEvent.spId;
		EventManager.newString = activeEvent.info;
		EventManager.titleString = activeEvent.name;
		EventManager.result = activeEvent.result;
	}
}
