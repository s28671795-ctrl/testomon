/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.responses.OnvifResponse;

public interface OnvifResponseListener {
    public void onResponse(OnvifDevice var1, OnvifResponse var2);

    public void onError(OnvifDevice var1, int var2, String var3);
}

