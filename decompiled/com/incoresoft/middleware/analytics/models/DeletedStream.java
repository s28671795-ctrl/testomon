/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models;

import com.incoresoft.middleware.analytics.streams.authorization.NoAuth;
import com.incoresoft.middleware.analytics.streams.params.EmptyParams;
import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.FrameGrabResult;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

public class DeletedStream
implements Stream {
    private final int id;
    private final UUID uuid = UUID.randomUUID();

    public DeletedStream(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public UUID getUUID() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public String getName() {
        return "Deleted";
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public Codec getCodec() {
        return null;
    }

    @Override
    public StreamStatus getStatus() {
        return StreamStatus.deleted;
    }

    @Override
    public Instant getCreatedAt() {
        return null;
    }

    @Override
    public Coordinates getLocation() {
        return null;
    }

    @Override
    public Resolution getResolution() {
        return null;
    }

    @Override
    public StreamType getType() {
        return this.getParams().getType();
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public int getDirection() {
        return 0;
    }

    @Override
    public StreamAuth getAuth() {
        return new NoAuth();
    }

    @Override
    public int getClientId() {
        return 0;
    }

    @Override
    public StreamParams getParams() {
        return new EmptyParams(StreamType.rtsp);
    }

    @Override
    public int getFolderId() {
        return 0;
    }

    @Override
    public void update(FrameGrabResult frameGrabResult) {
    }

    @Override
    public ZoneId getTimezone() {
        return null;
    }

    @Override
    public Duration getDuration() {
        return null;
    }
}

