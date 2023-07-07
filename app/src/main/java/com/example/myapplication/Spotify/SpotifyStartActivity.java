package com.example.myapplication.Spotify;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.BigQuery.GetBigQuery;
import com.example.myapplication.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class SpotifyStartActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "43af88b8aecf4b3ab2c8a8d9021fec40";
    private static final String REDIRECT_URI = "com.example.myapplication://callback";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "playlist-read-private,user-read-private";
    SpotifyAppRemote mSpotifyAppRemote;
    private static String token;

    boolean loginAlreadyFailed = false;
    TextView spotifyMsg;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Spotify:","SpotifyStartActivity-onCreate() - called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_start);
          spotifyMsg = findViewById(R.id.spotify_connect);
          lottie = findViewById(R.id.lottie_spotify);

          spotifyMsg.animate().translationY(-1400).setDuration(1200).setStartDelay(0);
          lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2000);

        Thread waitUntilAuthenticated = new Thread(() -> {
            while (mSpotifyAppRemote == null || token == null) {}
            Log.d("Spotify:","SpotifyStartActivity - onCreate() - exit from while");
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ignored) {}
            Log.d("Spotify:","SpotifyStartActivity-onCreate() - move to spotifyStartActivity");
            Intent newIntent = new Intent(SpotifyStartActivity.this, SpotifyMainActivity.class);
            newIntent.putExtra("token", token);
            SpotifyMainActivity.spotifyAppRemote = mSpotifyAppRemote;
            startActivity(newIntent);
            finishAffinity();
        });

        waitUntilAuthenticated.start();
        PackageManager pm = getPackageManager();
        boolean isSpotifyInstalled;
        try {
            pm.getPackageInfo("com.spotify.music", 0);
            isSpotifyInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isSpotifyInstalled = false;
        }

        if (isSpotifyInstalled) {
            Log.d("Spotify:","SpotifyStartActivity-onStart() - spotify is installed!");
            connectToSpotifyApp();
        }
        else {
            Log.d("Spotify:","SpotifyStartActivity-onStart() - spotify is not installed!");
            //TODO - MAKE A DIALOG
            getSpotify();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Spotify:","SpotifyStartActivity - onStart() called");
    }

    private void getSpotify() {
        Log.d("SpotifyStartActivity:","getSpotify() - getSpotify called!");
        final String referrer = "adjust_campaign=PACKAGE_NAME&adjust_tracker=ndjczk&utm_source=adjust_preinstall";

        try {
            Uri uri = Uri.parse("market://details")
                    .buildUpon()
                    .appendQueryParameter("id", "com.spotify.music")
                    .appendQueryParameter("referrer", referrer)
                    .build();
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (android.content.ActivityNotFoundException ignored) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details")
                    .buildUpon()
                    .appendQueryParameter("id", "com.spotify.music")
                    .appendQueryParameter("referrer", referrer)
                    .build();
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
        finish();
    }

    // SPOTIFY SDK
    private void connectToSpotifyApp() {
        Log.d("Spotify:","SpotifyStartActivity-connectToSpotifyApp() - connectToSpotifyApp called!");
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        Log.d("Spotify:","SpotifyStartActivity-connectToSpotifyApp() - connect sdk success!");
                        mSpotifyAppRemote = spotifyAppRemote;
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("Spotify:","SpotifyStartActivity - connectToSpotifyApp() - connect sdk WASNT success!");
                        Log.e("Spotify:", throwable.getMessage(), throwable);

                        if (loginAlreadyFailed) {
                            Log.d("Spotify:","SpotifyStartActivity-connectToSpotifyApp() - connect sdk WASNT success -loginAlreadyFailed!");
                            finishAffinity();
                        }
                        loginAlreadyFailed = true;
                        //TODO - HANDLE FAILURE ALSO WITH UI
                    }
                });

        connectToSpotifyAPI();
    }


    // SPOTIFY WEB API
    private void connectToSpotifyAPI() {
        Log.d("Spotify:","SpotifyStartActivity - connectToSpotifyAPI() -  connectToSpotifyAPI called");
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("Spotify:","SpotifyStartActivity - onActivityResult() -  onActivityResult called");
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    token = response.getAccessToken();
                    Log.d("Spotify:","SpotifyStartActivity - onActivityResult() -  found token : " +token);
                    break;

                // Auth flow returned an error
                case ERROR:
                    // TODO, Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

}