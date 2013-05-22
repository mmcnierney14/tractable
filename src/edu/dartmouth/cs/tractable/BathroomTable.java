package edu.dartmouth.cs.tractable;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


//HistoryTable contains constants for the table name and the columns. 
public class BathroomTable{
	
			// Table name string. (Only one table)
			public static final String TABLE_NAME_ENTRIES = "ENTRIES";
			public static final String COLUMN_SUMMARY = "summary";

			// Table column names
			public static final String KEY_ROWID = Globals.KEY_ROWID;
			public static final String KEY_LATITUDE = Globals.KEY_LATITUDE;
			public static final String KEY_LONGITUDE = Globals.KEY_LONGITUDE;
			public static final String KEY_DATE_TIME = Globals.KEY_DATE_TIME;
			public static final String KEY_BUILDING = Globals.KEY_BUILDING;
			public static final String KEY_FLOOR = Globals.KEY_FLOOR;
			public static final String KEY_BATHROOMQUALITY = Globals.KEY_BATHROOMQUALITY;
			public static final String KEY_EXPERIENCEQUALITY = Globals.KEY_EXPERIENCEQUALITY;		
			public static final String KEY_COMMENT = Globals.KEY_COMMENT;

			// SQL query to create the table for the first time
			// Data types are defined below
			public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
					+ TABLE_NAME_ENTRIES
					+ " ("
					+ KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_DATE_TIME
					+ " DATETIME NOT NULL, "
					+ KEY_LATITUDE
					+ " DOUBLE, "
					+ KEY_LONGITUDE
					+ " DOUBLE, "
					+ KEY_BUILDING
					+ " TEXT, "
					+ KEY_FLOOR
					+ " INTEGER, "
					+ KEY_BATHROOMQUALITY
					+ " INTEGER, "
					+ KEY_EXPERIENCEQUALITY
					+ " INTEGER, "
					+ KEY_COMMENT
					+ " TEXT"
					+ ");";
			
			public static void onCreate(SQLiteDatabase database) {
			    database.execSQL(CREATE_TABLE_ENTRIES);
			  }

			  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			      int newVersion) {
			    Log.w(BathroomTable.class.getName(), "Upgrading database from version "
			        + oldVersion + " to " + newVersion
			        + ", which will destroy all old data");
			    database.execSQL("DROP TABLE IF EXISTS ");
			    onCreate(database);
			  }
}