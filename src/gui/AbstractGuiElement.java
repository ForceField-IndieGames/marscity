package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

import animation.Animatable;

/**
 * This abstract class implements the guiElement interface and can be used
 * to easily create new gui elements without having to implement all
 * of the methods.
 * @author Benedikt Ringlein
 */

public abstract class AbstractGuiElement implements GuiElement, Animatable {

	private GuiEvent event;
	private float x=0,y=0,width=0,height=0;
	private boolean visible=true;
	private GuiElement parent = null;
	private Texture texture = null;
	private Color color = Color.white;
	private float opacity = 1f;
	private boolean clickThrough = false;
	public List<GuiElement> elements = new ArrayList<GuiElement>();
	
	public void add(GuiElement guielement)
	{
		guielement.setParent(this);
		elements.add(guielement);
	}
	
	@Override
	public void setZ(float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotX(float x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotY(float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotZ(float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOpacity() {
		return opacity;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype)
	{
		try {
			getEvent().run(eventtype, this);
		} catch (Exception e) {
		}
	}

	@Override
	public GuiElement mouseover() {
		return this;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public float getScreenX() {
		if(getParent()!=null)return getX()+getParent().getScreenX();
		return getX();
	}

	@Override
	public float getScreenY() {
		if(getParent()!=null)return getY()+getParent().getScreenY();
		return getY();
	}

	@Override
	public boolean isScreenVisible() {
		if(getParent()!=null)return isVisible()&&getParent().isScreenVisible();
		return isVisible();
	}
	
	@Override
	public float getScreenOpacity() {
		if(getParent()!=null)return getOpacity()*getParent().getOpacity();
		return getOpacity();
	}

	@Override
	public GuiElement getParent() {
		return parent;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setX(float x) {
		this.x = x;

	}

	@Override
	public void setY(float y) {
		this.y = y;

	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;

	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void setParent(GuiElement guielement) {
		this.parent = guielement;
	}

	@Override
	public void setTexture(Texture texure) {
		this.texture = texure;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	public GuiEvent getEvent() {
		return event;
	}

	public void setEvent(GuiEvent event) {
		this.event = event;
	}

	@Override
	public boolean isClickThrough() {
		return this.clickThrough;
	}

	@Override
	public void setClickThrough(boolean clickthrough) {
		this.clickThrough = clickthrough;
	}

}
