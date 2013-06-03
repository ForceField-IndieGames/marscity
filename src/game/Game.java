package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import gui.GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import objects.Building;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.opengl.ImageIOImageData;

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
			
			//Save a screenshot
			saveThumbnail(new File("res/cities/"+((new File(path)).getName()).substring(0, ((new File(path)).getName()).length()-5)+".png"));
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
	
	/**
	 * Saves a screenshot of the gl contents in a file
	 * @param file The file the screenshot should be saved in
	 * @param withgui Shows or hides the gui in the screenshot
	 */
	 public static void saveScreenshot(File file, boolean withgui){
		 
		 //Hide/show the gui
		 boolean prevguiv = GUI.isVisible();
		 GUI.setVisible(withgui);
		 
         //Creating an rbg array of total pixels
         int[] pixels = new int[Display.getWidth() * Display.getHeight()];
         int bindex;
         // allocate space for RBG pixels
         ByteBuffer fb = ByteBuffer.allocateDirect(Display.getWidth() * Display.getHeight() * 3);

         // grab a copy of the current frame contents as RGB
         glReadPixels(0, 0, Display.getWidth(), Display.getHeight(), GL_RGB, GL_UNSIGNED_BYTE, fb);

         BufferedImage imageIn = new BufferedImage(Display.getWidth(), Display.getHeight(),BufferedImage.TYPE_INT_RGB);
         // convert RGB data in ByteBuffer to integer array
         for (int i=0; i < pixels.length; i++) {
             bindex = i * 3;
             pixels[i] =
                 ((fb.get(bindex) << 16))  +
                 ((fb.get(bindex+1) << 8))  +
                 ((fb.get(bindex+2) << 0));
         }
         //Allocate colored pixel to buffered Image
         imageIn.setRGB(0, 0, Display.getWidth(), Display.getHeight(), pixels, 0 , Display.getWidth());

         //Creating the transformation direction (horizontal)
         AffineTransform at =  AffineTransform.getScaleInstance(1, -1);
         at.translate(0, -imageIn.getHeight(null));

         //Applying transformation
         AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
         BufferedImage imageOut = opRotated.filter(imageIn, null);

         try {//Try to screate image, else show exception.
             ImageIO.write(imageOut, "png", file);
         }
         catch (Exception e) {
             System.out.println("ScreenShot() exception: " +e);
         }
         
       //restore gui visibility
		 GUI.setVisible(prevguiv);
     }
	 
public static void saveThumbnail(File file){
		 
		 //Hide the gui
		 boolean prevguiv = GUI.isVisible();
		 GUI.setVisible(false);
		 
         //Creating an rbg array of total pixels
         int[] pixels = new int[1024 * 512];
         int bindex;
         // allocate space for RBG pixels
         ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 512 * 3);

         // grab a copy of the current frame contents as RGB
         glReadPixels(Display.getWidth()/2-512, Display.getHeight()/2-256, 1024, 512, GL_RGB, GL_UNSIGNED_BYTE, buf);

         BufferedImage imageIn = new BufferedImage(1024, 512,BufferedImage.TYPE_INT_RGB);
         // convert RGB data in ByteBuffer to integer array
         for (int i=0; i < pixels.length; i++) {
             bindex = i * 3;
             pixels[i] =
                 ((buf.get(bindex) << 16))  +
                 ((buf.get(bindex+1) << 8))  +
                 ((buf.get(bindex+2) << 0));
         }
         //Allocate colored pixel to buffered Image
         imageIn.setRGB(0, 0, 1024, 512, pixels, 0 , 1024);


         //Creating the transformation direction (horizontal)
         AffineTransform at =  AffineTransform.getScaleInstance(1, -1);
         at.translate(0, -imageIn.getHeight(null));

         //Applying transformation
         AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
         BufferedImage imageOut = opRotated.filter(imageIn, null);

         try {//Try to screate image, else show exception.
             ImageIO.write(imageOut, "png", file);
         }
         catch (Exception e) {
             System.out.println("ScreenShot() exception: " +e);
         }
         
       //restore gui visibility
		 GUI.setVisible(prevguiv);
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
