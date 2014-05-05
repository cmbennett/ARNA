package edu.ycp.cs481.arna.client.ui;

import java.util.List;

import android.content.Context;
import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.shared.persistence.POIDataSource;

public class POISingleton {

	private static POIDataSource datasource;
	private static TourMode tour;
	private static TourController cont;

	private static POISingleton instance = null;
	protected POISingleton() {
		// Exists only to defeat instantiation.
	}
	public static POISingleton getInstance() {
		if(instance == null) {
			instance = new POISingleton();
		}
		return instance;
	}

	public static POIDataSource getDataSource( )
	{
		return datasource;
	}

	public static void setDataSource(Context context )
	{
		datasource = new POIDataSource(context);
		datasource.open();
	}


	public static List<POI> getPOIS(POIDataSource datasource)
	{
		List<POI> pois = datasource.getTourModePOIs();	   

		return pois;

	}
	
	public static void setTourMode(TourMode tourMode)
	{
		tour = tourMode;
	
	}

	public static TourMode getTourMode( )
	{
		return tour;
	}
	
	public static void setTourCont(TourController tourCont)
	{
		cont = tourCont;	
	}

	public static TourController getTourCont( )
	{
		return cont;
	}
	
	public static List<POI> getCompassPOIS(POIDataSource datasource)
	{
		List<POI> pois = datasource.getCompassModePOIs();	   

		return pois;

	}


}