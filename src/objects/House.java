package objects;

import game.BuildingTask;
import game.Main;

import java.util.Timer;


public class House extends Building {
	
	private int citizens = 0;
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
			public void run() {
				if(((House) getBuilding()).getCitizens()<House.getCitizensmax()){
					Main.citizens++;
					((House) getBuilding()).setCitizens(((House) getBuilding()).getCitizens()+1);
				}
				else cancel();
			}
		}, 0, 500);
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
