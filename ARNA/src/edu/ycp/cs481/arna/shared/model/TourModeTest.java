package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class TourModeTest extends TestCase {
	
	private TourMode test;
	private User user;
	private POI poi;
	
	//@Override
	protected void setUp() throws Exception {
		user = new User();
		poi = new POI(39.949120, -76.735165,0.0); // kinsley
		test = new TourMode();
		test.setUser(user); 
		user.setLocation(39.949778, -76.734095,0.0); // middle of the road outside Kinsley
		test.addWaypoint(poi);

	}
	
	public void testGetOnScreen() throws Exception {
		
	}
	
	public void testSetOnScreen() throws Exception {
		
	}
	
	public void testPopulateOnScreen() throws Exception {
		user.setOrient(240.0,90.0,1.0);
		test.populateOnScreen(54.0);	
		assertEquals(poi,test.getOnScreen().get(0));
		
		
		user.setOrient(90.0, 90.0, 1.0); 
		test.populateOnScreen(54.0); 
		assertTrue(test.getOnScreen().isEmpty());
		
		
		/*//West
		user.setLocation(39.949383, -76.732899,0.0); 
		user.setOrient(90.0, 90.0, 1.0); 
		test.populateOnScreen(54.0);
		System.out.println(user.getBearingTo(poi)); 
		assertTrue(test.getOnScreen().isEmpty());
		
		//East
		user.setLocation(39.949030, -76.737341,0.0); 
		user.setOrient(90.0, 90.0, 1.0); 
		test.populateOnScreen(54.0); 
		System.out.println(user.getBearingTo(poi)); 
		assertTrue(test.getOnScreen().isEmpty());
		*/
	}
	
	public void testComputeDisplacementVector() throws Exception {
		
	}
}
