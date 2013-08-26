package guielements;

import game.ResourceManager;
import gui.GuiEventType;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

/**
 * A simple button with background image and Text. When the background color
 * is white, it automatically gets darker when hovered.
 * @author Benedikt Ringlein
 */

public class GuiButton extends GuiLabel {
	
	private boolean showBackground = true;
	
	@Override
	public void callGuiEvents(GuiEventType eventtype)
	{
		try {
				switch (eventtype) {
				case Click:
						ResourceManager.playSound(ResourceManager.SOUND_SELECT);
				case Mouseover:
						if(getColor().equals(Color.white))setColor(new Color(235,235,235));
						break;
				case Mouseout:
						if(getColor().equals(new Color(235,235,235)))setColor(Color.white);
						break;
				default:break;
			}
			super.callGuiEvents(eventtype);	
		} catch (Exception e) {}
	}

	public boolean isShowBackground() {
		return showBackground;
	}

	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}

	public GuiButton()
	{
		setCentered(true);
	}

	public GuiButton(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setCentered(true);
	}
	
	public GuiButton(float x, float y, float width, float height, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
		setCentered(true);
	}
	
	public GuiButton(float x, float y, float width, float height, Texture texture)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		setCentered(true);
	}
	
	public GuiButton(float x, float y, float width, float height, Texture texture, Color color)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		setColor(color);
		setCentered(true);
	}

	
}
