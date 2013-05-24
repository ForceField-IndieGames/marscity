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
	private float preferredY = 0;
	private int buidlingType;
	
	public int getBuidlingType() {
		return buidlingType;
	}

	@Override
	public float getPreferredY()
	{return preferredY;}
	
	public Building(int bt)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture());
		preferredY = ResourceManager.getBuildingType(bt).getPreferredY();
		this.buidlingType = bt;
	}
	
	public Building(int bt, float x, float y, float z)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture(),x,y+5,z);
		preferredY = ResourceManager.getBuildingType(bt).getPreferredY();
		AnimationManager.animateValue(this, AnimationValue.Y, y, 0.05f);
		this.buidlingType = bt;
	}
	
	@Override
	public void update(int delta)
	{
		
	}
	
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-getPreferredY()*5, 0.002f, AnimationManager.ACTION_DELETE);
		AnimationManager.animateValue(this, AnimationValue.rotX, (float) (getRotX()-10+Math.random()*20), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotY, (float) (getRotY()-10+Math.random()*20), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotZ, (float) (getRotZ()-10+Math.random()*20), 0.02f);
	}
}
