/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.http2.FlowControlStrategy;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.ManagedOperation;
import org.eclipse.jetty.util.component.Dumpable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public abstract class AbstractFlowControlStrategy
implements FlowControlStrategy,
Dumpable {
    protected static final Logger LOG = LoggerFactory.getLogger(FlowControlStrategy.class);
    private final AtomicLong sessionStall = new AtomicLong();
    private final AtomicLong sessionStallTime = new AtomicLong();
    private final Map<IStream, Long> streamsStalls = new ConcurrentHashMap<IStream, Long>();
    private final AtomicLong streamsStallTime = new AtomicLong();
    private int initialStreamSendWindow;
    private int initialStreamRecvWindow;

    public AbstractFlowControlStrategy(int initialStreamSendWindow) {
        this.initialStreamSendWindow = initialStreamSendWindow;
        this.initialStreamRecvWindow = 65535;
    }

    @ManagedAttribute(value="The initial size of stream's flow control send window", readonly=true)
    public int getInitialStreamSendWindow() {
        return this.initialStreamSendWindow;
    }

    @ManagedAttribute(value="The initial size of stream's flow control receive window", readonly=true)
    public int getInitialStreamRecvWindow() {
        return this.initialStreamRecvWindow;
    }

    @Override
    public void onStreamCreated(IStream stream) {
        stream.updateSendWindow(this.initialStreamSendWindow);
        stream.updateRecvWindow(this.initialStreamRecvWindow);
    }

    @Override
    public void onStreamDestroyed(IStream stream) {
        this.streamsStalls.remove(stream);
    }

    @Override
    public void updateInitialStreamWindow(ISession session, int initialStreamWindow, boolean local) {
        int previousInitialStreamWindow;
        if (local) {
            previousInitialStreamWindow = this.getInitialStreamRecvWindow();
            this.initialStreamRecvWindow = initialStreamWindow;
        } else {
            previousInitialStreamWindow = this.getInitialStreamSendWindow();
            this.initialStreamSendWindow = initialStreamWindow;
        }
        int delta = initialStreamWindow - previousInitialStreamWindow;
        if (delta == 0) {
            return;
        }
        for (Stream stream : session.getStreams()) {
            if (local) {
                ((IStream)stream).updateRecvWindow(delta);
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Updated initial stream recv window {} -> {} for {}", previousInitialStreamWindow, initialStreamWindow, stream);
                continue;
            }
            session.onWindowUpdate((IStream)stream, new WindowUpdateFrame(stream.getId(), delta));
        }
    }

    @Override
    public void onWindowUpdate(ISession session, IStream stream, WindowUpdateFrame frame) {
        int delta = frame.getWindowDelta();
        if (frame.getStreamId() > 0) {
            if (stream != null) {
                int oldSize = stream.updateSendWindow(delta);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Updated stream send window {} -> {} for {}", oldSize, oldSize + delta, stream);
                }
                if (oldSize <= 0) {
                    this.onStreamUnstalled(stream);
                }
            }
        } else {
            int oldSize = session.updateSendWindow(delta);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Updated session send window {} -> {} for {}", oldSize, oldSize + delta, session);
            }
            if (oldSize <= 0) {
                this.onSessionUnstalled(session);
            }
        }
    }

    @Override
    public void onDataReceived(ISession session, IStream stream, int length) {
        int oldSize = session.updateRecvWindow(-length);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Data received, {} bytes, updated session recv window {} -> {} for {}", length, oldSize, oldSize - length, session);
        }
        if (stream != null) {
            oldSize = stream.updateRecvWindow(-length);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Data received, {} bytes, updated stream recv window {} -> {} for {}", length, oldSize, oldSize - length, stream);
            }
        }
    }

    @Override
    public void windowUpdate(ISession session, IStream stream, WindowUpdateFrame frame) {
    }

    @Override
    public void onDataSending(IStream stream, int length) {
        if (length == 0) {
            return;
        }
        ISession session = stream.getSession();
        int oldSessionWindow = session.updateSendWindow(-length);
        int newSessionWindow = oldSessionWindow - length;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending, session send window {} -> {} for {}", oldSessionWindow, newSessionWindow, session);
        }
        if (newSessionWindow <= 0) {
            this.onSessionStalled(session);
        }
        int oldStreamWindow = stream.updateSendWindow(-length);
        int newStreamWindow = oldStreamWindow - length;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sending, stream send window {} -> {} for {}", oldStreamWindow, newStreamWindow, stream);
        }
        if (newStreamWindow <= 0) {
            this.onStreamStalled(stream);
        }
    }

    @Override
    public void onDataSent(IStream stream, int length) {
    }

    protected void onSessionStalled(ISession session) {
        this.sessionStall.set(NanoTime.now());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Session stalled {}", (Object)session);
        }
    }

    protected void onStreamStalled(IStream stream) {
        this.streamsStalls.put(stream, NanoTime.now());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Stream stalled {}", (Object)stream);
        }
    }

    protected void onSessionUnstalled(ISession session) {
        long stallTime = NanoTime.since(this.sessionStall.getAndSet(0L));
        this.sessionStallTime.addAndGet(stallTime);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Session unstalled after {} ms {}", (Object)TimeUnit.NANOSECONDS.toMillis(stallTime), (Object)session);
        }
    }

    protected void onStreamUnstalled(IStream stream) {
        Long time = this.streamsStalls.remove(stream);
        if (time != null) {
            long stallTime = NanoTime.since(time);
            this.streamsStallTime.addAndGet(stallTime);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream unstalled after {} ms {}", (Object)TimeUnit.NANOSECONDS.toMillis(stallTime), (Object)stream);
            }
        }
    }

    @ManagedAttribute(value="The time, in milliseconds, that the session flow control has stalled", readonly=true)
    public long getSessionStallTime() {
        long pastStallTime = this.sessionStallTime.get();
        long currentStallTime = this.sessionStall.get();
        if (currentStallTime != 0L) {
            currentStallTime = NanoTime.since(currentStallTime);
        }
        return TimeUnit.NANOSECONDS.toMillis(pastStallTime + currentStallTime);
    }

    @ManagedAttribute(value="The time, in milliseconds, that the streams flow control has stalled", readonly=true)
    public long getStreamsStallTime() {
        long pastStallTime = this.streamsStallTime.get();
        long now = NanoTime.now();
        long currentStallTime = this.streamsStalls.values().stream().reduce(0L, (result, time) -> NanoTime.elapsed(time, now));
        return TimeUnit.NANOSECONDS.toMillis(pastStallTime + currentStallTime);
    }

    @ManagedOperation(value="Resets the statistics", impact="ACTION")
    public void reset() {
        this.sessionStallTime.set(0L);
        this.streamsStallTime.set(0L);
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        out.append(this.toString()).append(System.lineSeparator());
    }
}

