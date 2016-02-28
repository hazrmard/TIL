package me.iahmed.til;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class BackgroundTasks {

    public static class GetToken extends AsyncTask<Context,Void,String> {

        protected String doInBackground(Context... c) {

            return ReadRedditTitle.get_token(c[0]).toString();
        }
    }
}
