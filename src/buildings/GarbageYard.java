package buildings;

import game.MonthlyActions;
import game.Supply;
import game.TransactionCategory;
import objects.Building;
import objects.Buildings;

public class GarbageYard extends Building {

	public GarbageYard(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
		setProducedSupply(Supply.Garbagecollection);
	}

	@Override
	public void monthlyAction() {
		MonthlyActions.addTransaction(-Buildings.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Garbage);
	}
	
}
