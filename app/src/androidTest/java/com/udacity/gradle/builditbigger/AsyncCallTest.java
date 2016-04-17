package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import java.util.concurrent.TimeUnit;

/**
 * Created by Derek on 4/17/2016.
 */
public class AsyncCallTest extends AndroidTestCase {
    public AsyncCallTest(){ super(); }

    public void testNonNullResponse() {

        // taken from: https://discussions.udacity.com/t/writing-tests-for-async-task/25482/7

        // this test works on the emulator (5554 Nexus 5) with the GCE running (jokebackend)
        // this test times out if there is no response from the GCE within 10 seconds
        // (ie the jokebackend is not running)
        try {
            GetJokeAsyncTask jokeTask = new GetJokeAsyncTask();
            jokeTask.execute(getContext());
            String joke = jokeTask.get(10, TimeUnit.SECONDS);

            // the purpose of this test is to confirm that the string returned from the server
            // is not null (presumably it contains a joke!)
            assertNotNull(joke);

        } catch (Exception e){
            fail("Timed out");
        }
    }
}
