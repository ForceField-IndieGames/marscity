package buildings;

import objects.Building;

public class CityCenter extends Building {

	public CityCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}
	
	
	
	@Override
	public void delete() {
		//This building can't be deleted
	}
}
