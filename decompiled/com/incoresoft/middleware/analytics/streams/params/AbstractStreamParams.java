/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import java.util.Objects;

public abstract class AbstractStreamParams
implements StreamParams {
    private final StreamType type;

    protected AbstractStreamParams(StreamType type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public StreamType getType() {
        return this.type;
    }
}

