package game;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

import objects.Building;

public enum DataView {
	
	Energy(ResourceManager.TEXTURE_DATAVIEWBUTTONENERGY){
		@Override
		public Color buildingColor(Building b) {
			if(b.getProducedSupply()==Supply.Energy)return Color.yellow;
			if(b.getOwnedSupplyAmount(Supply.Energy)<b.getNeededSupplyAmount(Supply.Energy)){
				if(b.getOwnedSupplyAmount(Supply.Energy)<=0)return Color.red;
				else return new Color(255,100,0);
			}else if(b.getNeededSupplyAmount(Supply.Energy)>0)return Color.green;
			return super.buildingColor(b);
		}
	},
	Security(ResourceManager.TEXTURE_DATAVIEWBUTTONSECUTIRY){
		@Override
		public Color buildingColor(Building b) {
			if(b.getProducedSupply()==Supply.Security)return Color.yellow;
			if(b.getOwnedSupplyAmount(Supply.Security)<b.getNeededSupplyAmount(Supply.Security)){
				if(b.getOwnedSupplyAmount(Supply.Security)<=0)return Color.red;
				else return new Color(255,100,0);
			}else if(b.getNeededSupplyAmount(Supply.Security)>0)return Color.green;
			return super.buildingColor(b);
		}
	}, 
	Health(ResourceManager.TEXTURE_DATAVIEWBUTTONHEALTH){
		@Override
		public Color buildingColor(Building b) {
			if(b.getProducedSupply()==Supply.Health)return Color.yellow;
			if(b.getOwnedSupplyAmount(Supply.Health)<b.getNeededSupplyAmount(Supply.Health)){
				if(b.getOwnedSupplyAmount(Supply.Health)<=0)return Color.red;
				else return new Color(255,100,0);
			}else if(b.getNeededSupplyAmount(Supply.Health)>0)return Color.green;
			return super.buildingColor(b);
		}
	},
	Garbagecollection(ResourceManager.TEXTURE_DATAVIEWBUTTONGARBAGE){
		@Override
		public Color buildingColor(Building b) {
			if(b.getProducedSupply()==Supply.Garbagecollection)return Color.yellow;
			if(b.getOwnedSupplyAmount(Supply.Garbagecollection)<b.getNeededSupplyAmount(Supply.Garbagecollection)){
				if(b.getOwnedSupplyAmount(Supply.Garbagecollection)<=0)return Color.red;
				else return new Color(255,100,0);
			}else if(b.getNeededSupplyAmount(Supply.Garbagecollection)>0)return Color.green;
			return super.buildingColor(b);
		}
	},
	Internet(ResourceManager.TEXTURE_DATAVIEWBUTTONINTERNET){
		@Override
		public Color buildingColor(Building b) {
			if(b.getProducedSupply()==Supply.Internet)return Color.yellow;
			if(b.getOwnedSupplyAmount(Supply.Internet)<b.getNeededSupplyAmount(Supply.Internet)){
				if(b.getOwnedSupplyAmount(Supply.Internet)<=0)return Color.red;
				else return new Color(255,100,0);
			}else if(b.getNeededSupplyAmount(Supply.Internet)>0)return Color.green;
			return super.buildingColor(b);
		}
	};
	
	
	private Texture buttontexture;
	
	DataView(Texture buttontexture)
	{
		this.buttontexture = buttontexture;
	}
	
	public Color buildingColor(Building b)
	{
		return Color.white;
	}
	
	public Texture getButtonTexture()
	{
		return this.buttontexture;
	}
}
