package gui;

import java.awt.Color;

import animation.AnimationManager;
import animation.AnimationValue;
import game.Main;
import game.ResourceManager;

public class CategoryButton extends GuiButton {

	public CategoryButton(float x, float y, String name)
	{
		setX(x);
		setY(y);
		setWidth(150);
		setHeight(30);
		setTexture(ResourceManager.TEXTURE_GUIBUTTON);
		setText(name);
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Click:
						Main.gui.buildingPanels.setElementsVisible(false);
						Main.gui.buildingPanelsl.setVisible(true);
						Main.gui.buildingPanels.setVisible(true);
						Main.selectedTool = Main.TOOL_SELECT;
						Main.buildpreview.setBuilding(-1);
						Main.currentBuildingType = -1;
						Main.gui.deleteBorder.setVisible(false);
						Main.gui.toolDelete.setColor(Color.white);
						AnimationManager.animateValue(Main.gui.buildingPanels, AnimationValue.Y, 68f, 0.5f);
						if(e==Main.gui.categoryStreets){
							Main.gui.buildingPanelStreet.setVisible(true);
						}
						if(e==Main.gui.categoryResidential){
							Main.gui.buildingPanelResidential.setVisible(true);
						}
						break;
				case Mouseover:
						break;
				default:break;}}});
	}
	
}
