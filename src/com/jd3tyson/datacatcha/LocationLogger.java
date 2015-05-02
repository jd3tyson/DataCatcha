package com.jd3tyson.datacatcha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class LocationLogger extends Activity 
{
	TextView textlatitude;		//TextView from locationlogger.xml required for displaying current location
	TextView textlongitude;		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationlogger);	//Layout of the class including fields
		
		textlatitude = (TextView)findViewById(R.id.textlat);	//Link value to field in XML layout
		textlongitude = (TextView)findViewById(R.id.textlong);	//Link value to field in XML layout
		
		LocationManager locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE); //Instantiate location manager
		LocationListener loclis = new mylocationlistener();	//Instantiate location listener
		locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				600000, 0, loclis);	//Request location updates from GPS using location listener every 10 minutes, no minimum distance
	}
	
	private class mylocationlistener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location location)	//Each time the location changes, execute code 
		{
				double LongitudeResult = location.getLongitude();	//Get the longitude and set as pLongitude
				double LatitudeResult = location.getLatitude();		//Get the latitude and set as pLatitude
				
				textlatitude.setText(Double.toString(LatitudeResult));	//Set textlatitude to LatitudeResult, current latitude is now displayed in app
				textlongitude.setText(Double.toString(LongitudeResult));	//Set textlongitude to LongitudeResult, current longitude is now displayed in app
				
				try 
				{	
					File dir = Environment.getExternalStorageDirectory();	//Get the storage directory for the device
					File locationFile = new File(dir, "Locations.txt");		//Save a new text file in the root directory, called Locations.txt					
					FileOutputStream fOut = new FileOutputStream(locationFile, true);	//OutputStream will append to file if it already exists
		        	OutputStreamWriter myWriter = 
		                              new OutputStreamWriter(fOut);		         
		        	
		        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");	//Create a variable to hold current date and time
		        	String timestamp = sdf.format(new Date());	//timestamp is equal to current date and time
		        	
		        	myWriter.write("Date: " + timestamp + "\n");		//Write the value of variable timestamp to file 
		        	myWriter.write("Latitude: " + textlatitude.getText() + "\n");	//Write the current latitude to file
		            myWriter.write("Longitude: " + textlongitude.getText() + "\n");	//Write the current longitude to file 
		            myWriter.write("\n");	
		            myWriter.write("\n");	//Leave a blank line for next record
		            myWriter.close();		//Close the writer
		            fOut.close();			//Close the stream 
		            
		            Toast.makeText(getBaseContext(),
		                    "Location recorded",
		                    Toast.LENGTH_LONG).show();	//Show notification to user on successful write
		        } 
				catch (Exception e) 
				{
		            Toast.makeText(getBaseContext(), e.getMessage(),
		                    Toast.LENGTH_SHORT).show();	//If unsuccessful show the error message
		        }
			
		}

		@Override
		public void onProviderDisabled(String provider) 
		{
			Toast.makeText(getBaseContext(),
                    "GPS turned off",
                    Toast.LENGTH_SHORT).show();		//When the GPS is turned off, show notification
		}

		@Override
		public void onProviderEnabled(String provider) 
		{
			Toast.makeText(getBaseContext(),
                    "GPS turned on",
                    Toast.LENGTH_SHORT).show();		//When the GPS is turned on, show notification				
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) 
		{
				//Unimplemented method
		}		
	}
}