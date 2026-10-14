/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.StreamFrame;

public class PushPromiseFrame
extends StreamFrame {
    private final int promisedStreamId;
    private final MetaData.Request metaData;

    public PushPromiseFrame(int streamId, MetaData.Request metaData) {
        this(streamId, 0, metaData);
    }

    public PushPromiseFrame(int streamId, int promisedStreamId, MetaData.Request metaData) {
        super(FrameType.PUSH_PROMISE, streamId);
        this.promisedStreamId = promisedStreamId;
        this.metaData = metaData;
    }

    public int getPromisedStreamId() {
        return this.promisedStreamId;
    }

    public MetaData.Request getMetaData() {
        return this.metaData;
    }

    @Override
    public PushPromiseFrame withStreamId(int streamId) {
        return new PushPromiseFrame(this.getStreamId(), streamId, this.getMetaData());
    }

    @Override
    public String toString() {
        return String.format("%s#%d/#%d", super.toString(), this.getStreamId(), this.promisedStreamId);
    }
}

