package buildings;

import game.Main;
import game.MonthlyActions;
import game.TransactionCategory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import objects.Building;

/**
 * This is a big house.
 * @author Benedikt Ringlein
 */

public class BigHouse extends Building {

	private int citizens = 0;
	private static final byte citizensMax = 100;
	
	private static final int CITIZENSPERMONTH = 20;

	public BigHouse(int bt, float x, float y, float z, float rY) {
		super(bt,x,y,z,rY);
		setHasHappiness(true);
	}
	
	@Override
	public void update(int delta) {
	}
	
	@Override
	public void monthlyAction() {
		super.monthlyAction();
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
		MonthlyActions.addTransaction((int) (citizens*((float)Main.taxes/100)), TransactionCategory.Taxes);
	}
	
	@Override
	public void saveToStream(ObjectOutputStream o) throws IOException {
		super.saveToStream(o);
		o.writeByte(citizens);
	}
	
	@Override
	public void loadFromStream(ObjectInputStream i) throws IOException {
		super.loadFromStream(i);
		citizens = i.readByte();
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
