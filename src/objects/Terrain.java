package objects;
import game.ResourceManager;

import org.newdawn.slick.opengl.Texture;


public class Terrain extends Entity {
	private float x = 0, y = 0, z=-100;
	private float rotX=0,rotY=0,rotZ=0;
	private float scaleX=1,scaleY=1,scaleZ=1;
	private Texture texture;
	private int displayList;
	private Model model;
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
	public float getScaleX() {
		return scaleX;
	}
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	public float getScaleZ() {
		return scaleZ;
	}
	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public int getDisplayList() {
		return displayList;
	}
	public void setDisplayList(int displayList) {
		this.displayList = displayList;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Terrain()
	{
		super(ResourceManager.OBJECT_TERRAIN,ResourceManager.TEXTURE_TERRAIN);
	}
	public Terrain(int x, int y, int z)
	{
		super(ResourceManager.OBJECT_TERRAIN,ResourceManager.TEXTURE_TERRAIN,x,y,z);
	}
}
