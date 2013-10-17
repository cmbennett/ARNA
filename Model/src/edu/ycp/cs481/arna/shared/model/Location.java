package edu.ycp.cs481.arna.shared.model;

public class Location {
	
	private float x, y, z;
	
	public Location(float X, float Y, float Z) {
		x = X;
		y = Y;
		z = Z;
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
	
	// Compares this Location object to another given Location object.
	public int compareTo(Location loc) {
		if(this.x == loc.getX() && this.y == loc.getY() && this.z == loc.getZ()) {
			return 1;
		} else {
			return -1;
		}
	}
}
