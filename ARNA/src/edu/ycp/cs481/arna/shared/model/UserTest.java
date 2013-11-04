package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User test_user;
	private Location test_loc;
	private Orientation test_ori;
	private POI test_wp;
	
	
	@Override
	protected void setUp() throws Exception {
		test_loc = new Location(39.949054, -76.735405, 0.0);
		test_ori = new Orientation(52.0,100.0,1.0);
		test_user = new User(test_loc, test_ori);
		test_wp = new POI(39.944222, -76.733237, 0.0);
	}
	
	public void testGetOrient() {
		assertEquals(52.0, test_user.getOrient().getAzimuth()); 
		assertEquals(100.0, test_user.getOrient().getPitch());
		assertEquals(1.0, test_user.getOrient().getRoll());
	}
	
	public void testGetLocation() {
		assertEquals(39.949054, test_user.getLocation().getLatitude()); 
		assertEquals(-76.735405, test_user.getLocation().getLongitude());
		assertEquals(0.0, test_user.getLocation().getElevation());
		
	}
	
	public void testSetOrient() {
		test_user.setOrient(100.0, 50.0, 10.0);
		assertEquals(100.0, test_user.getOrient().getAzimuth()); 
		assertEquals(50.0, test_user.getOrient().getPitch());
		assertEquals(10.0, test_user.getOrient().getRoll());
	}
	
	public void testSetLocation() {
		test_user.setLocation(38.76653, -77.735405, 5.0); 
		assertEquals(38.76653, test_user.getLocation().getLatitude()); 
		assertEquals(-77.735405, test_user.getLocation().getLongitude());
		assertEquals(5.0, test_user.getLocation().getElevation());
	}
	
	public void testGetDistanceTo() {
		assertEquals(0.4, test_user.getDistanceTo(test_wp), .05);
	}
}
