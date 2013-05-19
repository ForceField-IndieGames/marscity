package game;

import objects.Building;

public class GridCell {
	private Building building;


	public Building getBuilding() {
		return building;
	}


	public void setBuilding(Building building) {
		this.building = building;
	}


	public GridCell(Building building)
	{
		this.building = building;
	}
}