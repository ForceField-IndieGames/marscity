package buildings;

import game.MonthlyActions;
import game.ResourceManager;
import game.Supply;
import game.TransactionCategory;
import objects.Building;

public class Police extends Building {

	public Police(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
		setProducedSupply(Supply.Security);
	}

	@Override
	public void MonthlyTransaction() {
		MonthlyActions.addTransaction(-ResourceManager.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Police);
	}
	
}
