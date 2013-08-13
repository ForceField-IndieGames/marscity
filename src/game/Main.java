package game;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import effects.ParticleEffects;
import gui.GUI;
import gui.GuiEventType;
import gui.GuiElement;

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
import java.util.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import objects.BuildPreview;
import objects.Building;
import objects.Buildings;
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
import animation.CustomAnimationValue;


/**
 * @author: Benedikt Ringlein
 * This is the main class with all the rendering and updating code.
 * Input is handled here as well.
 **/

class splashScreen extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	       short        loadeditems=0;
	       JLabel       label;
	       JLabel       label2;
	       JLabel       background;
	       JProgressBar progress;
	public Thread       thread;
	
	/**
	 * The splash screen that is displayed while the resources are still loading
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
		label.setBounds(5, getHeight()-50, 50, 50);
		label2 = new JLabel("");
		add(label2);
		label2.setForeground(Color.white);
		label2.setBounds(2, 0, 500, 20);
		progress = new JProgressBar();
		progress.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width , 3);
		progress.setBackground(Color.black);
		progress.setForeground(new Color(56,130,185));
		progress.setBorderPainted(false);
		add(progress);
		background = new JLabel(new ImageIcon(Main.class.getResource(ResourceManager.texturespath+"gui/forcefieldbackground.png")));
		add(background);
		background.setBounds(0, 0, getWidth(), getHeight());
		background.setDoubleBuffered(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void setInfo(String text)
	{
		int percent = Math.round(loadeditems/89f*100);
		if(percent>100)percent=100;
		label2.setText(percent+"% "+text);
		progress.setValue(percent);
		loadeditems++;
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
	
	//////////////////////////////////////////////////////////////////////////
	//The debugmode enables cheats and displays additional debug information//
	public static boolean debugMode = true;//////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	
	//The tools
	public final static byte TOOL_SELECT = 0;
	public final static byte TOOL_ADD = 1;
	public final static byte TOOL_DELETE = 2;
	
	//The game states
	public final static byte STATE_INTRO = 0;
	public final static byte STATE_MENU = 1;
	public final static byte STATE_GAME = 2;
	
	//other constants
	public final static int MONTH_MILLIS = debugMode?1000:60000;
	public final static int LOD1 = 50;
	public final static int LOD2 = 150;

	//Variables that are used for calculating the delta and fps
	long lastFrame,lastTime;
	int  fpsnow, fps;
	
	public static int      hoveredEntity = -1;                               //The index of the object that is hovered with the mouse
	public static byte     selectedTool  = TOOL_SELECT;                      //The selected tool, SELECT,ADD or DELETE
	public static int      money         = Game.INITIALMONEY;                //The players money
	public static int      citizens      = 0;                                //The citizens that live in the city
	public static byte     taxes         = Game.INITIALTAXES;                //Taxes for citizens in %
	public static String   cityname      = "Meine Stadt";                    //The city's name
	public static int      currentBT     = -1;                               //The currently selected building type
	public static float[]  mousepos3d    = new float[3];                     //The mouse position in 3d space
	public static byte     gameState     = STATE_INTRO;                      //The current game state
	public static String   clipboard     = "";                               //A local text clipboard
	public static Timer    MonthlyTimer  = new Timer();                      //A timer for sheduling monthly acitons
	public static DataView currentDataView = null;                           //The currently displayed DataView
	
	//Some more objects
	public static Camera       camera       = new Camera();
	       static Terrain      terrain; 
	       static Entity       skybox;
	public static GUI          gui;
	public static BuildPreview buildpreview;
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
	 */
	public void start(){
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
		
		ResourceManager.SOUND_AMBIENT.playAsMusic(1, 0, true);
		
		System.out.println("Loaded "+splashscreen.loadeditems+" resources");
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
		
		gui.IntroFF.setVisible(true);

		//Main Gameloop
		while (!Display.isCloseRequested()) {
			int delta = getDelta(); //Calculate the time between two Frames, for correct timing
			
			
			switch(gameState){
			case(STATE_INTRO): 
				updateIntro(delta);
				renderMenu();
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
	private int getDelta()
	{
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}
	
	/**
	 * Get the time in ms
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
	private static void picking()
	{
		glEnable(GL_SCISSOR_TEST);
		glScissor(Mouse.getX(), Mouse.getY(), 1, 1);
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		for(int i=0;i<Buildings.buildings.size();i++){
			glColor3ub((byte) ((i >> 0) & 0xff), (byte) ((i >> 8) & 0xff), (byte) ((i >> 16) & 0xff));
			ResourceManager.getObject(i).draw();
		}
		ByteBuffer color = BufferUtils.createByteBuffer(4);
		glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL_RGB, GL_UNSIGNED_BYTE, color);
        hoveredEntity=(color.getInt(0)>16000000)?-1:color.getInt(0);
        glDisable(GL_SCISSOR_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glColor3f(1f, 1f, 1f);
	}
	
	/**
	 * Calculates the position of the mousecursor in 3d space.
	 * Only points on the terrain can be mouse positions.
	 */
	private static void picking3d()
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
	 * Processes Keyboard inputs
	 * @param delta The calculated delta for timing
	 */
	public void inputKeyboard(int delta)
	{
		//Movable object
		try {
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) Buildings.buildings.get(1).setX(Buildings.buildings.get(1).getX() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) Buildings.buildings.get(1).setX(Buildings.buildings.get(1).getX() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) Buildings.buildings.get(1).setY(Buildings.buildings.get(1).getY() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) Buildings.buildings.get(1).setY(Buildings.buildings.get(1).getY() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) Buildings.buildings.get(1).setZ(Buildings.buildings.get(1).getZ() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) Buildings.buildings.get(1).setZ(Buildings.buildings.get(1).getZ() - 0.05f * delta);
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
							gui.pauseMenu.hide();
							gui.settingsMenu.hide();
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pauseMenu.setVisible(true);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.OPACITY, 1, 0.005f);
						}
						if(debugMode)Game.exit();
					}
					if(debugMode&&Keyboard.getEventKey()==Keyboard.KEY_T)
					{
						gui.showToolTip("Das ist ein Test Tooltip");
					}
					if(debugMode&&Keyboard.getEventKey()==Keyboard.KEY_M&&Keyboard.getEventKeyState()){
						gui.MsgBox("Text", "Sie haben auf die M Taste gedrückt und eine Messagebox aufgerufen. Der Text hier sollte automatisch gewrapt werden. Das funktioniert bei allen Labels, man braucht also nicht mehr über Zeilenumbrüche nachzudenken");
					}
					//Pause and resume game with p
					if(Keyboard.getEventKey()==Keyboard.KEY_P && Keyboard.getEventKeyState())
					{
						if(Game.isPaused())
						{
							Game.Resume();
							gui.blur.setVisible(false);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.OPACITY, 0, 0.005f, AnimationManager.ACTION_HIDE);
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pauseMenu.setVisible(true);
							AnimationManager.animateValue(gui.pauseMenu, AnimationValue.OPACITY, 1, 0.005f);
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
		GuiElement guihit = gui.getMouseover();
		
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
				if(camera.wasRotated()){
					gui.cameraRotate.setVisible(true);
				}
			}
			//Move the camera with middle mouse button
			if(Mouse.isButtonDown(2)){
				camera.setX((float) (camera.getX()+delta*(0.00008f*camera.getZoom()+0.0004f)
						*MY*Math.sin(Math.toRadians(camera.getRotY()))-delta*(0.00008f*camera.getZoom()+0.0004f)
						*MX*Math.cos(Math.toRadians(camera.getRotY()))));
				camera.setZ((float) (camera.getZ()+delta*(0.00008f*camera.getZoom()+0.0004f)
						*MY*Math.cos(Math.toRadians(camera.getRotY()))+delta*(0.00008f*camera.getZoom()+0.0004f)
						*MX*Math.sin(Math.toRadians(camera.getRotY()))));
			}
		}
		
		//Fire Mouseover and Mouseout events
		if(gui.lastHovered!=guihit)gui.callGuiEvents(GuiEventType.Mouseover);
		if(gui.lastHovered!=null&&gui.lastHovered!=guihit)gui.callGuiEvents(GuiEventType.Mouseout,gui.lastHovered);
		gui.lastHovered = guihit;
		
		//Process Mouse events
		while(Mouse.next())
		{
			//Don't grab mouse when right and middle button are relesed
			if((Mouse.getEventButton()==1||Mouse.getEventButton()==2)&&!Mouse.getEventButtonState())
			{
				Mouse.setGrabbed(false);
				gui.buildinginfo.hide();
			}
			//Set mouse grabbed when pressing middl or righte mouse button
			if((Mouse.getEventButton()==2||Mouse.getEventButton()==1)&&Mouse.getEventButtonState()){
					Mouse.setGrabbed(true);
			}
			//Show move icon when moving camera
			if(Mouse.getEventButton()==2&&Mouse.getEventButtonState()){
				gui.cameraMove.setVisible(true);
			}
			//hide move icon
			if(Mouse.getEventButton()==2&&!Mouse.getEventButtonState()){
				gui.cameraMove.setVisible(false);
			}
			//Hide rotation icon
			if(Mouse.getEventButton()==1){
				gui.cameraRotate.setVisible(false);
			}
			
			//Fire Mousemove and Drag events
			if(Mouse.getDX()!=0||Mouse.getDY()!=0)
			{
				if(Mouse.isButtonDown(0))
				{
					gui.callGuiEvents(GuiEventType.Drag);
				}else{
					gui.callGuiEvents(GuiEventType.Mousemove);
				}
			}
			
			//Fire gui click event & Building click event & hide the building info 'n stuff
			if(Mouse.getEventButton()==0&&!Mouse.getEventButtonState()){
				if(Mouse.getX()<gui.moneypanel.getScreenX()
						||Mouse.getX()>gui.moneypanel.getScreenX()+gui.moneypanel.getWidth()
						||Mouse.getY()<gui.moneypanel.getScreenY()
						||Mouse.getY()>gui.moneypanel.getScreenY()+gui.moneypanel.getHeight()){
					gui.moneypanel.hide();
				}
				if(Mouse.getX()<gui.citizenspanel.getScreenX()
						||Mouse.getX()>gui.citizenspanel.getScreenX()+gui.citizenspanel.getWidth()
						||Mouse.getY()<gui.citizenspanel.getScreenY()
						||Mouse.getY()>gui.citizenspanel.getScreenY()+gui.citizenspanel.getHeight()){
					gui.citizenspanel.hide();
				}
				gui.callGuiEvents(GuiEventType.Click);
				gui.buildinginfo.hide();
				if(hoveredEntity!=-1&&selectedTool==TOOL_SELECT){
					try {
						ResourceManager.getObject(hoveredEntity).click();
					} catch (Exception e) {
					}
				}
			}
			
			//Fire scroll events
			if(Mouse.getEventDWheel()>0)gui.callGuiEvents(GuiEventType.Scrollup);
			if(Mouse.getEventDWheel()<0)gui.callGuiEvents(GuiEventType.Scrolldown);
			
			//Only do things when the mouse is not over the gui
			if(guihit!=null)return;
				
			//Build a street to the mouse position
			if(Mouse.getEventButton()==0&&!Mouse.getEventButtonState()&&currentBT==Buildings.BUILDINGTYPE_STREET&&selectedTool==TOOL_ADD){
				Streets.buildStreet(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
				Buildings.refreshSupply();
			}
			
			//Delete a street
			if(Mouse.getEventButton()==0&&!Mouse.getEventButtonState()&&selectedTool==TOOL_DELETE&&ResourceManager.getHoveredBuildingtype(hoveredEntity)==Buildings.BUILDINGTYPE_STREET){
				Streets.deleteStreet(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
				Buildings.refreshSupply();
			}
			
			//Do some action with the left mouse button based on the selected tool
			if(Mouse.getEventButton()==0){
				switch(selectedTool)
				{
					case(TOOL_SELECT): //Zoom to a house
						if(Mouse.getEventButtonState())break;
						try {
							AnimationManager.animateValue(camera, AnimationValue.X, ResourceManager.getObject(hoveredEntity).getX(), 200);
							AnimationManager.animateValue(camera, AnimationValue.Z, ResourceManager.getObject(hoveredEntity).getZ(), 200);
						} catch (Exception e) {}
						break;
					
					case(TOOL_ADD): // Create a new building/street at mouse position
						if(!Mouse.getEventButtonState()||currentBT==-1)break;
						//Start street building
						if(currentBT==Buildings.BUILDINGTYPE_STREET){
							Streets.setStartPos(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
							break;
						}
						//Build a building
						if (!Grid.isAreaFree((int) Math.round(mousepos3d[0]),
								(int) Math.round(mousepos3d[2]), 
								Buildings.getBuildingType(currentBT).getWidth(),
								Buildings.getBuildingType(currentBT).getDepth())){
							gui.showToolTip(ResourceManager.getString("FEEDBACK_OVERLAPPING"));
							break;
						}
						if (money < Buildings.getBuildingType(currentBT).getBuidlingcost()){
							gui.showToolTip(ResourceManager.getString("FEEDBACK_NOTENOUGHMONEY"));
							break;
						}
						if (!Grid.buildingSurroundedWith((int) Math.round(mousepos3d[0]), (int) Math.round(mousepos3d[2]), currentBT, Buildings.BUILDINGTYPE_STREET)){
							gui.showToolTip(ResourceManager.getString("FEEDBACK_NOSTREET"));
							break;
						}
						ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
						Building b = Buildings.buildBuilding(mousepos3d[0], mousepos3d[1]+5, mousepos3d[2], currentBT);
						money -= Buildings.getBuildingType(currentBT).getBuidlingcost();
						ParticleEffects.dustEffect(b.getX(), 0, b.getZ());
						camera.setY(0);
						AnimationManager.animateValue(camera, AnimationValue.Y, camera.getY()+2, 0.05f, AnimationManager.ACTION_REVERSE);
						AnimationManager.animateValue(b, AnimationValue.Y, Math.round(mousepos3d[1]), 0.05f);
						buildpreview.waveEffect();
						Buildings.refreshSupply();
						break;
						
					case(TOOL_DELETE): // Delete the hovered Building
						if(!Mouse.getEventButtonState()||hoveredEntity==-1)break;
						if(ResourceManager.getObject(hoveredEntity).getBuildingType()==Buildings.BUILDINGTYPE_STREET){
							Streets.setStartPos(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]));
							break;
						}
						try {
							ResourceManager.playSoundRandom(ResourceManager.SOUND_DESTROY);
							Grid.clearsCells((int)ResourceManager.getObject(hoveredEntity).getX(), (int)ResourceManager.getObject(hoveredEntity).getZ(), Buildings.getBuildingType(ResourceManager.getObject(hoveredEntity).getBuildingType()).getWidth(), Buildings.getBuildingType(ResourceManager.getObject(hoveredEntity).getBuildingType()).getDepth());
							ParticleEffects.dustEffect(ResourceManager.getObject(hoveredEntity).getX(),ResourceManager.getObject(hoveredEntity).getY(),ResourceManager.getObject(hoveredEntity).getZ());
							ResourceManager.getObject(hoveredEntity).delete();
						} catch (Exception e) {e.printStackTrace();}
						break;
				}
			}
			//Select the select tool with right mouse button
			if(Mouse.getEventButton()==1&&!Mouse.getEventButtonState()&&!camera.wasRotated()){
				selectedTool = TOOL_SELECT;
				gui.toolDelete.setColor(Color.white);
				buildpreview.setBuilding(-1);
				currentBT = -1;
				gui.deleteBorder.setVisible(false);
				gui.buildingPanels.hide();
				gui.infoBuildingCosts.setVisible(false);
				gui.infoMonthlyCosts.setVisible(false);
			}
			//Set the last rotation of the camera, for checkign if the camera was moved
			if(Mouse.getEventButton()==1&&Mouse.getEventButtonState()){
				camera.setLastrotx();
				camera.setLastroty();
			}
			//Control the zoom with the mouse wheel
			if(Mouse.getEventDWheel()!=0){
				AnimationManager.animateValue(camera, new CustomAnimationValue(){
					@Override
					public double getValue() {
						return camera.getZoom();
					}
					@Override
					public void setValue(double input) {
						camera.setZoom((float) ((input)<7?7:((input>1000)?1000:input)));
					}
				}, camera.getZoom()-0.004*camera.getZoom()*Mouse.getEventDWheel()-((Mouse.getEventDWheel()<0)?0.1:0.01)*Mouse.getEventDWheel(), 200);
			}
		}
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
		glLightModel(GL_LIGHT_MODEL_AMBIENT, ResourceManager.toFlippedFloatBuffer(new float[] {0.5f,0.5f,0.5f,1f}));
		glLight(GL_LIGHT0, GL_AMBIENT, ResourceManager.toFlippedFloatBuffer(new float[]{0.2f,0.2f,0.2f,0.2f}));
		
		glEnable(GL_MAP_COLOR);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_FOG);
		glFog(GL_FOG_COLOR, ResourceManager.toFlippedFloatBuffer(new float[]{0f,0f,0f,1f}));
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glHint(GL_FOG_HINT, GL_NICEST);
		glFogf(GL_FOG_START, 4000);
		glFogf(GL_FOG_END, 5000);
		glFogf(GL_FOG_DENSITY, 0.0005f);
	}

	/**
	 * Draw the Main menu
	 */
	public void renderMenu()
	{
		gui.drawMenu();
	}

	/**
	 * Do the actual rendering
	 */
	public static void renderGL() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glPushMatrix();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30f, 1337f / 768f, 0.3f, 5000f);
		glMatrixMode(GL_MODELVIEW);
