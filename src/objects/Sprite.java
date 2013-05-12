package objects;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;


public class Sprite implements Drawable {
	
	private float x = 0, y = 0, z=-50;
	private float rotX=0,rotY=0,rotZ=0;
	private float scaleX=1,scaleY=1,scaleZ=1;
	private Texture texture;
	private boolean deletePlanned = false;
	private float destY;
	private float preferredY;
	
	public float getPreferredY() {
		return preferredY;
	}

	public void setPreferredY(float preferredY) {
		this.preferredY = preferredY;
	}

	public float getDestY() {
		return destY;
	}

	public void setDestY(float destY) {
		this.destY = destY;
	}

	public boolean isDeletePlanned() {
		return deletePlanned;
	}

	public void setDeletePlanned(boolean deletePlanned) {
		this.deletePlanned = deletePlanned;
	}

	public Sprite(Texture tex)
	{
		this.texture = tex;

			

		
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public float getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public void setRotX(float x) {
		this.rotX = x;
	}

	@Override
	public void setRotY(float y) {
		this.rotY=y;
	}

	@Override
	public void setRotZ(float z) {
		this.rotZ=z;
	}

	@Override
	public float getRotX() {
		return rotX;
	}

	@Override
	public float getRotY() {
		return rotY;
	}

	@Override
	public float getRotZ() {
		return rotZ;
	}

	@Override
	public void draw() {
				// draw quad
				glPushMatrix();
					glTranslatef(x, y, z);
					glScalef(scaleX, scaleY, scaleZ);
					glRotatef(rotX, 1, 0, 0);
					glRotatef(rotY, 0, 1, 0);
					glRotatef(rotZ, 0, 0, 1);
					glTranslatef(-x, -y, -z);
					
					// Bind the texture
					glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
					
					glBegin(GL_QUADS);
						glTexCoord2d(0, 1f);
						glVertex3f(x- 10,y- 10,z); //links unten
						
						glTexCoord2d(1f, 1f);
						glVertex3f(x+10,y- 10,z); //rechts unten
						
						glTexCoord2d(1f, 0);
						glVertex3f(x+10,y+10,z); //rechts oben
						
						glTexCoord2d(0, 0);
						glVertex3f(x-10,y+10,z); //rechts unten
					glEnd();
				glPopMatrix();
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
