/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.listeners.OnvifGetPtzStatusListener;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.requests.OnvifRequest;

public class GetPtzStatusRequest
implements OnvifRequest {
    private final OnvifGetPtzStatusListener listener;
    private final OnvifMediaProfile mediaProfile;

    public GetPtzStatusRequest(OnvifMediaProfile mediaProfile, OnvifGetPtzStatusListener listener) {
        this.mediaProfile = mediaProfile;
        this.listener = listener;
    }

    public OnvifGetPtzStatusListener getListener() {
        return this.listener;
    }

    @Override
    public String getXml() {
        return "<GetStatus xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\"/><ProfileToken>" + this.mediaProfile.getToken() + "</ProfileToken></GetStatus>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_PTZ_STATUS;
    }
}

