package com.udacity.gradle.builditbigger;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar mSpinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // find the spinner and hide it for now
        mSpinner = (ProgressBar) root.findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE);

        // find the joke button and attach a click listener to it
        Button button = (Button)root.findViewById(R.id.jokeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpinner.setVisibility(View.VISIBLE);

                // this Async class will request a joke from the GCE and then send it to a class
                // in the androidjokelibrary so it can be displayed
                new GetJokeAsyncTask().execute(getActivity());
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mSpinner) {
            mSpinner.setVisibility(View.GONE);
        }
    }
}
