package game;

public class House extends Entity {
	@Override
	public float getPreferredY()
	{return 15;}
	
	public House()
	{
		super(ResourceManager.OBJECT_HOUSE, ResourceManager.TEXTURE_HOUSE);
	}
	
	public House(int x, int y, int z)
	{
		super(ResourceManager.OBJECT_HOUSE, ResourceManager.TEXTURE_HOUSE,x,y+50,z);
		AnimationManager.animateValue(this, AnimationValue.Y, y, 0.5f);
	}
	
	@Override
	public void update(int delta)
	{
		
	}
	
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-50, 0.05f, AnimationManager.ACTION_DELETE);
		AnimationManager.animateValue(this, AnimationValue.rotX, (float) (getRotX()-50+Math.random()*100), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotY, (float) (getRotY()-50+Math.random()*100), 0.02f);
		AnimationManager.animateValue(this, AnimationValue.rotZ, (float) (getRotZ()-50+Math.random()*100), 0.02f);
	}
}
