package game;
import static org.lwjgl.util.glu.GLU.gluLookAt;

import animation.Animatable;

/**
 * @author Benedikt Ringlein
 * This is the camera. It contains information like posiiton, rotation and zoom and
 * also applies those transformations.
 */

public class Camera implements Animatable {
	
	private float x=0,y=0,z=0;
	private float rotX=-45,rotY=0,rotZ=0;
	private float lastrotx=0, lastroty=0;
	private float zoom = 50;
	private boolean animate = false;
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
		
		
//		float cx = (float)(getX()+2*getZoom()*Math.sin(Math.toRadians(getRotY())));
//		float cy = y+zoom*zoom*0.2f;
//		float cz = (float)(getZ()+2*getZoom()*Math.cos(Math.toRadians(getRotY())));
//		
//		cx = getX()+dcos(getRotY())*dcos(getRotZ())*zoom+dcos(getRotY())*(-dsin(getRotZ()))*zoom+dsin(getRotY())*zoom;
//		cy = getY()+((-dsin(getRotX()))*(-dsin(getRotY()))*dcos(getRotZ())+dcos(getRotX())*dsin(getRotZ()))*zoom+((-dsin(getRotX()))*(-dsin(getRotY()))*(-dsin(getRotZ()))+dcos(getRotX())*dcos(getRotZ()))*zoom+(-dsin(getRotX()))*dcos(getRotY())*zoom;
//		cz = getZ()+(dcos(getRotX())*(-dsin(getRotY()))*dcos(getRotZ())+dsin(getRotX())*dsin(getRotZ()))*zoom+(dcos(getRotX())*(-dsin(getRotY()))*(-dsin(getRotZ()))+dsin(getRotX())*dcos(getRotZ()))*zoom+dcos(getRotX())*dcos(getRotY())*zoom;
		
		float cx = getX() + dsin(getRotY()) * dcos(getRotX()) * zoom;
		float cy = getY() - dsin(getRotX()) * zoom;
		float cz = getZ() + dcos(getRotX()) * dcos(getRotY()) * zoom;
		
		gluLookAt(cx, cy, cz,getX(), getY(), getZ(), 0, 1, 0);
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
