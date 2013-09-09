package buildings;

import animation.AnimationManager;
import animation.AnimationValue;
import effects.ParticleEffects;
import game.ResourceManager;
import objects.Building;
import objects.Buildings;
import objects.Entity;
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
		//Add upgrade models
		getChildren().clear();
		Entity vehicles = new Entity(ResourceManager.OBJECT_POLICEVEHICLES,ResourceManager.TEXTURE_UPGRADEVEHICLES,0,0,0);
		if(getUpgrade(Upgrade.PoliceVehicles))addChild(vehicles);
		
		if(changedupgrade==Upgrade.PoliceVehicles){
			vehicles.setY(-1);
			AnimationManager.animateValue(vehicles, AnimationValue.Y, 0, 500);
			ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
			ParticleEffects.dustEffect(getX(), 0, getZ());
		}
	}
	
}
