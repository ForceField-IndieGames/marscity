package animation;

public interface Animatable {
	public void setX(float x);
	public void setY(float y);
	public void setZ(float z);
	public void setRotX(float x);
	public void setRotY(float y);
	public void setRotZ(float z);
	public void setOpacity(float opacity);
	public void setVisible(boolean visible);
	
	public float getX();
	public float getY();
	public float getZ();
	public float getRotX();
	public float getRotY();
	public float getRotZ();
	public float getOpacity();
	public boolean isVisible();
}
