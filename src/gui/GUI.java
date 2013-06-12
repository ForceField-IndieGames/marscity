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

/**
 * This class generates and displays the gui.
 * @author Benedikt Ringlein
 */

public class GUI {
	
	private GuiElement keyboardfocus;
	private static boolean visible = true;
	
	public GuiPanel MenuBG = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_MAINMENUBG);
	public GuiPanel MenuFF = new GuiPanel(Display.getWidth()-512,0,512,128,ResourceManager.TEXTURE_MAINMENUFF);
	public GuiButton MenuPlay = new GuiButton(0, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_PLAY"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(GuiEvents.MenuPlay);
	}};
	public GuiButton MenuLoad = new GuiButton(220, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_LOAD"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(GuiEvents.MenuLoad);
	}};
	public GuiButton MenuSettings = new GuiButton(440, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_SETTINGS"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(GuiEvents.MenuSettings);
	}};
	public GuiButton MenuExit = new GuiButton(660, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_EXIT"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(GuiEvents.MenuExit);
	}};
	public GuiLabel MenuVersion = new GuiLabel(0,0,180,20,(Color)null){{setText("Mars City [Alpha]");}};
	public GuiPanel MenuIcon = new GuiPanel(Display.getWidth()/2-64,20,64,64,ResourceManager.TEXTURE_ICON256){{
		setOpacity(0.9f);
	}};
	public GuiPanel IntroFF = new GuiPanel(Display.getWidth()/2-960,Display.getHeight()/2-540,1920,1080,ResourceManager.TEXTURE_FORCEFIELDBG){{
		setVisible(false);
	}};
	public GuiPanel MenuPanel = new GuiPanel(Display.getWidth()/2-430,Display.getHeight()-150,1080,50,(Color)null){{
		add(MenuPlay);
		add(MenuLoad);
		add(MenuSettings);
		add(MenuExit);
	}};
	public LoadingScreen loadingscreen = new LoadingScreen();
	
	public BuildingToolTip buildingTooltip = new BuildingToolTip(){{
		setVisible(false);
	}};
	
	public GuiPanel deleteBorder = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_GUIDELETEBORDER){{
		setClickThrough(true);
		setVisible(false);
	}};
	
	public GuiButton menuButton = new GuiButton(0, 64, 32, 32, ResourceManager.TEXTURE_GUIMENUBUTTON){{
		setEvent(GuiEvents.menuButton);
	}};
	public GuiButton toolDelete = new GuiButton(0, 0, 64, 64, ResourceManager.TEXTURE_GUIDELETE){{
		setEvent(GuiEvents.toolDelete);
	}};
	public GuiPanel guiTools = new GuiPanel(0,0,69,100,ResourceManager.TEXTURE_GUITOOLSBG){{
		add(menuButton);
		add(toolDelete);
	}};
	
	public GuiTextbox cityName = new GuiTextbox(100,5,200,30){{
		setText(Main.cityname);
		setCharlimit(25);
		setEvent(GuiEvents.cityName);
	}};
	public GuiLabel infoMoney = new GuiLabel(350,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setText("Geld: 0$");
		setFont(ResourceManager.Arial15B);
	}};
	public GuiLabel infoCitizens = new GuiLabel(600,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setText("Einwohner: 0");
		setFont(ResourceManager.Arial15B);
	}};
	public GuiPanel infoBar = new GuiPanel(0,30,Display.getWidth(),40,ResourceManager.TEXTURE_GUITOOLBAR){{
		add(cityName);
		add(infoMoney);
		add(infoCitizens);
	}};
	
	public BuildingPanel buildingPanelStreet = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_STREETS")){{
		addBuildingButton(ResourceManager.BUILDINGTYPE_STREET);
	}};
	public BuildingPanel buildingPanelResidential = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_RESIDENTIAL")){{
		addBuildingButton(ResourceManager.BUILDINGTYPE_HOUSE);
		addBuildingButton(ResourceManager.BUILDINGTYPE_BIGHOUSE);
	}};
	public GuiPanel buildingPanelsl = new GuiPanel(-50,0,50,100,ResourceManager.TEXTURE_GUIBUILDINGSPANELL);
	public GuiPanel buildingPanels = new GuiPanel(150,20,Display.getWidth(),100,ResourceManager.TEXTURE_GUIBUILDINGSPANEL,(Color)null){{
		setVisible(false);
		add(buildingPanelsl);
		
		add(buildingPanelStreet);
		add(buildingPanelResidential);
	}};
	public BuildingCategories buildingCategories = new BuildingCategories(150,0,Display.getWidth(),50){{
		addCategoryButton(buildingPanelStreet);
		addCategoryButton(buildingPanelResidential);
	}};
	
	public GuiPanel toolBar = new GuiPanel(0,0,Display.getWidth(),70, ResourceManager.TEXTURE_GUITOOLBAR){{
		add(buildingCategories);
		add(infoBar);
	}};
	
	public GuiPanel blur = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),(Color)null){{
		setBlurBehind(true);
		setVisible(false);
	}};
	
	public GuiButton pauseMainmenu = new GuiButton(28, 200, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_MAINMENU"));
	    setEvent(GuiEvents.pauseMainmenu);
	}};
	public GuiButton pauseLoad = new GuiButton(28, 170, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_LOAD"));
	    setEvent(GuiEvents.pauseLoad);
	}};
	public GuiButton pauseSave = new GuiButton(28, 140, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_SAVE"));
	    setEvent(GuiEvents.pauseSave);
	}};
	public GuiButton pauseSettings = new GuiButton(28, 110, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_SETTINGS"));
	    setEvent(GuiEvents.pauseSettings);
	}};
	public GuiButton pauseExit = new GuiButton(28, 80, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_EXIT"));
	    setEvent(GuiEvents.pauseExit);
	}};
	public GuiButton pauseResume = new GuiButton(28, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_RESUME"));
		setEvent(GuiEvents.pauseResume);
	}};
	public GuiPanel pauseLogo = new GuiPanel(-128,256,512,128,ResourceManager.TEXTURE_MARSCITYLOGO);
	public GuiPanel pauseMenu = new GuiPanel(Display.getWidth()/2-128,Display.getHeight()/2-128,256,256,ResourceManager.TEXTURE_GUIMENU){{
		setVisible(false);
		add(pauseLogo);
		add(pauseMainmenu);
		add(pauseLoad);
		add(pauseSave);
		add(pauseSettings);
		add(pauseExit);
		add(pauseResume);
	}};
	
	public GuiLabel debugInfo = new GuiLabel(0,Display.getHeight()-20,Display.getWidth(),20,Color.white){{
		setVisible(Main.debugMode);
	}};
	
	public GuiButton settingsResume = new GuiButton(156, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_RESUME"));
		setEvent(GuiEvents.settingsResume);
	}};
	public GuiLabel settingsTitle = new GuiLabel(0, 460, 512,40, (Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_TITLE"));
		setCentered(true);
		setFont(ResourceManager.Arial30B);
	}};
	public GuiLabel settingsVsync = new GuiLabel(30,440,452,20,(Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_VSYNC"));
		setFont(ResourceManager.Arial15B);
	}};
	public GuiButton settingsVsyncon = new GuiButton(30,410,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCON"));
		setEvent(GuiEvents.settingsVsyncon);
		if (ResourceManager.getSetting("vsync").equals("enabled"))setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	}};
	public GuiButton settingsVsyncoff = new GuiButton(130,410,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_VSYNCOFF"));
		setEvent(GuiEvents.settingsVsyncoff);
		if (!ResourceManager.getSetting("vsync").equals("enabled"))setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	}};
	public GuiLabel settingsParticles = new GuiLabel(30,380,452,20,(Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_PARTICLES"));
		setFont(ResourceManager.Arial15B);                                 
	}};
	public GuiButton settingsParticlesoff = new GuiButton(30,350,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESOFF"));
		setEvent(GuiEvents.settingsParticlesoff);                              
	}};
	public GuiButton settingsParticleslow = new GuiButton(130,350,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESLOW"));
		setEvent(GuiEvents.settingsParticleslow);                              
	}};
	public GuiButton settingsParticlesmiddle = new GuiButton(230,350,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESMIDDLE"));
		setEvent(GuiEvents.settingsParticlesmiddle);
	}};
	public GuiButton settingsParticleshigh = new GuiButton(330,350,100,30,ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESHIGH"));
		setEvent(GuiEvents.settingsParticleshigh);
	}};
	public GuiPanel settingsMenu = new GuiPanel(Display.getWidth()/2-256,Display.getHeight()/2-256,512,512,ResourceManager.TEXTURE_GUIMENU){{
		setVisible(false);
		add(settingsTitle);
		add(settingsVsyncon);
		add(settingsVsyncoff);
		add(settingsVsync);
		add(settingsParticles);
		add(settingsParticlesoff);
		add(settingsParticleslow);
		add(settingsParticlesmiddle);
		add(settingsParticleshigh);
		add(settingsResume);
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
	}};
	
	public GuiPanel cameraMove = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAMOVE){{
		setVisible(false);
	}};
	public GuiPanel cameraRotate = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAROTATE){{
		setVisible(false);
	}};
	
	List<GuiElement> elements = new ArrayList<GuiElement>();
	List<GuiElement> menuElements = new ArrayList<GuiElement>();
	public GuiElement lastHovered;
	
	public GUI()
	{
		//Main menu
		menuElements.add(MenuBG);
		menuElements.add(MenuFF);
		menuElements.add(MenuIcon);
		menuElements.add(MenuVersion);
		menuElements.add(MenuPanel);
		menuElements.add(IntroFF);
		menuElements.add(loadingscreen);
		menuElements.add(settingsMenu);
		
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
		add(settingsMenu);
		add(debugInfo);			  
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
		add(new MsgBox(title, text, color));
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
