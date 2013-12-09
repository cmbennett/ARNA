package edu.ycp.cs481.shared.persistence;

import edu.ycp.cs481.arna.shared.model.CompassMode;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;

public class addingCompassModeWaypoints {

	public addingCompassModeWaypoints(CompassMode compass) {
		// TODO Auto-generated constructor stub
	
	
		POI kinsley = new POI(39.949120, -76.735165,9.7536);
		kinsley.setName("Kinsley Enginnering Center");
		
		POI northSide = new POI(39.949792, -76.734041,28.3464);
		northSide.setName("North Side Commons");
		
		POI campbellHall = new POI(39.946461, -76.730509,9.4488);
		campbellHall.setName("Campbell Hall");
		
		POI schmidtLibrary = new POI(39.947177, -76.729852,17.0);
		schmidtLibrary.setName("Schmidt Library");
		
		POI millerAdmin = new POI(39.946165, -76.727993,5.1816);
		millerAdmin.setName("Miller Administration Building");		
		
		
		// adding the waypoints to the compass Mode
		compass.addWaypoint(kinsley);
		compass.addWaypoint(northSide);
		compass.addWaypoint(campbellHall);
		compass.addWaypoint(schmidtLibrary);
		compass.addWaypoint(millerAdmin);
	 }
	
}
