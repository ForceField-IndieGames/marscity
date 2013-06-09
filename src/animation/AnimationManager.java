package animation;

import game.Main;
import game.ResourceManager;
import gui.GuiElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benedikt Ringlein
 * This is the animation manager that provides methods for animating values
 * over time. Object that implement Animatable can be animated. 
 */

class Animation{
	Animatable object;
	AnimationValue value;
	double startvalue;
	double destValue;
	double speed;
	CustomAnimationValue customvalue;
	int finishedAction = AnimationManager.ACTION_NOTHING;

	public Animation(Animatable object, AnimationValue value, CustomAnimationValue customvalue, double startvalue, double destValue, double speed, int finishedAction){
		this.object=object;
		this.value = value;
		this.customvalue = customvalue;
		this.startvalue = startvalue;
		this.destValue = destValue;
		this.speed = speed;
		this.finishedAction = finishedAction;
	}
}

public class AnimationManager {

	public static final int ACTION_NOTHING = 0;
	public static final int ACTION_DELETE = 1;
	public static final int ACTION_SHOW = 2;
	public static final int ACTION_HIDE = 3;
	public static final int ACTION_RESET = 4;
	public static final int ACTION_REVERSE = 5;
	public static final int ACTION_REMOVEGUI = 6;
	
	private static List<Animation> animations = new ArrayList<Animation>();
	
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, int time)
	{
		animateValue(object, customvalue, destValue, time, ACTION_NOTHING);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, int time, int action)
	{
		double speed = Math.abs(destValue-customvalue.getValue())/time;
		animateValue(object, customvalue, destValue, speed, action);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, double speed)
	{
		animateValue(object, customvalue, destValue, speed, ACTION_NOTHING);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, double speed, int action)
	{
		for(int i=0;i<animations.size();i++){
			if(animations.get(i).object==object&&animations.get(i).value==AnimationValue.custom){
				animations.remove(i);
				i--;
			}
		}
		animations.add(new Animation(object, AnimationValue.custom, customvalue, customvalue.getValue(), destValue, speed, action));
	}
	public static void animateValue(Animatable object, AnimationValue value, float destValue, int time){
		animateValue(object, value, destValue, time, ACTION_NOTHING);
	}
	/**
	 * Animates a value
	 * @param object The object to be animated
	 * @param value The value that should be animated
	 * @param destValue The value at the end of the animation
	 * @param time The duration of the animation in milliseconds
	 * @param action The actions that should be done when the animation is finished
	 */
	public static void animateValue(Animatable object, AnimationValue value, double destValue, int time, int action)
	{
		double speed=0;
		switch(value){
		case X:
			speed = Math.abs(destValue-object.getX())/time;
			break;
		case Y:
			speed = Math.abs(destValue-object.getY())/time;
			break;
		case Z:
			speed = Math.abs(destValue-object.getZ())/time;
			break;
		case rotX:
			speed = Math.abs(destValue-object.getRotX())/time;
			break;
		case rotY:
			speed = Math.abs(destValue-object.getRotY())/time;
			break;
		case rotZ:
			speed = Math.abs(destValue-object.getRotZ())/time;
			break;
		case opacity:
			speed = Math.abs(destValue-object.getOpacity())/time;
			break;
		default:
			return;
		}
		animateValue(object, value, destValue, speed, action);
		
	}
	public static void animateValue(Animatable object, AnimationValue value, double destValue, double speed)
	{
		animateValue(object, value, destValue, speed, ACTION_NOTHING);
		
	}
	public static void animateValue(Animatable object, AnimationValue value, double destValue, double speed, int action)
	{
		for(int i=0;i<animations.size();i++){
			if(animations.get(i).object==object&&animations.get(i).value==value){
				animations.remove(i);
				i--;
			}
		}
		switch(value){
		case X:
			animations.add(new Animation(object, value, null, object.getX() , destValue, speed, action));
			break;
		case Y:
			animations.add(new Animation(object, value, null, object.getY() , destValue, speed, action));
			break;
		case Z:
			animations.add(new Animation(object, value, null, object.getZ() , destValue, speed, action));
			break;
		case rotX:
			animations.add(new Animation(object, value, null, object.getRotX() , destValue, speed, action));
			break;
		case rotY:
			animations.add(new Animation(object, value, null, object.getRotY() , destValue, speed, action));
			break;
		case rotZ:
			animations.add(new Animation(object, value, null, object.getRotZ() , destValue, speed, action));
			break;
		case opacity:
			animations.add(new Animation(object, value, null, object.getOpacity() , destValue, speed, action));
			break;
		default:
			break;
		}
	}
	
	public static void update(int delta)
	{
		for(Animation animation: animations)
		{
			switch(animation.value)
			{
				case X: 
					if(Math.abs(animation.object.getX()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getX()<animation.destValue){
							animation.object.setX((float) (animation.object.getX()+delta*animation.speed));
						}else{
							animation.object.setX((float) (animation.object.getX()-delta*animation.speed));
						}
					}else{
						animation.object.setX((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case Y: 
					if(Math.abs(animation.object.getY()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getY()<animation.destValue){
							animation.object.setY((float) (animation.object.getY()+delta*animation.speed));
						}else{
							animation.object.setY((float) (animation.object.getY()-delta*animation.speed));
						}
					}else{
						animation.object.setY((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case Z: 
					if(Math.abs(animation.object.getZ()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getZ()<animation.destValue){
							animation.object.setZ((float) (animation.object.getZ()+delta*animation.speed));
						}else{
							animation.object.setZ((float) (animation.object.getZ()-delta*animation.speed));
						}
					}else{
						animation.object.setZ((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotX: 
					if(Math.abs(animation.object.getRotX()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotX()<animation.destValue){
							animation.object.setRotX((float) (animation.object.getRotX()+delta*animation.speed));
						}else{
							animation.object.setRotX((float) (animation.object.getRotX()-delta*animation.speed));
						}
					}else{
						animation.object.setRotX((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotY: 
					if(Math.abs(animation.object.getRotY()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotY()<animation.destValue){
							animation.object.setRotY((float) (animation.object.getRotY()+delta*animation.speed));
						}else{
							animation.object.setRotY((float) (animation.object.getRotY()-delta*animation.speed));
						}
					}else{
						animation.object.setRotY((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotZ: 
					if(Math.abs(animation.object.getRotZ()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotZ()<animation.destValue){
							animation.object.setRotZ((float) (animation.object.getRotZ()+delta*animation.speed));
						}else{
							animation.object.setRotZ((float) (animation.object.getRotZ()-delta*animation.speed));
						}
					}else{
						animation.object.setRotZ((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case opacity:
					if(Math.abs(animation.object.getOpacity()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getOpacity()<animation.destValue){
							animation.object.setOpacity((float) (animation.object.getOpacity()+delta*animation.speed));
						}else{
							animation.object.setOpacity((float) (animation.object.getOpacity()-delta*animation.speed));
						}
					}else{
						animation.object.setOpacity((float) animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
					break;
				case custom:
					if(Math.abs(animation.customvalue.getValue()-animation.destValue)>=delta*animation.speed){
						if(animation.customvalue.getValue()<animation.destValue){
							animation.customvalue.setValue(animation.customvalue.getValue()+delta*animation.speed);
						}else{
							animation.customvalue.setValue(animation.customvalue.getValue()-delta*animation.speed);
						}
					}else{
						animation.customvalue.setValue(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
					break;
				default:
					break;
			}
		}
	}
	
	public static void ExecuteFinishedAction(Animation animation)
	{
		if(animation.finishedAction==ACTION_DELETE)
		{
			ResourceManager.deleteObject(animation.object);
		}else if(animation.finishedAction==ACTION_REMOVEGUI)
		{
			Main.gui.remove((GuiElement)animation.object);
		}else if(animation.finishedAction==ACTION_HIDE)
		{
			animation.object.setVisible(false);
		}else if(animation.finishedAction==ACTION_SHOW)
		{
			animation.object.setVisible(true);
		}else if(animation.finishedAction==ACTION_RESET){
			switch(animation.value){
			case X: 
				animation.object.setX((float) animation.startvalue);
				break;
			case Y: 
				animation.object.setX((float) animation.startvalue);
				break;
			case Z: 
				animation.object.setZ((float) animation.startvalue);
				break;
			case rotX: 
				animation.object.setRotX((float) animation.startvalue);
				break;
			case rotY: 
				animation.object.setRotY((float) animation.startvalue);
				break;
			case rotZ: 
				animation.object.setRotZ((float) animation.startvalue);
				break;
			case opacity: 
				animation.object.setOpacity((float) animation.startvalue);
				break;
			case custom:
				animation.customvalue.setValue(animation.startvalue);
				break;
			default:
				break;
			}
		}else if(animation.finishedAction==ACTION_REVERSE){
			if(animation.value==AnimationValue.custom){
				animateValue(animation.object, animation.customvalue, animation.startvalue, animation.speed, ACTION_NOTHING);
			}else{
				animateValue(animation.object, animation.value, animation.startvalue, animation.speed);
			}
		}
		if(animation.value==AnimationValue.custom){
			animation.customvalue.finishedAction();
		}
		animations.remove(animation);
	}
	
}
