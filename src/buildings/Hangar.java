package buildings;

import game.MonthlyActions;
import game.ResourceManager;
import game.TransactionCategory;
import objects.Building;

public class Hangar extends Building {

	public Hangar(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
	}

	@Override
	public void MonthlyTransaction() {
		MonthlyActions.addTransaction(-ResourceManager.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Other);
	}
	
}
