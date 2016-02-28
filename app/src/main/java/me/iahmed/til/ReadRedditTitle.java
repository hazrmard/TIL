package me.iahmed.til;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class ReadRedditTitle {

    private static String client_id = config.client_id;
    private static String url = "https://www.reddit.com/api/v1/access_token";
    private static String UserAgent = "android:me.iahmed.til:0.5 (by /u/hazrmard)";

    static Integer get_token(Context c) {
        System.out.println("Getting token...");
        try {
            URL url = new URL(ReadRedditTitle.url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String encoding = Base64.encodeToString((ReadRedditTitle.client_id + ":").getBytes(), Base64.NO_PADDING);
            conn.setRequestProperty("Authorization", "Basic " + encoding);
            conn.setRequestProperty("User-Agent", UserAgent);

            String params = "grant_type=" + URLEncoder.encode("https://oauth.reddit.com/grants/installed_client", "UTF-8");
            params += "&device_id=" + URLEncoder.encode(helpers.getUUID(c), "UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            int response_code = conn.getResponseCode();


            InputStream in = (InputStream) conn.getInputStream();
            System.out.println(in.read());

            in.close();

            return response_code;

        } catch (IOException e) {
            //Like I care
            return -1;
        }
        //return true;
    }

    static void authenticate(){
        System.out.println("Authenticating.....");
    }
}
