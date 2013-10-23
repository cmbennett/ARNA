package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class WaypointTest extends TestCase {

	private Waypoint test;
	
	@Override
	protected void setUp() throws Exception {
		//GPS coordinates of Kinsley Engineering Center
		test = new Waypoint(39.949054, -76.735405, 0.0);	
	}
	
	public void testGetDescription() {
		assertEquals("No Description.", test.getDescription());
	}
	
	public void testSetDescription() {
		test.setDescription("Test");
		assertEquals("Test", test.getDescription());
	}
	
	public void testGetLocation() {
		
		assertEquals(39.949054, test.getLocation().getLatitude());
		assertEquals(-76.735405, test.getLocation().getLongitude());
		assertEquals(0.0, test.getLocation().getElevation());
	}
	
	public void testSetLocation() {
		test.setLocation(3.0,4.0,5.0);
		assertEquals(1, test.getLocation().compareTo(new Location(3.0,4.0,5.0)));
	}
	
}