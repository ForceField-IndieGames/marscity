package gui;
import java.awt.Color;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

/**
 * This interface has to be implemented by every Object that is renered
 * by the gui.
 * @author Benedikt Ringlein
 */

public interface GuiElement {
	
	public void draw();
	public GuiElement mouseover();
	public void callGuiEvents(GuiEventType eventtype);
	public void callIndirectGuiEvents(GuiEventType eventtype);
	
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public boolean isVisible();
	public float getScreenX();
	public float getScreenY();
	public boolean isScreenVisible();
	public GuiElement getParent();
	public Texture getTexture();
	public Color getColor();
	public boolean isClickThrough();
	public float getOpacity();
	public float getScreenOpacity();
	public List<GuiElement> getElements();
	
	public void setX(float x);
	public void setY(float y);
	public void setWidth(float width);
	public void setHeight(float height);
	public void setVisible(boolean visible);
	public void setParent(GuiElement guielement);
	public void setTexture(Texture texure);
	public void setColor(Color color);
	public void setClickThrough(boolean clickthrough);
	public void setOpacity(float opacity);
	public void setElements(List<GuiElement> elements);
}
