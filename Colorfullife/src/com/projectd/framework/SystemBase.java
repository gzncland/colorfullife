package com.projectd.framework;


/**RPP����е�ϵͳ�趨����,��SystemManager���м̳�*/
public class SystemBase {
	/**��Ϸ�汾��*/
	public static int versionCode;
	/**�����������³���*/
	public static SceneBase newScene = null;
	/**��ǰ����*/
	public static SceneBase scene = null;
	/**������ȴʱ��*/
	public static int touchCoolDown;
	/**SE����*/
	public static int seVolume;
	/**BGM����*/
	public static final int bgmVolume = 0;
	/**��Ļ��*/
	public static float SCREEN_WIDTH = 0;
	/**��Ļ��*/
	public static float SCREEN_HEIGHT = 0;
	/**����Red*/
	public static final float BG_COLOR_R = 0f / 255f;
	/**����Green*/
	public static final float BG_COLOR_G =  181f / 255f;
	/**����Blue*/
	public static final float BG_COLOR_B = 233f / 255f;
	
	public static final float SCREEN_OX_FIXED = 128;
	/**������Դ��ʼ�����*/
	public static boolean ALL_GREEN = false;
}
