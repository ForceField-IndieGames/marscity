package gui;

import java.awt.Color;

import game.Main;
import game.ResourceManager;
import guielements.GuiButton;

/**
 * Those buttons represent categorys. When clicked, they show the assigned
 * building panel.
 * Also, the button label is set automatically according to the panels name.
 * @author Benedikt Ringlein
 */

public class CategoryButton extends GuiButton {

	private BuildingPanel panel;
	
	public CategoryButton(float x, float y, BuildingPanel panel)
	{
		this.setPanel(panel);
		setX(x);
		setY(y);
		setWidth(150);
		setHeight(30);
		setTexture(ResourceManager.TEXTURE_GUIBUTTON);
		setText(panel.getName());
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Click:
						Main.gui.buildingPanels.show();
						Main.selectedTool = Main.TOOL_SELECT;
						Main.buildpreview.setBuilding(-1);
						Main.currentBT = -1;
						Main.gui.deleteBorder.setVisible(false);
						Main.gui.toolDelete.setColor(Color.white);
						((CategoryButton) e).getPanel().show();
						break;
				case Mousein:
						break;
				default:break;}}});
	}

	public BuildingPanel getPanel() {
		return panel;
	}

	public void setPanel(BuildingPanel panel) {
		this.panel = panel;
		setText(panel.getName());
	}
	
}
