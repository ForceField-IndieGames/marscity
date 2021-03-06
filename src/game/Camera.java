package game;
import static org.lwjgl.util.glu.GLU.gluLookAt;

import animation.Animatable;

/**
 * @author Benedikt Ringlein
 * This is the camera. It contains information like posiiton, rotation and zoom and
 * also applies those transformations.
 */

public class Camera implements Animatable {
	
	private float x,y,z;
	private float rotX,rotY,rotZ;
	private float lastrotx=0, lastroty=0;
	private float zoom;
	private boolean animate = false;
	private float cx,cy,cz;
	
	public float getCx() {
		return cx;
	}

	public float getCy() {
		return cy;
	}

	public float getCz() {
		return cz;
	}

	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}
	
	private float dcos(float f){
		return (float) Math.cos(Math.toRadians(f));
	}
	private float dsin(float f){
		return (float) Math.sin(Math.toRadians(f));
	}

	public void applyTransform()
	{
		float rx = getRotX();
		if(rx<-89)rx=-89;
		if(rx>-1)rx=-1;
		
		cx = getX() + dsin(getRotY()) * dcos(rx) * zoom;
		cy = getY() - dsin(rx) * zoom;
		cz = getZ() + dcos(rx) * dcos(getRotY()) * zoom;
		
		gluLookAt(cx, cy, cz,getX(), getY()+((getRotX()>0)?getRotX()/10:0), getZ(), 0, 1, 0);
	}
	
	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public Camera()
	{
		
	}

	

	@Override
	public void setOpacity(float opacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public float getLastroty() {
		return lastroty;
	}

	public void setLastroty() {
		this.lastroty = getRotY();
	}

	public float getLastrotx() {
		return lastrotx;
	}

	public void setLastrotx() {
		this.lastrotx = getRotX();
	}
	
	public boolean wasRotated()
	{
		if(getLastrotx()!=getRotX()||getLastroty()!=getRotY())return true;
		return false;
	}
}
