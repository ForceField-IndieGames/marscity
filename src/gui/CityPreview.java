package gui;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;

import game.ResourceManager;

public class CityPreview extends GuiPanel {

	private String cityname="";
	private GuiLabel namelabel;
	private GuiPanel image;
	private GuiPanel spec;
	
	public CityPreview(float x, float y, String cityname)
	{
		setX(x);
		setY(y);
		setWidth(225);
		setHeight(112);
		setTexture(ResourceManager.TEXTURE_CPSHADOW);
		image = new GuiPanel(10,20,getWidth()-20,getWidth()/2-20,(Color)null);
		image.setClickThrough(true);
		add(image);
		spec = new GuiPanel(0,0,getWidth(),getHeight(),ResourceManager.TEXTURE_CPSPEC);
		spec.setClickThrough(true);
		add(spec);
		namelabel = new GuiLabel(15,getHeight()-20,getWidth()-30,30,ResourceManager.TEXTURE_GUILABELBG,ResourceManager.TEXTURE_GUILABELBGL,ResourceManager.TEXTURE_GUILABELBGR);
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
	
	public void setEnabled(boolean enabled)
	{
		if(enabled){
			setVisible(true);
			try {
				image.setTexture(ResourceManager.LoadTexture(new FileInputStream(new File("res/cities/"+getCityname()+".png"))));
			} catch (Exception e) {
			}
			
		}else{
			setVisible(false);
		}
	}
	
}
