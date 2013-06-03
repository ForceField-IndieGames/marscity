package gui;

import java.awt.Color;
import java.io.File;

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
			if(e.getColor().equals(Color.white))e.setColor(new Color(235,235,235));
			break;
	case Mouseout:
			if(e.getColor().equals(new Color(235,235,235)))e.setColor(Color.white);
			break;
	default:break;}}};
	
	
	public static GuiEvent GuiTextBoxes = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	GuiTextbox t = ((GuiTextbox)e);
	switch (eventtype) {
	case Click:
			Main.gui.setKeyboardfocus(e);
			t.setColor(Color.darkGray);
			t.setTextColor(Color.white);
			t.setCaret(true);
			break;
	case Keypress:
			if(Keyboard.getEventKey()==Keyboard.KEY_RETURN
			||Keyboard.getEventKey()==Keyboard.KEY_ESCAPE){
				Main.gui.setKeyboardfocus(null);
				t.setColor(Color.white);
				t.setTextColor(Color.black);
				t.setCaret(false);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_LSHIFT
					||Keyboard.getEventKey()==Keyboard.KEY_RSHIFT
					||Keyboard.getEventKey()==Keyboard.KEY_LCONTROL
					||Keyboard.getEventKey()==Keyboard.KEY_RCONTROL)return;
			if(Keyboard.getEventKey()==Keyboard.KEY_BACK&&Keyboard.getEventKeyState()){
				t.setText(t.getText().substring(0, t.getCaretPos()-1)+t.getText().substring(t.getCaretPos()));
				if(t.getCaretPos()>0)t.setCaretPos(t.getCaretPos()-1);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_END&&Keyboard.getEventKeyState()){
				t.setCaretPos(t.getText().length());
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_HOME&&Keyboard.getEventKeyState()){
				t.setCaretPos(0);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_LEFT&&Keyboard.getEventKeyState()){
				if(t.getCaretPos()>0)t.setCaretPos(t.getCaretPos()-1);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_RIGHT&&Keyboard.getEventKeyState()){
				if(t.getCaretPos()<t.getText().length())t.setCaretPos(t.getCaretPos()+1);
				return;
			}
			if(Keyboard.getEventKey()==Keyboard.KEY_DELETE&&Keyboard.getEventKeyState()){
				t.setText(t.getText().substring(0, t.getCaretPos())+t.getText().substring(t.getCaretPos()+1));
				return;
			}
			if((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))&&Keyboard.getEventKey()==Keyboard.KEY_C&&Keyboard.getEventKeyState()){
				Main.clipboard = t.getText();
				return;
			}
			if((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))&&Keyboard.getEventKey()==Keyboard.KEY_V&&Keyboard.getEventKeyState()){
				t.setText(Main.clipboard);
				if(t.getCaretPos()>t.getText().length())t.setCaretPos(t.getText().length());
				return;
			}
			if(Keyboard.getEventKeyState()&&(t.getText().length()<t.getCharlimit()||t.getCharlimit()==0)){
				t.setText(t.getText().substring(0, t.getCaretPos())+Keyboard.getEventCharacter()+t.getText().substring(t.getCaretPos()));
				t.setCaretPos(t.getCaretPos()+1);
			}
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
			if(Keyboard.getEventKey()==Keyboard.KEY_RETURN&&Keyboard.getEventKeyState()){
				Main.cityname = ((GuiTextbox)e).getText();
			}
			break;
	default:break;}}};
	
	
	public static GuiEvent LoadingScreenButton = new GuiEvent(){
	@Override public void run(GuiEventType eventtype) {
	switch (eventtype) {
	case Click:
			if(Main.gui.loadingscreen.getCityName()==""){
				Main.gui.MsgBox("Keine Auswahl", "Bitte eine Stadt auswählen!");
				break;
			}
			Game.Load("res/cities/"+Main.gui.loadingscreen.getCityName()+".city");
			Game.Resume();
			break;
	case Mouseover:
			break;
	default:break;}}};
	
	public static GuiEvent LoadingScreenAbort = new GuiEvent(){
		@Override public void run(GuiEventType eventtype) {
		switch (eventtype) {
		case Click:
				Main.gui.blur.setVisible(false);
				Game.Resume();
				AnimationManager.animateValue(Main.gui.pauseMenu, AnimationValue.opacity, 0, 0.005f, AnimationManager.ACTION_HIDE);
				Main.gui.loadingscreen.setVisible(false);
				break;
		case Mouseover:
				break;
		default:break;}}};
	
	
	public static GuiEvent CityPreview = new GuiEvent(){
	@Override public void run(GuiEventType eventtype, GuiElement e) {
	switch (eventtype) {
	case Click:
			if(((CityPreview)e).getCityname()!="")
				Main.gui.loadingscreen.setCityName(((CityPreview)e).getCityname());
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