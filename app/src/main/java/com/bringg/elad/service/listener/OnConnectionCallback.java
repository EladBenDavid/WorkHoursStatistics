package com.bringg.elad.service.listener;

import com.google.android.gms.common.ConnectionResult;

public interface OnConnectionCallback {
    void onSuccess();
    void onFailure(ConnectionResult connectionResult);
}
