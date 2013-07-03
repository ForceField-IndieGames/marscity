package gui;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;

import org.lwjgl.opengl.Display;

import animation.AnimationManager;
import animation.AnimationValue;

import game.Game;
import game.Main;
import game.ResourceManager;
import guielements.GuiButton;
import guielements.GuiLabel;
import guielements.GuiPanel;

/**
 * The loading screen displays all available saved citys. The list can be scrolled.
 * @author Benedikt Ringlein
 */

public class LoadingScreen extends GuiPanel {

	private int scrolling=0;
	private GuiLabel title;
	private GuiButton abort;
	private GuiButton up;
	private GuiButton down;
	private int h=295;
	private int h2=112;
	private CityPreview[] cps = new CityPreview[]{
		new CityPreview(30, h, "Stadt 1"),
		new CityPreview(255, h, "Stadt 2"),
		new CityPreview(30, h-h2, "Stadt 3"),
		new CityPreview(255, h-h2, "Stadt 4"),
		new CityPreview(30, h-2*h2, "Stadt 5"),
		new CityPreview(255, h-2*h2, "Stadt 6")
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
		title.setText(ResourceManager.getString("LOADINGSCREEN_LABEL_TITLE"));
		title.setFont(ResourceManager.Arial30B);
		title.setCentered(true);
		add(title);
		abort = new GuiButton(206, 20, 100, 32, ResourceManager.TEXTURE_LOADABORT);
		abort.setText(ResourceManager.getString("LOADINGSCREEN_LABEL_ABORT"));
		abort.setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						Main.gui.blur.setVisible(false);
						Game.Resume();
						AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
						Main.gui.loadingscreen.setVisible(false);
						break;
				case Mouseover:
						break;
				default:break;}}});
		add(abort);
		up = new GuiButton(306,20,32,32,ResourceManager.TEXTURE_SCROLLUP);
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
		down = new GuiButton(174,20,32,32,ResourceManager.TEXTURE_SCROLLDOWN);
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
			if(f.length>i+getScrolling()*cps.length){
				cps[i].setCityname(f[i+getScrolling()*cps.length].getName().substring(0,f[i+getScrolling()*cps.length].getName().length()-5));
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
