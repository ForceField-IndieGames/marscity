package game;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;

import gui.GUI;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import objects.Building;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Game {
	private static boolean pause = false;
	
	public static void Pause()
	{
		pause = true;
	}
	
	public static void Resume()
	{
		pause = false;
	}
	
	public static void Save(String path)
	{
		//TODO Save the game
		Document xml = ResourceManager.addXML(null);
		Element element = xml.createElement("money");
		element.setTextContent(""+Main.money);
		xml.appendChild(element);
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			 DOMSource        source = new DOMSource(xml);
			 File file = new File(path);
			 file.delete();
			 file.createNewFile();
	         FileOutputStream os     = new FileOutputStream(file);
	         StreamResult     result = new StreamResult(os);
	         transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Load(String path)
	{
		//TODO Load the game
		if(!(new File(path)).exists())return;
		Document xml = ResourceManager.addXML(path);
		Main.money = Integer.parseInt(xml.getElementsByTagName("money").item(0).getTextContent());
	}
	
	public static void newGame()
	{
		Main.money = 10000;
		Grid.init();
		ResourceManager.objects = new ArrayList<Building>();
		Main.gameState = Main.STATE_GAME;
		Main.gui = null;
		Main.gui = new GUI();
		Game.Resume();
	}
	
	public static boolean isPaused()
	{
		return pause;
	}
	
	public static void exit()
	{
		AL.destroy();
		glDeleteProgram(ResourceManager.shaderProgram);
		glDeleteShader(ResourceManager.vertexShader);
		glDeleteShader(ResourceManager.fragmentShader);
		Display.destroy();
		System.exit(0);
	}
}
