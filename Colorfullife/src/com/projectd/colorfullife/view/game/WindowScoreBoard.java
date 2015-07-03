package com.projectd.colorfullife.view.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout.Alignment;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.CacheManager;
import com.projectd.colorfullife.DataManager;
import com.projectd.colorfullife.GameManager;
import com.projectd.colorfullife.SystemManager;
import com.projectd.framework.WindowBase;
import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.SpriteBatch;
import com.projectd.framework.sprite.SpriteButton;
import com.projectd.framework.sprite.TextField;

public class WindowScoreBoard extends WindowBase {
	
	class DatasetShoter implements Comparator<DataSet>{

		@Override
		public int compare(DataSet lhs, DataSet rhs) {
			if(Integer.parseInt(lhs.txtAchievement.text) > Integer.parseInt(rhs.txtAchievement.text)){
				return -1;
			}else if(Integer.parseInt(lhs.txtAchievement.text) == Integer.parseInt(rhs.txtAchievement.text)){				
				return 0;
			}
			return 1;
		}
		
	}
	class DataSet{
		TextField txtName;
		TextField txtCash;
		TextField txtJob;
		TextField txtETC;
		TextField txtAchievement;	
		Sprite2D playerFace;
		public DataSet(){
			txtName = new TextField("name", 128 , 64);
			txtName.setColor(Color.BLACK);
			txtName.setSize(26);
			txtCash = new TextField("cash", 128 , 32);
			txtCash.setAlign(Alignment.ALIGN_OPPOSITE);
			txtCash.setColor(Color.BLACK);
			txtJob = new TextField("job", 128 , 32);
			txtJob.setColor(Color.BLACK);
		    txtETC = new TextField("ETC", 128, 32);
		    txtETC.setAlign(Alignment.ALIGN_OPPOSITE);
		    txtETC.setColor(Color.BLACK);
			txtAchievement = new TextField("achievement", 128 , 128);
			txtAchievement.setAlign(Alignment.ALIGN_OPPOSITE);
			txtAchievement.setColor(Color.BLACK);
			txtAchievement.setSize(80);
			txtAchievement.alpha = 0.5f;
			playerFace = CacheManager.getPlayerFaceById(0, 64, 64);
		}
		public void setLocation(float setX,float setY){
			txtName.setLocation(setX , setY - 4);
			txtCash.setLocation(setX  , setY + 40);
			txtJob.setLocation(setX + 168, setY );
			txtETC.setLocation(setX + 168, setY + 40);
			txtAchievement.setLocation(setX + 416 , setY - 10);
			playerFace.setLocation(setX - 80, setY -2 );
		}
		public void onDrawFrame(){
			SpriteBatch.drawText(txtName);
			SpriteBatch.drawText(txtCash);
			SpriteBatch.drawText(txtJob);
			SpriteBatch.drawText(txtETC);
			SpriteBatch.drawText(txtAchievement);
			SpriteBatch.draw(playerFace);
		}
		
		public void dispose() {
			txtName.dispose();
			txtCash.dispose();
			txtJob.dispose();
			txtETC.dispose();
			txtAchievement.dispose();
		}		

		public void resume() {
			txtName.resume();
			txtCash.resume();
			txtJob.resume();
			txtETC.resume();
			txtAchievement.resume();
		}
		public void offset(int setX, int setY) {
			txtName.offset(setX, setY);
			txtCash.offset(setX, setY);
			txtJob.offset(setX, setY);
			txtETC.offset(setX, setY);
			txtAchievement.offset(setX, setY);
			playerFace.offset(setX, setY);
			
		}
		
	}
	/**背景*/
	Sprite2D bg;
	
	TextField txtDayCount;
	
	/**[不可见]关闭按钮*/
	public SpriteButton btnClose;
	
	/**得分版按钮*/
	public SpriteButton btnDay;
	
	/**正在隐藏标志*/
	public boolean isHidding;
	
	/**得分版窗口*/
	public ArrayList<DataSet> dataSetsArrayList = new ArrayList<DataSet>();
	/**得分版窗口*/
	DataSet dataSets[] = new DataSet[4];
	
