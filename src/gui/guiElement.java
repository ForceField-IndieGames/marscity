package gui;
import java.awt.Color;

import org.newdawn.slick.opengl.Texture;


public interface guiElement {
	
	int x=0,y=0,width=0,height=0;
	boolean visible=true;
	guiElement parent = null;
	Texture texture = null;
	Color color = Color.gray;
	
	public void draw();
	public guiElement mouseover();
	
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public boolean isVisible();
	public float getScreenX();
	public float getScreenY();
	public boolean isScreenVisible();
	public guiElement getParent();
	public Texture getTexture();
	public Color getColor();
	
	public void setX(float x);
	public void setY(float y);
	public void setWidth(float width);
	public void setHeight(float height);
	public void setVisible(boolean visible);
	public void setParent(guiElement guielement);
	public void setTexture(Texture texure);
	public void setColor(Color color);
}
