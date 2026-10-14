/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.PtzStatus;

public interface OnvifGetPtzStatusListener {
    public void onGetPtz(OnvifDevice var1, PtzStatus var2);
}

