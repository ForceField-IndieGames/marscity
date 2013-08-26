package game;

import org.newdawn.slick.opengl.Texture;

/**
 * Contains up to 3 LOD textures
 * @author Benedikt Ringlein
 */

public class EntityTexture {

	private Texture[] textures = new Texture[3];
	
	/**
	 * Creates a new empty entity texture set
	 */
	public EntityTexture()
	{
		
	}
	
	/**
	 * Creates an entity texture set with the specified textures (up to 3)
	 * @param tex
	 */
	public EntityTexture(Texture... tex)
	{
		setTextures(tex);
	}
	
	/**
	 * Sets the individual Textures of this texture set
	 * @param tex
	 */
	public void setTextures(Texture... tex)
	{
		for(int i=0;i<=2;i++)
		{
			if(i<tex.length)textures[i] = tex[i];
			else textures[i] = null;
		}
	}
	
	/**
	 * Sets a texture with the specified LOD
	 * @param LOD Level of detail
	 * @param tex Texture
	 */
	public void setTexture(int LOD,Texture tex)
	{
		if(LOD>=0&&LOD<=2)textures[LOD] = tex;
	}
	
	/**
	 * returns the specified LOD texture. When this texture does not exist,
	 * the next best texture is returned. Lower LODs are returned preferrably.
	 * @param LOD
	 * @return
	 */
	public Texture getTexture(int LOD)
	{
		if(textures[LOD]!=null){
			return textures[LOD];
		}else{
			// If correct LoD texture is not available, 
			// try to use a lower LoD texture, or else a higher one
			for(int i=2;i>=0;i--)
			{
				if(textures[i]!=null){
					return textures[i];
				}
			}
		}	
		return null;
	}
	
}
