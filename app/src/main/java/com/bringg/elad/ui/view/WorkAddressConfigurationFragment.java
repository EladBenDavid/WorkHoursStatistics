package com.bringg.elad.ui.view;

import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bringg.elad.BuildConfig;
import com.bringg.elad.R;
import com.bringg.elad.service.geofence.GeofenceConnector;
import com.bringg.elad.service.listener.OnConnectionCallback;
import com.google.android.gms.common.ConnectionResult;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesApi;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WorkAddressConfigurationFragment extends Fragment implements OnPlaceSelectedListener {
    private static final String TAG = WorkAddressConfigurationFragment.class.getSimpleName();
    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // init view components
        View view = inflater.inflate(R.layout.fragment_work_address_config, container, false);
        LinearLayout placesAutocompleteContainer = view.findViewById(R.id.places_autocomplete_container);
        PlacesAutocompleteTextView placesAutocomplete = new PlacesAutocompleteTextView(getContext(),
                BuildConfig.GoogleMapsApiKey);
        LinearLayout.LayoutParams placesAutocompleteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        placesAutocomplete.setLayoutParams(placesAutocompleteParams);
        placesAutocompleteContainer.addView(placesAutocomplete);
        placesAutocomplete.setOnPlaceSelectedListener(this);
        return view;
    }
    // Geofence connection listener
    OnConnectionCallback mGeofenceConnectionCallback =  new OnConnectionCallback() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
            hideKeyboard();
        }

        @Override
        public void onFailure(ConnectionResult connectionResult) {
            resolutionGooglePlayProblem(connectionResult);
        }
    };


    @Override
    public void onPlaceSelected(@NonNull Place place) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            // get lat and long from the address text
            List<Address> address = geocoder.getFromLocationName(place.description, 1);
            if (address != null && address.size() > 0) {
                // in case we found the lat and long we set the geofence call back
                GeofenceConnector geofenceConnector = new GeofenceConnector(getActivity(), address.get(0), mGeofenceConnectionCallback);
                // setting up the Geofence service
                geofenceConnector.connect();
            } else {
                Toast.makeText(this.getContext(), getString(R.string.cant_find_the_address), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG,"error to start Geofence", e);
        }
    }

    // for nice user experience
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager == null) {
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // in case we had issue we the Google play services we try to solve it
    private void resolutionGooglePlayProblem(ConnectionResult connectionResult){
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
    }
}
