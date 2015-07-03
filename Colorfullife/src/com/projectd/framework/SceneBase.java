package com.projectd.framework;

//import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

public abstract class SceneBase {
	public abstract void onDrawFrame();

	public abstract void update(float deltaTime);

	public abstract void pause();

	public abstract void dispose(GL11 gl);

	public abstract void touchUpdateDown(MotionEvent e, float touchX, float touchY);

	public abstract void touchUpdateUp(MotionEvent e, float touchX, float touchY);

	public abstract void touchUpdateMove(MotionEvent e, float touchX, float touchY);

	public abstract void resume();

	public abstract void touchUpdatePointerDown(MotionEvent e);

	public abstract void touchUpdatePointerUp(MotionEvent e);

	public abstract void updateOnce();
}
