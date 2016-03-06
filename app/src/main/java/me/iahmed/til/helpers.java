package me.iahmed.til;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Layout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class helpers {
    static final String pref_fname = "TIL_prefs";
    static private SharedPreferences.Editor ed;
    static private SharedPreferences settings=null;

    private static void openSettings(MainActivity c) {
        if (settings==null) {
            settings = c.getSharedPreferences(pref_fname, 0);
        }
    }

    protected static String getUUID(MainActivity c) {
        openSettings(c);
        String uuid = settings.getString("UUID", "");
        if (uuid.equals("")) {
            uuid = UUID.randomUUID().toString();
            putUUID(uuid);
        }
        return uuid;
    }

    private static void putUUID(String uuid) {
        startPrefEditor();
        ed.putString("UUID", uuid);
        closePrefEditor();
    }

    public static boolean getNightMode(MainActivity c) {
        openSettings(c);
        return settings.getBoolean("NightMode", false);
    }

    public static String getSearchMode(MainActivity c) {
        openSettings(c);
        return settings.getString("SearchMode", "hot");
    }

    public static void setSearchMode(MainActivity c, boolean _hot, boolean _top, boolean _new){
        openSettings(c);
        startPrefEditor();
        String mode="hot";
        if (_hot) {
            mode = "hot";
        } else if (_top) {
            mode = "top";
        } else if (_new) {
            mode = "new";
        }
        ReadRedditTitle.request_suburl = mode;
        ed.putString("SearchMode", mode);
        closePrefEditor();
        System.out.println("SearchMode options:" + _hot + " " + _top + " " + _new);
        System.out.println("setSearchMode called: " + mode);
    }

    public static void setNightMode(MainActivity c, boolean mode) {
        openSettings(c);
        startPrefEditor();
        ed.putBoolean("NightMode", mode);
        closePrefEditor();

        RelativeLayout l = (RelativeLayout) c.findViewById(R.id.relative_layout);
        TextView t = c.main_text;
        if (mode) {
            l.setBackgroundColor(Color.DKGRAY);
            t.setTextColor(Color.WHITE);
        } else {
            l.setBackgroundColor(Color.WHITE);
            t.setTextColor(Color.DKGRAY);
        }
    }

    private static void startPrefEditor() {
        ed = settings.edit();
    }

    private static void closePrefEditor() {
        ed.commit();
        ed = null;
    }
}
