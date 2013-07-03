package gui;

import java.awt.Color;

import objects.Building;

import org.lwjgl.opengl.Display;

import buildings.BigHouse;
import buildings.House;

import animation.AnimationManager;
import animation.AnimationValue;

import game.ResourceManager;
import guielements.GuiLabel;
import guielements.GuiPanel;

/**
 * This is a panel that contains information for a building.
 * It shows up, when a building is clicked. The buildings information is
 * updated in realtime.
 * @author Benedikt Ringlein
 */

public class BuildingInfo extends GuiPanel {

	private GuiLabel title;
	private GuiLabel description;
	private GuiLabel monthlycost;
	private Building building;
	private String text="";
	
 	public BuildingInfo()
	{
		setX(Display.getWidth()/2-286);
		setY(Display.getHeight()/2-128);
		setWidth(256);
		setHeight(256);
		setTexture(ResourceManager.TEXTURE_BUILDINGINFO);
		setVisible(false);
		title = new GuiLabel(0,224,256,30,(Color)null);
		title.setCentered(true);
		title.setFont(ResourceManager.Arial15B);
		title.setTextColor(Color.white);
		title.setText("Title");
		add(title);
		description = new GuiLabel(10,10,232,214,(Color)null);
		description.setCentered(true);
		description.setText("Description");
		add(description);
		monthlycost = new GuiLabel(126,10,100,30,(Color)null);
		monthlycost.setRightaligned(true);
		monthlycost.setTextColor(Color.red);
		monthlycost.setText("-0$");
		add(monthlycost);
	}
	
	public void show(Building building)
	{
		this.building = building;
		setVisible(true);
		AnimationManager.animateValue(this, AnimationValue.opacity, 1, 200);
		title.setText(ResourceManager.getBuildingTypeName(building.getBuildingType()));
		text = ResourceManager.getBtDescription(building.getBuildingType())+System.lineSeparator()+System.lineSeparator()+
				ResourceManager.getBtDescription2(building.getBuildingType());
		description.setText(text);
		description.wrapText();
		text = description.getText();
		monthlycost.setText("-"+ResourceManager.getBuildingType(building.getBuildingType()).getMonthlycost()+"$");
	}
	
	public void update()
	{
		if(isVisible())
		{
			description.setText(text);
			switch(building.getBuildingType()){
			case ResourceManager.BUILDINGTYPE_HOUSE:
				description.setText(description.getText().replaceFirst(ResourceManager.PLACEHOLDER1, ""+((House)building).getCitizens()).replaceFirst(ResourceManager.PLACEHOLDER2, ""+House.getCitizensmax()));
				break;
			case ResourceManager.BUILDINGTYPE_BIGHOUSE:
				description.setText(description.getText().replaceFirst(ResourceManager.PLACEHOLDER1, ""+((BigHouse)building).getCitizens()).replaceFirst(ResourceManager.PLACEHOLDER2, ""+BigHouse.getCitizensmax()));
				break;
			default: break;
			}
		}
	}
	
	public void hide()
	{
		AnimationManager.animateValue(this, AnimationValue.opacity, 0, 200,AnimationManager.ACTION_HIDE);
	}
	
}
