package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Derek on 4/17/2016.
 * Contains the button that requests a joke and the spinner to indicate 'work in progress'
 */
public class MainActivityFragment extends Fragment {
    private InterstitialAd mInterstitialAd;
    private ProgressBar mSpinner;
    private int mInterstitial_click_count;
    private int mClick_count_max;
    private boolean mShow_interstitial = false;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        // make sure the spinner is not visible when the user returns here from seeing a joke
        if (null != mSpinner) {
            mSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // find the banner ad widget
        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // find the spinner and hide it for now
        mSpinner = (ProgressBar) root.findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE);

        // setup for the Interstitial Ad
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getActivity().getString(R.string.interstitial_ad_id));
        mClick_count_max = getActivity().getResources().getInteger(R.integer.ad_free_clicks);
        mInterstitial_click_count = mClick_count_max;

        // find the joke button and attach a click listener to it
        Button button = (Button)root.findViewById(R.id.jokeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInterstitial_click_count++;

                if (mInterstitial_click_count >= mClick_count_max) {
                    mInterstitial_click_count = 0;
                    mShow_interstitial = true;
                }

                // show the Interstitial Ad if the button has been clicked enough and the Ad is ready
                if (mShow_interstitial && mInterstitialAd.isLoaded()) {
                    mShow_interstitial = false;
                    mInterstitialAd.show();
                } else {
                    // show the spinner while waiting for the requested joke to be displayed
                    mSpinner.setVisibility(View.VISIBLE);
                    requestJoke();
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // when the user closes the current Interstitial Ad then request a new Ad from Google
                requestNewInterstitial();

                // display the spinner while waiting for the joke to arrive from the server
                mSpinner.setVisibility(View.VISIBLE);

                requestJoke();
            }
        });

        // start the Interstitial Ad process by getting the first Ad (when this view is created)
        requestNewInterstitial();

        return root;
    }

    private void requestJoke(){
        // this Async class will request a joke from the GCE and then send it to a class
        // in the androidjokelibrary so it can be displayed
        new GetJokeAsyncTask().execute(getActivity());
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}