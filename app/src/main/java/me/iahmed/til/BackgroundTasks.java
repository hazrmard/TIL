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

            final MainActivity context = c[0];

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    context.main_text.setText("TOKEN: " + ReadRedditTitle.token);
                }
            });
            return ReadRedditTitle.token;
        }


    }
}
