package gui;

import game.Main;
import game.ResourceManager;
import guielements.GuiButton;
import guielements.GuiLabel;
import guielements.GuiPanel;

import java.awt.Color;

import objects.Buildings;

import animation.AnimationManager;
import animation.AnimationValue;
import animation.FinishedAction;

/**
 * This is a button that selects a buildingtpe to build.
 * It contains a thumbnail of the building and its name. Also,
 * it shows a tooltip when hovered.
 * @author Benedikt Ringlein
 */

public class BuildingButton extends GuiPanel {

	private GuiLabel name;
	private GuiButton image;
	private int bt;
	private GuiPanel lock;
	private GuiPanel unlockpanel;
	private GuiLabel unlocktext;
	
	public BuildingButton(float x, float y, int bt)
	{
		setX(x);
		setY(y);
		this.setBt(bt);
		setWidth(80);
		setHeight(100);
		setColor(null);
		setEvent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Click:
					if(Buildings.getBuildingType(getBt()).isLocked())break;
					Main.selectedTool = Main.TOOL_ADD;
					Main.gui.buildingPanels.hide();
					Main.currentBT = getBt();
					Main.buildpreview.setBuilding(getBt());
					Main.gui.infoBuildingCosts.setVisible(true);
					Main.gui.infoBuildingCosts.setText(""+Buildings.getBuildingType(Main.currentBT).getBuidlingcost());
					Main.gui.infoBuildingCosts.AutoSize();
					Main.gui.infoMonthlyCosts.setVisible(true);
					Main.gui.infoMonthlyCosts.setText(""+Buildings.getBuildingType(Main.currentBT).getMonthlycost());
					Main.gui.infoMonthlyCosts.AutoSize();
					AnimationManager.animateValue(image, AnimationValue.Y, 145f, 50,FinishedAction.RESET);
					break;
				case Mousein:
					AnimationManager.animateValue(image, AnimationValue.Y, 35, 300);
					if(lock.isVisible()){
						lock.setX(-24);
						lock.setY(1);
						lock.setWidth(128);
						lock.setHeight(128);
						unlockpanel.setVisible(true);
						AnimationManager.animateValue(unlockpanel, AnimationValue.Y, 208, 200);
						AnimationManager.animateValue(unlockpanel, AnimationValue.OPACITY, 1, 200);
					}
					Main.gui.buildingTooltip.setVisible(true);
					AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.OPACITY, 1f, 200);
					Main.gui.buildingTooltip.setY(e.getScreenY()+e.getHeight()-10);
					AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()+10, 200);
					if(Main.gui.buildingTooltip.getOpacity()>0)
					AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.X, e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2, 200);
					else Main.gui.buildingTooltip.setX(e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2);
					Main.gui.buildingTooltip.setBuilding(getBt());
					break;
				case Mouseout:
					AnimationManager.animateValue(image, AnimationValue.Y, 25, 300);
					if(lock.isVisible()){
						lock.setX(-10);
						lock.setY(15);
						lock.setWidth(100);
						lock.setHeight(100);
						AnimationManager.animateValue(unlockpanel, AnimationValue.Y, 108, 200,FinishedAction.HIDE);
						AnimationManager.animateValue(unlockpanel, AnimationValue.OPACITY, 0, 200);
					}
					AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.OPACITY, 0f, 200,FinishedAction.HIDE);
					AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()-10, 200);
					break;
				default:
					break;
				}
			}
		});
		image = new GuiButton(0,25,80,80, Buildings.getBuildingType(bt).getThumb());
		image.setClickThrough(true);
		add(image);
		name = new GuiLabel(0,0,getWidth(),36,(Color)null);
		name.setText(Buildings.getBuildingTypeName(bt));
		name.setFont(ResourceManager.Arial12);
		name.setCentered(true);
		name.wrapText();
		name.setClickThrough(true);
		add(name);
		lock = new GuiPanel(-10,15,100,100,ResourceManager.TEXTURE_GUILOCKED);
		lock.setClickThrough(true);
		lock.setVisible(false);
		add(lock);
		unlockpanel = new GuiPanel(-88,108,256,128,ResourceManager.TEXTURE_GUITOOLTIP,Color.darkGray);
		GuiPanel lock2 = new GuiPanel(69,5,118,118,ResourceManager.TEXTURE_GUILOCKED);
		lock2.setClickThrough(true);
		lock2.setOpacity(0.2f);
		unlockpanel.add(lock2);
		unlockpanel.setVisible(false);
		unlockpanel.setOpacity(0);
		add(unlockpanel);
		unlocktext = new GuiLabel(20,20,216,88,(Color)null);
		unlocktext.setTextColor(Color.white);
		unlocktext.setText(ResourceManager.getString("UNLOCK_"+Buildings.getBuildingType(getBt()).getName()));
		unlocktext.wrapText();
		unlockpanel.add(unlocktext);
	}
	
	public void lock()
	{
		lock.setVisible(true);
		image.setVisible(false);
	}
	
	public void unlock()
	{
		lock.setVisible(false);
		image.setVisible(true);
	}
	

	public int getBt() {
		return bt;
	}

	public void setBt(int bt) {
		this.bt = bt;
	}
	
}
