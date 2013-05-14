package objects;

import static org.lwjgl.opengl.GL11.*;
import game.ResourceManager;


import org.newdawn.slick.opengl.Texture;

public class BuildPreview extends Entity {
	
	private boolean show = false;

	
	public BuildPreview(int displaylist, Texture texture) {
		super(displaylist, texture);
	}
	
	public BuildPreview(int bt) {
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture());
	}
	
	public BuildPreview() {
		super(ResourceManager.getBuildingType(0).getDisplaylist(), ResourceManager.getBuildingType(0).getTexture());
	}
	
	public void setBuilding(int bt)
	{
		if(bt==-1){
			show = false;
			return;
		}
		setDisplayList(ResourceManager.getBuildingType(bt).getDisplaylist());
		setTexture(ResourceManager.getBuildingType(bt).getTexture());
		show = true;
	}
	
	@Override
	public void draw() {
		if(!isVisible()||!show)return;
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
