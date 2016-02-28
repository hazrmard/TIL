package me.iahmed.til;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

/**
 * Created by Ibrahim on 2/27/2016.
 */
public class helpers {
    static final String pref_fname = "TIL_prefs";
    static private SharedPreferences.Editor ed;
    static private SharedPreferences settings=null;

    private static void openSettings(Context c) {
        if (settings==null) {
            settings = c.getSharedPreferences(pref_fname, 0);
        }
    }

    protected static String getUUID(Context c) {
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

    public boolean getNightMode(Context c) {
        openSettings(c);
        return settings.getBoolean("NightMode", false);
    }

    public static void setNightMode(Context c) {
        openSettings(c);
        startPrefEditor();
        ed.putBoolean("NightMode", false);
        closePrefEditor();
    }

    private static void startPrefEditor() {
        ed = settings.edit();
    }

    private static void closePrefEditor() {
        ed.commit();
        ed = null;
    }
}
