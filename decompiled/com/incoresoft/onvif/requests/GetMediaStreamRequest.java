/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.listeners.OnvifMediaStreamURIListener;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.models.OnvifType;
import com.incoresoft.onvif.requests.OnvifRequest;

public class GetMediaStreamRequest
implements OnvifRequest {
    public static final String TAG = GetMediaStreamRequest.class.getSimpleName();
    private final OnvifMediaProfile mediaProfile;
    private final OnvifMediaStreamURIListener listener;

    public GetMediaStreamRequest(OnvifMediaProfile mediaProfile, OnvifMediaStreamURIListener listener) {
        this.mediaProfile = mediaProfile;
        this.listener = listener;
    }

    public OnvifMediaProfile getMediaProfile() {
        return this.mediaProfile;
    }

    public OnvifMediaStreamURIListener getListener() {
        return this.listener;
    }

    @Override
    public String getXml() {
        return "<GetStreamUri xmlns=\"http://www.onvif.org/ver10/media/wsdl\"><StreamSetup><Stream xmlns=\"http://www.onvif.org/ver10/schema\">RTP-Unicast</Stream><Transport xmlns=\"http://www.onvif.org/ver10/schema\"><Protocol>RTSP</Protocol></Transport></StreamSetup><ProfileToken>" + this.mediaProfile.getToken() + "</ProfileToken></GetStreamUri>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STREAM_URI;
    }
}

