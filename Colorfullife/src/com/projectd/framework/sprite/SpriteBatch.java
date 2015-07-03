package com.projectd.framework.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL11;

import com.projectd.framework.TextManager;


public class SpriteBatch {

	private static ArrayList<Sprite2D> _spriteNodeList = new ArrayList<Sprite2D>();
	private static Texture2D _activeTexture = new Texture2D();
	private static GL11 gl;
	private static ByteBuffer indicesBuffer = ByteBuffer.allocate(300);
	private static ByteBuffer verticesBuffer = ByteBuffer.allocateDirect(2400);
	private static ByteBuffer trianglesBuffer = ByteBuffer.allocateDirect(1600);
	private static ByteBuffer colorsBuffer = ByteBuffer.allocateDirect(3200);
	/**开始绘制*/
	public static void begin(GL11 setGl){
		gl = setGl;
		
	}
	
	/**结束绘制*/
	public static void end(){
	    //结束之前Flush一次全部精灵节点
	    flush();
	    //重置
	    gl.glLoadIdentity();
	}
	/**
	 * 添加精灵至绘画列表
	 * @param setSprite 设置的精灵
	 */
	public static void draw(Sprite2D setSprite){
		//如果当前纹理与活动纹理不同
		if (setSprite.texture != _activeTexture){
			//Flush一次先前的全部节点
            flush();
            // 更新活动纹理
            _activeTexture = setSprite.texture;
        }else if (_spriteNodeList.size() == 49){
        	_spriteNodeList.add(setSprite);
        	 flush();
        	 return;
        }
        //新增精灵节点
		_spriteNodeList.add(setSprite);
    }
	
