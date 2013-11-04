package edu.ycp.cs481.arna.shared.model;

public class Vector {
	private double x; 
	private double y; 
	private double z; 
	
	public Vector(){
		x = 0; 
		y = 0; 
		z = 0; 
	}
	
	public Vector(double x2, double y2, double z2){
		this.x = x2; 
		this.y = y2; 
		this.z = z2; 
	}
	
	public void setVector(float x, float y, float z){
		this.x = x; 
		this.y = y; 
		this.z = z; 
	}
	
	public double getX(){
		return x; 
	}
	
	public double getY(){
		return y; 
	}

	public double getZ(){
		return z; 
	}
}
