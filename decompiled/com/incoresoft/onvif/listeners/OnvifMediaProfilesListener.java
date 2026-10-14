/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import java.util.List;

public interface OnvifMediaProfilesListener {
    public void onMediaProfilesReceived(OnvifDevice var1, List<OnvifMediaProfile> var2);
}

