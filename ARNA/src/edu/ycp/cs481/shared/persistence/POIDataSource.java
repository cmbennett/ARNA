package edu.ycp.cs481.shared.persistence;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs481.arna.shared.model.POI;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class POIDataSource implements IDatabase {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_DESCRIPTION, 
			MySQLiteHelper.COLUMN_LOCATION};


	public POIDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}



	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}





	@Override
	public void addPOI(POI poi) {

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, poi.getName());		
		values.put(MySQLiteHelper.COLUMN_DESCRIPTION, poi.getDescription());
		values.put(MySQLiteHelper.COLUMN_LOCATION, poi.getLocation().getLatitude() + " " + poi.getLocation().getLongitude() + " "  + poi.getLocation().getElevation());


		long insertId = database.insert(MySQLiteHelper.POI_LIST, null,
				values);

		poi.setID(insertId);
	}
	@Override
	public List<POI> getPOIs() {		

		List<POI> pois = new ArrayList<POI>();

		Cursor cursor = database.query(MySQLiteHelper.POI_LIST,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			POI poi = cursorToPOI(cursor);
			pois.add(poi);

			cursor.moveToNext();

		}
		// make sure to close the cursor
		cursor.close();
		return pois;
	}

	// get a single POI
	@Override
	public POI getPOI(String name) {
		Cursor cursor = database.query(MySQLiteHelper.POI_LIST,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			POI poi = cursorToPOI(cursor);
			if (poi.getName().equals(name))
			{			
				return poi;		

			}
			else
			{
				cursor.moveToNext();
			}

		}
		// make sure to close the cursor
		cursor.close();
		return null;

	}

	// ORM method
	private POI cursorToPOI(Cursor cursor) {
		// must add the poi in this order, ID, NAME,DESCRIPTION , LOCATION

		POI poi = new POI();
		poi.setID(cursor.getLong(0));
		poi.setName(cursor.getString(1));
		poi.setDescription(cursor.getString(2));

		String location = cursor.getString(3);
		String delims = "[ ]";
		String[] tokens = location.split(delims);

		poi.setLocation(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));

		return poi;
	}

}
