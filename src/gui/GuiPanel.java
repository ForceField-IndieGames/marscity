package gui;

import game.ResourceManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;


public class GuiPanel extends AbstractGuiElement {
	
	private float x,y;
	private float width,height;
	private boolean visible = true;
	private guiElement parent;
	private Color color = new Color(0.5f,0.5f,0.5f);
	private Texture texture;
	private boolean blurBehind = false;
	private float opacity = 1f;
	
	List<guiElement> elements = new ArrayList<guiElement>();
	
	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public boolean isBlurBehind() {
		return blurBehind;
	}

	public void setBlurBehind(boolean blurBehind) {
		this.blurBehind = blurBehind;
	}

	public GuiPanel()
	{

	}

	public GuiPanel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GuiPanel(int x, int y, int width, int height, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public GuiPanel(int x, int y, int width, int height, Texture texture)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.color = Color.white;
	}
	
	public GuiPanel(int x, int y, int width, int height, Texture texture, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.color = color;
	}
	
	public void add(guiElement guielement)
	{
		guielement.setParent(this);
		elements.add(guielement);
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
	
	public float getHeight() {
		return height;
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

	public List<guiElement> getElements() {
		return elements;
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
			if(blurBehind)
			{
				for(int i=1;i<=12;i+=3){
					for(int j=1;j<=12;j+=3){
						glEnable(GL_TEXTURE_2D);
						glBindTexture(GL_TEXTURE_2D, ResourceManager.TEXTURE_EMPTY.getTextureID());
						glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, (int)-j*((j%2)*2-1),(int)-i*((i%2)*2-1), (int)width, (int)height,0);
						glPushMatrix();
						glBegin(GL_QUADS);
						glColor4f(1f, 1f, 1f, 0.3f);
							glTexCoord2d(0, 0f);
							glVertex2f(0, 0);
							glTexCoord2d(1f, 0f);
							glVertex2f(width, 0);
							glTexCoord2d(1f, 1f);
							glVertex2f(width, height);
							glTexCoord2d(0, 1f);
							glVertex2f(0, height);
						glEnd();
						glBindTexture(GL_TEXTURE_2D, 0);
						glPopMatrix();
					}
				}
					
			}
			
			if (color != null || texture != null){
				if(texture!=null){
							glEnable(GL_TEXTURE_2D);
							glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
						}else glDisable(GL_TEXTURE_2D);
				glPushMatrix();
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
				
			for(guiElement element: elements) element.draw();
			
		}
	}

	@Override
	public guiElement mouseover() {
		
		for(int i=elements.size()-1;i>=0;i--)
		{
			if(elements.get(i).isScreenVisible()
					&&elements.get(i).getScreenX()<Mouse.getX()
					&&elements.get(i).getScreenY()<Mouse.getY()
					&&elements.get(i).getWidth()+elements.get(i).getScreenX()>Mouse.getX()
					&&elements.get(i).getHeight()+elements.get(i).getScreenY()>Mouse.getY()) return elements.get(i).mouseover();
		}
		return this;
	}

	
	
	

	
	

}
