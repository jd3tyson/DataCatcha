package com.jd3tyson.datacatcha;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class mainmenu extends ListActivity 
{
	String menuitem[] = {"LocationLogger", "About", "Legal"}; //Items on main menu stored at string array
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(mainmenu.this, android.R.layout.simple_list_item_1, menuitem));
		//use the simple menu layout, display the array as a simple list
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);	//When the user clicks an item
		String selection = menuitem[position];	//Ascretain its position in the array
		
		try
		{
			Class menuClass = Class.forName("com.jd3tyson.datacatcha." + selection);	//Load the class using the name in the string array
			Intent menuIntent = new Intent(mainmenu.this, menuClass);
			startActivity(menuIntent);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();	//print error to console
		}
	}
	
	public boolean onCreateOptionsMenu(android.view.Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.mainmenu, menu); //Load sub-menu on user selection
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{			
			case R.id.Exit:
				finish();		//logout of the application if user selects option
			break;	
		}
		
		return false;			//if no selection, return to main activity
	}
}
