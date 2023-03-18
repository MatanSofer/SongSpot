package com.example.myapplication.BigQuery;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.myapplication.DataSingelton;
import com.example.myapplication.Ranking.Ranking;
import com.example.myapplication.Spotify.state.GlobalState;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetBigQuery extends AsyncTask<Void, Void, List<String>> {

    private Context context;
    private String query;
    private String queryType;
    private final String CREDENTIALS_FILE = "songspot-13c0986bb1d9.json";
    private final String PROJECT_ID = "songspot";
    BigQuery bigquery;
    List<String> results = new ArrayList<>();
    List<Integer> IntArrayResults = new ArrayList<>();

    ProgressBar progressBar;
    Dialog dialog;

    public GetBigQuery(String query, String queryType,ProgressBar progressBar, Context context) {
        this.query = query;
        this.context = context;
        this.queryType = queryType;
        this.progressBar = progressBar;
        this.dialog =null;
        try {
            bigquery = BigQueryOptions.newBuilder().setProjectId(PROJECT_ID)
                    .setCredentials(ServiceAccountCredentials.fromStream(context.getAssets()
                            .open(CREDENTIALS_FILE))).
                    build().
                    getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public GetBigQuery(String query, String queryType,ProgressBar progressBar,Dialog dialog,  Context context) {
        this.query = query;
        this.context = context;
        this.queryType = queryType;
        this.progressBar = progressBar;
        this.dialog = dialog;
        try {
            bigquery = BigQueryOptions.newBuilder().setProjectId(PROJECT_ID)
                    .setCredentials(ServiceAccountCredentials.fromStream(context.getAssets()
                            .open(CREDENTIALS_FILE))).
                    build().
                    getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected List<String> doInBackground(Void... params) {

        Log.d("BigQueryActivity", "do in background has been called");
        try {

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(query)
                            .setUseLegacySql(false)
                            .build();

            Log.d("BigQueryActivity", "created query config");

            JobId jobId = JobId.of(UUID.randomUUID().toString());
            Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
            Log.d("BigQueryActivity", "before wait to job");
            queryJob = queryJob.waitFor();
            Log.d("BigQueryActivity", "job has been done");
            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
            TableResult result = queryJob.getQueryResults();
            // Extract the results and return them as a list of strings due to the type of query
            switch(queryType) {
                case "getTopRated":
                    for (FieldValueList row : result.iterateAll()) {
                        results.add(row.get("id").getStringValue());
                        Log.d("BigQueryActivity", row.get("id").getStringValue());
                    }
                    Ranking.songsIdResults = results;
                    break;
                case "votesArrray":
                    for (FieldValueList row : result.iterateAll()) {
                        List<FieldValue> fieldValues = row.get(DataSingelton.getInstance().getColumnName()+"ratings").getRepeatedValue();
                        for (FieldValue fieldValue : fieldValues) {
                            IntArrayResults.add(fieldValue.getNumericValue().intValue());
                            Log.d("BigQueryActivity",String.valueOf(fieldValue.getNumericValue().intValue()));
                        }
                    }
                    Log.d("BigQueryActivity","entered votesArrray");
                    Ranking.voteArray = IntArrayResults;
                    if(IntArrayResults.size() == 0 ) {
                        Log.d("BigQueryActivity", "row isnt exist because there is no ratings array");
                        makeNewIdRow();
                    }

                    break;

            }
        } catch (InterruptedException e) {
            Log.d("BigQueryActivity", "exception has been found");
            e.printStackTrace();
        }
        return null;
    }


    @Override
        protected void onPostExecute (List < String > results) {
         // Update the UI with the results
         Log.d("BigQueryActivity", "onPostExecute has been called");
         progressBar.setVisibility(View.INVISIBLE);
         if(dialog!=null){
             dialog.dismiss();
         }
         if(queryType.equals("votesArrray")) Ranking.startRankAlgorithm(context);
     }

    private void makeNewIdRow() {
        Log.d("BigQueryActivity", "makeNewIdRow() is called because row isnt exist");
        this.query = "INSERT "+Ranking.tableName+"  (id) VALUES "+"('"+Ranking.currentSongID+"')";
        try {
            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(query)
                            .setUseLegacySql(false)
                            .build();
            Log.d("BigQueryActivity", "makeNewIdRow -created query config");

            JobId jobId = JobId.of(UUID.randomUUID().toString());
            Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
            Log.d("BigQueryActivity", "makeNewIdRow - before wait to job");
            queryJob = queryJob.waitFor();
            Log.d("BigQueryActivity", "makeNewIdRow - job has been done");
            if (queryJob == null) {
                throw new RuntimeException("makeNewIdRow - Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
        } catch (InterruptedException e) {
            Log.d("BigQueryActivity", "exception has been found");
            e.printStackTrace();
        }
        IntArrayResults.add(0);
        Ranking.voteArray = IntArrayResults;
    }





  }




        //should be run from outside
//    //Call the AsyncTask to run the BigQuery query on a separate thread
//    public void rungetQuery(View view) {
//        Log.d("BigQueryActivity", "runQuery has been called");
//        BigQueryActivity.GetBigQuery task = new BigQueryActivity.GetBigQuery("SELECT id FROM songspot.songspot_spotify.spotify_songs WHERE id = '7lmeHLHBe4nmXzuXc0HDjk'");
//        task.execute();
//    }



