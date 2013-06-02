package gui;

import java.awt.Color;
import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.opengl.Display;

import game.ResourceManager;

public class LoadingScreen extends GuiPanel {

	private GuiLabel title;
	private GuiTextbox cityname;
	private GuiButton load;
	private GuiButton abort;
	private CityPreview city1 = new CityPreview(30, 275, "Stadt 1");
	private CityPreview city2 = new CityPreview(180, 275, "Stadt 2");
	private CityPreview city3 = new CityPreview(330, 275, "Stadt 3");
	private CityPreview city4 = new CityPreview(30, 125, "Stadt 4");
	private CityPreview city5 = new CityPreview(180, 125, "Stadt 5");
	private CityPreview city6 = new CityPreview(330, 125, "Stadt 6");
	
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
		add(city1);
		add(city2);
		add(city3);
		add(city4);
		add(city5);
		add(city6);
	}
	
	public String getCityName()
	{
		return cityname.getText();
	}
	
	public void setCityName(String name)
	{
		cityname.setText(name);
	}
	
	public void show()
	{
		setVisible(true);
		File d = new File("res/cities/");
		File[] f =  d.listFiles();
		if(f.length>0){
			city1.setCityname(f[0].getName().substring(0,f[0].getName().length()-5));
		}else{
			city1.setCityname("");
			city1.setColor(Color.gray);
		}
		if(f.length>1){
			city2.setCityname(f[1].getName().substring(0,f[1].getName().length()-5));
		}else{
			city2.setCityname("");
			city2.setColor(Color.gray);
		}
		if(f.length>2){
			city3.setCityname(f[2].getName().substring(0,f[2].getName().length()-5));
		}else{
			city3.setCityname("");
			city3.setColor(Color.gray);
		}
		if(f.length>3){
			city4.setCityname(f[03].getName().substring(0,f[3].getName().length()-5));
		}else{
			city4.setCityname("");
			city4.setColor(Color.gray);
		}
		if(f.length>4){
			city5.setCityname(f[4].getName().substring(0,f[4].getName().length()-5));
		}else{
			city5.setCityname("");
			city5.setColor(Color.gray);
		}
		if(f.length>5){
			city6.setCityname(f[5].getName().substring(0,f[5].getName().length()-5));
		}else{
			city6.setCityname("");
			city6.setColor(Color.gray);
		}
	}

	
}
