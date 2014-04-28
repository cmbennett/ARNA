package edu.ycp.cs481.shared.persistence;
import edu.ycp.cs481.arna.shared.model.POI;
import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {
	  public static final String POI_LIST = "poi";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_DESCRIPTION = "description";
	  public static final String COLUMN_LOCATION = "location";
	  

	  private static final String DATABASE_NAME = "poi.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + POI_LIST + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_NAME +  " text not null ," 
	      + COLUMN_DESCRIPTION  + " text not null ," + COLUMN_LOCATION
	      + " text not null);";

	  // Context
	  private static Context context;
	  
	  public MySQLiteHelper(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  this.context = context;
	  }
	  
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	    
	    // ContentValues values = new ContentValues();
	    //  values.put(MySQLiteHelper.COLUMN_TEXT, context.getString(edu.ycp.cs.cs496.cs496_lab16.R.string.first_note));
	   // database.insert(POI_LIST, null, values);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + POI_LIST);
	    onCreate(db);
	  }

}
