package com.projectd.colorfullife.view;

import java.util.ArrayList;

import android.graphics.Color;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowScoreBoardRetire extends WindowBase {
	class DataSet{
		TextField txtCash;	
		Sprite2D playerFace;
		Sprite2D graduatePic;
		public DataSet(){
			txtCash = new TextField("", 128 , 64);
			txtCash.setColor(Color.WHITE);
			txtCash.setSize(26);
			txtCash.setAlign(Alignment.ALIGN_CENTER);
			playerFace = CacheManager.getPlayerSprtieById(0, 48, 48);
		}
		public void setLocation(float setX,float setY){
			playerFace.setLocation(setX, setY);
			txtCash.setLocation(setX + 64, setY+4);
		}
		public void onDrawFrame(){
			SpriteBatch.drawText(txtCash);
			SpriteBatch.draw(playerFace);
		}
		
		public void dispose() {
			txtCash.dispose();
		}		

		public void resume() {
			txtCash.resume();
		}		
	}
	

	Sprite2D bg;

	public ArrayList<DataSet> dataSets = new ArrayList<DataSet>();

	public boolean isHidding;
	
	public WindowScoreBoardRetire(){
		bg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_retire),0,0,672,416);
		bg.setLocation(176, 64);
		for(int i=0; i < 4; i++){
			DataSet ds = new DataSet();
			ds.setLocation(576,192+i*64);
			dataSets.add(ds);
		}		
	}
	@Override
	public void dispose() {
		for(DataSet dataSet : dataSets){
			dataSet.dispose();
		}

	}

	@Override
	public void resume() {
		for(DataSet dataSet : dataSets){
			dataSet.resume();
		}

	}
	
	
	public void onDrawFrame() {
		if(isOpening == true){
			SpriteBatch.draw(bg);
			for(DataSet dataSet : dataSets){
				dataSet.onDrawFrame();
			}
		}
		
	}

	public void refresh(int i) {	
		int x =GameManager.players.get(i).characterData.characterid % 4 * 128;
		int y =GameManager.players.get(i).characterData.characterid / 4 * 128;
		dataSets.get(i).playerFace.setSrcRectLocation(x, y);
//		playersFace[i].setSrcRectLocation(x, y);
		
		dataSets.get(i).txtCash.text = "гд" + String.valueOf(GameManager.players.get(i).cash);
	}
	public void open(){
		isOpening = true;
		isCloseing = false;
	}
	public void close(){
		isOpening = false;
		isCloseing = true;
	}


}
