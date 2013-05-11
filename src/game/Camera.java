package game;
import static org.lwjgl.util.glu.GLU.gluLookAt;


public class Camera implements Animatable {
	
	private float x=0,y=40,z=-100;
	private float rotX=0,rotY=0,rotZ=0;
	private float zoom = 300;
	private boolean animate = false;
	public boolean isAnimate() {
		return animate;
	}

	public void setAnimate(boolean animate) {
		this.animate = animate;
	}

	public void applyTransform()
	{
		gluLookAt((float)(getX()+2*getZoom()*Math.sin(Math.toRadians(getRotY()))), y+zoom*zoom*0.01f, (float)(getZ()+2*getZoom()*Math.cos(Math.toRadians(getRotY()))),
				getX(), getY(), getZ(), 
				0, 1, 0);
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
}
