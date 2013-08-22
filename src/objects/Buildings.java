package objects;

import game.Grid;
import game.ResourceManager;
import game.Supply;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import buildings.Bank;
import buildings.BigHouse;
import buildings.CityCenter;
import buildings.FusionPower;
import buildings.GarbageYard;
import buildings.Hangar;
import buildings.House;
import buildings.MedicalCenter;
import buildings.Police;
import buildings.ResearchStation;
import buildings.ServerCenter;
import buildings.SolarPower;
import buildings.Street;

public class Buildings{

	public final static List<BuildingType> buildingTypes = new ArrayList<BuildingType>();
	public static List<Building> buildings = new ArrayList<Building>();
	
	//The building types (used when placing buildings, also saving and loading)
	public final static short BUILDINGTYPE_HOUSE = 0;
	public final static short BUILDINGTYPE_BIGHOUSE = 1;
	public final static short BUILDINGTYPE_STREET = 2;
	public final static short BUILDINGTYPE_CITYCENTER = 3;
	public final static short BUILDINGTYPE_RESEARCHSTATION = 4;
	public final static short BUILDINGTYPE_HANGAR = 5;
	public final static short BUILDINGTYPE_BANK = 6;
	public final static short BUILDINGTYPE_MEDICALCENTER = 7;
	public final static short BUILDINGTYPE_SERVERCENTER = 8;
	public final static short BUILDINGTYPE_GARBAGEYARD = 9;
	public final static short BUILDINGTYPE_POLICE = 10;
	public final static short BUILDINGTYPE_SOLARPOWER = 11;
	public final static short BUILDINGTYPE_FUSIONPOWER = 12;
	
	public static boolean updateSupply = false;
	
