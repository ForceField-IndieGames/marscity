package guielements;

import org.lwjgl.opengl.GL11;

import animation.AnimationManager;
import animation.AnimationValue;

import gui.GuiElement;
import gui.GuiEventType;

public class GuiScrollablePanel extends GuiPanel {

	private GuiPanel contentPanel;
	private float scrollH = 0;
	private float scrollV = 0;

	public GuiScrollablePanel(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(null);
		contentPanel = new GuiPanel(0,0,width,height);
		elements.add(contentPanel);
		contentPanel.setParent(this);
		contentPanel.setColor(null);
	}
	
	@Override
	public void add(GuiElement guielement) {
		contentPanel.add(guielement);
		if(guielement.getX()+guielement.getWidth()>contentPanel.getWidth()){
			contentPanel.setWidth(guielement.getX()+guielement.getWidth());
		}
		if(guielement.getY()+guielement.getHeight()>contentPanel.getHeight()){
			contentPanel.setHeight(guielement.getY()+guielement.getHeight());
		}
	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype) {
		super.callGuiEvents(eventtype);
		if(eventtype == GuiEventType.Scrolldown&&scrollV<0){
			scrollV += 40;
			AnimationManager.animateValue(contentPanel, AnimationValue.Y, scrollV, 100);
		}else if(eventtype == GuiEventType.Scrollup&&scrollV>getHeight()-contentPanel.getHeight()){
			scrollV -= 40;
			AnimationManager.animateValue(contentPanel, AnimationValue.Y, scrollV, 100);
		}
	}
	
	@Override
	public void callIndirectGuiEvents(GuiEventType eventtype) {
		super.callGuiEvents(eventtype);
		callGuiEvents(eventtype);
	}
	
	@Override
	public void draw() {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
		super.draw();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	/**
	 * Fit the size of the scroll panel to its contents
	 */
	public void refreshSize()
	{
		contentPanel.setWidth(0);
		contentPanel.setHeight(0);
		for(GuiElement e:contentPanel.elements)
		{
			if(e.getX()+e.getWidth()>contentPanel.getWidth())contentPanel.setWidth(e.getX()+e.getWidth());
			if(e.getY()+e.getHeight()>contentPanel.getHeight())contentPanel.setHeight(e.getY()+e.getHeight());
		}
		scrollH=0;
		contentPanel.setX(scrollH);
		scrollV = getHeight()-contentPanel.getHeight();
		contentPanel.setY(scrollV);
	}

	public float getScrollH() {
		return scrollH;
	}

	public void setScrollH(float scrollH) {
		this.scrollH = scrollH;
	}

	public float getScrollV() {
		return scrollV;
	}

	public void setScrollV(float scrollV) {
		this.scrollV = scrollV;
	}

	public GuiPanel getContentPanel() {
		return contentPanel;
	}

}
