package guielements;

import java.awt.Color;

import game.ResourceManager;
import gui.GuiElement;
import gui.GuiEventType;

/**
 * A checkbox with label
 * @author Benedikt Ringlein
 */
public class GuiRadiobutton extends GuiPanel {
	
	private GuiButton bg = new GuiButton(0,0,32,32,ResourceManager.TEXTURE_GUIRADIOBUTTON);
	private GuiPanel check = new GuiPanel(0,0,32,32,ResourceManager.TEXTURE_GUIRADIOCHECKED);
	private GuiLabel label;
	private boolean checked = false;

	public GuiRadiobutton(float x, float y, float width)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(32);
		setColor(null);
		bg.setClickThrough(true);
		add(bg);
		check.setClickThrough(true);
		check.setVisible(false);
		add(check);
		label = new GuiLabel(32,0,width-32,32);
		label.setClickThrough(true);
		label.setColor(null);
		add(label);
	}
	
	public void setText(String text)
	{
		label.setText(text);
		label.wrapText();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		if(checked==true)check.setVisible(true);
		else check.setVisible(false);
	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype) {
		if(eventtype==GuiEventType.Click){
			for(GuiElement g:getParent().getElements())
			{
				if(g instanceof GuiRadiobutton)
				{
					((GuiRadiobutton)g).setChecked(false);
				}
			}
			setChecked(true);
		}
		if(eventtype==GuiEventType.Mouseover)bg.setColor(new Color(230,230,230));
		if(eventtype==GuiEventType.Mouseout)bg.setColor(Color.white);
		super.callGuiEvents(eventtype);
	}
	
}
