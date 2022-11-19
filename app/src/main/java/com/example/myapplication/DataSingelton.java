package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
//this is data that recieve only when app is running
public class DataSingelton {

    private static DataSingelton single_instance = null;
    public String userChosenPlace;
    List<String> placesFound = new ArrayList<>();

    private DataSingelton(){}


    public static DataSingelton getInstance()
    {
        if (single_instance == null)
            single_instance = new DataSingelton();
        return single_instance;
    }

    public String[] convertArrayType(){
        String[] strarray = new String[placesFound.size()];
        placesFound.toArray(strarray );

        for(int i = 0 ; i < placesFound.size() ; i++){
            strarray[i]= strarray[i].replaceAll("_" , " ");

        }


        return strarray;
    }

    public String getUserChosenPlace() {
        return userChosenPlace;
    }

    public void setUserChosenPlace(String userChosenPlace) {
        this.userChosenPlace = userChosenPlace;
    }

    public List<String> getPlacesFound() {
        return placesFound;
    }

    public void setPlacesFound(List<String> placesFound) {
        this.placesFound = placesFound;
    }
}
