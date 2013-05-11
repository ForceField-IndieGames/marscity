package game;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

public class BuildPreview extends Entity {

	
	public BuildPreview(int displaylist, Texture texture) {
		super(displaylist, texture);
	}
	
	@Override
	public void draw() {
		if(!isVisible())return;
		glPushMatrix();
			glTranslatef(getX(), getY(), getZ());
			glScalef(getScaleX(), getScaleY(), getScaleZ());
			glRotatef(getRotX(), 1, 0, 0);
			glRotatef(getRotY(), 0, 1, 0);
			glRotatef(getRotZ(), 0, 0, 1);
			glDisable(GL_LIGHTING);
			glEnable(GL_TEXTURE_2D);
			if(getTexture()!=null)glBindTexture(GL_TEXTURE_2D, getTexture().getTextureID());
			else glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1f, 1f, 1f, 0.3f);
			glCallList(getDisplayList());
			glColor4f(1f, 1f, 1f, 1f);
			glEnable(GL_LIGHTING);
		glPopMatrix();
	}

}
