/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifServices;

public interface OnvifServicesListener {
    public void onServicesReceived(OnvifDevice var1, OnvifServices var2);
}