	public static void drawText(TextField setText) {
		if(setText.oldText != setText.text){
			setText.oldText = setText.text;
			TextManager.disposeTexture(setText.texture.textureId);
			setText.texture.textureId = TextManager.getTexture(setText);
		}
		
		draw(setText);
	}
	static byte[] indices = new byte[50 * 6];
	static float[] vertices = new float[50 * 12];
	static float[] texCoord = new float[50 * 8];
	static float[] colors = new float[50 * 16];
	static int i = 0;
	static int j = 0;
	static int k = 0;
	static int l = 0;
	static byte m = 0;
	private static void flush() {
		int size = _spriteNodeList.size();
		//异常判别
		if (size == 0){
			return;
		}
		clearArrs();
		indicesBuffer.limit(300);
		verticesBuffer.limit(2400);
		trianglesBuffer.limit(1600);
		colorsBuffer.limit(3200);
		for (int index = 0; index < size; index++) {
			//for (int i = 0; i < 300; i+=6) {
// 			indices[i] = m;
//        	indices[i + 1] =  (byte) (m + 1);
//        	indices[i + 2] = (byte) (m + 2);
//        	
//        	indices[i + 3] = m;
//        	indices[i + 4] = (byte) (m + 2);
//        	indices[i + 5] = (byte) (m + 3);
        	
        	indicesBuffer.put(i,m);
        	indicesBuffer.put(i + 1,(byte) (m + 1));
        	indicesBuffer.put(i + 2,(byte) (m + 2));
        	
        	indicesBuffer.put(i + 3,m);
        	indicesBuffer.put(i + 4,(byte) (m + 2));
        	indicesBuffer.put(i + 5,(byte) (m + 3));
        	
			//}
        	
//        	vertices[j] = _spriteNodeList.get(index).tarPoint[0].x;
//        	vertices[j + 1] = - _spriteNodeList.get(index).tarPoint[0].y;
//        	vertices[j + 2] =   _spriteNodeList.get(index).z;  // 0, Top Left
//        	
//        	vertices[j + 3] =  _spriteNodeList.get(index).tarPoint[1].x;
//        	vertices[j + 4] = - _spriteNodeList.get(index).tarPoint[1].y;
//        	vertices[j + 5] =  _spriteNodeList.get(index).z;  // 1, Bottom Left
//        	 
//        	vertices[j + 6] =  _spriteNodeList.get(index).tarPoint[2].x;
//        	vertices[j + 7] = - _spriteNodeList.get(index).tarPoint[2].y;
//        	vertices[j + 8] =  _spriteNodeList.get(index).z;  // 2, Bottom Right
//        			 
//        	vertices[j + 9] =  _spriteNodeList.get(index).tarPoint[3].x;
//        	vertices[j + 10] = - _spriteNodeList.get(index).tarPoint[3].y;
//        	vertices[j + 11] =  _spriteNodeList.get(index).z;  // 3, Top Right
        	
        	
        	verticesBuffer.putFloat((j) * 4, _spriteNodeList.get(index).tarPoint[0].x);
        	verticesBuffer.putFloat((j + 1) * 4,- _spriteNodeList.get(index).tarPoint[0].y);
        	verticesBuffer.putFloat((j + 2) * 4, _spriteNodeList.get(index).z);
        	
        	verticesBuffer.putFloat((j + 3) * 4, _spriteNodeList.get(index).tarPoint[1].x);
        	verticesBuffer.putFloat((j + 4) * 4,- _spriteNodeList.get(index).tarPoint[1].y);
        	verticesBuffer.putFloat((j + 5) * 4, _spriteNodeList.get(index).z);
        	
        	verticesBuffer.putFloat((j + 6) * 4, _spriteNodeList.get(index).tarPoint[2].x);
        	verticesBuffer.putFloat((j + 7) * 4,- _spriteNodeList.get(index).tarPoint[2].y);
        	verticesBuffer.putFloat((j + 8) * 4, _spriteNodeList.get(index).z);
        	
        	verticesBuffer.putFloat((j + 9) * 4, _spriteNodeList.get(index).tarPoint[3].x);
        	verticesBuffer.putFloat((j + 10) * 4,- _spriteNodeList.get(index).tarPoint[3].y);
        	verticesBuffer.putFloat((j + 11) * 4, _spriteNodeList.get(index).z);
        	
//        	texCoord[k] = _spriteNodeList.get(index).texCoord[0];
//    		texCoord[k + 1] = _spriteNodeList.get(index).texCoord[1];
//    		
//    		texCoord[k + 2] = _spriteNodeList.get(index).texCoord[2];
//    		texCoord[k + 3] = _spriteNodeList.get(index).texCoord[3];	
//    		
//    		texCoord[k + 4] = _spriteNodeList.get(index).texCoord[4];
//    		texCoord[k + 5] = _spriteNodeList.get(index).texCoord[5];
//				
//    		texCoord[k + 6] = _spriteNodeList.get(index).texCoord[6];
//    		texCoord[k + 7] = _spriteNodeList.get(index).texCoord[7];
        	
        	trianglesBuffer.putFloat(k * 4,_spriteNodeList.get(index).texCoord[0]);
        	trianglesBuffer.putFloat((k+1) * 4,_spriteNodeList.get(index).texCoord[1]);
        	
        	trianglesBuffer.putFloat((k+2) * 4,_spriteNodeList.get(index).texCoord[2]);
        	trianglesBuffer.putFloat((k+3)* 4,_spriteNodeList.get(index).texCoord[3]);
        	
        	trianglesBuffer.putFloat((k+4) * 4,_spriteNodeList.get(index).texCoord[4]);
        	trianglesBuffer.putFloat((k+5) * 4,_spriteNodeList.get(index).texCoord[5]);
        	
        	trianglesBuffer.putFloat((k+6) * 4,_spriteNodeList.get(index).texCoord[6]);
        	trianglesBuffer.putFloat((k+7) * 4,_spriteNodeList.get(index).texCoord[7]);
        	
    		colorsBuffer.putFloat(l * 4,1);
    		colorsBuffer.putFloat((l+1) * 4,1);
    		colorsBuffer.putFloat((l+2) * 4,1);
    		colorsBuffer.putFloat((l+3) * 4,_spriteNodeList.get(index).alpha);
    		colorsBuffer.putFloat((l+4) * 4,1);
    		colorsBuffer.putFloat((l+5) * 4,1);
    		colorsBuffer.putFloat((l+6) * 4,1);
    		colorsBuffer.putFloat((l+7) * 4,_spriteNodeList.get(index).alpha);
    		colorsBuffer.putFloat((l+8) * 4,1);
    		colorsBuffer.putFloat((l+9) * 4,1);
    		colorsBuffer.putFloat((l+10) * 4,1);
    		colorsBuffer.putFloat((l+11) * 4,_spriteNodeList.get(index).alpha);
    		colorsBuffer.putFloat((l+12) * 4,1);
    		colorsBuffer.putFloat((l+13) * 4,1);
    		colorsBuffer.putFloat((l+14) * 4,1);
    		colorsBuffer.putFloat((l+15) * 4,_spriteNodeList.get(index).alpha);
    		
//    		colors[l] = 1;
//    		colors[l + 1] = 1;
//    		colors[l + 2] = 1;
//    		colors[l + 3] = _spriteNodeList.get(index).alpha;
//    		
//    		colors[l + 4] = 1;
//    		colors[l + 5] = 1;
//    		colors[l + 6] = 1;
//    		colors[l + 7] = _spriteNodeList.get(index).alpha;
//    		
//    		colors[l + 8] = 1;
//    		colors[l + 9] = 1;
//    		colors[l + 10] = 1;
//    		colors[l + 11] = _spriteNodeList.get(index).alpha;
//    		
//    		colors[l + 12] = 1;
//    		colors[l + 13] = 1;
//    		colors[l + 14] = 1;
//    		colors[l + 15] = _spriteNodeList.get(index).alpha;
            i += 6;
            j += 12;
            k += 8;
            l += 16;
            m += 4;
		}
		/*
		for (Sprite2D node : _spriteNodeList){
			//for (int i = 0; i < indices.length; i += 6) {
				indices[i] = m;
	        	indices[i + 1] =  (byte) (m + 1);
	        	indices[i + 2] = (byte) (m + 2);
	        	
	        	indices[i + 3] = m;
	        	indices[i + 4] = (byte) (m + 2);
	        	indices[i + 5] = (byte) (m + 3);
				
			//	indicesBuffer.put(i, indices[i]);
			//}
			vertices[j] = node.tarPoint[0].x;
        	vertices[j + 1] = -node.tarPoint[0].y;
        	vertices[j + 2] =  node.z;  // 0, Top Left
        	
        	vertices[j + 3] = node.tarPoint[1].x;
        	vertices[j + 4] = -node.tarPoint[1].y;
        	vertices[j + 5] = node.z;  // 1, Bottom Left
        	 
        	vertices[j + 6] = node.tarPoint[2].x;
        	vertices[j + 7] = -node.tarPoint[2].y;
        	vertices[j + 8] = node.z;  // 2, Bottom Right
        			 
        	vertices[j + 9] = node.tarPoint[3].x;
        	vertices[j + 10] = -node.tarPoint[3].y;
        	vertices[j + 11] = node.z;  // 3, Top Right
			
//			verticesBuffer.putFloat(j * 4, node.tarPoint[0].x);
//			verticesBuffer.putFloat((j+1) * 4, node.tarPoint[0].y);
//			verticesBuffer.putFloat((j+2) * 4, node.z);
//			
//			verticesBuffer.putFloat((j+3) * 4, node.tarPoint[1].x);
//			verticesBuffer.putFloat((j+4) * 4, node.tarPoint[1].y);
//			verticesBuffer.putFloat((j+5) * 4, node.z);
//			
//			verticesBuffer.putFloat((j+6) * 4, node.tarPoint[2].x);
//			verticesBuffer.putFloat((j+7) * 4, node.tarPoint[2].y);
//			verticesBuffer.putFloat((j+8) * 4, node.z);
//			
//			verticesBuffer.putFloat((j+9) * 4, node.tarPoint[3].x);
//			verticesBuffer.putFloat((j+10) * 4, node.tarPoint[3].y);
//			verticesBuffer.putFloat((j+11) * 4, node.z);
        	
        	texCoord[k] = node.texCoord[0];
    		texCoord[k + 1] = node.texCoord[1];
    		
    		texCoord[k + 2] = node.texCoord[2];
    		texCoord[k + 3] = node.texCoord[3];	
    		
    		texCoord[k + 4] = node.texCoord[4];
    		texCoord[k + 5] = node.texCoord[5];
				
    		texCoord[k + 6] = node.texCoord[6];
    		texCoord[k + 7] = node.texCoord[7];
        	
    		colors[l] = 1;
    		colors[l + 1] = 1;
    		colors[l + 2] = 1;
    		colors[l + 3] = node.alpha;
    		
    		colors[l + 4] = 1;
    		colors[l + 5] = 1;
    		colors[l + 6] = 1;
    		colors[l + 7] = node.alpha;
    		
    		colors[l + 8] = 1;
    		colors[l + 9] = 1;
    		colors[l + 10] = 1;
    		colors[l + 11] = node.alpha;
    		
    		colors[l + 12] = 1;
    		colors[l + 13] = 1;
    		colors[l + 14] = 1;
    		colors[l + 15] = node.alpha;
            i += 6;
            j += 12;
            k += 8;
            l += 16;
            m += 4;
        }*/
		//verticesBuffer.position(0);
			
//		for (int i = 0; i < indices.length; i++) {
//			indicesBuffer.put(i,indices[i]);
//		}
		indicesBuffer.position(0);
		indicesBuffer.limit(i);
		
//		for (int i = 0; i < vertices.length; i++) {
//			verticesBuffer.putFloat(i * 4,vertices[i]);
//		}
		verticesBuffer.position(0);
		verticesBuffer.limit(j*4);
		
//		for (int i = 0; i < texCoord.length; i++) {
//			trianglesBuffer.putFloat(i * 4,texCoord[i]);
//		}		
		trianglesBuffer.position(0);
		trianglesBuffer.limit(k*4);
//		for (int i = 0; i < colors.length; i++) {
//			colorsBuffer.putFloat(i * 4,colors[i]);
//		}		
		colorsBuffer.position(0);
		colorsBuffer.limit(l*4);
		//indicesBuffer.flip();
		//trianglesBuffer.flip();
		//colorsBuffer.flip();
		//verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		
 		//verticesBuffer.put(vertices);
 		//verticesBuffer.position(0);
 		
 		// 创建索引缓冲
 		//indicesBuffer = ByteBuffer.allocateDirect(indices.length * 2);
 		
 		//indicesBuffer.position(0);

 		// 创建纹理坐标缓冲
 		//trianglesBuffer = ByteBuffer.allocateDirect(texCoord.length * 4);
 		
 		//trianglesBuffer.position(0);
 		
 		//colorsBuffer = ByteBuffer.allocateDirect(colors.length * 4);
 		
 		//colorsBuffer.position(0);
 		// 设置活动纹理
 		gl.glBindTexture(GL11.GL_TEXTURE_2D, _activeTexture.textureId);
		gl.glColorPointer(4, GL11.GL_FLOAT, 0, colorsBuffer);
 		// 设置顶点缓冲
 		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, verticesBuffer);
 		// 设置索引缓冲
 		gl.glTexCoordPointer(2, GL11.GL_FLOAT, 0, trianglesBuffer);
 		// 三角形绘制
 		gl.glDrawElements(GL11.GL_TRIANGLES, indicesBuffer.remaining(),GL11.GL_UNSIGNED_BYTE, indicesBuffer);
		
