/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.Device;
import java.util.List;

public interface DiscoveryCallback {
    public void onDiscoveryStarted();

    public void onDevicesFound(List<Device> var1);

    public void onDiscoveryFinished();
}

