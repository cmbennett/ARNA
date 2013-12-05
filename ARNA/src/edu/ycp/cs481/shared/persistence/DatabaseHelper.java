package edu.ycp.cs481.shared.persistence;

import android.database.Cursor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	@SuppressLint("SdCardPath")
	private static final String DB_PATH = "/data/data/edu.ycp.cs481.arna.shared.persistence/databases/";

	private static final String POI_TABLE = "pois";
	
	private static final String DB_NAME = "arna";

	private SQLiteDatabase database;

	private final Context context;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Creates the local database from asset file.
	public void createDatabase() throws IOException {
		boolean exist = checkDatabase();

		if (!exist) {

			this.getReadableDatabase();

			try {
				copyDatabase();
			}
			catch (IOException e) {
				throw new Error("Error: Copying database");
			}
		}
	}


	// Checks to see if local database exists.
	private boolean checkDatabase() {
		SQLiteDatabase check = null;

		try {
			String path = DB_PATH + DB_NAME;
			check = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		}
		catch (SQLiteException e) {
			// Database doesn't exist
		}

		if (check != null) {
			check.close();
		}

		return check != null ? true : false;
	}

	// Copies asset db file to local db file.
	private void copyDatabase() throws IOException {

		// Open local database as an input stream.
		InputStream input = context.getAssets().open(DB_NAME);

		// Path to newly created database
		String outFileName = DB_PATH + DB_NAME;

		// Open empty database as an output stream.
		OutputStream output = new FileOutputStream(outFileName);

		// Transfer bytes.
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}

		output.flush();
		output.close();
		input.close();
	}

	// Open local database.
	public void openDatabase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if(database != null)
			database.close();

		super.close();
	}
	
	// Get Cursor from database.
    @SuppressLint("NewApi")
	public Cursor getCursorfromDatabase(String tag) {
    	Cursor cursor;
    	if (tag.isEmpty()) {
    		cursor = getReadableDatabase().query(POI_TABLE, null, null, null, null, null, null);
    	} else {
    		String[] selArgs = {tag}; 
    		cursor = getReadableDatabase().query(POI_TABLE, null, "name=?", selArgs, null, null, null);
    	}
    	cursor.moveToFirst();
    	return cursor;
    }
}
