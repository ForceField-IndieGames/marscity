package game;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;

import gui.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


import objects.Building;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

/**
 * This class provides static methods for pausing, resuming, saving and loading the game or
 * restarting it.
 * @author Benedikt Ringlein
 */

public class Game {
	private static boolean pause = false;
	public static final int INITIALMONEY = 5000;
	
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
		try {
			if(!(new File(path)).exists())(new File(path)).createNewFile();
			BufferedWriter file = new BufferedWriter(new FileWriter(path));
			
			//Money
			file.write("m "+Main.money+System.lineSeparator());
			
			//grid
			for(Building b:ResourceManager.objects){
				file.write("b "+b.getBuidlingType()+" "+b.getX()+" "+b.getY()+" "+b.getZ()+System.lineSeparator());
			}
			
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox("Fehler beim Speichern", "Beim speichern des Spielstandes ist ein Fehler aufgetreten.");
			return;
		}
		Main.gui.MsgBox("Spiel gespeichert", "Der Spielstand wurde erfolgreich gespeichert.");
	}
	
	public static void Load(String path)
	{
		newGame();
		try {
			if(!(new File(path)).exists())return;
			BufferedReader file = new BufferedReader(new FileReader(path));
			
			String line;
			while((line=file.readLine())!=null)
			{
				//Money
				if(line.startsWith("m")){
					Main.money = Integer.parseInt(line.split(" ")[1]);
				}
				if(line.startsWith("b")){
					int bt = Integer.parseInt(line.split(" ")[1]);
					float x = Float.parseFloat(line.split(" ")[2]);
					float y = Float.parseFloat(line.split(" ")[3]);
					float z = Float.parseFloat(line.split(" ")[4]);
					
					ResourceManager.buildBuilding(x, y, z, bt);
				}
			}
			
			
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox("Fehler beim Laden", "Beim Laden des Spielstandes ist ein Fehler aufgetreten.");
			return;
		}
		Main.gui.MsgBox("Spielstand geladen", "Der Spielstand wurde erfolgreich geladen."+System.lineSeparator()+"Viel Spaﬂ beim spielen!");
	}
	
	public static void newGame()
	{
		Main.money = INITIALMONEY;
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
