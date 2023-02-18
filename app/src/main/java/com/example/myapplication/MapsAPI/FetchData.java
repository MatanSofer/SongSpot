package com.example.myapplication.MapsAPI;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.DataSingelton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends AsyncTask<Object,String,String> {

    String googleNearByPlacesData ;
    GoogleMap googleMap;
    String url;
    List<String> placesFound = new ArrayList<>();
    @Override
    protected void onPostExecute(String s) {
        //using volley
        Log.d("json?" ,s);
        try{

            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i= 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject getLocation = jsonObject1.getJSONObject("geometry").
                        getJSONObject("location");

                String lat = getLocation.getString("lat");
                String lng = getLocation.getString("lng");


                JSONObject getName = jsonArray.getJSONObject(i);
                String name = getName.getString("name");

                try {
                    JSONArray jsonArrayType = jsonArray.getJSONObject(i).getJSONArray("types");
                  //  if(placesFound.contains((String) jsonArrayType.get(0))) {
                        placesFound.add((String) jsonArrayType.get(0));
                        Log.d("place" + i + getName.getString("name"), (String) jsonArrayType.get(0));
                  //  }
                }catch (Exception e){
                    Log.d("ERR", "onPostExecute:cant find type location for some reason ");
                }

                LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                MarkerOptions maekerOptions = new MarkerOptions();
                maekerOptions.title(name);
                maekerOptions.position(latLng);
//                maekerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.));
                googleMap.addMarker(maekerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

            }

            for(String placeType:placesFound){
                Log.d("placetype",placeType);
            }
            DataSingelton.getInstance().setPlacesFound(placesFound);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Object... objects) {
        try {

            googleMap = (GoogleMap)objects[0];
            url = (String)objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googleNearByPlacesData = downloadUrl.retireveUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  googleNearByPlacesData;
    }
}