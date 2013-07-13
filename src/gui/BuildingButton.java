package gui;

import game.Main;
import guielements.GuiLabel;
import guielements.GuiPanel;

import java.awt.Color;

import objects.Buildings;

import animation.AnimationManager;
import animation.AnimationValue;

/**
 * This is a button that selects a buildingtpe to build.
 * It contains a thumbnail of the building and its name. Also,
 * it shows a tooltip when hovered.
 * @author Benedikt Ringlein
 */

public class BuildingButton extends GuiPanel {

	private GuiLabel name;
	private GuiPanel image;
	private int bt;
	
	public BuildingButton(float x, float y, int bt)
	{
		setX(x);
		setY(y);
		this.setBt(bt);
		setWidth(100);
		setHeight(100);
		setColor(null);
		image = new GuiPanel(10,25,80,80, Buildings.getBuildingType(bt).getThumb());
		image.setEvent(new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
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
			break;
	case Mouseover:
			Main.gui.buildingTooltip.setVisible(true);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 1f, 200);
			Main.gui.buildingTooltip.setY(e.getScreenY()+e.getHeight()-10);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()+10, 200);
			if(Main.gui.buildingTooltip.getOpacity()>0)
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.X, e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2, 200);
			else Main.gui.buildingTooltip.setX(e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2);
			Main.gui.buildingTooltip.setBuilding(getBt());
			break;
	case Mouseout:
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 0f, 200,AnimationManager.ACTION_HIDE);
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()-10, 200);
		break;
	default:break;}}});
		add(image);
		name = new GuiLabel(0,0,getWidth(),30,(Color)null);
		name.setText(Buildings.getBuildingTypeName(bt));
		name.setCentered(true);
		name.wrapText();
		add(name);
	}

	public int getBt() {
		return bt;
	}

	public void setBt(int bt) {
		this.bt = bt;
	}
	
}
