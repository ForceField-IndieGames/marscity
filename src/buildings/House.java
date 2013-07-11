package buildings;

import game.Main;
import game.MonthlyActions;
import game.TransactionCategory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import objects.Building;

/**
 * This is a house.
 * @author Benedikt Ringlein
 */

public class House extends Building {
	
	private int citizens = 0;
	private static final byte citizensMax = 20;
	
	private static final int CITIZENSPERMONTH = 5;
	
	public House(int bt, float x, float y, float z) {
		super(bt,x,y,z);
	}
	
	@Override
	public void update(int delta) {
	}
	
	@Override
	public void monthlyAction() {
		super.monthlyAction();
		MonthlyActions.addTransaction((int) (citizens*((float)Main.taxes/100)), TransactionCategory.Taxes);
		if(getCitizens()<getCitizensmax()*(getHappiness()/100f)){
			if(getCitizens()+CITIZENSPERMONTH<=getCitizensmax()*(getHappiness()/100f)){
				setCitizens(getCitizens()+CITIZENSPERMONTH);
				Main.citizens += CITIZENSPERMONTH;
			}
			else {
				Main.citizens += (getCitizensmax()*(getHappiness()/100f))-getCitizens();
				setCitizens((int) (getCitizensmax()*(getHappiness()/100f)));
			}
		}else if(getCitizens()>getCitizensmax()*(getHappiness()/100f)){
			if(getCitizens()-CITIZENSPERMONTH>=0){
				setCitizens(getCitizens()-CITIZENSPERMONTH);
				Main.citizens -= CITIZENSPERMONTH;
			}
			else {
				Main.citizens -= getCitizens();
				setCitizens(0);
			}
		}
	}
	
	@Override
	public void saveToStream(ObjectOutputStream o) throws IOException {
		o.writeInt(citizens);
	}
	
	@Override
	public void loadFromStream(ObjectInputStream i) throws IOException {
		citizens = i.readInt();
	}

	
	@Override
	public void delete() {
		Main.citizens-=getCitizens();
		super.delete();
	}

	public int getCitizens() {
		return citizens;
	}

	public void setCitizens(int citizens) {
		this.citizens = citizens;
	}

	public static byte getCitizensmax() {
		return citizensMax;
	}

}
