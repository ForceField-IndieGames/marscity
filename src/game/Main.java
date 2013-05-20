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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import objects.BuildPreview;
import objects.Drawable;
import objects.Building;
import objects.Terrain;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import animation.AnimationManager;
import animation.AnimationValue;


/**
 * @author: Benedikt Ringlein
 * Just testing the Light Weight Java Game Library! :D
 **/

class splashScreen extends JFrame implements Runnable{

	
	private static final long serialVersionUID = 1L;
	
	JLabel label;
	JLabel label2;
	JLabel background;
	public Thread thread;
	
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
	
	final static int TOOL_SELECT = 0;
	final static int TOOL_ADD = 1;
	final static int TOOL_DELETE = 2;
	
	final static int STATE_INTRO = 0;
	final static int STATE_MENU = 1;
	final static int STATE_GAME = 2;

//	
	long lastFrame;
	int fpsnow, fps;
	long lastTime;
	
	boolean debugMode = false;
	
	int soundbuffer;
	int soundsource;
	int hoveredEntity = -1;
	int selectedTool = 0;
	static int money = 10000;
	int currentBuildingType = -1;
	Audio sound;
	float[] mousepos3d=new float[3];
	static int gameState = STATE_MENU;
	
	Camera camera = new Camera();
	Terrain terrain;
	public static GUI gui;
	BuildPreview buildpreview;
	static splashScreen splashscreen;
	
	public static void log(String text)
	{
		try {
			FileWriter log = new FileWriter("mars city.log",true);
			log.append(text+System.lineSeparator());
			log.close();
			
		} catch (IOException e1) {e1.printStackTrace();}
	}

