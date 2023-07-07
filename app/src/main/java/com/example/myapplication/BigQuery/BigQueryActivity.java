package com.example.myapplication.BigQuery;

import androidx.appcompat.app.AppCompatActivity;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.ServiceAccountJwtAccessCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class BigQueryActivity extends AppCompatActivity {
    private final String CREDENTIALS_FILE = "songspot-13c0986bb1d9.json";
    private final String PROJECT_ID = "songspot";
    Button queryget,queryset;
    BigQuery bigquery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_query);
        queryget = findViewById(R.id.query_btn);
        queryset = findViewById(R.id.query_btn2);
        try {
            bigquery = BigQueryOptions.newBuilder().setProjectId(PROJECT_ID)
                    .setCredentials(ServiceAccountCredentials.fromStream(BigQueryActivity.this.getAssets()
                            .open(CREDENTIALS_FILE))).
                    build().
                    getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        queryget.setOnClickListener((View v) -> {
            rungetQuery(v);
        });
        queryset.setOnClickListener((View v) -> {
            runsetQuery(v);
        });
    }

    //   Define an AsyncTask to run the BigQuery query on a separate thread
    private class GetQueryTask extends AsyncTask<Void, Void, List<String>> {
        private final String query;
        public GetQueryTask(String query) {
            this.query = query;
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
                // Extract the results and return them as a list of strings
                List<String> results = new ArrayList<>();
                for (FieldValueList row : result.iterateAll()) {
                    results.add(row.get("id").getStringValue());
                    Log.d("BigQueryActivity", row.get("id").getStringValue());
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

        //Call the AsyncTask to run the BigQuery query on a separate thread
        public void rungetQuery(View view) {
            Log.d("BigQueryActivity", "runQuery has been called");
            GetQueryTask task = new GetQueryTask("SELECT id FROM songspot.songspot_spotify.spotify_songs WHERE id = '7lmeHLHBe4nmXzuXc0HDjk'");
            task.execute();
        }



    private class SetQueryTask extends AsyncTask<Void, Void, List<String>> {
        private final String query;
        public SetQueryTask(String query) {
            this.query = query;
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
        protected void onPostExecute (List < String > results) {
            // Update the UI with the results
            Log.d("BigQueryActivity", "SetQueryTask - onPostExecute has been called");
        }
    }
    public void runsetQuery(View view) {
        Log.d("BigQueryActivity", "SetQueryTask - runQuery has been called");
        SetQueryTask task = new SetQueryTask("UPDATE songspot.songspot_spotify.spotify_songs SET male1 = '3' WHERE id = '3YpLIrG8hG6fACaFA7NAxM'");
        task.execute();
    }
}


