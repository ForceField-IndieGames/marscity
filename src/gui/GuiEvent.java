package gui;

/**
 * GuiEvents are fired, when an element is hovered, clicked etc.
 * Then the run method is executed. THe event gets called with every
 * event type but the event type is given as a parameter, so the events
 * can be distinguished.
 * @author Benedikt Ringlein
 */

public class GuiEvent {

	public GuiEvent()
	{
		
	}
	
	public void run(GuiEventType eventtype){
		
	}
	
	public void run(GuiEventType eventtype, GuiElement element){
		run(eventtype);
	}
	
}
