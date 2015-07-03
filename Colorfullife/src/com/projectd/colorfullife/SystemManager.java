package com.projectd.colorfullife;

import com.projectd.framework.SystemBase;

public class SystemManager extends SystemBase {

//	public static final int TILES_COLUMN_COUNT = 8;
//	public static final int TILES_ROW_COUNT = 8;
	
	public static final int STUDY_TILES_COLUMN_COUNT = 3;
	public static final int STUDY_TILES_ROW_COUNT = 3;

	
	public static final int STUDY_ERA_DAYMAX = 21;
	
	public static final int ADULT_TILES_COLUMN_COUNT = 3;
	public static final int ADULT_TILES_ROW_COUNT = 3;
	
	public static final int ADULT_ERA_DAYMAX = 46;
	
	public static final int OLD_TILES_COLUMN_COUNT = 3;
	public static final int OLD_TILES_ROW_COUNT = 3;
	
	public static final int OLD_ERA_DAYMAX = 66;
	public static final int[] TILEMAX = {31,76,109};
	public static final int[] TILESTART = {1,39,84};
	
	public static final int CREDIT_LIMTED = 50;

	public static int deltaTimex100 = 0;
	public static float deltaTimex5 = 0;
	public static float deltaTimex10 = 0;
	
	public static boolean isShowDialog = true;
	public static boolean isDisableDither;

}
