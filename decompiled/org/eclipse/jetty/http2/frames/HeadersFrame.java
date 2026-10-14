/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.frames.StreamFrame;

public class HeadersFrame
extends StreamFrame {
    private final MetaData metaData;
    private final PriorityFrame priority;
    private final boolean endStream;

    public HeadersFrame(MetaData metaData, PriorityFrame priority, boolean endStream) {
        this(0, metaData, priority, endStream);
    }

    public HeadersFrame(int streamId, MetaData metaData, PriorityFrame priority, boolean endStream) {
        super(FrameType.HEADERS, streamId);
        this.metaData = metaData;
        this.priority = priority;
        this.endStream = endStream;
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public PriorityFrame getPriority() {
        return this.priority;
    }

    public boolean isEndStream() {
        return this.endStream;
    }

    @Override
    public HeadersFrame withStreamId(int streamId) {
        PriorityFrame priority = this.getPriority();
        priority = priority == null ? null : priority.withStreamId(streamId);
        return new HeadersFrame(streamId, this.getMetaData(), priority, this.isEndStream());
    }

    @Override
    public String toString() {
        return String.format("%s#%d[end=%b,{%s},priority=%s]", super.toString(), this.getStreamId(), this.isEndStream(), this.getMetaData(), this.getPriority());
    }
}

