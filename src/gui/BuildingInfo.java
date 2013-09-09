package gui;

import java.awt.Color;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

import org.lwjgl.opengl.Display;

import buildings.BigHouse;
import buildings.CityCenter;
import buildings.House;
import animation.AnimationManager;
import animation.AnimationValue;
import animation.FinishedAction;

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
	private GuiLabel description2;
	private GuiLabel monthlycost;
	private Building building;
	private String text ="";
	
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
		description = new GuiLabel(10,114,232,74,(Color)null);
		description.setText("Description");
		add(description);
		description2 = new GuiLabel(10,10,232,104,(Color)null);
		description2.setCentered(true);
		description2.setText("Description 2");
		add(description2);
		monthlycost = new GuiLabel(126,10,100,30,(Color)null);
		monthlycost.setRightaligned(true);
		monthlycost.setTextColor(Color.red);
		monthlycost.setText("-0$$");
		monthlycost.setTooltip(ResourceManager.getString("TOOLTIP_BUILDINGINFOCOST"));
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
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 0.9f, 200);
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
			supplyneed.setTextColor(Color.black);
		}else supplyneed.setTextColor(Color.red);
		supplyneed.setText(sneed);
		supplyneed.wrapText();
		description.setText(ResourceManager.getBtDescription(building.getBuildingType()));
		description.wrapText();
		text = ResourceManager.getBtDescription2(building.getBuildingType());
		description2.setText(text);
		description2.wrapText();
		text = description2.getText();
		if(building.hasUpgrades())
		{
			monthlycost.setText("-"+building.getMonthlycost()+"$$ ("+"-"+(building.getMonthlycost()-Buildings.getBuildingType(building).getMonthlycost())+"$$"+")");
		}else monthlycost.setText("-"+building.getMonthlycost()+"$$");
		
	}
	
	public void update()
	{
		if(isVisible())
		{
			switch(building.getBuildingType()){
			case Buildings.BUILDINGTYPE_HOUSE:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+((House) building).getCitizens(),""+House.getCitizensmax()));
				break;
			case Buildings.BUILDINGTYPE_BIGHOUSE:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+((BigHouse)building).getCitizens(),""+BigHouse.getCitizensmax()));
				break;
			case Buildings.BUILDINGTYPE_MEDICALCENTER:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount(),""+building.getProducedSupplyRadius()));
				break;
			case Buildings.BUILDINGTYPE_SERVERCENTER:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount()));
				break;
			case Buildings.BUILDINGTYPE_GARBAGEYARD:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount(),""+building.getProducedSupplyRadius()));
				break;
			case Buildings.BUILDINGTYPE_POLICE:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount(),""+building.getProducedSupplyRadius()));
				break;
			case Buildings.BUILDINGTYPE_SOLARPOWER:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount()));
				break;
			case Buildings.BUILDINGTYPE_FUSIONPOWER:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+building.getProducedSupplyAmount()));
				break;
			case Buildings.BUILDINGTYPE_CITYCENTER:
				description2.setText(ResourceManager.replacePlaceholders(text, ""+((CityCenter)building).getBuildRadius()));
				break;
			default: break;
			}
		}
	}
	
	public void hide()
	{
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 0, 200,FinishedAction.HIDE);
		Main.gui.buildingupgrades.hide();
	}
	
}
