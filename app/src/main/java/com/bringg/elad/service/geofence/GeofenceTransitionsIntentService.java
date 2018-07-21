package com.bringg.elad.service.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bringg.elad.R;
import com.bringg.elad.application.WorkHoursStatisticsApplication;
import com.bringg.elad.service.model.WorkingDay;
import com.bringg.elad.service.repository.Repository;
import com.bringg.elad.service.storage.preferences.SharedPreferencesUtility;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import javax.inject.Inject;

import static com.bringg.elad.service.storage.preferences.SharedPreferencesUtility.UNFOUNDED_LAST_ENTER_VALUE;

public class GeofenceTransitionsIntentService extends IntentService {
    private static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();
    protected static final String ENTER_TIMESTAMP_KEY = "enter_timestamp_key";

    @Inject
    public Repository mRepository;
    @Inject
    public SharedPreferencesUtility mSharedPreferences;


    public GeofenceTransitionsIntentService() {
        // make sure we calling the other constructor for inject mSharedPreferences
        this(TAG);
        Log.d(TAG, "constructor");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GeofenceTransitionsIntentService(String name) {
        super(name);
        Log.d(TAG, "constructor:" + name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((WorkHoursStatisticsApplication)getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, getString(R.string.geofencing_event_error_code) + Integer.toString(geofencingEvent.getErrorCode()));
            return;
        }
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            Log.d(TAG, "in");
            mSharedPreferences.putData(ENTER_TIMESTAMP_KEY,  System.currentTimeMillis());
        } else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.d(TAG, "out");
            long lastEnter = mSharedPreferences.getData(ENTER_TIMESTAMP_KEY);
            if(lastEnter != UNFOUNDED_LAST_ENTER_VALUE ){
                WorkingDay workingDay = new WorkingDay(lastEnter, System.currentTimeMillis());
                mRepository.insertWorkingDay(workingDay);
                // remove key for reduce error in case we miss the enter
                mSharedPreferences.removeData(ENTER_TIMESTAMP_KEY);
            }
        }else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type) + geofenceTransition);
        }
    }

    protected GeofencingEvent getGeofencingEvent(Intent intent){
        return GeofencingEvent.fromIntent(intent);
    }
}
