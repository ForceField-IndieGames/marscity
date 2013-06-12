package objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import game.ResourceManager;
import animation.AnimationManager;
import animation.AnimationValue;

/**
 * This is a building. It is defined by a buildingtype and a position.
 * It renders and updates itself. Buildings have to be added using the ResourceManager in
 * order to be displayed.
 * @author Benedikt Ringlein
 */

public class Building extends Entity {
	
	private float height = 0;
	private int buidlingType;
	
	public Building(){}

	public Building(int bt)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture());
		height = ResourceManager.getBuildingType(bt).getPreferredY();
		this.buidlingType = bt;
	}

	public Building(int bt, float x, float y, float z)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture(),x,y,z);
		height = ResourceManager.getBuildingType(bt).getPreferredY();
		this.buidlingType = bt;
	}

	public int getBuildingType() {
		return buidlingType;
	}

	@Override
	public float getHeight()
	{return height;}
	
	/**
	 * Is called every frame
	 */
	@Override
	public void update(int delta)
	{
		
	}
	
	/**
	 * Is called when the building is clicked
	 */
	@Override
	public void click()
	{
		
	}
	
	/**
	 * Buildings that have to load special data can overide this method
	 * @param i The ObjectInputStream to read from
	 * @throws IOException 
	 */
	public void loadFromStream(ObjectInputStream i) throws IOException
	{
		
	}
	
	/**
	 * Buildings that have to save special data can override this method
	 * @param o The ObjectOutputStream to write to
	 * @throws IOException 
	 */
	public void saveToStream(ObjectOutputStream o) throws IOException
	{
		
	}
	
	/**
	 * Deletes the buliding with an animaiton
	 */
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-getHeight(), 1000, AnimationManager.ACTION_DELETE);
		AnimationManager.animateValue(this, AnimationValue.rotX, (float) (getRotX()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.rotY, (float) (getRotY()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.rotZ, (float) (getRotZ()-10+Math.random()*20), 1000);
	}
}
