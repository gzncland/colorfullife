package com.projectd.colorfullife.data;

import java.util.ArrayList;


public class DataEvent{
	public enum EventResultType {
		RESULT_GOOD,RESULT_BAD,RESULT_OTHER
	}
	public enum EventTriggerType{
		EVENT_AUTO,
		EVENT_HIT
	}
	/**¥•∑¢¿‡–Õ*/
	public EventTriggerType triggerType = EventTriggerType.EVENT_HIT;
	
	public int spId;
	public String name;
	public String info;
	public EventResultType result = EventResultType.RESULT_OTHER;

	
	public int hpMin;
	public int hpRand;
	
	public int cashMin;
	public int cashRand;
	
	public int creditMin;
	public int creditRand;
	
	public int itemGet = -1;
	public int rushCount;
	
	public EventType type = EventType.EVENT_NORMAL;
	
	public enum EventType{
		EVENT_NORMAL,EVENT_WEEKEND,EVENT_RANDOM,EVENT_ITEM
	}
	
	public DataEvent selections[] = null;

	public int apMin;

	public int apRand;

	public ArrayList<DataSelectionEvent> selectionEvents = new ArrayList<DataSelectionEvent>();

	public int salary;

	public int spEventId;
}
