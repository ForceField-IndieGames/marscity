package gui;

import org.lwjgl.opengl.Display;

/**
 * A Buildingspanel is a panel that contains buildings of a category
 * It can automatically arrange buildingbuttons in it.
 * @author Benedikt Ringlein
 */
public class BuildingPanel extends GuiPanel {

	private String name;
	
	public BuildingPanel(String name)
	{
		this.setName(name);
		setVisible(false);
		setWidth(Display.getWidth());
		setHeight(100);
		setColor(null);
	}
	
	public void addBuildingButton(int bt)
	{
		add(new BuildingButton(100*getElements().size(), 0, bt));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
