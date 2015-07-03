package com.projectd.colorfullife.view.game;


public class WindowDialogData{
	public enum WindowDialogDisplayType{
		PLAYER,OPPOMENT_A,OPPOMENT_B,OPPOMENT_C
	}
	public String dialogText;
	public WindowDialogDisplayType displayType;
	public float moveCameraX;
	public float moveCameraY;
}
