/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.location;

import com.google.cloud.location.Location;
import com.google.cloud.location.LocationOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ListLocationsResponseOrBuilder
extends MessageOrBuilder {
    public List<Location> getLocationsList();

    public Location getLocations(int var1);

    public int getLocationsCount();

    public List<? extends LocationOrBuilder> getLocationsOrBuilderList();

    public LocationOrBuilder getLocationsOrBuilder(int var1);

    public String getNextPageToken();

    public ByteString getNextPageTokenBytes();
}

