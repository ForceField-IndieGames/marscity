package gui;

import static org.lwjgl.opengl.GL11.*;

import game.ResourceManager;

import java.awt.Color;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

/**
 * A simple button with background image and Text. When the background color
 * is white, it automatically gets darker when hovered.
 * @author Benedikt Ringlein
 */

public class GuiButton extends AbstractGuiElement {
	
	private String text = "";
	
	private org.newdawn.slick.Color textColor = new org.newdawn.slick.Color(0,0,0);
	private boolean showBackground = true;
	private UnicodeFont font = ResourceManager.Arial15;
	
	
	@Override
	public void callGuiEvents(GuiEventType eventtype)
	{
		try {
				switch (eventtype) {
				case Click:
						ResourceManager.playSound(ResourceManager.SOUND_SELECT);
				case Mouseover:
						if(getColor().equals(Color.white))setColor(new Color(235,235,235));
						break;
				case Mouseout:
						if(getColor().equals(new Color(235,235,235)))setColor(Color.white);
						break;
				default:break;
			}
			getEvent().run(eventtype, this);	
		} catch (Exception e) {}
	}

	public boolean isShowBackground() {
		return showBackground;
	}

	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}

	public GuiButton()
	{
		
	}

	public GuiButton(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public GuiButton(float x, float y, float width, float height, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}
	
	public GuiButton(float x, float y, float width, float height, Texture texture)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
	}
	
	public GuiButton(float x, float y, float width, float height, Texture texture, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		setColor(color);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw() {
		if(isScreenVisible()){
			
			if (showBackground){
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
						glBegin(GL_QUADS);
							if(getColor()!=null)glColor4ub((byte) getColor().getRed(), 
										(byte) getColor().getGreen(),
										(byte) getColor().getBlue(),
										(byte) (getScreenOpacity()*255));
							glTexCoord2d(0, 1f);
							glVertex2f(getScreenX(), getScreenY());
							glTexCoord2d(1f, 1f);
							glVertex2f(getWidth()+getScreenX(), getScreenY());
							glTexCoord2d(1f, 0);
							glVertex2f(getWidth()+getScreenX(), getHeight()+getScreenY());
							glTexCoord2d(0, 0);
							glVertex2f(getScreenX(), getHeight()+getScreenY());
							
						glEnd();
					glPopMatrix();
						
				}
			
				glMatrixMode(GL_PROJECTION);
				glPushMatrix();
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
				glMatrixMode(GL_MODELVIEW);
				TextureImpl.bindNone();
				glEnable(GL_SCISSOR_TEST);
				glScissor((int)getScreenX(), (int)getScreenY(), (int)getWidth(), (int)getHeight());
				float xpos = getScreenX()+getWidth()/2-font.getWidth(getText())/2;
				float ypos = Display.getHeight()-getScreenY()-getHeight()/2-font.getHeight(getText())/2;
				font.drawString(xpos, ypos, getText(),new org.newdawn.slick.Color(getTextColor().getRed(), getTextColor().getGreen(), getTextColor().getBlue(),getScreenOpacity()));
				glDisable(GL_SCISSOR_TEST);
				TextureImpl.bindNone();		
				glPopMatrix();
				
				for(GuiElement element: elements) element.draw();
		}
	}

	public UnicodeFont getFont() {
		return font;
	}

	public void setFont(UnicodeFont font) {
		this.font = font;
	}

	public Color getTextColor() {
		return new Color(textColor.getRed(),textColor.getGreen(),textColor.getBlue());
	}

	public void setTextColor(Color textColor) {
		this.textColor = new org.newdawn.slick.Color(textColor.getRed(), textColor.getBlue(), textColor.getGreen());
	}

	
}
