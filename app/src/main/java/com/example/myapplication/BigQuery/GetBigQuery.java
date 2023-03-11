package com.example.myapplication.BigQuery;

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
    private final String query;
    private String queryType;
    private final String CREDENTIALS_FILE = "songspot-13c0986bb1d9.json";
    private final String PROJECT_ID = "songspot";
    BigQuery bigquery;
    List<String> results = new ArrayList<>();
    Integer songVotesNumber,songRateSum;

    public GetBigQuery(String query, String queryType,  Context context) {
        this.query = query;
        this.context = context;
        this.queryType = queryType;
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
    protected List<String> doInBackground(Void... params) {

        Log.d("BigQueryActivity", "do in background has been called");
        try {

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(query)
                            // .setDestinationTable(destinationTable)
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
                case "getIdratingSum":
                    for (FieldValueList row : result.iterateAll()) {
                        results.add(Long.toString(row.get(DataSingelton.getInstance().getColumnName()+"ratesum").getLongValue()));
                        Log.d("BigQueryActivity", Long.toString(row.get(DataSingelton.getInstance().getColumnName()+"ratesum").getLongValue()));
                    }
                    Ranking.idRatingSum = results;
                    if(Ranking.idRatingSum == null) Log.d("BigQueryActivity", "idRatingSumIsNull");

                    break;
                case "getIdNumberOfSongRate":
                    for (FieldValueList row : result.iterateAll()) {
                        results.add(Long.toString(row.get(DataSingelton.getInstance().getColumnName()+"votenum").getLongValue()));
                        Log.d("BigQueryActivity", Long.toString(row.get(DataSingelton.getInstance().getColumnName()+"votenum").getLongValue()));
                    }
                    Ranking.idVoteNum = results;
                    if(Ranking.idVoteNum == null) Log.d("BigQueryActivity", "idVoteNumIsNull");

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

        }
    }

        //should be run from outside
//    //Call the AsyncTask to run the BigQuery query on a separate thread
//    public void rungetQuery(View view) {
//        Log.d("BigQueryActivity", "runQuery has been called");
//        BigQueryActivity.GetBigQuery task = new BigQueryActivity.GetBigQuery("SELECT id FROM songspot.songspot_spotify.spotify_songs WHERE id = '7lmeHLHBe4nmXzuXc0HDjk'");
//        task.execute();
//    }


