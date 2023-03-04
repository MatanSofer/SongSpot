package com.example.myapplication;

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


        public  String getColumnName() {
            Model.instance.GetUserById(ModelFireBase.getCurrentUser(), (user) -> {
                 age = Integer.parseInt(user.getAge());
                 gender = user.getGender().toLowerCase(Locale.ROOT);
            });
            columnName += gender;
            int age = 50;
            if (age >= 8 && age <= 18) {
                columnName += "1";
            } else if (age > 18 && age <= 30) {
                columnName += "2";
            } else if (age > 30 && age <= 50) {
                columnName += "3";
            } else if (age > 50 && age <= 99) {
                columnName += "4";
            } else {
                throw new IllegalArgumentException("Invalid age: " + age);
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

    //also removes duplicat
    public String[] convertArrayType(){
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
