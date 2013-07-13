package gui;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

import game.DataView;
import game.Main;
import guielements.GuiButton;
import guielements.GuiPanel;

public class DataViewButton extends GuiButton {

	private DataView dataview;
	
	public DataViewButton(float x, float y, float width, float height, Texture texture, DataView dataview)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(texture);
		setDataview(dataview);
		setEvent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Click:
					if(Main.currentDataView!=getDataview()){
						Main.currentDataView = getDataview();
						((GuiPanel)getParent()).setElementsColor(Color.white);
						setColor(Color.gray);
					}
					else{
						Main.currentDataView = null;
						setColor(Color.white);
					}
					break;
				default:
					break;
				}
			}
		});
	}

	public DataView getDataview() {
		return dataview;
	}

	public void setDataview(DataView dataview) {
		this.dataview = dataview;
	}
	
}
