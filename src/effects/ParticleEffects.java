package effects;

import game.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class ParticleEffects {
	
	public static final int PARTICLESOFF = 0;
	public static final int PARTICLESLOW = 1;
	public static final int PARTICLESMIDDLE = 3;
	public static final int PARTICLESHIGH = 8;
	public static int particleQuality = PARTICLESHIGH;

	private static List<ParticleEffect> fx = new ArrayList<ParticleEffect>();

	public static ParticleEffect getEffect(int index) {
		return fx.get(index);
	}

	public static void addEffect(int particlecount, float x, float y, float z,
			float size, float distribution, float velocity, float lifetime, Texture texture) {
		fx.add(new ParticleEffect(particlecount, x, y, z, size, distribution, velocity, lifetime, texture));
	}
	
	public static void dustEffect(float x, float y, float z)
	{
		addEffect(50*particleQuality, x, y, z, 2f, 2f, 0.002f, 800, ResourceManager.TEXTURE_PARTICLEFOG);
	}
	
	public static int getEffectCount()
	{
		return fx.size();
	}

	public static void update(int delta) {
		for (int i=0;i<fx.size();i++) {
			if(fx.get(i)==null)continue;
			fx.get(i).update(delta);
			if(fx.get(i).getParticleCount()<=0){
				fx.remove(i);
			}
		}
	}

	public static void draw() {
		for(ParticleEffect effect:fx){
			effect.draw();
		}
	}

}
