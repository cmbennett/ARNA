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
	private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_TEXT};
	
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
	    values.put(MySQLiteHelper.COLUMN_TEXT, poi.getName());
	    long insertId = database.insert(MySQLiteHelper.POI_LIST, null,
	        values);
	    poi.setID(insertId);
	 }

	@Override
	public void deletePOI(POI poi) {
		String[] deleteArg = {poi.getName()};
		database.delete(MySQLiteHelper.POI_LIST, MySQLiteHelper.COLUMN_TEXT
	        + " = ?", deleteArg);
	}

	@Override
	public void deleteAllPOI() {
	    database.delete(MySQLiteHelper.POI_LIST, MySQLiteHelper.COLUMN_TEXT
	        + " = *", null);
	}

	@Override
	public List<POI> getPOI() {
	    List<POI> notes = new ArrayList<POI>();

	    Cursor cursor = database.query(MySQLiteHelper.POI_LIST,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      POI poi = cursorToNote(cursor);
	      notes.add(poi);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return notes;
	}

	// ORM method
	private POI cursorToNote(Cursor cursor) {
		POI poi = new POI();
		poi.setID(cursor.getLong(0));
		poi.setName(cursor.getString(1));
	    return poi;
	  }

}
