package objects;

import game.Grid;
import game.Main;
import game.ResourceManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Obstacles {

	public static final int OBSTACLESMAX = 50;
	public static final int OBSTACLESMIN = 30;
	public static final int XMIN = -500;
	public static final int XMAX = 500;
	public static final int YMIN = -500;
	public static final int YMAX = 500;
	public static final int WIDTH = 10;
	public static final int DEPTH = 8;
	
	private static ArrayList<Entity> obstacles = new ArrayList<Entity>();
	private static int count=0;
	
	/**
	 * Generates obstacles and places them on the terrain
	 */
	public static void generate()
	{
		count = (int) Math.round(OBSTACLESMIN+Math.random()*(OBSTACLESMAX-OBSTACLESMIN));
		int countis=0;
		while(countis<count){
			int x =  (int) Math.round(XMIN+Math.random()*(XMAX-XMIN));
			int z =  (int) Math.round(YMIN+Math.random()*(YMAX-YMIN));
			if(Grid.isAreaFree(x, z, WIDTH, DEPTH))
			{
				Entity obs = new Entity(ResourceManager.OBJECT_OBSTACLE1, ResourceManager.TEXTURE_OBSTACLE1, x, 0, z);
				int rot = (int) (Math.round(Math.random()*3)*90);
				obstacles.add(obs);
				obs.setRotY(rot);
				Main.terrain.addChild(obs);
				Grid.setBuilding(x, z, WIDTH, DEPTH, new Building(), rot);
				countis++;
			}
		}
	}
	
	/**
	 * Loads obstacle data from a stream
	 * @param i
	 * @throws IOException 
	 */
	public static void loadFromStream(ObjectInputStream i) throws IOException
	{
		count = i.readShort();
		obstacles.clear();
		Main.terrain.getChildren().clear();
		for(int j=0;j<count;j++)
		{
			Entity e = new Entity(ResourceManager.OBJECT_OBSTACLE1, ResourceManager.TEXTURE_OBSTACLE1, i.readShort(), i.readShort(), i.readShort());
			e.setRotY(i.readShort());
			Main.terrain.addChild(e);
			Grid.setBuilding((int)e.getX(), (int)e.getZ(), WIDTH, DEPTH, new Building(), e.getRotY());
			obstacles.add(e);
		}
	}
	
	/**
	 * Saves obstacle data to a stream
	 * @param o
	 * @throws IOException 
	 */
	public static void saveToStream(ObjectOutputStream o) throws IOException
	{
		o.writeShort((short)count);
		for(int i=0;i<obstacles.size();i++)
		{
			o.writeShort((short)obstacles.get(i).getX());
			o.writeShort((short)obstacles.get(i).getY());
			o.writeShort((short)obstacles.get(i).getZ());
			o.writeShort((short)obstacles.get(i).getRotY());
		}
	}
	
}
