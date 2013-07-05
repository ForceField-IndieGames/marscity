package buildings;

import game.MonthlyActions;
import game.ResourceManager;
import game.Supply;
import game.TransactionCategory;
import objects.Building;

public class MedicalCenter extends Building {

	public MedicalCenter(int bt, float x, float y, float z)
	{
		super(bt,x,y,z);
		setProducedSupply(Supply.Health);
	}

	@Override
	public void MonthlyTransaction() {
		MonthlyActions.addTransaction(-ResourceManager.getBuildingType(getBuildingType()).getMonthlycost(), TransactionCategory.Health);
	}
	
}
