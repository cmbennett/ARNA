package edu.ycp.cs481.arna.client.uicontroller;

import edu.ycp.cs481.arna.client.uicontroller.CompassController;
import edu.ycp.cs481.arna.shared.model.CompassMode;
import edu.ycp.cs481.arna.shared.model.Location;
import edu.ycp.cs481.arna.shared.model.Orientation;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.User;
import junit.framework.TestCase;

public class CompassControllerTest extends TestCase  {
	 private CompassMode t;
     private CompassController cont; 
     private User user;
     private POI poi;
     private Location loc;
     private Orientation ori;

@Override
protected void setUp() throws Exception {
		loc=new Location(5.4,6.6,3.1);
		ori=new Orientation(3.2,4.5,6.1);
		user=new User(loc,ori);
		poi=new POI(5.0,5.0,5.0);
        t = new CompassMode(user,poi); 
        cont = new CompassController(t); 
}
public void testSetModel() throws Exception {
    cont.setModel(t); 
    assertEquals(t, cont.getModel());
}
public void testgetModel() throws Exception{
	assertEquals(t, cont.getModel());
}
public void testupdateLocation() throws Exception{
   ;
	cont.updateLocation(4.3,5.4,2.1);
	t.getUser().setLocation(4.3,5.4,2.1);
	assertEquals(t,cont.getModel());
}
public void testupdateOrientation() throws Exception{
 
	cont.updateOrientation(4.3,2.1,3.4);
	t.getUser().setOrient(4.3,2.1,3.4);
	assertEquals(t,cont.getModel());
}
public void testupdateDistance() throws Exception
{
	cont.updateDistance();
	t.setDistance(3.4);
	assertEquals(t,cont.getModel());
    
}
public void testupdateDirection() throws Exception {
   cont.updateDirection();
   t.setDirection(34.7);
   assertEquals(t,cont.getModel());
  
}


}