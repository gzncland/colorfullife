package com.projectd.colorfullife;

import com.projectd.colorfullife.data.DataArchive;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FileManager {

	private static Editor _editor;
	private static SharedPreferences _sp;
	
	public static void initialize(SharedPreferences setSp) {
		_editor = setSp.edit();
		_sp = setSp;
	}
	public static void saveGame(DataArchive setData){
		_editor.putInt("activePlayerIndex", setData.activePlayerIndex);
		_editor.putInt("dayCount", setData.dayCount);
		_editor.putInt("age", setData.age);
		_editor.putBoolean("nowRcDice", setData.nowRcDice);
		_editor.putBoolean("nowSpeedUp", setData.nowSpeedUp);
		for(int i = 0 ; i < 4; i++){
			_editor.putInt("playersCash"+i, setData.playersCash[i]);
			_editor.putInt("playersStopCount"+i, setData.playersStopCount[i]);
			_editor.putInt("playersRushCount"+i, setData.playersRushCount[i]);
			_editor.putInt("playersCredit"+i, setData.playersCredit[i]);
			_editor.putInt("playersHp"+i, setData.playersHp[i]);
			_editor.putInt("playersMaxHp"+i, setData.playersMaxHp[i]);
			_editor.putInt("playersSlowCount"+i, setData.playersSlowCount[i]);
			_editor.putInt("playersMapIndex"+i, setData.playersMapIndex[i]);
			_editor.putInt("achievementPoint"+i, setData.achievementPoint[i]);
			_editor.putBoolean("isAi"+i, setData.isAi[i]);
			_editor.putBoolean("isMoreCareer"+i, setData.isMoreCareer[i]);
			_editor.putInt("characterId"+i, setData.characterId[i]);
			_editor.putInt("salary"+i, setData.salary[i]);
			_editor.putInt("hplost"+i, setData.hplost[i]);
			_editor.putInt("cashlost"+i, setData.cashlost[i]);
			_editor.putInt("careerId"+i, setData.careerId[i]);
			_editor.putInt("invest"+i, setData.invest[i]);
			_editor.putBoolean("isLI"+i, setData.isLI[i]);
			_editor.putBoolean("isAI"+i, setData.isAI[i]);
			
			_editor.putBoolean("isEnd"+i, setData.isEnd[i]);
			_editor.putBoolean("isFakeEnd"+i, setData.isFakeEnd[i]);
			_editor.putBoolean("isMarried"+i, setData.isMarried[i]);
			for (int j = 0; j < 6; j++) {
				_editor.putInt("playerItems" + i + "_" + j, setData.items[i][j]);
			}
		}
		_editor.putString("time", setData.saveTime);
		_editor.putBoolean("hasSave", true);
		_editor.commit();
	}
	public static DataArchive loadGame(){
		DataArchive dataArchive = new DataArchive();
		dataArchive.activePlayerIndex = _sp.getInt("activePlayerIndex", 0);
		dataArchive.dayCount = _sp.getInt("dayCount", 0);
		dataArchive.age = _sp.getInt("age", 0);
		dataArchive.nowRcDice = _sp.getBoolean("nowRcDice", false);
		dataArchive.nowSpeedUp = _sp.getBoolean("nowSpeedUp", false);
		for (int i = 0; i < 4; i++) {
			dataArchive.playersCash[i] = _sp.getInt("playersCash"+i, 0);
			dataArchive.playersStopCount[i] = _sp.getInt("playersStopCount"+i, 0);
			dataArchive.playersRushCount[i] = _sp.getInt("playersRushCount"+i, 0);
			dataArchive.playersCredit[i] = _sp.getInt("playersCredit"+i, 0);
			dataArchive.playersHp[i] = _sp.getInt("playersHp"+i, 0);
			dataArchive.playersMaxHp[i] = _sp.getInt("playersMaxHp"+i, 0);
			dataArchive.playersSlowCount[i] = _sp.getInt("playersSlowCount"+i, 0);
			dataArchive.playersMapIndex[i] = _sp.getInt("playersMapIndex"+i, 0);
			dataArchive.achievementPoint[i] = _sp.getInt("achievementPoint"+i, 0);
			dataArchive.isAi[i] = _sp.getBoolean("isAi"+i, false);
			dataArchive.isMoreCareer[i] = _sp.getBoolean("isMoreCareer"+i, false);
			dataArchive.characterId[i] = _sp.getInt("characterId"+i, 0);
			dataArchive.careerId[i] = _sp.getInt("careerId"+i, 0);
			dataArchive.salary[i] = _sp.getInt("salary"+i, 0);
			dataArchive.hplost[i] = _sp.getInt("hplost"+i, 0);
			dataArchive.cashlost[i] = _sp.getInt("cashlost"+i, 0);
			dataArchive.invest[i] = _sp.getInt("invest"+i, 0);
			dataArchive.isLI[i] = _sp.getBoolean("isLI"+i, false);
			dataArchive.isAI[i] = _sp.getBoolean("isAI"+i, false);
			dataArchive.isEnd[i] = _sp.getBoolean("isEnd"+i, false);
			dataArchive.isFakeEnd[i] = _sp.getBoolean("isFakeEnd"+i, false);
			dataArchive.isMarried[i] = _sp.getBoolean("isMarried"+i, false);
			for (int j = 0; j < 6; j++) {
				dataArchive.items[i][j] =  _sp.getInt("playerItems" + i + "_" + j, 0);
			}
		}	
		dataArchive.saveTime = _sp.getString("time", "");
		if(_sp.getBoolean("hasSave", false)){
			return dataArchive;
		}
		
		return null;
	}
	public static void saveOptions() {
		_editor.putBoolean("bgm", SoundManager.bgmIsMute);
		_editor.putBoolean("se", SoundManager.seIsMute);
		_editor.putBoolean("dialog", SystemManager.isShowDialog);
		_editor.putBoolean("dither", SystemManager.isDisableDither);
		_editor.commit();
	}
	
	public static void loadOptions() {
		SoundManager.bgmIsMute = _sp.getBoolean("bgm", false);
		SoundManager.seIsMute = _sp.getBoolean("se", false);
		SystemManager.isShowDialog = _sp.getBoolean("dialog", true);
		SystemManager.isDisableDither = _sp.getBoolean("dither", false);
	}
	
}
