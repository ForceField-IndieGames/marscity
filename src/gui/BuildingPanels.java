package gui;

import java.awt.Color;

import animation.AnimationManager;
import animation.AnimationValue;
import game.Main;
import game.ResourceManager;

public class BuildingPanels extends GuiPanel {

	private GuiPanel left = new GuiPanel(-50,0,50,100,ResourceManager.TEXTURE_GUIBUILDINGSPANELL);
	
	public BuildingPanels(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setVisible(false);
		setOpacity(0f);
		setColor(null);
		setTexture(ResourceManager.TEXTURE_GUIBUILDINGSPANEL);
		add(left);
	}
	
	public void show()
	{
		setElementsVisible(false);
		setVisible(true);
		left.setVisible(true);
		AnimationManager.animateValue(this, AnimationValue.Y, 68f, 100);
		AnimationManager.animateValue(this, AnimationValue.opacity, 1f, 100);
	}
	
	public void hide()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, 20f, 100, AnimationManager.ACTION_HIDE);
		AnimationManager.animateValue(this, AnimationValue.opacity, 0f, 100);
	}
	
}
