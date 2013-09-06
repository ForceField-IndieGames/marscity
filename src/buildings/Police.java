package buildings;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

public class Police extends Building {

	public Police(int bt, float x, float y, float z, float rY)
	{
		super(bt,x,y,z,rY);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//PoliceStaff upgrade gives +40% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.PoliceStaff)?1.4:1)));
		//PoliceVehicles upgrade gives +40% range
		setProducedSupplyRadius((int) (Buildings.getBuildingType(this).getProducedSupplyRadius()*(getUpgrade(Upgrade.PoliceVehicles)?1.4:1)));
	}
	
}
