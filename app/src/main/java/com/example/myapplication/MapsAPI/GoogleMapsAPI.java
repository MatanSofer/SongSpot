package com.example.myapplication.MapsAPI;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BigQuery.GetBigQuery;
import com.example.myapplication.DataSingelton;
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity;
import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

public class GoogleMapsAPI extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code =101 ;
    double lat,lng;
    Button button1,button2 ;
    public static String placesType =  "";
    ProgressBar progressBarLoading;
    Dialog dialog;
    ViewGroup root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        root = (ViewGroup) inflater.inflate(R.layout.activity_google_maps_api, container, false);
       // setContentView(R.layout.activity_google_maps_api);

        button1 = root.findViewById(R.id.firstButton);
        button2 = root.findViewById(R.id.secondButton);
        progressBarLoading = root.findViewById(R.id.progressBar);
        progressBarLoading.setVisibility(View.INVISIBLE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
//        SupportMapFragment mapFragment =(SupportMapFragment) getParentFragmentManager()  .findFragmentById(R.id.maps);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(this);
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.maps, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);

        button1.setOnClickListener((View v) ->{
            button1.setVisibility(View.INVISIBLE);
            progressBarLoading.setVisibility(View.VISIBLE);


            new Handler().postDelayed(() -> {
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
                progressBarLoading.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.VISIBLE);
            }, 1000);


        });

        button2.setOnClickListener((View v)->{
            showDialog();
        });
    return root;
    }
    public void showDialog(){
        Button backaccept;
        ImageView closeWindow;
        TextView tv1;
        ImageView status;
        TextInputLayout borderPlaceType;
        AutoCompleteTextView placeTypeItem;


        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ok);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        closeWindow = dialog.findViewById(R.id.close_dialog_button);
        backaccept = dialog.findViewById(R.id.backaccept);
//        backaccept.setEnabled(false);
        tv1 = dialog.findViewById(R.id.t1);
        borderPlaceType = dialog.findViewById(R.id.t2);
        placeTypeItem = dialog.findViewById(R.id.placeType);
        status = dialog.findViewById(R.id.status);


        String[] placeTypes = DataSingelton.getInstance().convertArrayType();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,placeTypes);
        placeTypeItem.setAdapter(arrayAdapter);


//        tv1.setText("sdasd");


        status.setImageResource(R.drawable.location_icon);
        tv1.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green));
        backaccept.setBackgroundResource(R.drawable.greenbtn);


        backaccept.setOnClickListener((View view)->{
            String placeTypeStr = placeTypeItem.getText().toString();
            if(placeTypeStr.isEmpty()){
                borderPlaceType.setError("Place cannot be empty");
            }
            else{
                DataSingelton.getInstance().setUserChosenPlace(placeTypeStr);
                for (int i = 0 ; i < 3 ;i++){
                    MainScreensActivity.tabLayout.getTabAt(i).view.setClickable(true);
                    MainScreensActivity.viewPager2.setUserInputEnabled(true);
                }
                GetBigQuery task = new GetBigQuery("SELECT id FROM songspot.songspot_spotify.spotify_songs WHERE id = '7lmeHLHBe4nmXzuXc0HDjk'",getActivity().getApplicationContext());
                task.execute();
                dialog.dismiss();
            }

        });
        closeWindow.setOnClickListener((View view)->{
            dialog.dismiss();
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT ,ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

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
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                ( getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),new String[]{
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
//                Toast.makeText(getApplicationContext(),"location result is = " + locationResult
//                        ,Toast.LENGTH_LONG).show();

                if(locationResult == null){
                    Toast.makeText(getActivity().getApplicationContext(),"Current location is null",Toast.LENGTH_LONG).show();
                    return;
                }

                for (Location location : locationResult.getLocations()){

                    if(location != null){
//                        Toast.makeText(getApplicationContext(),"Current location is"+location.getLongitude()
//                                ,Toast.LENGTH_LONG).show();
                    }
                }

            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        Log.d("GoogleMapsAPI","last location is " + fusedLocationProviderClient.getLastLocation());
        task.addOnSuccessListener(location -> {
            if(location!=null){
                lat = location.getLatitude();
                lng = location.getLongitude();
                Log.d("GoogleMapsAPI","lat" + lat + " lang" + lng);
                LatLng latLng = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title("current location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            }
            else{
                Log.d("GoogleMapsAPI","location is  null");
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


