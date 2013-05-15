package objects;

import game.ResourceManager;
import animation.AnimationManager;
import animation.AnimationValue;

public class Building extends Entity {
	private float preferredY = 0;
	
	@Override
	public float getPreferredY()
	{return preferredY;}
	
	public Building(int bt)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture());
		preferredY = ResourceManager.getBuildingType(bt).getPreferredY();
	}
	
	public Building(int bt, int x, int y, int z)
	{
		super(ResourceManager.getBuildingType(bt).getDisplaylist(), ResourceManager.getBuildingType(bt).getTexture(),x,y+5,z);
		preferredY = ResourceManager.getBuildingType(bt).getPreferredY();
		AnimationManager.animateValue(this, AnimationValue.Y, y, 0.05f);
	}
	
	@Override
	public void update(int delta)
	{
		
	}
	
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-getPreferredY()*5, 0.002f, AnimationManager.ACTION_DELETE);
		AnimationManager.animateValue(this, AnimationValue.rotX, (float) (getRotX()-50+Math.random()*100), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotY, (float) (getRotY()-50+Math.random()*100), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotZ, (float) (getRotZ()-50+Math.random()*100), 0.02f);
	}
}
