package game;

import java.util.ArrayList;
import java.util.List;

import objects.Building;
import objects.Buildings;

/**
 * This grid contain information about placed buildings. It is used when
 * new buildings are placed to determine if there is already an object at this position.
 * @author Benedikt Ringlein
 */

public class Grid {

	public final static int cellSize = 1;
	public final static int cellsX = 1000;
	public final static int cellsY = 1000;
	
	private static List<GridCell> cells = initCells();
	
	public static void init()
	{
		cells = initCells();
	}
	
	/**
	 * Return the cell at the given position, with (0|0) beein the center of the map
	 * @param x The x posiiton
	 * @param y The y position
	 * @return The cell at the given position
	 * @throws IndexOutOfBoundsException
	 */
	public static GridCell getCell(int x, int y)
	{
		try {
			return cells.get(XYtoIndex(x+cellsX/2, y+cellsY/2));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int getCellBT(int x, int y)
	{
		if(getCell(x, y)!=null)return getCell(x, y).getBuildingType();
		return -1;
	}
	
	public static void setBuilding(int x, int y, Building building, float rY)
	{
		setBuilding(x, y, Buildings.getBuildingType(building.getBuildingType())
				.getWidth(),Buildings.getBuildingType(building.getBuildingType()).getDepth(), 
				building, rY);
	}
	
	public static void setBuilding(int x, int y, int width, int depth, Building building, float rY)
	{
		int w;
		int d;
		//Take rotation into account
		if(rY!=0&&rY!=180){
			w = depth;
			d = width;
		}else{
			w = width;
			d = depth;
		}
		
		int x1;
		int y1;
		int x2;
		int y2;
		if(w==1){
			x1 = x;
			x2 = x;
		}else{
			x1 = x - (int) Math.ceil(w/2-1);
			x2 = x + (int) Math.floor(w/2);
		}
		if(d==1){
			y1 = y;
			y2 = y;
		}else{
			y1 = y -(int) Math.ceil(d/2-1);
			y2 = y +(int) Math.floor(d/2);
		}
		for(int i=y1+cellsY/2;i<=y2+cellsY/2;i++){
			for(int j=x1+cellsX/2;j<=x2+cellsX/2;j++){
				cells.get(XYtoIndex(j, i)).setBuilding(building);
			}
		}
	}
	
	public static void clearsCells(int x, int y, int width, int height)
	{
		if(width==1&&height==1){
			cells.get(posToIndex(x, y)).setBuilding(null);
			return;
		}
		int x1;
		int y1;
		int x2;
		int y2;
		if(width==1){
			x1 = x;
			x2 = x;
		}else{
			x1 = x - (int) Math.ceil(width/2-1);
			x2 = x + (int) Math.floor(width/2);
		}
		if(height==1){
			y1 = y;
			y2 = y;
		}else{
			y1 = y -(int) Math.ceil(height/2-1);
			y2 = y +(int) Math.floor(height/2);
		}
		for(int i=y1;i<=y2;i++){
			for(int j=x1;j<=x2;j++){
				cells.get(posToIndex(j, i)).setBuilding(null);
			}
		}
	}
	
	private static List<GridCell> initCells()
	{
		List<GridCell> cells = new ArrayList<GridCell>();
		for(int y=0;y<cellsY;y++){
			for(int x=0;x<cellsX;x++){
				cells.add(XYtoIndex(x, y),new GridCell(null));
			}
		}
		return cells;
	}

	/**
	 * Returns the index of the cell at the given position
	 * (0|0) it the bottom left corner of the grid
	 * @param x
	 * @param y
	 * @return The cell
	 */
	private static int XYtoIndex(int x, int y)
	{
		return x+cellsX*y;
	}
	
	/**
	 * Returns the index of the cell at the given position
	 * (0|0) it the center of the map
	 * @param x
	 * @param y
	 * @return The cell
	 */
	public static int posToIndex(int x, int y)
	{
		return (x+cellsX/2)+cellsX*(y+cellsY/2);
	}
	
	public static boolean isAreaFree(int x, int y, int width, int height)
	{
		if(x>Grid.cellsX/2||x<-Grid.cellsX/2)return false;
		if(width==1&&height==1){
			try {
				if(cells.get(posToIndex(x, y)).getBuilding()!=null)return false;else return true;
			} catch (Exception e) {
				return false;
			}
		}
		int x1;
		int y1;
		int x2;
		int y2;
		
		//width!=1&&height!=1
		x1 = x - (int) Math.ceil(width/2-1);
		x2 = x + (int) Math.floor(width/2);
		y1 = y -(int) Math.ceil(height/2-1);
		y2 = y +(int) Math.floor(height/2);
		for(int i=y1;i<=y2;i++){
			for(int j=x1;j<=x2;j++){
				try {
					if(cells.get(posToIndex(j, i)).getBuilding()!=null)return false;
				} catch (Exception e) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	public static boolean isStripFree(int xpos, int ypos, int length, boolean vertical)
	{
		return isStripFree(xpos, ypos, length, vertical, -1);
	}
	
	public static boolean isStripFree(int xpos, int ypos, int length, boolean vertical, int ignore)
	{
		if(vertical){
			//vertical
			int yposdest = ypos+length;
			if(length<0){
				int tmp = yposdest;
				yposdest = ypos;
				ypos = tmp;
			}
			for(int j=ypos;j<=yposdest;j++){
				try {
					if(cells.get(posToIndex(xpos, j)).getBuilding()!=null&&cells.get(posToIndex(xpos, j)).getBuilding().getBuildingType()!=ignore)return false;
				} catch (Exception e) {
					return false;
				}
			}
		}else{
			//horizontal
			int xposdest = xpos+length;
			if(length<0){
				int tmp = xposdest;
				xposdest = xpos;
				xpos = tmp;
			}
			for(int j=xpos;j<=xposdest;j++){
				try {
					if(cells.get(posToIndex(j, ypos)).getBuilding()!=null&&cells.get(posToIndex(j, ypos)).getBuilding().getBuildingType()!=ignore)return false;
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks if an area is surrounded by a building type (i.e. has at least one cell
	 * with that building type next to it)
	 * @param x Position of the area (Left edge)
	 * @param y Position of the area (down edge)
	 * @param width Width of the area
	 * @param height Height of the area
	 * @param bt Building type that it searched for
	 * @return True, if the building type was found around the area
	 */
	public static boolean areaSurroundedWith(int x, int y,int width, int height,int bt)
	{
		try {
			//bottom
			for(int i=x;i<x+width;i++){
				if(getCell(i, y+height).getBuildingType()==bt)return true;
			}
			//top
			for(int i=x;i<x+width;i++){
				if(getCell(i, y-1).getBuildingType()==bt)return true;
			}
			//left
			for(int i=y;i<y+height;i++){
				if(getCell(x-1, i).getBuildingType()==bt)return true;
			}
			//right
			for(int i=y;i<y+height;i++){
				if(getCell(x+width, i).getBuildingType()==bt)return true;
			}
		} catch (Exception e) {}
			
		return false;
	}
	
	/**
	 * Checks, if a building is sourrounded by a building type
	 * (i.e. has at least one cell with that building type next to it)
	 * >Convenience method for calling areaSurroundedWith() more easily
	 * @param x The x position of the building's center
	 * @param y The y posiitno of the building's center
	 * @param btarea The building type of the building
	 * @param btsourround The buildingtype that is searched for
	 * @param rY Y rotation
	 * @return True, if the buildings type was found next to the building
	 */
	public static boolean buildingSurroundedWith(int x, int y, int btarea, int btsourround, float rY)
	{
		int width; 
		int height;
		if(rY!=0&&rY!=180){
			width = Buildings.getBuildingType(btarea).getDepth();
			height = Buildings.getBuildingType(btarea).getWidth();
		}else{
			width = Buildings.getBuildingType(btarea).getWidth();
			height = Buildings.getBuildingType(btarea).getDepth();
		}
		int x1;
		int y1;
		if(width==1) x1 = x; else x1 = x - (int) Math.ceil(width/2-1);
		if(height==1) y1 = y; else y1 = y -(int) Math.ceil(height/2-1);
		return areaSurroundedWith(x1, y1, width, height, btsourround);
	}
	
}
