package gui;

import java.awt.Color;

import game.ResourceManager;

import org.lwjgl.opengl.Display;

import animation.Animatable;
import animation.AnimationManager;
import animation.AnimationValue;

/**
 * This is a basic messagebox. It can display text and a title and
 * contains a button to close the messagebox.
 * @author Benedikt Ringlein
 */

public class MsgBox extends GuiPanel {

	private GuiLabel titlelabel;
	private GuiLabel textlabel;
	private GuiButton button;
	
	public MsgBox(String title, String text, Color color)
	{
		setTexture(ResourceManager.TEXTURE_MSGBOX);
		setColor(color);
		setWidth(512);
		setHeight(256);
		setX(Display.getWidth()/2-256);
		setY(Display.getHeight()/2-128);
		setOpacity(0f);
		titlelabel = new GuiLabel(0,200,512,40,(Color)null);
		titlelabel.setText(title);
		titlelabel.setFont(ResourceManager.Arial30B);
		titlelabel.setCentered(true);
		add(titlelabel);
		textlabel = new GuiLabel(15,50,482,160,(Color)null);
		textlabel.setText(text);
		textlabel.setCentered(true);
		textlabel.wrapText();
		add(textlabel);
		button = new GuiButton(156,20,200,30,ResourceManager.TEXTURE_GUIBUTTON);
		button.setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement element) {
				switch (eventtype) {
				case Click:
						AnimationManager.animateValue((Animatable) element.getParent(), AnimationValue.opacity, 0f, 100, AnimationManager.ACTION_REMOVEGUI);
						AnimationManager.animateValue((Animatable) element.getParent(), AnimationValue.Y, element.getParent().getY()-20, 100);
						break;
				default:break;}}});
		button.setText(ResourceManager.getString("MSGBOX_BUTTON_OK"));
		button.setColor(color);
		add(button);
		AnimationManager.animateValue(this, AnimationValue.opacity, 1f, 100);
		AnimationManager.animateValue(this, AnimationValue.Y, getY()+10, 100, AnimationManager.ACTION_REVERSE);
	}
	
}
