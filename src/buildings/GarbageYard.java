package buildings;

import game.MonthlyActions;
import game.ResourceManager;
import game.Supply;
import game.TransactionCategory;
import objects.Building;

public class GarbageYard extends Building {

	public GarbageYard(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
		setProducedSupply(Supply.Garbagecollection);
	}

	@Override
	public void MonthlyTransaction() {
		MonthlyActions.addTransaction(-ResourceManager.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Garbage);
	}
	
}
