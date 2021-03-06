package objects;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import game.EntityTexture;
import game.ResourceManager;

import animation.Animatable;

/**
 * This is a basic entity with texture, model and transforms.
 * @author Benedikt Ringlein
 */

public class Entity implements Drawable, Animatable {

	private float x = 0, y = 0, z=0;
	private float rotX=0,rotY=0,rotZ=0;
	private float scaleX=1,scaleY=1,scaleZ=1;
	private EntityTexture texture;
	private int[] displayList;
	private float destY;
	private float height;
	private boolean visible = true;
	private Entity parent;
	private List<Entity> children = new ArrayList<Entity>();

	public List<Entity> getChildren() {
		return children;
	}

	public void addChild(Entity child) {
		child.setParent(this);
		this.children.add(child);
	}
	
	public void deleteChild(Entity child)
	{
		this.children.remove(child);
	}

	public Entity()
	{
		
	}
	
	public Entity(int[] displaylist, EntityTexture texture, float x, float y, float z)
	{
			this.displayList = displaylist;
			this.texture = texture;
			this.x=x;
			this.y=y;
			this.z=z;
	}

	public Entity(int[] displaylist, EntityTexture texture)
	{
			this.displayList = displaylist;
			this.texture = texture;
	}

	public Entity(int[] displaylist, float x, float y, float z)
	{
			this.displayList = displaylist;
			this.x=x;
			this.y=y;
			this.z=z;
	}

	public int[] getDisplayList() {
		return displayList;
	}

	public void setDisplayList(int[] displayList) {
		this.displayList = displayList;
	}

	public float getHeight() {
		return height;
	}

	public void setPreferredZ(float height) {
		this.height = height;
	}

	public float getDestY() {
		return destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;
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

	public EntityTexture getTexture() {
		return texture;
	}

	public void setTexture(EntityTexture texture) {
		this.texture = texture;
	}

	@Override
	public void draw() {
		if(!isVisible())return;
		glPushMatrix();
			glTranslatef(x, y, z);
			glScalef(scaleX, scaleY, scaleZ);
			glRotatef(rotX, 1, 0, 0);
			glRotatef(rotY, 0, 1, 0);
			glRotatef(rotZ, 0, 0, 1);
			ResourceManager.drawEntity(this);
			for(Entity e:children)
			{
				e.draw();
			}
		glPopMatrix();
	}

	@Override
	public void update(int delta) {
		
	}
	
	public void click()
	{
		
	}

	@Override
	public void delete() {
		ResourceManager.deleteObject((Drawable)this);
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
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	public Entity getParent() {
		return parent;
	}

	public void setParent(Entity parent) {
		this.parent = parent;
	}
}
