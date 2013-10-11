package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class LocationTest extends TestCase {

	private Location test;
	
	@Override
	protected void setUp() throws Exception {
		test = new Location(1,1,1);
	}
	
	public void testGetX() throws Exception {
			assertEquals(1, test.getX());
	}
	
	public void testSetX() throws Exception {
		test.setX(3);
		assertEquals(3, test.getX());
	}
	
	public void testGetY() throws Exception {
		assertEquals(1, test.getY());
	}
	
	public void testSetY() throws Exception {
		test.setY(6);
		assertEquals(6, test.getY());
	}
	
	public void testGetZ() throws Exception {
		assertEquals(1, test.getZ());
	}
	
	public void testSetZ() throws Exception {
		test.setZ(9);
		assertEquals(9, test.getZ());
	}
	
	public void testCompareTo() throws Exception {
		assertEquals(-1, test.compareTo(new Location(0,0,0)));
		assertEquals(1, test.compareTo(new Location(1,1,1)));
	}
}
