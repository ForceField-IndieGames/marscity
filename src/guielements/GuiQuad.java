package guielements;

import gui.AbstractGuiElement;
import gui.GuiElement;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

/**
 * A simple quad
 * @author Benedikt Ringlein
 */

public class GuiQuad extends AbstractGuiElement {
	
	private float x1,x2,x3,x4,y1,y2,y3,y4;

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getX3() {
		return x3;
	}

	public void setX3(float x3) {
		this.x3 = x3;
	}

	public float getX4() {
		return x4;
	}

	public void setX4(float x4) {
		this.x4 = x4;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}

	public float getY3() {
		return y3;
	}

	public void setY3(float y3) {
		this.y3 = y3;
	}

	public float getY4() {
		return y4;
	}

	public void setY4(float y4) {
		this.y4 = y4;
	}

	public GuiQuad(float x1, float x2, float x3, float x4, float y1, float y2,
			float y3, float y4) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.y4 = y4;
		setColor(Color.white);
	}

	public List<GuiElement> getElements() {
		return elements;
	}

	@Override
	public void draw() {
		if(isScreenVisible()){
			if (getColor() != null || getTexture() != null){
				if(getTexture()!=null){
							glEnable(GL_TEXTURE_2D);
							glBindTexture(GL_TEXTURE_2D, getTexture().getTextureID());
						}else glDisable(GL_TEXTURE_2D);
				glPushMatrix();
				glMatrixMode(GL_PROJECTION);
				glPushMatrix();
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
				glMatrixMode(GL_MODELVIEW);
					//Prevent weird artifacts, when needed
					if(isIntegerPosition())glTranslatef((int)getScreenX(), (int)getScreenY(), 0);
					else glTranslatef(getScreenX(), getScreenY(), 0);
					glBegin(GL_QUADS);
						if(getColor()!=null)glColor4ub((byte) getColor().getRed(), 
									(byte) getColor().getGreen(),
									(byte) getColor().getBlue(),
									(byte) (getScreenOpacity()*255));
						else if(getTexture()!=null)glColor4f(1, 1, 1,getScreenOpacity());
						
						glTexCoord2d(0, 1f);
						glVertex2f(getX1(), getY1());
						glTexCoord2d(1f, 1f);
						glVertex2f(getX2(), getY2());
						glTexCoord2d(1f, 0);
						glVertex2f(getX3(), getY3());
						glTexCoord2d(0, 0);
						glVertex2f(getX4(), getY4());
					glEnd();
				glPopMatrix();
			}
				
			for(GuiElement element: elements) element.draw();
			
		}
	}

	@Override
	public GuiElement mouseover() {
		return null;
	}

}
