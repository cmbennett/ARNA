package edu.ycp.cs481.arna.shared.model;

import edu.ycp.cs481.arna.shared.model.CompassMode;
import junit.framework.TestCase;

public class CompassModeTest extends TestCase {

	private CompassMode test;
	private User user;
	private POI way;
	
	@Override
	protected void setUp() throws Exception {
		user  = new User(new Location(), new Orientation());
		way = new POI(3.0,4.0,0.0);
		test = new CompassMode(user, way);
	}
	
	public void testGetDestination() throws Exception {
		assertEquals(way, test.getDestination());
	}
	
	public void testSetDestination() throws Exception {
		POI way2 = new POI(1.0,1.0,1.0);
		test.setDestination(way2);
		assertEquals(way2, test.getDestination());
	}

	public void testGetUser() throws Exception {
		assertEquals(user, test.getUser());
	}

	public void testSetUser() throws Exception {
		User user2 = new User(new Location(1.0,1.0,1.0), new Orientation());
		test.setUser(user2);
		assertEquals(user2, test.getUser());
	}

	public void testGetDistance() throws Exception {
		assertEquals(0.0, test.getDistance());
	}
	
	public void testGetDirection() throws Exception {
		assertEquals(0.0, test.getDirection());
	}
	
	public void testSetDirection() throws Exception {
		test.setDirection(30);
		assertEquals(30.0, test.getDirection());
	}
}
