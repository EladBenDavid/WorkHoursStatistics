package com.bringg.elad.preferences;

import android.content.Context;

import com.bringg.elad.service.storage.preferences.SharedPreferencesUtility;

import java.util.HashMap;
import java.util.Map;

public class FakeSharedPreferencesUtility extends SharedPreferencesUtility{

    public final static Map<String,Long> sharedPreferences = new HashMap<>();
    public FakeSharedPreferencesUtility(Context context) {
        super();
    }

    public void putData(String key, long data) {
        sharedPreferences.put(key,data);
    }


    public long getData(String key) {
        if(sharedPreferences.containsKey(key)) {
            return sharedPreferences.get(key);
        }
        return UNFOUNDED_LAST_ENTER_VALUE;
    }

    public void removeData(String key) {
        sharedPreferences.remove(key);
    }

}
