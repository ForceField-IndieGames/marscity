package buildings;

import game.MonthlyActions;
import game.TransactionCategory;
import objects.Building;
import objects.Buildings;

public class CityCenter extends Building {

	public CityCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}
	
	@Override
	public void monthlyAction() {
		MonthlyActions.addTransaction(-Buildings.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Other);
	}
	
	
	@Override
	public void delete() {
		//This building can't be deleted
	}
	
}
