package edu.ycp.cs481.shared.persistence;

import edu.ycp.cs481.arna.shared.model.CompassMode;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;

public class addingCompassModeWaypoints {

	public addingCompassModeWaypoints(CompassMode compass) {
		// TODO Auto-generated constructor stub
	
		POI kinsley = new POI(39.949120, -76.735165,120.396);
		kinsley.setName("Kinsley Enginnering Center");
		
		
		POI northSide = new POI(39.949792, -76.734041,121.31);
		northSide.setName("North Side Commons");
		
		
		POI campbellHall = new POI(39.946461, -76.730509,127.406);
		campbellHall.setName("Campbell Hall");
		
		
		POI schmidtLibrary = new POI(39.947177, -76.729852,124.663);
		schmidtLibrary.setName("Schmidt Library");

		
		POI millerAdmin = new POI(39.946165, -76.727993,125.273);
		millerAdmin.setName("Miller Administration Building");		

		
		
		// adding the waypoints to the compass Mode
		compass.addWaypoint(kinsley);
		compass.addWaypoint(northSide);
		compass.addWaypoint(campbellHall);
		compass.addWaypoint(schmidtLibrary);
		compass.addWaypoint(millerAdmin);
	 }
	
}
