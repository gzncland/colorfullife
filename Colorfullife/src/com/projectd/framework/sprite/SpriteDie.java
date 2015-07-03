package com.projectd.framework.sprite;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;

public class SpriteDie extends Sprite2D{
	private int index;
	public SpriteDie() {
		super(CacheManager.getTextureById(R.drawable.sp_die),
				0, 0, 64, 64);
	}

	public void update(float deltaTime) {
		index += 1;
		if(index == 6){
			index = 0;
		}
		float x = index % 4 * 64;
		float y = (index / 4) * 64;
		srcRect.offsetTo(x, y);
		initializeTexCoord();
	}
	
	public void setIndex(int setIndex) {
		index = setIndex;
		float x = index % 4 * 64;
		float y = (index / 4) * 64;
		srcRect.offsetTo(x, y);
		initializeTexCoord();
	}
}
