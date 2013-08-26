package buildings;

import objects.Building;

/**
 * This is a street segment.
 * @author Benedikt Ringlein
 */

public class Street extends Building {

	public Street(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}
	
	@Override
	public void click() {
	}
	
}
