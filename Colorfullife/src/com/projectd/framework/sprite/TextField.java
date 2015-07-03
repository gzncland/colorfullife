package com.projectd.framework.sprite;

import com.projectd.framework.TextManager;

import android.graphics.Color;
import android.text.Layout.Alignment;

public class TextField extends Sprite2D {

	public String text;
	private boolean bold;
	private int size = 20;
	private Alignment algin = Alignment.ALIGN_NORMAL;
	private int color = Color.WHITE;
	public String oldText = null;
	
	public TextField(String setText,int setWidth, int setHeight) {
		super(new Texture2D(setWidth,setHeight), 0, 0, setWidth, setHeight);
		
		text = setText;
	}

	public final void dispose() {
		TextManager.disposeTexture(texture.textureId);
	}
	public final void setColor(int value) {
		oldText = null;
		color = value;
	}
	
	public final void setAlign(Alignment value) {
		oldText = null;
		algin = value;
	}
	
	public final void setSize(int value) {
		oldText = null;
		size = value;
	}
	
	public final void setBold(boolean value) {
		oldText = null;
		bold = value;
	}
	
	public final int getColor() {
		return color;
	}
	
	public final Alignment getAlign() {
		return algin;
	}
	
	public final int getSize() {
		return size;
	}

	public final boolean getBold() {
		return bold;
	}

	public void resume() {
		oldText = null;
	}
}
