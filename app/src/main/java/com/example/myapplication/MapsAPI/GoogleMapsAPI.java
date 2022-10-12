package com.example.myapplication.MapsAPI;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleMapsAPI extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code =101 ;
    double lat,lng;
    Button button ;
    TextView tv;
    public static String placesType =  "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_api);

        button = findViewById(R.id.button2);
        tv = findViewById(R.id.textView6);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());




        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        button.setOnClickListener((View v) ->{

            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + lat + "," + lng +
                    "&radius=200" +
                    "&type=" +
                    "&sensor=true" +
                    "&key=" + getResources().getString(R.string.google_maps_key);
            Object[] dataFetch = new Object[2];
            dataFetch[0]=mMap;
            dataFetch[1]=url;
            FetchData fetchData = new FetchData();
            fetchData.execute(dataFetch);
            // Log.d("places type" , placesType);
           //  tv.setText(placesType);
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("Error", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Error", "Can't find style. Error: ", e);
        }
        mMap = googleMap ;
        getCurrentLocation();

    }

    private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
                return;
            }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority( Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult( LocationResult locationResult) {
                Toast.makeText(getApplicationContext(),"location result is = " + locationResult
                ,Toast.LENGTH_LONG).show();

                if(locationResult == null){
                    Toast.makeText(getApplicationContext(),"Current location is null",Toast.LENGTH_LONG).show();
                    return;
                }

                for (Location location : locationResult.getLocations()){

                    if(location != null){
                        Toast.makeText(getApplicationContext(),"Current location is"+location.getLongitude()
                        ,Toast.LENGTH_LONG).show();
                    }
                }

            }
        };

            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        lat = location.getLatitude();
                        lng = location.getLongitude();

                        LatLng latLng = new LatLng(lat,lng);
                       mMap.addMarker(new MarkerOptions().position(latLng).title("current location"));
                       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    }
                }
            });
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        switch (Request_code){

            case Request_code:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
        }
    }


}



