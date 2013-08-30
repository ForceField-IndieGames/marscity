package objects;

import java.awt.Color;

import game.EntityTexture;
import game.Supply;
import game.TransactionCategory;

import org.newdawn.slick.opengl.Texture;

/**
 * The buildings type represents a type of building, e.g. House, Street, etc. It
 * not only contains information about model and texture but also about the
 * price, size and the height that is best suited when the camera looks at it
 * when selected.
 * 
 * @author Benedikt Ringlein
 */

public class BuildingType {

	private String name;
	private int[] displaylist;
	private EntityTexture texture;
	private int buidlingcost;
	private int monthlycost;
	private int width, depth;
	private float height;
	private int happinessEffect;
	private int happinessRadius;
	private Texture thumb;
	private Supply producedSupply;
	private int producedSupplyAmount;
	private int producedSupplyRadius;
	private TransactionCategory transactioncategory;
	private int[] neededSupplies;
	private Color gridColor;

	/**
	 * Creates a new BuildingType
	 * 
	 * @param name
	 *            The buildings name (as in the language file)
	 * @param displaylist
	 *            The displaylist with the 3d object data
	 * @param texture
	 *            The texture to draw on this building
	 * @param buidlingcost
	 *            Costs for building this building.
	 * @param width
	 *            The buildings width, in grid cells
	 * @param depth
	 *            The buildings depth, in grid cells
	 * @param height
	 *            The buildings height
	 */
	public BuildingType(String name, int[] displaylist, EntityTexture texture,
			Texture thumb, Color gridColor, int buidlingcost, int monthlycost, int width,
			int depth, float height, int happinessEffect, int happinessRadius, TransactionCategory transactioncategory, Supply producedSupply,
			int producedSupplyAmount,int producedSupplyRadius, int... neededSupplies) {
		this.name = name;
		this.displaylist = displaylist;
		this.texture = texture;
		this.setThumb(thumb);
		this.gridColor = gridColor;
		this.buidlingcost = buidlingcost;
		this.monthlycost = monthlycost;
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.happinessEffect = happinessEffect;
		this.happinessRadius = happinessRadius;
		this.transactioncategory = transactioncategory;
		this.setProducedSupply(producedSupply);
		this.producedSupplyAmount = producedSupplyAmount;
		this.producedSupplyRadius = producedSupplyRadius;
		this.neededSupplies = neededSupplies;
	}

	public int getHappinessEffect() {
		return happinessEffect;
	}

	public int getHappinessRadius() {
		return happinessRadius;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public int getProducedSupplyAmount() {
		return producedSupplyAmount;
	}

	public int getNeededSupplies(Supply supply) {
		return neededSupplies[supply.ordinal()];
	}

	public float getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public int[] getDisplaylist() {
		return displaylist;
	}

	public EntityTexture getTexture() {
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

	public Texture getThumb() {
		return thumb;
	}

	public void setThumb(Texture thumb) {
		this.thumb = thumb;
	}

	public int getMonthlycost() {
		return monthlycost;
	}

	public int getProducedSupplyRadius() {
		return producedSupplyRadius;
	}

	public void setProducedSupplyRadius(int producedSupplyRadius) {
		this.producedSupplyRadius = producedSupplyRadius;
	}

	public Supply getProducedSupply() {
		return producedSupply;
	}

	public void setProducedSupply(Supply producedSupply) {
		this.producedSupply = producedSupply;
	}

	public TransactionCategory getTransactioncategory() {
		return transactioncategory;
	}

	public void setTransactioncategory(TransactionCategory transactioncategory) {
		this.transactioncategory = transactioncategory;
	}

}
