package com.projectd.colorfullife;

import java.util.HashMap;

import com.projectd.colorfullife.R;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**����������*/
public class SoundManager {
	private static Context context;
	public static MediaPlayer mp;
	//private static boolean mPlayering=false;
	private static final int MAX_CHANNEL = 8; 
	//private static SoundPool gameSoundPool; 
	private static SoundPool systemSoundPool; 
	/**�����ļ�load()�󷵻ص�id��ɵ��б�*/
	//private static HashMap<String, Integer> soundIDList = new HashMap<String, Integer>(); 
	/**�����ļ�play()�󷵻ص�id��ɵ��б�*/
	//private static HashMap<String, Integer> streamIDList = new HashMap<String, Integer>();
	//private static AudioManager audioManager; 
	
	private static  HashMap<Integer, Integer> sounds = new HashMap<Integer, Integer>();
	public static boolean bgmIsMute;
	public static boolean seIsMute;
	
	/**
	 * ��ʼ��
	 * @param setContext ���õ�Context
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
	 * ���ű�������
	 */ 	
	/**
	 * ����BGM
	 * @param resid ��Դid
	 * @param looping ѭ����־
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
	 * ����ϵͳ����
	 * @param setString �����ļ���
	 */
	public static void playSystemSE(int setIndex) {
		if(SoundManager.seIsMute){
			return;
		}
		systemSoundPool.play(sounds.get(setIndex), 1, 1, 0, 0, 1);
	}

}
