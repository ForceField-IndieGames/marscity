package gui;

import static org.lwjgl.opengl.GL11.*;

import game.ResourceManager;

import java.awt.Color;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

/**
 * A simple label with background image.
 * Can also use 3 textures (one on the left and one on the right side of the text)
 * Height of the left/right textures must be 2 times their width
 * @author Benedikt Ringlein
 */

public class GuiLabel extends AbstractGuiElement {
	
	private String text;
	
	private org.newdawn.slick.Color textColor = new org.newdawn.slick.Color(0,0,0);
	private Texture texturel;
	private Texture texturer;
	private boolean centered = false;
	private UnicodeFont font = ResourceManager.Arial15;

	public GuiLabel()
	{
		
	}

	public GuiLabel(int x, int y, int width, int height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public GuiLabel(int x, int y, int width, int height, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}
	
	public GuiLabel(int x, int y, int width, int height, Texture texture)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
	}
	
	public GuiLabel(int x, int y, int width, int height, Texture texture, Texture texturel, Texture texturer)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		this.texturel = texturel;
		this.texturer = texturer;
	}
	
	public GuiLabel(int x, int y, int width, int height, Texture texture, Color color)
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
						
						glTexCoord2d(0, 1f);
						glVertex2f(0, 0);
						glTexCoord2d(1f, 1f);
						glVertex2f(getWidth(), 0);
						glTexCoord2d(1f, 0);
						glVertex2f(getWidth(), getHeight());
						glTexCoord2d(0, 0);
						glVertex2f(0, getHeight());
					glEnd();
					//Draw the left texture
					if(texturel!=null){
						glBindTexture(GL_TEXTURE_2D, texturel.getTextureID());
						glBegin(GL_QUADS);
							glTexCoord2d(0, 1f);
							glVertex2f(-0.5f*getHeight(), 0);
							glTexCoord2d(1f, 1f);
							glVertex2f(0, 0);
							glTexCoord2d(1f, 0);
							glVertex2f(0, getHeight());
							glTexCoord2d(0, 0);
							glVertex2f(-0.5f*getHeight(), getHeight());
						glEnd();
					}
					//Draw the right texture
					if(texturer!=null){
						glBindTexture(GL_TEXTURE_2D, texturer.getTextureID());
						glBegin(GL_QUADS);
							glTexCoord2d(0, 1f);
							glVertex2f(getWidth(), 0);
							glTexCoord2d(1f, 1f);
							glVertex2f(getWidth()+0.5f*getHeight(), 0);
							glTexCoord2d(1f, 0);
							glVertex2f(getWidth()+0.5f*getHeight(), getHeight());
							glTexCoord2d(0, 0);
							glVertex2f(getWidth(), getHeight());
						glEnd();
					}
				glPopMatrix();
			}		
				
			//Draw the text
				glMatrixMode(GL_PROJECTION);
				glPushMatrix();
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
				glMatrixMode(GL_MODELVIEW);
				TextureImpl.bindNone();
				glEnable(GL_SCISSOR_TEST);
				glScissor((int)getScreenX(), (int)getScreenY(), (int)getWidth(), (int)getHeight()+10);
				float xpos;
				if(isCentered()){
					xpos = getScreenX()+getWidth()/2-font.getWidth(getText())/2;
				}else xpos = getScreenX();
				float ypos = (Display.getHeight()-getScreenY())-getHeight()/2-font.getHeight(getText())/2;
				font.drawString(xpos, ypos, getText(),new org.newdawn.slick.Color(getTextColor().getRed(), getTextColor().getGreen(), getTextColor().getBlue()));
				glDisable(GL_SCISSOR_TEST);
				TextureImpl.bindNone();
				glPopMatrix();
		}
	}

	public boolean isCentered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
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
		this.textColor = new org.newdawn.slick.Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
	}
	
}
