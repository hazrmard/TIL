package me.iahmed.til;

import android.content.Context;
import android.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class ReadRedditTitle {

    private static String client_id = config.client_id;
    private static String access_url = "https://www.reddit.com/api/v1/access_token";
    protected static String request_url = "https://oauth.reddit.com/r/TodayILearned/";
    protected static String request_suburl = "hot";
    private static String UserAgent = "android:me.iahmed.til:0.6 (by /u/hazrmard)";
    private static String anchor = "";
    public static String token = null;
    public static List<Entry> entries = Collections.synchronizedList(new ArrayList<Entry>());

    private static Object streamToJson(InputStream in) {        //cast to JSONObject
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            return new JSONObject(responseStrBuilder.toString());
        } catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
            return new Object();
        }
    }

    private static String constructURL() {
        String queries = "";
        if (request_suburl.equals("top")) {
            queries = "&t=day";
        }
        String url = request_url + request_suburl + "?after=" + anchor + queries;
        System.out.println("Contructed URL:" + url);
        return url;
    }

    static Integer get_token(MainActivity c) {
        System.out.println("Getting token...");
        try {
            URL url = new URL(ReadRedditTitle.access_url + "?after=" + anchor);
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

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                return -1;
            }

            InputStream in = (InputStream) conn.getInputStream();
            JSONObject json_response = (JSONObject) streamToJson(in);
            token = json_response.getString("access_token");
            System.out.println("TOKEN: " + token);
            in.close();

            return 0;

        } catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        //return true;
    }

    static int refill_queue(MainActivity c){

        System.out.println("Getting titles.....");
        try {

            URL url = new URL(constructURL());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "bearer " + token);

            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                System.out.println("Good response");
            }   else if (conn.getResponseCode()==HttpsURLConnection.HTTP_UNAUTHORIZED) {
                get_token(c);
                refill_queue(c);
            }   else {
                System.out.println("Bad response.");
                return -1;
            }

            InputStream in = (InputStream) conn.getInputStream();

            JSONObject json_response = (JSONObject) streamToJson(in);

            //JSON format: data.children[].data{}
            JSONObject j_obj = json_response.getJSONObject("data");
            anchor = j_obj.getString("after");
            System.out.println("After parameter: " + anchor);
            JSONArray j_array = j_obj.getJSONArray("children");
            for (int i=0; i<j_array.length(); i++) {
                JSONObject j = j_array.getJSONObject(i).getJSONObject("data");
                if (j.isNull("distinguished") && !j.getBoolean("stickied")
                                && !j.getBoolean("is_self") && j.getInt("score")>0) {        // check if a Mod-post or not
                    Entry e = new Entry(j.getString("title"), j.getString("author"), j.getString("url"));
                    entries.add(e);
                }
            }
            System.out.println("Got " + entries.size() + " titles.");

        } catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }

    static int flush_queue() {
        entries.clear();
        anchor="";
        System.out.println("Flushed entries queue. Cleared anchor.");
        return 0;
    }
}
