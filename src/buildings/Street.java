package buildings;

import objects.Building;

/**
 * This is a street segment.
 * @author Benedikt Ringlein
 */

public class Street extends Building {

	public Street(int bt, float x, float y, float z, float rY)
	{
		super(bt,x,y,z,rY);
	}
	
	@Override
	public void click() {
	}
	
}
