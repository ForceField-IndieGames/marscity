package gui;

import java.awt.Color;

import game.ResourceManager;

public class CityPreview extends GuiPanel {

	private String cityname="";
	private GuiLabel namelabel;
	
	public CityPreview(float x, float y, String cityname)
	{
		setX(x);
		setY(y);
		setWidth(150);
		setHeight(150);
		setTexture(ResourceManager.TEXTURE_GUIMENU);
		namelabel = new GuiLabel(5,0,getWidth()-10,30,(Color)null);
		namelabel.setText(cityname);
		namelabel.setCentered(true);
		add(namelabel);
		setCityname(cityname);
		setEvent(GuiEvents.CityPreview);
		
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
		namelabel.setText(cityname);
	}
	
}
