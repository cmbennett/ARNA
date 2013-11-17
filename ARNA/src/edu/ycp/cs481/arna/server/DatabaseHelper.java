package edu.ycp.cs481.arna.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "arna.db";
	public static final int DATABASE_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		POITable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		POITable.onUpgrade(db, oldversion, newversion);
	}
}