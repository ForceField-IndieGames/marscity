package gui;
import static org.lwjgl.opengl.GL11.*;

import effects.ParticleEffects;
import game.Main;
import game.ResourceManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GUI {
	
	public GuiPanel MenuBG = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_MAINMENUBG);
	public GuiPanel MenuFF = new GuiPanel(Display.getWidth()-512,0,512,128,ResourceManager.TEXTURE_MAINMENUFF);
	public GuiPanel MenuPanel = new GuiPanel(Display.getWidth()/2-430,Display.getHeight()-150,1080,50,(Color)null);
	

	public GuiButton MenuPlay = new GuiButton(0, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuLoad = new GuiButton(220, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuSettings = new GuiButton(440, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuExit = new GuiButton(660, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);

	public GuiLabel MenuVersion = new GuiLabel(0,0,180,20,(Color)null);
	
	public GuiPanel toolBar = new GuiPanel(0,0,Display.getWidth(),84, ResourceManager.TEXTURE_GUITOOLBAR);
	public GuiPanel toolAdd = new GuiPanel(20, 10, 64, 64, ResourceManager.TEXTURE_GUIADD);
	public GuiPanel toolDelete = new GuiPanel(80, 5, 32, 32, ResourceManager.TEXTURE_GUIDELETE);
	public GuiPanel guiTools = new GuiPanel(0,0,128,128,ResourceManager.TEXTURE_GUITOOLSBG);
	public GuiPanel menuButton = new GuiPanel(10, 65, 32, 32, ResourceManager.TEXTURE_GUIMENUBUTTON);
	
	public GuiPanel infoBar = new GuiPanel(300,55,700,20,(Color)null);
	public GuiLabel infoMoney = new GuiLabel(0,0,150,20,(Color)null);
	public GuiLabel infoCitizens = new GuiLabel(150,0,150,20,(Color)null);
	
	public GuiPanel buildingChooser = new GuiPanel(300,0,700,50, (Color)null);
	public GuiButton buildingHouse = new GuiButton(0,0,100,40, ResourceManager.TEXTURE_GUIBUTTON2, Color.gray);
	public GuiButton buildingBighouse = new GuiButton(100,0,100,40, ResourceManager.TEXTURE_GUIBUTTON2);
	
	public GuiPanel blur = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),(Color)null);
	public GuiPanel pauseMenu = new GuiPanel(Display.getWidth()/2-128,Display.getHeight()/2-128,256,256,ResourceManager.TEXTURE_GUIMENU);
	public GuiButton pauseMainmenu = new GuiButton(28, 200, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseLoad = new GuiButton(28, 170, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseSave = new GuiButton(28, 140, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseSettings = new GuiButton(28, 110, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseExit = new GuiButton(28, 80, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton pauseResume = new GuiButton(28, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiPanel pauseLogo = new GuiPanel(-128,256,512,128,ResourceManager.TEXTURE_MARSCITYLOGO);
	
	public GuiLabel debugInfo = new GuiLabel(0,Display.getHeight()-20,Display.getWidth(),20,Color.white);
	
	public GuiPanel settingsMenu = new GuiPanel(Display.getWidth()/2-256,Display.getHeight()/2-256,512,512,ResourceManager.TEXTURE_GUIMENU);
	public GuiButton settingsResume = new GuiButton(156, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON);
	public GuiLabel settingsTitle = new GuiLabel(30, 470, 100, 20, (Color)null);
	public GuiLabel settingsVsync = new GuiLabel(30,440,190,20,(Color)null);
	public GuiButton settingsVsyncon = new GuiButton(30,410,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton settingsVsyncoff = new GuiButton(130,410,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiLabel settingsParticles = new GuiLabel(30,380,190,20,(Color)null);
	public GuiButton settingsParticlesoff = new GuiButton(30,350,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton settingsParticleslow = new GuiButton(130,350,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton settingsParticlesmiddle = new GuiButton(230,350,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton settingsParticleshigh = new GuiButton(330,350,100,30,ResourceManager.TEXTURE_GUIBUTTON2);
	
	List<guiElement> elements = new ArrayList<guiElement>();
	List<guiElement> menuElements = new ArrayList<guiElement>();
	
	public GUI()
	{
		
		if(ResourceManager.getSetting("particlequality").equals("off")){
			settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
		}else if(ResourceManager.getSetting("particlequality").equals("low")){
			settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
		}else if(ResourceManager.getSetting("particlequality").equals("middle")){
			settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
		}else if(ResourceManager.getSetting("particlequality").equals("high")){
			settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
		}
		
		//Main menu
		menuElements.add(MenuBG);
		menuElements.add(MenuFF);
		menuElements.add(MenuVersion);
						 MenuVersion.setText("Mars City [Alpha 0.0.1]");
		menuElements.add(MenuPanel);
						 MenuPanel.add(MenuPlay);
		     		    			   MenuPlay.setText(ResourceManager.getString("MAINMENU_BUTTON_PLAY"));
		     		     MenuPanel.add(MenuLoad);
		     		     			   MenuLoad.setText(ResourceManager.getString("MAINMENU_BUTTON_LOAD"));
		     		     MenuPanel.add(MenuSettings);
		     		     			   MenuSettings.setText(ResourceManager.getString("MAINMENU_BUTTON_SETTINGS"));
		     		     MenuPanel.add(MenuExit);
		     		     			   MenuExit.setText(ResourceManager.getString("MAINMENU_BUTTON_EXIT"));
		
		//GUI
		add(toolBar);
			toolBar.setY(-40);
			toolBar.add(buildingChooser);
						buildingChooser.add(buildingHouse);
											buildingHouse.setText(ResourceManager.getString(ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_HOUSE).getName()));
						buildingChooser.add(buildingBighouse);
											buildingBighouse.setText(ResourceManager.getString(ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_BIGHOUSE).getName()));
			toolBar.add(infoBar);
						infoBar.add(infoMoney);
									infoMoney.setText("Geld: 0$");
						infoBar.add(infoCitizens);
									infoCitizens.setText("Einwohner: 0");
		add(guiTools);
		guiTools.add(menuButton);
		guiTools.add(toolAdd);
		guiTools.add(toolDelete);
		
		add(blur);
			blur.setBlurBehind(true);
			blur.setVisible(false);
			
		add(pauseMenu);
			pauseMenu.setVisible(false);
			pauseMenu.add(pauseLogo);
			pauseMenu.add(pauseMainmenu);
						  pauseMainmenu.setText(ResourceManager.getString("PAUSEMENU_BUTTON_MAINMENU"));
			pauseMenu.add(pauseLoad);
						  pauseLoad.setText(ResourceManager.getString("PAUSEMENU_BUTTON_LOAD"));
			pauseMenu.add(pauseSave);
						  pauseSave.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SAVE"));
			pauseMenu.add(pauseSettings);
						  pauseSettings.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SETTINGS"));
			pauseMenu.add(pauseExit);
						  pauseExit.setText(ResourceManager.getString("PAUSEMENU_BUTTON_EXIT"));			  
			pauseMenu.add(pauseResume);
						  pauseResume.setText(ResourceManager.getString("PAUSEMENU_BUTTON_RESUME"));

  ///////////		
		add(settingsMenu);
			settingsMenu.setVisible(false);
			settingsMenu.add(settingsTitle);
			settingsMenu.add(settingsVsyncon);
			settingsMenu.add(settingsVsyncoff);
			settingsMenu.add(settingsVsync);
			settingsMenu.add(settingsParticles);
			settingsMenu.add(settingsParticlesoff);
			settingsMenu.add(settingsParticleslow);
			settingsMenu.add(settingsParticlesmiddle);
			settingsMenu.add(settingsParticleshigh);
			settingsMenu.add(settingsResume);
			
			settingsTitle.setText(ResourceManager.getString("SETTINGSMENU_LABEL_TITLE"));
			settingsVsyncon.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCON"));
			if (ResourceManager.getSetting("vsync").equals("enabled"))settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			settingsVsyncoff.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCOFF"));
			if (!ResourceManager.getSetting("vsync").equals("enabled"))settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON2DOWN);
			settingsVsync.setText(ResourceManager.getString("SETTINGSMENU_LABEL_VSYNC"));
			settingsParticles.setText(ResourceManager.getString("SETTINGSMENU_LABEL_PARTICLES"));
			settingsParticlesoff.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESOFF"));
			settingsParticleslow.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESLOW"));
			settingsParticlesmiddle.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESMIDDLE"));
			settingsParticleshigh.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESHIGH"));
			settingsResume.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_RESUME"));
///////////
			
		add(debugInfo);	
			debugInfo.setVisible(Main.debugMode);
						  
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
