package game;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import effects.ParticleEffects;
import gui.GUI;
import gui.guiElement;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import objects.BuildPreview;
import objects.Drawable;
import objects.Building;
import objects.Entity;
import objects.Streets;
import objects.Terrain;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import animation.AnimationManager;
import animation.AnimationValue;


/**
 * @author: Benedikt Ringlein
 * This is the main class with all the rendering and updating code.
 * Input is handled here as well.
 **/

class splashScreen extends JFrame implements Runnable{

	
	private static final long serialVersionUID = 1L;
	
	JLabel label;
	JLabel label2;
	JLabel background;
	public Thread thread;
	
	/**
	 * The splash screen that is displayed while the resources are sill loaded
	 */
	public splashScreen()
	{
		this.setUndecorated(true);
		getContentPane().setBackground(Color.black);
		setVisible(true);
		setTitle("lwjglTest");
		setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width , Toolkit.getDefaultToolkit().getScreenSize().height);
		setAlwaysOnTop(true);
		setLayout(null);
		label = new JLabel("");
		label.setForeground(Color.white);
		label.setFont(new Font("comicsans",Font.BOLD,40));
		add(label);
		label.setBounds(10, getHeight()-50, 50, 50);
		label2 = new JLabel("");
		add(label2);
		label2.setForeground(Color.white);
		label2.setBounds(0, 0, 500, 20);
		background = new JLabel(new ImageIcon(Main.class.getResource("/res/forcefieldbackground.png")));
		add(background);
		background.setBounds(0, 0, getWidth(), getHeight());
		background.setDoubleBuffered(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		//Animate the dots
		while(isVisible())
		{
			if(label.getText()==""){
				label.setText(".");
			}else if(label.getText()=="."){
				label.setText("..");
			}else if(label.getText()==".."){
				label.setText("...");
			}else if(label.getText()=="..."){
				label.setText("");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class Main {
	
	//The tools
	public final static int TOOL_SELECT = 0;
	public final static int TOOL_ADD = 1;
	public final static int TOOL_DELETE = 2;
	
	//The game states (intro is currently not used)
	final static int STATE_INTRO = 0;
	final static int STATE_MENU = 1;
	final static int STATE_GAME = 2;

	//Variables that are used for calculating the delta and fps
	long lastFrame;
	int fpsnow, fps;
	long lastTime;
	
	//The debugmode enables cheats and displays additional debug information
	public static boolean debugMode = true;
	
	public static int hoveredEntity = -1; //The index of the object that is hovered with the mouse
	public static int selectedTool = 0; //The selected tool, SELECT,ADD or DELETE
	public static int money = 10000000; //The players money
	public static int currentBuildingType = -1; //The currently selected building type
	public static float[] mousepos3d=new float[3]; //The mouse position in 3d space
	static int gameState = STATE_MENU; //The current game state
	
	//Some more objects
	public static Camera camera = new Camera();
	Terrain terrain; 
	Entity skybox;
	public static GUI gui;
	BuildPreview buildpreview;
	static splashScreen splashscreen;
	
	/**
	 * Writes a string into the log file
	 * @param text The string to be written
	 */
	public static void log(String text)
	{
		try {
			FileWriter log = new FileWriter("mars city.log",true);
			log.append(text+System.lineSeparator());
			log.close();
			
		} catch (IOException e1) {e1.printStackTrace();}
	}

	/**
	 * Starts the main game loop
	 * @throws FileNotFoundException Why? idk.
	 */
	public void start() throws FileNotFoundException {
		
		
		try {
			//Setup the display
			if(debugMode){
				Display.setDisplayMode(new DisplayMode(1300, 690));
				Display.setResizable(true);
			}else Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setTitle("Mars City");
			Display.setLocation(0, 0);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Display konnte nicht erstellt werden");
			Game.exit();
		}
		
		//Set the icon
		ByteBuffer[] list = new ByteBuffer[2];
		list[0] = ByteBuffer.wrap(ResourceManager.TEXTURE_ICON16.getTextureData());
		list[1] =  ByteBuffer.wrap(ResourceManager.TEXTURE_ICON32.getTextureData());
		Display.setIcon(list);
		
		//Delete the log file
		try {(new File("mars city.log")).delete();} catch (Exception e) {}
		
		System.out.println("Mars City started...");
		log("Mars City started...");
		
		ResourceManager.init();//Initialize the resources
		
		log("Finished loading resources.");
		
		gui = new GUI(); //Create the GUI
		buildpreview = new BuildPreview(); //Create the Building Preview
		skybox = new Entity(ResourceManager.OBJECT_SKYBOX, ResourceManager.TEXTURE_SKYBOX);
		terrain = new Terrain(0,0,0);//create the terrain
		
		//Enable vsync according to the settings
		if(ResourceManager.getSetting("vsync").equals("enabled"))Display.setVSyncEnabled(true);

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastTime = getTime(); // call before loop to initialise fps timer
		
		splashscreen.setVisible(false); //Hide the splashscreen
		

		//Main Gameloop
		while (!Display.isCloseRequested()) {
			int delta = getDelta(); //Calculate the time between two Frames, for correct timing
			
			
			switch(gameState){
			case(STATE_INTRO): 
				break;
			case(STATE_MENU):
				updateMenu(delta);
				renderMenu();
				break;
			case(STATE_GAME):
				update(delta); //Gamelogic
				renderGL();    //Rendering
				break;
			default: break;
			}
			
			Display.update(); //Refresh the display
			Display.sync(60); //Limit framerate to 60
		}

		Game.exit();
	}
	
	
	
	
	
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	private int getDelta() {
		
		
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	private long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate the FPS
	 */
	private void updateFPS() {
		if (getTime() - lastTime > 1000) {
			fps = fpsnow;
			fpsnow = 0;
			lastTime += 1000;
		}
		fpsnow++;
	}
	
	/**
	 * Get the Objectindex of the Object under the Mousecursor
	 */
	private void picking()
	{
		glEnable(GL_SCISSOR_TEST);
		glScissor(Mouse.getX(), Mouse.getY(), 1, 1);
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		for(int i=0;i<ResourceManager.objects.size();i++){
			glColor3ub((byte) ((i >> 0) & 0xff), (byte) ((i >> 8) & 0xff), (byte) ((i >> 16) & 0xff));
			ResourceManager.getObject(i).draw();
		}
		new BufferUtils();
		ByteBuffer color = BufferUtils.createByteBuffer(4);
		glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL_RGB, GL_UNSIGNED_BYTE, color);
        hoveredEntity = color.getInt(0);
        if(hoveredEntity>16000000)hoveredEntity=-1;
        glDisable(GL_SCISSOR_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glColor3f(1f, 1f, 1f);
	}
	
	/**
	 * Calculates the position of the mousecursor in 3d space.
	 * Only points on the terrain can be mouse positions.
	 */
	private void picking3d()
	{
		final IntBuffer vp = BufferUtils.createIntBuffer(16);
        final FloatBuffer mv = BufferUtils.createFloatBuffer(16);
        final FloatBuffer p = BufferUtils.createFloatBuffer(16);
        final FloatBuffer result = BufferUtils.createFloatBuffer(3);
        final FloatBuffer mouseZ = BufferUtils.createFloatBuffer(1);
        
        glEnable(GL_SCISSOR_TEST);
		glScissor(Mouse.getX(), Mouse.getY(), 1, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		terrain.draw();
		glDisable(GL_SCISSOR_TEST);
 
        glGetInteger(GL_VIEWPORT,vp);
        glGetFloat(GL_MODELVIEW_MATRIX, mv);
        glGetFloat(GL_PROJECTION_MATRIX, p);
        glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL_DEPTH_COMPONENT, GL_FLOAT, mouseZ);
        gluUnProject(Mouse.getX(), Mouse.getY(), mouseZ.get(0), mv, p,  vp,  result);
        
        mousepos3d[0] = result.get(0);
        mousepos3d[1] = result.get(1);
        mousepos3d[2] = result.get(2);
	}
	
	/**
	 * Manages the gui input
	 * @param guihit The element that was hit
	 */
	public void inputGui(guiElement guihit)
	{
		//The menu button (...)
		if(guihit==gui.menuButton){
			Game.Pause();
			gui.blur.setVisible(true);
			gui.pauseMenu.setVisible(true);
			AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 1, 0.005f);
		}
		//The add tool button (+)
		if(guihit==gui.toolAdd){
			selectedTool = TOOL_ADD;
			gui.toolAdd.setColor(Color.gray);
			gui.toolDelete.setColor(Color.white);
			AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, 0, 0.5f);
			gui.deleteBorder.setVisible(false);
		}
		//The delete tool button (X)
 		if(guihit==gui.toolDelete){
			selectedTool = TOOL_DELETE;
			gui.toolAdd.setColor(Color.white);
			gui.toolDelete.setColor(Color.gray);
			AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, -40, 0.5f);
			buildpreview.setBuilding(-1);
			currentBuildingType = -1;
			gui.deleteBorder.setVisible(true);
		}
 		//The resume button in the pause menu
 		if(guihit==gui.pauseResume){
			gui.blur.setVisible(false);
			Game.Resume();
			AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
		}
 		//The exit button in the pause menu
 		if(guihit==gui.pauseExit){
			Game.exit();
		}
 		//The button for switching on vsync in the settings
 		if(guihit==gui.settingsVsyncon){
 			try {
				gui.settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
				gui.settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				ResourceManager.setSetting("vsync", "enabled");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The button for switching off vsync in the settings
 		if(guihit==gui.settingsVsyncoff){
 			try {
	 			gui.settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				gui.settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			ResourceManager.setSetting("vsync", "disabled");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The button for switching off particles in the settings
 		if(guihit==gui.settingsParticlesoff){
 			try {
				gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
	 			ResourceManager.setSetting("particlequality", "off");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The button for low quality particles in the settings
 		if(guihit==gui.settingsParticleslow){
 			try {
				gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
	 			ResourceManager.setSetting("particlequality", "low");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The button for middle quality particles in the settings
 		if(guihit==gui.settingsParticlesmiddle){
 			try {
				gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
	 			ResourceManager.setSetting("particlequality", "middle");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The button for high quality particles in the settings
 		if(guihit==gui.settingsParticleshigh){
 			try {
				gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
	 			ResourceManager.setSetting("particlequality", "high");
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		//The resume button in the settings panel
 		if(guihit==gui.settingsResume){
			Game.Resume();
			gui.blur.setVisible(false);
			AnimationManager.animateValue(gui.settingsMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
		}
 		//The settings button in the pause menu
		if(guihit==gui.pauseSettings){
		gui.pauseMenu.setVisible(false);
		gui.settingsMenu.setVisible(true);
		AnimationManager.animateValue(gui.settingsMenu, AnimationValue.opacity, 1, 0.005f);
		}
		//The main menu button in the pause menu
 		if(guihit==gui.pauseMainmenu){
			gameState = STATE_MENU;
		}
 		//The street button in the building menu
 		if(guihit==gui.buildingStreet){
 			currentBuildingType = ResourceManager.BUILDINGTYPE_STREET;
 			buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_STREET);
 			gui.buildingStreet.setColor(Color.gray);
 		}else gui.buildingStreet.setColor(Color.white);
 		//The house button in the building menu
 		if(guihit==gui.buildingHouse){
 			currentBuildingType = ResourceManager.BUILDINGTYPE_HOUSE;
 			buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_HOUSE);
 			gui.buildingHouse.setColor(Color.gray);
 		}else gui.buildingHouse.setColor(Color.white);
 		//The big house button in the building menu
 		if(guihit==gui.buildingBighouse){
 			currentBuildingType = ResourceManager.BUILDINGTYPE_BIGHOUSE;
 			buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_BIGHOUSE);
 			gui.buildingBighouse.setColor(Color.gray);
 		}else gui.buildingBighouse.setColor(Color.white);
 		//The save button in the pause menu
 		if(guihit==gui.pauseSave){
 			Game.Save("res/saves/savegame.save");
 			Game.Resume();
 			gui.blur.setVisible(false);
			AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 1, 0.005f, AnimationManager.ACTION_HIDE);
 		}
 		//The load button in the pause menu
 		if(guihit==gui.pauseLoad){
 			Game.Load("res/saves/savegame.save");
			gui = null;
			gui = new GUI();
			Game.Resume();
 		}
	}
	
	/**
	 * Processes Keyboard inputs
	 * @param delta The calculated delta for timing
	 */
	public void inputKeyboard(int delta)
	{
		//Movable object
		try {
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) ResourceManager.objects.get(1).setX(ResourceManager.objects.get(1).getX() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) ResourceManager.objects.get(1).setX(ResourceManager.objects.get(1).getX() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) ResourceManager.objects.get(1).setY(ResourceManager.objects.get(1).getY() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) ResourceManager.objects.get(1).setY(ResourceManager.objects.get(1).getY() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) ResourceManager.objects.get(1).setZ(ResourceManager.objects.get(1).getZ() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) ResourceManager.objects.get(1).setZ(ResourceManager.objects.get(1).getZ() - 0.05f * delta);
		} catch (Exception e) {
		}
				
				//Camera movement with WASD and space, leftshift
				if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
					camera.setX((float) (camera.getX()-0.002f*delta*camera.getZoom()*Math.sin(Math.toRadians(camera.getRotY()))));
					camera.setZ((float) (camera.getZ()-0.002f*delta*camera.getZoom()*Math.cos(Math.toRadians(camera.getRotY()))));
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
					camera.setX((float) (camera.getX()+0.002f*delta*camera.getZoom()*Math.sin(Math.toRadians(camera.getRotY()))));
					camera.setZ((float) (camera.getZ()+0.002f*delta*camera.getZoom()*Math.cos(Math.toRadians(camera.getRotY()))));
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
					camera.setX((float) (camera.getX()-0.002f*delta*camera.getZoom()*Math.cos(Math.toRadians(camera.getRotY()))));
					camera.setZ((float) (camera.getZ()+0.002f*delta*camera.getZoom()*Math.sin(Math.toRadians(camera.getRotY()))));
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
					camera.setX((float) (camera.getX()+0.002f*delta*camera.getZoom()*Math.cos(Math.toRadians(camera.getRotY()))));
					camera.setZ((float) (camera.getZ()-0.002f*delta*camera.getZoom()*Math.sin(Math.toRadians(camera.getRotY()))));
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.setY(camera.getY()+0.1f*delta);
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.setY(camera.getY()-0.1f*delta);
				
				//The o key aktivates a particle effect in debug mode
				if(Keyboard.isKeyDown(Keyboard.KEY_O)&&debugMode){
					ParticleEffects.dustEffect(mousepos3d[0], mousepos3d[1], mousepos3d[2]);
				}
				
				//Process the key press and release events
				while(Keyboard.next()){
					//Pause and resume game with ESC
					if(Keyboard.getEventKey()==Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()){
						if(Game.isPaused())
						{
							Game.Resume();
							gui.blur.setVisible(false);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pauseMenu.setVisible(true);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 1, 0.005f);
						}
						if(debugMode)Game.exit();
					}
					//Pause and resume game with p
					if(Keyboard.getEventKey()==Keyboard.KEY_P && Keyboard.getEventKeyState())
					{
						if(Game.isPaused())
						{
							Game.Resume();
							gui.blur.setVisible(false);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pauseMenu.setVisible(true);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 1, 0.005f);
						}
					}
					//Activate and deactivate debug mode with TAB
					if(Keyboard.getEventKey()==Keyboard.KEY_TAB&&Keyboard.getEventKeyState()){
						if(debugMode){
							debugMode = false;
							gui.debugInfo.setVisible(false);
						}else{
							debugMode = true;
							gui.debugInfo.setVisible(true);
						}
					}
					//Switch Fullscreen/Window with F4 in debug mode
					if(Keyboard.getEventKey()==Keyboard.KEY_F4&&Keyboard.getEventKeyState()&&debugMode){
						if(Display.isFullscreen())try {Display.setFullscreen(false);Display.setDisplayMode(Display.getDesktopDisplayMode());} catch (LWJGLException e) {e.printStackTrace();}
						else try {Display.setFullscreen(true);} catch (LWJGLException e) {e.printStackTrace();}
					}
					//Get money with F1 in debug mode
					if(Keyboard.getEventKey()==Keyboard.KEY_F1&&Keyboard.getEventKeyState()&&debugMode){
						money += 50000;
					}
				}
	}
	
	/**
	 * Process mouse inputs
	 * @param delta The calculated delta for timing
	 */
	public void inputMouse(int delta)
	{
		//Is the mouse over a gui item?
		guiElement guihit = gui.mouseover();
		
		
		if(guihit==null || Mouse.isGrabbed())
		{
			
			int MX = Mouse.getDX();
			int MY = Mouse.getDY();
	
			//Rotate the camera with right mouse button
			if(Mouse.isButtonDown(1)){
				camera.setRotY(camera.getRotY()-0.1f*MX);
				camera.setRotX(camera.getRotX()+0.1f*MY);
				if(camera.getRotX()<-89)camera.setRotX(-89);
				if(camera.getRotX()>-1)camera.setRotX(-1);
			}
			//Move the camera with middle mouse button
			if(Mouse.isButtonDown(2)){
				camera.setX((float) (camera.getX()+delta*(0.00008f*camera.getZoom()+0.0005f)*MY*Math.sin(Math.toRadians(camera.getRotY()))-delta*(0.00008f*camera.getZoom()+0.0005f)*MX*Math.cos(Math.toRadians(camera.getRotY()))));
				camera.setZ((float) (camera.getZ()+delta*(0.00008f*camera.getZoom()+0.0005f)*MY*Math.cos(Math.toRadians(camera.getRotY()))+delta*(0.00008f*camera.getZoom()+0.0005f)*MX*Math.sin(Math.toRadians(camera.getRotY()))));
			}
		}
		
		//Process Mouse events
		while(Mouse.next())
		{
			//Don't grab mouse when right and middle button are relesed
			if((Mouse.getEventButton()==1||Mouse.getEventButton()==2)&&!Mouse.getEventButtonState())
			{
				Mouse.setGrabbed(false);
			}
			//Only do things when the mouse is not over the gui
			if(guihit==null)
			{
				if(Mouse.getEventButton()==0&&!Mouse.getEventButtonState()&&currentBuildingType==ResourceManager.BUILDINGTYPE_STREET){
					Streets.endBuilding(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
				}
				//Do some action with the left mouse button based on the selected tool
				if(Mouse.getEventButton()==0&&Mouse.getEventButtonState()){
					switch(selectedTool)
					{
						case(TOOL_SELECT): //Zoom to a house
							try {
								AnimationManager.animateValue(camera, AnimationValue.X, ResourceManager.getObject(hoveredEntity).getX(), 0.2f);
								AnimationManager.animateValue(camera, AnimationValue.Z, ResourceManager.getObject(hoveredEntity).getZ(), 0.2f);
							} catch (Exception e) {}
							break;
						
						case(TOOL_ADD): // Create a new Building at mouse position
							if(currentBuildingType==-1)break;
							if(currentBuildingType==ResourceManager.BUILDINGTYPE_STREET){
								Streets.startBuilding(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
								break;
							}
							if(!Grid.isAreaFree((int)Math.round(mousepos3d[0]), (int)Math.round(mousepos3d[2]), ResourceManager.getBuildingType(currentBuildingType).getWidth(), ResourceManager.getBuildingType(currentBuildingType).getDepth())||money<ResourceManager.getBuildingType(currentBuildingType).getBuidlingcost())break;
								ResourceManager.playSound(ResourceManager.SOUND_DROP);
								Building b = ResourceManager.buildBuilding(mousepos3d[0], mousepos3d[1], mousepos3d[2], currentBuildingType);
								money -= ResourceManager.getBuildingType(currentBuildingType).getBuidlingcost();
								ParticleEffects.dustEffect(b.getX(), b.getY(), b.getZ());
							break;
							
						case(TOOL_DELETE): // Delete the hovered Building
							if(hoveredEntity==-1)break;
							try {
								ResourceManager.playSound(ResourceManager.SOUND_DESTROY);
								Grid.clearsCells((int)ResourceManager.getObject(hoveredEntity).getX(), (int)ResourceManager.getObject(hoveredEntity).getZ(), ResourceManager.getBuildingType(ResourceManager.getObject(hoveredEntity).getBuidlingType()).getWidth(), ResourceManager.getBuildingType(ResourceManager.getObject(hoveredEntity).getBuidlingType()).getDepth());
								ParticleEffects.dustEffect(ResourceManager.getObject(hoveredEntity).getX(),ResourceManager.getObject(hoveredEntity).getY(),ResourceManager.getObject(hoveredEntity).getZ());
								ResourceManager.getObject(hoveredEntity).delete();
							} catch (Exception e) {e.printStackTrace();}
							break;
					}
				}
				//Select the select tool with right mouse button
				if(Mouse.getEventButton()==1&&Mouse.getEventButtonState()){
					selectedTool = TOOL_SELECT;
					gui.toolAdd.setColor(Color.white);
					gui.toolDelete.setColor(Color.white);
					AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, -40, 0.5f);
					buildpreview.setBuilding(-1);
					currentBuildingType = -1;
					gui.deleteBorder.setVisible(false);
				}
				//Set mouse grabbes when pressing irght or middle mouse button
				if((Mouse.getEventButton()==2||Mouse.getEventButton()==1)&&Mouse.getEventButtonState()){
						Mouse.setGrabbed(true);
				}
				//Control the zoom with the mouse wheel
				camera.setZoom((float) (camera.getZoom()-0.001*camera.getZoom()*Mouse.getEventDWheel()));
				if(camera.getZoom()<7)camera.setZoom(7);
				if(camera.getZoom()>1000)camera.setZoom(1000);
		}else{
				//GUI behavior
				if(Mouse.getEventButton()==0&&Mouse.getEventButtonState())
				{
					inputGui(guihit);
				}
			}
	}
	}
	
	
	/**
	 * The Gamelogic
	 * @param delta The delta for timing
	 */
	public void update(int delta) {
		
		//Keyboard input
		inputKeyboard(delta);

		//Mouse input
		inputMouse(delta);
		
		//Continous Mouse
		if(Mouse.getX()<=1) Mouse.setCursorPosition(Display.getWidth()-2, Mouse.getY());
		if(Mouse.getX()>=Display.getWidth()-1) Mouse.setCursorPosition(2, Mouse.getY());
		if(Mouse.getY()<=0) Mouse.setCursorPosition(Mouse.getX(), Display.getHeight()-2);
		if(Mouse.getY()>=Display.getHeight()-1) Mouse.setCursorPosition(Mouse.getX(), 2);
		
		//Run the animations
		AnimationManager.update(delta);
		
		//Move the BuildPreview
		if(selectedTool==TOOL_ADD&&gui.mouseover()==null&&!Mouse.isGrabbed()){
			buildpreview.setX(Grid.cellSize*Math.round(mousepos3d[0]/Grid.cellSize));
			buildpreview.setY(Grid.cellSize*Math.round(mousepos3d[1]/Grid.cellSize));
			buildpreview.setZ(Grid.cellSize*Math.round(mousepos3d[2]/Grid.cellSize));
			buildpreview.setVisible(true);
		}else{
			buildpreview.setVisible(false);
		}
		
		
		//Update the objects
		for(Drawable object:ResourceManager.objects)
		{
			object.update(delta);
			if(!ResourceManager.objects.contains(object))break;
		}
		
		//Show debug info
		String bt = "-";
		try {
			if(Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding()!=null){
			bt = ""+Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getBuidlingType()+" ("+ResourceManager.getBuildingTypeName(Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getBuidlingType())+")";
		}
		} catch (Exception e) {
		}
		
		gui.debugInfo.setText("debug mode | Objects: "+ResourceManager.objects.size()+
				", FPS: "+fps+", ParticleEffects: "+ParticleEffects.getEffectCount()+", Mouse:("+Math.round(mousepos3d[0])+","+Math.round(mousepos3d[1])+","+Math.round(mousepos3d[2])+")"+
				", GridIndex: "+Grid.posToIndex(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]))+
				", BuildingType: "+bt);

		//Update gui info labels
		gui.infoMoney.setText(ResourceManager.getString("INFOBAR_LABEL_MONEY")+": "+money+"$");
		gui.infoCitizens.setText(ResourceManager.getString("INFOBAR_LABEL_CITIZENS")+": "+0);
		
		//Update the paticle effects
		ParticleEffects.update(delta);
		
		//Rotate the camera while in pause mode
		if(Game.isPaused())camera.setRotY(camera.getRotY()+0.05f);
		
		// update FPS Counter
		updateFPS(); 
	}

	/**
	 * "initalize" OpenGL
	 */
	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30f, 1337 / 768f, 0.3f, 3000f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glClearColor(0.7f, 0.4f, 1f, 1f);
		glClearDepth(1); 
		
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glEnable(GL_ALPHA_TEST);
    	glAlphaFunc(GL_GREATER, 0);
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(new float[]{-30f,50,100f,0f}));
		glLight(GL_LIGHT0, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(new float[]{1f,1f,0.9f,1f}));
		glLightModel(GL_LIGHT_MODEL_AMBIENT, BufferTools.asFlippedFloatBuffer(new float[] {0.9f,0.9f,0.9f,1f}));
		
		glEnable(GL_MAP_COLOR);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_FOG);
		glFog(GL_FOG_COLOR, BufferTools.asFlippedFloatBuffer(new float[]{0f,0f,0f,1f}));
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glHint(GL_FOG_HINT, GL_NICEST);
		glFogf(GL_FOG_START, 4000);
		glFogf(GL_FOG_END, 5000);
		glFogf(GL_FOG_DENSITY, 0.0005f);
	}

	/**
	 * Do the actual rendering
	 */
	public void renderGL() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glPushMatrix();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30f, 1337 / 768f, 0.3f, 5000f);
		glMatrixMode(GL_MODELVIEW);
		
		//glUseProgram(shaderProgram);
//		
		
		//Apply the camera transformations
		camera.applyTransform();
		
		//Set the light positions
		glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(new float[]{-30f,50,100f,0f}));
        
		if(gui.mouseover()==null)
		{
			//Picking requires the fog to be disables and the background to be white
			glDisable(GL_FOG);
			glClearColor(1f, 1f, 1f, 1f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			picking();//Picking
			picking3d();//Calculate 3d mouse position
			glClearColor(0f,0f, 0f, 1f);
			glEnable(GL_FOG);
		}else hoveredEntity = -1;
		
        //Rendering
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_TEXTURE_2D);
        
        //Draw the skybox
        glDisable(GL_LIGHTING);
        skybox.draw();
        glEnable(GL_LIGHTING);
        
        //Draw the terrain
        terrain.draw();
       
        //Draw the buidings
        for(int i=0;i<ResourceManager.objects.size();i++){
        	if(i==hoveredEntity&&!Mouse.isGrabbed()&&selectedTool!=TOOL_ADD){
        		if(selectedTool==TOOL_DELETE)glColor3f(1f, 0f, 0f);
        		if(selectedTool==TOOL_SELECT)glColor3f(1f, 1f, 1f);
        		glDisable(GL_LIGHTING);
        	}else {
        		glColor3f(1f, 1f, 1f);
        	}
			ResourceManager.objects.get(i).draw();
			glEnable(GL_LIGHTING);
		}
        
        //Draw the building preview
        buildpreview.draw();
        
        //Draw the particle effects
        ParticleEffects.draw();

//		glUseProgram(0);
		
		glPopMatrix();
		
		//GUI rendern
		gui.draw();
		
	}
	
	/**
	 * Draw the Main menu
	 */
	public void renderMenu()
	{
		gui.drawMenu();
	}
	
	float i=0; //value for the menu animation
	/**
	 * Update the Main menu
	 * @param delta
	 */
	public void updateMenu(int delta)
	{
		//Animate the background image
		gui.MenuBG.setX((float) (30*Math.sin(i)-30));
		gui.MenuBG.setWidth((float) (Display.getWidth()-60*Math.sin(i)+60));
		gui.MenuBG.setY((float) (30*Math.sin(i))-30);
		gui.MenuBG.setHeight((float) (Display.getHeight()-60*Math.sin(i)+60));
		gui.MenuIcon.setX((float) (Display.getWidth()/2-gui.MenuIcon.getWidth()/2+3*Math.sin(2*i)-3));
		gui.MenuIcon.setWidth((float) (128-12*Math.sin(2*i)+12));
		gui.MenuIcon.setY((float) (20+6*Math.sin(2*i)-6));
		gui.MenuIcon.setHeight((float) (128-12*Math.sin(2*i)+12));
		i=(i<2*Math.PI)?i+0.005f:0;
		//Process mouse inputs
		guiElement guihit = gui.mouseoverMenu();
		if(guihit==gui.MenuPlay)gui.MenuPlay.setColor(Color.gray);
		else gui.MenuPlay.setColor(Color.white);
		if(guihit==gui.MenuLoad)gui.MenuLoad.setColor(Color.gray);
		else gui.MenuLoad.setColor(Color.white);
		if(guihit==gui.MenuSettings)gui.MenuSettings.setColor(Color.gray);
		else gui.MenuSettings.setColor(Color.white);
		if(guihit==gui.MenuExit)gui.MenuExit.setColor(Color.gray);
		else gui.MenuExit.setColor(Color.white);
		while(Mouse.next())
		{
			if(Mouse.getEventButton()==0&&Mouse.getEventButtonState()){
				
				if(guihit==null)return;
				if(guihit==gui.MenuPlay){
					Game.newGame();
				}else if(guihit==gui.MenuExit){
					Game.exit();
				}else if(guihit==gui.MenuLoad){
					Game.Load("res/saves/savegame.save");
					gui = null;
					gui = new GUI();
					Game.Resume();
					gameState = STATE_GAME;
				}
			}
		}
		while(Keyboard.next()){} //So key events dont get saved
	}
	
	public static void main(String[] argv) throws FileNotFoundException {
		Main LwjglTest = new Main();
		splashscreen = new splashScreen();
		LwjglTest.start();
		
		
	}
}