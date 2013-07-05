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
	private static int x1;
	private static int x2;
	private static int y1;
	private static int y2;
	
	private static int length;
	private static boolean vertical;
	
	public static int getX1() {
		return x1;
	}

	public static int getX2() {
		return x2;
	}

	public static int getY1() {
		return y1;
	}

	public static int getY2() {
		return y2;
	}

	public static int getLength() {
		return length;
	}

	public static boolean isVertical() {
		return vertical;
	}

	/**
	 * Marks the starting point of a new road
	 * @param posx
	 * @param posy
	 */
	public static void setStartPos(int posx, int posy)
	{
		startposx = posx;
		startposy = posy;
	}
	
	public static void updatePreview(int posx, int posy)
	{
		if(Math.abs(startposx-posx)<Math.abs(startposy-posy)){
			// vertical
			vertical = true;
			x1 = startposx;
			x2 = startposx;
			y1=startposy;
			y2 = posy;
			if(startposy>y2){
				int tmp = startposy;
				y1 = y2;
				y2 = tmp;
			}
			y2+=1;
			length = Math.abs(y2-y1)+1;
			int segments=0;
			for(int y=y1;y<y2;y++){
				if(Grid.isAreaFree(x1,y,1,1))segments++;
			}
			Main.gui.infoBuildingCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getBuidlingcost()*segments);
			Main.gui.infoBuildingCosts.AutoSize();
			Main.gui.infoMonthlyCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getMonthlycost()*segments);
			Main.gui.infoMonthlyCosts.AutoSize();
		}else{
			// horizontal
			vertical = false;
			x1 = startposx;
			x2 = posx;
			y1 = startposy;
			y2 = startposy;
			if(startposx>x2){
				int tmp = x1;
				x1 = x2;
				x2 = tmp;
			}
			x2+=1;
			length = Math.abs(x2-x1)+1;
			int segments=0;
			for(int x=x1;x<x2;x++){
				if(Grid.isAreaFree(x,y1,1,1))segments++;
			}
			Main.gui.infoBuildingCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getBuidlingcost()*segments);
			Main.gui.infoBuildingCosts.AutoSize();
			Main.gui.infoMonthlyCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getMonthlycost()*segments);
			Main.gui.infoMonthlyCosts.AutoSize();
		}
	}
	
	/**
	 * Marks the end point of a new road and builds it
	 * @param posx
	 * @param posy
	 */
	public static void buildStreet(int posx, int posy)
	{
		if(Math.abs(startposx-posx)<Math.abs(startposy-posy)){
			// vertical
			endposx = startposx;
			endposy = posy;
			if(!Grid.isStripFree(startposx, startposy, endposy-startposy, true, Buildings.BUILDINGTYPE_STREET))return;
			int cost=0;
			if(startposy>endposy){
				int tmp = startposy;
				startposy = endposy;
				endposy = tmp;
			}
			for(int i=startposy;i<=endposy;i++){
				if(Grid.isAreaFree(startposx, i, 1, 1)){
					cost+=Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getBuidlingcost();
				}
			}
			if(Main.money<cost)return; else Main.money-=cost;
			for(int i=startposy;i<=endposy;i++){
				if(Grid.isAreaFree(startposx, i, 1, 1)){
					Buildings.buildBuilding(startposx, 0, i, Buildings.BUILDINGTYPE_STREET);
				}
			}
		}else{
			// horizontal
			endposx = posx;
			endposy = startposy;
			if(!Grid.isStripFree(startposx, startposy, endposx-startposx, false, Buildings.BUILDINGTYPE_STREET))return;
			int cost=0;
			if(startposx>endposx){
				int tmp = startposx;
				startposx = endposx;
				endposx = tmp;
			}
			for(int i=startposx;i<=endposx;i++){
				if(Grid.isAreaFree(i, startposy, 1, 1)){
					cost+=Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getBuidlingcost();
				}
			}
			if(Main.money<cost)return; else Main.money-=cost;
			for(int i=startposx;i<=endposx;i++){
				if(Grid.isAreaFree(i, startposy, 1, 1)){
					Buildings.buildBuilding(i, 0, startposy, Buildings.BUILDINGTYPE_STREET);
				}
			}
		}
		ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
		Main.gui.infoBuildingCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getBuidlingcost());
		Main.gui.infoBuildingCosts.AutoSize();
		Main.gui.infoMonthlyCosts.setText(""+Buildings.getBuildingType(Buildings.BUILDINGTYPE_STREET).getMonthlycost());
		Main.gui.infoMonthlyCosts.AutoSize();
	}
	
	/**
	 * Marks the end point of a new road and deletes it
	 * @param posx
	 * @param posy
	 */
	public static void deleteStreet(int posx, int posy)
	{
		if(Math.abs(startposx-posx)<Math.abs(startposy-posy)){
			// vertical
			endposx = startposx;
			endposy = posy;
			if(startposy>endposy){
				int tmp = startposy;
				startposy = endposy;
				endposy = tmp;
			}
			for(int i=startposy;i<=endposy;i++){
				try {
					if((Grid.getCell(startposx, i).getBuilding().getBuildingType()==Buildings.BUILDINGTYPE_STREET)){
						Buildings.deleteBuiding(Grid.getCell(startposx, i).getBuilding());
						Grid.getCell(startposx, i).setBuilding(null);
					}
				} catch (Exception e) {}
			}
		}else{
			// horizontal
			endposx = posx;
			endposy = startposy;
			if(startposx>endposx){
				int tmp = startposx;
				startposx = endposx;
				endposx = tmp;
			}
			for(int i=startposx;i<=endposx;i++){
				try {
					if((Grid.getCell(i, startposy).getBuilding().getBuildingType()==Buildings.BUILDINGTYPE_STREET)){
						Buildings.deleteBuiding(Grid.getCell(i, startposy).getBuilding());
						Grid.getCell(i, startposy).setBuilding(null);
					}
				} catch (Exception e) {}
			}
		}
		ResourceManager.playSoundRandom(ResourceManager.SOUND_DESTROY);
	}

	public static int getStartposx() {
		return startposx;
	}

	public static void setStartposx(int startposx) {
		Streets.startposx = startposx;
	}

	public static int getStartposy() {
		return startposy;
	}

	public static void setStartposy(int startposy) {
		Streets.startposy = startposy;
	}

	public static int getEndposx() {
		return endposx;
	}

	public static void setEndposx(int endposx) {
		Streets.endposx = endposx;
	}

	public static int getEndposy() {
		return endposy;
	}

	public static void setEndposy(int endposy) {
		Streets.endposy = endposy;
	}
}

