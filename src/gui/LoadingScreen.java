package gui;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.opengl.Display;

import game.Game;
import game.Main;
import game.ResourceManager;

public class LoadingScreen extends GuiPanel {

	private int scrolling=0;
	private GuiLabel title;
	private GuiTextbox cityname;
	private GuiButton load;
	private GuiButton abort;
	private GuiPanel up;
	private GuiPanel down;
	private CityPreview[] cps = new CityPreview[]{
		new CityPreview(30, 350, "Stadt 1"),
		new CityPreview(180, 350, "Stadt 2"),
		new CityPreview(330, 350, "Stadt 3"),
		new CityPreview(30, 265, "Stadt 4"),
		new CityPreview(180, 265, "Stadt 5"),
		new CityPreview(330, 265, "Stadt 6"),
		new CityPreview(30, 180, "Stadt 7"),
		new CityPreview(180, 180, "Stadt 8"),
		new CityPreview(330, 180, "Stadt 9"),
		new CityPreview(30, 95, "Stadt 10"),
		new CityPreview(180, 95, "Stadt 11"),
		new CityPreview(330, 95, "Stadt 12"),
	};
	
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
		up = new GuiPanel(72,430,32,32,ResourceManager.TEXTURE_SCROLLUP);
		up.setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Click:
						if(Main.gui.loadingscreen.getScrolling()>0){
							Main.gui.loadingscreen.setScrolling(Main.gui.loadingscreen.getScrolling()-1);
							Main.gui.loadingscreen.show();
						}
						break;
				default:break;}}});
		add(up);
		down = new GuiPanel(40,430,32,32,ResourceManager.TEXTURE_SCROLLDOWN);
		down.setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Click:
							Main.gui.loadingscreen.setScrolling(Main.gui.loadingscreen.getScrolling()+1);
							Main.gui.loadingscreen.show();
						break;
				default:break;}}});
		add(down);
		for(CityPreview c: cps){
			add(c);
		}
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
		File[] f =  d.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".city"))return true;
				return false;
			}
		});
		for(int i=0;i<cps.length;i++){
			if(f.length>i+getScrolling()*12){
				cps[i].setCityname(f[i+getScrolling()*12].getName().substring(0,f[i+getScrolling()*12].getName().length()-5));
				cps[i].setEnabled(true);
			}else{
				cps[i].setCityname("");
				cps[i].setEnabled(false);
			}
		}
	}

	public int getScrolling() {
		return scrolling;
	}

	public void setScrolling(int scrolling) {
		this.scrolling = scrolling;
	}

	
}
