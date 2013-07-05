package buildings;

import game.MonthlyActions;
import game.TransactionCategory;
import objects.Building;
import objects.Buildings;

public class ResearchStation extends Building {

	public ResearchStation(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	@Override
	public void MonthlyTransaction() {
		MonthlyActions.addTransaction(-Buildings.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Other);
	}
	
}
