package com.bringg.elad.ui.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bringg.elad.R;
import com.bringg.elad.service.storage.preferences.SharedPreferencesUtility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    WorkAddressConfigurationFragment workAddressConfigurationFragment;
    WorkingDaysFragment workingDaysFragment;
    @Inject SharedPreferencesUtility mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Rather than displayng this activity, simply display a toast indicating that the geofence
        // service is being created. This should happen in less than a second.
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(this, getString(R.string.google_play_services_unavailable), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Google Play services unavailable.");
            return;
        }
        // ask location permission
        int accessFineLocationPermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ;
        if(accessFineLocationPermissionStatus == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        // init screen fragments
        workAddressConfigurationFragment = new WorkAddressConfigurationFragment();
        workingDaysFragment = new WorkingDaysFragment();
        // init screen navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        // set the address fragment as default
        navigation.setSelectedItemId(R.id.address);
        updateFragment(workAddressConfigurationFragment);
    }

    /**
     * Checks if Google Play services is available.
     * @return true if it is.
     */
    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.e(TAG, "Google Play services is unavailable.");
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statics:
                updateFragment(workingDaysFragment);
                return true;
            case R.id.location:
                updateFragment(workAddressConfigurationFragment);
                return true;
        }
        return false;
    }

    private void updateFragment(Fragment fragment){

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        fragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragmentsContainer' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentsContainer, fragment).commit();
    }
}
