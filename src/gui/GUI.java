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

import animation.AnimationManager;
import animation.AnimationValue;

/**
 * This class generates and displays the gui.
 * @author Benedikt Ringlein
 */

public class GUI {
	
	private GuiElement keyboardfocus;
	private static boolean visible = true;
	
	public GuiPanel MenuBG = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_MAINMENUBG);
	public GuiPanel MenuFF = new GuiPanel(Display.getWidth()-512,0,512,128,ResourceManager.TEXTURE_MAINMENUFF);
	public GuiPanel MenuPanel = new GuiPanel(Display.getWidth()/2-430,Display.getHeight()-150,1080,50,(Color)null);
	public GuiButton MenuPlay = new GuiButton(0, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuLoad = new GuiButton(220, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuSettings = new GuiButton(440, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiButton MenuExit = new GuiButton(660, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2);
	public GuiLabel MenuVersion = new GuiLabel(0,0,180,20,(Color)null);
	public GuiPanel MenuIcon = new GuiPanel(Display.getWidth()/2-64,20,128,128,ResourceManager.TEXTURE_ICON256);
	public GuiPanel IntroFF = new GuiPanel(Display.getWidth()/2-960,Display.getHeight()/2-540,1920,1080,ResourceManager.TEXTURE_FORCEFIELDBG);
	public LoadingScreen loadingscreen = new LoadingScreen();
	
	public BuildingToolTip buildingTooltip = new BuildingToolTip();
	
	public GuiPanel deleteBorder = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_GUIDELETEBORDER);
	
	public GuiPanel toolBar = new GuiPanel(0,0,Display.getWidth(),70, ResourceManager.TEXTURE_GUITOOLBAR);
	
	public GuiPanel guiTools = new GuiPanel(0,0,69,100,ResourceManager.TEXTURE_GUITOOLSBG);
	public GuiPanel menuButton = new GuiPanel(0, 64, 32, 32, ResourceManager.TEXTURE_GUIMENUBUTTON);
	public GuiPanel toolDelete = new GuiPanel(0, 0, 64, 64, ResourceManager.TEXTURE_GUIDELETE);
	
	public GuiPanel infoBar = new GuiPanel(0,30,Display.getWidth(),40,ResourceManager.TEXTURE_GUITOOLBAR);
	public GuiTextbox cityName = new GuiTextbox(100,5,200,30);
	public GuiLabel infoMoney = new GuiLabel(350,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR);
	public GuiLabel infoCitizens = new GuiLabel(600,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR);
	
	public CategoryButton categoryStreets = new CategoryButton(0,0,ResourceManager.getString("BUILDINGCATEGORY_STREETS"));
	public CategoryButton categoryResidential = new CategoryButton(150,0,ResourceManager.getString("BUILDINGCATEGORY_RESIDENTIAL"));
	public GuiPanel buildingCategories = new GuiPanel(150,0,Display.getWidth(),50, (Color)null)
	{{
		add(categoryStreets);
		add(categoryResidential);
	}};
	
	public BuildingPanel buildingPanelStreet = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_STREETS"))
	{{
		addBuildingButton(ResourceManager.BUILDINGTYPE_STREET);
	}};
	public BuildingPanel buildingPanelResidential = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_RESIDENTIAL"))
	{{
		addBuildingButton(ResourceManager.BUILDINGTYPE_HOUSE);
		addBuildingButton(ResourceManager.BUILDINGTYPE_BIGHOUSE);
	}};
	public GuiPanel buildingPanelsl = new GuiPanel(-50,0,50,100,ResourceManager.TEXTURE_GUIBUILDINGSPANELL);
	public GuiPanel buildingPanels = new GuiPanel(150,20,Display.getWidth(),100,ResourceManager.TEXTURE_GUIBUILDINGSPANEL,(Color)null)
	{{
		add(buildingPanelsl);
		
		add(buildingPanelStreet);
		add(buildingPanelResidential);
	}};
	
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
	public GuiLabel settingsTitle = new GuiLabel(0, 460, 512,40, (Color)null);
	public GuiLabel settingsVsync = new GuiLabel(30,440,190,20,(Color)null);
	public GuiButton settingsVsyncon = new GuiButton(30,410,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton settingsVsyncoff = new GuiButton(130,410,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiLabel settingsParticles = new GuiLabel(30,380,190,20,(Color)null);
	public GuiButton settingsParticlesoff = new GuiButton(30,350,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton settingsParticleslow = new GuiButton(130,350,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton settingsParticlesmiddle = new GuiButton(230,350,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	public GuiButton settingsParticleshigh = new GuiButton(330,350,100,30,ResourceManager.TEXTURE_GUIBUTTON);
	
	public GuiPanel cameraMove = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAMOVE);
	public GuiPanel cameraRotate = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAROTATE);
	
	List<GuiElement> elements = new ArrayList<GuiElement>();
	List<GuiElement> menuElements = new ArrayList<GuiElement>();
	public GuiElement lastHovered;
	
	public GUI()
	{
		
		if(ResourceManager.getSetting("particlequality").equals("off")){
			settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
		}else if(ResourceManager.getSetting("particlequality").equals("low")){
			settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
		}else if(ResourceManager.getSetting("particlequality").equals("middle")){
			settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
		}else if(ResourceManager.getSetting("particlequality").equals("high")){
			settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
		}
		
		//Main menu
		
		menuElements.add(MenuBG);
		menuElements.add(MenuFF);
		menuElements.add(MenuIcon);
		menuElements.add(MenuVersion);
		menuElements.add(MenuPanel);
		menuElements.add(IntroFF);
		
		MenuPanel.add(MenuPlay);
		MenuPanel.add(MenuLoad);
		MenuPanel.add(MenuSettings);
		MenuPanel.add(MenuExit);
		
		IntroFF.setVisible(false);
		
		MenuIcon.setOpacity(0.9f);
	
		MenuVersion.setText("Mars City [Alpha 0.0.1]");
		
	    MenuPlay.setText(ResourceManager.getString("MAINMENU_BUTTON_PLAY"));
	    MenuPlay.setFont(ResourceManager.Arial15B);
	    MenuPlay.setEvent(GuiEvents.MenuPlay);
 
	    MenuLoad.setText(ResourceManager.getString("MAINMENU_BUTTON_LOAD"));
	    MenuLoad.setFont(ResourceManager.Arial15B);
	    MenuLoad.setEvent(GuiEvents.MenuLoad);
 
	    MenuSettings.setText(ResourceManager.getString("MAINMENU_BUTTON_SETTINGS"));
	    MenuSettings.setFont(ResourceManager.Arial15B);
	    MenuSettings.setEvent(GuiEvents.MenuSettings);
	    MenuSettings.setColor(Color.red);
 
	    MenuExit.setText(ResourceManager.getString("MAINMENU_BUTTON_EXIT"));
	    MenuExit.setFont(ResourceManager.Arial15B);
	    MenuExit.setEvent(GuiEvents.MenuExit);
		
		//GUI
	    
	    
		add(deleteBorder);
		add(cameraMove);
		add(cameraRotate);
		add(buildingPanels);
		add(toolBar);
		
		add(guiTools);
		add(buildingTooltip);
		add(blur);
		add(pauseMenu);
		add(loadingscreen);
		
		buildingTooltip.setVisible(false);
		
		toolBar.add(buildingCategories);
		toolBar.add(infoBar);
		
		infoBar.add(infoMoney);
		infoBar.add(infoCitizens);
		
		infoBar.add(cityName);
		
		guiTools.add(menuButton);
		guiTools.add(toolDelete);
		
		pauseMenu.add(pauseLogo);
		pauseMenu.add(pauseMainmenu);
		pauseMenu.add(pauseLoad);
		pauseMenu.add(pauseSave);
		pauseMenu.add(pauseSettings);
		pauseMenu.add(pauseExit);
		pauseMenu.add(pauseResume);
		
		buildingPanels.setVisible(false);
		
		cityName.setText(Main.cityname);
		cityName.setCharlimit(25);
		cityName.setEvent(GuiEvents.cityName);
		
		deleteBorder.setClickThrough(true);
		deleteBorder.setVisible(false);
		
		cameraMove.setVisible(false);
		
		cameraRotate.setVisible(false);
						
		infoMoney.setText("Geld: 0$");
		infoMoney.setFont(ResourceManager.Arial15B);

		infoCitizens.setText("Einwohner: 0");
		infoCitizens.setFont(ResourceManager.Arial15B);
		
		menuButton.setEvent(GuiEvents.menuButton);

		toolDelete.setEvent(GuiEvents.toolDelete);
		
		blur.setBlurBehind(true);
		blur.setVisible(false);
		
		pauseMenu.setVisible(false);
		
	    pauseMainmenu.setText(ResourceManager.getString("PAUSEMENU_BUTTON_MAINMENU"));
	    pauseMainmenu.setEvent(GuiEvents.pauseMainmenu);

	    pauseLoad.setText(ResourceManager.getString("PAUSEMENU_BUTTON_LOAD"));
	    pauseLoad.setEvent(GuiEvents.pauseLoad);

	    pauseSave.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SAVE"));
	    pauseSave.setEvent(GuiEvents.pauseSave);

	    pauseSettings.setText(ResourceManager.getString("PAUSEMENU_BUTTON_SETTINGS"));
	    pauseSettings.setEvent(GuiEvents.pauseSettings);

	    pauseExit.setText(ResourceManager.getString("PAUSEMENU_BUTTON_EXIT"));
	    pauseExit.setEvent(GuiEvents.pauseExit);

	    pauseResume.setText(ResourceManager.getString("PAUSEMENU_BUTTON_RESUME"));
	    pauseResume.setEvent(GuiEvents.pauseResume);

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
			settingsTitle.setCentered(true);
			settingsTitle.setFont(ResourceManager.Arial30B);
			
			settingsVsyncon.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCON"));
			settingsVsyncon.setEvent(GuiEvents.settingsVsyncon);
			if (ResourceManager.getSetting("vsync").equals("enabled"))settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			
			settingsVsyncoff.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCOFF"));
			settingsVsyncoff.setEvent(GuiEvents.settingsVsyncoff);
			if (!ResourceManager.getSetting("vsync").equals("enabled"))settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
			
			settingsVsync.setText(ResourceManager.getString("SETTINGSMENU_LABEL_VSYNC"));
			settingsVsync.setFont(ResourceManager.Arial15B);
			
			settingsParticles.setText(ResourceManager.getString("SETTINGSMENU_LABEL_PARTICLES"));
			settingsParticles.setFont(ResourceManager.Arial15B);
			
			settingsParticlesoff.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESOFF"));
			settingsParticlesoff.setEvent(GuiEvents.settingsParticlesoff);
			
			settingsParticleslow.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESLOW"));
			settingsParticleslow.setEvent(GuiEvents.settingsParticleslow);
			
			settingsParticlesmiddle.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESMIDDLE"));
			settingsParticlesmiddle.setEvent(GuiEvents.settingsParticlesmiddle);
			
			settingsParticleshigh.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESHIGH"));
			settingsParticleshigh.setEvent(GuiEvents.settingsParticleshigh);
			
			settingsResume.setText(ResourceManager.getString("SETTINGSMENU_BUTTON_RESUME"));
			settingsResume.setEvent(GuiEvents.settingsResume);
///////////
			
		add(debugInfo);	
			debugInfo.setVisible(Main.debugMode);
						  
	}
	
	/**
	 * Shows a message box with an ok button that hides it again.
	 * The messagebox smoothly fades in and out.
	 * @param title The title of the message
	 * @param text The message's text
	 * @param color [OPTIONAL] The background color of the message
	 */
	public void MsgBox(String title, String text)
	{
		MsgBox(title, text, Color.white);
	}
	
	public void MsgBox(String title, String text, Color color)
	{
		MsgBox msgbox = new MsgBox(title, text, color);
		msgbox.setOpacity(0f);
		AnimationManager.animateValue(msgbox, AnimationValue.opacity, 1f, 0.005f);
		AnimationManager.animateValue(msgbox, AnimationValue.Y, msgbox.getY()+10, 0.1f, AnimationManager.ACTION_REVERSE);
		add(msgbox);
	}
	
	/**
	 * Adds an element to the gui
	 * @param guielement
	 */
	public void add(GuiElement guielement)
	{
		elements.add(guielement);
	}
	
	/**
	 * Removes an element from the gui
	 * @param index
	 */
	public void remove(int index){
		elements.remove(index);}
	public void remove(GuiElement guielement){
		elements.remove(guielement);}
	
	public void draw()
	{
		if(!isVisible())return;
		
		glDisable(GL_DEPTH_TEST);
		glPushMatrix();
		
		glDisable(GL_LIGHTING);
		for(GuiElement element: elements) {
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
		for(GuiElement element: menuElements) {
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
			glMatrixMode(GL_MODELVIEW);
			element.draw();
		}
		glPopMatrix();
		glEnable(GL_DEPTH_TEST);
	}
	
	public GuiElement mouseoverMenu()
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
	
	public GuiElement getMouseover()
	{
		for(int i=elements.size()-1;i>=0;i--)
		{
			if(elements.get(i).isScreenVisible()
					&&elements.get(i).getX()<Mouse.getX()
					&&elements.get(i).getY()<Mouse.getY()
					&&elements.get(i).getWidth()+elements.get(i).getX()>Mouse.getX()
					&&elements.get(i).getHeight()+elements.get(i).getY()>Mouse.getY()
					&&!elements.get(i).isClickThrough()) return elements.get(i).mouseover();
		}
		return null;
	}
	
	public void callGuiEvents(GuiEventType eventtype)
	{
		GuiElement mo = getMouseover();
		if(mo!=null)mo.callGuiEvents(eventtype);
	}
	
	public void callGuiEventsMenu(GuiEventType eventtype)
	{
		GuiElement mo = mouseoverMenu();
		if(mo!=null)mo.callGuiEvents(eventtype);
	}
	
	public void callGuiEvents(GuiEventType eventtype, GuiElement element)
	{
		if(element!=null)element.callGuiEvents(eventtype);
	}

	public GuiElement getKeyboardfocus() {
		return keyboardfocus;
	}

	public void setKeyboardfocus(GuiElement keyboardfocus) {
		this.keyboardfocus = keyboardfocus;
	}

	public static boolean isVisible() {
		return visible;
	}

	public static void setVisible(boolean visible) {
		GUI.visible = visible;
	}
	
}
