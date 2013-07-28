package game;

import java.util.TimerTask;

import objects.Building;

/**
 * This is a timer task that can access building information.
 * It is used to update building specific things, like inhabitants.
 * @author Benedikt Ringlein
 */

public class BuildingTask extends TimerTask {
	
	private Building building;
	
	public BuildingTask(Building building)
	{
		this.setBuilding(building);
	}

	@Override
	public void run() {
		if(Main.gameState==Main.STATE_GAME){
			if(!Game.isPaused())task();
		}else cancel();
		
	}
	
	public void task()
	{
		
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

}
