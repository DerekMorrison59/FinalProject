package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jokebackend.myApi.MyApi;
import com.example.androidjokedisplay.ActivityJoke;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Derek on 4/17/2016.
 */
public class GetJokeAsyncTask extends AsyncTask<Context, Void, String> {
    private final String LOG_TAG = GetJokeAsyncTask.class.getSimpleName();

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        //Log.i(LOG_TAG, "doInBackground");

        if(myApiService == null) {  // Only do this once
            Log.i(LOG_TAG, "Creating MyApi");
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            //Log.i(LOG_TAG, "building MyApi");

            myApiService = builder.build();
        }

        // the Context required in onPostExecute to launch the ActivityJoke class is passed in as the first parameter
        context = params[0];

        // set the delay value from resources
        int delay = context.getResources().getInteger(R.integer.launch_delay_ms);
        //Log.i(LOG_TAG, ">> doInBackground calling library to get a joke, delay = " + delay);

        // this delay only exists so there is time to show the spinner on the main fragment
        // the speed of the local demo server is very fast and the spinner barely shows up without this delay
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.i(LOG_TAG, "***** EX call to myApiService failed: " + e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Log.i(LOG_TAG, "onPostExecute - joke: " + result);

        Intent jokeIntent = new Intent(context, ActivityJoke.class);
        jokeIntent.putExtra(ActivityJoke.JOKE_KEY, result);
        jokeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(jokeIntent);
    }
}
