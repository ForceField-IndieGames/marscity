package animation;

import game.Main;
import game.ResourceManager;
import gui.guiElement;

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
	float startvalue;
	float destValue;
	float speed;
	int finishedAction = AnimationManager.ACTION_NOTHING;

	public Animation(Animatable object, AnimationValue value, float startvalue, float destValue, float speed, int finishedAction){
		this.object=object;
		this.value = value;
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
	
	public static void animateValue(Animatable object, AnimationValue value, float destValue, float speed)
	{
		animateValue(object, value, destValue, speed, ACTION_NOTHING);
		
	}
	public static void animateValue(Animatable object, AnimationValue value, float destValue, float speed, int action)
	{
		switch(value){
		case X:
			animations.add(new Animation(object, value, object.getX() , destValue, speed, action));
			break;
		case Y:
			animations.add(new Animation(object, value, object.getY() , destValue, speed, action));
			break;
		case Z:
			animations.add(new Animation(object, value, object.getZ() , destValue, speed, action));
			break;
		case rotX:
			animations.add(new Animation(object, value, object.getRotX() , destValue, speed, action));
			break;
		case rotY:
			animations.add(new Animation(object, value, object.getRotY() , destValue, speed, action));
			break;
		case rotZ:
			animations.add(new Animation(object, value, object.getRotZ() , destValue, speed, action));
			break;
		case opacity:
			animations.add(new Animation(object, value, object.getOpacity() , destValue, speed, action));
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
							animation.object.setX(animation.object.getX()+delta*animation.speed);
						}else{
							animation.object.setX(animation.object.getX()-delta*animation.speed);
						}
					}else{
						animation.object.setX(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case Y: 
					if(Math.abs(animation.object.getY()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getY()<animation.destValue){
							animation.object.setY(animation.object.getY()+delta*animation.speed);
						}else{
							animation.object.setY(animation.object.getY()-delta*animation.speed);
						}
					}else{
						animation.object.setY(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case Z: 
					if(Math.abs(animation.object.getZ()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getZ()<animation.destValue){
							animation.object.setZ(animation.object.getZ()+delta*animation.speed);
						}else{
							animation.object.setZ(animation.object.getZ()-delta*animation.speed);
						}
					}else{
						animation.object.setZ(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotX: 
					if(Math.abs(animation.object.getRotX()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotX()<animation.destValue){
							animation.object.setRotX(animation.object.getRotX()+delta*animation.speed);
						}else{
							animation.object.setRotX(animation.object.getRotX()-delta*animation.speed);
						}
					}else{
						animation.object.setRotX(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotY: 
					if(Math.abs(animation.object.getRotY()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotY()<animation.destValue){
							animation.object.setRotY(animation.object.getRotY()+delta*animation.speed);
						}else{
							animation.object.setRotY(animation.object.getRotY()-delta*animation.speed);
						}
					}else{
						animation.object.setRotY(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case rotZ: 
					if(Math.abs(animation.object.getRotZ()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getRotZ()<animation.destValue){
							animation.object.setRotZ(animation.object.getRotZ()+delta*animation.speed);
						}else{
							animation.object.setRotZ(animation.object.getRotZ()-delta*animation.speed);
						}
					}else{
						animation.object.setRotZ(animation.destValue);
						ExecuteFinishedAction(animation);
						return;
					}
				break;
				case opacity:
					if(Math.abs(animation.object.getOpacity()-animation.destValue)>=delta*animation.speed){
						if(animation.object.getOpacity()<animation.destValue){
							animation.object.setOpacity(animation.object.getOpacity()+delta*animation.speed);
						}else{
							animation.object.setOpacity(animation.object.getOpacity()-delta*animation.speed);
						}
					}else{
						animation.object.setOpacity(animation.destValue);
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
			Main.gui.remove((guiElement)animation.object);
		}else if(animation.finishedAction==ACTION_HIDE)
		{
			animation.object.setVisible(false);
		}else if(animation.finishedAction==ACTION_SHOW)
		{
			animation.object.setVisible(true);
		}else if(animation.finishedAction==ACTION_RESET){
			switch(animation.value){
			case X: 
				animation.object.setX(animation.startvalue);
				break;
			case Y: 
				animation.object.setX(animation.startvalue);
				break;
			case Z: 
				animation.object.setZ(animation.startvalue);
				break;
			case rotX: 
				animation.object.setRotX(animation.startvalue);
				break;
			case rotY: 
				animation.object.setRotY(animation.startvalue);
				break;
			case rotZ: 
				animation.object.setRotZ(animation.startvalue);
				break;
			case opacity: 
				animation.object.setOpacity(animation.startvalue);
				break;
			}
		}else if(animation.finishedAction==ACTION_REVERSE){
			animateValue(animation.object, animation.value, animation.startvalue, animation.speed);
		}
		animations.remove(animation);
	}
	
}
