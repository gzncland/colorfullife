package com.projectd.framework.sprite;

import android.graphics.PointF;
import android.graphics.RectF;


public class Sprite2D extends SpriteNode{
	public enum CentrePoint{
		TOPLEFT, CENTER
	}
	public Texture2D texture = new Texture2D();
	
	public float texCoord[] = new float[8];
	
	protected float angle = 0;
	
	protected float scaleY = 1;
	protected float scaleX = 1;
	public float alpha = 1;
	
	/**
	 * 构造函数
	 * @param setTexture 设置纹理
	 * @param setRect 设置目标区域
	 * @param setSrcRect 设置纹理剪裁区域
	 */
	public Sprite2D(Texture2D setTexture,RectF setDesRect,RectF setSrcRect) {
		oriRect = setDesRect;
		texture = setTexture;
		srcRect = setSrcRect;
		initializeTarVecCoord();
		initializeTexCoord();
	}
	
	
	/**
	 * 构造函数
	 * @param setTexture 设置纹理
	 * @param setWidth Sprite宽
	 * @param setHeight Sprite高
	 * @param setSrcLeft 纹理剪裁区域x
	 * @param setSrcTop 纹理剪裁区域y
	 */
	public Sprite2D(Texture2D setTexture,int setSrcLeft,int setSrcTop,int setWidth,int setHeight) {
		oriRect = new RectF(0,0,setWidth,setHeight);
		texture = setTexture;
		srcRect = new RectF(setSrcLeft,setSrcTop,setSrcLeft + setWidth,setSrcTop + setHeight);
		initializeTexCoord();
		initializeTarVecCoord();
	}
	
	
	public void updateVector() {
		float cos = (float) Math.cos(Math.toRadians(angle));
    	float sin = (float) Math.sin(Math.toRadians(angle));
    	float halfWidth = getWidth() * scaleX / 2f;
    	float halfHeight = getHeight()* scaleY / 2f;
    	//float offsetWidth = getWidth() * (scaleX - 1) / 2;
    	//float offsetHeight = getHeight() * (scaleY - 1) / 2;
    	tarPoint[0].x =  (-halfWidth) * cos - (-halfHeight) * sin + halfWidth + oriRect.left;// - offsetWidth;
		tarPoint[0].y = (-halfWidth * sin + (-halfHeight) * cos) + halfHeight + oriRect.top;// - offsetHeight;
    	
		tarPoint[1].x = (-halfWidth * cos - halfHeight * sin) + halfWidth + oriRect.left;// - offsetWidth;
		tarPoint[1].y = (-halfWidth * sin + halfHeight * cos) + halfHeight + oriRect.top;// - offsetHeight;
    	
		tarPoint[2].x = halfWidth * cos - halfHeight * sin  + halfWidth + oriRect.left;// - offsetWidth;
		tarPoint[2].y = (halfWidth * sin + halfHeight * cos)  + halfHeight + oriRect.top;// - offsetHeight;
    	
		tarPoint[3].x = halfWidth * cos - (-halfHeight) * sin + halfWidth + oriRect.left;// - offsetWidth;
		tarPoint[3].y = (halfWidth * sin + (-halfHeight) * cos)+ halfHeight + oriRect.top;// - offsetHeight;
	}
	
	
	
	/** 初始化目标顶点坐标*/
	protected final void initializeTarVecCoord() {
		tarPoint[0] = new PointF(oriRect.left,oriRect.top);
		tarPoint[1] = new PointF(oriRect.left,oriRect.bottom);
		tarPoint[2] = new PointF(oriRect.right,oriRect.bottom);
		tarPoint[3] = new PointF(oriRect.right,oriRect.top);
	}
	/** 初始化纹理坐标 */
	protected final void initializeTexCoord(){
		texCoord[0] = (srcRect.left + 1) / texture.width;
		texCoord[1] = (srcRect.top + 1) / texture.height;	
		
		texCoord[2] = (srcRect.left + 1) / texture.width;
		texCoord[3] = (srcRect.bottom - 1) / texture.height;	
		
		texCoord[4] = (srcRect.right - 1) / texture.width;
		texCoord[5] = (srcRect.bottom - 1) / texture.height;
			
		texCoord[6] = (srcRect.right - 1) / texture.width;
		texCoord[7] = (srcRect.top + 1)  / texture.height;
	}
	
	/**
	 * 设置角度
	 * @param value 设置的角度(0-360)
	 */
	public final void setAngle(float value) {
		if(value > 360){
			value -= 360;
		}else if (value < 0) {
			value += 360;
		}
		angle = value;
    	updateVector();	
	}
	/**
	 * 设置缩放比例
	 * @param value 设置的比例
	 */
	public void setScale(float value) {
		scaleX = value;
		scaleY = value;
		updateVector();
	}
	public void setScale(float setX, float setY) {
		scaleX = setX;
		scaleY = setY;
		updateVector();
	}
	/**
	 * 偏移源矩阵坐标
	 * @param setX
	 * @param setY
	 */
	public void setSrcRectLocation(float setX,float setY) {
		srcRect.offsetTo(setX, setY);
		initializeTexCoord();
	}
	
	/**
	 * 设置Sprite坐标
	 * @param setX X
	 * @param setY Y
	 */
	public final void setLocation(float setX,float setY){
		oriRect.offsetTo(setX,setY);
		updateVector();
	}
	/**
	 * 移动Sprite
	 * @param setX
	 * @param setY
	 */
	public final void offset(float setX,float setY){
		oriRect.offset(setX,setY);
		updateVector();
	}
	
	/**获取X坐标*/
	public final float getX(){
		return oriRect.left;
	}
	/**获取Y坐标*/
	public final float getY(){
		return oriRect.top;
	}
	/**获取宽度*/
	public final float getWidth(){
		return oriRect.width();
	}
	/**获取高度*/
	public final float getHeight(){
		return oriRect.height();
	}
	/**获取角度 */
	public final float getAngle() {
		return angle;
	}
	/**获取X放大率*/
	public final float getScaleX(){
		return scaleX;
	}
	/**获取Y放大率*/
	public final float setScaleY(){
		return scaleY;
	}

	/**获取宽度*/
	public final float getScaleWidth(){
		return oriRect.width() * scaleX;
	}
	/**获取高度*/
	public final float getScaleHeight(){
		return oriRect.height() * scaleY;
	}
	


}
