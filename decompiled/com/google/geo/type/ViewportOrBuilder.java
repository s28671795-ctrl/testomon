/*
 * Decompiled with CFR 0.152.
 */
package com.google.geo.type;

import com.google.protobuf.MessageOrBuilder;
import com.google.type.LatLng;
import com.google.type.LatLngOrBuilder;

public interface ViewportOrBuilder
extends MessageOrBuilder {
    public boolean hasLow();

    public LatLng getLow();

    public LatLngOrBuilder getLowOrBuilder();

    public boolean hasHigh();

    public LatLng getHigh();

    public LatLngOrBuilder getHighOrBuilder();
}

