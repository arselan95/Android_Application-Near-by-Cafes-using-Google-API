package com.example.cafesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class searchParse {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJSON) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String addresses = "";
        try {

            if (!googlePlaceJSON.isNull("formatted_address")) {
                addresses = googlePlaceJSON.getString("formatted_address");
            }
            googlePlaceMap.put("formatted_address", addresses);

        } catch (JSONException e) {
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
            jsonArray=jsonObject.getJSONArray("candidates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllPlaces(jsonArray);
    }
}

