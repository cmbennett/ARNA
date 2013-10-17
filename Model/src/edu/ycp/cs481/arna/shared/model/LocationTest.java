package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class LocationTest extends TestCase {

	private Location test;
	
	@Override
	protected void setUp() throws Exception {
		test = new Location(1.0,1.0,1.0);
	}
	
	public void testGetLatitude() throws Exception {
			assertEquals(1.0, test.getLatitude());
	}
	
	public void testSetLatitude() throws Exception {
		test.setLatitude(3.0);
		assertEquals(3.0, test.getLatitude());
	}
	
	public void testGetLongitude() throws Exception {
		assertEquals(1.0, test.getLongitude());
	}
	
	public void testSetLongitude() throws Exception {
		test.setLongitude(6.0);
		assertEquals(6.0, test.getLongitude());
	}
	
	public void testGetElevation() throws Exception {
		assertEquals(1.0, test.getElevation());
	}
	
	public void testSetElevation() throws Exception {
		test.setElevation(9.0);
		assertEquals(9.0, test.getElevation());
	}
	
	public void testCompareTo() throws Exception {
		assertEquals(-1, test.compareTo(new Location(0.0,0.0,0.0)));
		assertEquals(1, test.compareTo(new Location(1.0,1.0,1.0)));
	}
}
