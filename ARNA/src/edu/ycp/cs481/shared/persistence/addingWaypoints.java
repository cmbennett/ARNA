package edu.ycp.cs481.shared.persistence;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;


public class addingWaypoints {	

	
	private ArrayList<POI> pois;
	public addingWaypoints(TourMode tour) {
		pois  = new ArrayList<POI>();
			    
		// adding waypoints to the tour mode		 
		POI kinsley = new POI(39.949120, -76.735165,120.396);
		kinsley.setName("Kinsley Enginnering Center");
		kinsley.setDescription("Home to mechanical, electrical, and computer engineering students. It contains state-of-the-art " +
				"laboratories, equipment, computers, and design facilities including a mechatronics lab, " +
				"materials science and engineering lab, machine shop and welding lab, thermodynamics and " +
				"fluid mechanics lab, and CAD lab.");
		
		POI northSide = new POI(39.949792, -76.734041,121.45);
		northSide.setName("North Side Commons");
		northSide.setDescription("A co-ed, traditional style residence hall that features both singles and" +
				" doubles and houses a mix of new and returning students.");
		
		POI campbellHall = new POI(39.946461, -76.730509,128.321);
		campbellHall.setName("Campbell Hall");
		campbellHall.setDescription("Campbell is home to Academic Services, which includes " +
				"Academic Advising, the Center for Professional Excellence, the Career Development " +
				"Center, and the Center for Teaching and Learning. Campbell also includes" +
				" state-of-the-art chemistry instrumentation to support majors in Chemistry," +
				" Forensic Chemistry and Pre-Med");
		
		POI schmidtLibrary = new POI(39.947177, -76.729852,126.492);
		schmidtLibrary.setName("Schmidt Library");
		schmidtLibrary.setDescription("YCP's library provides dynamic group study spaces, quiet study " +
				"floors, wireless laptops for use in the library, technology-enhanced group study " +
				"rooms, comfortable lounge areas, wireless network connections including the outdoor" +
				" courtyard, college archives, and Special Collections.");
		
		POI millerAdmin = new POI(39.946165, -76.727993,126.797);
		millerAdmin.setName("Miller Administration Building");		
		millerAdmin.setDescription("Bearing the name of the College's first president, " +
				"it includes the President's Office, Admissions, Academic Affairs, Business Affairs, " +
				"Campus Operations, Financial Aid Office and Registrar.");
		
		
		// adding the waypoints to POI array list
		
		pois.add(kinsley);
		pois.add(northSide);
		pois.add(campbellHall);
		pois.add(schmidtLibrary);
		pois.add(millerAdmin);
	 }
	
	public ArrayList<POI> getPOI()
	{
		return pois;
		
	}

}
