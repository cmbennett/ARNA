package edu.ycp.cs481.arna.shared.model;

public class Orientation {
	private double azimuth; 
	private double pitch;
	private double roll; 
	
	public Orientation(double az, double pitch, double roll){
		this.azimuth = az; 
		this.pitch = pitch; 
		this.roll = roll;
	}

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double az) {
		this.azimuth = az;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
	}
	
	
}
