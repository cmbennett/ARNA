package edu.ycp.cs481.arna.client.ui;

import java.util.List;

import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.shared.persistence.POIDataSource;

public class POISingleton {

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
	   
	   
	   public static List<POI> getPOIS(POIDataSource datasource)
	   {
		   List<POI> pois = datasource.getPOIs();	   
		   
		return pois;
		   
	   }
	}