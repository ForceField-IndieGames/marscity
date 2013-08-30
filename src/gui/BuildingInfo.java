package gui;

import java.awt.Color;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

import org.lwjgl.opengl.Display;

import buildings.BigHouse;
import buildings.House;

import animation.AnimationManager;
import animation.AnimationValue;

import game.Main;
import game.ResourceManager;
import game.Supply;
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
	private GuiLabel supplyneed;
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
		supplyneed = new GuiLabel(10,174,232,40,(Color)null);
		supplyneed.setCentered(true);
		supplyneed.setText("supplyneed");
		supplyneed.setTextColor(Color.red);
		add(supplyneed);
		description = new GuiLabel(10,10,232,174,(Color)null);
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
		int count=0;
		if(building.hasUpgrades()){
			Main.gui.buildingupgrades.show();
			for(Upgrade u:Upgrade.values())
			{
				if(u.getBt()==building.getBuildingType())
				{
					Main.gui.buildingupgrades.add(new GuiUpgrade(0, count*128, u, building));
					count++;
				}
			}
		}
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 1, 200);
		setX(Display.getWidth()/2-336);
		AnimationManager.animateValue(this, AnimationValue.X, Display.getWidth()/2-286, 200);
		title.setText(Buildings.getBuildingTypeName(building.getBuildingType()));
		String sneed = "";
		for(Supply s:Supply.values())
		{
			if(building.getOwnedSupplyAmount(s)<building.getNeededSupplyAmount(s))sneed=ResourceManager.getString("SUPPLYNEED_"+s.name().toUpperCase());
		}
		if(sneed==""){
			sneed = ResourceManager.getString("SUPPLYNEED_NONE");
			supplyneed.setTextColor(Color.green);
		}else supplyneed.setTextColor(Color.red);
		supplyneed.setText(sneed);
		supplyneed.wrapText();
		text = ResourceManager.getBtDescription(building.getBuildingType())+System.lineSeparator()+System.lineSeparator()+
				ResourceManager.getBtDescription2(building.getBuildingType());
		description.setText(text);
		description.wrapText();
		text = description.getText();
		if(building.hasUpgrades())
		{
			monthlycost.setText("-"+building.getMonthlycost()+"$ ("+"-"+(building.getMonthlycost()-Buildings.getBuildingType(building).getMonthlycost())+"$"+")");
		}else monthlycost.setText("-"+building.getMonthlycost()+"$");
		
	}
	
	public void update()
	{
		if(isVisible())
		{
			description.setText(text);
			switch(building.getBuildingType()){
			case Buildings.BUILDINGTYPE_HOUSE:
				description.setText(description.getText().replaceFirst(ResourceManager.PLACEHOLDER1, ""+((House)building).getCitizens()).replaceFirst(ResourceManager.PLACEHOLDER2, ""+House.getCitizensmax()));
				break;
			case Buildings.BUILDINGTYPE_BIGHOUSE:
				description.setText(description.getText().replaceFirst(ResourceManager.PLACEHOLDER1, ""+((BigHouse)building).getCitizens()).replaceFirst(ResourceManager.PLACEHOLDER2, ""+BigHouse.getCitizensmax()));
				break;
			default: break;
			}
		}
	}
	
	public void hide()
	{
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 0, 200,AnimationManager.ACTION_HIDE);
		Main.gui.buildingupgrades.hide();
	}
	
}
