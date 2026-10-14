/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ResourceLocationOrBuilder
extends MessageOrBuilder {
    public List<String> getCurrentLocationsList();

    public int getCurrentLocationsCount();

    public String getCurrentLocations(int var1);

    public ByteString getCurrentLocationsBytes(int var1);

    public List<String> getOriginalLocationsList();

    public int getOriginalLocationsCount();

    public String getOriginalLocations(int var1);

    public ByteString getOriginalLocationsBytes(int var1);
}

