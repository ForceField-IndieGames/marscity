package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

/**
 * The resourcemanager provides static methods for accessing resources.
 * It also loads them when the game starts and maintains the list of objects.
 * @author Benedikt Ringlein
 */

public class ResourceManager {
	
	/**
	 * This font is used by buttons and labels in the gui
	 */
	public final static UnicodeFont Arial15 = new UnicodeFont(new Font("Arial",0,15));
	public final static UnicodeFont Arial15B = new UnicodeFont(new Font("Arial",Font.BOLD,15));
	public final static UnicodeFont Arial30B = new UnicodeFont(new Font("Arial",Font.BOLD,30));
	
	//The shaders, currently not used
	public static int shaderProgram, vertexShader, fragmentShader;
	
	//Some xml stuff that is only used internally
	static Document langFile = null;
	static Document settingsFile = null;
	static DocumentBuilder builder = null;
	
	//The path of the settings file
	static final String FILE_SETTINGS = "res/settings/settings.xml";
	
	//The building types (used when placing buildings, also saving and loading)
	public final static int BUILDINGTYPE_HOUSE = 0;
	public final static int BUILDINGTYPE_BIGHOUSE = 1;
	public final static int BUILDINGTYPE_STREET = 2;
	
	//The objects (Actually loads and pares a file and generates a displaylist)
	public final static int OBJECT_HOUSE = addObject("/res/house.obj");
	public final static int OBJECT_TERRAIN = addObject("/res/terrain.obj");
	public final static int OBJECT_SKYBOX = addObject("/res/skybox.obj");
	public final static int OBJECT_BIGHOUSE = addObject("/res/bighouse.obj");
	public final static int OBJECT_STREET = addObject("/res/streetsegment.obj");
	public final static int OBJECT_GRIDCELL = addObject("/res/gridcell.obj");
	
	//The audio files
	public final static Audio SOUND_DROP = addSound("WAV", "/res/drop.wav");
	public final static Audio SOUND_DESTROY = addSound("WAV", "/res/destroy.wav");
	
	//Loads the textures
	public final static Texture TEXTURE_ICON16 = addTexture("/res/icon16.png");
	public final static Texture TEXTURE_ICON32 = addTexture("/res/icon32.png");
	public final static Texture TEXTURE_ICON256 = addTexture("/res/icon256.png");
	public final static Texture TEXTURE_HOUSE = addTexture("/res/housetexture.png");
	public final static Texture TEXTURE_TERRAIN = addTexture("/res/mars.png");
	public final static Texture TEXTURE_STREET = addTexture("/res/street.png");
	public final static Texture TEXTURE_SKYBOX = addTexture("/res/skybox.png");
	public final static Texture TEXTURE_GUITOOLSBG = addTexture("/res/guitoolsBG.png");
	public final static Texture TEXTURE_GUISELECT = addTexture("/res/guiselect.png");
	public final static Texture TEXTURE_GUIMENUBUTTON = addTexture("/res/guimenubutton.png");
	public final static Texture TEXTURE_GUIADD = addTexture("/res/guiadd.png");
	public final static Texture TEXTURE_GUIDELETE = addTexture("/res/guidelete.png");
	public final static Texture TEXTURE_GUITOOLBAR = addTexture("/res/guitoolbar.png");
	public final static Texture TEXTURE_GUIMENU = addTexture("/res/guimenu.png");
	public final static Texture TEXTURE_EMPTY = addTexture("/res/empty.png");
	public final static Texture TEXTURE_GUIBUTTON = addTexture("/res/guibutton.png");
	public final static Texture TEXTURE_GUIBUTTONDOWN = addTexture("/res/guibuttondown.png");
	public final static Texture TEXTURE_MAINMENUBG = addTexture("/res/mainmenubg.png");
	public final static Texture TEXTURE_GUIBUTTON2 = addTexture("/res/guibutton2.png");
	public final static Texture TEXTURE_GUIBUTTON2DOWN = addTexture("/res/guibutton2down.png");
	public final static Texture TEXTURE_GUILABELBG = addTexture("/res/guilabelbg.png");
	public final static Texture TEXTURE_GUILABELBGL = addTexture("/res/guilabelbgl.png");
	public final static Texture TEXTURE_GUILABELBGR = addTexture("/res/guilabelbgr.png");
	public final static Texture TEXTURE_MAINMENUFF = addTexture("/res/ForceField.png");
	public final static Texture TEXTURE_MARSCITYLOGO = addTexture("/res/marscitylogo.png");
	public final static Texture TEXTURE_BIGHOUSE = addTexture("/res/bighousetexture.png");
	public final static Texture TEXTURE_PARTICLEFOG = addTexture("/res/fogparticle.png");
	public final static Texture TEXTURE_GUIDELETEBORDER = addTexture("/res/guideleteborder.png");
	
	public final static List<BuildingType> buildingTypes = new ArrayList<BuildingType>();
	
	public static List<Building> objects = new ArrayList<Building>();
	