	public void start() throws FileNotFoundException {
		
		
		try {
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setVSyncEnabled(true);
			Display.setTitle("Mars City");
			Display.create();
			Display.setLocation(0, 0);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Display konnte nicht erstellt werden");
			System.exit(0);
		}
		
		try {
			File file = new File("mars city.log");
			file.delete();
		} catch (Exception e) {
		}
		
		System.out.println("Mars City started...");
		log("Mars City started...");
		
		ResourceManager.init();//Initialize the resources
		
		log("Finished loading resources.");
		
		gui = new GUI(); //Create the GUI
		
		buildpreview = new BuildPreview(); //Create the Building Preview
		
		terrain = new Terrain(0,0,-150);//create the terrain
		
		//Set up the sound
		try {
			sound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		WaveData data = WaveData.create("res/sound.wav");
//		soundbuffer = alGenBuffers();
//		alBufferData(soundbuffer.get(0), data.format, data.data, data.samplerate);
//		data.dispose();
//		soundsource = alGenSources();
//		alSourcei(soundsource, AL_BUFFER, soundbuffer.get(0));
		

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastTime = getTime(); // call before loop to initialise fps timer
		
		splashscreen.setVisible(false);
		

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
			
			
			
			Display.update();
			Display.sync(60);
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
	
	public void inputGui(guiElement guihit)
	{
		if(guihit==gui.toolSelect){
			selectedTool = TOOL_SELECT;
			gui.toolSelect.setColor(Color.white);
			gui.toolAdd.setColor(Color.gray);
			gui.toolDelete.setColor(Color.gray);
			AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, -40, 0.5f);
			buildpreview.setBuilding(-1);
			currentBuildingType = -1;
		}
		if(guihit==gui.toolAdd){
			selectedTool = TOOL_ADD;
			gui.toolSelect.setColor(Color.gray);
			gui.toolAdd.setColor(Color.white);
			gui.toolDelete.setColor(Color.gray);
			AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, 0, 0.5f);
		}
 		if(guihit==gui.toolDelete){
			selectedTool = TOOL_DELETE;
			gui.toolSelect.setColor(Color.gray);
			gui.toolAdd.setColor(Color.gray);
			gui.toolDelete.setColor(Color.white);
			AnimationManager.animateValue(gui.toolBar, AnimationValue.Y, -40, 0.5f);
			buildpreview.setBuilding(-1);
			currentBuildingType = -1;
		}
 		if(guihit==gui.pauseResume){
			gui.blur.setVisible(false);
			Game.Resume();
			AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
		}
 		if(guihit==gui.pauseExit){
			Game.exit();
		}
 		if(guihit==gui.settingsResume){
			Game.Resume();
			AnimationManager.animateValue(gui.settingsMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
		}
 			if(guihit==gui.pauseSettings){
			gui.pauseMenu.setVisible(false);
			gui.settingsMenu.setVisible(true);
			gui.blur.setVisible(false);
			AnimationManager.animateValue(gui.settingsMenu, AnimationValue.opacity, 1, 0.005f);
		}
 		if(guihit==gui.pauseMainmenu){
			gameState = STATE_MENU;
		}
 		if(guihit==gui.buildingHouse){
 			currentBuildingType = ResourceManager.BUILDINGTYPE_HOUSE;
 			buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_HOUSE);
 			gui.buildingHouse.setColor(Color.gray);
 		}else gui.buildingHouse.setColor(Color.white);
 		if(guihit==gui.buildingBighouse){
 			currentBuildingType = ResourceManager.BUILDINGTYPE_BIGHOUSE;
 			buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_BIGHOUSE);
 			gui.buildingBighouse.setColor(Color.gray);
 		}else gui.buildingBighouse.setColor(Color.white);
 		if(guihit==gui.pauseSave){
 			Game.Save("res/saves/savegame.save");
 			Game.Resume();
 			gui.blur.setVisible(false);
			AnimationManager.animateValue(gui.pauseMenu, AnimationValue.opacity, 1, 0.005f, AnimationManager.ACTION_HIDE);
 		}
 		if(guihit==gui.pauseLoad){
 			Game.Load("res/saves/savegame.save");
			gui = null;
			gui = new GUI();
			Game.Resume();
 		}
	}
	
	
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
				
				//Camera movement with WASD
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
				
				while(Keyboard.next()){
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
					if(Keyboard.getEventKey()==Keyboard.KEY_RETURN && Keyboard.getEventKeyState()){
						if(sound.isPlaying()){
							sound.stop();
						}else{
							sound.playAsMusic(1, 1, false);
						}
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_T && Keyboard.getEventKeyState()){
						if(glIsEnabled(GL_TEXTURE_2D)){
							glDisable(GL_TEXTURE_2D);
						}else{
							glEnable(GL_TEXTURE_2D);
						}
					}
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
					if(Keyboard.getEventKey()==Keyboard.KEY_1&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_SELECT;
						gui.toolSelect.setColor(Color.white);
						gui.toolAdd.setColor(Color.gray);
						gui.toolDelete.setColor(Color.gray);
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_2&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_ADD;
						gui.toolSelect.setColor(Color.gray);
						gui.toolAdd.setColor(Color.white);
						gui.toolDelete.setColor(Color.gray);
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_3&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_DELETE;
						gui.toolSelect.setColor(Color.gray);
						gui.toolAdd.setColor(Color.gray);
						gui.toolDelete.setColor(Color.white);
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_TAB&&Keyboard.getEventKeyState()){
						if(debugMode){
							debugMode = false;
							gui.debugInfo.setVisible(false);
						}else{
							debugMode = true;
							gui.debugInfo.setVisible(true);
						}
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_F4&&Keyboard.getEventKeyState()&&debugMode){
						if(Display.isFullscreen())try {Display.setFullscreen(false);} catch (LWJGLException e) {e.printStackTrace();}
						else try {Display.setFullscreen(true);} catch (LWJGLException e) {e.printStackTrace();}
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_F1&&Keyboard.getEventKeyState()&&debugMode){
						money += 50000;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_O&&Keyboard.getEventKeyState()&&debugMode){
						ParticleEffects.dustEffect(mousepos3d[0], mousepos3d[1], mousepos3d[2]);
					}
				}
	}
	
	public void inputMouse(int delta)
	{
		//Is the mouse over a gui item?
		guiElement guihit = gui.mouseover();
		
		
		if(guihit==null || Mouse.isGrabbed())
		{
			
			int MX = Mouse.getDX();
			int MY = Mouse.getDY();
	
			
			if(Mouse.isButtonDown(2)){
				camera.setRotY(camera.getRotY()-0.1f*MX);
				camera.setRotX(camera.getRotX()-0.1f*MY);
			}
			if(Mouse.isButtonDown(1)){
				camera.setX((float) (camera.getX()+0.0002f*delta*camera.getZoom()*MY*Math.sin(Math.toRadians(camera.getRotY()))-0.0002f*delta*camera.getZoom()*MX*Math.cos(Math.toRadians(camera.getRotY()))));
				camera.setZ((float) (camera.getZ()+0.0002f*delta*camera.getZoom()*MY*Math.cos(Math.toRadians(camera.getRotY()))+0.0002f*delta*camera.getZoom()*MX*Math.sin(Math.toRadians(camera.getRotY()))));
			}
		}
		
		while(Mouse.next())
		{
			if((Mouse.getEventButton()==1||Mouse.getEventButton()==2)&&!Mouse.getEventButtonState())
			{
				Mouse.setGrabbed(false);
			}
			if(guihit==null)
			{
				if(Mouse.getEventButton()==0&&Mouse.getEventButtonState()){
					switch(selectedTool)
					{
						case(TOOL_SELECT): //Zoom to a house
							try {
								AnimationManager.animateValue(camera, AnimationValue.X, ResourceManager.getObject(hoveredEntity).getX(), 1f);
								AnimationManager.animateValue(camera, AnimationValue.Y, ResourceManager.getObject(hoveredEntity).getY()+ResourceManager.getObject(hoveredEntity).getPreferredY(), 1f);
								AnimationManager.animateValue(camera, AnimationValue.Z, ResourceManager.getObject(hoveredEntity).getZ(), 1f);
							} catch (Exception e) {}
							break;
						
						case(TOOL_ADD): // Create a new Building
							if(currentBuildingType==-1)break;
							if(!Grid.isAreaFree((int)Math.round(mousepos3d[0]), (int)Math.round(mousepos3d[2]), ResourceManager.getBuildingType(currentBuildingType).getWidth(), ResourceManager.getBuildingType(currentBuildingType).getDepth())||money<ResourceManager.getBuildingType(currentBuildingType).getBuidlingcost())break;
								ResourceManager.playSound(ResourceManager.SOUND_DROP);
								Building building = new Building(currentBuildingType,(int)Grid.cellSize*Math.round(mousepos3d[0]/Grid.cellSize), (int)Grid.cellSize*Math.round(mousepos3d[1]/Grid.cellSize), (int)Grid.cellSize*Math.round(mousepos3d[2]/Grid.cellSize));
								ResourceManager.objects.add(building);
								Grid.setBuilding((int)building.getX(), (int)building.getZ(), building);
								money -= ResourceManager.getBuildingType(currentBuildingType).getBuidlingcost();
								ParticleEffects.dustEffect(Grid.cellSize*Math.round(mousepos3d[0]/Grid.cellSize), Grid.cellSize*Math.round(mousepos3d[1]/Grid.cellSize), Grid.cellSize*Math.round(mousepos3d[2]/Grid.cellSize));
							break;
							
						case(TOOL_DELETE): // Delete the Object
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
				if((Mouse.getEventButton()==2||Mouse.getEventButton()==1)&&Mouse.getEventButtonState()){
						Mouse.setGrabbed(true);
				}
				camera.setZoom((float) (camera.getZoom()-0.001*camera.getZoom()*Mouse.getEventDWheel()));
				if(camera.getZoom()<5)camera.setZoom(5);
				if(camera.getZoom()>100)camera.setZoom(100);
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
		gui.debugInfo.setText("debug mode | Objects: "+ResourceManager.objects.size()+
				", FPS: "+fps+", Mouse:("+Math.round(mousepos3d[0])+","+Math.round(mousepos3d[1])+","+Math.round(mousepos3d[2])+")"+
				", GridIndex: "+Grid.posToIndex(Math.round(mousepos3d[0]), Math.round(mousepos3d[2])));

		gui.infoMoney.setText(ResourceManager.getString("INFOBAR_LABEL_MONEY")+": "+money+"$");
		gui.infoCitizens.setText(ResourceManager.getString("INFOBAR_LABEL_CITIZENS")+": "+0);
		
		ParticleEffects.update(delta);
		
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
		
		camera.applyTransform();
		
		glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(new float[]{-30f,50,100f,0f}));
        
		if(gui.mouseover()==null)
		{
			glDisable(GL_FOG);
			glClearColor(1f, 1f, 1f, 1f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			picking();//Picking
			picking3d();
			glClearColor(0f,0f, 0f, 1f);
			glEnable(GL_FOG);
		}else hoveredEntity = -1;
		
        //Rendering
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        
        terrain.draw();
       
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
        
        buildpreview.draw();
        
        ParticleEffects.draw();

//		glUseProgram(0);
		
		glPopMatrix();
		
		//GUI rendern
		gui.draw();
		
	}
	
	public void renderMenu()
	{
		gui.drawMenu();
	}
	
	float i=0;
	public void updateMenu(int delta)
	{
		gui.MenuBG.setX((float) (30*Math.sin(i)-30));
		gui.MenuBG.setWidth((float) (Display.getWidth()-60*Math.sin(i)+60));
		gui.MenuBG.setY((float) (30*Math.sin(i))-30);
		gui.MenuBG.setHeight((float) (Display.getHeight()-60*Math.sin(i)+60));
		i=(i<2*Math.PI)?i+0.005f:0;
		while(Mouse.next())
		{
			if(Mouse.getEventButton()==0&&Mouse.getEventButtonState()){
				guiElement guihit = gui.mouseoverMenu();
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