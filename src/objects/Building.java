package objects;

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
	
	public int getBuildingType() {
		return buidlingType;
	}

	@Override
	public float getHeight()
	{return height;}
	
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
	
	@Override
	public void update(int delta)
	{
		
	}
	
	@Override
	public void click()
	{
		
	}
	
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-getHeight(), 1000, AnimationManager.ACTION_DELETE);
		AnimationManager.animateValue(this, AnimationValue.rotX, (float) (getRotX()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.rotY, (float) (getRotY()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.rotZ, (float) (getRotZ()-10+Math.random()*20), 1000);
	}
}
