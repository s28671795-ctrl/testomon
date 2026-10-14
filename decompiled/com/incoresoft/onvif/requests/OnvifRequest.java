/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif.requests;

import com.incoresoft.onvif.models.OnvifType;

public interface OnvifRequest {
    public String getXml();

    public OnvifType getType();
}

