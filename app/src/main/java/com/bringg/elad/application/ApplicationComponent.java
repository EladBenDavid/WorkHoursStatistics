package com.bringg.elad.application;

import com.bringg.elad.service.geofence.GeofenceTransitionsIntentService;
import com.bringg.elad.service.repository.RepositoryModule;
import com.bringg.elad.service.storage.preferences.SharedPreferencesModule;
import com.bringg.elad.ui.viewmodel.WorkingDaysViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, SharedPreferencesModule.class})
public interface ApplicationComponent {
    void inject(GeofenceTransitionsIntentService geofenceTransitions);
    void inject(WorkingDaysViewModel workingDaysViewModel);
}
