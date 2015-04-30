package com.example.whereintheworld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DBQuery {

	public static List<Continent> continentList = new ArrayList<Continent>();
	public static List<Country> countryList = new ArrayList<Country>();
	public static void populateContinentList(final Context context, final ListView continentListView){
		//final List<Continent> continentList = new ArrayList<Continent>();
		continentList.clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Continent");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> parseObjectList, ParseException e) {
		        if (e == null) {
		            
		        	Iterator<ParseObject> itr = parseObjectList.iterator();
		            while(itr.hasNext()){
		            	ParseObject obj = itr.next();
		            	Continent continent = new Continent(obj.getInt("continentId"), obj.getString("continentName"));
		            	Log.d("continent",continent.toString());
		            	continentList.add(continent);
		            }
		        	
		            ArrayAdapter<Continent> continentAdapter = new ArrayAdapter<Continent>(context, android.R.layout.simple_list_item_1, continentList);
					continentAdapter.setNotifyOnChange(true);
					continentListView.setAdapter(continentAdapter);
		        	Log.d("continent", "Retrieved " + parseObjectList.size() + " continents");
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }

			
		});
		//Log.d("size",continentList.size()+"");
		//return continentList;
		
	}
	
	public static void populateCountryList(final Context context, final ListView countryListView, int position){
		//final List<Continent> continentList = new ArrayList<Continent>();
		countryList.clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Country");
		query.whereEqualTo("continentId", position+1);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> parseObjectList, ParseException e) {
		        if (e == null) {
		            
		        	Iterator<ParseObject> itr = parseObjectList.iterator();
		            while(itr.hasNext()){
		            	ParseObject obj = itr.next();
		            	Country country = new Country(obj.getInt("continentId"), obj.getInt("countryId"),obj.getString("countryName"));
		            	//Log.d("continent",continent.toString());
		            	countryList.add(country);
		            }
		        	
		            ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(context, android.R.layout.simple_list_item_1, countryList);
		            countryAdapter.setNotifyOnChange(true);
					countryListView.setAdapter(countryAdapter);
		        	Log.d("country", "Retrieved " + parseObjectList.size() + " country");
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }

			
		});
		//Log.d("size",continentList.size()+"");
		//return continentList;
		countryListView.setVisibility(ListView.VISIBLE);
	}
	
	
}
