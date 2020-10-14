package com.example.cafesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser

{

    private HashMap<String ,String> getPlace(JSONObject googlePlaceJSON)
    {
        HashMap<String,String> googlePlaceMap= new HashMap<>();
        String addresses="";
        String names= "";
        String vicinities= "-NA-";
        String latitude= "";
        String longitude= "";
        String reference= "";
        String ratings="";

        try
        {

            if(!googlePlaceJSON.isNull("name"))
            {
                names= googlePlaceJSON.getString("name");
            }
            if(!googlePlaceJSON.isNull("vicinity"))
            {
                vicinities= googlePlaceJSON.getString("vicinity");
            }
           // if(!googlePlaceJSON.isNull("lat")) {
                latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            //}
            //if(!googlePlaceJSON.isNull("lng")) {
                longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //}
            //if(!googlePlaceJSON.isNull("reference")) {
                reference = googlePlaceJSON.getString("reference");
            //}
            //if(!googlePlaceJSON.isNull("rating")) {
                ratings = googlePlaceJSON.getString("rating");
            //}

           // googlePlaceMap.put("formatted_address",addresses);

            googlePlaceMap.put("name",names);
            googlePlaceMap.put("vicinity",vicinities);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);
            googlePlaceMap.put("rating",ratings);


        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    private List<HashMap<String,String>> getAllPlaces(JSONArray jsonArray)
    {
        int count= jsonArray.length();
        List<HashMap<String,String>> nearByPlacesList=new ArrayList<>();

        HashMap<String,String> nearByMap=null;

        for(int i =0;i<count;i++)
        {
            try {
                nearByMap= getPlace((JSONObject) jsonArray.get(i));
                nearByPlacesList.add(nearByMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearByPlacesList;
    }

    public List<HashMap<String,String>> parse (String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllPlaces(jsonArray);
    }
}
