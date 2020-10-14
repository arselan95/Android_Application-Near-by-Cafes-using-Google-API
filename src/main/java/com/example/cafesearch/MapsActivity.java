package com.example.cafesearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocaiton;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitude, longitude;
    private int ProximityRadius = 10000;
    String temp = "";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenNavDrawer, R.string.CloseNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onClick(View v) throws IOException {

        String cafe = "cafe";
        Object transferData[] = new Object[2];
        NearByPlaces nearByPlaces = new NearByPlaces();

        searchNearByPlaces searchnearByPlaces = new searchNearByPlaces();


        switch (v.getId()) {
            case R.id.search_address:
                EditText addressfield = (EditText) findViewById(R.id.location_search);
                String address = addressfield.getText().toString();
               address=address.replaceAll("\\s+","");
                temp = address;

                //System.out.println("hereeeee-------2929929292929292929   ----" + " " + temp);


                if (!TextUtils.isEmpty(address)) {
                    mMap.clear();
                    String curl = getUrl(latitude, longitude, cafe);


                    System.out.println("url99 = " + curl);
                    transferData[0] = mMap;
                    transferData[1] = curl;
                    //transferData[1]= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.4219983,-122.084&radius=10000&type=cafe&sensor=true&key=AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8";
                    nearByPlaces.execute(transferData);
                   // Toast.makeText(this, "searching...", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v,"searching...",Snackbar.LENGTH_LONG).setAction("Action",null).show();


                    String url = getSearchUrl(latitude, longitude, address);
                    System.out.println("url99 = " + url);

                    transferData[0] = mMap;
                    transferData[1] = url;
                    //transferData[1]= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.4219983,-122.084&radius=10000&type=cafe&sensor=true&key=AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8";
                    searchnearByPlaces.execute(transferData);

                    Snackbar.make(v,"displaying results...",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                } else {
                            Snackbar.make(v,"Please enter valid address",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                        }
                    //Toast.makeText(this, "Please enter valid address", Toast.LENGTH_SHORT).show();



               /*
                List<Address> addressList=null;
                MarkerOptions userMarkerOptions =new MarkerOptions();

                //if not null
                if (!TextUtils.isEmpty(address))
                {
                    mMap.clear();
                    String url = getSearchUrl(latitude, longitude, address);
                    transferData[0] = mMap;
                    transferData[1] = url;
                    //transferData[1]= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.4219983,-122.084&radius=10000&type=cafe&sensor=true&key=AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8";
                    searchnearByPlaces.execute(transferData);
                    Geocoder geocoder = new Geocoder(this);

                    try
                    {
                        addressList = geocoder.getFromLocationName(searchnearByPlaces.s, 3);


                        if(addressList!=null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i);
                                System.out.println(addressList.get(i));
                                System.out.println(userAddress.getLatitude());
                                System.out.println(userAddress.getLongitude());
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                System.out.println(i);

                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            }
                        }
                        else
                        {
                            Toast.makeText(this,"location not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();

                    }

                }
                else
                {
                    Toast.makeText(this,"Please enter valid address",Toast.LENGTH_SHORT).show();
                }


                */

                break;

            case R.id.cafe:
                mMap.clear();
                String url = getUrl(latitude, longitude, cafe);


                System.out.println("url99 = " + url);
                transferData[0] = mMap;
                transferData[1] = url;
                //transferData[1]= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.4219983,-122.084&radius=10000&type=cafe&sensor=true&key=AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8";
                nearByPlaces.execute(transferData);
                Toast.makeText(this, "searching...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "displaying results...", Toast.LENGTH_SHORT).show();


                break;

        }
    }

    private String getSearchUrl(double latitude, double longitude, String nearbycafe) {

        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?");
        googleURL.append("input=" + nearbycafe);
        googleURL.append("&inputtype=textquery");
        googleURL.append("&fields=photos,formatted_address,name,opening_hours,rating");
        googleURL.append("&location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        // googleURL.append("&locationbias=circle900@" + latitude +  "," +longitude);
        //googleURL.append("&radius=" + ProximityRadius);

        // googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8");

        Log.d("MapsActivity", "url = " + googleURL.toString());


        //Toast.makeText(this, "link--- " + googleURL.toString(), Toast.LENGTH_SHORT).show();
        // System.out.println("link-- "+googleURL.toString());

        return googleURL.toString();


    }


    private String getUrl(double latitude, double longitude, String nearbycafe) {

        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbycafe);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyBDkf4Dt1xdiRMj1iSdMfeVQosoFVoLmj8");

        Log.d("MapsActivity", "url = " + googleURL.toString());


        //Toast.makeText(this, "link--- " + googleURL.toString(), Toast.LENGTH_SHORT).show();
        // System.out.println("link-- "+googleURL.toString());

        return googleURL.toString();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        } else {
            System.out.println("NOO PERMISSION");
        }


        /*
        LatLng test= new LatLng(37.332970, -121.884860);
        mMap.addMarker(new MarkerOptions().position(test).title("here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

         */

    }


    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);

            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastLocaiton = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //LatLng latLng = new LatLng(37.332970, -121.884860);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("you are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
         drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        String cafe = "cafe";
        Object transferData[] = new Object[2];
        NearByPlaces nearByPlaces = new NearByPlaces();

        switch (item.getItemId()) {
            case R.id.cafe_by_ratings:

                mMap.clear();
                String url = getUrl(latitude, longitude, cafe);


                System.out.println("url99 = " + url);
                transferData[0] = mMap;
                transferData[1] = url;
                nearByPlaces.execute(transferData);


                Intent intent;
                intent = new Intent(MapsActivity.this, CafeRatings.class);
                startActivity(intent);

                break;

        }
        drawerLayout= findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
            return true;
    }
}