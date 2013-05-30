package gui;

import java.awt.Color;

import game.ResourceManager;

import org.lwjgl.opengl.Display;

public class MsgBox extends GuiPanel {

	private String title = "";
	private String text = "";
	private GuiLabel titlelabel;
	private GuiLabel textlabel;
	private GuiButton button;
	
	public MsgBox(String title, String text)
	{
		this.title = title;
		this.text = text;
		setTexture(ResourceManager.TEXTURE_MSGBOX);
		setColor(Color.white);
		setWidth(512);
		setHeight(256);
		setX(Display.getWidth()/2-256);
		setY(Display.getHeight()/2-128);
		titlelabel = new GuiLabel(0,200,512,40,(Color)null);
		titlelabel.setText(title);
		titlelabel.setFont(ResourceManager.Arial30B);
		titlelabel.setCentered(true);
		add(titlelabel);
		textlabel = new GuiLabel(10,50,492,160,(Color)null);
		textlabel.setText(text);
		textlabel.setCentered(true);
		add(textlabel);
		button = new GuiButton(156,20,200,30,ResourceManager.TEXTURE_GUIBUTTON);
		button.setEvent(GuiEvents.MsgBox);
		button.setText("OK");
		add(button);
	}
	
}
