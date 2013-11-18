package edu.ycp.cs481.arna.shared.model;

import java.awt.List;
import java.util.ArrayList;

public class CompassMode {
	
	private POI destination;
	private double direction;
	private double distance;
	private User user;
	private ArrayList<Integer> fifo;
	private int sum,limit;
	private double average;
	
	public CompassMode(User u, POI w) {
		 user = u;
		 destination = w;
		 direction = 0;
		 fifo = new ArrayList<Integer>();
		 sum=0;
		 limit=10;
		
	}
	
	
	public POI getDestination() {
		return destination;
	}
	
	public void setDestination(POI w) {
		destination = w;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double d) {
		distance = d;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double drctn) {
		direction = drctn; 
	}
	public int getsum(){
		return sum;	
	}
	public void setsum(int sum){
		this.sum=sum;	
	}
	public void calcsum(){
		int sum=0;
		for	(int x=0;x<getlimit();x++){
			sum=fifo.get(x)+sum;
		}
		setsum(sum);
	}
	public double getaverage(){
		return average;	
	}
	public void setaverage(double average){
		this.average=average;	
	}
	public void calcaverage(){
		calcsum();
		double aver=(double)getsum()/fifo.size();
		setaverage(aver);
	}
	public int getlimit(){
		return limit;	
	}
	public boolean fullfifo(){
		if(fifo.size()==limit){
			return true;
		}else{
			return false;
		}
	}
	public void addvalue(int value){
		if(fullfifo()==true){
			fifo.remove(0);
			fifo.add(value);
		}else{
			fifo.add(value);
		}
		}
	}
	


