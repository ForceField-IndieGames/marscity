package game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import gui.GUI;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import objects.Building;
import objects.Buildings;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

/**
 * This class provides static methods for pausing, resuming, saving and loading the game or
 * restarting it.
 * @author Benedikt Ringlein
 */

public class Game {
	private static boolean pause = false;
	public static final int INITIALMONEY = 50000;
	public static final int INITIALTAXES = 19;
	public static final short FileFormatVersion = 1;
	
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
			o.writeShort(FileFormatVersion);
			o.writeInt(Main.money);
			o.writeByte(Main.taxes);
			o.writeInt(Main.citizens);
			o.writeShort((short) Main.camera.getX());
			o.writeShort((short) Main.camera.getZ());
			o.writeByte((byte) Main.camera.getRotX());
			o.writeShort((short) Main.camera.getRotY());
			o.writeInt(Buildings.buildings.size());
			for(Building b:Buildings.buildings){
				o.writeShort((short) b.getX());
				o.writeShort((short) b.getZ());
				o.writeShort(b.getBuildingType());
				b.saveToStream(o);
			}
			o.close();
			
			//Save a screenshot
			saveThumbnail(new File("res/cities/"+ResourceManager.pathToCityname(path)+".png"));
			/////////////////////
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_SAVINGERROR"), ResourceManager.getString("MSGBOX_TEXT_SAVINGERROR").replaceAll(ResourceManager.PLACEHOLDER1, ResourceManager.pathToCityname(path)));
			return;
		}
		Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_CITYSAVED"), ResourceManager.getString("MSGBOX_TEXT_CITYSAVED").replaceAll(ResourceManager.PLACEHOLDER1, ResourceManager.pathToCityname(path)));
	}
	
	public static void Load(String path)
	{
		newGame();
		try {
			if(!(new File(path)).exists()){
				Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_LOADINGFILENOTFOUND"), ResourceManager.getString("MSGBOX_TEXT_LOADINGFILENOTFOUND").replaceAll(ResourceManager.PLACEHOLDER1, ResourceManager.pathToCityname(path)),new Color(200,0,0));
				return;
			}
			
			
			ObjectInputStream i = new ObjectInputStream(new FileInputStream(new File(path)));
			short FFV = i.readShort();
			
			switch(FFV)
			{
				case 1:
					Main.money = i.readInt();
					Main.taxes = i.readByte();
					Main.gui.taxes.setValue(Main.taxes);
					Main.citizens = i.readInt();
					Main.camera.setX(i.readShort());
					Main.camera.setZ(i.readShort());
					Main.camera.setRotX(i.readByte());
					Main.camera.setRotY(i.readShort());
					int count = i.readInt();
					for(int j=0;j<count;j++){
						(Buildings.buildBuilding(i.readShort(), 0, i.readShort(), i.readShort())).loadFromStream(i);
					}
					break;
				default:
					Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_UNKNOWNFFV"), ResourceManager.getString("MSGBOX_TEXT_UNKNOWNFFV").replaceFirst(ResourceManager.PLACEHOLDER1, ""+FFV).replaceFirst(ResourceManager.PLACEHOLDER2, ""+FileFormatVersion));
					i.close();
					return;
			}
			
			i.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_LOADINGERROR"), ResourceManager.getString("MSGBOX_TEXT_LOADINGERROR").replaceAll(ResourceManager.PLACEHOLDER1, ResourceManager.pathToCityname(path)));
			return;
		}
		Main.cityname = (new File(path)).getName().substring(0, (new File(path)).getName().length()-5);
		Main.gui.cityName.setText(Main.cityname);
		Main.gui.MsgBox(ResourceManager.getString("MSGBOX_TITLE_CITYLOADED"), ResourceManager.getString("MSGBOX_TEXT_CITYLOADED").replaceAll(ResourceManager.PLACEHOLDER1, ResourceManager.pathToCityname(path)));
		Buildings.refreshSupply();
	}
	
	public static void newGame()
	{
		Grid.init();
		Buildings.buildings = new ArrayList<Building>();
		Buildings.buildBuilding(0, 0, 0, Buildings.BUILDINGTYPE_CITYCENTER);
		Main.money = INITIALMONEY;
		Main.taxes = INITIALTAXES;
		Main.citizens = 0;
		Main.currentDataView = null;
		Main.gameState = Main.STATE_GAME;
		Main.currentBT = -1;
		Main.buildpreview.setVisible(false);
		Main.selectedTool=Main.TOOL_SELECT;
		Main.gui = null;
		Main.gui = new GUI();
		Game.Resume();
		try {
			Main.MonthlyTimer.scheduleAtFixedRate(MonthlyActions.ExecuteTransactions, Main.MONTH_MILLIS, Main.MONTH_MILLIS);
		} catch (Exception e) {}
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
		 
		 //Render
		 Main.renderGL();
		 
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
		 DataView dv = Main.currentDataView;
		 Main.currentDataView = null;
		 
		 //render
		 Main.renderGL();
		 
         //Creating an rbg array of total pixels
         int[] pixels = new int[1024 * 512];
         int bindex;
         // allocate space for RBG pixels
         ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 512 * 3);

         // grab a copy of the current frame contents as RGB
         glReadPixels(Display.getWidth()/2-512, Display.getHeight()/2-206, 1024, 512, GL_RGB, GL_UNSIGNED_BYTE, buf);

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
         AffineTransform at =  AffineTransform.getScaleInstance(0.25, -0.25);
         at.translate(0, -imageIn.getHeight(null));

         //Applying transformation
         AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
         BufferedImage imageOut = opRotated.filter(imageIn, null);
         
         try {//Try to screate image, else show exception.
             ImageIO.write(imageOut, "png", file);
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         
       //restore gui visibility
		 GUI.setVisible(prevguiv);
		 Main.currentDataView = dv;
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
