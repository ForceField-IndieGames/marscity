package game;

import java.util.ArrayList;
import java.util.List;

import objects.Building;

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
	
	public static GridCell getCell(int x, int y)
	{
		return cells.get(XYtoIndex(x+cellsX/2, y+cellsY/2));
	}
	
	public static void setBuilding(int x, int y, Building building)
	{
		int width = ResourceManager.getBuildingType(building.getBuidlingType()).getWidth();
		int height = ResourceManager.getBuildingType(building.getBuidlingType()).getDepth();
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
	
//	private static int[] indexToXY(int index)
//	{
//		return new int[]{index%cellsX,(index-(index%cellsX)/cellsX)};
//	}
	
	private static int XYtoIndex(int x, int y)
	{
		return x+cellsX*y;
	}
	
	public static int posToIndex(int x, int y)
	{
		return (x+cellsX/2)+cellsX*(y+cellsY/2);
	}
	
	public static boolean isAreaFree(int x, int y, int width, int height)
	{
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
					if(cells.get(posToIndex(xpos, j)).getBuilding()!=null)return false;
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
					if(cells.get(posToIndex(j, ypos)).getBuilding()!=null)return false;
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}
	
}
