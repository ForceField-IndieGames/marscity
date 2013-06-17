package gui;

import game.Main;
import game.ResourceManager;

import java.awt.Color;

import animation.AnimationManager;
import animation.AnimationValue;

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
		image = new GuiPanel(10,25,80,80, ResourceManager.getBuildingType(bt).getThumb());
		image.setEvent(new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			Main.selectedTool = Main.TOOL_ADD;
			Main.gui.buildingPanels.hide();
			Main.currentBT = getBt();
			Main.buildpreview.setBuilding(getBt());
			Main.gui.infoBuildingCosts.setVisible(true);
			Main.gui.infoBuildingCosts.setText(""+ResourceManager.getBuildingType(Main.currentBT).getBuidlingcost());
			Main.gui.infoBuildingCosts.AutoSize();
			break;
	case Mouseover:
			Main.gui.buildingTooltip.setVisible(true);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 1f, 0.005f);
			Main.gui.buildingTooltip.setY(e.getScreenY()+e.getHeight()-10);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()+10, 0.05f);
			if(Main.gui.buildingTooltip.getOpacity()>0)
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.X, e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2, 0.5f);
			else Main.gui.buildingTooltip.setX(e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2);
			Main.gui.buildingTooltip.setBuilding(getBt());
			break;
	case Mouseout:
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 0f, 0.005f,AnimationManager.ACTION_HIDE);
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()-10, 0.05f);
		break;
	default:break;}}});
		add(image);
		name = new GuiLabel(0,0,getWidth(),30,(Color)null);
		name.setText(ResourceManager.getBuildingTypeName(bt));
		name.setCentered(true);
		add(name);
	}

	public int getBt() {
		return bt;
	}

	public void setBt(int bt) {
		this.bt = bt;
	}
	
}
