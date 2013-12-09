package edu.ycp.cs481.shared.persistence;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;


public class addingTourModeWaypoints {

	public addingTourModeWaypoints(TourMode tour) {
		// adding waypoints to the tour mode		 
		POI kinsley = new POI(39.949120, -76.735165,75.0);
		kinsley.setName("Kinsley Enginnering Center");
		
		POI northSide = new POI(39.949792, -76.734041,92.0);
		northSide.setName("North Side Commons");
		
		POI campbellHall = new POI(39.946461, -76.730509,31.0);
		campbellHall.setName("Campbell Hall");
		
		POI schmidtLibrary = new POI(39.947177, -76.729852,17.0);
		schmidtLibrary.setName("Schmidt Library");
		
		POI millerAdmin = new POI(39.946165, -76.727993,43.0);
		millerAdmin.setName("Miller Administration Building");		
		
		
		// adding the waypoints to the tour Mode
		tour.addWaypoint(kinsley);
		tour.addWaypoint(northSide);
		tour.addWaypoint(campbellHall);
		tour.addWaypoint(schmidtLibrary);
		tour.addWaypoint(millerAdmin);
	 }

}
