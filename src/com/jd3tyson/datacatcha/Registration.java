package com.jd3tyson.datacatcha;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Registration extends Activity implements OnClickListener, OnItemSelectedListener
{
	// Variable Declaration
	private Button mSubmit;
	private EditText mUsername;
	private EditText mPassword;
	private EditText mEmail;

	protected DBHelper DB = new DBHelper(Registration.this); 

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration); //set view for screen

		//User input declaration
		mSubmit = (Button)findViewById(R.id.saveb);
		mSubmit.setOnClickListener(this);
		
		mUsername = (EditText)findViewById(R.id.editusername);	//set variables to the user entered values.
		mPassword = (EditText)findViewById(R.id.editpassword);
		mEmail = (EditText)findViewById(R.id.editemail);

	}

	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.saveb:	//If the user presses save button, commit the following values to database
			String uname = mUsername.getText().toString();	//Convert text to string before committing
			String pass = mPassword.getText().toString();
			String email = mEmail.getText().toString();

			boolean invalid = false;   //User entered values are valid

			if(uname.equals(""))	//If no username is entered
			{
				invalid = true;		//User has not entered valid data
				Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show(); //Show notification to user
			}
			else
				if(pass.equals(""))	//If no password is entered
				{
					invalid = true;	//User has not entered valid data
					Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show(); //Show notification to user
				}
				else 
					if(email.equals(""))	//If no email is entered
					{
						invalid = true;	//User has not entered valid data
						Toast.makeText(getApplicationContext(), "Enter your email address", Toast.LENGTH_SHORT).show(); //Show notification to user
					}
					else
						if(invalid == false)	//If entered data is valid
						{
							addEntry(uname, pass, email);	//add details to database
							Intent i_register = new Intent(Registration.this, LoginActivity.class);
							startActivity(i_register);
						}

						break;
		}
	}
	
	public void onDestroy()
	{
		super.onDestroy();	//When registration activity is complete, destroy it
		DB.close();			//Close database connection
	}

	private void addEntry(String uname, String pass, String email) 
	{
		SQLiteDatabase db = DB.getWritableDatabase();	//User values committed to database
		ContentValues values = new ContentValues();
		values.put("username", uname);
		values.put("password", pass);
		values.put("email", email);
		
		try
		{
			db.insert(DBHelper.DATABASE_TABLE_NAME, null, values);		//Try to insert data into the database
			Toast.makeText(getApplicationContext(), "Details have been saved", Toast.LENGTH_SHORT).show();	//Show notification on successful completion 
		}
		catch(Exception e)
		{
			e.printStackTrace();	//print error to console
		}
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) 
	{
			
	}

	public void onNothingSelected(AdapterView<?> arg0) 
	{
			
	}
}