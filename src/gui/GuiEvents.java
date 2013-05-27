package gui;

import java.awt.Color;

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
	
	
	public static GuiEvent toolAdd = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.selectedTool = Main.TOOL_ADD;
			Main.gui.toolAdd.setColor(Color.gray);
			Main.gui.toolDelete.setColor(Color.white);
			AnimationManager.animateValue(Main.gui.toolBar, AnimationValue.Y, 0, 0.5f);
			Main.gui.deleteBorder.setVisible(false);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent toolDelete = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.selectedTool = Main.TOOL_DELETE;
			Main.gui.toolAdd.setColor(Color.white);
			Main.gui.toolDelete.setColor(Color.gray);
			AnimationManager.animateValue(Main.gui.toolBar, AnimationValue.Y, -40, 0.5f);
			Main.buildpreview.setBuilding(-1);
			Main.currentBuildingType = -1;
			Main.gui.deleteBorder.setVisible(true);
			break;
	case Mouseover:
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
	
	
	public static GuiEvent pauseMainmenu = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.gameState = Main.STATE_MENU;
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent buildingStreet = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.currentBuildingType = ResourceManager.BUILDINGTYPE_STREET;
			Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_STREET);
			Main.gui.buildingStreet.setColor(Color.gray);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent buildingHouse = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.currentBuildingType = ResourceManager.BUILDINGTYPE_HOUSE;
			Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_HOUSE);
			Main.gui.buildingHouse.setColor(Color.gray);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent buildingBighouse = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Main.currentBuildingType = ResourceManager.BUILDINGTYPE_BIGHOUSE;
			Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_BIGHOUSE);
			Main.gui.buildingBighouse.setColor(Color.gray);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent pauseSave = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			Game.Save("res/saves/savegame.save");
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
			Game.Load("res/saves/savegame.save");
			Main.gui = null;
			Main.gui = new GUI();
			Game.Resume();
			break;
	case Mouseover:
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
	
	
//	public static GuiEvent event = new GuiEvent(){
//	@Override public void run(GuiEventType eventtype) {
//	switch (eventtype) {
//	case Click:
//			
//			break;
//	case Mouseover:
//			break;
//	default:break;}}};
	
	
//	public static GuiEvent event = new GuiEvent(){
//	@Override public void run(GuiEventType eventtype) {
//	switch (eventtype) {
//	case Click:
//			
//			break;
//	case Mouseover:
//			break;
//	default:break;}}};
	
	
//	public static GuiEvent event = new GuiEvent(){
//	@Override public void run(GuiEventType eventtype) {
//	switch (eventtype) {
//	case Click:
//			
//			break;
//	case Mouseover:
//			break;
//	default:break;}}};
	
	
//	public static GuiEvent event = new GuiEvent(){
//	@Override public void run(GuiEventType eventtype) {
//	switch (eventtype) {
//	case Click:
//			
//			break;
//	case Mouseover:
//			break;
//	default:break;}}};
	
	
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