	static Thread SupplyThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true)
			{
				if(updateSupply){
					updateSupply = false;
					for(Supply s:Supply.values())
					{
						SpreadSupply(s);
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});
	
	public static void init()
	{
		SupplyThread.start();
		SupplyThread.setName("Supply Thread");
		
		//Building Types:           INDEX                                            NAME                                            OBJECT                                   TEXTURE                                       THUMBNAIL                      GRIDCOLOR        BUILDINGCOST  MONTHLYCOST  WIDTH    DEPTH   HEIGHT  HAPPINESSEFFECT  HAPPINESSRADIUS  SUPPLY  NEEDED: ENERGY  HEALTH  GARBAGE  INTERNET  SECURITY                            
				buildingTypes.add(  BUILDINGTYPE_HOUSE,           new BuildingType(  "BUILDINGTYPE_HOUSE",           ResourceManager.OBJECT_HOUSE,            ResourceManager.TEXTURE_HOUSE,                ResourceManager.TEXTURE_GUITHUMBHOUSE,          Color.GREEN,     250,          0,           2,       2,      1.5f,   0,               0,               0,              10,     10,     10,      10,       10       ));
				buildingTypes.add(  BUILDINGTYPE_BIGHOUSE,        new BuildingType(  "BUILDINGTYPE_BIGHOUSE",        ResourceManager.OBJECT_BIGHOUSE,         ResourceManager.TEXTURE_BIGHOUSE,             ResourceManager.TEXTURE_GUITHUMBBIGHOUSE,       Color.GREEN,     1500,         0,           4,       4,      8,      0,               0,               0,              50,     50,     50,      50,       50       ));
				buildingTypes.add(  BUILDINGTYPE_STREET,          new BuildingType(  "BUILDINGTYPE_STREET",          ResourceManager.OBJECT_STREET,           ResourceManager.TEXTURE_STREET,               ResourceManager.TEXTURE_GUITHUMBSTREET,         Color.BLACK,     5,            0,           1,       1,      0,      0,               0,               0,              0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_CITYCENTER,      new BuildingType(  "BUILDINGTYPE_CITYCENTER",      ResourceManager.OBJECT_CITYCENTER,       ResourceManager.TEXTURE_CITYCENTER,           ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,    Color.BLACK,     0,            0,           8,       8,      6,      10,              15,              0,              0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_RESEARCHSTATION, new BuildingType(  "BUILDINGTYPE_RESEARCHSTATION", ResourceManager.OBJECT_RESEARCHSTATION,  ResourceManager.TEXTURE_RESEARCHSTATION,      ResourceManager.TEXTURE_GUITHUMBRESEARCHSTATION,Color.RED,       7000,         10,          6,       6,      4,      0,               0,               0,              10,     0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_HANGAR,          new BuildingType(  "BUILDINGTYPE_HANGAR",          ResourceManager.OBJECT_HANGAR,           ResourceManager.TEXTURE_HANGAR,               ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,    Color.RED,       15000,        10,          2,       2,      3,      0,               0,               0,              10,     0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_BANK,            new BuildingType(  "BUILDINGTYPE_BANK",            ResourceManager.OBJECT_BANK,             ResourceManager.TEXTURE_BANK,                 ResourceManager.TEXTURE_GUITHUMBBANK,           Color.RED,       5000,         10,          6,       4,      3,      0,               0,               0,              10,     0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_MEDICALCENTER,   new BuildingType(  "BUILDINGTYPE_MEDICALCENTER",   ResourceManager.OBJECT_MEDICALCENTER,    ResourceManager.TEXTURE_MEDICALCENTER,        ResourceManager.TEXTURE_GUITHUMBMEDICALCENTER,  Color.BLUE,      10000,        15,          6,       4,      3,      10,              15,              500,            0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_SERVERCENTER,    new BuildingType(  "BUILDINGTYPE_SERVERCENTER",    ResourceManager.OBJECT_SERVERCENTER,     ResourceManager.TEXTURE_SERVERCENTER,         ResourceManager.TEXTURE_GUITHUMBSERVERCENTER,   Color.BLUE,      10000,        10,          8,       8,      3,      0,               0,               1000,           0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_GARBAGEYARD,     new BuildingType(  "BUILDINGTYPE_GARBAGEYARD",     ResourceManager.OBJECT_GARBAGEYARD,      ResourceManager.TEXTURE_GARBAGEYARD,          ResourceManager.TEXTURE_GUITHUMBGARBAGEYARD,    Color.BLUE,      5000,         10,          8,       4,      2,      -20,             20,              1000,           0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_POLICE,          new BuildingType(  "BUILDINGTYPE_POLICE",          ResourceManager.OBJECT_POLICE,           ResourceManager.TEXTURE_POLICE,               ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,    Color.BLUE,      15000,        15,          2,       2,      3,      10,              15,              500,            0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_SOLARPOWER,      new BuildingType(  "BUILDINGTYPE_SOLARPOWER",      ResourceManager.OBJECT_SOLARPOWER,       ResourceManager.TEXTURE_SOLARPOWER,           ResourceManager.TEXTURE_GUITHUMBSOLARPOWER,     Color.YELLOW,    5000,         10,          16,      8,      1,      0,               0,               1000,           0,      0,      0,       0,        0        ));
				buildingTypes.add(  BUILDINGTYPE_FUSIONPOWER,     new BuildingType(  "BUILDINGTYPE_FUSIONPOWER",     ResourceManager.OBJECT_FUSIONPOWER,      ResourceManager.TEXTURE_FUSIONPOWER,          ResourceManager.TEXTURE_GUITHUMBPLACEHOLDER,    Color.YELLOW,    25000,        60,          2,       2,      3,      0,               0,               10000,          0,      0,      0,       0,        0        ));
	}                                                                                                                                                                                                                                                                                                                                                           
	
	/**
	 * Refreshes the distribution of supplies
	 */
	public static void refreshSupply()
	{
		updateSupply = true;
	}
	
	/**
	 * Spreads a supply starting from buildings that provide that supply.
	 * The supplies are spread using connected streets.
	 * @param supply
	 */
	public static void SpreadSupply(Supply supply)
	{
		List<Point> openlist = new ArrayList<Point>();
		List<Point> closedlist = new ArrayList<Point>();
		//Reset supplys
		for(Building b:buildings)
		{
			b.setOwnedSupplyAmount(0, supply);
		}
		//Every building that provides the supply is used as a starting point
		for(int building=0;building<buildings.size();building++)
		{
			Building b = buildings.get(building);
			if(b.getProducedSupply()==supply)
			{
				openlist.clear();
				closedlist.clear();
				//Get information about the building
				int supplyAmount = b.getProducedSupplyAmount();
				int x = (int) b.getX();
				int y = (int) b.getZ();
				int width = Buildings.getBuildingType(b).getWidth();
				int depth = Buildings.getBuildingType(b).getDepth();
				
				//Initialize the openlist:
				/*bottom*/for(int i=x;i<x+width;i++){if(Grid.getCell(i,y+depth).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(i,y+depth));}
				/*top*/   for(int i=x;i<x+width;i++){if(Grid.getCell(i,y-1).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(i,y-1));}
				/*left*/  for(int i=y;i<y+depth;i++){if(Grid.getCell(x-1,i).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(x-1,i));}
				/*right*/ for(int i=y;i<y+depth;i++){if(Grid.getCell(x+width,i).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(x+width,i));}
				
				//Main algorithm loop
				while((!openlist.isEmpty())&&supplyAmount>0)
				{
					//Choose closest point in the openlist
					Point current = openlist.get(0);
					for(Point p:openlist)
					{
						if(Math.sqrt((p.getX()-x)*(p.getX()-x)+(p.getY()-y)*(p.getY()-y))<Math.sqrt((current.getX()-x)*(current.getX()-x)+(current.getY()-y)*(current.getY()-y)))current = p;
					}
					
					//Add new point to closedlist
					closedlist.add(current);
					
					//Add sourrounding points to openlist
					/*bottom*/if(!openlist.contains(new Point(current.getX(),current.getY()+1))&&!closedlist.contains(new Point(current.getX(),current.getY()+1))&&Grid.getCell(current.getX(),current.getY()+1).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(current.getX(),current.getY()+1));
					/*top*/   if(!openlist.contains(new Point(current.getX(),current.getY()-1))&&!closedlist.contains(new Point(current.getX(),current.getY()-1))&&Grid.getCell(current.getX(),current.getY()-1).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(current.getX(),current.getY()-1));
					/*left*/  if(!openlist.contains(new Point(current.getX()+1,current.getY()))&&!closedlist.contains(new Point(current.getX()+1,current.getY()))&&Grid.getCell(current.getX()+1,current.getY()).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(current.getX()+1,current.getY()));
					/*right*/ if(!openlist.contains(new Point(current.getX()-1,current.getY()))&&!closedlist.contains(new Point(current.getX()-1,current.getY()))&&Grid.getCell(current.getX()-1,current.getY()).getBuildingType()==Buildings.BUILDINGTYPE_STREET)openlist.add(new Point(current.getX()-1,current.getY()));
					
					//Supply surrounding buildings
					int own,needed;
					try {
						//Bottom
						own = Grid.getCell(current.getX(), current.getY()+1).getBuilding().getOwnedSupplyAmount(supply);
						needed = Grid.getCell(current.getX(), current.getY()+1).getBuilding().getNeededSupplyAmount(supply);
						
						if(own<needed)
						{
							if((needed-own)<supplyAmount){
								//Give the needed supplies to the building
								supplyAmount -= (needed-own);
								Grid.getCell(current.getX(), current.getY()+1).getBuilding().setOwnedSupplyAmount(needed,supply);
							}else{
								//Give the rest of the suppliey to the building
								Grid.getCell(current.getX(), current.getY()+1).getBuilding().setOwnedSupplyAmount(supplyAmount,supply);
								supplyAmount = 0;
							}
						}
					} catch (Exception e) {}	
					try {
						//Top
						own = Grid.getCell(current.getX(), current.getY()-1).getBuilding().getOwnedSupplyAmount(supply);
						needed = Grid.getCell(current.getX(), current.getY()-1).getBuilding().getNeededSupplyAmount(supply);
						if(own<needed)
						{
							if((needed-own)<supplyAmount){
								//Give the needed supplies to the building
								supplyAmount -= (needed-own);
								Grid.getCell(current.getX(), current.getY()-1).getBuilding().setOwnedSupplyAmount(needed,supply);
							}else{
								//Give the rest of the suppliey to the building
								Grid.getCell(current.getX(), current.getY()-1).getBuilding().setOwnedSupplyAmount(supplyAmount,supply);
								supplyAmount = 0;
							}
						}
					} catch (Exception e) {}	
					try {
						//Right
						own = Grid.getCell(current.getX()+1, current.getY()).getBuilding().getOwnedSupplyAmount(supply);
						needed = Grid.getCell(current.getX()+1, current.getY()).getBuilding().getNeededSupplyAmount(supply);
						if(own<needed)
						{
							if((needed-own)<supplyAmount){
								//Give the needed supplies to the building
								supplyAmount -= (needed-own);
								Grid.getCell(current.getX()+1, current.getY()).getBuilding().setOwnedSupplyAmount(needed,supply);
							}else{
								//Give the rest of the suppliey to the building
								Grid.getCell(current.getX()+1, current.getY()).getBuilding().setOwnedSupplyAmount(supplyAmount,supply);
								supplyAmount = 0;
							}
						}
					} catch (Exception e) {}	
					try {
						//Left
						own = Grid.getCell(current.getX()-1, current.getY()).getBuilding().getOwnedSupplyAmount(supply);
						needed = Grid.getCell(current.getX()-1, current.getY()).getBuilding().getNeededSupplyAmount(supply);
						if(own<needed)
						{
							if((needed-own)<supplyAmount){
								//Give the needed supplies to the building
								supplyAmount -= (needed-own);
								Grid.getCell(current.getX()-1, current.getY()).getBuilding().setOwnedSupplyAmount(needed,supply);
							}else{
								//Give the rest of the supplies to the building
								Grid.getCell(current.getX()-1, current.getY()).getBuilding().setOwnedSupplyAmount(supplyAmount,supply);
								supplyAmount = 0;
							}
						}
					} catch (Exception e) {}
					
					//Remove point from openlist
					openlist.remove(current);
				}
			}
		}
	}
	
	/**
	 * Build a building (Adds it to the grid and to the object list)
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @param bt Building type
	 */
	public static Building buildBuilding(float x, float y, float z, int bt)
	{
		x = Grid.cellSize*Math.round(x/Grid.cellSize);
		y = Grid.cellSize*Math.round(y/Grid.cellSize);
		z = Grid.cellSize*Math.round(z/Grid.cellSize);
		Building building = null;
		switch(bt)
		{
			case BUILDINGTYPE_STREET:           building=new  Street(bt,x,y,z);          break;
			case BUILDINGTYPE_HOUSE:            building=new  House(bt,x,y,z);           break;
			case BUILDINGTYPE_BIGHOUSE:         building=new  BigHouse(bt,x,y,z);        break;
			case BUILDINGTYPE_CITYCENTER:       building=new  CityCenter(bt,x,y,z);      break;
			case BUILDINGTYPE_RESEARCHSTATION:  building=new  ResearchStation(bt,x,y,z); break;
			case BUILDINGTYPE_HANGAR:           building=new  Hangar(bt,x,y,z);          break;
			case BUILDINGTYPE_BANK:             building=new  Bank(bt,x,y,z);            break;
			case BUILDINGTYPE_MEDICALCENTER:    building=new  MedicalCenter(bt,x,y,z);   break;
			case BUILDINGTYPE_SERVERCENTER:     building=new  ServerCenter(bt,x,y,z);    break;
			case BUILDINGTYPE_GARBAGEYARD:      building=new  GarbageYard(bt,x,y,z);     break;
			case BUILDINGTYPE_POLICE:           building=new  Police(bt,x,y,z);          break;
			case BUILDINGTYPE_SOLARPOWER:       building=new  SolarPower(bt,x,y,z);      break;
			case BUILDINGTYPE_FUSIONPOWER:      building=new  FusionPower(bt,x,y,z);     break;
			default:                            building=new  Building(bt,x,y,z);        break;
		}                                                     
		Grid.setBuilding(Math.round(x),Math.round(z), building);
		buildings.add(building);
		return building;
	}

	/**
	 * Deletes an object from the render list
	 * @param obj The object to delete
	 */
	public static void deleteBuiding(Building obj)
	{
		buildings.remove(obj);
	}

	/**
	 * Gets a BuildingType from its Index
	 * @param index The index defined in the resourcemanager
	 * @return The corresponding BuildingType
	 */
	public static BuildingType getBuildingType(int index)
	{
		if(index==-1)return null;
		return buildingTypes.get(index);
	}

	/**
	 * Gets a BuildingType from its Index
	 * @param index The index defined in the resourcemanager
	 * @return The corresponding BuildingType
	 */
	public static BuildingType getBuildingType(Building b)
	{
		return getBuildingType(b.getBuildingType());
	}

	/**
	 * Gets the localized name of the building type
	 * @param buildingtype
	 * @return
	 */
	public static String getBuildingTypeName(int buildingtype)
	{
		return ResourceManager.getString(Buildings.getBuildingType(buildingtype).getName());
	}

	
}
