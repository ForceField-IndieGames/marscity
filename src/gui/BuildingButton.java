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
	
	public BuildingButton(float x, float y, int bt)
	{
		setX(x);
		setY(y);
		this.setBt(bt);
		setWidth(100);
		setHeight(100);
		setColor(null);
		setIndirectevent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Mousein:
					AnimationManager.animateValue(image, AnimationValue.Y, 35, 300);
					break;
				case Mouseout:
					AnimationManager.animateValue(image, AnimationValue.Y, 25, 300);
					break;
				default:
					break;
				}
			}
		});
		image = new GuiButton(10,25,80,80, Buildings.getBuildingType(bt).getThumb());
		image.setEvent(new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
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
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.OPACITY, 0f, 200,FinishedAction.HIDE);
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()-10, 200);
		break;
	default:break;}}});
		add(image);
		name = new GuiLabel(0,0,getWidth(),36,(Color)null);
		name.setText(Buildings.getBuildingTypeName(bt));
		name.setFont(ResourceManager.Arial12);
		name.setCentered(true);
		name.wrapText();
		name.setClickThrough(true);
		add(name);
		lock = new GuiPanel(0,15,100,100,ResourceManager.TEXTURE_GUILOCKED);
		lock.setClickThrough(true);
		lock.setVisible(false);
		add(lock);
	}
	
	public void lock()
	{
		lock.setVisible(true);
		image.setOpacity(0.2f);
	}
	
	public void unlock()
	{
		lock.setVisible(false);
		image.setOpacity(1);
	}
	

	public int getBt() {
		return bt;
	}

	public void setBt(int bt) {
		this.bt = bt;
	}
	
}
