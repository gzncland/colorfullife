package com.projectd.framework.sprite;

import android.graphics.PointF;
import android.graphics.RectF;

public class SpriteNode{
	/**未被旋转的目标区域*/
	protected RectF oriRect = new RectF();
	/**目标点*/
	protected PointF tarPoint[] = new PointF[4];
	/**Z轴*/
	public float z;
	/**纹理区域*/
	protected RectF srcRect = new RectF();
}