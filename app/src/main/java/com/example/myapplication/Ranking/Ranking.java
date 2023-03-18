package com.example.myapplication.Ranking;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.myapplication.BigQuery.GetBigQuery;
import com.example.myapplication.BigQuery.SetBigQuery;
import com.example.myapplication.DataSingelton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Ranking {
    static Context classContext;
    public static final String tableName = "songspot.songspot_spotify.spotify_songs";
    private static int numberOfSongsRecommendation = 4;
    public static String currentSongID;
    public static Integer currentRating;
    public static List<Integer> voteArray = new ArrayList<>();
    public static List<String> songsIdResults = new ArrayList<>();


    public static void createSetQuery(int rating, Context context,ProgressBar progressBar ){
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
        classContext = context;
        currentRating = rating;
        createGetRanksArrayQuery(classContext,progressBar);


        // at this point algorithm is running

    }

    public static void createGetRanksArrayQuery(Context context, ProgressBar progressBar){
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
        String columnName = DataSingelton.getInstance().getColumnName();
            GetBigQuery task1 = new GetBigQuery("" +
                    "SELECT "+columnName+"ratings"+" FROM " + tableName + " WHERE id = "+"'"+currentSongID+"'"
                    ,"votesArrray",progressBar,classContext);
            task1.execute();

    }


    public static void createGetQuery(Context context,ProgressBar progressBar, Dialog dialog){
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
        String columnName = DataSingelton.getInstance().getColumnName();
        //get the x top rated songs for the user
                GetBigQuery task = new GetBigQuery("" +
                        "SELECT id FROM " + tableName + " ORDER BY " + columnName+"rank" + " DESC LIMIT " + numberOfSongsRecommendation,
                        "getTopRated",progressBar,dialog,context);
                task.execute();

        }

    public static void startRankAlgorithm(Context context) {
        float newRating = RankAlgorithm.calculateRank(voteArray,currentRating);
        Log.d("Ranking:", "new rank is "+newRating);
        updateRanksAndRatingBigQuery(voteArray, newRating, context);
    }

    //called after algorithm is finished
    public static void updateRanksAndRatingBigQuery(List<Integer> songRatings, float newRank, Context context){
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
        String columnName = DataSingelton.getInstance().getColumnName();

        String formattedArr = String.join(",", songRatings.stream().map(Object::toString).collect(Collectors.toList()));
        SetBigQuery setRatingArray = new SetBigQuery
                ("UPDATE "+tableName+" SET "+columnName+"ratings"+" = "+"["+formattedArr+"]"+" WHERE id = "+"'"+currentSongID+"'", context);
        setRatingArray.execute();


        SetBigQuery setRank = new SetBigQuery
                ("UPDATE "+tableName+" SET "+columnName+"rank" +" = "+newRank+" WHERE id = "+"'"+currentSongID+"'", context);
        setRank.execute();
    }


    //to return all id
    public static List<String> getSongsIdResults() {
        return songsIdResults;
    }



}
