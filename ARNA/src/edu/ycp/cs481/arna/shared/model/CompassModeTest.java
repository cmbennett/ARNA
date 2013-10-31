package edu.ycp.cs481.arna.shared.model;

import edu.ycp.cs481.arna.shared.model.CompassMode;
import junit.framework.TestCase;

public class CompassModeTest extends TestCase {

	private CompassMode test;
	private User user;
	private Waypoint way;
	
	@Override
	protected void setUp() throws Exception {
		user  = new User(new Location(), new Orientation());
		way = new Waypoint(3.0,4.0,5.0);
		test = new CompassMode(user, way);
	}
	
	public void testGetDestination() throws Exception {
		assertEquals(way, test.getDestination());
	}
	
	public void testSetDestination() throws Exception {
		Waypoint way2 = new Waypoint(1.0,1.0,1.0);
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
		assertEquals(5.0, test.getDistance());
	}
	
	public void testGetDirection() throws Exception {
		Waypoint way3 = new Waypoint(0.8660254037844386,0.5,0);
		test.setDestination(way3);
		assertEquals(30.0, test.getDirection(), 0.01);
	}
}
