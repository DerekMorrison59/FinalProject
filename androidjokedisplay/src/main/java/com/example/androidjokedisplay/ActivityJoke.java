package com.example.androidjokedisplay;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityJoke extends AppCompatActivity {
    //private final String LOG_TAG = ActivityJoke.class.getSimpleName();

    public static final String JOKE_KEY = "JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // set up the action bar to display the 'back' arrow
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // provide a default string to display
        String joke = "Nothing received from the library and that's no joke!";

        // the intent should have an extras bundle that contains the joke string
        Bundle bun = getIntent().getExtras();

        if (null != bun) {
            if (bun.containsKey(JOKE_KEY)) {
                joke = bun.getString(JOKE_KEY);
            }
        }

        TextView jokeSpace = (TextView) findViewById(R.id.jokeTextView);
        if (null != jokeSpace) {
            jokeSpace.setText(joke);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Android home means return to the main activity
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
