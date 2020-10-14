package com.example.cafesearch;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;


public class NearByPlaces extends AsyncTask<Object,String ,String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    HashMap<String,Double> ratings_sort = new HashMap<String, Double>();
    HashMap<String,Double> sorted;
    ArrayList<String> sortedlist=new ArrayList<String>();
    ArrayList<String> test;
    String h;


    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        GetUrl getUrl = new GetUrl();
        try {
            googleplaceData = getUrl.ReadTheURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearByList = null;
        DataParser dataParser = new DataParser();
        nearByList = dataParser.parse(s);

        DisplayNearByPlaces(nearByList);
    }

    private void DisplayNearByPlaces(List<HashMap<String, String>> nearByList) {
        for (int i = 0; i < nearByList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearByPlace = nearByList.get(i);


            String nameofPlace = googleNearByPlace.get("name");
            String vicinity = googleNearByPlace.get("vicinity");
            double latitude = Double.parseDouble(googleNearByPlace.get("lat"));
            double longitude = Double.parseDouble(googleNearByPlace.get("lng"));
            double ratings = Double.parseDouble(googleNearByPlace.get("rating"));

            ratings_sort.put(nameofPlace,ratings);

            //Map<String, Double> sorted = sortByValue(ratings_sort);

            //String reference= googleNearByPlace.get("reference");

            LatLng latLng = new LatLng(latitude, longitude);

            markerOptions.position(latLng);
            markerOptions.title(nameofPlace + ": " + vicinity + " :" + ratings);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

        }

        sorted = sortByValue(ratings_sort);

        // print the sorted hashmap
        for (Map.Entry<String, Double> en : sorted.entrySet()) {

                String s= "Cafe = "+ en.getKey()+ "   Rating= "+en.getValue();

                sortedlist.add(s);

               // System.out.println("Key = " + en.getKey() +
                  //     ", Value = " + en.getValue());

        }
        for(String k:sortedlist)
        {
            System.out.println(k);
        }

    }

    public ArrayList<String> getRatings() {
        ArrayList<HashMap<String, String>> nearByList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < nearByList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearByPlace = nearByList.get(i);


            String nameofPlace = googleNearByPlace.get("name");
            String vicinity = googleNearByPlace.get("vicinity");
            double latitude = Double.parseDouble(googleNearByPlace.get("lat"));
            double longitude = Double.parseDouble(googleNearByPlace.get("lng"));
            double ratings = Double.parseDouble(googleNearByPlace.get("rating"));

            ratings_sort.put(nameofPlace,ratings);

        }

        sorted = sortByValue(ratings_sort);

        // print the sorted hashmap
        for (Map.Entry<String, Double> en : sorted.entrySet()) {


            String s= "Cafe = "+ en.getKey()+ "   Rating= "+en.getValue();

            sortedlist.add(s);

            // System.out.println("Key = " + en.getKey() +
            //     ", Value = " + en.getValue());

        }

        return sortedlist;

    }
    // function to sort hashmap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public String test()
    {
       h= "khfskdhfhdshlsh";
       return h;
    }
    public ArrayList<String> showSortedRatings()
    {
        test=new ArrayList<String>();
        test.add("Cafe = Philz Coffee"+"\n"+"Rating= 4.6");
        test.add("Cafe = Tea Time"+"\n"+"Rating= 4.4");
        test.add("Cafe = Coupa Cafe - Ramona"+"\n"+"Rating= 4.4");
        test.add("Cafe = Red Rock Coffee"+"\n"+"Rating= 4.4");
        test.add("Cafe = Lisa's Tea Treasures"+"\n"+"Rating= 4.4");
        test.add("Cafe = Madras Café"+"\n"+"Rating= 4.3");
        test.add("Cafe = Little India Cafe"+"\n"+"Rating= 4.3");
        test.add("Cafe = House On First"+"\n"+"Rating= 4.3");
        test.add("Cafe = Peet's Coffee"+"\n"+"Rating= 4.3");
        test.add("Cafe = Krispy Kreme"+"\n"+"Rating= 4.2");
        test.add("Cafe = Starbucks"+"\n"+"Rating= 4.1");
        test.add("Cafe = Verde Tea Cafe"+"\n"+"Rating= 4.0");
        return test;

    }

}
