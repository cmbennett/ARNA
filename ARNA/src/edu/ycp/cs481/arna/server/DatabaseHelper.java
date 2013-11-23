package edu.ycp.cs481.arna.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_PATH = "";
	public static final String DATABASE_NAME = "arna.db";
	public static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase myDatabase;
	
	private final Context myContext;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public synchronized void close() {
		if(myDatabase != null)
			myDatabase.close();
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		
	}

	// Create local database from asset file.
	public void createDataBase() throws IOException {
		boolean exist = checkDataBase();

		if(!exist) {
			try {
				copyDataBase();
			}
			catch (IOException e) {
				throw new Error("Error: Copying database");
			}
		}
	}

	// Check to see if local database exists.
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}
		catch (SQLiteException e) {
			// Database doesn't exist
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	// Copy asset .db file to local .db file.
	private void copyDataBase() throws IOException {
		
		// Open local database as an input stream.
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		String outFileName = DB_PATH + DATABASE_NAME;

		// Open the empty database as an output stream.
		OutputStream myOutput = new FileOutputStream(outFileName);

		// Transfer data.
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams.
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	// Opens local database.
	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DATABASE_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
}