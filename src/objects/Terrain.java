package objects;
import game.ResourceManager;

import org.newdawn.slick.opengl.Texture;

/**
 * This is basicly a normal entity. But it is the only object that is used
 * for picking, when it comes to creating a new building and it is also ignored
 * when selecting objects.
 * @author Benedikt Ringlein
 */

public class Terrain extends Entity {
	
	public Terrain()
	{
		super(ResourceManager.OBJECT_TERRAIN,ResourceManager.TEXTURE_TERRAIN);
	}
	
	public Terrain(int x, int y, int z)
	{
		super(ResourceManager.OBJECT_TERRAIN,ResourceManager.TEXTURE_TERRAIN,x,y,z);
	}
}
