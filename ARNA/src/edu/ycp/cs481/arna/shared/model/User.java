package edu.ycp.cs481.arna.shared.model;

public class User {
	private Location loc; 
	private Orientation orient; 
	
	public User(Location l, Orientation o){
		this.loc = l; 
		this.orient = o; 
	}
	
	
	public User() {
		loc = new Location(); 
		orient = new Orientation(); 
	}


	public void setOrient(double az, double pitch, double roll){
		this.orient.setAzimuth(az); 
		this.orient.setPitch(pitch);
		this.orient.setRoll(roll); 
	}
	
	public void setLocation(double lat, double lon, double elev){
		this.loc.setLatitude(lat); 
		this.loc.setLongitude(lon); 
		this.loc.setElevation(elev); 
	}
	
	public Location getLocation() {
		return loc; 
	}
	
	public Orientation getOrient() {
		return orient; 
	}
	
	// Finds the distance from the user to a given waypoint.
		public double getDistanceTo(Waypoint w) {
			double earth_radius = 3958.75;
			
			double diff_lat = Math.toRadians(w.getLocation().getLatitude() - loc.getLatitude());
			double diff_long = Math.toRadians(w.getLocation().getLongitude() - loc.getLongitude());
			
			double sin_diff_lat = Math.sin(diff_lat/2);
			double sin_diff_long = Math.sin(diff_long/2);
			
			double a = Math.pow(sin_diff_lat, 2) + Math.pow(sin_diff_long, 2)
					* Math.cos(Math.toRadians(loc.getLatitude())) * Math.cos(Math.toRadians(w.getLocation().getLatitude()));
			
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			
			double distance = earth_radius * c;
			
			return distance;
		}
}