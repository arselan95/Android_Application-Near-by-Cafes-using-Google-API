package com.example.cafesearch;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class searchNearByPlaces extends AsyncTask<Object,String ,String> {


    private String googleplaceData, url;
    private GoogleMap mMap;
    MapsActivity m = new MapsActivity();
    String s = "";


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
        searchParse dataParser = new searchParse();
        nearByList = dataParser.parse(s);

        DisplayNearByPlaces(nearByList);
    }

    private String DisplayNearByPlaces(List<HashMap<String, String>> nearByList) {


        Object transferData[] = new Object[2];
        getSearchPlace searchplace = new getSearchPlace();


        System.out.println(m.temp);
        for (int i = 0; i < nearByList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearByPlace = nearByList.get(i);


            String addy = googleNearByPlace.get("formatted_address");
           // s=addy;
            System.out.println("search ---"+s);
            String newaddy=addy.replaceAll("\\s+","+");
            s=newaddy;

            System.out.println("newsearch ---"+newaddy);

            String url= getSearchParsedUrl(newaddy);

            transferData[0]=mMap;
            transferData[1]= url;
            //transferData[1]= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.4219983,-122.084&radius=10000&type=cafe&sensor=true&key=AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8";
            searchplace.execute(transferData);


            /*

            String nameofPlace= googleNearByPlace.get("name");
          // System.out.println(nameofPlace);
            String vicinity= googleNearByPlace.get("vicinity");
           // System.out.println(vicinity);
            double latitude= Double.parseDouble(googleNearByPlace.get("lat"));
            double longitude= Double.parseDouble(googleNearByPlace.get("lng"));
            //double ratings= Double.parseDouble(googleNearByPlace.get("rating"));

            //String reference= googleNearByPlace.get("reference");

            LatLng latLng = new LatLng(latitude,longitude);
            
            markerOptions.position(latLng);
            markerOptions.title(nameofPlace);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            String t = markerOptions.getTitle();
            //System.out.print(t);


            if(markerOptions.title(nameofPlace).equals(s))
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

            }

             */


            //  mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(7));


        }
        return s;

    }

    private String getSearchParsedUrl(String nearbycafe)
    {

        StringBuilder googleURL= new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?");
        googleURL.append("address=" + nearbycafe);
        googleURL.append("&key=" + "AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8");

        Log.d("MapsActivity","url = " + googleURL.toString());


        //Toast.makeText(this, "link--- "+googleURL.toString(),Toast.LENGTH_SHORT).show();
        // System.out.println("link-- "+googleURL.toString());

        return googleURL.toString();



    }
}

