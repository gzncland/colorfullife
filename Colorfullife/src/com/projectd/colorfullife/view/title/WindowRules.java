package com.projectd.colorfullife.view.title;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;

public class WindowRules extends WindowBase {
	public boolean isHidding;
	public SpriteButton btnViewRules;
	public SpriteButton btnClose;
	public Sprite2D bg;
	public WindowRules(){
		bg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_rule),0,0,1024,512);
		bg.setLocation(0, 512);
		btnViewRules = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_title_newgame_sp),144,112,144,80);
		btnViewRules.setLocation(448, 432);
		btnClose = new SpriteButton(CacheManager.black.texture,0,0,1024,96);
        btnClose.setLocation(0, 512);
        btnClose.alpha = 0;
	}
	public void update(float deltaTime){
		int deltaValue = SystemManager.deltaTimex100;
		while (deltaValue  > 0) {
			deltaValue -= 1;
			if(isHidding){
				if(bg.getY() < 592){
					offset(0, 32);
				}
			}else if(isCloseing){
				if(bg.getY() < 512){
					offset(0, 32);
				}else{
					isOpening = false;
					isCloseing = false;
				}
			}else if(isOpening){
				if(bg.getY() > 0){
					offset(0, -32);
				}
			}else if(bg.getY() > 512){
				offset(0, -32);
			}
		}
		
	}
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnViewRules.offset(setX, setY);
		btnClose.offset(setX, setY);

	}
	public void onDrawFrame() {
		SpriteBatch.draw(btnViewRules);
		if(isOpening == true){
			SpriteBatch.draw(bg);
			SpriteBatch.draw(btnClose);
		}
		
	}
	public void open(){
		isOpening = true;
		isCloseing = false;
	}
	public void close(){
		isCloseing = true;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
