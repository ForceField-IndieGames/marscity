package game;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import gui.GUI;
import gui.guiElement;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import objects.Drawable;
import objects.Entity;
import objects.House;
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
		label = new JLabel("Daten werden geladen, bitte warten");
		label.setForeground(Color.white);
		add(label);
		label.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-100, Toolkit.getDefaultToolkit().getScreenSize().height/2, 500, 50);
		label2 = new JLabel("lwjgl Test");
		label2.setForeground(Color.white);
		add(label2);
		label2.setFont(new Font("Arial", Font.BOLD, 30));
		label2.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-70, Toolkit.getDefaultToolkit().getScreenSize().height/2-30, 500, 50);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while(true)
		{
			if(label.getText()=="Daten werden geladen, bitte warten"){
				label.setText("Daten werden geladen, bitte warten.");
			}else if(label.getText()=="Daten werden geladen, bitte warten."){
				label.setText("Daten werden geladen, bitte warten..");
			}else if(label.getText()=="Daten werden geladen, bitte warten.."){
				label.setText("Daten werden geladen, bitte warten...");
			}else if(label.getText()=="Daten werden geladen, bitte warten..."){
				label.setText("Daten werden geladen, bitte warten");
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
	

//	
	long lastFrame;
	int fpsnow, fps;
	long lastTime;
	
	int soundbuffer;
	int soundsource;
	int hoveredEntity = -1;
	int selectedTool = 0;
	Audio sound;
	float[] mousepos3d=new float[3];	
	
	Camera camera = new Camera();
	Terrain terrain;
	GUI gui;
	BuildPreview buildpreview;
	static splashScreen splashscreen;
	
	public static void log(String text)
	{
		try {
			FileWriter log = new FileWriter("lwjgl.log");
			log.append(text);
			log.close();
			
		} catch (IOException e1) {e1.printStackTrace();}
	}

	public void start() throws FileNotFoundException {
		
		
		try {
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setVSyncEnabled(true);
			Display.setTitle("lwjgl Test");
			Display.create();
			Display.setLocation(0, 0);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Display konnte nicht erstellt werden");
			System.exit(0);
		}

		log("Yay! Das Programm startet... ");
		
		ResourceManager.init();
		
		log("Die Resourcen wurden geladen");
		
		gui = new GUI(); //Create the GUI
		
		buildpreview = new BuildPreview(ResourceManager.OBJECT_HOUSE, ResourceManager.TEXTURE_HOUSE);
		
		//Create some Objects
				terrain = new Terrain(0,0,-150);
				ResourceManager.objects.add(new Entity(ResourceManager.OBJECT_MONKEY, ResourceManager.TEXTURE_MONKEY,-50,10,-100));
				ResourceManager.objects.add(new Entity(ResourceManager.OBJECT_BUNNY,2,0,-100));
				ResourceManager.objects.add(new House(70,0,-150));
		
		
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
			
			System.out.println("Objects: "+ResourceManager.objects.size()+" FPS: "+fps);
			
			update(delta); //Gamelogic
			renderGL();    //Rendering
			
			
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
		if(guihit==gui.toolselect){
			selectedTool = TOOL_SELECT;
			gui.toolselect.setColor(Color.white);
			gui.tooladd.setColor(Color.gray);
			gui.tooldelete.setColor(Color.gray);
		}else if(guihit==gui.tooladd){
			selectedTool = TOOL_ADD;
			gui.toolselect.setColor(Color.gray);
			gui.tooladd.setColor(Color.white);
			gui.tooldelete.setColor(Color.gray);
		}if(guihit==gui.tooldelete){
			selectedTool = TOOL_DELETE;
			gui.toolselect.setColor(Color.gray);
			gui.tooladd.setColor(Color.gray);
			gui.tooldelete.setColor(Color.white);
		}else if(guihit==gui.pauseresume){
			gui.blur.setVisible(false);
			Game.Resume();
			AnimationManager.animateValue(gui.pausemenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
		}else if(guihit==gui.pauseexit){
			Game.exit();
		}
	}
	
	
	public void inputKeyboard(int delta)
	{
		//Movable object
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) ResourceManager.objects.get(1).setX(ResourceManager.objects.get(1).getX() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) ResourceManager.objects.get(1).setX(ResourceManager.objects.get(1).getX() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) ResourceManager.objects.get(1).setY(ResourceManager.objects.get(1).getY() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) ResourceManager.objects.get(1).setY(ResourceManager.objects.get(1).getY() - 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) ResourceManager.objects.get(1).setZ(ResourceManager.objects.get(1).getZ() + 0.05f * delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) ResourceManager.objects.get(1).setZ(ResourceManager.objects.get(1).getZ() - 0.05f * delta);
				
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
							AnimationManager.animateValue(gui.pausemenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pausemenu.setVisible(true);
							AnimationManager.animateValue(gui.pausemenu, AnimationValue.opacity, 1, 0.005f);
						}
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
							AnimationManager.animateValue(gui.pausemenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
						}else {
							Game.Pause();
							gui.blur.setVisible(true);
							gui.pausemenu.setVisible(true);
							AnimationManager.animateValue(gui.pausemenu, AnimationValue.opacity, 1, 0.005f);
						}
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_1&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_SELECT;
						gui.toolselect.setColor(Color.white);
						gui.tooladd.setColor(Color.gray);
						gui.tooldelete.setColor(Color.gray);
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_2&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_ADD;
						gui.toolselect.setColor(Color.gray);
						gui.tooladd.setColor(Color.white);
						gui.tooldelete.setColor(Color.gray);
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_3&&Keyboard.getEventKeyState()){
						selectedTool = TOOL_DELETE;
						gui.toolselect.setColor(Color.gray);
						gui.tooladd.setColor(Color.gray);
						gui.tooldelete.setColor(Color.white);
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
				camera.setX((float) (camera.getX()+0.0001f*delta*camera.getZoom()*MY*Math.sin(Math.toRadians(camera.getRotY()))-0.0001f*delta*camera.getZoom()*MX*Math.cos(Math.toRadians(camera.getRotY()))));
				camera.setZ((float) (camera.getZ()+0.0001f*delta*camera.getZoom()*MY*Math.cos(Math.toRadians(camera.getRotY()))+0.0001f*delta*camera.getZoom()*MX*Math.sin(Math.toRadians(camera.getRotY()))));
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
						
						case(TOOL_ADD): // Create a new House
							if(hoveredEntity!=-1)break;
						ResourceManager.playSound(ResourceManager.SOUND_DROP);
								ResourceManager.objects.add(new House((int)mousepos3d[0], (int)mousepos3d[1], (int)mousepos3d[2]));
							break;
							
						case(TOOL_DELETE): // Delete the Object
							if(hoveredEntity==-1)break;
							try {
								ResourceManager.playSound(ResourceManager.SOUND_DESTROY);
								ResourceManager.getObject(hoveredEntity).delete();
							} catch (Exception e) {}
							break;
					}
				}
				if((Mouse.getEventButton()==2||Mouse.getEventButton()==1)&&Mouse.getEventButtonState()){
						Mouse.setGrabbed(true);
				}
				camera.setZoom((float) (camera.getZoom()-0.05*Mouse.getEventDWheel()));
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
			buildpreview.setX(mousepos3d[0]);
			buildpreview.setY(mousepos3d[1]);
			buildpreview.setZ(mousepos3d[2]);
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
		
		//Show FPS
		gui.debugInfo.setText("Objects: "+ResourceManager.objects.size()+
				", FPS: "+fps);
		
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
        	if(i==hoveredEntity&&!Mouse.isGrabbed()){
        		if(selectedTool==TOOL_DELETE)glColor3f(1f, 0f, 0f);
        		if(selectedTool==TOOL_SELECT)glColor3f(1f, 1f, 1f);
        		if(selectedTool==TOOL_ADD)glColor3f(1f, 1f, 1f);
        		glDisable(GL_LIGHTING);
        	}else {
        		glColor3f(1f, 1f, 1f);
        	}
			ResourceManager.objects.get(i).draw();
			glEnable(GL_LIGHTING);
		}
         buildpreview.draw();

		glUseProgram(0);
		
		glPopMatrix();
		
		//GUI rendern
		gui.draw();
		
	}
	
	public static void main(String[] argv) throws FileNotFoundException {
		Main LwjglTest = new Main();
		splashscreen = new splashScreen();
		LwjglTest.start();
		
		
	}
}