package com.projectd.framework;

import javax.microedition.khronos.opengles.GL11;

import com.projectd.framework.sprite.TextField;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.opengl.GLUtils;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;

public class TextManager {
	
	static GL11 gl;
	
	static final int MAX = 100;
	
	static DisplayMetrics metrics = null;
	static int[] textBuffer = new int[MAX];
	static int index = 0;

	public static void initialize(GL11 setGl) {
		gl = setGl;
		textBuffer = new int[MAX];
	}
	
	public static int getTexture(TextField setTextField){
		return TextManager.getTexture(setTextField.text,(int)setTextField.getWidth(),(int)setTextField.getHeight(),setTextField.getAlign(),setTextField.getSize(),setTextField.getBold(),setTextField.getColor());
	}
	public static int getTexture(String setString,int setWidth,int setHeight,Alignment setAlign, int setSize, boolean setBold,int setColor){
		while(true){
			
			if (index >= MAX) {
				index = 0;
			}
			if(textBuffer[index] == 0){
				break;
			}
			index += 1;
		}

		gl.glGenTextures(1,  textBuffer , index);
		// 将生成的空纹理绑定到当前2D纹理通道
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textBuffer[index]);
		//textHashTable.put(setString, index);

		
		Bitmap bitmap = createBitmap(setString,setWidth,setHeight,setAlign,setSize,setBold,setColor);
		// 设置2D纹理通道当前绑定的纹理的属性
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT);
		
		// 将bitmap应用到2D纹理通道当前绑定的纹理中 
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
        bitmap = null;
        return textBuffer[index];
	}
//	 private static Bitmap createBitmap(String setString,int setWidth,int setHeight,Alignment setAlign, int setSize, boolean setBold){
//		 return TextManager.createBitmap(setString,setWidth,setHeight,setAlign,setSize,setBold,Color.WHITE);    	
//	 }
	 private static Bitmap createBitmap(String setString,int setWidth,int setHeight,Alignment setAlign, int setSize, boolean setBold,int setColor){
		
		
    	Bitmap bitmap = Bitmap.createBitmap(setWidth,setHeight, Bitmap.Config.ARGB_8888);

    	Canvas canvas = new Canvas(bitmap);

    	TextPaint paint = new TextPaint();
 		paint.setColor(setColor);
 		paint.setSubpixelText(true);
 		paint.setAntiAlias(true);
 		
 		//paint.setTextAlign(setAlign);
 		//paint.setFakeBoldText(setBold);
 		if(setBold){
 			//paint.setTypeface( Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
 			paint.setStrokeWidth(1.5f);
 			paint.setStyle(Style.FILL_AND_STROKE);
 		}
		
		paint.setTextSize(setSize);
		
		StaticLayout layout = new StaticLayout(setString,paint,setWidth,setAlign,1f,2,false);
		if(setAlign == Alignment.ALIGN_OPPOSITE){
			canvas.translate(-2,2);
		}else {
			canvas.translate(2,5);
		}
		
		layout.draw(canvas);
		
//			if(setAlign == Align.RIGHT){
//				canvas.drawText(setString, setWidth, setHeight, paint);
//			}else{
//				canvas.drawText(setString, 0, setHeight, paint);
//			}
		
		return bitmap;
	 }

	public static void disposeTexture(int textureId) {
		if(textureId != -1){
			for (int i = 0; i < textBuffer.length; i++) {
				if(textureId == textBuffer[i]){
					gl.glDeleteTextures(1, textBuffer, i);
					textBuffer[i] = 0;
					return;
				}
			}
			
		}
	}

	
}