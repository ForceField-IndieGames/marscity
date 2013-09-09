package buildings;

import game.Main;
import objects.Building;
import objects.Upgrade;

public class CityCenter extends Building {
	
	private int buildRadius=200;

	public CityCenter(int bt, float x, float y, float z, float rY)
	{
		super(bt,x,y,z,rY);
		Main.BuildingCitycenter = this;
	}
	
	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//CitycenterShield upgrade gives 40% more buildradius
		setBuildRadius((int) (200*((getUpgrade(Upgrade.CitycenterShield))?1.4:1)));
		//CitycenterShield2 upgrade gives 50% more buildradius
		setBuildRadius((int) (getBuildRadius()*((getUpgrade(Upgrade.CitycenterShield2))?1.5:1)));
		Main.shield.setScaleX(getBuildRadius());
		Main.shield.setScaleY(getBuildRadius());
		Main.shield.setScaleZ(getBuildRadius());
	}
	
	
	@Override
	public void delete() {
		//This building can't be deleted
	}



	public int getBuildRadius() {
		return buildRadius;
	}



	public void setBuildRadius(int buildRadius) {
		this.buildRadius = buildRadius;
	}
}
