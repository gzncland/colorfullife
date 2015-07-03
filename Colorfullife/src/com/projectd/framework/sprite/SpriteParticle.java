package com.projectd.framework.sprite;

import java.util.Random;

public class SpriteParticle extends Sprite2D {
	static Random randSeed = new Random();
	
	public SpriteParticle(Texture2D setTexture,int setSrcLeft, int setSrcTop, int setWidth, int setHeight) {
		//super(setWidth, setHeight, CentrePoint.CENTER);
		super(setTexture, setSrcLeft, setSrcTop, setWidth, setHeight);
		alpha = 0;
		
	}
	public float speedX;
	public float speedY;
	
	public float accX;
	public float accY;
	
	public float life;
	public float lifeMax;
	
	public float scaleSpeed;
	
	public float alphaspeed;
	
	public int rotationSpeed;
	
	
	public void update(float deltaTime) {
		if(active() == false){
			return;
		}
		float normalizedLifetime = life / lifeMax;
		//float scale = 1+;
		alpha =  normalizedLifetime * (1 - normalizedLifetime);
		
		offset(speedX, speedY);
		setAngle(getAngle() + rotationSpeed);
	
		speedX += accX;
		speedY += accY;
		
		
//		scaleX = scale;
//		scaleY = scale;

		life += 1;
	}
	
//	public void onDrawFrame(GL10 gl) {
//
//		if(Sprite2D.oldTextureId != textureId){	
//			Sprite2D.oldTextureId = textureId;
//			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
//			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, trianglesBuffer);
//
//		}
//		
//		gl.glTranslatef(x, y, z);
//		
//		gl.glColor4f(1f, 1f, 1f,  alpha);
//		gl.glRotatef(angle, 0, 0, 1);
//		gl.glScalef(scaleX, scaleY, 1);
//		
//		gl.glDrawElements(GL10.GL_TRIANGLES, indicesBuffer.remaining(),GL10.GL_UNSIGNED_BYTE, indicesBuffer);
//		gl.glLoadIdentity();
//	}


	public final boolean active() {
		return life < lifeMax;
	}

	public void initialize(float setX, float setY, float setSpeedX,
			float setSpeedY, float setAccX, float setAccY, float setLife,
			float setScale, int setRotationSpeed) {
		setLocation(setX, setY);
		speedX = setSpeedX;
		speedY = setSpeedY;
		accX = setAccX;
		accY = setAccY;
		lifeMax = setLife;
		life = 0;
//		scaleX = setScale;
//		scaleY = setScale;
		setScale(setScale);
		rotationSpeed = setRotationSpeed;
	}
}
