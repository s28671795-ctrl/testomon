/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.listeners.OnvifServicesListener;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.requests.OnvifRequest;

public class GetServicesRequest
implements OnvifRequest {
    public static final String TAG = GetServicesRequest.class.getSimpleName();
    private final OnvifServicesListener listener;

    public GetServicesRequest(OnvifServicesListener listener) {
        this.listener = listener;
    }

    public OnvifServicesListener getListener() {
        return this.listener;
    }

    @Override
    public String getXml() {
        return "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\"><IncludeCapability>false</IncludeCapability></GetServices>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_SERVICES;
    }
}

