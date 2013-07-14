package gui;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import animation.AnimationManager;
import animation.AnimationValue;
import game.ResourceManager;
import guielements.GuiLabel;

public class ToolTip extends GuiLabel {
	
	private Timer timer;
	
	public ToolTip() {
		setTexture(ResourceManager.TEXTURE_TOOLTIP);
		setTexturel(ResourceManager.TEXTURE_TOOLTIPL);
		setTexturer(ResourceManager.TEXTURE_TOOLTIPR);
		setVisible(false);
		setOpacity(0);
		setClickThrough(true);
	}
	
	public void show(String text) {
		setText(text);
		AutoSize();
		setHeight(32);
		setVisible(true);
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 1f, 200);
		try{timer.cancel();}catch(Exception e){}
		timer = new Timer();
		timer.schedule(new TimerTask() {@Override public void run() {hide();}}, 1500);
	}
	
	@Override
	public void hide() {
		AnimationManager.animateValue(this, AnimationValue.OPACITY, 0f, 200,AnimationManager.ACTION_HIDE);
	}
	
}
