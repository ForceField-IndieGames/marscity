package gui;

import game.ResourceManager;

public class GuiTextbox extends GuiLabel {
	
	private int charlimit = 0;

	public GuiTextbox()
	{
		setTexture(ResourceManager.TEXTURE_GUITEXTFIELD);
		setTexturel(ResourceManager.TEXTURE_GUITEXTFIELDL);
		setTexturer(ResourceManager.TEXTURE_GUITEXTFIELDR);
	}
	
	public GuiTextbox(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(ResourceManager.TEXTURE_GUITEXTFIELD);
		setTexturel(ResourceManager.TEXTURE_GUITEXTFIELDL);
		setTexturer(ResourceManager.TEXTURE_GUITEXTFIELDR);
	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype)
	{
		try {
			GuiEvents.GuiTextBoxes.run(eventtype, this);
			getEvent().run(eventtype, this);
		} catch (Exception e) {
		}
	}

	public int getCharlimit() {
		return charlimit;
	}

	public void setCharlimit(int charlimit) {
		this.charlimit = charlimit;
	}
	
}
