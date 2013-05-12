package objects;

public interface Drawable {
	public void draw();
	public void update(int delta);
	public void delete();
	
	public void setX(float x);
	public void setY(float y);
	public void setZ(float z);
	public void setRotX(float x);
	public void setRotY(float y);
	public void setRotZ(float z);
	public void setScaleX(float x);
	public void setScaleY(float y);
	public void setScaleZ(float z);
	
	public float getX();
	public float getY();
	public float getZ();
	public float getRotX();
	public float getRotY();
	public float getRotZ();
	public float getScaleX();
	public float getScaleY();
	public float getScaleZ();
	public float getDestY();
	public float getPreferredY();
}
