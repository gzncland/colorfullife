package com.projectd.colorfullife.view;

import android.graphics.RectF;

import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.Texture2D;

public class SpritePlayer extends Sprite2D {
	public GamePlayer playerData;
	public SpritePlayer(Texture2D setTexture, int setSrcLeft, int setSrcTop,
			int setWidth, int setHeight) {
		super(setTexture, setSrcLeft, setSrcTop, setWidth, setHeight);
	}
	public SpritePlayer(Texture2D textureById, RectF rectF, RectF rectF2) {
		super(textureById, rectF, rectF2);
	}
}
