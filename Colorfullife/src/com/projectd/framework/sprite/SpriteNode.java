package com.projectd.framework.sprite;

import android.graphics.PointF;
import android.graphics.RectF;

public class SpriteNode{
	/**δ����ת��Ŀ������*/
	protected RectF oriRect = new RectF();
	/**Ŀ���*/
	protected PointF tarPoint[] = new PointF[4];
	/**Z��*/
	public float z;
	/**��������*/
	protected RectF srcRect = new RectF();
}