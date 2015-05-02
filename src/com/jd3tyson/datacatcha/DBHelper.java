package com.jd3tyson.datacatcha;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper 
{
	private SQLiteDatabase db;
	public static final String KEY_ROWID = "_id";   	//Id row in database
    public static final String KEY_USER = "username";	//Username row in database
    public static final String KEY_EMAIL = "email";		//Email row in database
    
    DBHelper DB = null;
	private static final String DATABASE_NAME = "datacatcha.db";		//Name of database
	private static final int DATABASE_VERSION = 1;						//Database version
    public static final String DATABASE_TABLE_NAME = "saveddetails";	//Table name in database
    
    private static final String DATABASE_TABLE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE_NAME + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "username TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL);"; //Create a table in database with the following fields and constraints
    
    public DBHelper(Context context) 
    {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);	
    	//Create a new instance of DBHelper to create database or append
    }

    public void onCreate(SQLiteDatabase db) 
    {
    	try
    	{
    		db.execSQL(DATABASE_TABLE_CREATE);	//Try to create the table on class execution
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();	//Display error if unsuccessful
    	}
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    	//Database does not requite new versions
    }

    public Cursor rawQuery(String string, String[] strings) 
    {
    	return null;
    }

    public void open() 
    {
    	getWritableDatabase();		//Open the database to write values
    }

    public Cursor getDetails(String text) throws SQLException 
    {
    	Cursor mCursor =
        db.query(true, DATABASE_TABLE_NAME, 
        new String[]{KEY_ROWID, KEY_USER, KEY_EMAIL}, 
            KEY_USER + "=" + text, 
            null, null, null, null, null);	//Retrieve user details saved in table
    if (mCursor != null) 
    {
        mCursor.moveToFirst();	//If there are no details to be retrieved, move to the first record in table
    }
    return mCursor;		//return the retrieved values
    }
}		