package com.projectd.framework.sprite;

import java.util.LinkedList;
import java.util.Random;

import com.projectd.framework.CacheBase;

public class ParticleSystem {
	protected SpriteParticle particles[];
	protected LinkedList<SpriteParticle> freeParticles;
	
	protected int minParticlesCount;	
    protected int maxParticlesCount;
    
    public float minInitialSpeed; 
    public float maxInitialSpeed;
    
    public float minAcc;
    public float maxAcc;
    
    public float minRotationSpeed;
    public float maxRotationSpeed;

    public float minLifetime;
    public float maxLifetime;

    public float minScale;
    public float maxScale;
    
    protected Random randomSeed = new Random();
    
    protected final float randomIn(float setA,float setB) {
    	return setA + randomSeed.nextFloat() * (setB - setA);
	}
    
    /**
     * ���캯��,ʹ�ú������ø��ֲ�����������ʹ��
     * @param setTextureId ����ID
     * @param setLeft x
     * @param setTop y
     * @param setWidth ��
     * @param setHeight ��
     * @param setMinParticlesCount ��С��������
     * @param setMaxParticlesCount �����������
     */
	public ParticleSystem(int setTextureId, int setLeft, int setTop, int setWidth, int setHeight,int setMinParticlesCount,int setMaxParticlesCount) {
		maxParticlesCount = setMaxParticlesCount;
		minParticlesCount = setMinParticlesCount;
        particles = new SpriteParticle[maxParticlesCount];
        freeParticles =new LinkedList<SpriteParticle>();
        
        for (int i = 0; i < particles.length; i++)
        {
        	Texture2D texture = CacheBase.getTextureById(setTextureId);
            particles[i] = new SpriteParticle(texture, setLeft, setTop, setWidth, setHeight);
            freeParticles.addLast(particles[i]);
        }
	}
	protected void addParticle()
    {
        // ���ӵ�������һ��λ�������ж������������֮������ֵ
        int numParticles = (int) randomIn(minParticlesCount,maxParticlesCount);

        // ���������������
        for (int i = 0; i < numParticles && freeParticles.size() > 0; i++)
        {
            // ��freeParticles���л�ȡ���Ӳ����г�ʼ��
        	SpriteParticle p = freeParticles.removeFirst();
            initializeParticle(p, randomIn(0, 1024), randomIn(0, 512));               
        }
    }
	public void update(float deltaTime) {
		for (SpriteParticle sp : particles) {
			if (sp.active()){
				sp.update(deltaTime);
				if (sp.active() == false) {
					freeParticles.addLast(sp);
				}
            }
		}
		addParticle();
	}
	public void onDrawFrame(){
		for (SpriteParticle sp : particles) {
			if(sp.active()){
				SpriteBatch.draw(sp);//sp.onDrawFrame(gl);
			}
		}
	}
	protected void initializeParticle(SpriteParticle setParticle ,float setX,float setY)
    {
		//�ٶ�
		float speed = randomIn(minInitialSpeed, maxInitialSpeed);
		float speedDirection = randomIn(0,(float) (Math.PI * 2));       
        float speedY = (float) (Math.sin(speedDirection) * speed);
        float speedX = (float) (Math.cos(speedDirection) * speed);
        //���ٶ�
        float acc = randomIn(maxAcc, maxAcc);
        float accDirection = randomIn(0,(float) (Math.PI * 2));   
        float accX =  (float) (Math.cos(accDirection) * acc);
        float accY = (float) (Math.sin(accDirection) * acc);
        
        float lifetime = randomIn(minLifetime, maxLifetime);
        float scale = randomIn(minScale, maxScale);
        int rotationSpeed = (int) randomIn(minRotationSpeed, maxRotationSpeed);

        //��ʼ������
        setParticle.initialize(setX ,setY, speedX , speedY , accX, accY ,lifetime, scale, rotationSpeed);
    }
}
