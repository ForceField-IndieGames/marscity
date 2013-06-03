package gui;

import game.ResourceManager;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * A panel that can conatin other guiElements or even other panels.
 * Visibility and position are copied to the child elements.
 * Also click events are given to child elements when they are within the boundarys
 * @author Benedikt Ringlein
 */

public class GuiPanel extends AbstractGuiElement {
	
	private boolean blurBehind = false;

	public GuiPanel()
	{
	
	}

	public GuiPanel(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public GuiPanel(float x, float y, float width, float height, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}

	public GuiPanel(float x, float y, float width, float height, Texture texture)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
	}

	public GuiPanel(float x, float y, float width, float height, Texture texture, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		setColor(color);
	}
	
	public void setElementsColor(Color color)
	{
		for(GuiElement e: elements){
			e.setColor(color);
		}
	}
	
	public void setElementsVisible(Boolean visible)
	{
		for(GuiElement e: elements){
			e.setVisible(visible);
		}
	}

	public boolean isBlurBehind() {
		return blurBehind;
	}

	public void setBlurBehind(boolean blurBehind) {
		this.blurBehind = blurBehind;
	}

	public List<GuiElement> getElements() {
		return elements;
	}

	@Override
	public void draw() {
		if(isScreenVisible()){
			if(blurBehind)
			{
				for(int i=1;i<=12;i+=3){
					for(int j=1;j<=12;j+=3){
						glEnable(GL_TEXTURE_2D);
						glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_EMPTY.getTextureID());
						glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int)-j*((j%2)*2-1),(int)-i*((i%2)*2-1), (int)getWidth(), (int)getHeight(),0);
						glPushMatrix();
						glTranslated(getScreenX(), getScreenY(), 0);
						glBegin(GL_QUADS);
						glColor4f(1f, 1f, 1f, 0.3f);
							glTexCoord2d(0, 0f);
							glVertex2f(0, 0);
							glTexCoord2d(1f, 0f);
							glVertex2f(getWidth(), 0);
							glTexCoord2d(1f, 1f);
							glVertex2f(getWidth(), getHeight());
							glTexCoord2d(0, 1f);
							glVertex2f(0, getHeight());
						glEnd();
						glBindTexture(GL_TEXTURE_2D, 0);
						glPopMatrix();
					}
				}
					
			}
			
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
					glTranslated(getScreenX(), getScreenY(), 0);
					glBegin(GL_QUADS);
						if(getColor()!=null)glColor4ub((byte) getColor().getRed(), 
									(byte) getColor().getGreen(),
									(byte) getColor().getBlue(),
									(byte) (getScreenOpacity()*255));
						else if(getTexture()!=null)glColor3f(1, 1, 1);
						
						glTexCoord2d(0, 1f);
						glVertex2f(0, 0);
						glTexCoord2d(1f, 1f);
						glVertex2f(getWidth(), 0);
						glTexCoord2d(1f, 0);
						glVertex2f(getWidth(), getHeight());
						glTexCoord2d(0, 0);
						glVertex2f(0, getHeight());
					glEnd();
				glPopMatrix();
			}
				
			for(GuiElement element: elements) element.draw();
			
		}
	}

	@Override
	public GuiElement mouseover() {
		
		for(int i=elements.size()-1;i>=0;i--)
		{
			if(elements.get(i).isScreenVisible()
					&&elements.get(i).getScreenX()<Mouse.getX()
					&&elements.get(i).getScreenY()<Mouse.getY()
					&&elements.get(i).getWidth()+elements.get(i).getScreenX()>Mouse.getX()
					&&elements.get(i).getHeight()+elements.get(i).getScreenY()>Mouse.getY()
					&&!elements.get(i).isClickThrough()) return elements.get(i).mouseover();
		}
		return this;
	}

}
