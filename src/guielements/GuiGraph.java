package guielements;

import game.ResourceManager;

import java.awt.Color;

/**
 * A simple graph that can visualize data
 * @author Benedikt Ringlein
 */

public class GuiGraph extends GuiPanel {

	private float max        = 1000;
	private int[] points     = new int[100];
	private Color graphColor = Color.gray;
	
	public GuiGraph(float x, float y, float width, float height, int... points)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(null);
		setPoints(points);
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public int[] getPoints() {
		return points;
	}

	public void setPoints(int... points) {
		this.points = points;
		getElements().clear();
		for(int i=1;i<points.length;i++)
		{
			GuiQuad quad = new GuiQuad((i-1)*(getWidth()/(points.length-1)),
					(i)*(getWidth()/(points.length-1)),
					(i)*(getWidth()/(points.length-1)),
					(i-1)*(getWidth()/(points.length-1)),
					0, 0, 
					(points[i]/getMax())*getHeight(),
					(points[(i<=0)?i:i-1]/getMax())*getHeight());
			quad.setColor(graphColor);
			quad.setTexture(ResourceManager.TEXTURE_GRAPHTRANSITION);
			add(quad);
			if(points[i]>getMax())setMax(points[i]);
		}
	}
	
	public void setGraphColor(Color color)
	{
		this.graphColor = color;
		setElementsColor(color);
	}

}
