package guielements;

import game.ResourceManager;
import gui.GuiEventType;

/**
 * A checkbox with label
 * @author Benedikt Ringlein
 */
public class GuiCheckbox extends GuiPanel {
	
	private GuiButton bg = new GuiButton(0,0,32,32,ResourceManager.TEXTURE_GUICHECKBOX);
	private GuiPanel check = new GuiPanel(0,0,32,32,ResourceManager.TEXTURE_GUICHECKED);
	private GuiLabel label;
	private boolean checked = false;

	public GuiCheckbox(float x, float y, float width)
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
	
	public void toggleChecked()
	{
		setChecked(!isChecked());
	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype) {
		if(eventtype==GuiEventType.Click)toggleChecked();
		super.callGuiEvents(eventtype);
	}
	
}
