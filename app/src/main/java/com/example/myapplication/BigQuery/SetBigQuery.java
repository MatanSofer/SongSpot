package com.example.myapplication.BigQuery;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SetBigQuery extends AsyncTask<Void, Void, List<String>> {
    private Context context;
    private final String query;
    private final String CREDENTIALS_FILE = "songspot-13c0986bb1d9.json";
    private final String PROJECT_ID = "songspot";
    BigQuery bigquery;

    public SetBigQuery(String query,Context context) {
        this.query = query;
        this.context = context;
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
        Log.d("BigQueryActivity", "SetQueryTask - do in background has been called");
        try {

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(query)
                            .setUseLegacySql(false)
                            .build();

            Log.d("BigQueryActivity", "SetQueryTask -created query config");

            JobId jobId = JobId.of(UUID.randomUUID().toString());
            Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
            Log.d("BigQueryActivity", "SetQueryTask - before wait to job");
            queryJob = queryJob.waitFor();
            Log.d("BigQueryActivity", "SetQueryTask - job has been done");
            if (queryJob == null) {
                throw new RuntimeException("SetQueryTask - Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
        } catch (InterruptedException e) {
            Log.d("BigQueryActivity", "exception has been found");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute (List <String> results) {
        // Update the UI with the results
        Log.d("BigQueryActivity", "SetQueryTask - onPostExecute has been called");
    }
}
//    public void runsetQuery(View view) {
//        Log.d("BigQueryActivity", "SetQueryTask - runQuery has been called");
//        BigQueryActivity.SetQueryTask task = new BigQueryActivity.SetQueryTask("UPDATE songspot.songspot_spotify.spotify_songs SET male1 = '3' WHERE id = '3YpLIrG8hG6fACaFA7NAxM'");
//        task.execute();
//    }
