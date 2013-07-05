package guielements;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import game.Main;
import game.ResourceManager;
import gui.GuiEventType;

/**
 * A simple textbox. It gets keyboard focus when clicked and looses it,
 * when the enter or ecs keys are pressed. Text can be entered and deleted and
 * there is also a caret which can be positioned using the mouse or the arrow keys.
 * With ctrl+c the text can be copied in the local clipboar and pasted with ctrl+v.
 * @author Benedikt Ringlein
 */

public class GuiTextbox extends GuiLabel {
	
	private int charlimit = 0;
	private GuiPanel caret;
	private int caretpos=0;

	public GuiTextbox()
	{
		new GuiTextbox(0,0,200,30);
	}
	
	public GuiTextbox(float x, float y, float width, float height)
	{
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTexture(ResourceManager.TEXTURE_GUITEXTFIELD);
		setTexturel(ResourceManager.TEXTURE_GUITEXTFIELDL);
		setTexturer(ResourceManager.TEXTURE_GUITEXTFIELDR);
		caret = new GuiPanel(0,5,1,(int) getHeight()-10,Color.white);
		caret.setVisible(false);
		add(caret);
	}
	
	public void setCaret(boolean visible)
	{
		caret.setVisible(visible);
	}
	
	public void setCaretPos(int pos)
	{
		caretpos = pos;
		caret.setX(getFont().getWidth(getText().substring(0, caretpos)));
	}
	
	public int getCaretPos()
	{
		return caretpos;
	}
	
	@Override
	public void callGuiEvents(GuiEventType eventtype)
	{
		try {
			GuiTextbox t = this;
			switch (eventtype) {
			case Click:
					Main.gui.setKeyboardfocus(this);
					t.setColor(Color.darkGray);
					t.setTextColor(Color.white);
					t.setCaret(true);
					float lastdx=getFont().getWidth(getText());
					float dx = 0;
					if(Math.abs(Mouse.getX()-getScreenX())>getFont().getWidth(getText())){
						setCaretPos(getText().length());
					}else{
						for(int i=0;i<t.getText().length()+1;i++){
							dx = Math.abs(Mouse.getX()-getScreenX()-getFont().getWidth(getText().substring(0, i)));
							if(dx>lastdx){
								setCaretPos(i-1);
								break;
							}
						lastdx = Math.abs(Mouse.getX()-getScreenX()-getFont().getWidth(getText().substring(0, i)));
						}
					}
					break;
			case Keypress:
					if(Keyboard.getEventKey()==Keyboard.KEY_RETURN
					||Keyboard.getEventKey()==Keyboard.KEY_ESCAPE){
						Main.gui.setKeyboardfocus(null);
						t.setColor(Color.white);
						t.setTextColor(Color.black);
						t.setCaret(false);
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_LSHIFT
							||Keyboard.getEventKey()==Keyboard.KEY_RSHIFT
							||Keyboard.getEventKey()==Keyboard.KEY_LCONTROL
							||Keyboard.getEventKey()==Keyboard.KEY_RCONTROL)break;
					if(Keyboard.getEventKey()==Keyboard.KEY_BACK&&Keyboard.getEventKeyState()){
						t.setText(t.getText().substring(0, t.getCaretPos()-1)+t.getText().substring(t.getCaretPos()));
						if(t.getCaretPos()>0)t.setCaretPos(t.getCaretPos()-1);
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_END&&Keyboard.getEventKeyState()){
						t.setCaretPos(t.getText().length());
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_HOME&&Keyboard.getEventKeyState()){
						t.setCaretPos(0);
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_LEFT&&Keyboard.getEventKeyState()){
						if(t.getCaretPos()>0)t.setCaretPos(t.getCaretPos()-1);
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_RIGHT&&Keyboard.getEventKeyState()){
						if(t.getCaretPos()<t.getText().length())t.setCaretPos(t.getCaretPos()+1);
						break;
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_DELETE&&Keyboard.getEventKeyState()){
						t.setText(t.getText().substring(0, t.getCaretPos())+t.getText().substring(t.getCaretPos()+1));
						break;
					}
					if((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))&&Keyboard.getEventKey()==Keyboard.KEY_C&&Keyboard.getEventKeyState()){
						Main.clipboard = t.getText();
						break;
					}
					if((Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))&&Keyboard.getEventKey()==Keyboard.KEY_V&&Keyboard.getEventKeyState()){
						t.setText(Main.clipboard);
						if(t.getCaretPos()>t.getText().length())t.setCaretPos(t.getText().length());
						break;
					}
					if(Keyboard.getEventKeyState()&&(t.getText().length()<t.getCharlimit()||t.getCharlimit()==0)){
						t.setText(t.getText().substring(0, t.getCaretPos())+Keyboard.getEventCharacter()+t.getText().substring(t.getCaretPos()));
						t.setCaretPos(t.getCaretPos()+1);
					}
					break;
			default:break;
			}
			getEvent().run(eventtype, this);
		} catch (Exception e) {
		}
	}

	public int getCharlimit() {
		return charlimit;
	}

	public void setCharlimit(int charlimit) {
		this.charlimit = charlimit;
	}
	
}
