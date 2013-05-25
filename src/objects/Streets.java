package objects;

import game.Grid;
import game.Main;
import game.ResourceManager;

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
		if(Math.abs(startposx-posx)<Math.abs(startposy-posy)){
			// vertical
			endposx = startposx;
			endposy = posy;
			if(!Grid.isStripFree(startposx, startposy, endposy-startposy, true))return;
			if(Main.money<ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_STREET).getBuidlingcost()*(Math.abs(endposy-startposy)+1))return;
			Main.money -= ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_STREET).getBuidlingcost()*(Math.abs(endposy-startposy)+1);
			if(startposy>endposy){
				int tmp = startposy;
				startposy = endposy;
				endposy = tmp;
			}
			for(int i=startposy;i<=endposy;i++){
				ResourceManager.buildBuilding(startposx, 0, i, ResourceManager.BUILDINGTYPE_STREET);
			}
		}else{
			// horizontal
			endposx = posx;
			endposy = startposy;
			if(!Grid.isStripFree(startposx, startposy, endposx-startposx, false))return;
			if(Main.money<ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_STREET).getBuidlingcost()*(Math.abs(endposx-startposx)+1))return;
			Main.money -= ResourceManager.getBuildingType(ResourceManager.BUILDINGTYPE_STREET).getBuidlingcost()*(Math.abs(endposx-startposx)+1);
			if(startposx>endposx){
				int tmp = startposx;
				startposx = endposx;
				endposx = tmp;
			}
			for(int i=startposx;i<=endposx;i++){
				ResourceManager.buildBuilding(i, 0, startposy, ResourceManager.BUILDINGTYPE_STREET);
			}
		}
		if((startposx!=endposx)||(startposy!=endposy)){
			ResourceManager.buildBuilding(endposx, 0, endposy, ResourceManager.BUILDINGTYPE_STREET);
		}
		
	}
	
}

