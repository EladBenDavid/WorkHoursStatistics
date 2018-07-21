package com.bringg.elad.service.geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.bringg.elad.R;
import com.bringg.elad.service.listener.OnConnectionCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A single Geofence object, defined by its center and radius.
 */
public class GeofenceConnector implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // constance variables
    private static final String TAG = GeofenceConnector.class.getSimpleName();
    public final static String ID = "WORK_LOCATION";
   // For the purposes of this demo, the geofences are hard-coded and should not expire.
    public final static long EXPIRATION_DURATION = Geofence.NEVER_EXPIRE;

    public final static int TRANSITION_TYPE = Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_DWELL;


    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GeofencingClient mGeofencingClient;
    private final WeakReference<Context> mContext;
    private final WeakReference<OnConnectionCallback> mConnectionListener;
    private final Address mAddress;


    /**
     * @param address contain Latitude  and Longitude of the Geofence's center in degrees.
     * @param connectionListener listener for the connection result.
     */
    public GeofenceConnector(final Context context, Address address,
                             final OnConnectionCallback connectionListener){
        mContext = new WeakReference<>(context.getApplicationContext());
        mAddress = address;
        mConnectionListener = new WeakReference<>(connectionListener);
    }

    public void connect(){
        getGoogleApiClient().connect();
    }


    /**
     * Creates a Location Services Geofence object
     * @return A Geofence object.
     */
    private Geofence getGeofence() {
        int radius = mContext.get().getResources().getInteger(R.integer.geofence_radius);
        int loiteringDelay = mContext.get().getResources().getInteger(R.integer.geofence_loitering_delay);
        // Build a new Geofence object.
        return new Geofence.Builder()
                .setRequestId(ID)
                .setTransitionTypes(TRANSITION_TYPE)
                .setCircularRegion(mAddress.getLatitude(), mAddress.getLongitude(),radius )
                .setExpirationDuration(EXPIRATION_DURATION)
                .setLoiteringDelay(loiteringDelay)
                .build();
    }


    private GoogleApiClient getGoogleApiClient(){
        GoogleApiClient apiClient = new GoogleApiClient.Builder(mContext.get())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        return apiClient;
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL);
        List<Geofence> geofenceList = new ArrayList<>(1);
        geofenceList.add(getGeofence());
        builder.addGeofences(geofenceList);
        return builder.build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(mContext.get() != null) {
            if (mConnectionListener.get() != null &&
                    ContextCompat.checkSelfPermission(mContext.get(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Get the PendingIntent for the geofence monitoring request.
                // Send a request to add the current geofences.
                Intent intent = new Intent(mContext.get(), GeofenceTransitionsIntentService.class);
                mGeofenceRequestIntent = PendingIntent.getService(mContext.get(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mGeofencingClient = LocationServices.getGeofencingClient(mContext.get());
                mGeofencingClient.addGeofences(getGeofencingRequest(), mGeofenceRequestIntent)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess"))
                        .addOnFailureListener(e -> Log.e(TAG, "onFailure", e));
                Toast.makeText(mContext.get(), mContext.get().getString(R.string.start_geofence_service), Toast.LENGTH_SHORT).show();
                mConnectionListener.get().onSuccess();
            } else {
                Toast.makeText(mContext.get(), mContext.get().getString(R.string.location_permission), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mContext.get() != null && mGeofenceRequestIntent != null ) {
            LocationServices.getGeofencingClient(mContext.get()).removeGeofences(mGeofenceRequestIntent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(mConnectionListener.get() != null) {
            mConnectionListener.get().onFailure(connectionResult);
        }
    }
}
