package gui;

import java.awt.Color;
import java.awt.RenderingHints.Key;

import org.lwjgl.input.Keyboard;

import effects.ParticleEffects;

import game.Game;
import game.Main;
import game.ResourceManager;
import animation.Animatable;
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
			Main.currentBuildingType = -1;
			Main.gui.deleteBorder.setVisible(true);
			AnimationManager.animateValue(Main.gui.buildingsPanel, AnimationValue.Y, 20f, 0.5f, AnimationManager.ACTION_HIDE);
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
			Game.Resume();
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


	public static GuiEvent building = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			Main.selectedTool = Main.TOOL_ADD;
			AnimationManager.animateValue(Main.gui.buildingsPanel, AnimationValue.Y, 20f, 0.5f, AnimationManager.ACTION_HIDE);
			if(e==Main.gui.buildingStreet){
				Main.currentBuildingType = ResourceManager.BUILDINGTYPE_STREET;
				Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_STREET);
			}else if(e==Main.gui.buildingHouse){
				Main.currentBuildingType = ResourceManager.BUILDINGTYPE_HOUSE;
				Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_HOUSE);
			}else if(e==Main.gui.buildingBighouse){
				Main.currentBuildingType = ResourceManager.BUILDINGTYPE_BIGHOUSE;
				Main.buildpreview.setBuilding(ResourceManager.BUILDINGTYPE_BIGHOUSE);
			}
			break;
	case Mouseover:
			Main.gui.buildingTooltip.setVisible(true);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 1f, 0.005f);
			Main.gui.buildingTooltip.setY(e.getScreenY()+e.getHeight()-10);
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()+10, 0.05f);
			if(Main.gui.buildingTooltip.getOpacity()>0)
			AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.X, e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2, 0.5f);
			else Main.gui.buildingTooltip.setX(e.getScreenX()+e.getWidth()/2-Main.gui.buildingTooltip.getWidth()/2);
			if(e==Main.gui.buildingStreet)Main.gui.buildingTooltip.setBuilding(ResourceManager.BUILDINGTYPE_STREET);
			else if(e==Main.gui.buildingHouse)Main.gui.buildingTooltip.setBuilding(ResourceManager.BUILDINGTYPE_HOUSE);
			else if(e==Main.gui.buildingBighouse)Main.gui.buildingTooltip.setBuilding(ResourceManager.BUILDINGTYPE_BIGHOUSE);
			break;
	case Mouseout:
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.opacity, 0f, 0.005f,AnimationManager.ACTION_HIDE);
		AnimationManager.animateValue(Main.gui.buildingTooltip, AnimationValue.Y, Main.gui.buildingTooltip.getY()-10, 0.05f);
		break;
	default:break;}}};
	
	
	public static GuiEvent buildingCategorys = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			Main.gui.buildingsPanel.setElementsVisible(false);
			Main.gui.buildingsPanell.setVisible(true);
			Main.gui.buildingsPanel.setVisible(true);
			Main.selectedTool = Main.TOOL_SELECT;
			Main.buildpreview.setBuilding(-1);
			Main.currentBuildingType = -1;
			Main.gui.deleteBorder.setVisible(false);
			Main.gui.toolDelete.setColor(Color.white);
			AnimationManager.animateValue(Main.gui.buildingsPanel, AnimationValue.Y, 68f, 0.5f);
		if(e==Main.gui.categoryStreets){
			Main.gui.buildingsStreet.setVisible(true);
		}
		if(e==Main.gui.categoryResidential){
			Main.gui.buildingsResidential.setVisible(true);
		}
			break;
	case Mouseover:
			break;
	default:break;}}};

	
	public static GuiEvent MsgBox = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement element) {
	switch (eventtype) {
	case Click:
			AnimationManager.animateValue((Animatable) element.getParent(), AnimationValue.opacity, 0f, 0.005f, AnimationManager.ACTION_REMOVEGUI);
			AnimationManager.animateValue((Animatable) element.getParent(), AnimationValue.Y, element.getParent().getY()-20, 0.1f);
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	
	public static GuiEvent GuiButtons = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			ResourceManager.playSound(ResourceManager.SOUND_SELECT);
	case Mouseover:
			e.setColor(new Color(235,235,235));
			break;
	case Mouseout:
			e.setColor(Color.white);
			break;
	default:break;}}};
	
	
	public static GuiEvent GuiTextFields = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			Main.gui.setKeyboardfocus(e);
			e.setColor(Color.darkGray);
			((GuiTextbox)e).setTextColor(Color.white);
			break;
	case Keypress:
			if(Keyboard.getEventKey()==Keyboard.KEY_RETURN
			||Keyboard.getEventKey()==Keyboard.KEY_ESCAPE){
				Main.gui.setKeyboardfocus(null);
				e.setColor(Color.white);
				((GuiTextbox)e).setTextColor(Color.black);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_LSHIFT
					||Keyboard.getEventKey()==Keyboard.KEY_RSHIFT
					||Keyboard.getEventKey()==Keyboard.KEY_LCONTROL
					||Keyboard.getEventKey()==Keyboard.KEY_RCONTROL)return;
			if(Keyboard.getEventKey()==Keyboard.KEY_BACK&&Keyboard.getEventKeyState()){
				((GuiTextbox)e).setText(((GuiTextbox)e).getText().substring(0, ((GuiTextbox)e).getText().length()-1));
				return;
			}
			if(Keyboard.getEventKeyState()&&(((GuiTextbox)e).getText().length()<((GuiTextbox)e).getCharlimit()||((GuiTextbox)e).getCharlimit()==0)){
				((GuiTextbox)e).setText(((GuiTextbox)e).getText()+Keyboard.getEventCharacter());
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