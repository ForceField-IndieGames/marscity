package gui;

import java.awt.Color;
import org.lwjgl.input.Keyboard;

import effects.ParticleEffects;

import game.Game;
import game.Main;
import game.ResourceManager;
import animation.AnimationManager;
import animation.AnimationValue;

/**
 * This contains all gui events, buttonpresses etc.
 * @author Benedikt Ringlein
 */

public class GuiEvents {

	public static GuiEvent menuButton = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.Pause();
			Main.gui.blur.setVisible(true);
			Main.gui.pauseMenu.setVisible(true);
			AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.opacity, 1, 0.005f);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent toolDelete = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.selectedTool = Main.TOOL_DELETE;
			Main.gui.toolDelete.setColor(Color.gray);
			Main.buildpreview.setBuilding(-1);
			Main.currentBT = -1;
			Main.gui.deleteBorder.setVisible(true);
			AnimationManager.animateValue(Main.gui.buildingPanels, AnimationValue.Y, 20f, 0.5f, AnimationManager.ACTION_HIDE);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsVsyncon = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
				Main.gui.settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
				Main.gui.settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				ResourceManager.setSetting("vsync", "enabled");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsVsyncoff = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
	 			Main.gui.settingsVsyncon.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsVsyncoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			ResourceManager.setSetting("vsync", "disabled");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsParticlesoff = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
				Main.gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
				Main.gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESOFF;
	 			ResourceManager.setSetting("particlequality", "off");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsParticleslow = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
				Main.gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			Main.gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
	 			Main.gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			Main.gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESLOW;
	 			ResourceManager.setSetting("particlequality", "low");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsParticlesmiddle = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
				Main.gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
				Main.gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
	 			ParticleEffects.particleQuality = ParticleEffects.PARTICLESMIDDLE;
	 			ResourceManager.setSetting("particlequality", "middle");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsParticleshigh = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			try {
				Main.gui.settingsParticlesoff.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticleslow.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticlesmiddle.setTexture(ResourceManager.TEXTURE_GUIBUTTON);
				Main.gui.settingsParticleshigh.setTexture(ResourceManager.TEXTURE_GUIBUTTONDOWN);
				ParticleEffects.particleQuality = ParticleEffects.PARTICLESHIGH;
				ResourceManager.setSetting("particlequality", "high");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent settingsResume = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.Resume();
			Main.gui.blur.setVisible(false);
			AnimationManager.animateValue(Main.gui.settingsMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent pauseMainmenu = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.gameState = Main.STATE_MENU;
			Main.gui = new GUI();
			break;
	case Mouseover:
			break;
	default:break;}}};


	public static GuiEvent pauseSettings = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.gui.pauseMenu.setVisible(false);
			Main.gui.settingsMenu.setVisible(true);
			AnimationManager.animateValue(Main.gui.settingsMenu, AnimationValue.opacity, 1, 0.005f);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent pauseSave = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.Save("res/cities/"+Main.cityname+".city");
			Game.Resume();
			Main.gui.blur.setVisible(false);
			AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.opacity, 1, 0.005f, AnimationManager.ACTION_HIDE);
			break;
	case Mouseover:
			break;
	default:break;}}};


	public static GuiEvent pauseLoad = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.gui.loadingscreen.show();
			break;
	default:break;}}};


	public static GuiEvent pauseResume = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.gui.blur.setVisible(false);
			Game.Resume();
			AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
			break;
	case Mouseover:
			break;
	default:break;}}};


	public static GuiEvent pauseExit = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.exit();
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	public static GuiEvent MenuPlay = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.newGame();
			break;
	default:break;}}};
	
	
	public static GuiEvent MenuExit = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.exit();
			break;
	default:break;}}};
	
	
	public static GuiEvent MenuLoad = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.Load("res/cities/Meine Stadt.city");
			Game.Resume();
			Main.gameState = Main.STATE_GAME;
			break;
	default:break;}}};
	
	
	public static GuiEvent MenuSettings = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			break;
	default:break;}}};
	
	
	public static GuiEvent cityName = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Keypress:
			if((Keyboard.getEventKey()==Keyboard.KEY_RETURN||Keyboard.getEventKey()==Keyboard.KEY_ESCAPE)&&Keyboard.getEventKeyState()){
				Main.cityname = ((GuiTextbox)e).getText();
			}
			break;
	default:break;}}};
	
	
//	public static GuiEvent event = new GuiEvent(){
//	@Override public void run(GuiEventType eventtype) {
//	switch (eventtype) {
//	case Click:
//			
//			break;
//	case Mouseover:
//			break;
//	default:break;}}};
	
}