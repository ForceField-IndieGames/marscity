package guielements;

import java.awt.Color;
import org.lwjgl.input.Mouse;
import animation.Animatable;
import animation.AnimationManager;
import animation.AnimationValue;

import game.ResourceManager;
import gui.GuiElement;
import gui.GuiEvent;
import gui.GuiEventType;

public class GuiScrollBar extends GuiPanel {

	private GuiPanel outer_top;
	private GuiPanel outer_middle;
	private GuiPanel outer_bottom;
	private GuiPanel inner;
	private GuiPanel inner_top;
	private GuiPanel inner_middle;
	private GuiPanel inner_bottom;
	private float value;
	private float total_size = 1;
	private float view_size = 0.5f;
	
	public GuiScrollBar(float x, float y, float length)
	{
		setX(x);
		setY(y);
		setWidth(16);
		setHeight(length);
		setColor(null);
		setOpacity(0.3f);
		outer_top = new GuiPanel(0,getHeight()-8,16,8,ResourceManager.TEXTURE_GUISCROLLBAROT);
		outer_middle = new GuiPanel(0,8,16,getHeight()-16,ResourceManager.TEXTURE_GUISCROLLBAROM);
		outer_bottom = new GuiPanel(0,0,16,8,ResourceManager.TEXTURE_GUISCROLLBAROB);
		add(outer_top);
		add(outer_middle);
		add(outer_bottom);
		inner = new GuiPanel(0,getHeight()/2,getWidth(),getHeight()/2,(Color)null);
		inner.setEvent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype) {
				switch (eventtype) {
				case Drag:
					inner.setY(inner.getY()+Mouse.getEventDY());
					if(inner.getY()<0)inner.setY(0);
					if(inner.getY()>(getHeight()-inner.getHeight()))inner.setY(getHeight()-inner.getHeight());
					setValue(inner.getY()/(getHeight()-inner.getHeight())*(getView_size()-getTotal_size()));
					callGuiEvents(GuiEventType.Valuechange);
					break;
				default:
					break;
				}
			}
		});
		setIndirectevent(new GuiEvent(){
			@Override
			public void run(GuiEventType eventtype, GuiElement element) {
				switch (eventtype) {
				case Mouseover:
					AnimationManager.animateValue((Animatable)element, AnimationValue.OPACITY, 0.8f,100);
					break;
				case Mouseout:
					AnimationManager.animateValue((Animatable)element, AnimationValue.OPACITY, 0.3f,100);
					break;
				default:
					break;
				}
			}
		});
		add(inner);
		inner_top = new GuiPanel(0,inner.getHeight()-8,16,8,ResourceManager.TEXTURE_GUISCROLLBARIT);
		inner_middle = new GuiPanel(0,8,16,inner.getHeight()-16,ResourceManager.TEXTURE_GUISCROLLBARIM);
		inner_bottom = new GuiPanel(0,0,16,8,ResourceManager.TEXTURE_GUISCROLLBARIB);
		inner_top.setClickThrough(true);
		inner_middle.setClickThrough(true);
		inner_bottom.setClickThrough(true);
		inner.add(inner_top);
		inner.add(inner_middle);
		inner.add(inner_bottom);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
		inner.setY((getValue()/(getView_size()-getTotal_size()))*(getHeight()-inner.getHeight()));
	}

	public float getTotal_size() {
		return total_size;
	}

	public void setTotal_size(float total_size) {
		this.total_size = total_size;
		inner.setHeight(getHeight()*(view_size/total_size));
		inner_top.setY(inner.getHeight()-8);
		inner_middle.setHeight(inner.getHeight()-16);
	}

	public float getView_size() {
		return view_size;
	}

	public void setView_size(float view_size) {
		this.view_size = view_size;
		inner.setHeight(getHeight()*(view_size/total_size));
		inner_top.setY(inner.getHeight()-8);
		inner_middle.setHeight(inner.getHeight()-16);
	}
}
