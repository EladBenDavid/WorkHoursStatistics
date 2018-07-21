package com.bringg.elad.service.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferencesUtility {
    public static final int UNFOUNDED_LAST_ENTER_VALUE = -1;
    private static final String GEOFENCETRANSITIONS_FILE_NAME = "geofencetransitions_file_name";
    private SharedPreferences mSharedPreferences;

    protected SharedPreferencesUtility(){

    }

    @Inject
    public SharedPreferencesUtility(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(GEOFENCETRANSITIONS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void putData(String key, long data) {
        mSharedPreferences.edit().putLong(key,data).apply();
    }


    public long getData(String key) {
        return mSharedPreferences.getLong(key,UNFOUNDED_LAST_ENTER_VALUE);
    }

    public void removeData(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

}
