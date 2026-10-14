/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.Resolution;
import java.time.Duration;

public interface FrameGrabResult {
    public Resolution getResolution();

    public Codec getCodec();

    public String getImage();

    public String getThumbnail();

    public Duration getDuration();
}

