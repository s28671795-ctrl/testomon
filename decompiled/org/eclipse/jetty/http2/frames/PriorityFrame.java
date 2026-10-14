/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.StreamFrame;

public class PriorityFrame
extends StreamFrame {
    public static final int PRIORITY_LENGTH = 5;
    private final int parentStreamId;
    private final int weight;
    private final boolean exclusive;

    public PriorityFrame(int parentStreamId, int weight, boolean exclusive) {
        this(0, parentStreamId, weight, exclusive);
    }

    public PriorityFrame(int streamId, int parentStreamId, int weight, boolean exclusive) {
        super(FrameType.PRIORITY, streamId);
        this.parentStreamId = parentStreamId;
        this.weight = weight;
        this.exclusive = exclusive;
    }

    public int getParentStreamId() {
        return this.parentStreamId;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isExclusive() {
        return this.exclusive;
    }

    @Override
    public PriorityFrame withStreamId(int streamId) {
        return new PriorityFrame(streamId, this.getParentStreamId(), this.getWeight(), this.isExclusive());
    }

    @Override
    public String toString() {
        return String.format("%s#%d/#%d{weight=%d,exclusive=%b}", super.toString(), this.getStreamId(), this.parentStreamId, this.weight, this.exclusive);
    }
}

