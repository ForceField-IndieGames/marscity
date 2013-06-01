package gui;

import java.awt.Color;

import game.Main;
import game.ResourceManager;

public class BuildingToolTip extends GuiPanel {

	private GuiLabel title;
	private GuiLabel description;
	private GuiLabel price;
	
	public BuildingToolTip()
	{
		setTexture(ResourceManager.TEXTURE_GUITOOLTIP);
		setWidth(256);
		setHeight(128);
		setOpacity(0f);
		title = new GuiLabel(0,85,256,30,(Color)null);
		title.setFont(ResourceManager.Arial15B);
		title.setCentered(true);
		title.setText("Geb�udename");
		add(title);
		description = new GuiLabel(20,40,216,50,(Color)null);
		description.setText("Kurze Beschreibung des\r\nGeb�udes. Maximal 3 Zeilen.");
		description.setCentered(true);
		add(description);
		price = new GuiLabel(20,15,100,30,(Color)null);
		price.setText("1500$");
		add(price);
	}
	
	public void setBuilding(int bt)
	{
		title.setText(ResourceManager.getBuildingTypeName(bt));
		description.setText(ResourceManager.getString("DESCRIPTION_"+ResourceManager.getBuildingType(bt).getName()));
		price.setText(ResourceManager.getBuildingType(bt).getBuidlingcost()+"$");
		if(Main.money<ResourceManager.getBuildingType(bt).getBuidlingcost()){
			price.setTextColor(Color.red);
		}else price.setTextColor(Color.black);
	}
	
}