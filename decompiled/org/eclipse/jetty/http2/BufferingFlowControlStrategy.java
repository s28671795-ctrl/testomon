/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.http2.AbstractFlowControlStrategy;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.util.Atomics;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;

@ManagedObject
public class BufferingFlowControlStrategy
extends AbstractFlowControlStrategy {
    private final AtomicInteger maxSessionRecvWindow = new AtomicInteger(65535);
    private final AtomicInteger sessionLevel = new AtomicInteger();
    private final Map<IStream, AtomicInteger> streamLevels = new ConcurrentHashMap<IStream, AtomicInteger>();
    private float bufferRatio;

    public BufferingFlowControlStrategy(float bufferRatio) {
        this(65535, bufferRatio);
    }

    public BufferingFlowControlStrategy(int initialStreamSendWindow, float bufferRatio) {
        super(initialStreamSendWindow);
        this.bufferRatio = bufferRatio;
    }

    @ManagedAttribute(value="The ratio between the receive buffer and the consume buffer")
    public float getBufferRatio() {
        return this.bufferRatio;
    }

    public void setBufferRatio(float bufferRatio) {
        this.bufferRatio = bufferRatio;
    }

    @Override
    public void onStreamCreated(IStream stream) {
        super.onStreamCreated(stream);
        this.streamLevels.put(stream, new AtomicInteger());
    }

    @Override
    public void onStreamDestroyed(IStream stream) {
        this.streamLevels.remove(stream);
        super.onStreamDestroyed(stream);
    }

    @Override
    public void onDataConsumed(ISession session, IStream stream, int length) {
        int maxLevel;
        if (length <= 0) {
            return;
        }
        float ratio = this.bufferRatio;
        int level = this.sessionLevel.addAndGet(length);
        if (level > (maxLevel = (int)((float)this.maxSessionRecvWindow.get() * ratio))) {
            if (this.sessionLevel.compareAndSet(level, 0)) {
                session.updateRecvWindow(level);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Data consumed, {} bytes, updated session recv window by {}/{} for {}", length, level, maxLevel, session);
                }
                this.sendWindowUpdate(null, session, new WindowUpdateFrame(0, level));
            } else if (LOG.isDebugEnabled()) {
                LOG.debug("Data consumed, {} bytes, concurrent session recv window level {}/{} for {}", length, this.sessionLevel, maxLevel, session);
            }
        } else if (LOG.isDebugEnabled()) {
            LOG.debug("Data consumed, {} bytes, session recv window level {}/{} for {}", length, level, maxLevel, session);
        }
        if (stream != null) {
            if (stream.isRemotelyClosed()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Data consumed, {} bytes, ignoring update stream recv window for remotely closed {}", (Object)length, (Object)stream);
                }
            } else {
                AtomicInteger streamLevel = this.streamLevels.get(stream);
                if (streamLevel != null) {
                    level = streamLevel.addAndGet(length);
                    if (level > (maxLevel = (int)((float)this.getInitialStreamRecvWindow() * ratio))) {
                        level = streamLevel.getAndSet(0);
                        stream.updateRecvWindow(level);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Data consumed, {} bytes, updated stream recv window by {}/{} for {}", length, level, maxLevel, stream);
                        }
                        this.sendWindowUpdate(stream, session, new WindowUpdateFrame(stream.getId(), level));
                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug("Data consumed, {} bytes, stream recv window level {}/{} for {}", length, level, maxLevel, stream);
                    }
                }
            }
        }
    }

    protected void sendWindowUpdate(IStream stream, ISession session, WindowUpdateFrame frame) {
        session.frames(stream, List.of(frame), Callback.NOOP);
    }

    @Override
    public void windowUpdate(ISession session, IStream stream, WindowUpdateFrame frame) {
        super.windowUpdate(session, stream, frame);
        if (frame.getStreamId() == 0) {
            int sessionWindow = session.updateRecvWindow(0);
            Atomics.updateMax(this.maxSessionRecvWindow, sessionWindow);
        }
    }

    public String toString() {
        return String.format("%s@%x[ratio=%.2f,sessionLevel=%s,sessionStallTime=%dms,streamsStallTime=%dms]", this.getClass().getSimpleName(), this.hashCode(), Float.valueOf(this.bufferRatio), this.sessionLevel, this.getSessionStallTime(), this.getStreamsStallTime());
    }
}

