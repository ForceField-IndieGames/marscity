package effects;

import game.Main;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

public class ParticleEffect {

	private float x = 0, y = 0, z = 0;
	private float velocity = 0;
	private Texture texture;
	private int particlecount;

	private List<Particle> particles = new ArrayList<Particle>();

	public ParticleEffect(int particlecount, float x, float y, float z,
			float size, float distribution, float velocity, float lifetime, Texture texture) {
		this.particlecount = particlecount;
		this.x = x;
		this.y = y;
		this.z = z;
		this.velocity = velocity;
		this.texture = texture;
		for (int i = 0; i < particlecount; i++) {
			particles.add(new Particle(x-distribution + (float) Math.random() * 2 * distribution,
					y-distribution + (float) Math.random() * 2 * distribution,
					z-distribution + (float) Math.random() * 2 * distribution,
					-velocity + (float) Math.random() * 2 * velocity,
					-velocity + (float) Math.random() * 2 * velocity,
					-velocity + (float) Math.random() * 2 * velocity, size, lifetime));
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	
	public int getParticleCount()
	{
		return particlecount;
	}

	public void update(int delta) {
		for (Particle p : particles) {
			p.setX(p.getX()+p.getVx()*delta);
			p.setY(p.getY()+p.getVy()*delta);
			p.setZ(p.getZ()+p.getVz()*delta);
			p.setLifetime(p.getLifetime()-delta);
			if(p.getLifetime()<=0){
				p.setVisible(false);
				particlecount --;
			}
		}
	}

	public void draw() {
		for (Particle p : particles) {
			if(!p.isVisible())continue;
			glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
			glColor4f(1f, 1f, 1f, p.getLifetime()/p.getInitlifetime());
			glDisable(GL_DEPTH_TEST);
			glDisable(GL_LIGHTING);
			glDisable(GL_CULL_FACE);
			glPushMatrix();
			glTranslatef(p.getX(), p.getY(), p.getZ());
			glRotatef(Main.camera.getRotY(), 0, 1, 0);
			glTranslatef(-p.getX(), -p.getY(), -p.getZ());
			
			glBegin(GL_QUADS);
					glTexCoord2d(0, 1f);
					glVertex3f(-p.getSize()/2+p.getX(), -p.getSize()/2+p.getY(),p.getZ());
					
					glTexCoord2d(1f, 1f);
					glVertex3f(p.getSize()/2+p.getX(), -p.getSize()/2+p.getY(),p.getZ());
					
					glTexCoord2d(1f, 0);
					glVertex3f(p.getSize()/2+p.getX(), p.getSize()/2+p.getY(),p.getZ());
					
					glTexCoord2d(0, 0);
					glVertex3f(-p.getSize()/2+p.getX(), p.getSize()/2+p.getY(),p.getZ());
				
			glEnd();
				
			glPopMatrix();
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_LIGHTING);
			glEnable(GL_CULL_FACE);
		}
	}

}
