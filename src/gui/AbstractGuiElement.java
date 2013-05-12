package gui;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

import animation.Animatable;


public abstract class AbstractGuiElement implements guiElement, Animatable {

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public guiElement mouseover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getScreenX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScreenY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isScreenVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public guiElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Texture getTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(float width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeight(float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParent(guiElement guielement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTexture(Texture texure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

}
