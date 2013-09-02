package buildings;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

public class SolarPower extends Building {

	public SolarPower(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//SolarpowerPanel upgrade gives +30% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.SolarpowerPanel)?1.3:1)));
	}
	
}
