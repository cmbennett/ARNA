package edu.ycp.cs481.arna.shared.model;

import java.util.List;

import junit.framework.TestCase;

public class ModeTest extends TestCase {

	private Mode test;
	private User user;
	private Location loc;
	private Orientation orient;
	List<Waypoint> wpList;

	@Override
	protected void setUp() throws Exception {
		loc=new Location(0.2,3.6,1.3);
		orient=new Orientation(45.5,2.3,56.7); 
		user=new User(loc, orient);
		test = new Mode(user,wpList);
		

	}
	public void testgetuser() throws Exception {
		assertEquals(user, test.getUser());
	}
	public void testsetuser() throws Exception {
		test.setUser(user);
		assertEquals(user, test.getUser());
	}
	public void testsetWpList() throws Exception {
		test.setWpList(wpList);
		assertEquals(wpList, test.getWpList());
	}
	public void testgetWpList() throws Exception {
		assertEquals(wpList, test.getWpList());
	}
	public void testaddWaypoint() throws Exception {
		assertEquals(wpList, test.getWpList());
	}

}
