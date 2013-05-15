package objects;

import org.newdawn.slick.opengl.Texture;

public class BuildingType {

	private String name;
	private int displaylist;
	private Texture texture;
	private int buidlingcost;
	private int width, depth;
	private float preferredY;
	
	
/**
	 * Creates a new BuildingType
	 * @param name The buildings name (as in the language file)
	 * @param displaylist The displaylist with the 3d object data
	 * @param texture The texture to draw on this building
	 * @param buidlingcost Costs for building this building.
	 * @param width The buildings width, in grid cells
	 * @param depth The buildings depth, in grid cells
	 */
		public BuildingType(String name, 
							int displaylist, 
							Texture texture, 
							int buidlingcost, 
							int width, 
							int depth,
							float preferredY) {
			this.name = name;
			this.displaylist = displaylist;
			this.texture = texture;
			this.buidlingcost = buidlingcost;
			this.width = width;
			this.depth = depth;
			this.preferredY = preferredY;
		}

public float getPreferredY() {
		return preferredY;
	}

public String getName() {
		return name;
	}

	public int getDisplaylist() {
		return displaylist;
	}
	public Texture getTexture() {
		return texture;
	}
	public int getBuidlingcost() {
		return buidlingcost;
	}
	public int getWidth() {
		return width;
	}
	public int getDepth() {
		return depth;
	}
	
}
