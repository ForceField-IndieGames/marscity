package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Contains statistics about a city
 * @author Benedikt Ringlein
 */

public class Statistics {

	public static int CitizensHouseMax;
	public static int CitizensHouseCurrent;
	public static int CitizensBighouseMax;
	public static int CitizensBighouseCurrent;
	
	public static void loadFromStream(ObjectInputStream i) throws IOException
	{
		CitizensHouseMax = i.readInt();
		CitizensHouseCurrent = i.readInt();
		CitizensBighouseMax = i.readInt();
		CitizensBighouseCurrent = i.readInt();
	}
	
	public static void saveToStream(ObjectOutputStream o) throws IOException
	{
		 o.writeInt(CitizensHouseMax);
		 o.writeInt(CitizensHouseCurrent);
		 o.writeInt(CitizensBighouseMax);
		 o.writeInt(CitizensBighouseCurrent);
	}

	public static int      citizens      = 0;                                //The citizens that live in the city
	public static int      money         = Game.INITIALMONEY;                //The players money
	
}
