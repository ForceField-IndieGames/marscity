package effects;

/**
 * @author Benedikt Ringlein
 * This class describes a particle that is used by the particle system.
 * It is used only as a data structure to contain the necessary variables.
 */

public class Particle {

	private float x = 0, y = 0, z = 0;
	private float vx = 0, vy = 0, vz = 0;
	private float size = 1;
	private float lifetime = 100;
	private float initlifetime = 100;
	private boolean visible = true;

	public Particle(float x, float y, float z, float vx, float vy, float vz,
			float size, float lifetime) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		this.size = size;
		this.lifetime = lifetime;
		this.initlifetime = lifetime;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getVz() {
		return vz;
	}

	public void setVz(float vz) {
		this.vz = vz;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
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

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	public float getLifetime() {
		return lifetime;
	}

	public void setLifetime(float lifetime) {
		this.lifetime = lifetime;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getInitlifetime() {
		return initlifetime;
	}

	public void setInitlifetime(float initlifetime) {
		this.initlifetime = initlifetime;
	}

}
