/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.jetty.http2.CloseState;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.StreamFrame;
import org.eclipse.jetty.util.Attachable;
import org.eclipse.jetty.util.Callback;

public interface IStream
extends Stream,
Attachable,
Closeable {
    public boolean isLocal();

    @Override
    public ISession getSession();

    public Stream.Listener getListener();

    public void setListener(Stream.Listener var1);

    public void send(FrameList var1, Callback var2);

    public void process(Frame var1, Callback var2);

    public boolean updateClose(boolean var1, CloseState.Event var2);

    @Override
    public void close();

    public int updateSendWindow(int var1);

    public int updateRecvWindow(int var1);

    public void notIdle();

    public boolean isRemotelyClosed();

    public boolean failAllData(Throwable var1);

    public boolean isResetOrFailed();

    public void commit();

    public boolean isCommitted();

    public int dataSize();

    public static class FrameList {
        private final List<StreamFrame> frames;

        public FrameList(HeadersFrame headers) {
            Objects.requireNonNull(headers);
            this.frames = List.of(headers);
        }

        public FrameList(HeadersFrame headers, DataFrame data, HeadersFrame trailers) {
            Objects.requireNonNull(headers);
            ArrayList<StreamFrame> frames = new ArrayList<StreamFrame>(3);
            int streamId = headers.getStreamId();
            if (data != null && data.getStreamId() != streamId) {
                throw new IllegalArgumentException("Invalid stream ID for DATA frame " + String.valueOf(data));
            }
            if (trailers != null && trailers.getStreamId() != streamId) {
                throw new IllegalArgumentException("Invalid stream ID for HEADERS frame " + String.valueOf(trailers));
            }
            frames.add(headers);
            if (data != null) {
                frames.add(data);
            }
            if (trailers != null) {
                frames.add(trailers);
            }
            this.frames = Collections.unmodifiableList(frames);
        }

        public int getStreamId() {
            return this.frames.get(0).getStreamId();
        }

        public List<StreamFrame> getFrames() {
            return this.frames;
        }
    }
}

