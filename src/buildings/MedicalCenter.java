package buildings;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

public class MedicalCenter extends Building {

	public MedicalCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	
	@Override
	public void updateUpgrades() {
		super.updateUpgrades();
		//MedicalcenterRooms upgrade gives +40% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.MedicalcenterRooms)?1.4:1)));
		//MedicalcenterVehicles upgrade gives +40% range
		setProducedSupplyRadius((int) (Buildings.getBuildingType(this).getProducedSupplyRadius()*(getUpgrade(Upgrade.MedicalcenterVehicles)?1.4:1)));
	}
	
}
