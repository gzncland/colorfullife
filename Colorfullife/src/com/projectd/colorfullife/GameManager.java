package com.projectd.colorfullife;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.projectd.colorfullife.data.DataArchive;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.colorfullife.view.game.WindowDialogData;
import com.projectd.colorfullife.view.game.WindowDialogData.WindowDialogDisplayType;

public class GameManager {
	public enum GameState{
		GAME_START,
		TURN_START,	//回合开始
		TURN_WAIT,	//等待用户操作
		TURN_DICE,	//投骰子
		TURN_DIECD,	//投骰子结束
		TURN_MOVE,	//移动
		TURN_MOVED,	//移动完毕
		TURN_EVENT, //事件触发
		TURN_END,	//回合结束
		GAME_END, 
		NONE, TURN_SELECT, TURN_AI_SELECT,GAME_LOADED
	}
	public static ArrayList<GamePlayer> players = new ArrayList<GamePlayer>();
	public static GameState state = GameState.NONE;
	public static int activePlayerIndex; 
	public static int dayCount;
	public static int age;
	public static int moveCount;
	public static boolean nowRcDice;
	public static boolean nowSpeedUp;
	public static ArrayList<String> messages = new ArrayList<String>();
	private static void initializeOppoments() {
		GamePlayer player1 = players.get(0);
		GamePlayer player2 = players.get(1);
		GamePlayer player3 = players.get(2);
		GamePlayer player4 = players.get(3);
		player1.opponents.add(player2);
		player1.opponents.add(player3);
		player1.opponents.add(player4);
		
		player2.opponents.add(player1);
		player2.opponents.add(player3);
		player2.opponents.add(player4);
		
		player3.opponents.add(player1);
		player3.opponents.add(player2);
		player3.opponents.add(player4);
		
		player4.opponents.add(player1);
		player4.opponents.add(player2);
		player4.opponents.add(player3);
	}
	
