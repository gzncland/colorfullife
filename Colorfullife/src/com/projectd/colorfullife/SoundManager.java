package com.projectd.colorfullife;

import java.util.HashMap;

import com.projectd.colorfullife.R;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**声音管理器*/
public class SoundManager {
	private static Context context;
	public static MediaPlayer mp;
	//private static boolean mPlayering=false;
	private static final int MAX_CHANNEL = 8; 
	//private static SoundPool gameSoundPool; 
	private static SoundPool systemSoundPool; 
	/**音乐文件load()后返回的id组成的列表*/
	//private static HashMap<String, Integer> soundIDList = new HashMap<String, Integer>(); 
	/**音乐文件play()后返回的id组成的列表*/
	//private static HashMap<String, Integer> streamIDList = new HashMap<String, Integer>();
	//private static AudioManager audioManager; 
	
	private static  HashMap<Integer, Integer> sounds = new HashMap<Integer, Integer>();
	public static boolean bgmIsMute;
	public static boolean seIsMute;
	
	/**
	 * 初始化
	 * @param setContext 设置的Context
	 */
	public static void initiailze(Context setContext) {
		context = setContext;
		systemSoundPool = new SoundPool(MAX_CHANNEL, AudioManager.STREAM_MUSIC, 0); 		
	
		sounds.put(R.raw.confirm,systemSoundPool.load(context, R.raw.confirm, 1));
		sounds.put(R.raw.cancel,systemSoundPool.load(context, R.raw.cancel, 1));
		sounds.put(R.raw.open,systemSoundPool.load(context, R.raw.open, 1));
		sounds.put(R.raw.switcher,systemSoundPool.load(context, R.raw.switcher, 1));
		sounds.put(R.raw.dice,systemSoundPool.load(context, R.raw.dice, 1));
		sounds.put(R.raw.move,systemSoundPool.load(context, R.raw.move, 1));
		sounds.put(R.raw.close,systemSoundPool.load(context, R.raw.close, 1));
		sounds.put(R.raw.freeze,systemSoundPool.load(context, R.raw.freeze, 1));
	}
	

	/**
	 * 播放背景音乐
	 */ 	
	/**
	 * 播放BGM
	 * @param resid 资源id
	 * @param looping 循环标志
	 */
	public static void playBGM(int resid,Boolean looping){
		if(mp==null){
			mp=MediaPlayer.create(context, resid);
		}
		else {
			mp.stop();
			mp=MediaPlayer.create(context, resid);
		}
		if(SoundManager.bgmIsMute){
			SoundManager.mp.setVolume(0, 0);
		}else {
			SoundManager.mp.setVolume(1, 1);
		}
		mp.setLooping(looping);
		mp.start();

		
	}
	public static void pauseBGM() {
		if(mp!=null){
			try {
				mp.pause();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
			
		}
	}
	
	public static void stopBGM(){
		if(mp!=null){
			try {
				mp.stop();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
			
		}
	}


	public static void resumeBGM() {
		if(mp!=null){
			try {
				mp.start();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
			
		}
	}

	/**
	 * 播放系统声音
	 * @param setString 声音文件名
	 */
	public static void playSystemSE(int setIndex) {
		if(SoundManager.seIsMute){
			return;
		}
		systemSoundPool.play(sounds.get(setIndex), 1, 1, 0, 0, 1);
	}

}
