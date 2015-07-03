package com.projectd.framework.sprite;

import com.projectd.framework.SystemBase;

import android.graphics.RectF;


public class SpriteButton extends Sprite2D {
	public boolean active = true;
	
	public SpriteButton(Texture2D setTexture, RectF setDesRect, RectF setSrcRect) {
		super(setTexture, setDesRect, setSrcRect);
		
	}
	public SpriteButton(Sprite2D setSprite){
		super(setSprite.texture, setSprite.oriRect, setSprite.srcRect);
	}
	public SpriteButton(Texture2D setTexture, int setSrcLeft, int setSrcTop,
			int setWidth, int setHeight) {
		super(setTexture, setSrcLeft, setSrcTop, setWidth, setHeight);
		
	}
	public boolean isPressed(float setX,float setY) {
		if(active){
			if(setX > this.getX()  && setX < this.getX() + this.getWidth()){
				if(setY < this.getY() + this.getHeight() && setY > this.getY() ){
					SystemBase.touchCoolDown = 10;
					return true;
				}
			}
		}
		return false;
	}
}