	/**
	 * Initializes the Resources that need to be initialized
	 */
	@SuppressWarnings("unchecked")
	public static void init()
	{
		//Set up the font
		Arial15.getEffects().add(new ColorEffect(Color.black));
		Arial15B.getEffects().add(new ColorEffect(Color.black));
		Arial30B.getEffects().add(new ColorEffect(Color.black));
		Arial15.addAsciiGlyphs();
		Arial15B.addAsciiGlyphs();
		Arial30B.addAsciiGlyphs();
		try {
			Arial15.loadGlyphs();
			Arial15B.loadGlyphs();
			Arial30B.loadGlyphs();
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
		buildingTypes.add(BUILDINGTYPE_BIGHOUSE,new BuildingType("BUILDINGTYPE_BIGHOUSE",OBJECT_BIGHOUSE,TEXTURE_BIGHOUSE,1500,4,4,4f));
		buildingTypes.add(BUILDINGTYPE_STREET,new BuildingType("BUILDINGTYPE_STREET",OBJECT_STREET,TEXTURE_STREET,5,1,1,0f));
		
		//create necessary folders and extract files
		if(!(new File("res")).exists()){
			(new File("res")).mkdir();
			(new File("res/lang")).mkdir();
			(new File("res/settings")).mkdir();
			(new File("res/saves")).mkdir();
			Main.log("Created necessary folders.");
			try {
				(new File("res/lang/DE.xml")).createNewFile();
				(new File(FILE_SETTINGS)).createNewFile();
				
				//settings.xml
				BufferedReader input = new BufferedReader(new InputStreamReader(ResourceManager.class.getResourceAsStream("/res/settings/settings.xml")));
				BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("res/settings/settings.xml"))));
			
				String line;
				while((line=input.readLine())!=null){
					output.write(line+System.lineSeparator());
				}
				input.close();
				output.close();
				
				//DE.xml
				input = new BufferedReader(new InputStreamReader(ResourceManager.class.getResourceAsStream("/res/lang/DE.xml")));
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("res/lang/DE.xml"))));
			
				while((line=input.readLine())!=null){
					output.write(line+System.lineSeparator());
				}
				input.close();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		
		
		//make XML files available for the static methods
		settingsFile = addXML("res/settings/settings.xml");
		langFile = addXML("res/lang/"+getSetting("lang")+".xml");
	}

	/**
	 * Loads a texture from the specified input stream
	 * @param stream The imput stream
	 * @return The loaded texture
	 */
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
	
	/**
	 * Deletes an object from the render list
	 * @param obj The object to delete
	 */
	public static void deleteObject(Drawable obj)
	{
		objects.remove(obj);
	}
	
	/**
	 * Deletes an animatable from the render list ()for compatibility
	 * @param obj
	 */
	public static void deleteObject(Animatable obj)
	{
		objects.remove(obj);
	}
	
	/**
	 * Deletes an object from the render list
	 * @param obj The objects index
	 */
	public static void deleteObject(int obj)
	{
		objects.remove(obj);
	}
	
	/**
	 * Adds an object to the render list
	 * @param path Path to the .obj file
	 * @return An integer representing the displaylist
	 */
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
	
	/**
	 * Adds a sound file
	 * @param format The files format, e.g. "WAV"
	 * @param path The path of the sound file
	 * @return The loaded Audio
	 */
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
	
	/**
	 * Loads a texture
	 * @param path The path of the texture file (has to be .png!)
	 * @return The loaded texture
	 */
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
	 * @param setting The setting that should be loaded
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
	 * @param setting The setting that should be written
	 * @param value The new value of the setting
	 */
	public static void setSetting(String setting, String value)
	{
		try {
			settingsFile.getElementsByTagName(setting).item(0).setTextContent(value);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't write setting "+setting);
		}
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			 DOMSource        source = new DOMSource(settingsFile);
	         FileOutputStream os     = new FileOutputStream(new File(FILE_SETTINGS));
	         StreamResult     result = new StreamResult(os);
	         transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * Loads an XML file
	 * @param path Path of the file
	 * @return A Document representing the file
	 */
	public static Document addXML(String path)
	{
		if(path==null){
			return builder.newDocument();
		}
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
	 * @param sound The sound that should be played
	 */
	public static void playSound(Audio sound)
	{
		try {
			sound.playAsSoundEffect((float)(Math.random()+0.5), 1, false);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Return the object at the given index in the render list
	 * @param index
	 * @return
	 */
	public static Building getObject(int index)
	{
		return objects.get(index);
	}
	
	/**
	 * Sets up a shader
	 * @param vert The vertex shader (path)
	 * @param frag The fragment shader (path)
	 */
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
	 * Gets the localized name of the building type
	 * @param buildingtype
	 * @return
	 */
	public static String getBuildingTypeName(int buildingtype)
	{
		return ResourceManager.getString(ResourceManager.getBuildingType(buildingtype).getName());
	}
	
	/**
	 * Build a building (Adds it to the grid and to the object list)
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @param bt Building type
	 */
	public static Building buildBuilding(float x, float y, float z, int bt)
	{
		x = Grid.cellSize*Math.round(x/Grid.cellSize);
		y = Grid.cellSize*Math.round(y/Grid.cellSize);
		z = Grid.cellSize*Math.round(z/Grid.cellSize);
		Building building = new Building(bt,x,y,z);
		Grid.setBuilding(Math.round(x),Math.round(z), building);
		ResourceManager.objects.add(building);
		return building;
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
