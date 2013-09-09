package animation;

import game.Main;
import game.ResourceManager;
import gui.GuiElement;

import java.util.ArrayList;
import java.util.List;

import objects.Buildings;

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
	FinishedAction finishedAction = FinishedAction.NOTHING;

	public Animation(Animatable object, AnimationValue value, CustomAnimationValue customvalue, double startvalue, double destValue, double speed, FinishedAction finishedAction){
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
	
	private static List<Animation> animations = new ArrayList<Animation>();
	
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, int time)
	{
		animateValue(object, customvalue, destValue, time, FinishedAction.NOTHING);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, int time, FinishedAction action)
	{
		double speed = Math.abs(destValue-customvalue.getValue())/time;
		animateValue(object, customvalue, destValue, speed, action);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, double speed)
	{
		animateValue(object, customvalue, destValue, speed, FinishedAction.NOTHING);
	}
	public static void animateValue(Animatable object, CustomAnimationValue customvalue, double destValue, double speed, FinishedAction action)
	{
		for(int i=0;i<animations.size();i++){
			if(animations.get(i).object==object&&animations.get(i).value==AnimationValue.CUSTOM){
				animations.remove(i);
				i--;
			}
		}
		animations.add(new Animation(object, AnimationValue.CUSTOM, customvalue, customvalue.getValue(), destValue, speed, action));
	}
	public static void animateValue(Animatable object, AnimationValue value, float destValue, int time){
		animateValue(object, value, destValue, time, FinishedAction.NOTHING);
	}
	/**
	 * Animates a value
	 * @param object The object to be animated
	 * @param value The value that should be animated
	 * @param destValue The value at the end of the animation
	 * @param time The duration of the animation in milliseconds
	 * @param action The actions that should be done when the animation is finished
	 */
	public static void animateValue(Animatable object, AnimationValue value, double destValue, int time, FinishedAction action)
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
		case ROTX:
			speed = Math.abs(destValue-object.getRotX())/time;
			break;
		case ROTY:
			speed = Math.abs(destValue-object.getRotY())/time;
			break;
		case ROTZ:
			speed = Math.abs(destValue-object.getRotZ())/time;
			break;
		case OPACITY:
			speed = Math.abs(destValue-object.getOpacity())/time;
			break;
		default:
			return;
		}
		animateValue(object, value, destValue, speed, action);
		
	}
	public static void animateValue(Animatable object, AnimationValue value, double destValue, double speed)
	{
		animateValue(object, value, destValue, speed, FinishedAction.NOTHING);
		
	}
	public static void animateValue(Animatable object, AnimationValue value, double destValue, double speed, FinishedAction action)
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
		case ROTX:
			animations.add(new Animation(object, value, null, object.getRotX() , destValue, speed, action));
			break;
		case ROTY:
			animations.add(new Animation(object, value, null, object.getRotY() , destValue, speed, action));
			break;
		case ROTZ:
			animations.add(new Animation(object, value, null, object.getRotZ() , destValue, speed, action));
			break;
		case OPACITY:
			animations.add(new Animation(object, value, null, object.getOpacity() , destValue, speed, action));
			break;
		default:
			break;
		}
	}
	
	public static void update(int delta)
	{
		for(int i=0;i<animations.size();i++)
		{
			switch(animations.get(i).value)
			{
				case X: 
					if(Math.abs(animations.get(i).object.getX()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getX()<animations.get(i).destValue){
							animations.get(i).object.setX((float) (animations.get(i).object.getX()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setX((float) (animations.get(i).object.getX()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setX((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case Y: 
					if(Math.abs(animations.get(i).object.getY()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getY()<animations.get(i).destValue){
							animations.get(i).object.setY((float) (animations.get(i).object.getY()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setY((float) (animations.get(i).object.getY()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setY((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case Z: 
					if(Math.abs(animations.get(i).object.getZ()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getZ()<animations.get(i).destValue){
							animations.get(i).object.setZ((float) (animations.get(i).object.getZ()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setZ((float) (animations.get(i).object.getZ()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setZ((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case ROTX: 
					if(Math.abs(animations.get(i).object.getRotX()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getRotX()<animations.get(i).destValue){
							animations.get(i).object.setRotX((float) (animations.get(i).object.getRotX()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setRotX((float) (animations.get(i).object.getRotX()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setRotX((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case ROTY: 
					if(Math.abs(animations.get(i).object.getRotY()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getRotY()<animations.get(i).destValue){
							animations.get(i).object.setRotY((float) (animations.get(i).object.getRotY()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setRotY((float) (animations.get(i).object.getRotY()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setRotY((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case ROTZ: 
					if(Math.abs(animations.get(i).object.getRotZ()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getRotZ()<animations.get(i).destValue){
							animations.get(i).object.setRotZ((float) (animations.get(i).object.getRotZ()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setRotZ((float) (animations.get(i).object.getRotZ()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setRotZ((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
				break;
				case OPACITY:
					if(Math.abs(animations.get(i).object.getOpacity()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).object.getOpacity()<animations.get(i).destValue){
							animations.get(i).object.setOpacity((float) (animations.get(i).object.getOpacity()+delta*animations.get(i).speed));
						}else{
							animations.get(i).object.setOpacity((float) (animations.get(i).object.getOpacity()-delta*animations.get(i).speed));
						}
					}else{
						animations.get(i).object.setOpacity((float) animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
						return;
					}
					break;
				case CUSTOM:
					if(Math.abs(animations.get(i).customvalue.getValue()-animations.get(i).destValue)>=delta*animations.get(i).speed){
						if(animations.get(i).customvalue.getValue()<animations.get(i).destValue){
							animations.get(i).customvalue.setValue(animations.get(i).customvalue.getValue()+delta*animations.get(i).speed);
						}else{
							animations.get(i).customvalue.setValue(animations.get(i).customvalue.getValue()-delta*animations.get(i).speed);
						}
					}else{
						animations.get(i).customvalue.setValue(animations.get(i).destValue);
						ExecuteFinishedAction(animations.get(i));
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
		if(animation.finishedAction==FinishedAction.DELETE)
		{
			ResourceManager.deleteObject(animation.object);
			Buildings.refreshSupply();
		}else if(animation.finishedAction==FinishedAction.REMOVEGUI)
		{
			Main.gui.remove((GuiElement)animation.object);
		}else if(animation.finishedAction==FinishedAction.HIDE)
		{
			animation.object.setVisible(false);
		}else if(animation.finishedAction==FinishedAction.SHOW)
		{
			animation.object.setVisible(true);
		}else if(animation.finishedAction==FinishedAction.RESET){
			switch(animation.value){
			case X: 
				animation.object.setX((float) animation.startvalue);
				break;
			case Y: 
				animation.object.setY((float) animation.startvalue);
				break;
			case Z: 
				animation.object.setZ((float) animation.startvalue);
				break;
			case ROTX: 
				animation.object.setRotX((float) animation.startvalue);
				break;
			case ROTY: 
				animation.object.setRotY((float) animation.startvalue);
				break;
			case ROTZ: 
				animation.object.setRotZ((float) animation.startvalue);
				break;
			case OPACITY: 
				animation.object.setOpacity((float) animation.startvalue);
				break;
			case CUSTOM:
				animation.customvalue.setValue(animation.startvalue);
				break;
			default:
				break;
			}
		}else if(animation.finishedAction==FinishedAction.REVERSE){
			if(animation.value==AnimationValue.CUSTOM){
				animateValue(animation.object, animation.customvalue, animation.startvalue, animation.speed, FinishedAction.NOTHING);
			}else{
				animateValue(animation.object, animation.value, animation.startvalue, animation.speed);
			}
		}else if(animation.finishedAction==FinishedAction.REVERSEREPEAT){
			if(animation.value==AnimationValue.CUSTOM){
				animateValue(animation.object, animation.customvalue, animation.startvalue, animation.speed, FinishedAction.REVERSEREPEAT);
			}else{
				animateValue(animation.object, animation.value, animation.startvalue, animation.speed,FinishedAction.REVERSEREPEAT);
			}
		}
		if(animation.value==AnimationValue.CUSTOM){
			animation.customvalue.finishedAction();
		}
		animations.remove(animation);
	}
	
}
