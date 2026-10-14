/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.frames;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.StreamFrame;

public class DataFrame
extends StreamFrame {
    private final ByteBuffer data;
    private final boolean endStream;
    private final int padding;

    public DataFrame(ByteBuffer data, boolean endStream) {
        this(0, data, endStream);
    }

    public DataFrame(int streamId, ByteBuffer data, boolean endStream) {
        this(streamId, data, endStream, 0);
    }

    public DataFrame(int streamId, ByteBuffer data, boolean endStream, int padding) {
        super(FrameType.DATA, streamId);
        this.data = data;
        this.endStream = endStream;
        this.padding = padding;
    }

    public ByteBuffer getData() {
        return this.data;
    }

    public boolean isEndStream() {
        return this.endStream;
    }

    public int remaining() {
        return this.data.remaining();
    }

    public int padding() {
        return this.padding;
    }

    @Override
    public DataFrame withStreamId(int streamId) {
        return new DataFrame(streamId, this.getData(), this.isEndStream());
    }

    @Override
    public String toString() {
        return String.format("%s#%d{length:%d,end=%b}", super.toString(), this.getStreamId(), this.data.remaining(), this.endStream);
    }
}

