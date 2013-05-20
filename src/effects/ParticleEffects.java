package effects;

import game.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

public class ParticleEffects {

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
		addEffect(200, x, y, z, 2f, 2f, 0.002f, 800, ResourceManager.TEXTURE_PARTICLEFOG);
	}

	public static void update(int delta) {
		for (ParticleEffect effect : fx) {
			effect.update(delta);
			if(effect.getParticleCount()<=0){
				fx.remove(effect);
				break;
			}
		}
	}

	public static void draw() {
		for(ParticleEffect effect:fx){
			effect.draw();
		}
	}

}
