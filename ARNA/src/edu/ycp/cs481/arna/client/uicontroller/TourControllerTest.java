package edu.ycp.cs481.arna.client.uicontroller;

import edu.ycp.cs481.arna.shared.model.TourMode;
import junit.framework.TestCase;

public class TourControllerTest extends TestCase {
	private TourMode t;
	private TourController cont; 
	
	@Override
	protected void setUp() throws Exception {
		t = new TourMode(); 
		cont = new TourController(); 
	}
	
	public void testSetModel() throws Exception {
		cont.setModel(t); 
		assertEquals(t, cont.getModel());
	}
	
	public void testUpdateLocation() throws Exception {
		cont.setModel(t); 
		cont.updateLocation(36.5555, 77.7777, 0.0); 
		assertEquals(36.5555, t.getUser().getLocation().getLatitude()); 
		assertEquals(77.7777, t.getUser().getLocation().getLongitude());
		assertEquals(0.0, t.getUser().getLocation().getElevation()); 
	}
	
	public void testUpdateOrientation() throws Exception {
		cont.setModel(t); 
		cont.updateOrientation(52.45, 10.2, 3.3); 
		assertEquals(52.45, t.getUser().getOrient().getAzimuth()); 
		assertEquals(10.2, t.getUser().getOrient().getPitch());
		assertEquals(3.3, t.getUser().getOrient().getRoll()); 
	}
}
