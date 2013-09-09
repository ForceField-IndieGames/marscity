package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import objects.Building;
import objects.Buildings;
import objects.Drawable;
import objects.ObjectLoader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.w3c.dom.*;

import objects.Entity;

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
	public final static UnicodeFont Arial12 = addFont(new Font("Arial",0,12));
	public final static UnicodeFont Arial15 = addFont(new Font("Arial",0,15));
	public final static UnicodeFont Arial15B = addFont(new Font("Arial",Font.BOLD,15));
	public final static UnicodeFont Arial30B = addFont(new Font("Arial",Font.BOLD,30));
	
	public final static String PLACEHOLDER1 = "%1%";
	public final static String PLACEHOLDER2 = "%2%";
	
	//The shaders, currently not used
	public static int shaderProgram, vertexShader, fragmentShader;
	
	//Some xml stuff that is only used internally
	static Document settingsFile = null;
	static DocumentBuilder builder = null;
	
	//A hashmap that contains the localized strings
	public static HashMap<String,String> strings = new HashMap<String,String>();
	
	//The path of the settings file
	static final String FILE_SETTINGS = "res/settings/settings.xml";
	
	//Resource paths
	public static final String objectspath = "/res/objects/";
	public static final String soundspath = "/res/sounds/";
	public static final String texturespath = "/res/textures/";
	public static final String shaderpath = "/res/shader/";
	
	//The objects (Actually loads and pares a file and generates displaylists for each LOD model)
	public final static int[] OBJECT_HOUSE                 = addObject("house");
	public final static int[] OBJECT_TERRAIN               = addObject("terrain");
	public final static int[] OBJECT_SKYBOX                = addObject("skybox");
	public final static int[] OBJECT_BIGHOUSE              = addObject("bighouse");
	public final static int[] OBJECT_STREET                = addObject("streetsegment");
	public final static int[] OBJECT_GRIDCELL              = addObject("gridcell");
	public final static int[] OBJECT_PLACEHOLDER           = addObject("placeholder");
	public final static int[] OBJECT_CITYCENTER            = addObject("citycenter");
	public final static int[] OBJECT_RESEARCHSTATION       = addObject("researchstation");
	public final static int[] OBJECT_HANGAR                = addObject("hangar");
	public final static int[] OBJECT_BANK                  = addObject("bank");
	public final static int[] OBJECT_MEDICALCENTER         = addObject("medicalcenter");
	public final static int[] OBJECT_SERVERCENTER          = addObject("servercenter");
	public final static int[] OBJECT_GARBAGEYARD           = addObject("garbageyard");
	public final static int[] OBJECT_POLICE                = addObject("police");
	public final static int[] OBJECT_SOLARPOWER            = addObject("solarpower");
	public final static int[] OBJECT_FUSIONPOWER           = addObject("fusionpower");
	public final static int[] OBJECT_SHIELD                = addObject("shield");
	public final static int[] OBJECT_MEDICALCENTERROOMS    = addObject("medicalcenterrooms");
	public final static int[] OBJECT_MEDICALCENTERVEHICLES = addObject("medicalcentervehicles");
	public final static int[] OBJECT_SERVERCENTERSERVERS   = addObject("servercenterservers");
	public final static int[] OBJECT_SOLARPOWERPANELS      = addObject("solarpowerpanels");
	public final static int[] OBJECT_GARBAGEYARDBURNER     = addObject("garbageyardburner");
	public final static int[] OBJECT_GARBAGEYARDVEHICLES   = addObject("garbageyardvehicles");
	
	//The audio files
	public final static Audio SOUND_DROP    = addSound("WAV", "drop.wav");
	public final static Audio SOUND_DESTROY = addSound("WAV", "destroy.wav");
	public final static Audio SOUND_SELECT  = addSound("WAV", "select.wav");
	public final static Audio SOUND_AMBIENT = addSound("WAV", "ambient.wav");
	public final static Audio SOUND_UPGRADE = addSound("WAV", "upgrade.wav");
	
	//Loads the textures
	public final static EntityTexture TEXTURE_SKYBOX            = addEntityTexture("skybox");
	public final static EntityTexture TEXTURE_TERRAIN           = addEntityTexture("terrain");
	public final static EntityTexture TEXTURE_STREETDEFAULT     = addEntityTexture("streetdefault");
	public final static EntityTexture TEXTURE_STREETSTRAIGHT    = addEntityTexture("streetstraight");
	public final static EntityTexture TEXTURE_STREETTCROSSING   = addEntityTexture("streettcrossing");
	public final static EntityTexture TEXTURE_STREETCROSSING    = addEntityTexture("streetcrossing");
	public final static EntityTexture TEXTURE_STREETCURVE       = addEntityTexture("streetcurve");
	public final static EntityTexture TEXTURE_STREETEND         = addEntityTexture("streetend");
	public final static EntityTexture TEXTURE_HOUSE             = addEntityTexture("house");
	public final static EntityTexture TEXTURE_BIGHOUSE          = addEntityTexture("bighouse");
	public final static EntityTexture TEXTURE_CITYCENTER        = addEntityTexture("citycenter");
	public final static EntityTexture TEXTURE_PLACEHOLDER       = addEntityTexture("placeholder");
	public final static EntityTexture TEXTURE_RESEARCHSTATION   = addEntityTexture("researchstation");
	public final static EntityTexture TEXTURE_HANGAR            = addEntityTexture("hangar");
	public final static EntityTexture TEXTURE_BANK              = addEntityTexture("bank");
	public final static EntityTexture TEXTURE_MEDICALCENTER     = addEntityTexture("medicalcenter");
	public final static EntityTexture TEXTURE_SERVERCENTER      = addEntityTexture("servercenter");
	public final static EntityTexture TEXTURE_GARBAGEYARD       = addEntityTexture("garbageyard");
	public final static EntityTexture TEXTURE_POLICE            = addEntityTexture("police");
	public final static EntityTexture TEXTURE_SOLARPOWER        = addEntityTexture("solarpower");
	public final static EntityTexture TEXTURE_FUSIONPOWER       = addEntityTexture("fusionpower");
	public final static EntityTexture TEXTURE_SHIELD            = addEntityTexture("shield");
	public final static EntityTexture TEXTURE_MEDICALCENTERROOMS= addEntityTexture("medicalcenterrooms");
	public final static EntityTexture TEXTURE_UPGRADEVEHICLES   = addEntityTexture("upgradevehicles");
	
	public final static Texture TEXTURE_GUITHUMBSTREET               = addTexture("gui/thumbstreet.png");
	public final static Texture TEXTURE_GUITHUMBHOUSE                = addTexture("gui/thumbhouse.png");
	public final static Texture TEXTURE_GUITHUMBBIGHOUSE             = addTexture("gui/thumbbighouse.png");
	public final static Texture TEXTURE_GUITHUMBMEDICALCENTER        = addTexture("gui/thumbmedicalcenter.png");
	public final static Texture TEXTURE_GUITHUMBSERVERCENTER         = addTexture("gui/thumbservercenter.png");
	public final static Texture TEXTURE_GUITHUMBGARBAGEYARD          = addTexture("gui/thumbgarbageyard.png");
	public final static Texture TEXTURE_GUITHUMBBANK                 = addTexture("gui/thumbbank.png");
	public final static Texture TEXTURE_GUITHUMBRESEARCHSTATION      = addTexture("gui/thumbresearchstation.png");
	public final static Texture TEXTURE_GUITHUMBSOLARPOWER           = addTexture("gui/thumbsolarpower.png");
	public final static Texture TEXTURE_GUITHUMBFUSIONPOWER          = addTexture("gui/thumbfusionpower.png");
	public final static Texture TEXTURE_GUITHUMBHANGAR               = addTexture("gui/thumbhangar.png");
	public final static Texture TEXTURE_GUITHUMBMEDICALCENTERROOMS   = addTexture("gui/thumbmedicalcenterrooms.png");
	public final static Texture TEXTURE_GUITHUMBUPGRADEVEHICLES      = addTexture("gui/thumbupgradevehicles.png");
	public final static Texture TEXTURE_GUITHUMBSERVERCENTERSERVERS  = addTexture("gui/thumbservercenterservers.png");
	public final static Texture TEXTURE_GUITHUMBCITYCENTERSHIELD     = addTexture("gui/thumbcitycentershield.png");
	public final static Texture TEXTURE_GUITHUMBSOLARPOWERPANELS     = addTexture("gui/thumbsolarpowerpanels.png");
	public final static Texture TEXTURE_GUITHUMBSOLARPOWERCOATING    = addTexture("gui/thumbsolarpowercoating.png");
	public final static Texture TEXTURE_GUITHUMBGARBAGEYARDBURNER    = addTexture("gui/thumbgarbageyardburner.png");
	public final static Texture TEXTURE_GUITHUMBPOLICE               = addTexture("gui/thumbpolice.png");
	
	public final static Texture TEXTURE_ICON16                  = addTexture("gui/icon16.png");
	public final static Texture TEXTURE_ICON32                  = addTexture("gui/icon32.png");
	public final static Texture TEXTURE_ICON256                 = addTexture("gui/icon256.png");
	public final static Texture TEXTURE_EMPTY                   = addTexture("empty.png");
	public final static Texture TEXTURE_MAINMENUBG              = addTexture("gui/mainmenubg.png");
	public final static Texture TEXTURE_MAINMENUFF              = addTexture("gui/ForceField.png");
	public final static Texture TEXTURE_MARSCITYLOGO            = addTexture("gui/marscitylogo.png");
	public final static Texture TEXTURE_FORCEFIELDBG            = addTexture("gui/forcefieldbackground2.png");
	public final static Texture TEXTURE_MSGBOX                  = addTexture("gui/msgbox.png");
	public final static Texture TEXTURE_GUITOOLSBG              = addTexture("gui/guitoolsBG.png");
	public final static Texture TEXTURE_GUITOOLTIP              = addTexture("gui/guitooltip.png");
	public final static Texture TEXTURE_GUIMENUBUTTON           = addTexture("gui/guimenubutton.png");
	public final static Texture TEXTURE_GUIDELETE               = addTexture("gui/guidelete.png");
	public final static Texture TEXTURE_GUITOOLBAR              = addTexture("gui/guitoolbar.png");
	public final static Texture TEXTURE_GUIMENU                 = addTexture("gui/guimenu.png");
	public final static Texture TEXTURE_GUIBUTTON               = addTexture("gui/guibutton.png");
	public final static Texture TEXTURE_GUIBUTTONDOWN           = addTexture("gui/guibuttondown.png");
	public final static Texture TEXTURE_GUIBUTTON2              = addTexture("gui/guibutton2.png");
	public final static Texture TEXTURE_GUIBUTTON2DOWN          = addTexture("gui/guibutton2down.png");
	public final static Texture TEXTURE_GUILABELBG              = addTexture("gui/guilabelbg.png");
	public final static Texture TEXTURE_GUILABELBGL             = addTexture("gui/guilabelbgl.png");
	public final static Texture TEXTURE_GUILABELBGR             = addTexture("gui/guilabelbgr.png");
	public final static Texture TEXTURE_PARTICLEFOG             = addTexture("fogparticle.png");
	public final static Texture TEXTURE_GUIDELETEBORDER         = addTexture("gui/guideleteborder.png");
	public final static Texture TEXTURE_GUICAMERAMOVE           = addTexture("gui/cameramove.png");
	public final static Texture TEXTURE_GUICAMERAROTATE         = addTexture("gui/camerarotate.png");
	public final static Texture TEXTURE_GUIBUILDINGSPANEL       = addTexture("gui/buildingspanel.png");
	public final static Texture TEXTURE_GUIBUILDINGSPANELL      = addTexture("gui/buildingspanell.png");
	public final static Texture TEXTURE_GUITEXTFIELD            = addTexture("gui/guitextfield.png");
	public final static Texture TEXTURE_GUITEXTFIELDL           = addTexture("gui/guitextfieldl.png");
	public final static Texture TEXTURE_GUITEXTFIELDR           = addTexture("gui/guitextfieldr.png");
	public final static Texture TEXTURE_CPSHADOW                = addTexture("gui/cpshadow.png");
	public final static Texture TEXTURE_SCROLLUP                = addTexture("gui/scrollup.png");
	public final static Texture TEXTURE_SCROLLDOWN              = addTexture("gui/scrolldown.png");
	public final static Texture TEXTURE_LOADABORT               = addTexture("gui/loadabort.png");
	public final static Texture TEXTURE_CPSPEC                  = addTexture("gui/cpspec.png");
	public final static Texture TEXTURE_BUILDINGINFO            = addTexture("gui/buildinginfo.png");
	public final static Texture TEXTURE_MONEYBG                 = addTexture("gui/moneybg.png");
	public final static Texture TEXTURE_GRAPHTRANSITION         = addTexture("gui/graphtransition.png");
	public final static Texture TEXTURE_GUITHUMBPLACEHOLDER     = addTexture("gui/placeholderthumb.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONENERGY    = addTexture("gui/dataviewenergy.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONSECUTIRY  = addTexture("gui/dataviewsecurity.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONHEALTH    = addTexture("gui/dataviewhealth.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONGARBAGE   = addTexture("gui/dataviewgarbagecollection.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONINTERNET  = addTexture("gui/dataviewinternet.png");
	public final static Texture TEXTURE_GUICHECKBOX             = addTexture("gui/guicheckbox.png");
	public final static Texture TEXTURE_GUICHECKED              = addTexture("gui/guichecked.png");
	public final static Texture TEXTURE_GUIRADIOBUTTON          = addTexture("gui/guiradiobutton.png");
	public final static Texture TEXTURE_GUIRADIOCHECKED         = addTexture("gui/guiradiochecked.png");
	public final static Texture TEXTURE_DATAVIEWBUTTONHAPPINESS = addTexture("gui/dataviewhappiness.png");
	public final static Texture TEXTURE_TOOLTIP                 = addTexture("gui/tooltip.png");
	public final static Texture TEXTURE_TOOLTIPL                = addTexture("gui/tooltipl.png");
	public final static Texture TEXTURE_TOOLTIPR                = addTexture("gui/tooltipr.png");
	public final static Texture TEXTURE_GUISCROLLBAROT          = addTexture("gui/scrollbar_ot.png");
	public final static Texture TEXTURE_GUISCROLLBAROM          = addTexture("gui/scrollbar_om.png");
	public final static Texture TEXTURE_GUISCROLLBAROB          = addTexture("gui/scrollbar_ob.png");
	public final static Texture TEXTURE_GUISCROLLBARIT          = addTexture("gui/scrollbar_it.png");
	public final static Texture TEXTURE_GUISCROLLBARIM          = addTexture("gui/scrollbar_im.png");
	public final static Texture TEXTURE_GUISCROLLBARIB          = addTexture("gui/scrollbar_ib.png");
	public final static Texture TEXTURE_GUIDVBACKGROUND         = addTexture("gui/DVbackground.png");
	public final static Texture TEXTURE_GUIDVBACKGROUNDT        = addTexture("gui/DVbackgroundt.png");
	public final static Texture TEXTURE_GUIDVBACKGROUNDB        = addTexture("gui/DVbackgroundb.png");
	public final static Texture TEXTURE_HAPPINESSEFFECT         = addTexture("happinesseffect.png");
	public final static Texture TEXTURE_GUIUPGRADEBACKGROUND    = addTexture("gui/upgradebackground.png");
	public final static Texture TEXTURE_GUIUPGRADEBUTTON        = addTexture("gui/upgradebutton.png");
	public final static Texture TEXTURE_SUPPLYRADIUS            = addTexture("supplyradius.png");
	public final static Texture TEXTURE_GUIUPGRADED             = addTexture("gui/upgraded.png");
	public final static Texture TEXTURE_GUILOCKED               = addTexture("gui/locked.png");
	
	/**
	 * Initializes the Resources that need to be initialized
	 */
	public static void init()
	{
		
		//Set up the shader
		//Main.splashscreen.setInfo("Loading shader...");
		//setupShader("shader.v","shader.f");
		
		//Load and parse the Language file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Buildings.init();
		
		//create necessary folders and extract files
		Main.splashscreen.setInfo("Creating folders...");
		if(!(new File("res")).exists()||Main.debugMode){
			(new File("res")).mkdir();
			
			(new File("res/settings")).mkdir();
			(new File("res/cities")).mkdir();
			Main.log("Created necessary folders.");
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//load xml files
		Main.splashscreen.setInfo("Loading xml files...");
		settingsFile = addXML("res/settings/settings.xml");
		try {
			strings = addLangFile("/res/lang/"+getSetting("lang")+".lang");
		} catch (IOException e) {}
	}

	/**
	 * Loads a unicode font
	 * @param font Font to load
	 * @return The unicode font ocject
	 */
	@SuppressWarnings("unchecked")
	public static UnicodeFont addFont(Font font)
	{
		UnicodeFont ufont = new UnicodeFont(font);
		ufont.getEffects().add(new ColorEffect());
		ufont.addAsciiGlyphs();
		try {
			Main.splashscreen.setInfo("Loading Font: "+font.getFontName()+" "+font.getSize());
			ufont.loadGlyphs();
			return ufont;
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads a texture from the specified input stream
	 * @param stream The imput stream
	 * @return The loaded texture
	 */
 	public static Texture LoadTexture(InputStream stream)
	{
		try {
			return TextureLoader.getTexture("PNG", new BufferedInputStream(stream));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
 	public static HashMap<String,String> addLangFile(String path) throws IOException
 	{
 		Main.splashscreen.setInfo("Loading language file: "+path);
 		Main.log("Loading language file: "+path);
 		HashMap<String,String> hm = new HashMap<String,String>();
 		BufferedReader reader=null;
 		try {
 			reader = new BufferedReader(new InputStreamReader(ResourceManager.class.getResourceAsStream(path)));

		} catch (Exception e) {
			e.printStackTrace();
			Game.exit();
		}
 		
 		String line;
 		while((line=reader.readLine()) != null)
 		{
 			if(line.startsWith("//"))continue;
 			int pos = line.indexOf("|");
 			if(pos!=-1){
 				try {
					hm.put(line.substring(0,pos), line.substring(pos+1));
				} catch (Exception e) {
					e.printStackTrace();
				}
 			}
 		}
 		
 		reader.close();
 		return hm;
 	}
 	
	/**
	 * Deletes an animatable from the render list ()for compatibility
	 * @param obj
	 */
	public static void deleteObject(Animatable obj)
	{
		Buildings.buildings.remove(obj);
	}
	
	/**
	 * Deletes an drawable from the render list ()for compatibility
	 * @param obj
	 */
	public static void deleteObject(Drawable obj)
	{
		Buildings.buildings.remove(obj);
	}
	
	/**
	 * Deletes an object from the render list
	 * @param obj The objects index
	 */
	public static void deleteObject(int obj)
	{
		Buildings.buildings.remove(obj);
	}
	
	/**
	 * Adds an object to the render list
	 * @param path Path to the .obj file
	 * @return An integer representing the displaylist
	 */
	public static int[] addObject(String path)
	{
		try {
			Main.log("Loading object: "+path);
			Main.splashscreen.setInfo("Loading object: "+path);
			int[] i = new int[]{-1,-1,-1};
			for(int j=0;j<=2;j++){
				try {
					i[j]=ObjectLoader.createDisplayList(ObjectLoader.loadModel(path,j));
				} catch (Exception e) {
					i[j]=-1;
				}
			}
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.label2.setText("Error! Failed to load object: "+path);
			Main.log("Failed to load object: "+path);
		}
		return (new int[]{-1,-1,-1});
	}
	
	/**
	 * Adds a sound file
	 * @param format The files format, e.g. "WAV"
	 * @param path The path of the sound file
	 * @return The loaded Audio
	 */
	public static Audio addSound(String format,String path)
	{
		path = soundspath + path;
		Main.log("Loading sound: "+path);
		Main.splashscreen.setInfo("Loading sound: "+path);
		try {
			return AudioLoader.getAudio(format, new BufferedInputStream(ResourceManager.class.getResourceAsStream(path)));
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.setInfo("Error! Failed to load sound: "+path);
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
		path = texturespath + path;
		Main.log("Loading texture: "+path);
		Main.splashscreen.setInfo("Loading texture: "+path);
		try {
			return LoadTexture(ResourceManager.class.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
			Main.splashscreen.label2.setText("Error! Failed to load texture: "+path);
			Main.log("Failed to load texture: "+path);
		}
		return null;
	}
	
	/**
	 * Loads LOD entity textures
	 * A LOD texture set contains 3 PNG textures, named like this:
	 * folder name: object
	 * files: object0.png, object1.png, object2.png
	 * @param path The name of the texture set (name of the folder)
	 * @return The loaded texture
	 */
	public static EntityTexture addEntityTexture(String path)
	{
		path = texturespath + "objects/"+ path + "/" + path;
		Main.log("Loading texture: "+path);
		Main.splashscreen.setInfo("Loading texture: "+path);
		EntityTexture tex = new EntityTexture();
		for(int i=0;i<=2;i++)
		{
			try {
				InputStream is = ResourceManager.class.getResourceAsStream(path+i+".png");
				if(is!=null)tex.setTexture(i, LoadTexture(is));
				else System.out.println("LoD texture not found: "+path+", LoD: "+i);
			} catch (Exception e) {
				tex.setTexture(i, null);
				System.out.println("LoD texture not found: "+path+", LoD: "+i);
			}
		}
		return tex;
	}
	
	/**
	 * Returns a the localized String from the language file
	 * @param input
	 * @return The localized string or the input, if not found
	 */
	public static String getString(String input)
	{
		String output =  null;
		output = strings.get(input);
		if(output==null)output=input;
		return output;
	}
	
	/**
	 * Return a localized String from the language file and replaces placeholders
	 * %1%, %2%, %3%, ...
	 * @param input
	 * @param replace String to fill in the placeholders
	 * @return The localized string with replacements or the input, if not found
	 */
	public static String getString(String input, String...replace)
	{
		String output = getString(input);
		return replacePlaceholders(output, replace);
	}
	
	/**
	 * Replaces placeholders in the input String
	 * @param input
	 * @param replace Strings to replace the placeholders %1%,%2%... with
	 * @return
	 */
	public static String replacePlaceholders(String input, String...replace)
	{
		String output=input;
		for(int i=0;i<replace.length;i++)
		{
			output = output.replaceAll("%"+(i+1)+"%", replace[i]);
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
	public static void playSoundRandom(Audio sound)
	{
		try {
			sound.playAsSoundEffect((float)(Math.random()+0.5), 1f, false);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Plays a sound from the soundPool
	 * @param sound The sound that should be played
	 */
	public static void playSound(Audio sound)
	{
		try {
			sound.playAsSoundEffect(1f, 1f, false);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Return the object at the given index in the render list
	 * @param index
	 * @return
	 */
	public static Building getObject(int index)
	{
		if(index==-1)return new Building(-1,0,0,0,0);
		return Buildings.buildings.get(index);
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
		StringBuilder vertexShaderSource = readFile(shaderpath+vert);
		StringBuilder fragmentShaderSource = readFile(shaderpath+frag);
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
	
	public static int getHoveredBuildingtype(int hoveredEntity)
	{
		if(hoveredEntity==-1)return -1;
		return getObject(hoveredEntity).getBuildingType();
	}

	public static FloatBuffer toFlippedFloatBuffer(float... floats) {
	    FloatBuffer b = BufferUtils.createFloatBuffer(floats.length);
	    b.put(floats);
	    b.flip();
	    return b;
	}
	
	public static String pathToCityname(String path)
	{
		return ((new File(path)).getName()).substring(0, ((new File(path)).getName()).length()-5);
	}
	
	public static String getBtDescription(int bt)
	{
		return ResourceManager.getString("DESCRIPTION_"+Buildings.getBuildingType(bt).getName());
	}
	
	public static String getBtDescription2(int bt)
	{
		return ResourceManager.getString("DESCRIPTION2_"+Buildings.getBuildingType(bt).getName());
	}
	
	/**
	 * Chosses the correct LoD model of the entity and draws it
	 * @param e The entity to draw
	 */
	public static void drawEntity(Entity e)
	{
		int dist = (int) Math.sqrt((e.getX()-Main.camera.getCx())*(e.getX()-Main.camera.getCx())+(e.getY()-Main.camera.getCy())*(e.getY()-Main.camera.getCy())+(e.getZ()-Main.camera.getCz())*(e.getZ()-Main.camera.getCz()));
		int lod;
		if(dist<Main.LOD1){
			lod = 0;
		}else if(dist<Main.LOD2){
			lod = 1;
		}else{
			lod = 2;
		}
		if(e.getTexture().getTexture(lod)!=null)glBindTexture(GL_TEXTURE_2D, e.getTexture().getTexture(lod).getTextureID());
		else glBindTexture(GL_TEXTURE_2D, 0);
		if(e.getDisplayList()[lod]!=-1){
			glCallList(e.getDisplayList()[lod]);
		}else{
			//If correct LoD model is not available, try to use a lower LoD model, or else a higher one
			for(int i=2;i>=0;i--)
			{
				if(e.getDisplayList()[i]!=-1){
					glCallList(e.getDisplayList()[i]);
					break;
				}
			}
		}	
	}
	
}
