package com.example.cafesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class CafeRatings extends AppCompatActivity {

    NearByPlaces nearByPlaces=new NearByPlaces();
    ArrayList<String> s;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<String> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_ratings);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        test=new ArrayList<String>();
        test.add("hsdlkhflks");
        test.add("bcxnvmncxm");

        s= nearByPlaces.showSortedRatings();

       // nearByPlaces.showSortedRatings();
       // Collections.copy(test,s);

        for(String st:s)
        {

            System.out.println(st);
        }


        //recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        adapter=new MainAdapter(s);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}