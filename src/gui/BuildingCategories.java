package gui;

/**
 * This is a panel that contains the category buttons.
 * It can automatically add category buttons when given a BuildingPanel
 * and give them the right name and position them correctly.
 * @author Benedikt Ringlein
 */

public class BuildingCategories extends GuiPanel {

	public BuildingCategories(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(null);
	}
	
	public void addCategoryButton(BuildingPanel panel)
	{
		add(new CategoryButton(150*getElements().size(), 0, panel));
	}
	
}
