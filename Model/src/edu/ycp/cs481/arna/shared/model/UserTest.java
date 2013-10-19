package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User test_user;
	private Location test_loc;
	private Orientation test_ori;
	private Waypoint test_wp;
	
	@Override
	protected void setUp() throws Exception {
		test_loc = new Location(39.949054, -76.735405, 0.0);
		test_ori = new Orientation((float)1.0,(float)1.0,(float)1.0);
		test_user = new User(test_loc, test_ori);
		test_wp = new Waypoint(39.944222, -76.733237, 0.0);
	}
	
	/*public void testGetOrient() {
		
	}
	
	public void testGetLocation() {
		
	}
	
	public void testSetOrient() {
		
	}
	
	public void testSetLocation() {
		
	}*/
	
	public void testGetDistanceTo() {
		assertEquals(0.4, test_user.getDistanceTo(test_wp), .05);
	}
}
