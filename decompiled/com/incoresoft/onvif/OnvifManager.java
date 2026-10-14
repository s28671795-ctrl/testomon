/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.incoresoft.onvif.OnvifExecutor;
import com.incoresoft.onvif.listeners.OnvifDeviceInformationListener;
import com.incoresoft.onvif.listeners.OnvifGetPtzStatusListener;
import com.incoresoft.onvif.listeners.OnvifMediaProfilesListener;
import com.incoresoft.onvif.listeners.OnvifMediaStreamURIListener;
import com.incoresoft.onvif.listeners.OnvifResponseListener;
import com.incoresoft.onvif.listeners.OnvifServicesListener;
import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.requests.GetDeviceInformationRequest;
import com.incoresoft.onvif.requests.GetMediaProfilesRequest;
import com.incoresoft.onvif.requests.GetMediaStreamRequest;
import com.incoresoft.onvif.requests.GetPtzStatusRequest;
import com.incoresoft.onvif.requests.GetServicesRequest;
import com.incoresoft.onvif.requests.OnvifRequest;
import com.incoresoft.onvif.responses.OnvifResponse;

public class OnvifManager
implements OnvifResponseListener {
    public static final String TAG = OnvifManager.class.getSimpleName();
    private final OnvifExecutor executor;
    private OnvifResponseListener onvifResponseListener;

    public OnvifManager() {
        this(null);
    }

    private OnvifManager(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        this.executor = new OnvifExecutor(this);
    }

    public void getServicesAsync(OnvifDevice device, OnvifServicesListener listener) {
        GetServicesRequest request = new GetServicesRequest(listener);
        this.executor.sendRequestAsync(device, request);
    }

    public void getDeviceInformationAsync(OnvifDevice device, OnvifDeviceInformationListener listener) {
        GetDeviceInformationRequest request = new GetDeviceInformationRequest(listener);
        this.executor.sendRequestAsync(device, request);
    }

    public void getMediaProfilesAsync(OnvifDevice device, OnvifMediaProfilesListener listener) {
        GetMediaProfilesRequest request = new GetMediaProfilesRequest(listener);
        this.executor.sendRequestAsync(device, request);
    }

    public void getMediaStreamURIAsync(OnvifDevice device, OnvifMediaProfile profile, OnvifMediaStreamURIListener listener) {
        GetMediaStreamRequest request = new GetMediaStreamRequest(profile, listener);
        this.executor.sendRequestAsync(device, request);
    }

    public OnvifResponse getServices(OnvifDevice device, OnvifServicesListener listener) {
        GetServicesRequest request = new GetServicesRequest(listener);
        return this.executor.sendRequest(device, request);
    }

    public OnvifResponse getDeviceInformation(OnvifDevice device, OnvifDeviceInformationListener listener) {
        GetDeviceInformationRequest request = new GetDeviceInformationRequest(listener);
        return this.executor.sendRequest(device, request);
    }

    public OnvifResponse getMediaProfiles(OnvifDevice device) {
        GetMediaProfilesRequest request = new GetMediaProfilesRequest(null);
        return this.executor.sendRequest(device, request);
    }

    public OnvifResponse getMediaStreamURI(OnvifDevice device, OnvifMediaProfile profile, OnvifMediaStreamURIListener listener) {
        GetMediaStreamRequest request = new GetMediaStreamRequest(profile, listener);
        return this.executor.sendRequest(device, request);
    }

    public OnvifResponse getPtzStatus(OnvifDevice device, OnvifMediaProfile profile, OnvifGetPtzStatusListener listener) {
        GetPtzStatusRequest request = new GetPtzStatusRequest(profile, listener);
        return this.executor.sendRequest(device, request);
    }

    public OnvifResponse sendOnvifRequest(OnvifDevice device, OnvifRequest request) {
        return this.executor.sendRequest(device, request);
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    public void destroy() {
        this.onvifResponseListener = null;
        this.executor.clear();
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {
        if (this.onvifResponseListener != null) {
            this.onvifResponseListener.onResponse(onvifDevice, response);
        }
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        if (this.onvifResponseListener != null) {
            this.onvifResponseListener.onError(onvifDevice, errorCode, errorMessage);
        }
    }
}

