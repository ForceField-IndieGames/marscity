package gui;

import game.ResourceManager;

import org.newdawn.slick.UnicodeFont;

/**
 * The number box contains a label and two buttons. The displayed number can be
 * altered using the buttons.
 * 
 * @author Benedikt Ringlein
 */

public class GuiNumberbox extends GuiPanel {

	private GuiLabel label;
	private GuiButton plus;
	private GuiButton minus;
	private int value = 0;
	private int min = 0;
	private int max = 2000000000;
	private String prefix = "";
	private String suffix = "";
	private int step = 1;

	public GuiNumberbox(float x, float y, float width, float height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(null);
		label = new GuiLabel(height, 0, width - height * 2, height);
		label.setTexture(ResourceManager.TEXTURE_LOADABORT);
		label.setText(getPrefix() + value + getSuffix());
		label.setCentered(true);
		label.setEvent(new GuiEvent() {
			@Override
			public void run(GuiEventType eventtype, GuiElement element) {
				GuiNumberbox n = ((GuiNumberbox) element.getParent());
				switch (eventtype) {
				case Scrollup:
					if (n.getValue() + n.getStep() <= n.getMax()){
						n.setValue(n.getValue() + n.getStep());
						n.callGuiEvents(GuiEventType.Valuechange);
					}
					break;
				case Scrolldown:
					if (n.getValue() - n.getStep() >= n.getMin()){
						n.setValue(n.getValue() - n.getStep());
						n.callGuiEvents(GuiEventType.Valuechange);
					}
					break;
				default:
					break;
				}
			}
		});
		plus = new GuiButton(width - height, 0, height, height);
		plus.setTexture(ResourceManager.TEXTURE_SCROLLUP);
		plus.setEvent(new GuiEvent() {
			@Override
			public void run(GuiEventType eventtype, GuiElement element) {
				GuiNumberbox n = ((GuiNumberbox) element.getParent());
				switch (eventtype) {
				case Click:
					if (n.getValue() + n.getStep() <= n.getMax()){
						n.setValue(n.getValue() + n.getStep());
						n.callGuiEvents(GuiEventType.Valuechange);
					}
					break;
				default:
					break;
				}
			}
		});
		minus = new GuiButton(0, 0, height, height);
		minus.setTexture(ResourceManager.TEXTURE_SCROLLDOWN);
		minus.setEvent(new GuiEvent() {
			@Override
			public void run(GuiEventType eventtype, GuiElement element) {
				GuiNumberbox n = ((GuiNumberbox) element.getParent());
				switch (eventtype) {
				case Click:
					if (n.getValue() - n.getStep() >= n.getMin()){
						n.setValue(n.getValue() - n.getStep());
						n.callGuiEvents(GuiEventType.Valuechange);
					}
					break;
				default:
					break;
				}
			}
		});
		add(label);
		add(plus);
		add(minus);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		label.setText(getPrefix() + value + getSuffix());
		callGuiEvents(GuiEventType.Valuechange);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		label.setText(getPrefix() + value + getSuffix());
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
		label.setText(getPrefix() + value + getSuffix());
	}

	public void setFont(UnicodeFont font) {
		label.setFont(font);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

}
