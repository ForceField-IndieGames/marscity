package game;

import java.util.TimerTask;

import objects.Building;

public class BuildingTask extends TimerTask {
	
	private Building building;
	
	public BuildingTask(Building building)
	{
		this.setBuilding(building);
	}

	@Override
	public void run() {
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

}
