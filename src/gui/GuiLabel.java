package gui;

import static org.lwjgl.opengl.GL11.*;

import game.ResourceManager;

import java.awt.Color;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;


public class GuiLabel extends AbstractGuiElement {
	
	private String text;
	
	private float x,y,width,height;
	private boolean visible = true;
	private guiElement parent;
	private Color color = new Color(0.5f,0.5f,0.5f);
	private Texture texture;
	private float opacity = 1f;

	
	
	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public GuiLabel()
	{
		
	}

	public GuiLabel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GuiLabel(int x, int y, int width, int height, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public GuiLabel(int x, int y, int width, int height, Texture texture)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.color = Color.white;
	}
	
	public GuiLabel(int x, int y, int width, int height, Texture texture, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public guiElement getParent() {
		return parent;
	}

	public void setParent(guiElement parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public float getScreenX() {
		if(parent!=null)return x+parent.getScreenX();
		return x;
	}

	@Override
	public float getScreenY() {
		if(parent!=null)return y+parent.getScreenY();
		return y;
	}

	@Override
	public boolean isScreenVisible() {
		if(parent!=null)return visible&&parent.isScreenVisible();
		return visible;
	}

	@Override
	public void draw() {
		if(isScreenVisible()){
			if (color != null || texture != null){
				if(texture!=null){
					glEnable(GL_TEXTURE_2D);
					glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
				}else glDisable(GL_TEXTURE_2D);
				glPushMatrix();
				glMatrixMode(GL_PROJECTION);
				glPushMatrix();
				glLoadIdentity();
				glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
				glMatrixMode(GL_MODELVIEW);
				glTranslated(getScreenX(), getScreenY(), 0);
					glBegin(GL_QUADS);
						if(color!=null)glColor4ub((byte) color.getRed(), 
									(byte) color.getGreen(),
									(byte) color.getBlue(),
									(byte) (opacity*255));
						
						glTexCoord2d(0, 1f);
						glVertex2f(0, 0);
						glTexCoord2d(1f, 1f);
						glVertex2f(width, 0);
						glTexCoord2d(1f, 0);
						glVertex2f(width, height);
						glTexCoord2d(0, 0);
						glVertex2f(0, height);
						
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
				glScissor((int)getScreenX(), (int)getScreenY(), (int)width, (int)height);
				float xpos = getScreenX();
				float ypos = Display.getHeight()-getScreenY()-height/2-ResourceManager.font.getHeight(text)/2;
				ResourceManager.font.drawString(xpos, ypos, text);
				glDisable(GL_SCISSOR_TEST);
				TextureImpl.bindNone();		
				glPopMatrix();
					
				
				
			
			
		}
	}

	@Override
	public guiElement mouseover() {
		return this;
	}
	
}
