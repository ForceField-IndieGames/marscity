package gui;

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
