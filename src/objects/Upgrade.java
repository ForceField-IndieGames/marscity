package objects;

import game.ResourceManager;

import org.newdawn.slick.opengl.Texture;

/**
 * An enum thhat contains the building upgrades
 * @author Benedikt Ringlein
 */

public enum Upgrade {

	MedicalcenterRooms(ResourceManager.TEXTURE_GUITHUMBMEDICALCENTERROOMS,Buildings.BUILDINGTYPE_MEDICALCENTER,1000,4),
	MedicalcenterVehicles(ResourceManager.TEXTURE_GUITHUMBUPGRADEVEHICLES,Buildings.BUILDINGTYPE_MEDICALCENTER,750,3),
	PoliceStaff(ResourceManager.TEXTURE_GUITHUMBPOLICESTAFF,Buildings.BUILDINGTYPE_POLICE,500,5),
	PoliceVehicles(ResourceManager.TEXTURE_GUITHUMBUPGRADEVEHICLES,Buildings.BUILDINGTYPE_POLICE,750,3),
	SolarpowerPanel(ResourceManager.TEXTURE_GUITHUMBSOLARPOWERPANELS,Buildings.BUILDINGTYPE_SOLARPOWER,1000,3),
	SolarpowerCoating(ResourceManager.TEXTURE_GUITHUMBSOLARPOWERCOATING,Buildings.BUILDINGTYPE_SOLARPOWER,5000,-2),
	GarbageyardBurner(ResourceManager.TEXTURE_GUITHUMBGARBAGEYARDBURNER,Buildings.BUILDINGTYPE_GARBAGEYARD,1000,3),
	GarbageyardVehicles(ResourceManager.TEXTURE_GUITHUMBUPGRADEVEHICLES,Buildings.BUILDINGTYPE_GARBAGEYARD,750,3),
	ServercenterServers(ResourceManager.TEXTURE_GUITHUMBSERVERCENTERSERVERS,Buildings.BUILDINGTYPE_SERVERCENTER,900,2),
	CitycenterShield(ResourceManager.TEXTURE_GUITHUMBCITYCENTERSHIELD,Buildings.BUILDINGTYPE_CITYCENTER,15000,0),
	CitycenterShield2(ResourceManager.TEXTURE_GUITHUMBCITYCENTERSHIELD,Buildings.BUILDINGTYPE_CITYCENTER,50000,0,CitycenterShield);
	
	private Texture image;
	private int bt;
	private Upgrade[] neededupgrades;
	private int upgradecost;
	private int monthlyupgradecost;
	
	Upgrade(Texture image, int bt, int upgradecost, int monthlyupgradecost, Upgrade... neededupgrades)
	{
		setImage(image);
		setBt(bt);
		setNeededupgrades(neededupgrades);
		setMonthlyupgradecost(monthlyupgradecost);
		setUpgradecost(upgradecost);
	}
	
	Upgrade(Texture image, int bt, int upgradecost, int monthlyupgradecost)
	{
		setImage(image);
		setBt(bt);
		setMonthlyupgradecost(monthlyupgradecost);
		setUpgradecost(upgradecost);
	}
	
	public Texture getImage() {
		return image;
	}
	public void setImage(Texture image) {
		this.image = image;
	}
	public int getBt() {
		return bt;
	}
	public void setBt(int bt) {
		this.bt = bt;
	}
	public Upgrade[] getNeededupgrades() {
		return neededupgrades;
	}
	public void setNeededupgrades(Upgrade[] neededupgrades) {
		this.neededupgrades = neededupgrades;
	}

	public int getUpgradecost() {
		return upgradecost;
	}

	public void setUpgradecost(int upgradecost) {
		this.upgradecost = upgradecost;
	}

	public int getMonthlyupgradecost() {
		return monthlyupgradecost;
	}

	public void setMonthlyupgradecost(int monthlyupgradecost) {
		this.monthlyupgradecost = monthlyupgradecost;
	}
	
}
