package com.bringg.elad.service.storage.preferences;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    Context mContext;

    public SharedPreferencesModule(Context context){
        mContext = context;
    }

    public SharedPreferencesModule() {
    }

    @Provides
    @Singleton
    public SharedPreferencesUtility provideSharedPreferences() {
        return new SharedPreferencesUtility(mContext);
    }
}
