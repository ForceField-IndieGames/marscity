package buildings;

import animation.AnimationManager;
import animation.AnimationValue;
import effects.ParticleEffects;
import game.ResourceManager;
import objects.Building;
import objects.Buildings;
import objects.Entity;
import objects.Upgrade;

public class ServerCenter extends Building {

	public ServerCenter(int bt, float x, float y, float z, float rY)
	{
		super(bt,x,y,z,rY);
	}

	@Override
	public void updateUpgrades(Upgrade changedupgrade) {
		super.updateUpgrades(changedupgrade);
		//ServercenterServers upgrade gives +30% supply
		setProducedSupplyAmount((int) (Buildings.getBuildingType(this).getProducedSupplyAmount()*(getUpgrade(Upgrade.ServercenterServers)?1.3:1)));
		//Add upgrade models
		getChildren().clear();
		Entity servers = new Entity(ResourceManager.OBJECT_SERVERCENTERSERVERS,ResourceManager.TEXTURE_SERVERCENTER,0,0,0);
		if(getUpgrade(Upgrade.ServercenterServers))addChild(servers);
		
		if(changedupgrade==Upgrade.ServercenterServers){
			servers.setY(-1);
			AnimationManager.animateValue(servers, AnimationValue.Y, 0, 500);
			ResourceManager.playSoundRandom(ResourceManager.SOUND_DROP);
			ParticleEffects.dustEffect(getX(), 0, getZ());
		}
	}
	
}
