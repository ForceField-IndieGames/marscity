package gui;

import animation.AnimationManager;
import animation.AnimationValue;
import game.ResourceManager;
import guielements.GuiPanel;

/**
 * This panel contains the building panels. When its shown/hidden, it
 * automatically animates itsself.
 * @author Benedikt Ringlein
 */

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
		setIntegerPosition(true);
		setTexture(ResourceManager.TEXTURE_GUIBUILDINGSPANEL);
		add(left);
		left.setIntegerPosition(true);
	}
	
	public void show()
	{
		setElementsVisible(false);
		setVisible(true);
		left.setVisible(true);
		AnimationManager.animateValue(this, AnimationValue.Y, 68f, 100);
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 1f, 100);
	}
	
	public void hide()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, 20f, 100, AnimationManager.ACTION_HIDE);
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 0f, 100);
	}
	
}
