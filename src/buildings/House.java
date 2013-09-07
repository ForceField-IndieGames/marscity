package buildings;

import game.Main;
import game.MonthlyActions;
import game.Statistics;
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
	
	public House(int bt, float x, float y, float z, float rY) {
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
				Statistics.citizens += CITIZENSPERMONTH;
				Statistics.CitizensHouseMax += CITIZENSPERMONTH;
				Statistics.CitizensHouseCurrent += CITIZENSPERMONTH;
			}
			else {
				Statistics.citizens += (getCitizensmax()*(getHappiness()/100f))-getCitizens();
				Statistics.CitizensHouseMax += (getCitizensmax()*(getHappiness()/100f))-getCitizens();
				Statistics.CitizensHouseCurrent += (getCitizensmax()*(getHappiness()/100f))-getCitizens();
				setCitizens((int) (getCitizensmax()*(getHappiness()/100f)));
			}
		}else if(getCitizens()>getCitizensmax()*(getHappiness()/100f)){
			if(getCitizens()-CITIZENSPERMONTH>=0){
				setCitizens(getCitizens()-CITIZENSPERMONTH);
				Statistics.citizens -= CITIZENSPERMONTH;
				Statistics.CitizensHouseCurrent -= CITIZENSPERMONTH;
			}
			else {
				Statistics.citizens -= getCitizens();
				Statistics.CitizensHouseCurrent -= getCitizens();
				setCitizens(0);
			}
		}
		MonthlyActions.addTransaction((int) (citizens*((float)Main.taxes/100)), TransactionCategory.Taxes);
	}
	
	@Override
	public void saveToStream(ObjectOutputStream o) throws IOException {
		super.saveToStream(o);
		o.writeInt(citizens);
	}
	
	@Override
	public void loadFromStream(ObjectInputStream i) throws IOException {
		super.loadFromStream(i);
		citizens = i.readInt();
	}

	
	@Override
	public void delete() {
		Statistics.citizens-=getCitizens();
		Statistics.CitizensHouseCurrent-=getCitizens();
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
