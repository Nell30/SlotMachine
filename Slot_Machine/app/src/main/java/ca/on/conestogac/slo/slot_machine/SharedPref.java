package ca.on.conestogac.slo.slot_machine;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    final String PREF_NAME = "APP_DATA";

    private Context context;
    private SharedPreferences preferences;

    SharedPref(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    int getInt(String key) {
        return preferences.getInt(key,0);
    }

    Boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }



}
