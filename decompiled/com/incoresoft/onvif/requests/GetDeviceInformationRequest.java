/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.listeners.OnvifDeviceInformationListener;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.requests.OnvifRequest;

public class GetDeviceInformationRequest
implements OnvifRequest {
    public static final String TAG = GetDeviceInformationRequest.class.getSimpleName();
    private final OnvifDeviceInformationListener listener;

    public GetDeviceInformationRequest(OnvifDeviceInformationListener listener) {
        this.listener = listener;
    }

    public OnvifDeviceInformationListener getListener() {
        return this.listener;
    }

    @Override
    public String getXml() {
        return "<GetDeviceInformation xmlns=\"http://www.onvif.org/ver10/device/wsdl\"></GetDeviceInformation>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_DEVICE_INFORMATION;
    }
}

