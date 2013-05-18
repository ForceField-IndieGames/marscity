package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import objects.Building;
import objects.BuildingType;
import objects.Drawable;
import objects.ObjectLoader;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.w3c.dom.*;

import animation.Animatable;


public class ResourceManager {
	
	public final static UnicodeFont font = new UnicodeFont(new Font("Arial",Font.BOLD,15));
	
	public static int shaderProgram, vertexShader, fragmentShader;
	
	static Document langFile = null;
	static Document settingsFile = null;
	static DocumentBuilder builder = null;
	
	public final static int BUILDINGTYPE_HOUSE = 0;
	public final static int BUILDINGTYPE_BIGHOUSE = 1;
	
	public final static int OBJECT_HOUSE = addObject("/res/house.obj");
	public final static int OBJECT_MONKEY = addObject("/res/monkey.obj");
	public final static int OBJECT_BUNNY = addObject("/res/bunny.obj");
	public final static int OBJECT_TERRAIN = addObject("/res/terrain.obj");
	public final static int OBJECT_BIGHOUSE = addObject("/res/bighouse.obj");
	
	public final static Audio SOUND_DROP = addSound("WAV", "/res/drop.wav");
	public final static Audio SOUND_DESTROY = addSound("WAV", "/res/destroy.wav");
	
	public final static Texture TEXTURE_HOUSE = addTexture("/res/housetexture.png");
	public final static Texture TEXTURE_MONKEY = addTexture("/res/monkeytexture.png");
	public final static Texture TEXTURE_TERRAIN = addTexture("/res/mars.png");
	public final static Texture TEXTURE_GUISELECT = addTexture("/res/guiselect.png");
	public final static Texture TEXTURE_GUIADD = addTexture("/res/guiadd.png");
	public final static Texture TEXTURE_GUIDELETE = addTexture("/res/guidelete.png");
	public final static Texture TEXTURE_GUITOOLBAR = addTexture("/res/guitoolbar.png");
	public final static Texture TEXTURE_GUIMENU = addTexture("/res/guimenu.png");
	public final static Texture TEXTURE_EMPTY = addTexture("/res/empty.png");
	public final static Texture TEXTURE_GUIBUTTON = addTexture("/res/guibutton.png");
	public final static Texture TEXTURE_MAINMENUBG = addTexture("/res/mainmenubg.png");
	public final static Texture TEXTURE_GUIBUTTON2 = addTexture("/res/guibutton2.png");
	public final static Texture TEXTURE_MAINMENUFF = addTexture("/res/ForceField.png");
	public final static Texture TEXTURE_MARSCITYLOGO = addTexture("/res/marscitylogo.png");
	public final static Texture TEXTURE_BIGHOUSE = addTexture("/res/bighousetexture.png");
	
	public final static List<BuildingType> buildingTypes = new ArrayList<BuildingType>();
	
	public static List<Building> objects = new ArrayList<Building>();
	
	/**
	 * Initializes the Resources that need to be initialized
	 */
	@SuppressWarnings("unchecked")
	public static void init()
	{
		//Set up the font
		font.getEffects().add(new ColorEffect(Color.black));
		font.addAsciiGlyphs();
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		//Set up the shader
		setupShader("/res/shader.v","/res/shader.f");
		
		//Load and parse the Language file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		//Building Types
		buildingTypes.add(BUILDINGTYPE_HOUSE,new BuildingType("BUILDINGTYPE_HOUSE",OBJECT_HOUSE,TEXTURE_HOUSE,500,2,2,0.25f));
		buildingTypes.add(BUILDINGTYPE_BIGHOUSE,new BuildingType("BUILDINGTYPE_BIGHOUSE",OBJECT_BIGHOUSE,TEXTURE_BIGHOUSE,1500,4,4,4));
		
		//XML files
		settingsFile = addXML("res/settings/settings.xml");
		langFile = addXML("res/lang/"+getSetting("lang")+".xml");
	}

