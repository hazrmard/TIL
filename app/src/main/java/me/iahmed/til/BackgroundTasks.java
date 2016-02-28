package me.iahmed.til;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class BackgroundTasks {

    public static class GetToken extends AsyncTask<MainActivity,Void,String> {

        protected String doInBackground(MainActivity... c) {

            ReadRedditTitle.get_token(c[0]);

            return ReadRedditTitle.token;
        }
    }

    public static class RefillQueue extends AsyncTask<MainActivity, Void, Integer> {

        protected Integer doInBackground(MainActivity... c) {

            return ReadRedditTitle.refill_queue();
        }
    }
}
