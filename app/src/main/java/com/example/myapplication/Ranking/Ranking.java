package com.example.myapplication.Ranking;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.myapplication.BigQuery.GetBigQuery;
import com.example.myapplication.BigQuery.SetBigQuery;
import com.example.myapplication.DataSingelton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Ranking {
    private static final String tableName = "songspot.songspot_spotify.spotify_songs";
    private static int numberOfSongsRecommendation = 3;

    public static String currentSongID;


    public static List<String> songsIdResults = new ArrayList<>();
    public static List<String> idRatingSum = new ArrayList<>();
    public static List<String> idVoteNum = new ArrayList<>();

    public static void createSetQuery(int rating, Context context ){
        String current ="0VNzEY1G4GLqcNx5qaaTl6";
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
       String columnName = DataSingelton.getInstance().getColumnName();

      // get the relevant values for rank algorithm
        createGetQuery(context,"getIdratingSum");
        createGetQuery(context,"getIdNumberOfSongRate");

        // run algorithm here
        Log.d("BigQueryActivity", "idRatingSumFromDb:"+idRatingSum.get(0));
        Log.d("BigQueryActivity", "idRatingSumFromDb:"+idVoteNum.get(0));


        //  after algorithm running set the relevant fields - maybe possible one query only
//       SetBigQuery setRank = new SetBigQuery
//               ("UPDATE "+tableName+" SET "+columnName+"rank" +" = "+rating+" WHERE id = "+"'"+currentSongID+"'", context);
//        setRank.execute();
//
        SetBigQuery setRatingSum = new SetBigQuery
                ("UPDATE "+tableName+" SET "+columnName+"ratesum"+" = "+(Integer.parseInt(idRatingSum.get(0))+rating)+" WHERE id = "+"'"+current+"'", context);
        setRatingSum.execute();
        waitForSetTask(setRatingSum);

        SetBigQuery setNumberOfSongRate = new SetBigQuery
                ("UPDATE "+tableName+" SET "+columnName+"votenum"+" = "+(Integer.parseInt(idVoteNum.get(0))+1)+" WHERE id = "+"'"+current+"'", context);
        setNumberOfSongRate.execute();
        waitForSetTask(setNumberOfSongRate);
    }

    public static void createGetQuery(Context context, String requestType){
        //find the name of the column in the bigQuery table = the template is - gender+ age range value(1-4) + location type
        String columnName = DataSingelton.getInstance().getColumnName();
        String current ="0VNzEY1G4GLqcNx5qaaTl6";
    switch(requestType) {
        //get the x top rated songs for the user
        case "getTopRated":
            GetBigQuery task = new GetBigQuery("" +
                  "SELECT id FROM " + tableName + " ORDER BY " + columnName+"rank" + " DESC LIMIT " + numberOfSongsRecommendation,
                  "getTopRated",context);
            task.execute();
            waitForGetTask(task);
            break;
        //get the sum of all rating for the specific song    
        case "getIdratingSum":
            GetBigQuery task1 = new GetBigQuery("" +
                    "SELECT "+columnName+"ratesum"+" FROM " + tableName + " WHERE id = "+"'"+current+"'"
                    ,"getIdratingSum",context);
            task1.execute();
            waitForGetTask(task1);
            break;
        //get the total number of users rated this song 
        case "getIdNumberOfSongRate":
            GetBigQuery task2 = new GetBigQuery("" +
                    "SELECT "+columnName+"votenum"+" FROM " + tableName + " WHERE id = "+"'"+current+"'"
                    ,"getIdNumberOfSongRate",context);
            task2.execute();
            waitForGetTask(task2);
            break;



    }

    }

    //to return all id
    public static List<String> getSongsIdResults() {
        return songsIdResults;
    }
    public static void waitForGetTask(GetBigQuery task){
        try {
            task.get();
        }catch (ExecutionException | InterruptedException e){}
    }
    public static void waitForSetTask(SetBigQuery task){
        try {
            task.get();
        }catch (ExecutionException | InterruptedException e){}
    }


}
