package game;

import objects.Building;

/**
 * GridCells are used in the grid to contain the information needed.
 * @author Benedikt Ringlein
 */

public class GridCell {
	private Building building;
	private byte happinessEffect=0;

	public GridCell(Building building)
	{
		this.building = building;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}


	public int getBuildingType()
	{
		if(building==null)return -1;
		return building.getBuildingType();
	}


	public byte getHappinessEffect() {
		return happinessEffect;
	}


	public void setHappinessEffect(byte happinessEffect) {
		this.happinessEffect = happinessEffect;
	}
}
