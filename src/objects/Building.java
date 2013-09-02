package objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import game.Grid;
import game.Main;
import game.MonthlyActions;
import game.Supply;
import animation.AnimationManager;
import animation.AnimationValue;
import animation.FinishedAction;

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
	private int producedSupplyRadius = 0;
	private int[] neededSupplyAmount = new int[Supply.values().length];
	private int[] ownedSupplyAmount = new int[Supply.values().length];
	private byte happiness = 0;  
	private boolean hasHappiness = false;
	private int happinessEffect;
	private int happinessRadius;
	private int monthlycost;
	private HashMap<Upgrade,Boolean> upgrades = new HashMap<Upgrade,Boolean>();

	public Building(int bt, float x, float y, float z)
	{
		super(Buildings.getBuildingType(bt).getDisplaylist(), Buildings.getBuildingType(bt).getTexture(),x,y,z);
		height = Buildings.getBuildingType(bt).getHeight();
		this.buidlingType = bt;
		this.setMonthlycost(Buildings.getBuildingType(this).getMonthlycost());
		setProducedSupply(Buildings.getBuildingType(this).getProducedSupply());
		for(Supply supply:Supply.values())
		{
			setNeededSupplyAmount(Buildings.getBuildingType(this).getNeededSupplies(supply), supply);
		}
		setProducedSupplyAmount(Buildings.getBuildingType(this).getProducedSupplyAmount());
		//set happiness effect and radius
		setHappinessEffect(Buildings.getBuildingType(this).getHappinessEffect());
		setHappinessRadius(Buildings.getBuildingType(this).getHappinessRadius());
		//update happinessEffect on the grid:
		for(int i=(int) (getZ()-getHappinessRadius());i<=getZ()+getHappinessRadius();i++){
			for(int j=(int) (getX()-getHappinessRadius());j<=getX()+getHappinessRadius();j++){
				try {
					double val = (1-Math.sqrt((getX()-j)*(getX()-j)+(getZ()-i)*(getZ()-i))/getHappinessRadius())*getHappinessEffect();
					Grid.getCell(j, i).setHappinessEffect((byte) (Grid.getCell(j, i).getHappinessEffect()+val));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		setProducedSupplyRadius(Buildings.getBuildingType(this).getProducedSupplyRadius());
		//Load upgrades that belong to this building
		for(Upgrade u:Upgrade.values())
		{
			if(u.getBt()==bt)upgrades.put(u, false);
		}
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
		calculateHappiness();
		//Apply transactions
		if(Buildings.getBuildingType(this).getTransactioncategory()!=null)MonthlyActions.addTransaction(-getMonthlycost(), Buildings.getBuildingType(this).getTransactioncategory());
	}
	
	public void calculateHappiness()
	{
		if(isHasHappiness())
		{
			int happiness = 0;
			int count = 0;
			for(Supply s:Supply.values())
			{
				if(getNeededSupplyAmount(s)>0)
				{
					happiness += (((float)getOwnedSupplyAmount(s))/getNeededSupplyAmount(s))*100;
					count ++;
				}
			}
			happiness /= count;
			//Max happiness tha can be achieved thru supplies
			happiness *= 0.8; 
			/*Taxes over 20 reduce happiness by 5 each percent. Taxes under 20
			add 1 to the happines each percent */
			happiness += ((Main.taxes>20)?5:1.5)*(20-Main.taxes);
			//The happnessEffect on the grid effects the happiness
			happiness += Grid.getCell((int)getX(), (int)getZ()).getHappinessEffect();
			setHappiness((byte) (happiness));
		}
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
		if(hasUpgrades())
		{
			for(Upgrade u:Upgrade.values())
			{
				if(u.getBt()==getBuildingType())
				{
					upgrades.put(u,i.readBoolean());
				}
			}
			updateUpgrades(null);
		}
	}
	
	/**
	 * Buildings that have to save special data can override this method
	 * @param o The ObjectOutputStream to write to
	 * @throws IOException 
	 */
	public void saveToStream(ObjectOutputStream o) throws IOException
	{
		if(hasUpgrades())
		{
			for(Upgrade u:Upgrade.values())
			{
				if(u.getBt()==getBuildingType())
				{
					o.writeBoolean(upgrades.get(u));
				}
			}
		}
	}
	
	/**
	 * Deletes the buliding with an animaiton
	 */
	@Override
	public void delete()
	{
		AnimationManager.animateValue(this, AnimationValue.Y, getY()-getHeight(), 1000, FinishedAction.DELETE);
		AnimationManager.animateValue(this, AnimationValue.ROTX, (float) (getRotX()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.ROTY, (float) (getRotY()-10+Math.random()*20), 1000);
		AnimationManager.animateValue(this, AnimationValue.ROTZ, (float) (getRotZ()-10+Math.random()*20), 1000);
		//update happinessEffect on the grid:
		for(int i=(int) (getZ()-getHappinessRadius());i<=getZ()+getHappinessRadius();i++){
			for(int j=(int) (getX()-getHappinessRadius());j<=getX()+getHappinessRadius();j++){
				try {
					double dist = Math.sqrt((getX()-j)*(getX()-j)+(getZ()-i)*(getZ()-i));
					double val = (1-dist/getHappinessRadius())*getHappinessEffect();
					if(Math.abs(dist)<=getHappinessRadius())Grid.getCell(j, i).setHappinessEffect((byte) (Grid.getCell(j, i).getHappinessEffect()-val));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
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
		this.happiness = (happiness<0)?0:((happiness>100)?100:happiness);
	}

	public boolean isHasHappiness() {
		return hasHappiness;
	}

	public void setHasHappiness(boolean hasHappiness) {
		this.hasHappiness = hasHappiness;
	}

	public int getHappinessEffect() {
		return happinessEffect;
	}

	public void setHappinessEffect(int happinessEffect) {
		this.happinessEffect = happinessEffect;
	}

	public int getHappinessRadius() {
		return happinessRadius;
	}

	public void setHappinessRadius(int happinessRadius) {
		this.happinessRadius = happinessRadius;
	}

	public int getProducedSupplyRadius() {
		return producedSupplyRadius;
	}

	public void setProducedSupplyRadius(int producedSupplyRadius) {
		this.producedSupplyRadius = producedSupplyRadius;
	}

	public boolean getUpgrade(Upgrade upgrade) {
		return upgrades.get(upgrade);
	}

	public void setUpgrade(Upgrade upgrade, boolean value) {
		this.upgrades.put(upgrade, value);
	}
	public boolean hasUpgrades()
	{
		if(upgrades.size()>0)return true;
		else return false;
	}
	
	/**
	 * Is overwritten by the individual buildings.
	 * Applies upgrades to the building's values.
	 */
	public void updateUpgrades(Upgrade changedupgrade)
	{
		monthlycost=Buildings.getBuildingType(this).getMonthlycost();
		for(Upgrade u:Upgrade.values())
		{
			if(u.getBt()==getBuildingType()&&getUpgrade(u))
			{
				monthlycost+=u.getMonthlyupgradecost();
			}
		}
	}

	public int getMonthlycost() {
		return monthlycost;
	}

	public void setMonthlycost(int monthlycost) {
		this.monthlycost = monthlycost;
	}
	
}
