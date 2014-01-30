package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class LocationTest extends TestCase {

	private Location test;
	
	@Override
	protected void setUp() throws Exception {
		test = new Location(1,1,1);
	}
	
	public void testGetX() throws Exception {
		assertEquals(1.0, test.getLatitude());
	}
	
	public void testsetLatitude() throws Exception {
		test.setLatitude(3.0);
		assertEquals(3.0, test.getLatitude());
	}
	
	public void testGetY() throws Exception {
		assertEquals(1.0, test.getLongitude());
	}
	
	public void testSetY() throws Exception {
		test.setLongitude(6.0);
		assertEquals(6.0, test.getLongitude());
	}
	
	public void testGetZ() throws Exception {
		assertEquals(1.0, test.getElevation());
	}
	
	public void testSetZ() throws Exception {
		test.setElevation(9.0);
		assertEquals(9.0, test.getElevation());
	}
	
	public void testCompareTo() throws Exception {
		assertEquals(-1, test.compareTo(new Location(0,0,0)));
		assertEquals(1, test.compareTo(new Location(1,1,1)));
	}
}
