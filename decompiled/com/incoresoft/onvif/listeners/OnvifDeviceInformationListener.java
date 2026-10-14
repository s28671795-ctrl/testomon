/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifDeviceInformation;

public interface OnvifDeviceInformationListener {
    public void onDeviceInformationReceived(OnvifDevice var1, OnvifDeviceInformation var2);
}

