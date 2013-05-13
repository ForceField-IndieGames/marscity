package gui;
import static org.lwjgl.opengl.GL11.*;

import game.ResourceManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GUI {
	
	public GuiPanel MenuBG = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_MAINMENUBG);
	public GuiPanel MenuPanel = new GuiPanel(Display.getWidth()/2-540,Display.getHeight()-150,1080,50,(Color)null);
	public GuiButton MenuPlay = new GuiButton(0, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton MenuLoad = new GuiButton(220, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton MenuSave = new GuiButton(440, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton MenuSettings = new GuiButton(660, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton MenuExit = new GuiButton(880, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON);
	
	
	public GuiPanel toolbar = new GuiPanel(0,0,Display.getWidth(),84, ResourceManager.TEXTURE_GUITOOLBAR);
	public GuiPanel toolselect = new GuiPanel(10, 10, 64, 64, ResourceManager.TEXTURE_GUISELECT);
	public GuiPanel tooladd = new GuiPanel(84, 10, 64, 64, ResourceManager.TEXTURE_GUIADD, Color.gray);
	public GuiPanel tooldelete = new GuiPanel(158, 10, 64, 64, ResourceManager.TEXTURE_GUIDELETE, Color.gray);
	public GuiLabel tooltext = new GuiLabel(Display.getWidth()/2-200,55,400,18,(Color)null);
	
	public GuiPanel blur = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),(Color)null);
	public GuiPanel pausemenu = new GuiPanel(Display.getWidth()/2-128,Display.getHeight()/2-128,256,256,ResourceManager.TEXTURE_GUIMENU);
	public GuiButton pausemainmenu = new GuiButton(28, 200, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseload = new GuiButton(28, 170, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pausesave = new GuiButton(28, 140, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pausesettings = new GuiButton(28, 110, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseexit = new GuiButton(28, 80, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseresume = new GuiButton(28, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	
	public GuiLabel debugInfo = new GuiLabel(0,Display.getHeight()-20,200,20,Color.white);
	
	public GuiPanel settingsmenu = new GuiPanel(Display.getWidth()/2-128,Display.getHeight()/2-128,256,256,ResourceManager.TEXTURE_GUIMENU);
	public GuiButton settingsresume = new GuiButton(28, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiLabel settingstitle = new GuiLabel(30, 220, 100, 20, (Color)null);
	
	List<guiElement> elements = new ArrayList<guiElement>();
	List<guiElement> menuElements = new ArrayList<guiElement>();
	
	public GUI()
	{
		//Main menu
		menuElements.add(MenuBG);
		MenuBG.add(MenuPanel);
		MenuPanel.add(MenuPlay);
		     		    MenuPlay.setText(ResourceManager.getString("MAINMENU_BUTTON_PLAY"));
	 		   MenuPanel.add(MenuLoad);
		     		    MenuLoad.setText(ResourceManager.getString("MAINMENU_BUTTON_LOAD"));
	 		   MenuPanel.add(MenuSave);
		     		    MenuSave.setText(ResourceManager.getString("MAINMENU_BUTTON_SAVE"));
	 		   MenuPanel.add(MenuSettings);
		     		    MenuSettings.setText(ResourceManager.getString("MAINMENU_BUTTON_SETTINGS"));
	 		   MenuPanel.add(MenuExit);
		     		    MenuExit.setText(ResourceManager.getString("MAINMENU_BUTTON_EXIT"));
		
		//GUI
		add(toolbar);
			toolbar.setBlurBehind(true);
			toolbar.add(toolselect);
			toolbar.add(tooladd);
			toolbar.add(tooldelete);
			toolbar.add(tooltext);
						tooltext.setText(ResourceManager.getString("TOOLBAR_LABEL_DESCRIPTION"));
		add(blur);
			blur.setBlurBehind(true);
			blur.setVisible(false);
		add(pausemenu);
			pausemenu.setVisible(false);
			pausemenu.setOpacity(0.8f);
			pausemenu.add(pausemainmenu);
						  pausemainmenu.setText(ResourceManager.getString("PAUSEMENU_BUTTON_MAINMENU"));
			pausemenu.add(pauseload);
						  pauseload.setText(ResourceManager.getString("PAUSEMENU_BUTTON_LOAD"));
			pausemenu.add(pausesave);
						  pausesave.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SAVE"));
			pausemenu.add(pausesettings);
						  pausesettings.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SETTINGS"));
			pausemenu.add(pauseexit);
						  pauseexit.setText(ResourceManager.getString("PAUSEMENU_BUTTON_EXIT"));			  
			pausemenu.add(pauseresume);
						  pauseresume.setText(ResourceManager.getString("PAUSEMENU_BUTTON_RESUME"));
		add(settingsmenu);
			settingsmenu.setVisible(false);
			settingsmenu.add(settingstitle);
							 settingstitle.setText(ResourceManager.getString("SETTINGSMENU_LABEL_TITLE"));
			settingsmenu.add(settingsresume);
							 settingsresume.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_RESUME"));
		add(debugInfo);	
						  
	}
	
	public void add(guiElement guielement)
	{
		elements.add(guielement);
	}
	
	public void remove(int index){
		elements.remove(index);}
	public void remove(guiElement guielement){
		elements.remove(guielement);}
	
	public void draw()
	{
		glDisable(GL_DEPTH_TEST);
		glPushMatrix();
		
		glDisable(GL_LIGHTING);
		for(guiElement element: elements) {
			glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		glMatrixMode(GL_MODELVIEW);
			element.draw();
		}
		glPopMatrix();
		glEnable(GL_DEPTH_TEST);
	}
	
	public void drawMenu()
	{
		glDisable(GL_DEPTH_TEST);
		glPushMatrix();
		
		glDisable(GL_LIGHTING);
		for(guiElement element: menuElements) {
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
			glMatrixMode(GL_MODELVIEW);
			element.draw();
		}
		glPopMatrix();
		glEnable(GL_DEPTH_TEST);
	}
	
	public guiElement mouseoverMenu()
	{
		for(int i=menuElements.size()-1;i>=0;i--)
		{
			if(menuElements.get(i).isScreenVisible()
					&&menuElements.get(i).getX()<Mouse.getX()
					&&menuElements.get(i).getY()<Mouse.getY()
					&&menuElements.get(i).getWidth()+menuElements.get(i).getX()>Mouse.getX()
					&&menuElements.get(i).getHeight()+menuElements.get(i).getY()>Mouse.getY()) return menuElements.get(i).mouseover();
		}
		return null;
	}
	
	public guiElement mouseover()
	{
		for(int i=elements.size()-1;i>=0;i--)
		{
			if(elements.get(i).isScreenVisible()
					&&elements.get(i).getX()<Mouse.getX()
					&&elements.get(i).getY()<Mouse.getY()
					&&elements.get(i).getWidth()+elements.get(i).getX()>Mouse.getX()
					&&elements.get(i).getHeight()+elements.get(i).getY()>Mouse.getY()) return elements.get(i).mouseover();
		}
		return null;
	}
	
}
