package com.bringg.elad.application;

import com.bringg.elad.preferences.FakeSharedPreferencesModule;
import com.bringg.elad.repository.FakeRepositoryModule;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

public class TestWorkHoursStatisticsApplication extends WorkHoursStatisticsApplication implements TestLifecycleApplication {
    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerApplicationComponent.builder().
                sharedPreferencesModule(new FakeSharedPreferencesModule(this)).
                repositoryModule(new FakeRepositoryModule(this)).
                build();

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }




    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {

    }

    @Override
    public void afterTest(Method method) {

    }
}
