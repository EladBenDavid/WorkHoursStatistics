package com.bringg.elad.application;

import android.app.Application;

import com.bringg.elad.service.repository.RepositoryModule;
import com.bringg.elad.service.storage.preferences.SharedPreferencesModule;

public class WorkHoursStatisticsApplication extends Application {

    private ApplicationComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerApplicationComponent.builder().
                sharedPreferencesModule(new SharedPreferencesModule(this)).
                repositoryModule(new RepositoryModule(this)).
                build();
    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
