package buildings;

import effects.ParticleEffects;
import animation.AnimationManager;
import animation.AnimationValue;
import game.ResourceManager;
import objects.Building;
import objects.Buildings;
import objects.Entity;
import objects.Upgrade;

public class MedicalCenter extends Building {

	public MedicalCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	
	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//MedicalcenterRooms upgrade gives +40% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.MedicalcenterRooms)?1.4:1)));
		//MedicalcenterVehicles upgrade gives +40% range
		setProducedSupplyRadius((int) (Buildings.getBuildingType(this).getProducedSupplyRadius()*(getUpgrade(Upgrade.MedicalcenterVehicles)?1.4:1)));
		//Add upgrade models
		if(changedupgrade==Upgrade.MedicalcenterRooms){
			Entity ent = new Entity(ResourceManager.OBJECT_MEDICALCENTERROOMS,ResourceManager.TEXTURE_MEDICALCENTERROOMS,0,2,0);
			AnimationManager.animateValue(ent, AnimationValue.Y, 0, 100);
			ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
			ParticleEffects.dustEffect(getX(), 0, getZ());
			addChild(ent);
		}
		
	}
	
}
