package objects;

import game.ResourceManager;

import org.newdawn.slick.opengl.Texture;

/**
 * An enum thhat contains the building upgrades
 * @author Benedikt Ringlein
 */

public enum Upgrade {

	MedicalcenterRooms(ResourceManager.TEXTURE_GUITHUMBMEDICALCENTER,Buildings.BUILDINGTYPE_MEDICALCENTER,1000,4),
	MedicalcenterVehicles(ResourceManager.TEXTURE_GUITHUMBMEDICALCENTER,Buildings.BUILDINGTYPE_MEDICALCENTER,750,3),
	PoliceStaff(ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,Buildings.BUILDINGTYPE_POLICE,500,5),
	PoliceVehicles(ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,Buildings.BUILDINGTYPE_POLICE,750,3),
	SolarpowerPanel(ResourceManager.TEXTURE_GUITHUMBSOLARPOWER,Buildings.BUILDINGTYPE_SOLARPOWER,1000,3),
	SolarpowerCoating(ResourceManager.TEXTURE_GUITHUMBSOLARPOWER,Buildings.BUILDINGTYPE_SOLARPOWER,5000,-2);
	
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