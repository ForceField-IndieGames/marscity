package objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import game.Main;
import game.Supply;
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
	private int[] supply = new int[Supply.values().length];
	private Supply producedSupply = null;
	private int producedSupplyAmount = 0;
	private int[] neededSupplyAmount = new int[Supply.values().length];
	private int[] ownedSupplyAmount = new int[Supply.values().length];
	private byte happiness = 0;  
	private boolean hasHappiness = false;
	
	public Building(){}

	public Building(int bt)
	{
		super(Buildings.getBuildingType(bt).getDisplaylist(), Buildings.getBuildingType(bt).getTexture());
		height = Buildings.getBuildingType(bt).getHeight();
		this.buidlingType = bt;
		for(Supply supply:Supply.values())
		{
			setNeededSupplyAmount(Buildings.getBuildingType(this).getNeededSupplies(supply), supply);
		}	
		setProducedSupplyAmount(Buildings.getBuildingType(this).getProducedSupplyAmount());
	}

	public Building(int bt, float x, float y, float z)
	{
		super(Buildings.getBuildingType(bt).getDisplaylist(), Buildings.getBuildingType(bt).getTexture(),x,y,z);
		height = Buildings.getBuildingType(bt).getHeight();
		this.buidlingType = bt;
		for(Supply supply:Supply.values())
		{
			setNeededSupplyAmount(Buildings.getBuildingType(this).getNeededSupplies(supply), supply);
		}
		setProducedSupplyAmount(Buildings.getBuildingType(this).getProducedSupplyAmount());
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
	
	public void monthlyAction()
	{
		//Calculate happiness value
		int counter = 0;
		int happiness = 0;
		for(Supply s:Supply.values())
		{
			if(getNeededSupplyAmount(s)>0)
			{
				happiness += (((float)getOwnedSupplyAmount(s))/getNeededSupplyAmount(s))*100;
				counter++;
			}
		}
		setHappiness((byte) ((counter<=0)?0:(happiness/counter)));
	}
	
	/**
	 * Is called when the building is clicked
	 */
	@Override
	public void click()
	{
		Main.gui.buildinginfo.show(this);
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
		AnimationManager.animateValue(this, AnimationValue.ROTX, (float) (getRotX()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.ROTY, (float) (getRotY()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.ROTZ, (float) (getRotZ()-10+Math.random()*20), 1000);
	}
	
	public void setSupply(int value, Supply supply)
	{
		this.supply[supply.ordinal()] = value;
	}
	
	public int getSupply(Supply supply)
	{
		return this.supply[supply.ordinal()];
	}

	public Supply getProducedSupply() {
		return producedSupply;
	}

	public void setProducedSupply(Supply producedSupply) {
		this.producedSupply = producedSupply;
	}

	public int getProducedSupplyAmount() {
		return producedSupplyAmount;
	}

	public void setProducedSupplyAmount(int producedSupplyAmount) {
		this.producedSupplyAmount = producedSupplyAmount;
	}

	public int getNeededSupplyAmount(Supply supply) {
		return neededSupplyAmount[supply.ordinal()];
	}

	public void setNeededSupplyAmount(int neededSupplyAmount, Supply supply) {
		this.neededSupplyAmount[supply.ordinal()] = neededSupplyAmount;
	}

	public int getOwnedSupplyAmount(Supply supply) {
		return ownedSupplyAmount[supply.ordinal()];
	}

	public void setOwnedSupplyAmount(int ownedSupplyAmount, Supply supply) {
		this.ownedSupplyAmount[supply.ordinal()] = ownedSupplyAmount;
	}

	public byte getHappiness() {
		return happiness;
	}

	public void setHappiness(byte happiness) {
		this.happiness = happiness;
	}

	public boolean isHasHappiness() {
		return hasHappiness;
	}

	public void setHasHappiness(boolean hasHappiness) {
		this.hasHappiness = hasHappiness;
	}
	
}
