package buildings;

import animation.AnimationManager;
import animation.AnimationValue;
import effects.ParticleEffects;
import game.ResourceManager;
import objects.Building;
import objects.Buildings;
import objects.Entity;
import objects.Upgrade;

public class GarbageYard extends Building {

	public GarbageYard(int bt, float x, float y, float z, float rY)
	{
		super(bt,x,y,z,rY);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//GarbageyardBurner upgrade gives +40% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.GarbageyardBurner)?1.4:1)));
		//GarbageyardVehicles upgrade gives +30% range
		setProducedSupplyRadius((int) (Buildings.getBuildingType(this).getProducedSupplyRadius()*(getUpgrade(Upgrade.GarbageyardVehicles)?1.3:1)));
		//Add upgrade models
		getChildren().clear();
		Entity burner = new Entity(ResourceManager.OBJECT_GARBAGEYARDBURNER,ResourceManager.TEXTURE_GARBAGEYARD,0,0,0);
		Entity vehicles = new Entity(ResourceManager.OBJECT_GARBAGEYARDVEHICLES,ResourceManager.TEXTURE_UPGRADEVEHICLES,0,0,0);
		if(getUpgrade(Upgrade.GarbageyardBurner))addChild(burner);
		if(getUpgrade(Upgrade.GarbageyardVehicles))addChild(vehicles);
		
		if(changedupgrade==Upgrade.GarbageyardBurner){
			burner.setY(-1.5f);
			AnimationManager.animateValue(burner, AnimationValue.Y, 0, 500);
			ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
			ParticleEffects.dustEffect(getX(), 0, getZ());
		}
		if(changedupgrade==Upgrade.GarbageyardVehicles){
			vehicles.setY(-1);
			AnimationManager.animateValue(vehicles, AnimationValue.Y, 0, 500);
			ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
			ParticleEffects.dustEffect(getX(), 0, getZ());
		}
	}
	
}
