package com.projectd.colorfullife;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.projectd.colorfullife.R;
import com.projectd.colorfullife.data.DataCareer;
import com.projectd.colorfullife.data.DataCharacter;
import com.projectd.colorfullife.data.DataEvent;
import com.projectd.colorfullife.data.DataItem;
import com.projectd.colorfullife.data.DataSelectionEvent;
import com.projectd.colorfullife.data.DataTile;
import com.projectd.colorfullife.data.DataEvent.EventResultType;
import com.projectd.colorfullife.data.DataEvent.EventTriggerType;
import com.projectd.colorfullife.data.DataEvent.EventType;

/**存放静态数据的类*/
public class DataManager {
	public static ArrayList<DataTile> tiles = new ArrayList<DataTile>();
	//public static ArrayList<DataRandomEvent> randomevents = new ArrayList<DataRandomEvent>();
	private static Resources resources;
	//public static ArrayList<DataEvent> events = new ArrayList<DataEvent>();
	public static ArrayList<DataCharacter> characters = new ArrayList<DataCharacter>();
	
	public static ArrayList<DataCareer> careers = new ArrayList<DataCareer>();
	public static ArrayList<DataItem> items = new ArrayList<DataItem>();
	public static ArrayList<DataEvent> randomEvents = new ArrayList<DataEvent>();
	public static ArrayList<DataSelectionEvent> selectionEvent = new ArrayList<DataSelectionEvent>();
	static ArrayList<DataSelectionEvent> weekendEvent = new ArrayList<DataSelectionEvent>();
	
	public static void initialize(Resources setRes) {
		resources = setRes;
		initializeItems();
		initializeSelectEvent();
		initializeWeekendEvent();
		initializeRandomEvent();
		initializeTiles();
		initializeCharacters();
		initializeCareers();
	}
	
