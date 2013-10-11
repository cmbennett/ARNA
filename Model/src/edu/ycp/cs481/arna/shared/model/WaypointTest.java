package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class WaypointTest extends TestCase {

	private Waypoint test;
	
	@Override
	protected void setUp() throws Exception {
		test = new Waypoint();
	}
	
	public void testGetDescription() {
		assertEquals("No description.", test.getDescription());
	}
	
	public void testSetDescription() {
		test.setDescription("Test");
		assertEquals("Test", test.getDescription());
	}
	
	public void testGetLocation() {
		assertEquals(new Location(-1,-1,-1), test.getLocation());
	}
	
	public void testSetLocation() {
		test.setLocation(3,4,5);
		assertEquals(1, test.getLocation().compareTo(new Location(3,4,5)));
	}
	
}
