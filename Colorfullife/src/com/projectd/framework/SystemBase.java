package com.projectd.framework;


/**RPP框架中的系统设定基类,由SystemManager进行继承*/
public class SystemBase {
	/**游戏版本号*/
	public static int versionCode;
	/**即将跳至的新场景*/
	public static SceneBase newScene = null;
	/**当前场景*/
	public static SceneBase scene = null;
	/**触摸冷却时间*/
	public static int touchCoolDown;
	/**SE音量*/
	public static int seVolume;
	/**BGM音量*/
	public static final int bgmVolume = 0;
	/**屏幕宽*/
	public static float SCREEN_WIDTH = 0;
	/**屏幕高*/
	public static float SCREEN_HEIGHT = 0;
	/**清屏Red*/
	public static final float BG_COLOR_R = 0f / 255f;
	/**清屏Green*/
	public static final float BG_COLOR_G =  181f / 255f;
	/**清屏Blue*/
	public static final float BG_COLOR_B = 233f / 255f;
	
	public static final float SCREEN_OX_FIXED = 128;
	/**所有资源初始化完成*/
	public static boolean ALL_GREEN = false;
}
