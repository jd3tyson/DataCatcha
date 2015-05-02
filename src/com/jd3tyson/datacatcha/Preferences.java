package com.jd3tyson.datacatcha;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity 
{
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.options);	//options for the sub-menu, no longer used.
	}
}
