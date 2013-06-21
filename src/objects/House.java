package objects;

import game.BuildingTask;
import game.Main;
import game.MonthlyTransactions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;

/**
 * This is a house.
 * @author Benedikt Ringlein
 */

public class House extends Building {
	
	private byte citizens = 0;
	private static final byte citizensMax = 15;
	private Timer tCitizens = new Timer();

	public Timer gettCitizens() {
		return tCitizens;
	}

	public void settCitizens(Timer tCitizens) {
		this.tCitizens = tCitizens;
	}

	public House(int bt, float x, float y, float z) {
		super(bt,x,y,z);
		tCitizens.scheduleAtFixedRate(new BuildingTask(this) {
			@Override
			public void task() {
				if(((House) getBuilding()).getCitizens()<House.getCitizensmax()){
					Main.citizens++;
					((House) getBuilding()).setCitizens((byte) (((House) getBuilding()).getCitizens()+1));
				}
				else cancel();
			}
		}, 0, 500);
	}
	
	@Override
	public void update(int delta) {
	}
	
	@Override
	public void MonthlyTransaction() {
		MonthlyTransactions.addTransaction((int) (citizens*((float)Main.taxes/100)), MonthlyTransactions.CATEGORY_TAXES);
	}
	
	@Override
	public void saveToStream(ObjectOutputStream o) throws IOException {
		o.writeByte(citizens);
	}
	
	@Override
	public void loadFromStream(ObjectInputStream i) throws IOException {
		citizens = i.readByte();
	}

	
	@Override
	public void delete() {
		Main.citizens-=getCitizens();
		tCitizens.cancel();
		super.delete();
	}

	public byte getCitizens() {
		return citizens;
	}

	public void setCitizens(byte citizens) {
		this.citizens = citizens;
	}

	public static byte getCitizensmax() {
		return citizensMax;
	}

}
