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
		assertEquals(5.0, test.getDistance());
	}
	
	public void testGetDirection() throws Exception {
		assertEquals(0, test.getDirection());
	}
	
	public void testSetDirection() throws Exception {
		test.setDirection(30);
		assertEquals(30, test.getDirection());
	}
	public void testGetSum() throws Exception {
		assertEquals(0, test.getsum());
	}
	
	public void testSetSum() throws Exception {
		test.setsum(30);
		assertEquals(30, test.getsum());
	}
	public void testGetaverage() throws Exception {
		assertEquals(0, test.getsum());
	}
	
	public void testSetaverage() throws Exception {
		test.setaverage(6.5);
		assertEquals(6.5, test.getaverage());
	}
	public void testGetlimit() throws Exception {
		assertEquals(10, test.getlimit());
	}
	public void testfifofull() throws Exception {
		assertEquals(false, test.fullfifo());
	}
	public void testadding() throws Exception {
		test.addvalue(1);
		assertEquals(1, test.getvalue(0));
		test.addvalue(2);
		assertEquals(2, test.getvalue(1));
		test.addvalue(3);
		assertEquals(3, test.getvalue(2));
		test.addvalue(4);
		assertEquals(4, test.getvalue(3));
		test.addvalue(5);
		assertEquals(5, test.getvalue(4));
		test.addvalue(6);
		assertEquals(6, test.getvalue(5));
		test.addvalue(7);
		assertEquals(7, test.getvalue(6));
		test.addvalue(8);
		assertEquals(8, test.getvalue(7));
		test.addvalue(9);
		assertEquals(9, test.getvalue(8));
		test.addvalue(10);
		assertEquals(10, test.getvalue(9));
		assertEquals(true, test.fullfifo());
		test.addvalue(11);
		assertEquals(10, test.getvalue(9));
		assertEquals(2, test.getvalue(0));
		test.calcsum();
		assertEquals(65, test.getsum());
		 test.calcaverage();
		 assertEquals(6.5, test.getaverage());
	}
}
