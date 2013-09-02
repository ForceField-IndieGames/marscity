package buildings;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

public class GarbageYard extends Building {

	public GarbageYard(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//GarbageyardBurner upgrade gives +40% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.GarbageyardBurner)?1.4:1)));
		//GarbageyardVehicles upgrade gives +30% range
		setProducedSupplyRadius((int) (Buildings.getBuildingType(this).getProducedSupplyRadius()*(getUpgrade(Upgrade.GarbageyardVehicles)?1.3:1)));
	}
	
}
