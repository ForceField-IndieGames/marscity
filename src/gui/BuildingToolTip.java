package gui;

import java.awt.Color;

import objects.Buildings;

import game.Main;
import game.ResourceManager;
import guielements.GuiLabel;
import guielements.GuiPanel;

/**
 * This is a tooltip that displays information about a building type, like
 * name, description and building costs.
 * It is displayed when hovering over a building button.
 * @author Benedikt Ringlein
 */

public class BuildingToolTip extends GuiPanel {

	private GuiLabel title;
	private GuiLabel description;
	private GuiLabel price;
	private GuiLabel monthlycost;
	
	public BuildingToolTip()
	{
		setTexture(ResourceManager.TEXTURE_GUITOOLTIP);
		setWidth(256);
		setHeight(128);
		setOpacity(0f);
		setClickThrough(true);
		setVisible(false);
		title = new GuiLabel(0,85,256,30,(Color)null);
		title.setFont(ResourceManager.Arial15B);
		title.setCentered(true);
		title.setText("Gebäudename");
		add(title);
		description = new GuiLabel(20,35,216,60,(Color)null);
		description.setText("Kurze Beschreibung des\r\nGebäudes. Maximal 3 Zeilen.");
		add(description);
		price = new GuiLabel(20,15,100,30,(Color)null);
		price.setText("0$");
		add(price);
		monthlycost = new GuiLabel(136,15,100,30,(Color)null);
		monthlycost.setRightaligned(true);
		monthlycost.setText("0$");
		add(monthlycost);
	}
	
	public void setBuilding(int bt)
	{
		title.setText(Buildings.getBuildingTypeName(bt));
		description.setText(ResourceManager.getBtDescription(bt));
		description.wrapText();
		price.setText(Buildings.getBuildingType(bt).getBuidlingcost()+"$");
		if(Main.money<Buildings.getBuildingType(bt).getBuidlingcost()){
			price.setTextColor(Color.red);
		}else price.setTextColor(Color.black);
		monthlycost.setText(Buildings.getBuildingType(bt).getMonthlycost()+"$");
	}
	
}
