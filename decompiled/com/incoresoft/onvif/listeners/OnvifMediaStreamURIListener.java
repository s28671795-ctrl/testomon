/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.listeners;

import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifMediaProfile;

public interface OnvifMediaStreamURIListener {
    public void onMediaStreamURIReceived(OnvifDevice var1, OnvifMediaProfile var2, String var3);
}

