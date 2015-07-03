package com.projectd.colorfullife.view;

import android.R.integer;
import android.graphics.Color;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.R;
import com.projectd.colorfullife.SystemManager;
import com.projectd.colorfullife.game.GamePlayer;
import com.projectd.colorfullife.view.WindowScoreBoardGraduate.DataSet;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.TextField;

public class WindowScoreBoardFinal extends WindowBase {

	Sprite2D bg ;
	Sprite2D playerFace;
	TextField txtAp;
	TextField txtPlayerName;
	public WindowScoreBoardFinal(){
		bg = new Sprite2D(CacheManager.getTextureById(R.drawable.ui_final),0,0,672,448);
		bg.setLocation(176, 32);
		playerFace = CacheManager.getPlayerFaceById(0, 112, 112);
		playerFace.setLocation(456, 232);
		txtAp = new TextField("0", 128, 64);
		txtAp.setAlign(Alignment.ALIGN_OPPOSITE);
		txtAp.setLocation(452, 390);
		txtAp.setSize(40);
		txtAp.setColor(Color.BLACK);
		
		txtPlayerName = new TextField("xxx", 128, 64);
		txtPlayerName.setAlign(Alignment.ALIGN_CENTER);
		txtPlayerName.setLocation(448, 352);
		txtPlayerName.setSize(24);
		txtPlayerName.setColor(Color.BLACK);
	}
	public void onDrawFrame() {
		if(isOpening == true){
			SpriteBatch.draw(bg);
			SpriteBatch.draw(playerFace);
			SpriteBatch.drawText(txtAp);
			SpriteBatch.drawText(txtPlayerName);
		}
	}
	@Override
	public void dispose() {
		txtAp.dispose();
		txtPlayerName.dispose();
	}

	@Override
	public void resume() {
		txtAp.resume();
		txtPlayerName.resume();
	}
	public void open(){
		isOpening = true;
		isCloseing = false;
		for (GamePlayer player : GameManager.players) {
			boolean max = true;
			for (GamePlayer oppoment : player.opponents) {
				if(player.achievementPoint < oppoment.achievementPoint){
					max = false;
					break;
				}
			}
			if(max){
				txtPlayerName.text = player.characterData.name;
				playerFace = CacheManager.getPlayerFaceById(player.characterData.faceid, 112, 112);
				playerFace.setLocation(456, 232);
				txtAp.text = String.valueOf(player.achievementPoint);
			}
		}
		
	}
	public void close(){
		isOpening = false;
		isCloseing = true;
	}

}