	private static void initializeWeekendEvent() {
		weekendEvent.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.weekend_event);
		try {
			DataSelectionEvent event = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("event")){    
				    	event = new DataSelectionEvent();
				   	}else if (nodeName.equals("name")) {
				   		event.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						event.info = xrp.nextText();
					}else if (nodeName.equals("message")) {
						event.message = xrp.nextText();
					}else if (nodeName.equals("spid")) {
						event.spId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("result")) {
						String result = xrp.nextText();
						if(result.equals("bad")){
							event.result = EventResultType.RESULT_BAD;
						}else if (result.equals("good")) {
							event.result = EventResultType.RESULT_GOOD;
						}
					}else if (nodeName.equals("hp_cost")) {
						event.hpCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_cost")) {
						event.cashCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("stopcount")) {
						event.stopCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_cost")) {
						event.creditCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hp_get")) {
						event.hpGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hpmax_get")) {
						event.hpMaxGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_get")) {
						event.cashGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("rushcount")) {
						event.rushCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("item_get")) {
						event.itemGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_get")) {
						event.creditGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("sp_event")) {
						event.spEventId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("ap_get")) {
						event.apGet = Integer.parseInt(xrp.nextText());
					}
					
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("event") && event != null){  
	                	weekendEvent.add(event);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void initializeTiles(){
		tiles.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.tiles);
		try {
			DataTile tile = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("tile")){    
				    	tile = new DataTile();
				   	}else if (nodeName.equals("name")) {
				   		tile.event.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						tile.event.info = xrp.nextText();
					}else if (nodeName.equals("type")) {
						if(xrp.nextText().equals("auto")){
							tile.event.triggerType = EventTriggerType.EVENT_AUTO;
						}
					}else if (nodeName.equals("mapx")) {
						tile.mapX = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("mapy")) {
						tile.mapY = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("spid")) {
						tile.event.spId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("result")) {
						String result = xrp.nextText();
						if(result.equals("bad")){
							tile.event.result = EventResultType.RESULT_BAD;
						}else if (result.equals("good")) {
							tile.event.result = EventResultType.RESULT_GOOD;
						}
					}else if (nodeName.equals("hp_lim")) {
						tile.event.hpMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hp_rand")) {
						tile.event.hpRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_lim")) {
						tile.event.cashMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_rand")) {
						tile.event.cashRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("rushcount")) {
						tile.event.rushCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("item_get")) {
						tile.event.itemGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_lim")) {
						tile.event.creditMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_rand")) {
						tile.event.creditRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("ap_lim")) {

						tile.event.apMin = Integer.parseInt(xrp.nextText());

					}else if (nodeName.equals("ap_rand")) {
						tile.event.apRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("selection")) {
						tile.event.selectionEvents.add(DataManager.selectionEvent.get(Integer.parseInt(xrp.nextText())));
					}else if (nodeName.equals("sl_lim")) {
						tile.event.salary = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("sp_event")) {
						tile.event.spEventId = Integer.parseInt(xrp.nextText());
					}
					else if (nodeName.equals("command")) {
						String typeText = null;
						try {
							typeText = xrp.nextText();
						} catch (Exception e) {
							continue;
						}
						
						if(typeText.equals("item")){
							tile.event.type = EventType.EVENT_ITEM;
						}else if (typeText.equals("random")) {
							tile.event.type = EventType.EVENT_RANDOM;
						}else if (typeText.equals("weekend")) {
							tile.event.type = EventType.EVENT_WEEKEND;
						}
					}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("tile") && tile != null){  
	                    tiles.add(tile);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void initializeRandomEvent() {
		randomEvents.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.random_event);
		try {
			DataEvent event = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("event")){    
				    	event = new DataEvent();
				   	}else if (nodeName.equals("name")) {
				   		event.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						event.info = xrp.nextText();
					}else if (nodeName.equals("spid")) {
						event.spId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("result")) {
						String result = xrp.nextText();
						if(result.equals("bad")){
							event.result = EventResultType.RESULT_BAD;
						}else if (result.equals("good")) {
							event.result = EventResultType.RESULT_GOOD;
						}
					}else if (nodeName.equals("hp_lim")) {
						event.hpMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hp_rand")) {
						event.hpRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_lim")) {
						event.cashMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_rand")) {
						event.cashRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("rushcount")) {
						event.rushCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("item_get")) {
						event.itemGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_lim")) {
						event.creditMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_rand")) {
						event.creditRand = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("ap_lim")) {
						event.apMin = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("ap_rand")) {
						event.apRand = Integer.parseInt(xrp.nextText());
					}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("event") && event != null){  
	                	randomEvents.add(event);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void initializeItems() {
		items.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.items);
		try {
			DataItem item = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("item")){    
				    	item = new DataItem();
				   	}else if (nodeName.equals("name")) {
				   		item.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						item.info = xrp.nextText();
					}else if (nodeName.equals("spid")) {
						item.spId = Integer.parseInt(xrp.nextText());
					}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("item") && item != null){  
	                	items.add(item);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	private static void initializeEvent() {
		events.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.events);
		try {
			GameEvent event = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("event")){    
				    	event = new GameEvent();
				   	}else if (nodeName.equals("title")) {
				   		event.title = xrp.nextText();
					}else if (nodeName.equals("info")) {
						event.info = xrp.nextText();
						}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("event") && event != null){  
	                	events.add(event);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	private static void initializeCharacters() {
		characters.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.characters);
		try {
			DataCharacter player = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("character")){    
						player = new DataCharacter();
				   	}else if (nodeName.equals("name")) {
				   		player.name = xrp.nextText();
					}else if (nodeName.equals("maxhp")) {
						player.maxHp = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("goodevent")) {
						player.goodEvent.add(xrp.nextText());
					}else if (nodeName.equals("badevent")) {
						player.badEvent.add(xrp.nextText());
					}else if (nodeName.equals("turn_start")) {
						player.turnStart.add(xrp.nextText());
					}else if (nodeName.equals("turn_end")) {
						player.turnEnd.add(xrp.nextText());
					}else if (nodeName.equals("turn_skip")) {
						player.turnSkip.add(xrp.nextText());		
					}else if (nodeName.equals("oppoment_goodevent")) {
						player.oppomentGoodEvent.add(xrp.nextText());
					}else if (nodeName.equals("oppoment_badevent")) {
						player.oppomentBadEvent.add(xrp.nextText());		
					}else if (nodeName.equals("money")) {
						player.startCash = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("characterid")) {
						player.characterid = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("faceid")) {
						player.faceid = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("study_king")) {
						player.isStudyKing = true;
					}else if (nodeName.equals("muscle_man")) {
						player.isMuscleMan = true;
					}else if (nodeName.equals("info")) {
						player.info = xrp.nextText();
					}else if (nodeName.equals("note")) {
						player.note = xrp.nextText();
					}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("character") && player != null){  
	                	characters.add(player);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	private static void initializeSelectEvent() {
		selectionEvent.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.selections);
		try {
			DataSelectionEvent event = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("selection")){    
				    	event = new DataSelectionEvent();
				   	}else if (nodeName.equals("name")) {
				   		event.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						event.info = xrp.nextText();
					}else if (nodeName.equals("message")) {
						event.message = xrp.nextText();
					}else if (nodeName.equals("spid")) {
						event.spId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("result")) {
						String result = xrp.nextText();
						if(result.equals("bad")){
							event.result = EventResultType.RESULT_BAD;
						}else if (result.equals("good")) {
							event.result = EventResultType.RESULT_GOOD;
						}
					}else if (nodeName.equals("hp_cost")) {
						event.hpCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_cost")) {
						event.cashCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("stopcount")) {
						event.stopCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_cost")) {
						event.creditCost = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hp_get")) {
						event.hpGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("hpmax_get")) {
						event.hpMaxGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("cash_get")) {
						event.cashGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("rushcount")) {
						event.rushCount = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("item_get")) {
						event.itemGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("credit_get")) {
						event.creditGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("sp_event")) {
						event.spEventId = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("ap_get")) {
						event.apGet = Integer.parseInt(xrp.nextText());
					}else if (nodeName.equals("sl_get")) {
						event.salaryGet = Integer.parseInt(xrp.nextText());
					}
					
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("selection") && event != null){  
	                	selectionEvent.add(event);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void initializeCareers() {
		careers.clear();
		XmlResourceParser xrp = resources.getXml(R.xml.careers);
		try {
			DataCareer career = null;
			//标签不为文档结束符
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				String nodeName = xrp.getName();
				switch(xrp.getEventType()){
				//标签为开始符
				case XmlResourceParser.START_TAG:
					if(nodeName.equals("career")){    
				    	career = new DataCareer();
				   	}else if (nodeName.equals("name")) {
				   		career.name = xrp.nextText();
					}else if (nodeName.equals("info")) {
						career.info = xrp.nextText();
					}else if (nodeName.equals("spid")) {
						career.spid = Integer.parseInt(xrp.nextText());
					}
					break;
				//标签为结束符
				case XmlResourceParser.END_TAG: 
	                if(nodeName.equals("career") && career != null){  
	                	careers.add(career);  
	                } 
	                break;
				}
				xrp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
