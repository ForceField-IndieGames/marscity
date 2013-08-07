package gui;
import static org.lwjgl.opengl.GL11.*;

import effects.ParticleEffects;
import game.DataView;
import game.Game;
import game.Main;
import game.MonthlyActions;
import game.ResourceManager;
import game.TransactionCategory;
import guielements.GuiButton;
import guielements.GuiCheckbox;
import guielements.GuiGraph;
import guielements.GuiLabel;
import guielements.GuiNumberbox;
import guielements.GuiPanel;
import guielements.GuiProgressbar;
import guielements.GuiQuad;
import guielements.GuiRadiobutton;
import guielements.GuiScrollBar;
import guielements.GuiScrollablePanel;
import guielements.GuiTextbox;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import objects.Buildings;

import org.lwjgl.input.Keyboard;
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
	public GuiButton MenuPlay = new GuiButton(0, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_PLAY"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Game.newGame();
	    				break;
	    		default:break;}}});
	}};
	public GuiButton MenuLoad = new GuiButton(220, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_LOAD"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Main.gui.loadingscreen.show();
	    				break;
	    		default:break;}}});
	}};
	public GuiButton MenuSettings = new GuiButton(440, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_SETTINGS"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Main.gui.settingsMenu.setVisible(true);
	    				break;
	    		default:break;}}});
	}
		@Override
		public void hide() {
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 0, 200, AnimationManager.ACTION_HIDE);
		};
	};
	public GuiButton MenuExit = new GuiButton(660, 0, 200, 50,ResourceManager.TEXTURE_GUIBUTTON2){{
		setText(ResourceManager.getString("MAINMENU_BUTTON_EXIT"));
	    setFont(ResourceManager.Arial15B);
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Game.exit();
	    				break;
	    		default:break;}}});
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
	
	public BuildingToolTip buildingTooltip = new BuildingToolTip();
	public ToolTip tooltip = new ToolTip();
	
	public GuiPanel deleteBorder = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),ResourceManager.TEXTURE_GUIDELETEBORDER){{
		setClickThrough(true);
		setVisible(false);
	}};
	
	public GuiButton menuButton = new GuiButton(0, 64, 32, 32, ResourceManager.TEXTURE_GUIMENUBUTTON){{
		setTooltip(ResourceManager.getString("TOOLTIP_MENUBUTTON"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						Game.Pause();
						Main.gui.blur.setVisible(true);
						Main.gui.pauseMenu.setVisible(true);
						AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.OPACITY, 1, 0.005f);
						break;
				case Mouseover:
						break;
				default:break;}}});
	}};
	public GuiButton toolDelete = new GuiButton(0, 0, 64, 64, ResourceManager.TEXTURE_GUIDELETE){{
		setTooltip(ResourceManager.getString("TOOLTIP_TOOLDELETE"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						Main.selectedTool = Main.TOOL_DELETE;
						Main.gui.toolDelete.setColor(Color.gray);
						Main.buildpreview.setBuilding(-1);
						Main.currentBT = -1;
						Main.gui.deleteBorder.setVisible(true);
						Main.gui.buildingPanels.hide();
						Main.gui.infoBuildingCosts.setVisible(false);
						Main.gui.infoMonthlyCosts.setVisible(false);
						break;
				case Mouseover:
						break;
				default:break;}}});
	}};
	public GuiPanel guiTools = new GuiPanel(0,0,69,100,ResourceManager.TEXTURE_GUITOOLSBG){{
		add(menuButton);
		add(toolDelete);
	}};
	
	public GuiTextbox cityName = new GuiTextbox(100,5,200,30){{
		setTooltip(ResourceManager.getString("TOOLTIP_CITYNAME"));
		setText(Main.cityname);
		setCharlimit(25);
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Keypress:
						if((Keyboard.getEventKey()==Keyboard.KEY_RETURN||Keyboard.getEventKey()==Keyboard.KEY_ESCAPE)&&Keyboard.getEventKeyState()){
							Main.cityname = ((GuiTextbox)e).getText();
						}
						break;
				default:break;}}});
	}};
	public GuiLabel infoBuildingCosts = new GuiLabel(0,23,50,20,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setText("0$");
		setFont(ResourceManager.Arial12);
		AutoSize();
		setVisible(false);
	}
		@Override
		public void setText(String text)
		{
			super.setText("-"+text+"$");
		}
	};
	public GuiLabel infoMonthly = new GuiLabel(150,0,50,30,(Color)null){{
		setTooltip(ResourceManager.getString("TOOLTIP_INFOMONTHLY"));
		setText("0$");
		setFont(ResourceManager.Arial12);
		setRightaligned(true);
	}};
	public GuiLabel infoMonthlyCosts = new GuiLabel(infoMonthly.getScreenX(),23,50,20,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setText("0$");
		setFont(ResourceManager.Arial12);
		AutoSize();
		setVisible(false);
	}
		@Override
		public void setText(String text)
		{
			super.setText("-"+text+"$");
		}
	};
	public GuiLabel infoMoney = new GuiLabel(350,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setTooltip(ResourceManager.getString("TOOLTIP_INFOMONEY"));
		add(infoBuildingCosts);
		add(infoMonthly);
		add(infoMonthlyCosts);
		setText("Money: 0$");
		setFont(ResourceManager.Arial15B);
		setEvent(new GuiEvent(){
			public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
					moneypanel.show();
					break;
				default:
					break;
				}
			};
		});
	}};
	
	public GuiLabel infoCitizens = new GuiLabel(600,5,200,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR){{
		setTooltip(ResourceManager.getString("TOOLTIP_INFOCITIZENS"));
		setText("Citizens: 0");
		setFont(ResourceManager.Arial15B);
		setEvent(new GuiEvent(){
			public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
					citizenspanel.show();
					break;
				default:
					break;
				}
			};
		});
	}};
	public GuiPanel infoBar = new GuiPanel(0,30,Display.getWidth(),40,ResourceManager.TEXTURE_GUITOOLBAR){{
		add(cityName);
		add(infoMoney);
		add(infoCitizens);
	}};
	
	public BuildingPanel buildingPanelStreet = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_STREETS")){{
		addBuildingButton(Buildings.BUILDINGTYPE_STREET);
	}};
	public BuildingPanel buildingPanelResidential = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_RESIDENTIAL")){{
		addBuildingButton(Buildings.BUILDINGTYPE_HOUSE);
		addBuildingButton(Buildings.BUILDINGTYPE_BIGHOUSE);
	}};
	public BuildingPanel buildingPanelService = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_SERVICE")){{
		addBuildingButton(Buildings.BUILDINGTYPE_MEDICALCENTER);
		addBuildingButton(Buildings.BUILDINGTYPE_SERVERCENTER);
		addBuildingButton(Buildings.BUILDINGTYPE_GARBAGEYARD);
		addBuildingButton(Buildings.BUILDINGTYPE_POLICE);
	}};
	public BuildingPanel buildingPanelEnergy = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_ENERGY")){{
		addBuildingButton(Buildings.BUILDINGTYPE_SOLARPOWER);
		addBuildingButton(Buildings.BUILDINGTYPE_FUSIONPOWER);
	}};
	public BuildingPanel buildingPanelMiscellaneous = new BuildingPanel(ResourceManager.getString("BUILDINGCATEGORY_MISCELLANEOUS")){{
		addBuildingButton(Buildings.BUILDINGTYPE_RESEARCHSTATION);
		addBuildingButton(Buildings.BUILDINGTYPE_HANGAR);
		addBuildingButton(Buildings.BUILDINGTYPE_BANK);
	}};
	public BuildingPanels buildingPanels = new BuildingPanels(150,20,Display.getWidth(),100){{
		add(buildingPanelStreet);
		add(buildingPanelResidential);
		add(buildingPanelService);
		add(buildingPanelEnergy);
		add(buildingPanelMiscellaneous);
	}};
	public BuildingCategories buildingCategories = new BuildingCategories(150,0,Display.getWidth(),50){{
		addCategoryButton(buildingPanelStreet);
		addCategoryButton(buildingPanelResidential);
		addCategoryButton(buildingPanelService);
		addCategoryButton(buildingPanelEnergy);
		addCategoryButton(buildingPanelMiscellaneous);
	}};
	
	public GuiPanel toolBar = new GuiPanel(0,0,Display.getWidth(),70, ResourceManager.TEXTURE_GUITOOLBAR){{
		add(buildingCategories);
		add(infoBar);
	}};
	
	public GuiPanel DataViewButtons = new GuiPanel(Display.getWidth()-32,0,32,0,(Color)null)
	{
		{
			setHeight(32*DataView.values().length);
			setY(Display.getHeight()/2-getHeight()/2);
			for(DataView d:DataView.values())
			{
				add(new DataViewButton(0,32*d.ordinal(),32,32,d.getButtonTexture(),d));
			}
		}
	};
	
	public GuiPanel blur = new GuiPanel(0,0,Display.getWidth(),Display.getHeight(),(Color)null){{
		setBlurBehind(true);
		setVisible(false);
	}};
	
	public GuiButton pauseMainmenu = new GuiButton(28, 200, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_MAINMENU"));
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Main.gameState = Main.STATE_MENU;
	    				Main.gui = new GUI();
	    				break;
	    		case Mouseover:
	    				break;
	    		default:break;}}});
	}};
	public GuiButton pauseLoad = new GuiButton(28, 170, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_LOAD"));
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Main.gui.loadingscreen.show();
	    				break;
	    		default:break;}}});
	}};
	public GuiButton pauseSave = new GuiButton(28, 140, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_SAVE"));
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Game.Save("res/cities/"+Main.cityname+".city");
	    				Game.Resume();
	    				Main.gui.blur.setVisible(false);
	    				AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.OPACITY, 1, 0.005f, AnimationManager.ACTION_HIDE);
	    				break;
	    		case Mouseover:
	    				break;
	    		default:break;}}});
	}};
	public GuiButton pauseSettings = new GuiButton(28, 110, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_SETTINGS"));
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Main.gui.pauseMenu.setVisible(false);
	    				Main.gui.settingsMenu.setVisible(true);
	    				AnimationManager.animateValue(Main.gui.settingsMenu, AnimationValue.OPACITY, 1, 0.005f);
	    				break;
	    		case Mouseover:
	    				break;
	    		default:break;}}});
	}};
	public GuiButton pauseExit = new GuiButton(28, 80, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_EXIT"));
	    setEvent(new GuiEvent(){
	    	@Override public void run(GuiEventType eventtype) {
	    		switch (eventtype) {
	    		case Click:
	    				Game.exit();
	    				break;
	    		case Mouseover:
	    				break;
	    		default:break;}}});
	}};
	public GuiButton pauseResume = new GuiButton(28, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("PAUSEMENU_BUTTON_RESUME"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						Main.gui.blur.setVisible(false);
						Game.Resume();
						AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.OPACITY, 0, 0.005f, AnimationManager.ACTION_HIDE);
						break;
				case Mouseover:
						break;
				default:break;}}});
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
	}
		@Override
		public void hide() {
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 0, 200, AnimationManager.ACTION_HIDE);
		};
	};
	
	public GuiLabel debugInfo = new GuiLabel(0,Display.getHeight()-20,Display.getWidth(),20,Color.white){{
		setVisible(Main.debugMode);
	}};
	
	public GuiButton settingsResume = new GuiButton(156, 30, 200, 30, ResourceManager.TEXTURE_GUIBUTTON){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_RESUME"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
					switch (Main.gameState) {
					case Main.STATE_MENU:
						Main.gui.settingsMenu.setVisible(false);
						break;
					case Main.STATE_GAME:
						Game.Resume();
						Main.gui.blur.setVisible(false);
						AnimationManager.animateValue(Main.gui.settingsMenu, AnimationValue.OPACITY, 0, 0.005f, AnimationManager.ACTION_HIDE);
						break;
					default:
						break;
					}
						break;
				case Mouseover:
						break;
				default:break;}}});
	}};
	public GuiLabel settingsTitle = new GuiLabel(0, 460, 512,40, (Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_TITLE"));
		setCentered(true);
		setFont(ResourceManager.Arial30B);
	}};
	public GuiLabel settingsVsyncLabel = new GuiLabel(30,440,452,20,(Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_VSYNCLABEL"));
		setFont(ResourceManager.Arial15B);
	}};
	public GuiCheckbox settingsVsync = new GuiCheckbox(30,410,440){{
		setText(ResourceManager.getString("SETTINGSMENU_CHECKBOX_VSYNC"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype, GuiElement element) {
				switch (eventtype) {
				case Click:
						try {
							GuiCheckbox c = (GuiCheckbox) element;
							if(c.isChecked()){
								ResourceManager.setSetting("vsync", "enabled");
							}else{
								ResourceManager.setSetting("vsync", "disabled");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				default:break;}}});
		if(ResourceManager.getSetting("vsync").equals("enabled"))setChecked(true);
	}};
	public GuiLabel settingsParticlesLabel = new GuiLabel(30,380,452,20,(Color)null){{
		setText(ResourceManager.getString("SETTINGSMENU_LABEL_PARTICLES"));
		setFont(ResourceManager.Arial15B);                                 
	}};
	public GuiRadiobutton settingsParticlesoff = new GuiRadiobutton(30,0,100){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESOFF"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						try {
							ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
				 			ResourceManager.setSetting("particlequality", "off");
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				default:break;}}});                              
	}};
	public GuiRadiobutton settingsParticleslow = new GuiRadiobutton(130,0,100){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESLOW"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						try {
							ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
				 			ResourceManager.setSetting("particlequality", "low");
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				case Mouseover:
						break;
				default:break;}}});                              
	}};
	public GuiRadiobutton settingsParticlesmiddle = new GuiRadiobutton(230,0,100){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESMIDDLE"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						try {
							ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
				 			ResourceManager.setSetting("particlequality", "middle");
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				case Mouseover:
						break;
				default:break;}}});
	}};
	public GuiRadiobutton settingsParticleshigh = new GuiRadiobutton(330,0,100){{
		setText(ResourceManager.getString("SETTINGSMENU_BUTTON_PARTICLESHIGH"));
		setEvent(new GuiEvent(){
			@Override public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
						try {
							ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
							ResourceManager.setSetting("particlequality", "high");
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				case Mouseover:
						break;
				default:break;}}});
	}};
	public GuiPanel settingsParticlesRadiobuttons = new GuiPanel(0,350,400,32){{
		setColor(null);
		add(settingsParticlesoff);
		add(settingsParticleslow);
		setClickThrough(true);
		add(settingsParticlesmiddle);
		add(settingsParticleshigh);
	}};
	public GuiPanel settingsMenu = new GuiPanel(Display.getWidth()/2-256,Display.getHeight()/2-256,512,512,ResourceManager.TEXTURE_GUIMENU){{
		setVisible(false);
		add(settingsTitle);
		add(settingsVsync);
		add(settingsVsyncLabel);
		add(settingsParticlesLabel);
		add(settingsParticlesRadiobuttons);
		add(settingsResume);
		if(ResourceManager.getSetting("particlequality").equals("off")){
			settingsParticlesoff.setChecked(true);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
		}else if(ResourceManager.getSetting("particlequality").equals("low")){
			settingsParticleslow.setChecked(true);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
		}else if(ResourceManager.getSetting("particlequality").equals("middle")){
			settingsParticlesmiddle.setChecked(true);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
		}else if(ResourceManager.getSetting("particlequality").equals("high")){
			settingsParticleshigh.setChecked(true);
			ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
		}
	}
		@Override
		public void hide() {
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 0, 200, AnimationManager.ACTION_HIDE);
		};
	};
	
	public GuiPanel cameraMove = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAMOVE){{
		setVisible(false);
	}};
	public GuiPanel cameraRotate = new GuiPanel(Display.getWidth()/2-16,Display.getHeight()/2-16,32,32,ResourceManager.TEXTURE_GUICAMERAROTATE){{
		setVisible(false);
	}};
	
	public BuildingInfo buildinginfo = new BuildingInfo();
	
	public GuiPanel moneycategories = new GuiPanel(30,30,452,412,(Color)null);
	public GuiNumberbox taxes = new GuiNumberbox(372, 450, 110, 32);
	public GuiPanel moneypanel = new GuiPanel(infoMoney.getScreenX()+infoMoney.getWidth()/2-256,infoMoney.getScreenY()+infoMoney.getHeight(),512,512,ResourceManager.TEXTURE_MONEYBG){
		{
		setVisible(false);
		setOpacity(0f);
		GuiLabel taxeslabel = new GuiLabel(30,452,482,30,(Color)null);
		taxeslabel.setText(ResourceManager.getString("MONEYPANEL_LABEL_TAXES"));
		taxeslabel.setFont(ResourceManager.Arial15B);
		add(taxeslabel);
		taxes.setSuffix("%");
		taxes.setValue(Main.taxes);
		taxes.setMax(100);
		taxes.setEvent(new GuiEvent(){
			public void run(GuiEventType eventtype, GuiElement e) {
				switch (eventtype) {
				case Valuechange:
					Main.taxes = (byte) ((GuiNumberbox)e).getValue();
					break;
				default:
					break;
				}
			};
		});
		add(taxes);
		add(moneycategories);
		for(TransactionCategory t:TransactionCategory.values())
		{
			GuiProgressbar p = new GuiProgressbar(10, moneycategories.getHeight()-t.ordinal()*40-32, moneycategories.getWidth()-20, 32);
			p.setBarColor(Color.green);
			p.setText(t.getName());
			moneycategories.add(p);
		}
	}
		@Override public void show() {
			setVisible(true);
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 1f, 100);
			AnimationManager.animateValue(this, AnimationValue.Y, getY()+10, 100, AnimationManager.ACTION_REVERSE);
		};
		
		@Override public void hide() {
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 0f, 200,AnimationManager.ACTION_HIDE);
			AnimationManager.animateValue(this, AnimationValue.Y, getY()-10, 200, AnimationManager.ACTION_RESET);
		};
	};
	public GuiGraph populationGraph = new GuiGraph(30, 45, 452, 420, 0);
	public GuiLabel max = new GuiLabel(280,444,200,30,(Color)null);
	public GuiPanel citizenspanel = new GuiPanel(infoCitizens.getScreenX()+infoCitizens.getWidth()/2-256,infoCitizens.getScreenY()+infoCitizens.getHeight(),512,512,ResourceManager.TEXTURE_MONEYBG){
		{
			setVisible(false);
			setOpacity(0);
			populationGraph.setGraphColor(new Color(255,255,200));
			add(populationGraph);
			GuiLabel today = new GuiLabel(282,20,200,30,(Color)null);
			today.setRightaligned(true);
			today.setText(ResourceManager.getString("CITIZENSPANEL_LABEL_TODAY"));
			add(today);
			GuiLabel past = new GuiLabel(30,20,200,30,(Color)null);
			past.setText(ResourceManager.getString("CITIZENSPANEL_LABEL_PAST").replaceFirst(ResourceManager.PLACEHOLDER1, ""+MonthlyActions.PopulationStatistics.length));
			add(past);
			GuiLabel min = new GuiLabel(470,40,10,30,(Color)null);
			min.setRightaligned(true);
			min.setText(""+0);
			add(min);
			max.setRightaligned(true);
			max.setText(""+1000);
			add(max);
			GuiQuad line = new GuiQuad(482, 484, 484, 482, 45, 45, 464, 464);
			line.setColor(Color.black);
			add(line);
		}
		@Override public void show() {
			setVisible(true);
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 1f, 200);
			AnimationManager.animateValue(this, AnimationValue.Y, getY()+10, 100, AnimationManager.ACTION_REVERSE);
		};
		
		@Override public void hide() {
			AnimationManager.animateValue(this, AnimationValue.OPACITY, 0f, 200,AnimationManager.ACTION_HIDE);
			AnimationManager.animateValue(this, AnimationValue.Y, getY()-10, 200, AnimationManager.ACTION_RESET);
		};
	};
	
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
		add(buildinginfo);
		add(DataViewButtons);
		add(moneypanel);
		add(citizenspanel);
		add(buildingTooltip);
		add(blur);
		add(pauseMenu);
		add(loadingscreen);
		add(settingsMenu);
		add(debugInfo);		
		add(tooltip);
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
	
	public void showToolTip(String text)
	{
		tooltip.show(text);
	}
	
	public void hideToolTip()
	{
		tooltip.hide();
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