	public WindowScoreBoard() {
		bg = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game_scoreboard),0,0,1024,512);
		bg.setLocation(0, -512);
		btnClose = new SpriteButton(CacheManager.black.texture,
			new RectF(0,0,1024,96),
			new RectF(0,0,1,1)
		);
		btnClose.setLocation(0, -96);
		btnDay = new SpriteButton(CacheManager.getTextureById(R.drawable.ui_game),416,64,144,64);
		btnDay.setLocation(568,0);
		
		txtDayCount = new TextField("99", 64, 32);
		txtDayCount.setAlign(Alignment.ALIGN_CENTER);
		txtDayCount.setSize(28);
		txtDayCount.setLocation(600, 0);
		
		for(int i=0; i < 4; i++){
			DataSet ds = new DataSet();
			ds.setLocation(256,96+i*96-512);
			dataSetsArrayList.add(ds);
			dataSets[i] = ds;
		}
	}
	
	/**更新*/
	public void update(float deltaTime){
		
		int deltaValue = SystemManager.deltaTimex100;
		while (deltaValue  > 0) {
			deltaValue -= 1;
			if(isHidding){
				if(bg.getY() > -576){
					offset(0, -32);
				}
			}else if(isCloseing){
				if(bg.getY() > -512){
					offset(0, -32);
				}else{
					isOpening = false;
					isCloseing = false;
				}
			}else if(isOpening){
				if(bg.getY() < 0){
					offset(0, 32);
				}
			}else if(bg.getY() < -512){
				offset(0, 32);
			}
		}
	}
	/**打开窗口*/
	public void open(){
		isOpening = true;
		isCloseing = false;
	}
	/**关闭窗口*/
	public void close(){
		isCloseing = true;
	}
	/**整体偏移*/
	public void offset(int setX,int setY) {
		bg.offset(setX, setY);
		btnDay.offset(setX, setY);
		btnClose.offset(setX, setY);
		txtDayCount.offset(setX, setY);
		for(DataSet dataSet : dataSetsArrayList){
			dataSet.offset(setX, setY);
		}
	}
	/**整体设置坐标*/
	public void setLocation(int setX,int setY) {
		bg.setLocation(setX, setY);
		btnDay.setLocation(setX, setY);
		btnClose.setLocation(setX, setY);
		txtDayCount.setLocation(setX, setY);
	}
	/**释放资源*/
	@Override
	public void dispose() {
		txtDayCount.dispose();
		for (int i = 0; i < 4; i++) {
			dataSets[i].dispose();
		}
	}
	/**从后台恢复*/
	@Override
	public void resume() {	
		txtDayCount.resume();
		for (int i = 0; i < 4; i++) {
			dataSets[i].resume();
		}
	}
	/**重绘*/
	public void onDrawFrame(){
		SpriteBatch.draw(btnDay);
		SpriteBatch.drawText(txtDayCount);
		if(isOpening){
			SpriteBatch.draw(bg);
		}
//		for(DataSet dataSet : dataSetsArrayList){
//			dataSet.onDrawFrame();
//		}
		for (int i = 0; i < 4; i++) {
			dataSets[i].onDrawFrame();
		}
	}

	public void refresh() {
		txtDayCount.text = String.valueOf(GameManager.dayCount);
		
		for (int i = 0; i < 4; i++) {
			int x = CacheManager.PLAYER_FACE_WIDTH * (GameManager.players.get(i).characterData.faceid % 4);
			int y = CacheManager.PLAYER_FACE_HEIGHT * (GameManager.players.get(i).characterData.faceid / 4);
			dataSets[i].playerFace.setSrcRectLocation(x, y);
			dataSets[i].txtName.text = GameManager.players.get(i).characterData.name;
			dataSets[i].txtCash.text = "现金 ￥" + String.valueOf(GameManager.players.get(i).cash);
			dataSets[i].txtAchievement.text = String.valueOf(GameManager.players.get(i).achievementPoint);
			if(GameManager.age == 0){
				dataSets[i].txtJob.text = "学生";
				dataSets[i].txtETC.text = String.valueOf(GameManager.players.get(i).credit) + "学分";
			}else if(GameManager.age == 1){
//				if(GameManager.players.get(i).credit > SystemManager.CREDIT_LIMTED){
//					dataSets.get(i).txtETC.text = "已毕业";
//				}else{
//					dataSets.get(i).txtETC.text = "未毕业";
//				}
				dataSets[i].txtETC.text = "月薪 ￥" + GameManager.players.get(i).salary;
				dataSets[i].txtJob.text = DataManager.careers.get(GameManager.players.get(i).careerId).name;
			}else if(GameManager.age == 2){
				if(GameManager.players.get(i).lifeInsurance){
					dataSets[i].txtETC.text = "已交纳养老保险";
				}else{
					dataSets[i].txtETC.text = "无养老保险";
				}
				dataSets[i].txtJob.text = "已退休";
			}
			
		}
		Collections.sort(dataSetsArrayList, new DatasetShoter());
		for(int i=0; i < 4; i++){
			dataSetsArrayList.get(i).setLocation(256,96+i*96 + bg.getY());
		}
	}

}