	private static Texture LoadTexture(InputStream stream)
	{
		try {
			return TextureLoader.getTexture("PNG", new BufferedInputStream(stream));
		} catch (FileNotFoundException e) {
			System.err.println("Texture file not found.");
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		return null;
	}
	
	public static void deleteObject(Drawable obj)
	{
		objects.remove(obj);
	}
	
	public static void deleteObject(Animatable obj)
	{
		objects.remove(obj);
	}
	
	public static void deleteObject(int obj)
	{
		objects.remove(obj);
	}
	
	
	public static int addObject(String path)
	{
		try {
			Main.log("Loading object: "+path);
			Main.splashscreen.label2.setText("Loading object: "+path);
			return ObjectLoader.createDisplayList(ObjectLoader.loadModel(path));
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.label2.setText("Error! Failed to load object: "+path);
			Main.log("Failed to load object: "+path);
		}
		return -1;
	}
	
	public static Audio addSound(String format,String path)
	{
		Main.log("Loading sound: "+path);
		Main.splashscreen.label2.setText("Loading sound: "+path);
		try {
			return AudioLoader.getAudio(format, new BufferedInputStream(ResourceManager.class.getResourceAsStream(path)));
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.label2.setText("Error! Failed to load sound: "+path);
			Main.log("Failed to load sound: "+path);
		}
		return null;
	}
	
	public static Texture addTexture(String path)
	{
		Main.log("Loading texture: "+path);
		Main.splashscreen.label2.setText("Loading texture: "+path);
		try {
			return LoadTexture(ResourceManager.class.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.label2.setText("Error! Failed to load texture: "+path);
			Main.log("Failed to load texture: "+path);
		}
		return null;
	}
	
	public ResourceManager()
	{
			
	}
	
	/**
	 * Returns a the localized String from the language file
	 * @param input
	 * @return The localized strong or "String not found: #"
	 */
	public static String getString(String input)
	{
		String output =  null;
		if(langFile==null)return "Unable to load language file for "+getSetting("lang");
		try {
			output =  langFile.getElementsByTagName(input).item(0).getTextContent();
		} catch (Exception e) {
		}
		
		if(output==null){
			System.err.println("String not found: "+input);
			return "String not found: "+input;
		}
		return output;
	}
	
	/**
	 * Returns a the setting from the settings file
	 * @param input
	 * @return The value of the setting or "Setting not found: #"
	 */
	public static String getSetting(String setting)
	{
		String output =  null;
		if(settingsFile==null){
			System.err.println("Unable to load the settings file!");
			Game.exit();
		}
		try {
			output =  settingsFile.getElementsByTagName(setting).item(0).getTextContent();
		} catch (Exception e) {
		}
		
		if(output==null){
			System.err.println("Setting not found: "+setting);
			return "Setting not found: "+setting;
		}
		return output;
	}
	
	/**
	 * Writes a setting into the settings file
	 * @param setting
	 * @param value
	 */
	public static void setSetting(String setting, String value)
	{
		try {
			settingsFile.getElementsByTagName(setting).item(0).setTextContent(value);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't write setting "+setting);
		}
		System.out.println(getSetting("test"));
		 Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			 DOMSource        source = new DOMSource(settingsFile);
	         FileOutputStream os     = new FileOutputStream(new File(ResourceManager.class.getResource("/res/settings/settings.xml").toURI()));
	         StreamResult     result = new StreamResult(os);
	         transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * Loads an XML file
	 * @param path
	 * @return
	 */
	public static Document addXML(String path)
	{
		Main.log("Loading XML: "+path);
		try {
			Document file = builder.parse(new File(path));
			return file;
		} catch ( Exception e) {
			e.printStackTrace();
			Main.log("Error! Unable to load the XML file: "+path);
		}
		return null;
	}
	
	
	/**
	 * Plays a sound from the soundPool
	 * @param sound
	 */
	public static void playSound(Audio sound)
	{
		try {
			sound.playAsSoundEffect((float)(Math.random()+0.5), 1, false);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static Building getObject(int index)
	{
		return objects.get(index);
	}
	
	
	public static void setupShader(String vert, String frag)
	{
		shaderProgram = glCreateProgram();
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		StringBuilder vertexShaderSource = readFile(vert);
		StringBuilder fragmentShaderSource = readFile(frag);
		glShaderSource(vertexShader, vertexShaderSource);
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS)==GL_FALSE){
			System.err.println("Could not compile the vertex shader!");
			Display.destroy();
			System.exit(1);
		}
		glShaderSource(fragmentShader, fragmentShaderSource);
		glCompileShader(fragmentShader);
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS)==GL_FALSE){
			System.err.println("Could not compile the fragment shader!");
			Display.destroy();
			System.exit(1);
		}
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
	}
	
	/**
	 * Gets a BuildingType from its Index
	 * @param index The index defined in the resourcemanager
	 * @return The corresponding BuildingType
	 */
	public static BuildingType getBuildingType(int index)
	{
		return buildingTypes.get(index);
	}
	
	/**
	 * Loads a file into a StringBuilder
	 * @param path The Path to the File
	 * @return a StringBuilder conatining hte Filecontents
	 */
	private static StringBuilder readFile(String path)
	{
		Main.log("Reading file: "+path);
		StringBuilder string = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceManager.class.getResourceAsStream(path)));
			
			String line;
			while((line=reader.readLine())!=null)
			{
				string.append(line);
				string.append("\r\n");
			}
			reader.close();
			return string;
		} catch (Exception e) {
			System.err.println("File not found: "+path);
			Main.log("Error! Unable to read file: "+path);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
