package com.jd3tyson.datacatcha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener 
{
	Button mLogin;		//Create variables linking to login_screen.xml
	Button mRegister;
	EditText muname;
	EditText mpassword;
	DBHelper DB = null;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);		//Set the view of the class

		mRegister = (Button)findViewById(R.id.bregister);	//Link to the layout
		mRegister.setOnClickListener(this);					//Set a listener on the button

		mLogin = (Button)findViewById(R.id.blogin);
		mLogin.setOnClickListener(this); 
	}


	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bregister:	//If register is clicked
			Intent i = new Intent(getBaseContext(), Registration.class);	//Load a new instance of the Registration.class
			startActivity(i);	//Start the registration activity
			break;				//break from case
		case R.id.blogin:		//If login is clicked
			muname = (EditText)findViewById(R.id.edituname);		//Set the username to the value of edituname
			mpassword = (EditText)findViewById(R.id.editpassword);	//Set the password to the value of editpassword
			String username = muname.getText().toString();			//Convert text to string
			String password = mpassword.getText().toString();		//Convert text to string
			if(username.equals("") || username == null)				//If no username is entered
			{
				Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();	//Show notification
			}
			else if(password.equals("") || password == null)
			{
				Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();	//If the password field is blank, show notification
			}
			else
			{
				boolean validLogin = validateLogin(username, password, getBaseContext());	//If the username and password have been entered...
				if(validLogin)	//...and the details are stored in the database
				{
					Intent in = new Intent(getBaseContext(), mainmenu.class);	//Load the main menu
					in.putExtra("UserName", muname.getText().toString());
					startActivity(in);
				}
			}
			break;
		}
	}


	@SuppressWarnings("deprecation")
	private boolean validateLogin(String username, String password, Context baseContext) 
	{
		DB = new DBHelper(getBaseContext());	//New instance of the DBHelper class
		SQLiteDatabase db = DB.getReadableDatabase();	//Load the database
		String[] columns = {"_id"};
		String selection = "username=? AND password=?";	//Select username and password values from database for comparison
		String[] selectionArgs = {username,password};
		Cursor cursor = null;
		try{
			cursor = db.query(DBHelper.DATABASE_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
			startManagingCursor(cursor);	//Query the table and place cursor on matching result
		}
		catch(Exception e)
		{
			e.printStackTrace();	//print error to console
		}
		int numberOfRows = cursor.getCount();
		if(numberOfRows <= 0)
		{
			Toast.makeText(getApplicationContext(), "Details not recognised", Toast.LENGTH_LONG).show();	//Show error message
			Intent intent = new Intent(getBaseContext(), LoginActivity.class); //Restart the login activity
			startActivity(intent);
			return false;	//return false to boolean validLogin
		}
		return true; //if details are correct, return true to boolean validLogin
	}
	public void onDestroy()
	{
		super.onDestroy();	//On destruction of activity...
		DB.close(); //...close database connection
	}
}