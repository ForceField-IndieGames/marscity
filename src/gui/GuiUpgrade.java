package gui;

import java.awt.Color;

import animation.AnimationManager;
import animation.AnimationValue;
import animation.FinishedAction;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;
import game.Main;
import game.ResourceManager;
import guielements.GuiButton;
import guielements.GuiLabel;
import guielements.GuiPanel;

/**
 * A panel that contains information about an upgrade, an image and
 * a button
 * @author Benedikt Ringlein
 */

public class GuiUpgrade extends GuiPanel {
	
	private GuiButton button;
	private GuiLabel title;
	private GuiLabel description;
	private GuiLabel cost;
	private GuiLabel monthlycost;
	private GuiPanel image;
	private Upgrade upgrade;
	private Building building;
	private GuiPanel upgraded;

	public GuiUpgrade(float x, float y, Upgrade upgrade, Building building)
	{
		setX(x);
		setY(y);
		setWidth(512);
		setHeight(128);
		setTexture(ResourceManager.TEXTURE_GUIUPGRADEBACKGROUND);
		this.upgrade = upgrade;
		this.building = building;
		button = new GuiButton(362,0,128,128,ResourceManager.TEXTURE_GUIUPGRADEBUTTON);
		button.setTooltip(ResourceManager.getString("TOOLTIP_UPGRADEBUTTON"));
		if(upgrade.getNeededupgrades()!=null)
		{
			for(Upgrade u:upgrade.getNeededupgrades())
			{
				if(!building.getUpgrade(u)){
					button.setVisible(false);
					setOpacity(0.7f);
					break;
				}
			}
		}
		if(Main.money<upgrade.getUpgradecost()||building.getUpgrade(upgrade))
		{
			button.setVisible(false);
			setOpacity(0.7f);
		}
		if(building.getUpgrade(upgrade))setColor(new Color(50,255,50));
		button.setEvent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype, GuiElement element) {
				if(eventtype==GuiEventType.Click)
				{
					GuiUpgrade g = (GuiUpgrade)element.getParent();
					if(Main.money>=g.getUpgrade().getUpgradecost())
					{
						Main.money-=g.getUpgrade().getUpgradecost();
						g.getBuilding().setUpgrade(g.getUpgrade(), true);
						g.getBuilding().updateUpgrades(g.getUpgrade());
						AnimationManager.animateValue(g, AnimationValue.Y, g.getY()+20, 100,FinishedAction.REVERSE);
						Buildings.refreshSupply();
					}
				}	
			}
		});
		AnimationManager.animateValue(button, AnimationValue.OPACITY, 0.6f, 0.0003f,FinishedAction.REVERSEREPEAT);
		add(button);
		title = new GuiLabel(150,90,212,30,(Color)null);
		title.setText(ResourceManager.getString("UPGRADENAME_"+upgrade.name().toUpperCase()));
		title.setTextColor(Color.lightGray);
		title.setCentered(true);
		title.setFont(ResourceManager.Arial15B);
		add(title);
		description = new GuiLabel(150,30,212,60,(Color)null);
		description.setText(ResourceManager.getString("UPGRADEDESCRIPTION_"+upgrade.name().toUpperCase()));
		description.setTextColor(Color.white);
		description.setFont(ResourceManager.Arial12);
		description.wrapText();
		add(description);
		cost = new GuiLabel(375,10,95,20,(Color)null);
		cost.setFont(ResourceManager.Arial12);
		cost.setText(upgrade.getUpgradecost()+"$");
		if(Main.money<upgrade.getUpgradecost())cost.setTextColor(Color.red);
		else cost.setTextColor(Color.white);
		if(building.getUpgrade(upgrade))cost.setVisible(false);
		add(cost);
		monthlycost = new GuiLabel(375,10,95,20,(Color)null);
		monthlycost.setRightaligned(true);
		monthlycost.setFont(ResourceManager.Arial12);
		monthlycost.setText(upgrade.getMonthlyupgradecost()+"$$");
		monthlycost.setTextColor(Color.white);
		add(monthlycost);
		image = new GuiPanel(23,0,128,128,upgrade.getImage());
		add(image);
		upgraded = new GuiPanel(362,0,128,128,ResourceManager.TEXTURE_GUIUPGRADED);
		if(building.getUpgrade(upgrade)==false)upgraded.setVisible(false);
		add(upgraded);
	}
	
	public Upgrade getUpgrade() {
		return upgrade;
	}
	public Building getBuilding() {
		return building;
	}
	
}
