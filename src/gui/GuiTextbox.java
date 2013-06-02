package gui;

import java.awt.Color;

import game.ResourceManager;

public class GuiTextbox extends GuiLabel {
	
	private int charlimit = 0;
	private GuiPanel caret;
	private int caretpos=0;

	public GuiTextbox()
	{
		new GuiTextbox(0,0,200,30);
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
		caret = new GuiPanel(0,5,1,(int) getHeight()-10,Color.white);
		caret.setVisible(false);
		add(caret);
	}
	
	public void setCaret(boolean visible)
	{
		caret.setVisible(visible);
	}
	
	public void setCaretPos(int pos)
	{
		caretpos = pos;
		caret.setX(getFont().getWidth(getText().substring(0, caretpos)));
	}
	
	public int getCaretPos()
	{
		return caretpos;
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
