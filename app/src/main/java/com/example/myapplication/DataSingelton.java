package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.FireBase.Model;
import com.example.myapplication.FireBase.ModelFireBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

//this is data that recieve only when app is running
public class DataSingelton {

    private static DataSingelton single_instance = null;
    public String userChosenPlace;
    List<String> placesFound = new ArrayList<>();
    private static int age;
    private static String gender;
    private static String columnName ="";
    public static final List<String> places = new ArrayList<>(Arrays.asList(
            "amusement_park",
            "art_gallery",
            "bakery",
            "bar",
            "beauty_salon",
            "cafe",
            "campground",
            "casino",
            "clothing_store",
            "convenience_store",
            "gym",
            "hair_care",
            "lodging",
            "night_club",
            "park",
            "restaurant",
            "secondary_school",
            "shoe_store",
            "shopping_mall",
            "spa",
            "school"
    ));

    public static void setAge(int age) {
        DataSingelton.age = age;
    }

    public static void setGender(String gender) {
        DataSingelton.gender = gender;
    }

    public  String getColumnName()  {
        columnName="";
            columnName += gender;
            if (age >= 8 && age <= 18) {
                columnName += "1";
            } else if (age > 18 && age <= 30) {
                columnName += "2";
            } else if (age > 30 && age <= 50) {
                columnName += "3";
            } else if (age > 50 && age <= 99) {
                columnName += "4";
            }
            columnName += getUserChosenPlace();
            return columnName;
        }



    private DataSingelton(){}


    public static DataSingelton getInstance()
    {
        if (single_instance == null)
            single_instance = new DataSingelton();
        return single_instance;
    }

    //also removes duplicate
    public String[] convertArrayType(){
        // we dont want to display unsupported places
        placesFound.removeIf(place -> !places.contains(place));

        String[] strarray = new String[placesFound.size()];
        placesFound.toArray(strarray );
        for(int i = 0 ; i < placesFound.size() ; i++){
            strarray[i]= strarray[i].replaceAll("_" , " ");
        }
        Set<String> stringSet = new LinkedHashSet<>(Arrays.asList(strarray)); // LinkedHashSet preserves the order of elements

        return stringSet.toArray(new String[0]);
    }

    public String getUserChosenPlace() {
        return userChosenPlace;

    }

    //because in big query we are using _ instead of space
    public void setUserChosenPlace(String userChosenPlace) {
        this.userChosenPlace = userChosenPlace.replace(" ", "_");
    }

    public List<String> getPlacesFound() {
        return placesFound;
    }
    public void setPlacesFound(List<String> placesFound) {
        this.placesFound = placesFound;
    }

}
