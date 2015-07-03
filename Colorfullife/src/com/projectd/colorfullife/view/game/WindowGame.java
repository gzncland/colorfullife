package com.projectd.colorfullife.view.game;

import android.graphics.RectF;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;

public class WindowGame extends WindowBase {
	Sprite2D topBg;
	Sprite2D bottomBg;
	

	
	
	public WindowGame() {
		topBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),
        		new RectF(0, 0, 1024, 48),
        		new RectF(0, 0, 32, 48)
        	);

        bottomBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),
        		new RectF(0, 0, 1024, 32),
        		new RectF(0, 48, 32, 80)
        	);
        bottomBg.setLocation(0, 480);
       
   }
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void resume() {
		
	}


	public void onDrawFrame() {
		SpriteBatch.draw(topBg);
		SpriteBatch.draw(bottomBg);
	}

}
