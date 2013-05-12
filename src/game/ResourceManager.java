package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

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
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import animation.Animatable;


public class ResourceManager {
	
	public final static UnicodeFont font = new UnicodeFont(new Font("Arial",Font.BOLD,15));
	
	public static int shaderProgram, vertexShader, fragmentShader;
	
	static Document langFile = null;
	static DocumentBuilder builder = null;
	
	public final static int OBJECT_HOUSE = addObject("/res/haus.obj");
	public final static int OBJECT_MONKEY = addObject("/res/monkey.obj");
	public final static int OBJECT_BUNNY = addObject("/res/bunny.obj");
	public final static int OBJECT_TERRAIN = addObject("/res/terrain.obj");
	
	public final static Audio SOUND_DROP = addSound("WAV", "/res/drop.wav");
	public final static Audio SOUND_DESTROY = addSound("WAV", "/res/destroy.wav");
	
	public final static Texture TEXTURE_HOUSE = addTexture("/res/haustextur.png");
	public final static Texture TEXTURE_MONKEY = addTexture("/res/monkeytexture.png");
	public final static Texture TEXTURE_TERRAIN = addTexture("/res/mars.png");
	public final static Texture TEXTURE_GUISELECT = addTexture("/res/guiselect.png");
	public final static Texture TEXTURE_GUIADD = addTexture("/res/guiadd.png");
	public final static Texture TEXTURE_GUIDELETE = addTexture("/res/guidelete.png");
	public final static Texture TEXTURE_GUITOOLBAR = addTexture("/res/guitoolbar.png");
	public final static Texture TEXTURE_GUIMENU = addTexture("/res/guimenu.png");
	public final static Texture TEXTURE_EMPTY = addTexture("/res/empty.png");
	public final static Texture TEXTURE_GUIBUTTON = addTexture("/res/guibutton.png");
	
	public static List<Drawable> objects = new ArrayList<Drawable>();
	
	private static Texture LoadTexture(String path)
	{
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			System.err.println("Texture file not found: "+path);
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
			return ObjectLoader.createDisplayList(ObjectLoader.loadModel(new File(ResourceManager.class.getResource(path).toURI())));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			Main.log("Object kann nicht geladen werden: "+ResourceManager.class.getResource(path));
		}
		return -1;
	}
	
	public static Audio addSound(String format,String path)
	{
		try {
			return AudioLoader.getAudio(format, ResourceLoader.getResourceAsStream(ResourceManager.class.getResource(path).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Texture addTexture(String path)
	{
		try {
			return LoadTexture(ResourceManager.class.getResource(path).getPath());
		} catch (Exception e) {
			Display.destroy();
			System.exit(1);
			return null;
		}
		
	}
	
	public ResourceManager()
	{
			
	}
	
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
		
		//Set up the shaders
		setupShader("/res/shader.v","/res/shader.f");
		
		//Load and parse the Language file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		langFile = addXML("/res/lang/DE.xml");
	}
	
	/**
	 * Returns a the localized String from the language file
	 * @param input
	 * @return
	 */
	public static String getString(String input)
	{
		String output =  null;
		try {
			output =  langFile.getElementsByTagName(input).item(0).getTextContent();
		} catch (Exception e) {
		}
		
		if(output==null)return "";
		return output;
	}
	
	public static Document addXML(String path)
	{
		try {
			Document file = builder.parse(new File(ResourceManager.class
					.getResource(path).toURI()));
			return file;
		} catch (SAXException | IOException | URISyntaxException e) {
			e.printStackTrace();
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
	
	public static Drawable getObject(int index)
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
	 * Loads a file into a StringBuilder
	 * @param path The Path to the File
	 * @return a StringBuilder conatining hte Filecontents
	 */
	private static StringBuilder readFile(String path)
	{
		StringBuilder string = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(ResourceManager.class.getResource(path).toURI())));
			
			String line;
			while((line=reader.readLine())!=null)
			{
				string.append(line);
				string.append("\r\n");
			}
			reader.close();
			return string;
		} catch (IOException | URISyntaxException e) {
			System.err.println("File not found: "+ResourceManager.class.getResource(path).toExternalForm());
			
			e.printStackTrace();
		}
		return null;
	}
	
	
}
