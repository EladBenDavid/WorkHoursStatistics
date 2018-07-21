package com.bringg.elad.preferences;

import android.content.Context;

import com.bringg.elad.service.storage.preferences.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FakeSharedPreferencesModule extends SharedPreferencesModule{

    Context mContext;

    public FakeSharedPreferencesModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public FakeSharedPreferencesUtility provideSharedPreferences() {
        return new FakeSharedPreferencesUtility(mContext);
    }
}
