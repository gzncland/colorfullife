package com.projectd.framework;

import java.util.Hashtable;

import javax.microedition.khronos.opengles.GL11;

import com.projectd.framework.sprite.Sprite2D;
import com.projectd.framework.sprite.Texture2D;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.RectF;
import android.opengl.GLUtils;

public abstract class CacheBase {

	public static Sprite2D black;
	
	protected static Resources res;
	public static GL11 gl;
	protected final static int MAX_TEXTURE_COUNT = 100;
	protected static int textureIndex;
	protected static int[] textureArray = new int[MAX_TEXTURE_COUNT];
	protected static Hashtable<Integer,Texture2D> texturesHashtable = new Hashtable<Integer,Texture2D>();
	
	protected static void genBlackTexture() {
		Bitmap bitmap = Bitmap.createBitmap(1, 1, Config.RGB_565);
		bitmap.setPixel(0, 0, Color.BLACK);

		//gl.glGenTextures(1,  textureArray , textureIndex);
		
		// 将生成的空纹理绑定到当前2D纹理通道
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textureArray[textureIndex]);
		
		// 设置2D纹理通道当前绑定的纹理的属性
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT);
		
		// 将bitmap应用到2D纹理通道当前绑定的纹理中
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);
		
		black = new Sprite2D(
			new Texture2D(bitmap.getWidth(),bitmap.getHeight()), 
			new RectF(0, 0, 1024, 512), 
			new RectF(0, 0, 1, 1)
		);
		
		black.texture.textureId = textureArray[textureIndex];
			
	    //Texture2D texture2d = new Texture2D( bitmap.getScaledWidth(metrics),bitmap.getScaledHeight(metrics));
		//black.textureId = textureArray[textureIndex];
		
		bitmap.recycle();   
	    bitmap = null;
	    textureIndex += 1;
	}
	
	/**
	 * 解析绑定一般纹理
	 * @param setDrawableId
	 */
	public static void decode(int setDrawableId){
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inTargetDensity = 160;
		
		//DisplayMetrics metrics = res.getDisplayMetrics();
		Bitmap bitmap = BitmapFactory.decodeResource(res,setDrawableId,opts);

		
		
		// 将生成的空纹理绑定到当前2D纹理通道
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textureArray[textureIndex]);
		
		// 设置2D纹理通道当前绑定的纹理的属性
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_LINEAR);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT);
		
		// 将bitmap应用到2D纹理通道当前绑定的纹理中
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);
		
		Texture2D texture2d = new Texture2D( bitmap.getWidth(),bitmap.getHeight());
			
	    //Texture2D texture2d = new Texture2D( bitmap.getScaledWidth(metrics),bitmap.getScaledHeight(metrics));
		texture2d.textureId = textureArray[textureIndex];
		
		CacheBase.texturesHashtable.put(setDrawableId, texture2d);
		
		bitmap.recycle();   
	    bitmap = null;
	    textureIndex += 1;

	}

	public static Texture2D getTextureById(int setId) {
		return texturesHashtable.get(setId);
	}
}
