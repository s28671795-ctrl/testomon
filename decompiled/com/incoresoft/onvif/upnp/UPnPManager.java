/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.upnp;

import com.incoresoft.onvif.models.UPnPDevice;
import com.incoresoft.onvif.upnp.UPnPDeviceInformationListener;
import com.incoresoft.onvif.upnp.UPnPExecutor;
import com.incoresoft.onvif.upnp.UPnPResponseListener;

public class UPnPManager
implements UPnPResponseListener {
    public static final String TAG = UPnPManager.class.getSimpleName();
    private final UPnPExecutor executor;
    private UPnPResponseListener responseListener;

    public UPnPManager() {
        this(null);
    }

    private UPnPManager(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
        this.executor = new UPnPExecutor(this);
    }

    public void getDeviceInformation(UPnPDevice device, UPnPDeviceInformationListener listener) {
        this.executor.getDeviceInformation(device, listener);
    }

    public void setResponseListener(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onResponse(UPnPDevice onvifDevice) {
    }

    @Override
    public void onError(UPnPDevice onvifDevice, int errorCode, String errorMessage) {
    }
}

