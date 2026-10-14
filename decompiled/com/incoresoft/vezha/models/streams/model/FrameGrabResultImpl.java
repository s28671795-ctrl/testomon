/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.model;

import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.FrameGrabResult;
import com.incoresoft.middleware.model.stream.Resolution;
import java.time.Duration;

public class FrameGrabResultImpl
implements FrameGrabResult {
    private final Resolution resolution;
    private final Codec codec;
    private final String image;
    private final String thumbnail;
    private final Duration duration;

    public static FrameGrabResultImpl getDefault() {
        return new FrameGrabResultImpl(new Resolution(1920, 1080), Codec.h264, "", "", Duration.ofMillis(0L));
    }

    @Override
    public Resolution getResolution() {
        return this.resolution;
    }

    @Override
    public Codec getCodec() {
        return this.codec;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public String getThumbnail() {
        return this.thumbnail;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    public FrameGrabResultImpl(Resolution resolution, Codec codec, String image, String thumbnail, Duration duration) {
        this.resolution = resolution;
        this.codec = codec;
        this.image = image;
        this.thumbnail = thumbnail;
        this.duration = duration;
    }
}

