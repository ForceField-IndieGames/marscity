package objects;

/**
 * This class will provide static methods to build streets and also
 * it will manage traffic etc.
 * @author Benedikt Ringlein
 */

public class Streets {

	private static int startposx;
	private static int startposy;
	private static int endposx;
	private static int endposy;
	
	/**
	 * Marks the starting point of a new road
	 * @param posx
	 * @param posy
	 */
	public static void startBuilding(int posx, int posy)
	{
		startposx = posx;
		startposy = posy;
	}
	
	/**
	 * Marks the end point of a new road and builds it
	 * @param posx
	 * @param posy
	 */
	public static void endBuilding(int posx, int posy)
	{
		endposx = posx;
		endposy = posy;
		//TODO build the street
	}
	
}

