package me.iahmed.til;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class BackgroundTasks {

    public static class GetToken extends AsyncTask<MainActivity,Void,Integer> {

        protected Integer doInBackground(MainActivity... c) {

            ReadRedditTitle.get_token(c[0]);
            ReadRedditTitle.refill_queue();

            final MainActivity context = c[0];
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(context.fab, R.string.loaded, Snackbar.LENGTH_SHORT);
                }
            });

            //return ReadRedditTitle.token;
            return 0;
        }
    }

    public static class RefillQueue extends AsyncTask<MainActivity, Void, Integer> {

        protected Integer doInBackground(MainActivity... c) {

            return ReadRedditTitle.refill_queue();
        }
    }
}
