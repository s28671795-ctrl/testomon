/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.FrameGrabResult;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

public interface Stream
extends ClientId {
    public int getId();

    public UUID getUUID();

    public String getName();

    public URI getUri();

    public String getImage();

    public Codec getCodec();

    public StreamStatus getStatus();

    public Instant getCreatedAt();

    public Coordinates getLocation();

    public Resolution getResolution();

    public StreamType getType();

    public String getAddress();

    public int getDirection();

    public StreamAuth getAuth();

    public StreamParams getParams();

    default public RoleUserRestrictions getRestrictions() {
        return null;
    }

    public int getFolderId();

    public void update(FrameGrabResult var1);

    public ZoneId getTimezone();

    public Duration getDuration();
}

