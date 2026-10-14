/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.upnp;

import com.incoresoft.onvif.models.UPnPDevice;

public interface UPnPResponseListener {
    public void onResponse(UPnPDevice var1);

    public void onError(UPnPDevice var1, int var2, String var3);
}

