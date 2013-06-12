package objects;

import game.BuildingTask;
import game.Main;

import java.util.Timer;

public class BigHouse extends Building {

	private int citizens = 0;
	private static final byte citizensMax = 100;
	private Timer tCitizens = new Timer();

	public Timer gettCitizens() {
		return tCitizens;
	}

	public void settCitizens(Timer tCitizens) {
		this.tCitizens = tCitizens;
	}

	public BigHouse(int bt, float x, float y, float z) {
		super(bt,x,y,z);
		tCitizens.scheduleAtFixedRate(new BuildingTask(this) {
			@Override
			public void run() {
				if(((BigHouse) getBuilding()).getCitizens()<BigHouse.getCitizensmax()){
					Main.citizens++;
					((BigHouse) getBuilding()).setCitizens(((BigHouse) getBuilding()).getCitizens()+1);
				}
				else cancel();
			}
		}, 0, 200);
	}
	
	@Override
	public void update(int delta) {
	}
	
	@Override
	public void click() {
		
	}
	
	@Override
	public void delete() {
		Main.citizens-=getCitizens();
		tCitizens.cancel();
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
