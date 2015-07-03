package com.projectd.colorfullife.view.game;

import java.util.ArrayList;

import android.graphics.Color;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.view.game.WindowDialogData.WindowDialogDisplayType;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowDialog extends WindowBase {
	
	
	
	
	public boolean isOpening;
	
	WindowDialogDisplayType nowDisplayingType;
	Sprite2D playerDialogBg; 
	TextField txtPlayerDialog;
	ArrayList<Sprite2D> oppomentDialogBgs = new ArrayList<Sprite2D>(); 
	ArrayList<TextField> oppomentDialogs = new ArrayList<TextField>(); 
	int count;
	
	public WindowDialog(){
		playerDialogBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),624 ,112, 400, 128);
		txtPlayerDialog = new TextField("", 512, 64);
		playerDialogBg.setLocation(168, 96);
		txtPlayerDialog.setLocation(200, 154);
		txtPlayerDialog.setColor(0xff050505);
		txtPlayerDialog.setSize(32);
		
		for (int i = 0; i < 3; i++) {
			Sprite2D oppomentDialogBg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_game),624 ,240, 256, 128);
			oppomentDialogBg.setLocation(224 + i * 116, 320);
			oppomentDialogBgs.add(oppomentDialogBg);
			
			TextField oppomentDialog = new TextField("", 256, 64);
			oppomentDialog.setSize(26);
			oppomentDialog.setColor(Color.BLACK);
			oppomentDialog.setLocation(240 + i * 116, 350);
			oppomentDialogs.add(oppomentDialog);
		}
	}
	public void onDrawFrame() {
		if(!isOpening){
			return;
		}
		switch (nowDisplayingType) {
		case OPPOMENT_A:
			SpriteBatch.draw(oppomentDialogBgs.get(0));
			SpriteBatch.drawText(oppomentDialogs.get(0));
			break;
		case OPPOMENT_B:
			SpriteBatch.draw(oppomentDialogBgs.get(1));
			SpriteBatch.drawText(oppomentDialogs.get(1));
			break;
		case OPPOMENT_C:
			SpriteBatch.draw(oppomentDialogBgs.get(2));
			SpriteBatch.drawText(oppomentDialogs.get(2));
			break;
		case PLAYER:
			SpriteBatch.draw(playerDialogBg);
			SpriteBatch.drawText(txtPlayerDialog);
			break;
		default:
			break;
		}
	}
	public void open(String setText,WindowDialogDisplayType setDisplayType) {
		if(SystemManager.isShowDialog == false){
			return;
		}
		isOpening = true;
		count = 120;
		switch (setDisplayType) {
		case OPPOMENT_A:
			oppomentDialogs.get(0).text = setText;
			break;
		case OPPOMENT_B:
			oppomentDialogs.get(1).text = setText;
			break;
		case OPPOMENT_C:
			oppomentDialogs.get(2).text = setText;
			break;
		case PLAYER:
			txtPlayerDialog.text = setText;
			break;
		default:
			break;
		}
		nowDisplayingType = setDisplayType;
	}
	@Override
	public void dispose() {
		txtPlayerDialog.dispose();
		for (TextField oppomentDialog : oppomentDialogs) {
			oppomentDialog.dispose();
		}
	}

	
	@Override
	public void resume() {
		txtPlayerDialog.resume();
		for (TextField oppomentDialog : oppomentDialogs) {
			oppomentDialog.resume();
		}
	}
	public void update(float deltaTime) {
		if(count < 0){
			isOpening = false;
		}else {
			count -= SystemManager.deltaTimex100;
		}
	}

}
