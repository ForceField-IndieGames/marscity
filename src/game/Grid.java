package game;

import java.util.ArrayList;
import java.util.List;

public class Grid {

	public final static int cellSize = 1;
	public final static int cellsX = 100;
	public final static int cellsY = 100;
	
	private final static List<GridCell> cells = initCells();
	
	public static GridCell getCell(int x, int y)
	{
		return cells.get(XYtoIndex(x, y));
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
	
	public static int[] indexToXY(int index)
	{
		return new int[]{index%cellsX,(index-(index%cellsX)/cellsX)};
	}
	
	public static int XYtoIndex(int x, int y)
	{
		return x+cellsX*y;
	}
	
	public static boolean isAreaFree(int x1, int y1, int x2, int y2)
	{
		for(int i=y1;i<y2;i++){
			for(int j=x1;j<x2;j++){
				if(cells.get(XYtoIndex(j, i)).getBuilding()!=null)return false;
			}
		}
		return true;
	}
	
}
