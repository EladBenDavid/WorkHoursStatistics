package com.bringg.elad.service.geofence;

import android.content.Intent;

import com.bringg.elad.BuildConfig;
import com.bringg.elad.application.TestWorkHoursStatisticsApplication;
import com.bringg.elad.preferences.FakeSharedPreferencesUtility;
import com.bringg.elad.repository.FakeRepository;
import com.bringg.elad.service.geofence.GeofenceTransitionsIntentService;
import com.bringg.elad.service.model.WorkingDay;
import com.google.android.gms.location.Geofence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.Map;

import static com.bringg.elad.service.geofence.GeofenceTransitionsIntentService.ENTER_TIMESTAMP_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

// this class test the behavior of GeofenceTransitionsIntent
// particularly the in out fence events
public class GeofenceTransitionsIntentServiceTests {
    // this params take from com.google.android.gms.location.GeofencingEven class
    private static final String TRANSITION_KEY ="com.google.android.location.intent.extra.transition";
    private static final String ERROR_CODE_KEY = "gms_error_code";
    private static final String GEOFENCE_LIST_KEY = "com.google.android.location.intent.extra.geofence_list";
    private static final String triggering_location_key = "com.google.android.location.intent.extra.triggering_location";
    // we generate this class using Robolectric
    GeofenceTransitionsIntentService geoService;
    TestWorkHoursStatisticsApplication application;
    // represent the application state
    Map<String,Long> sharedPreferences;
    List<WorkingDay> dataBase;


    public void init(){
        application = (TestWorkHoursStatisticsApplication) RuntimeEnvironment.application;
        geoService = Robolectric.
                buildIntentService(GeofenceTransitionsIntentService.class).
                create().
                get();
        geoService.onCreate();
        sharedPreferences = FakeSharedPreferencesUtility.sharedPreferences;
        dataBase = FakeRepository.dataBase;
    }

    @Before
    public void setup(){
        if(application == null){
            init();
        }
        // we clear the application state after each test
        sharedPreferences.clear();
        dataBase.clear();
    }
    // generic function for sending event to GeofenceTransitionsIntentService
    private void sentEvent(int type){
        Intent intent =  new Intent(application, GeofenceTransitionsIntentService.class);
        intent.putExtra(TRANSITION_KEY, type);
        geoService.onHandleIntent(intent);
    }

    private void sentErrorEvent(int type){
        Intent intent =  new Intent(application, GeofenceTransitionsIntentService.class);
        intent.putExtra(TRANSITION_KEY, type);
        intent.putExtra(ERROR_CODE_KEY, type);
        geoService.onHandleIntent(intent);
    }

    @Test
    // we send only 'in' event, so the sharedPreferences should contain the event timestamp
    public void dwell_event() {
        sentEvent(Geofence.GEOFENCE_TRANSITION_DWELL);
        assertNotNull(sharedPreferences.get(ENTER_TIMESTAMP_KEY));
        assertEquals(0, dataBase.size());
    }



    @Test
    // we send 'in' and then 'out' event, so only in DB should be the data
    public void dwell_and_exit_event() {
        sentEvent(Geofence.GEOFENCE_TRANSITION_DWELL);
        sentEvent(Geofence.GEOFENCE_TRANSITION_EXIT);
        assertEquals(1, dataBase.size());
        assertNull(sharedPreferences.get(ENTER_TIMESTAMP_KEY));
    }

    @Test
    // in case we didn't had enter event we don't count the 'out' event
    public void only_exit_event() {
        sentEvent(Geofence.GEOFENCE_TRANSITION_EXIT);
        assertEquals(0, dataBase.size());
        assertNull(sharedPreferences.get(ENTER_TIMESTAMP_KEY));
    }

    @Test
    // in case we receive unknown event we don't change the app state
    public void unsupported_event() {
        sentEvent(Integer.MAX_VALUE);
        assertEquals(0, dataBase.size());
        assertEquals(0, sharedPreferences.size());
    }

    @Test
    // in case we receive error we don't change the app state
    public void error_case() {
        sentErrorEvent(Integer.MAX_VALUE);
        assertEquals(0, dataBase.size());
        assertEquals(0, sharedPreferences.size());
    }
}
