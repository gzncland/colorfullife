package com.projectd.framework;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.FileManager;
import com.projectd.colorfullife.SoundManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.scene.SceneLogo;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;


public class CLGame extends Activity implements Renderer {
	/**状态机:游戏状态*/
	enum GameState{
		INITIALZED,
		RUNNING,
		PAUSED,
		IDLE, LOADING
	}
	private GLSurfaceView glSurfaceView;
	private GameState state = GameState.INITIALZED;
	private WakeLock wakeLock;
	private Object stateChanged = new Object();
	private long startTime = System.nanoTime();

	//private Sprite2D blackScreen;
	
	private int fpsCount;
	private float fpsTime;

	private TextField fpsText;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//设置显示分辨率
		//TextManager.metrics = this.getResources().getDisplayMetrics();
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//创建一个 GLSurfaceView 用于绘制表面 
		glSurfaceView = new GLSurfaceView(this);
		//设置2.0版本
		//glSurfaceView.setEGLContextClientVersion(2);
		//设置 Renderer 用于执行实际的绘制工作 
		glSurfaceView.setRenderer(this);
		//持续绘制模式
		glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		//设置屏幕常亮
		PowerManager pManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = pManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK , "CLGame");
		
		setContentView(glSurfaceView);
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		//游戏在后台
		if(state != GameState.RUNNING){
			return super.onTouchEvent(e);
		}
		
		float touchX = (e.getX() / SystemBase.SCREEN_WIDTH) * 768 + 128 * (SystemBase.SCREEN_WIDTH / SystemBase.SCREEN_HEIGHT / 1.5f);
		float touchY = (e.getY() / SystemBase.SCREEN_HEIGHT) * 512;
		switch(e.getAction() & MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_UP:
			if(SystemBase.touchCoolDown > 0){
				return super.onTouchEvent(e);
			}
			SystemBase.scene.touchUpdateUp(e,touchX,touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			SystemBase.scene.touchUpdateMove(e,touchX,touchY);
			break;
		case MotionEvent.ACTION_DOWN:
			if(SystemBase.touchCoolDown > 0){
				return super.onTouchEvent(e);
			}
			SystemBase.scene.touchUpdateDown(e,touchX,touchY);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			SystemBase.scene.touchUpdatePointerDown(e);
			break;
		case MotionEvent.ACTION_POINTER_UP:
			SystemBase.scene.touchUpdatePointerUp(e);
			break;
		}
		
		return super.onTouchEvent(e);
	};
	
	@Override
	public void onResume() {
		super.onResume();
		glSurfaceView.onResume();
		wakeLock.acquire();
		SoundManager.resumeBGM();
	}
	
	@Override
	public void onPause(){
		synchronized (stateChanged) {
			state = GameState.PAUSED;
			while(true){
				try {
					stateChanged.wait();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		wakeLock.release();
		SoundManager.pauseBGM();
		glSurfaceView.onPause();
		super.onPause();
	}
	
	@Override
	public void onDrawFrame(GL10 gl10) {
		GameState newState = null;
		GL11 gl = (GL11)gl10;
		synchronized (stateChanged) {
			newState = state;
		}
		switch (newState) {
		case RUNNING:
			gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			gl.glClearColor(SystemBase.BG_COLOR_R, SystemBase.BG_COLOR_G, SystemBase.BG_COLOR_B, 255);
			//设置当前矩阵堆栈为模型堆栈
			gl.glMatrixMode(GL11.GL_MODELVIEW);
			gl.glLoadIdentity();
			//启用顶点数组、纹理坐标数组
			gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
			//设置正面
			gl.glFrontFace(GL11.GL_CW);

			//重绘		
			SpriteBatch.begin(gl);

			SystemBase.scene.onDrawFrame();
			
			SpriteBatch.draw(CacheManager.black);
			SpriteBatch.drawText(fpsText);
			
			SpriteBatch.end();
			
			
			// 禁用顶点、纹理坐标数组
			gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			
			fpsCount += 1;
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			
			startTime = System.nanoTime();
			
			SystemManager.deltaTimex100 = (int) (deltaTime * 100);
			SystemManager.deltaTimex10 = deltaTime * 10;
			SystemManager.deltaTimex5 = deltaTime * 5;
			
			fpsTime += deltaTime;
			if (fpsTime >= 1) {
				fpsTime -= 1;
				fpsText.text = "FPS:" + String.valueOf(fpsCount);
				fpsCount = 0;
			}

			if(SystemBase.newScene != null){
				if(CacheManager.black.alpha < 1){
					CacheManager.black.alpha += SystemManager.deltaTimex5;
				}else{
					SoundManager.stopBGM();
					SystemBase.scene.dispose(gl);
					SystemBase.scene = SystemBase.newScene;
					SystemBase.scene.updateOnce();
					SystemBase.newScene = null;
				}
				//break;
			}else if(CacheManager.black.alpha > 0){
				CacheManager.black.alpha -= SystemManager.deltaTimex5;
			}
			
			
			
			//游戏物理更新
			if(SystemBase.touchCoolDown > 0){
				SystemBase.touchCoolDown -= SystemManager.deltaTimex100;
			}
			SystemBase.scene.update(deltaTime);
			break;
		case PAUSED:
			SystemBase.scene.pause();
			synchronized (stateChanged) {
				newState = GameState.PAUSED;
				stateChanged.notifyAll();
			}
			break;
		case IDLE:
			SystemBase.scene.pause();
			SystemBase.scene.dispose(gl);
			synchronized (stateChanged) {
				newState = GameState.IDLE;
				stateChanged.notifyAll();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl10, int width, int height) {	
		GL11 gl = (GL11)gl10;
		float aspect = (float) width / (float) (height == 0 ? 1 : height);
		gl.glViewport(0, 0, width, height);
		//GLES20.glViewport(0, 0, width, height);
		// 设置当前矩阵堆栈为投影矩阵，并将矩阵重置为单位矩阵
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		// 设置投影变换矩阵
		
		//gl.glOrthof(128, 832 , 0, 512, 1, -1);
		
		GLU.gluPerspective(gl, 90.0f, aspect, 0.1f, 576.0f);
		//GLU.gluLookAt(gl, 524f, 0f,256f, 524f, 0f, 0f, 0, 1, 0);
		GLU.gluLookAt(gl, 512, -256, 256, 512, -256, 0, 0, 1, 0);
		
//		gl.glOrthox(-200,200,-200,200,-500,500);
//		GLU.gluLookAt(gl,300f,300f,600f,200f,200f,500f,0f,1f,0f);  
		
		SystemBase.SCREEN_WIDTH = width;
		SystemBase.SCREEN_HEIGHT = height;
	}


	@Override
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
		GL11 gl = (GL11)gl10;		
		synchronized (stateChanged) {
			//读取系统设定
			FileManager.initialize(getSharedPreferences("colorfullife", Context.MODE_PRIVATE));
			FileManager.loadOptions();
			if(SystemManager.isDisableDither){
				gl.glDisable(GL11.GL_DITHER);
			}else {
				gl.glEnable(GL11.GL_DITHER);
			}		
			gl.glClearColor(0, 0, 0, 255);
			
			gl.glDisable(GL11.GL_DEPTH_TEST);
			gl.glDepthFunc(GL11.GL_LEQUAL);
			gl.glEnable(GL11.GL_ALPHA_TEST);
			gl.glEnable(GL11.GL_BLEND); 
			gl.glEnable(GL11.GL_TEXTURE_2D);
			 // 设置透明显示
			gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);  
			gl.glAlphaFunc(GL11.GL_GREATER,0);
			gl.glClearDepthf(1f);
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			CacheManager.initialze(gl, getResources());
			TextManager.initialize(gl);
			SpriteBatch.initialize();
			
			
			if(state == GameState.INITIALZED){	
				fpsText = new TextField("FPS:0", 128, 32);
				
				fpsText.setLocation(SystemBase.SCREEN_OX_FIXED, 0);
				SoundManager.initiailze(this);
				DataManager.initialize(getResources());
				SystemBase.scene = new SceneLogo();
				SystemBase.scene.updateOnce();
			}
			//TODO:Thread
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			fpsText.resume();
			SpriteBatch.drawText(fpsText);
			
			SystemBase.scene.resume();
			state = GameState.RUNNING;
			
			
		}
	}

	
}
