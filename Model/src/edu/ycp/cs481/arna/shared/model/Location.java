package edu.ycp.cs481.arna.shared.model;

public class Location {
	
	private int x, y, z;
	
	public Location(int X, int Y, int Z) {
		x = X;
		y = Y;
		z = Z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
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
