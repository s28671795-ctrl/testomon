/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.upnp;

import com.incoresoft.onvif.models.UPnPDevice;
import com.incoresoft.onvif.upnp.UPnPDeviceInformation;

public interface UPnPDeviceInformationListener {
    public void onDeviceInformationReceived(UPnPDevice var1, UPnPDeviceInformation var2);

    public void onError(UPnPDevice var1, int var2, String var3);
}