 		// 精灵节点清空
 		_spriteNodeList.clear();
 		_activeTexture = new Texture2D();
	}

	private static void clearArrs() {
		//生成顶点缓冲数组
//		for (int i = 0; i < indices.length; i++) {
//			indices[i] = 0;
//		}
//		for (int j = 0; j < vertices.length; j++) {
//			vertices[j] = 0;
//		}
//		for (int k = 0; k < texCoord.length; k++) {
//			texCoord[k] = 0;
//		}
//		for (int l = 0; l < colors.length; l++) {
//			colors[l] = 0;
//		}
		i = 0;
		j = 0;
		k = 0;
		l = 0;
		m = 0x0;
	}
	
	public static void initialize(){
		verticesBuffer.clear();
 		verticesBuffer.order(ByteOrder.nativeOrder());
 		verticesBuffer.asFloatBuffer().put(vertices);
 		//verticesBuffer.flip();
 		
 		
 		
 		indicesBuffer.clear();
 		indicesBuffer.order(ByteOrder.nativeOrder());
 		indicesBuffer.put(indices);
 		//indicesBuffer.flip();
 		
 		trianglesBuffer.clear();
 		trianglesBuffer.order(ByteOrder.nativeOrder());
 		trianglesBuffer.asFloatBuffer().put(texCoord);
 		//trianglesBuffer.flip();
 		
 		colorsBuffer.clear();
 		colorsBuffer.order(ByteOrder.nativeOrder());
 		colorsBuffer.asFloatBuffer().put(colors);
 		//colorsBuffer.flip();
	}
}
	
