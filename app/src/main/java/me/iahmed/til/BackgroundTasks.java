package me.iahmed.til;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class BackgroundTasks {

    public static class GetToken extends AsyncTask<MainActivity,Void,Integer> {

        protected Integer doInBackground(MainActivity... c) {

            final MainActivity context = c[0];

            int status1 = ReadRedditTitle.get_token(c[0]);
            int status2 = ReadRedditTitle.refill_queue(context);

            if (status1==-1 || status2 ==-1) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,R.string.loading_error,Toast.LENGTH_SHORT).show();
                    }
                });
            }

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

            final MainActivity context = c[0];

            if (ReadRedditTitle.refill_queue(context)==-1) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,R.string.loading_error,Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return 0;
        }
    }
}
