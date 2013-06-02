package game;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;

import gui.GUI;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		try {
			if(!(new File(path)).exists())(new File(path)).createNewFile();

			/////////////////////
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(path)));
			o.writeInt(Main.money);
			o.writeInt(ResourceManager.objects.size());
			for(Building b:ResourceManager.objects){
				o.writeFloat(b.getX());
				o.writeFloat(b.getY());
				o.writeFloat(b.getZ());
				o.writeInt(b.getBuidlingType());
			}
			o.close();
			/////////////////////
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox("Fehler beim Speichern", "Beim speichern der Stadt "+Main.cityname+" ist ein Fehler aufgetreten.");
			return;
		}
		Main.gui.MsgBox("Stadt gespeichert", Main.cityname+" wurde erfolgreich gespeichert.");
	}
	
	public static void Load(String path)
	{
		newGame();
		try {
			if(!(new File(path)).exists()){
				Main.gui.MsgBox("Datei nicht gefunden", "Die Stadt "+(new File(path)).getName().substring(0, (new File(path)).getName().length()-5)+" ist nicht auffindbar.",new Color(200,0,0));
				return;
			}
			
			ObjectInputStream i = new ObjectInputStream(new FileInputStream(new File(path)));
			Main.money=i.readInt();
			int count = i.readInt();
			for(int j=0;j<count;j++){
				ResourceManager.buildBuilding(i.readFloat(), i.readFloat(), i.readFloat(), i.readInt());
			}
			i.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox("Fehler beim Laden", "Beim Laden der Stadt "+(new File(path)).getName().substring(0, (new File(path)).getName().length()-5)+" ist ein Fehler aufgetreten.");
			return;
		}
		Main.cityname = (new File(path)).getName().substring(0, (new File(path)).getName().length()-5);
		Main.gui.cityName.setText(Main.cityname);
		Main.gui.MsgBox("Stadt geladen", Main.cityname+" wurde erfolgreich geladen."+System.lineSeparator()+"Viel Spaß beim spielen!");
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
