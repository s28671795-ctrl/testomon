/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.listeners.OnvifMediaProfilesListener;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.requests.OnvifRequest;

public class GetMediaProfilesRequest
implements OnvifRequest {
    public static final String TAG = GetMediaProfilesRequest.class.getSimpleName();
    private final OnvifMediaProfilesListener listener;

    public GetMediaProfilesRequest(OnvifMediaProfilesListener listener) {
        this.listener = listener;
    }

    public OnvifMediaProfilesListener getListener() {
        return this.listener;
    }

    @Override
    public String getXml() {
        return "<GetProfiles xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_MEDIA_PROFILES;
    }
}