//		
		
		//Apply the camera transformations
		camera.applyTransform();
		
		//Set the light positions
		glLight(GL_LIGHT0, GL_POSITION, ResourceManager.toFlippedFloatBuffer(new float[]{-30f,50,100f,0f}));
		glLight(GL_LIGHT0, GL_DIFFUSE, ResourceManager.toFlippedFloatBuffer(new float[]{2f,2f,2f,1f}));
        
		if(gui.getMouseover()==null)
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
        
        if(currentDataView!=null){
        	glDisable(GL_TEXTURE_2D);
        }else{
        	glEnable(GL_TEXTURE_2D);
        }
       
        //Draw the skybox
        glDisable(GL_LIGHTING);
        if(currentDataView!=null)glColor3f(0.5f, 0.5f, 0.5f);
        else glColor3f(1f, 1f, 1f);
        skybox.draw();
        glEnable(GL_LIGHTING);
        
        //Draw the terrain
        if(currentDataView!=null)glColor3f(0.5f, 0.5f, 0.5f);
        terrain.draw();
        
       // glUseProgram(ResourceManager.shaderProgram);
       
        //Draw the buidings
        for(int i=0;i<Buildings.buildings.size();i++){
        	if(i==hoveredEntity&&!Mouse.isGrabbed()&&selectedTool!=TOOL_ADD){
        		if(selectedTool==TOOL_DELETE)glColor3f(1f, 0f, 0f);
        		if(selectedTool==TOOL_SELECT)glColor3f(0.5f, 0.5f, 0.5f);
        		glEnable(GL_LIGHTING);
        		glEnable(GL_COLOR_MATERIAL);
        	}else {
        		if(currentDataView==null)
        		{
        			glColor3f(1f, 1f, 1f);
        			glLight(GL_LIGHT0, GL_DIFFUSE, ResourceManager.toFlippedFloatBuffer(new float[]{2f,2f,2f,1f}));
        		}else{
        			Color col = currentDataView.buildingColor(Buildings.buildings.get(i));
        			glColor3f((float)col.getRed()/255f, (float)col.getGreen()/255f, (float)col.getBlue()/255f);
        			glEnable(GL_COLOR_MATERIAL);
        			glDisable(GL_TEXTURE_2D);
        			glLight(GL_LIGHT0, GL_DIFFUSE, ResourceManager.toFlippedFloatBuffer(new float[]{0.5f,0.5f,0.5f,1f}));
        		}
        	}
			Buildings.buildings.get(i).draw();
			glEnable(GL_LIGHTING);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_COLOR_MATERIAL);
		}
        
        //glUseProgram(0);
        
        //Draw the building preview
        buildpreview.draw();
        
        //Draw the particle effects
        ParticleEffects.draw();
		
		glPopMatrix();
		
		//GUI rendern
		gui.draw();
		
	}
	
	float i= (float) (0.5*Math.PI); //value for the menu animation
	public void updateIntro(int delta)
	{
		gui.IntroFF.setOpacity(1f-(float)anim/1000);
		gui.IntroFF.setX(gui.IntroFF.getX()-0.2f*delta);
		gui.IntroFF.setY(gui.IntroFF.getY()-0.1f*delta);
		gui.IntroFF.setWidth(gui.IntroFF.getWidth()+0.4f*delta);
		gui.IntroFF.setHeight(gui.IntroFF.getHeight()+0.2f*delta);
		if(anim>=1000){
			gameState = STATE_MENU;
			gui.IntroFF.setVisible(false);
		}
		anim+=delta;
	}

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
		i=(i<2*Math.PI)?i+0.005f:0;
		//Process mouse inputs
		GuiElement guihit = gui.mouseoverMenu();
		if(gui.lastHovered!=guihit)gui.callGuiEventsMenu(GuiEventType.Mouseover);
		if(gui.lastHovered!=null&&gui.lastHovered!=guihit)gui.callGuiEvents(GuiEventType.Mouseout,gui.lastHovered);
		gui.lastHovered = guihit;
		while(Mouse.next())
		{
			if(Mouse.getEventButton()==0&&!Mouse.getEventButtonState()){
				gui.callGuiEventsMenu(GuiEventType.Click);
			}
		}
		while(Keyboard.next()){} //So key events dont get saved
	}
	
	int anim = 0;
	/**
	 * The Gamelogic
	 * @param delta The delta for timing
	 */
	public void update(int delta) {
		
		//Mouse input
		inputMouse(delta);
		
		//Keyboard input
		if(gui.getKeyboardfocus()==null)inputKeyboard(delta);
		else{
			while(Keyboard.next()){
				gui.getKeyboardfocus().callGuiEvents(GuiEventType.Keypress);
			}
		}
		
		//Run the animations
		AnimationManager.update(delta);
		
		//Move the BuildPreview
		if(selectedTool==TOOL_ADD&&gui.getMouseover()==null&&!Mouse.isGrabbed()){
			buildpreview.setX(Grid.cellSize*Math.round(mousepos3d[0]/Grid.cellSize));
			buildpreview.setY(Grid.cellSize*Math.round(mousepos3d[1]/Grid.cellSize));
			buildpreview.setZ(Grid.cellSize*Math.round(mousepos3d[2]/Grid.cellSize));
			buildpreview.setVisible(true);
		}else{
			buildpreview.setVisible(false);
		}
		
		for(int i=0;i<Buildings.buildings.size();i++)
		{
			Buildings.buildings.get(i).update(delta);
		}
		
		//Update tooltip position
		if(gui.tooltip.isVisible())
		{
			gui.tooltip.setX((Mouse.getX()+gui.tooltip.getWidth()+gui.tooltip.getHeight()/2<=Display.getWidth())?Mouse.getX():Display.getWidth()-gui.tooltip.getWidth()-gui.tooltip.getHeight()/2);
			gui.tooltip.setY((Mouse.getY()+gui.tooltip.getHeight()<=Display.getHeight())?Mouse.getY():Display.getHeight()-gui.tooltip.getHeight());
		}
		
		//Show debug info
		String bt = "-";
		try {
			if(Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding()!=null){
			bt = ""+Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getBuildingType()+" ("+Buildings.getBuildingTypeName(Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getBuildingType())+")";
		}
		} catch (Exception e) {
		}
		String energy = "-";
		try {
			energy = (int)((Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getOwnedSupplyAmount(Supply.Energy)/(float)Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getNeededSupplyAmount(Supply.Energy))*100)+"% ("+Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getOwnedSupplyAmount(Supply.Energy)+")";
		} catch (Exception e) {}
		String happiness = "-";
		try {
			happiness = ""+(int)((Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getBuilding().getHappiness()));
		} catch (Exception e) {}
		String happinessEffect = "-";
		try {
			happinessEffect = ""+(int)((Grid.getCell(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])).getHappinessEffect()));
		} catch (Exception e) {}
		gui.debugInfo.setText("debug mode | Objects: "+Buildings.buildings.size()+
				", FPS: "+fps+", ParticleEffects: "+ParticleEffects.getEffectCount()+", Mouse:("+Math.round(mousepos3d[0])+","+Math.round(mousepos3d[1])+","+Math.round(mousepos3d[2])+")"+
				", GridIndex: "+Grid.posToIndex(Math.round(mousepos3d[0]), Math.round(mousepos3d[2]))+
				", BuildingType: "+bt+", Energy supply: "+energy+", Happiness: "+happiness+", HappinessEffect: "+happinessEffect);
	
			
		//Update gui info labels
		gui.infoMoney.setText(ResourceManager.getString("INFOBAR_LABEL_MONEY")+": "+money+"$");
		gui.infoCitizens.setText(ResourceManager.getString("INFOBAR_LABEL_CITIZENS")+": "+citizens);
		if(selectedTool==TOOL_ADD&&money<Buildings.getBuildingType(currentBT).getBuidlingcost()){
			gui.infoMoney.setTextColor(Color.red);
		}else {
			gui.infoMoney.setTextColor(Color.black);
		}
		
		//Update the building info
		gui.buildinginfo.update();
		
		//Update the paticle effects
		ParticleEffects.update(delta);
		
		//Rotate the camera while in pause mode
		if(Game.isPaused())camera.setRotY(camera.getRotY()+0.05f);
		
		// update FPS Counter
		updateFPS(); 
	}

	public static void main(String[] argv) throws FileNotFoundException {
		Main MarsCity = new Main();
		splashscreen = new splashScreen();
		MarsCity.start();
	}
}