package edu.ycp.cs481.arna.shared.model;

import junit.framework.TestCase;

public class OrientationTest  extends TestCase {
	private Orientation test;
	private double az;
	private double pitch;
	private double roll;

	@Override
	protected void setUp() throws Exception {
		az = 1.5;
		pitch = 5.5;
		roll = 2.3;
		test = new Orientation(az,pitch,roll);
	}
	
	public void testgetAzimuth() throws Exception {
		assertEquals(1.5, test.getAzimuth());
	}
	
	public void testgetPitch() throws Exception {

		assertEquals(5.5, test.getPitch());
	}
	
	public void testgetRoll() throws Exception {
		assertEquals(2.3, test.getRoll());
	}
	
	public void testsetAzimuth() throws Exception {
		test.setAzimuth(3.4);
		assertEquals(3.4, test.getAzimuth());

	}
	
	public void testsetPitch() throws Exception {
		test.setPitch(109.2);
		assertEquals(109.2, test.getPitch());
	}
	
	public void testsetRoll() throws Exception {
		test.setRoll(0.0);
		assertEquals(0.0, test.getRoll());
	}
}