	public static void initializeGame(int[] characterSelectIndex) {
		players.clear();
		dialogs.clear();

		GamePlayer player1 = new GamePlayer(characterSelectIndex[0]);
		GamePlayer player2 = new GamePlayer(characterSelectIndex[1]);
		GamePlayer player3 = new GamePlayer(characterSelectIndex[2]);
		GamePlayer player4 = new GamePlayer(characterSelectIndex[3]);
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		//player4.isAi = true;
		
		dayCount = 1;
		activePlayerIndex = 0;
		state = GameState.GAME_START;
		nowRcDice = false;
		nowSpeedUp = false;
		moveCount = 0;
		messages.clear();
		age = 0;
		
//		for (int i = 0; i < 6; i++) {
//			player1.items[i] += 10;
//		}
		
		initializeOppoments();
		
	}
	public static DataArchive saveToDataArchive() {
		DataArchive setData = new DataArchive();
		for(int i = 0; i < 4; i++){
			setData.playersCash[i] = players.get(i).cash;
			setData.playersStopCount[i] = players.get(i).stopCount;
			setData.playersRushCount[i] = players.get(i).rushCount;
			setData.playersCredit[i] = players.get(i).credit;
			setData.playersHp[i] = players.get(i).getHp();
			setData.playersMaxHp[i] = players.get(i).maxHp;
			setData.playersSlowCount[i] = players.get(i).slowCount;
			setData.playersMapIndex[i] = players.get(i).mapIndex;
			setData.achievementPoint[i] = players.get(i).achievementPoint;
			setData.isAi[i] = players.get(i).isAi;
			setData.isMoreCareer[i] = players.get(i).isMoreSl;
			setData.characterId[i] = players.get(i).characterIndex;
			setData.careerId[i] = players.get(i).careerId;
			setData.salary[i] = players.get(i).salary;
			setData.hplost[i] = players.get(i).hpLost;
			setData.cashlost[i] = players.get(i).cashLost;
			setData.invest[i] = players.get(i).investMoney;
			setData.isLI[i] = players.get(i).lifeInsurance;
			setData.isAI[i] = players.get(i).autoInsurance;
			setData.isEnd[i] = players.get(i).isEnd;
			setData.isFakeEnd[i] = players.get(i).isFakeEnd;
			setData.isMarried[i] = players.get(i).isMarried;
			for (int j = 0; j < 6; j++) {
				setData.items[i][j] = players.get(i).items[j];
			}
		}
		setData.activePlayerIndex = GameManager.activePlayerIndex;
		setData.dayCount = GameManager.dayCount;
		setData.age = GameManager.age;
		setData.nowRcDice = GameManager.nowRcDice;
		setData.nowSpeedUp = GameManager.nowSpeedUp;
		setData.saveTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()).toString();
		return setData;
	}
	public static void loadFromDataArchive(DataArchive setData) {
		players.clear();
		dialogs.clear();
		GamePlayer player1 = new GamePlayer(setData.characterId[0]);
		GamePlayer player2 = new GamePlayer(setData.characterId[1]);
		GamePlayer player3 = new GamePlayer(setData.characterId[2]);
		GamePlayer player4 = new GamePlayer(setData.characterId[3]);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		for (int i = 0; i < 4; i++) {
			players.get(i).cash = setData.playersCash[i];
			players.get(i).stopCount = setData.playersStopCount[i];
			players.get(i).rushCount = setData.playersRushCount[i];
			players.get(i).credit = setData.playersCredit[i];
			players.get(i).setHp(setData.playersHp[i]);
			players.get(i).maxHp = setData.playersMaxHp[i];
			players.get(i).slowCount = setData.playersSlowCount[i];
			players.get(i).mapIndex = setData.playersMapIndex[i];
			players.get(i).achievementPoint = setData.achievementPoint[i];
			players.get(i).isAi = setData.isAi[i];
			players.get(i).isMoreSl = setData.isMoreCareer[i];
			players.get(i).characterIndex = setData.characterId[i];
			players.get(i).careerId = setData.careerId[i];
			players.get(i).salary = setData.salary[i];
			players.get(i).hpLost = setData.hplost[i];
			players.get(i).cashLost = setData.cashlost[i];
			for (int j = 0; j < 6; j++) {
				players.get(i).items[j] = setData.items[i][j];
			}
			players.get(i).investMoney = setData.invest[i];
			players.get(i).lifeInsurance = setData.isLI[i];
			players.get(i).autoInsurance = setData.isAI[i];
			players.get(i).isEnd = setData.isEnd[i];
			players.get(i).isFakeEnd = setData.isFakeEnd[i];
			players.get(i).isMarried = setData.isMarried[i];
			players.get(i).isMarried = setData.isMarried[i];
		}
		GameManager.activePlayerIndex = setData.activePlayerIndex;
		GameManager.age = setData.age;
		GameManager.dayCount = setData.dayCount;
		GameManager.nowRcDice = setData.nowRcDice;
		GameManager.nowSpeedUp = setData.nowSpeedUp;
		state = GameState.TURN_WAIT;
		initializeOppoments();
	}
	
	/**随机种子*/
	public static Random seed = new Random();
	
	public static ArrayList<WindowDialogData> dialogs = new ArrayList<WindowDialogData>();
	
	
	/**切换至下一位玩家*/
	public static int nextPlayer() {
		activePlayerIndex += 1;
		if(activePlayerIndex >= players.size()){
			dayCount += 1;
			activePlayerIndex = 0;
		}
		return activePlayerIndex;
	}
	/**获取当前玩家*/
	public static final GamePlayer getActivePlayer() {
		return players.get(activePlayerIndex);	
	}
	
	/**推送新对话
	 * @param setDialogText 显示的文字
	 * @param setType 显示方式
	 * @param setIndex 摄像机跳转玩家索引*/
	public static final void addNewDialog(String setDialogText,WindowDialogDisplayType setType, int setIndex) {
		if(SystemManager.isShowDialog == false){
			return;
		}
		WindowDialogData dialog = new WindowDialogData();
		dialog.dialogText = setDialogText;
		dialog.displayType = setType;
		int x = DataManager.tiles.get(players.get(setIndex).mapIndex).mapX;
		int y = DataManager.tiles.get(players.get(setIndex).mapIndex).mapY;
		dialog.moveCameraX = x;
		dialog.moveCameraY = y;
		dialogs.add(dialog);
	}

	

}
