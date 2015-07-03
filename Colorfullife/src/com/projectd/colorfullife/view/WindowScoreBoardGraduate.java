package com.projectd.colorfullife.view;

import java.util.ArrayList;

import android.graphics.Color;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowScoreBoardGraduate extends WindowBase {
	class DataSet{
		TextField txtCredit;	
		Sprite2D playerFace;
		Sprite2D graduatePic;
		public DataSet(){
			txtCredit = new TextField("", 128 , 64);
			txtCredit.setColor(Color.WHITE);
			txtCredit.setSize(40);
			txtCredit.setAlign(Alignment.ALIGN_OPPOSITE);
			playerFace = CacheManager.getPlayerSprtieById(0, 48, 48);
			graduatePic = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_graduate),0,416,144,96);
			graduatePic.alpha = 0;
		}
		public void setLocation(float setX,float setY){
			playerFace.setLocation(setX, setY);
			txtCredit.setLocation(setX + 64, setY);
			graduatePic.setLocation(setX + 40, setY - 24);
		}
		public void onDrawFrame(){
			SpriteBatch.drawText(txtCredit);
			SpriteBatch.draw(graduatePic);
			SpriteBatch.draw(playerFace);
		}
		
		public void dispose() {
			txtCredit.dispose();
		}		

		public void resume() {
			txtCredit.resume();
		}		
	}
	

	Sprite2D bg;

	public ArrayList<DataSet> dataSets = new ArrayList<DataSet>();

	public boolean isHidding;
	
	public WindowScoreBoardGraduate(){
		bg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_graduate),0,0,672,416);
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
		
		dataSets.get(i).txtCredit.text = String.valueOf(GameManager.players.get(i).credit);
	}
	public void refreshGradute(int i) {
		if(GameManager.players.get(i).credit >= SystemManager.CREDIT_LIMTED){
			dataSets.get(i).graduatePic.alpha = 0.5f;
		}
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
