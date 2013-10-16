package edu.ycp.cs481.arna.shared.model;

public class Orientation {
	private float azimuth; 
	private float pitch;
	private float roll; 
	
	public Orientation(float az, float pitch, float roll){
		this.azimuth = az; 
		this.pitch = pitch; 
		this.roll = roll;
	}

	public float getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
}
