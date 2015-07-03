package com.projectd.colorfullife.data;

public class DataTile {
	

	/**地图坐标X*/
	public int mapX;
	/**地图坐标Y*/
	public int mapY;
	
	public DataEvent event;
	
	
	public DataTile(){
		event = new DataEvent();
	}
}