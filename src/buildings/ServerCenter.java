package buildings;

import objects.Building;
import objects.Buildings;
import objects.Upgrade;

public class ServerCenter extends Building {

	public ServerCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//ServercenterServers upgrade gives +30% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.ServercenterServers)?1.3:1)));
	}
	
}
