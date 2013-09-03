package buildings;

import animation.AnimationManager;
import animation.AnimationValue;
import effects.ParticleEffects;
import game.ResourceManager;
import objects.Building;
import objects.Buildings;
import objects.Entity;
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
		//Add upgrade models
				getChildren().clear();
				Entity panels = new Entity(ResourceManager.OBJECT_SOLARPOWERPANELS,ResourceManager.TEXTURE_SOLARPOWER,0,0,0);
				if(getUpgrade(Upgrade.SolarpowerPanel))addChild(panels);
				
				if(changedupgrade==Upgrade.SolarpowerPanel){
					panels.setY(-0.5f);
					AnimationManager.animateValue(panels, AnimationValue.Y, 0, 500);
					ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
					ParticleEffects.dustEffect(getX(), 0, getZ());
				}
	}
	
}
