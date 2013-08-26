package guielements;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.UnicodeFont;

import game.ResourceManager;

public class GuiProgressbar extends GuiPanel {

	private GuiLabel bg;
	private GuiLabel bar;
	private GuiLabel label;
	
	private float value = 0;
	
	public GuiProgressbar(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(null);
		bg = new GuiLabel(0,0,width,height,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR);
		bg.setClickThrough(true);
		bar = new GuiLabel(0,0,width,height,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR);
		bar.setClickThrough(true);
		label = new GuiLabel(0,0,width,height,(Color)null);
		label.setClickThrough(true);
		add(bg);
		add(bar);
		add(label);
	}
	
	@Override
	public void draw() {
		bg.draw();
		glEnable(GL_SCISSOR_TEST);
		glScissor((int)(bar.getScreenX()-bar.getHeight()/2), (int)bar.getScreenY(), (int)((bar.getWidth()+bar.getHeight())*value), (int)bar.getHeight());
		bar.draw();
		glDisable(GL_SCISSOR_TEST);
		label.draw();
	}
	
	public void setText(String text)
	{
		label.setText(text);
	}
	
	public void setFont(UnicodeFont font)
	{
		label.setFont(font);
	}
	
	public void setBarColor(Color color)
	{
		bar.setColor(color);
	}
	
	public Color getBarColor()
	{
		return bar.getColor();
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public void setRightaligned(boolean align)
	{
		label.setRightaligned(align);
	}
	
	public void setCentered(boolean centered)
	{
		label.setCentered(centered);
	}
}
