package edu.ycp.cs481.arna.shared.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public class POIList {
	private List<POI> list;
	
	public POIList() {
		list = new ArrayList<POI>();
	}
	
	// Adds a POI to the list. POI names must be unique.
	public void addPOI(POI poi) throws POIException {
		POI existingPOI = getPOI(poi.getName());
		
		// Check to see if POI exists.
		if(existingPOI != null) {
			throw new POIException("POI " + poi.getName() + " already exists");
		}
		
		list.add(poi);
	}
	
	public POI getPOI(String name) {
		for (POI poi : list) {
			if (poi.getName().equals(name)) {
				return poi;
			}
		}
		return null;
	}
	
	public List<POI> getList() {
		return list;
	}
	
	// Create POIList object from a Cursor object.
	public void getListFromCursor(Cursor cursor) {
		int id;
		String name;
		String description;
		double locX, locY, locZ;
		
		// Iterate through Cursor object and pull values.
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			id = cursor.getInt(0);
			name = cursor.getString(1);
			description = cursor.getString(2);
			locX = cursor.getDouble(3);
			locY = cursor.getDouble(4);
			locZ = cursor.getDouble(5);
			try {
				addPOI(new POI(id, name, description, locX, locY, locZ));
			} catch (POIException e) {
				e.printStackTrace();
			}
		}
	}
}
