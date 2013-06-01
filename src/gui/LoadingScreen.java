package gui;

import java.awt.Color;

import org.lwjgl.opengl.Display;

import game.ResourceManager;

public class LoadingScreen extends GuiPanel {

	private GuiLabel title;
	private GuiTextbox cityname;
	private GuiButton load;
	private GuiButton abort;
	private GuiLabel info;
	
	public LoadingScreen()
	{
		setTexture(ResourceManager.TEXTURE_GUIMENU);
		setWidth(512);
		setHeight(512);
		setX(Display.getWidth()/2-getWidth()/2);
		setY(Display.getHeight()/2-getHeight()/2);
		setVisible(false);
		title = new GuiLabel(0,460,512,40,(Color)null);
		title.setText("Stadt laden");
		title.setFont(ResourceManager.Arial30B);
		title.setCentered(true);
		add(title);
		cityname = new GuiTextbox(55, 30, 200, 30);
		cityname.setText("Meine Stadt");
		cityname.setCharlimit(25);
		add(cityname);
		load = new GuiButton(275, 30, 100, 30, ResourceManager.TEXTURE_GUIBUTTON2);
		load.setText("Laden");
		load.setEvent(GuiEvents.LoadingScreenButton);
		add(load);
		abort = new GuiButton(375, 30, 100, 30, ResourceManager.TEXTURE_GUIBUTTON2);
		abort.setText("Abbrechen");
		abort.setEvent(GuiEvents.LoadingScreenAbort);
		add(abort);
		info = new GuiLabel(30,100,452,300,(Color)null);
		info.setText("Hier werden später die bereits gespeicherten\r\nStädte angezeigt.");
		info.setCentered(true);
		add(info);
	}
	
	public String getCityName()
	{
		return cityname.getText();
	}
	
}
