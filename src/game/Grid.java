package game;

import java.util.ArrayList;
import java.util.List;

import objects.Building;

public class Grid {

	public final static int cellSize = 1;
	public final static int cellsX = 1500;
	public final static int cellsY = 1500;
	
	private final static List<GridCell> cells = initCells();
	
	public static GridCell getCell(int x, int y)
	{
		return cells.get(XYtoIndex(x+cellsX/2, y+cellsY/2));
	}
	
	public static void setBuilding(int x, int y, Building building)
	{
		int width = ResourceManager.getBuildingType(building.getBuidlingType()).getWidth();
		int height = ResourceManager.getBuildingType(building.getBuidlingType()).getDepth();
		int x1 = x - (int) Math.ceil(width/2-1);
		int y1 = y -(int) Math.ceil(height/2-1);
		int x2 = x + (int) Math.floor(width/2);
		int y2 = y +(int) Math.floor(height/2);
		for(int i=y1+cellsY/2;i<=y2+cellsY/2;i++){
			for(int j=x1+cellsX/2;j<=x2+cellsX/2;j++){
				cells.get(XYtoIndex(j, i)).setBuilding(building);
			}
		}
	}
	
	public static void clearsCells(int x, int y, int width, int height)
	{
		int x1 = x - (int) Math.ceil(width/2-1);
		int y1 = y -(int) Math.ceil(height/2-1);
		int x2 = x + (int) Math.floor(width/2);
		int y2 = y +(int) Math.floor(height/2);
		for(int i=y1+cellsY/2;i<=y2+cellsY/2;i++){
			for(int j=x1+cellsX/2;j<=x2+cellsX/2;j++){
				cells.get(XYtoIndex(j, i)).setBuilding(null);
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
	
	private static int[] indexToXY(int index)
	{
		return new int[]{index%cellsX,(index-(index%cellsX)/cellsX)};
	}
	
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
		int x1 = x - (int) Math.ceil(width/2-1);
		int y1 = y -(int) Math.ceil(height/2-1);
		int x2 = x + (int) Math.floor(width/2);
		int y2 = y +(int) Math.floor(height/2);
		System.out.println("x1:"+x1+" x2:"+x2+" y1:"+y1+" y2:"+y2);
		for(int i=y1+cellsY/2;i<=y2+cellsY/2;i++){
			for(int j=x1+cellsX/2;j<=x2+cellsX/2;j++){
				if(cells.get(XYtoIndex(j, i)).getBuilding()!=null)return false;
			}
		}
		return true;
	}
	
}
