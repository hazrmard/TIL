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

            byte[] encoding = Base64.encode((ReadRedditTitle.client_id + ":").getBytes(), Base64.DEFAULT);
            String S_encoding = encoding.toString();
            conn.setRequestProperty("Authorization", "Basic " + S_encoding);

            conn.setRequestProperty("User-Agent", UserAgent);

            JSONObject params = new JSONObject();
            params.put("grant_type", "https://oauth.reddit.com/grants/installed_client");
            params.put("device_id", helpers.getUUID(c));
            String enc_params = URLEncoder.encode(params.toString(), "UTF-8");

            OutputStream os = conn.getOutputStream();
            os.write(enc_params.getBytes());
            os.flush();
            os.close();

            int response_code = conn.getResponseCode();
            return response_code;

            //InputStream in = (InputStream) conn.getInputStream();
            //System.out.println(in.read());

            //in.close();

        } catch (IOException | JSONException e) {
            //Like I care
            return -1;
        }
        //return true;
    }

    static void authenticate(){
        System.out.println("Authenticating.....");
    }
}
