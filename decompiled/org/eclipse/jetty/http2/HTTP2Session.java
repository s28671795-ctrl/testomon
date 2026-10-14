/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.CloseState;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.FlowControlStrategy;
import org.eclipse.jetty.http2.HTTP2Flusher;
import org.eclipse.jetty.http2.HTTP2Stream;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.FailureFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.GoAwayFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PingFrame;
import org.eclipse.jetty.http2.frames.PrefaceFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.SettingsFrame;
import org.eclipse.jetty.http2.frames.StreamFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.generator.Generator;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.CyclicTimeouts;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.WriteFlusher;
import org.eclipse.jetty.util.AtomicBiInteger;
import org.eclipse.jetty.util.Atomics;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.CountingCallback;
import org.eclipse.jetty.util.MathUtils;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.Promise;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public abstract class HTTP2Session
extends ContainerLifeCycle
implements ISession,
Parser.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2Session.class);
    private final ConcurrentMap<Integer, IStream> streams = new ConcurrentHashMap<Integer, IStream>();
    private final AtomicLong streamsOpened = new AtomicLong();
    private final AtomicLong streamsClosed = new AtomicLong();
    private final StreamsState streamsState = new StreamsState();
    private final AtomicInteger localStreamIds = new AtomicInteger();
    private final AtomicInteger lastRemoteStreamId = new AtomicInteger();
    private final AtomicInteger localStreamCount = new AtomicInteger();
    private final AtomicBiInteger remoteStreamCount = new AtomicBiInteger();
    private final AtomicInteger sendWindow = new AtomicInteger();
    private final AtomicInteger recvWindow = new AtomicInteger();
    private final AtomicLong bytesWritten = new AtomicLong();
    private final EndPoint endPoint;
    private final Generator generator;
    private final Session.Listener listener;
    private final FlowControlStrategy flowControl;
    private final HTTP2Flusher flusher;
    private final StreamTimeouts streamTimeouts;
    private int maxLocalStreams;
    private int maxRemoteStreams;
    private long streamIdleTimeout;
    private int initialSessionRecvWindow;
    private int writeThreshold;
    private boolean pushEnabled;
    private boolean connectProtocolEnabled;

    public HTTP2Session(Scheduler scheduler, EndPoint endPoint, Generator generator, Session.Listener listener, FlowControlStrategy flowControl, int initialStreamId) {
        this.endPoint = endPoint;
        this.generator = generator;
        this.listener = listener;
        this.flowControl = flowControl;
        this.flusher = new HTTP2Flusher(this);
        this.streamTimeouts = new StreamTimeouts(scheduler);
        this.maxLocalStreams = -1;
        this.maxRemoteStreams = -1;
        this.localStreamIds.set(initialStreamId);
        this.streamIdleTimeout = endPoint.getIdleTimeout();
        this.sendWindow.set(65535);
        this.recvWindow.set(65535);
        this.writeThreshold = 32768;
        this.pushEnabled = true;
        this.addBean(flowControl);
        this.addBean(this.flusher);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        this.streamsState.halt("stop");
    }

    @ManagedAttribute(value="The flow control strategy", readonly=true)
    public FlowControlStrategy getFlowControlStrategy() {
        return this.flowControl;
    }

    @ManagedAttribute(value="The total number of streams opened", readonly=true)
    public long getStreamsOpened() {
        return this.streamsOpened.get();
    }

    @ManagedAttribute(value="The total number of streams closed", readonly=true)
    public long getStreamsClosed() {
        return this.streamsClosed.get();
    }

    @ManagedAttribute(value="The maximum number of concurrent local streams")
    public int getMaxLocalStreams() {
        return this.maxLocalStreams;
    }

    public void setMaxLocalStreams(int maxLocalStreams) {
        this.maxLocalStreams = maxLocalStreams;
    }

    @ManagedAttribute(value="The maximum number of concurrent remote streams")
    public int getMaxRemoteStreams() {
        return this.maxRemoteStreams;
    }

    public void setMaxRemoteStreams(int maxRemoteStreams) {
        this.maxRemoteStreams = maxRemoteStreams;
    }

    @ManagedAttribute(value="The stream's idle timeout")
    public long getStreamIdleTimeout() {
        return this.streamIdleTimeout;
    }

    public void setStreamIdleTimeout(long streamIdleTimeout) {
        this.streamIdleTimeout = streamIdleTimeout;
    }

    @ManagedAttribute(value="The initial size of session's flow control receive window")
    public int getInitialSessionRecvWindow() {
        return this.initialSessionRecvWindow;
    }

    public void setInitialSessionRecvWindow(int initialSessionRecvWindow) {
        this.initialSessionRecvWindow = initialSessionRecvWindow;
    }

    @ManagedAttribute(value="The number of bytes that trigger a TCP write")
    public int getWriteThreshold() {
        return this.writeThreshold;
    }

    public void setWriteThreshold(int writeThreshold) {
        this.writeThreshold = writeThreshold;
    }

    public EndPoint getEndPoint() {
        return this.endPoint;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    @Override
    public long getBytesWritten() {
        return this.bytesWritten.get();
    }

    @Override
    public void onData(DataFrame frame) {
        this.onData(frame, Callback.NOOP);
    }

    @Override
    public void onData(DataFrame frame, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        int streamId = frame.getStreamId();
        IStream stream = this.getStream(streamId);
        int flowControlLength = frame.remaining() + frame.padding();
        this.flowControl.onDataReceived(this, stream, flowControlLength);
        if (stream != null) {
            if (this.getRecvWindow() < 0) {
                this.onSessionFailure(ErrorCode.FLOW_CONTROL_ERROR.code, "session_window_exceeded", callback);
            } else if (stream.updateRecvWindow(0) < 0) {
                this.onSessionFailure(ErrorCode.FLOW_CONTROL_ERROR.code, "stream_window_exceeded", callback);
            } else {
                stream.process(frame, new DataCallback(callback, stream, flowControlLength));
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream #{} not found on {}", (Object)streamId, (Object)this);
            }
            this.flowControl.onDataConsumed(this, null, flowControlLength);
            if (this.isStreamClosed(streamId)) {
                this.reset(null, new ResetFrame(streamId, ErrorCode.STREAM_CLOSED_ERROR.code), callback);
            } else {
                this.onSessionFailure(ErrorCode.PROTOCOL_ERROR.code, "unexpected_data_frame", callback);
            }
        }
    }

    private boolean isStreamClosed(int streamId) {
        return this.isLocalStream(streamId) ? this.isLocalStreamClosed(streamId) : this.isRemoteStreamClosed(streamId);
    }

    private boolean isLocalStream(int streamId) {
        return (streamId & 1) == (this.localStreamIds.get() & 1);
    }

    protected boolean isLocalStreamClosed(int streamId) {
        return streamId <= this.localStreamIds.get();
    }

    protected boolean isRemoteStreamClosed(int streamId) {
        return streamId <= this.getLastRemoteStreamId();
    }

    @Override
    public abstract void onHeaders(HeadersFrame var1);

    @Override
    public void onPriority(PriorityFrame frame) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
    }

    @Override
    public void onReset(ResetFrame frame) {
        int streamId;
        IStream stream;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        if ((stream = this.getStream(streamId = frame.getStreamId())) != null) {
            stream.process(frame, new OnResetCallback());
        } else {
            this.onResetForUnknownStream(frame);
        }
    }

    protected void onResetForUnknownStream(ResetFrame frame) {
        if (this.isStreamClosed(frame.getStreamId())) {
            this.notifyReset(this, frame);
        } else {
            this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "unexpected_rst_stream_frame");
        }
    }

    @Override
    public void onSettings(SettingsFrame frame) {
        this.onSettings(frame, true);
    }

    public void onSettings(SettingsFrame frame, boolean reply) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        if (frame.isReply()) {
            return;
        }
        block9: for (Map.Entry<Integer, Integer> entry : frame.getSettings().entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            switch (key) {
                case 1: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Updating HPACK header table size to {} for {}", (Object)value, (Object)this);
                    }
                    this.generator.setHeaderTableSize(value);
                    continue block9;
                }
                case 2: {
                    boolean enabled;
                    boolean bl = enabled = value == 1;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} push for {}", (Object)(enabled ? "Enabling" : "Disabling"), (Object)this);
                    }
                    this.pushEnabled = enabled;
                    continue block9;
                }
                case 3: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Updating max local concurrent streams to {} for {}", (Object)value, (Object)this);
                    }
                    this.maxLocalStreams = value;
                    continue block9;
                }
                case 4: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Updating initial stream window size to {} for {}", (Object)value, (Object)this);
                    }
                    this.flowControl.updateInitialStreamWindow(this, value, false);
                    continue block9;
                }
                case 5: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Updating max frame size to {} for {}", (Object)value, (Object)this);
                    }
                    this.generator.setMaxFrameSize(value);
                    continue block9;
                }
                case 6: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Updating max header list size to {} for {}", (Object)value, (Object)this);
                    }
                    this.generator.setMaxHeaderListSize(value);
                    continue block9;
                }
                case 8: {
                    boolean enabled;
                    boolean bl = enabled = value == 1;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("{} CONNECT protocol for {}", (Object)(enabled ? "Enabling" : "Disabling"), (Object)this);
                    }
                    this.connectProtocolEnabled = enabled;
                    continue block9;
                }
            }
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug("Unknown setting {}:{} for {}", key, value, this);
        }
        this.notifySettings(this, frame);
        if (reply) {
            SettingsFrame replyFrame = new SettingsFrame(Collections.emptyMap(), true);
            this.settings(replyFrame, Callback.NOOP);
        }
    }

    @Override
    public void onPing(PingFrame frame) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        if (frame.isReply()) {
            this.notifyPing(this, frame);
        } else {
            PingFrame reply = new PingFrame(frame.getPayload(), true);
            this.control(null, Callback.NOOP, reply);
        }
    }

    @Override
    public void onGoAway(GoAwayFrame frame) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        this.streamsState.onGoAway(frame);
    }

    @Override
    public void onWindowUpdate(WindowUpdateFrame frame) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received {} on {}", (Object)frame, (Object)this);
        }
        int streamId = frame.getStreamId();
        int windowDelta = frame.getWindowDelta();
        if (streamId > 0) {
            IStream stream = this.getStream(streamId);
            if (stream != null) {
                int streamSendWindow = stream.updateSendWindow(0);
                if (MathUtils.sumOverflows(streamSendWindow, windowDelta)) {
                    this.reset(stream, new ResetFrame(streamId, ErrorCode.FLOW_CONTROL_ERROR.code), Callback.NOOP);
                } else {
                    stream.process(frame, Callback.NOOP);
                    this.onWindowUpdate(stream, frame);
                }
            } else if (!this.isStreamClosed(streamId)) {
                this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "unexpected_window_update_frame");
            }
        } else {
            int sessionSendWindow = this.updateSendWindow(0);
            if (MathUtils.sumOverflows(sessionSendWindow, windowDelta)) {
                this.onConnectionFailure(ErrorCode.FLOW_CONTROL_ERROR.code, "invalid_flow_control_window");
            } else {
                this.onWindowUpdate(null, frame);
            }
        }
    }

    @Override
    public void onWindowUpdate(IStream stream, WindowUpdateFrame frame) {
        this.flusher.window(stream, frame);
    }

    @Override
    public void onStreamFailure(int streamId, int error, String reason) {
        IStream stream;
        Callback callback = Callback.from(() -> this.reset(this.getStream(streamId), new ResetFrame(streamId, error), Callback.NOOP));
        Throwable failure = this.toFailure(error, reason);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Stream #{} failure {}", streamId, this, failure);
        }
        if ((stream = this.getStream(streamId)) != null) {
            this.failStream(stream, error, reason, failure, callback);
        } else {
            callback.succeeded();
        }
    }

    @Override
    public void onConnectionFailure(int error, String reason) {
        this.onSessionFailure(error, reason, Callback.NOOP);
    }

    private void onSessionFailure(int error, String reason, Callback callback) {
        this.streamsState.onSessionFailure(error, reason, callback);
    }

    void onWriteFailure(Throwable failure) {
        this.streamsState.onWriteFailure(failure);
    }

    protected void abort(String reason, Throwable failure, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Session abort {} for {}", reason, this, failure);
        }
        this.onFailure(ErrorCode.NO_ERROR.code, reason, failure, callback);
    }

    private void onFailure(int error, String reason, Throwable failure, Callback callback) {
        Collection<Stream> streams = this.getStreams();
        int count = streams.size();
        CountingCallback countCallback = new CountingCallback(callback, count + 1);
        for (Stream stream : streams) {
            if (stream.isClosed()) {
                countCallback.succeeded();
                continue;
            }
            this.failStream(stream, error, reason, failure, countCallback);
        }
        this.notifyFailure(this, failure, countCallback);
    }

    private void failStreams(Predicate<IStream> matcher, String reason, boolean reset) {
        int error = ErrorCode.CANCEL_STREAM_ERROR.code;
        Throwable failure = this.toFailure(error, reason);
        for (Stream stream : this.getStreams()) {
            if (stream.isClosed() || !matcher.test((IStream)stream)) continue;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failing stream {} of {}", (Object)stream, (Object)this);
            }
            this.failStream(stream, error, reason, failure, Callback.NOOP);
            if (!reset) continue;
            stream.reset(new ResetFrame(stream.getId(), error), Callback.NOOP);
        }
    }

    private void failStream(Stream stream, int error, String reason, Throwable failure, Callback callback) {
        ((IStream)stream).process(new FailureFrame(error, reason, failure), callback);
    }

    private Throwable toFailure(int error, String reason) {
        return new IOException(String.format("%s/%s", ErrorCode.toString(error, null), reason));
    }

    @Override
    public void newStream(HeadersFrame frame, Promise<Stream> promise, Stream.Listener listener) {
        this.newStream(new IStream.FrameList(frame), promise, listener);
    }

    @Override
    public void newStream(IStream.FrameList frames, Promise<Stream> promise, Stream.Listener listener) {
        this.streamsState.newLocalStream(frames, promise, listener);
    }

    public Stream newUpgradeStream(HeadersFrame frame, Stream.Listener listener, Consumer<Throwable> failFn) {
        return this.streamsState.newUpgradeStream(frame, listener, failFn);
    }

    protected IStream newStream(int streamId, MetaData.Request request, boolean local) {
        return new HTTP2Stream(this, streamId, request, local);
    }

    @Override
    public int priority(PriorityFrame frame, Callback callback) {
        return this.streamsState.priority(frame, callback);
    }

    @Override
    public void push(IStream stream, Promise<Stream> promise, PushPromiseFrame frame, Stream.Listener listener) {
        this.streamsState.push(frame, (Promise<Stream>)new Promise.Wrapper<Stream>(promise){

            @Override
            public void succeeded(Stream pushedStream) {
                ((IStream)pushedStream).updateClose(true, CloseState.Event.RECEIVED);
                super.succeeded(pushedStream);
            }
        }, listener);
    }

    @Override
    public void settings(SettingsFrame frame, Callback callback) {
        this.control(null, callback, frame);
    }

    @Override
    public void ping(PingFrame frame, Callback callback) {
        if (frame.isReply()) {
            callback.failed(new IllegalArgumentException());
        } else {
            this.control(null, callback, frame);
        }
    }

    void reset(IStream stream, ResetFrame frame, Callback callback) {
        this.control(stream, Callback.from(() -> {
            if (stream != null) {
                stream.close();
                this.removeStream(stream);
            }
        }, callback), frame);
    }

    @Override
    public boolean close(int error, String reason, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Closing {}/{} {}", ErrorCode.toString(error, null), reason, this);
        }
        return this.goAway(this.newGoAwayFrame(error, reason), callback);
    }

    @Override
    public CompletableFuture<Void> shutdown() {
        return this.streamsState.shutdown();
    }

    public boolean goAway(GoAwayFrame frame, Callback callback) {
        return this.streamsState.goAway(frame, callback);
    }

    private GoAwayFrame newGoAwayFrame(int error, String reason) {
        return this.newGoAwayFrame(this.getLastRemoteStreamId(), error, reason);
    }

    private GoAwayFrame newGoAwayFrame(int lastRemoteStreamId, int error, String reason) {
        byte[] payload = null;
        if (reason != null) {
            reason = reason.substring(0, Math.min(reason.length(), 32));
            payload = reason.getBytes(StandardCharsets.UTF_8);
        }
        return new GoAwayFrame(lastRemoteStreamId, error, payload);
    }

    @Override
    public boolean isClosed() {
        return this.getCloseState() != CloseState.NOT_CLOSED;
    }

    public CloseState getCloseState() {
        return this.streamsState.getCloseState();
    }

    private void control(IStream stream, Callback callback, Frame frame) {
        this.frames(stream, List.of(frame), callback);
    }

    @Override
    public void frames(IStream stream, List<? extends Frame> frames, Callback callback) {
        int count = frames.size();
        if (count > 1) {
            callback = new CountingCallback(callback, count);
        }
        for (int i = 1; i <= count; ++i) {
            Frame frame = frames.get(i - 1);
            HTTP2Flusher.Entry entry = this.newEntry(frame, stream, callback);
            this.frame(entry, i == count);
        }
    }

    private HTTP2Flusher.Entry newEntry(Frame frame, IStream stream, Callback callback) {
        return frame.getType() == FrameType.DATA ? new DataEntry((DataFrame)frame, stream, callback) : new ControlEntry(frame, stream, callback);
    }

    @Override
    public void data(IStream stream, Callback callback, DataFrame frame) {
        this.frame(this.newEntry(frame, stream, callback), true);
    }

    private void frame(HTTP2Flusher.Entry entry, boolean flush) {
        boolean queued;
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} {} on {}", flush ? "Sending" : "Queueing", entry, this);
        }
        boolean bl = queued = entry.hasHighPriority() ? this.flusher.prepend(entry) : this.flusher.append(entry);
        if (queued && flush) {
            if (entry.stream != null) {
                entry.stream.notIdle();
            }
            this.flusher.iterate();
        }
    }

    protected IStream createLocalStream(int streamId, MetaData.Request request, Consumer<Throwable> failFn) {
        int localCount;
        do {
            localCount = this.localStreamCount.get();
            int maxCount = this.getMaxLocalStreams();
            if (maxCount < 0 || localCount < maxCount) continue;
            IllegalStateException failure = new IllegalStateException("Max local stream count " + maxCount + " exceeded: " + localCount);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Could not create local stream #{} for {}", streamId, this, failure);
            }
            failFn.accept(failure);
            return null;
        } while (!this.localStreamCount.compareAndSet(localCount, localCount + 1));
        IStream stream = this.newStream(streamId, request, true);
        if (this.streams.putIfAbsent(streamId, stream) == null) {
            stream.setIdleTimeout(this.getStreamIdleTimeout());
            this.flowControl.onStreamCreated(stream);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Created local {} for {}", (Object)stream, (Object)this);
            }
            return stream;
        }
        this.localStreamCount.decrementAndGet();
        failFn.accept(new IllegalStateException("Duplicate stream " + streamId));
        return null;
    }

    protected IStream createRemoteStream(int streamId, MetaData.Request request) {
        int remoteClosing;
        int remoteCount;
        long encoded;
        this.updateLastRemoteStreamId(streamId);
        if (!this.streamsState.newRemoteStream(streamId)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Could not create remote stream #{} for {}", (Object)streamId, (Object)this);
            }
            return null;
        }
        do {
            encoded = this.remoteStreamCount.get();
            remoteCount = AtomicBiInteger.getHi(encoded);
            remoteClosing = AtomicBiInteger.getLo(encoded);
            int maxCount = this.getMaxRemoteStreams();
            if (maxCount < 0 || remoteCount - remoteClosing < maxCount) continue;
            IllegalStateException failure = new IllegalStateException("Max remote stream count " + maxCount + " exceeded: " + remoteCount + "+" + remoteClosing);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Could not create remote stream #{} for {}", streamId, this, failure);
            }
            this.reset(null, new ResetFrame(streamId, ErrorCode.REFUSED_STREAM_ERROR.code), Callback.from(() -> this.onStreamDestroyed(streamId)));
            return null;
        } while (!this.remoteStreamCount.compareAndSet(encoded, remoteCount + 1, remoteClosing));
        IStream stream = this.newStream(streamId, request, false);
        if (this.streams.putIfAbsent(streamId, stream) == null) {
            stream.setIdleTimeout(this.getStreamIdleTimeout());
            this.flowControl.onStreamCreated(stream);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Created remote {} for {}", (Object)stream, (Object)this);
            }
            return stream;
        }
        this.remoteStreamCount.addAndGetHi(-1);
        this.onStreamDestroyed(streamId);
        this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "duplicate_stream");
        return null;
    }

    void updateStreamCount(boolean local, int deltaStreams, int deltaClosing) {
        if (local) {
            this.localStreamCount.addAndGet(deltaStreams);
        } else {
            this.remoteStreamCount.add(deltaStreams, deltaClosing);
        }
    }

    @Override
    public boolean removeStream(IStream stream) {
        int streamId = stream.getId();
        IStream removed = (IStream)this.streams.remove(streamId);
        if (removed == null) {
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Removed {} {} from {}", stream.isLocal() ? "local" : "remote", stream, this);
        }
        this.onStreamClosed(stream);
        this.flowControl.onStreamDestroyed(stream);
        this.onStreamDestroyed(streamId);
        return true;
    }

    @Override
    public Collection<Stream> getStreams() {
        return new ArrayList<Stream>(this.streams.values());
    }

    @ManagedAttribute(value="The number of active streams")
    public int getStreamCount() {
        return this.streamsState.streamCount.intValue();
    }

    @Override
    public IStream getStream(int streamId) {
        return (IStream)this.streams.get(streamId);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        SocketAddress local = this.getLocalSocketAddress();
        if (local instanceof InetSocketAddress) {
            return (InetSocketAddress)local;
        }
        return null;
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return this.endPoint.getLocalSocketAddress();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        SocketAddress remote = this.getRemoteSocketAddress();
        if (remote instanceof InetSocketAddress) {
            return (InetSocketAddress)remote;
        }
        return null;
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return this.endPoint.getRemoteSocketAddress();
    }

    @ManagedAttribute(value="The flow control send window", readonly=true)
    public int getSendWindow() {
        return this.sendWindow.get();
    }

    @ManagedAttribute(value="The flow control receive window", readonly=true)
    public int getRecvWindow() {
        return this.recvWindow.get();
    }

    @Override
    public int updateSendWindow(int delta) {
        return this.sendWindow.getAndAdd(delta);
    }

    @Override
    public int updateRecvWindow(int delta) {
        return this.recvWindow.getAndAdd(delta);
    }

    @Override
    @ManagedAttribute(value="Whether HTTP/2 push is enabled", readonly=true)
    public boolean isPushEnabled() {
        return this.pushEnabled;
    }

    @ManagedAttribute(value="Whether CONNECT requests supports a protocol", readonly=true)
    public boolean isConnectProtocolEnabled() {
        return this.connectProtocolEnabled;
    }

    public void setConnectProtocolEnabled(boolean connectProtocolEnabled) {
        this.connectProtocolEnabled = connectProtocolEnabled;
    }

    @Override
    public void onShutdown() {
        this.streamsState.onShutdown();
    }

    @Override
    public boolean onIdleTimeout() {
        return this.streamsState.onIdleTimeout();
    }

    private void notIdle() {
        this.streamsState.idleNanoTime = NanoTime.now();
    }

    @Override
    public void onFrame(Frame frame) {
        this.onConnectionFailure(ErrorCode.PROTOCOL_ERROR.code, "upgrade");
    }

    void scheduleTimeout(HTTP2Stream stream) {
        this.streamTimeouts.schedule(stream);
    }

    private void onStreamCreated(int streamId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating stream #{} for {}", (Object)streamId, (Object)this);
        }
        this.streamsState.onStreamCreated();
    }

    protected final void onStreamOpened(IStream stream) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Opened stream {} for {}", (Object)stream, (Object)this);
        }
        this.streamsOpened.incrementAndGet();
    }

    private void onStreamClosed(IStream stream) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Closed stream {} for {}", (Object)stream, (Object)this);
        }
        this.streamsClosed.incrementAndGet();
    }

    private void onStreamDestroyed(int streamId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Destroyed stream #{} for {}", (Object)streamId, (Object)this);
        }
        this.streamsState.onStreamDestroyed();
    }

    @Override
    public void onFlushed(long bytes) throws IOException {
        this.flusher.onFlushed(bytes);
    }

    private void terminate(Throwable cause) {
        this.flusher.terminate(cause);
        this.streamTimeouts.destroy();
        this.disconnect();
    }

    public void disconnect() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Disconnecting {}", (Object)this);
        }
        this.endPoint.close();
    }

    public boolean isDisconnected() {
        return !this.endPoint.isOpen();
    }

    protected int getLastRemoteStreamId() {
        return this.lastRemoteStreamId.get();
    }

    protected void updateLastRemoteStreamId(int streamId) {
        Atomics.updateMax(this.lastRemoteStreamId, streamId);
    }

    protected Stream.Listener notifyNewStream(Stream stream, HeadersFrame frame) {
        try {
            return this.listener.onNewStream(stream, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
            return null;
        }
    }

    protected void notifySettings(Session session, SettingsFrame frame) {
        try {
            this.listener.onSettings(session, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyPing(Session session, PingFrame frame) {
        try {
            this.listener.onPing(session, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyReset(Session session, ResetFrame frame) {
        try {
            this.listener.onReset(session, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyGoAway(Session session, GoAwayFrame frame) {
        try {
            this.listener.onGoAway(session, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener " + String.valueOf(this.listener), x);
        }
    }

    protected void notifyClose(Session session, GoAwayFrame frame, Callback callback) {
        try {
            this.listener.onClose(session, frame, callback);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected boolean notifyIdleTimeout(Session session) {
        try {
            return this.listener.onIdleTimeout(session);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
            return true;
        }
    }

    protected void notifyFailure(Session session, Throwable failure, Callback callback) {
        try {
            this.listener.onFailure(session, failure, callback);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)this.listener, (Object)x);
        }
    }

    protected void notifyHeaders(IStream stream, HeadersFrame frame) {
        Stream.Listener listener = stream.getListener();
        if (listener == null) {
            return;
        }
        try {
            listener.onHeaders(stream, frame);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
        }
    }

    protected static boolean isClientStream(int streamId) {
        return (streamId & 1) == 1;
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, new DumpableCollection("streams", this.streams.values()));
    }

    @Override
    public String toString() {
        return String.format("%s@%x{local:%s,remote:%s,sendWindow=%s,recvWindow=%s,%s}", this.getClass().getSimpleName(), this.hashCode(), this.getEndPoint().getLocalSocketAddress(), this.getEndPoint().getRemoteSocketAddress(), this.sendWindow, this.recvWindow, this.streamsState);
    }

    private class StreamsState {
        private final AutoLock lock = new AutoLock();
        private final Queue<Slot> slots = new ArrayDeque<Slot>();
        private final AtomicLong streamCount = new AtomicLong();
        private long idleNanoTime = NanoTime.now();
        private CloseState closed = CloseState.NOT_CLOSED;
        private Runnable zeroStreamsAction;
        private GoAwayFrame goAwayRecv;
        private GoAwayFrame goAwaySent;
        private Throwable failure;
        private Thread flushing;
        private CompletableFuture<Void> shutdownCallback;

        private StreamsState() {
        }

        private CloseState getCloseState() {
            try (AutoLock ignored = this.lock.lock();){
                CloseState closeState = this.closed;
                return closeState;
            }
        }

        private CompletableFuture<Void> shutdown() {
            Callback.Completable future;
            try (AutoLock ignored = this.lock.lock();){
                if (this.shutdownCallback != null) {
                    CompletableFuture<Void> completableFuture = this.shutdownCallback;
                    return completableFuture;
                }
                if (this.closed == CloseState.CLOSED) {
                    CompletableFuture<Object> completableFuture = CompletableFuture.completedFuture(null);
                    return completableFuture;
                }
                this.shutdownCallback = future = new Callback.Completable();
            }
            this.goAway(GoAwayFrame.GRACEFUL, Callback.NOOP);
            return future;
        }

        /*
         * Unable to fully structure code
         */
        private boolean goAway(GoAwayFrame frame, Callback callback) {
            sendGoAway = false;
            tryRunZeroStreamsAction = false;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: {
                        this.goAwaySent = frame;
                        this.closed = CloseState.LOCALLY_CLOSED;
                        sendGoAway = true;
                        if (frame.isGraceful()) {
                            this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$goAway$0(), ()V)((StreamsState)this);
                            tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                            ** break;
                        }
lbl14:
                        // 3 sources

                        break;
                    }
                    case 2: {
                        if (frame.isGraceful()) {
                            if (HTTP2Session.LOG.isDebugEnabled()) {
                                HTTP2Session.LOG.debug("Already sent, ignored GOAWAY {} for {}", (Object)frame, (Object)HTTP2Session.this);
                                ** break;
                            }
lbl20:
                            // 3 sources

                        } else if (this.goAwaySent.isGraceful() || frame.getLastStreamId() < this.goAwaySent.getLastStreamId() || frame.getError() != ErrorCode.NO_ERROR.code) {
                            this.goAwaySent = frame;
                            sendGoAway = true;
                            ** break;
lbl25:
                            // 1 sources

                        } else if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already sent, ignored GOAWAY {} for {}", (Object)frame, (Object)HTTP2Session.this);
                            ** break;
                        }
lbl29:
                        // 3 sources

                        break;
                    }
                    case 3: {
                        this.goAwaySent = frame;
                        sendGoAway = true;
                        if (frame.isGraceful()) {
                            this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$goAway$1(), ()V)((StreamsState)this);
                            tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                            ** break;
lbl37:
                            // 1 sources

                        } else if (this.goAwayRecv.isGraceful()) {
                            if (HTTP2Session.LOG.isDebugEnabled()) {
                                HTTP2Session.LOG.debug("Waiting non-graceful GOAWAY for {}", (Object)HTTP2Session.this);
                                ** break;
                            }
lbl42:
                            // 3 sources

                        } else {
                            this.closed = CloseState.CLOSING;
                            this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$goAway$2(org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)frame);
                            tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                            ** break;
                        }
lbl47:
                        // 1 sources

                        break;
                    }
                    default: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already closed, ignored {} for {}", (Object)frame, (Object)HTTP2Session.this);
                        }
                        break;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (sendGoAway) {
                if (tryRunZeroStreamsAction) {
                    this.sendGoAway(frame, Callback.from(callback, (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, tryRunZeroStreamsAction(), ()V)((StreamsState)this)));
                } else {
                    this.sendGoAway(frame, callback);
                }
                return true;
            }
            callback.succeeded();
            return false;
        }

        /*
         * Unable to fully structure code
         */
        private void halt(String reason) {
            if (HTTP2Session.LOG.isDebugEnabled()) {
                HTTP2Session.LOG.debug("Halting ({}) for {}", (Object)reason, (Object)HTTP2Session.this);
            }
            goAwayFrame = null;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: {
                        if (this.goAwaySent == null || this.goAwaySent.isGraceful()) {
                            this.goAwaySent = goAwayFrame = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, reason);
                        }
                        goAwayFrameEvent = this.goAwayRecv != null ? this.goAwayRecv : this.goAwaySent;
                        this.closed = CloseState.CLOSED;
                        this.zeroStreamsAction = null;
                        if (this.failure != null) {
                            this.failure = HTTP2Session.this.toFailure(ErrorCode.NO_ERROR.code, reason);
                            ** break;
                        }
lbl16:
                        // 3 sources

                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            HTTP2Session.this.failStreams((Predicate<IStream>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$halt$3(org.eclipse.jetty.http2.IStream ), (Lorg/eclipse/jetty/http2/IStream;)Z)(), reason, true);
            if (goAwayFrame != null) {
                this.sendGoAwayAndTerminate(goAwayFrame, goAwayFrameEvent);
            } else {
                this.terminate(goAwayFrameEvent);
            }
        }

        /*
         * Unable to fully structure code
         */
        private void onGoAway(GoAwayFrame frame) {
            failStreams = false;
            tryRunZeroStreamsAction = false;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: {
                        this.goAwayRecv = frame;
                        if (frame.isGraceful()) {
                            this.closed = CloseState.REMOTELY_CLOSED;
                            if (HTTP2Session.LOG.isDebugEnabled()) {
                                HTTP2Session.LOG.debug("Waiting non-graceful GOAWAY for {}", (Object)HTTP2Session.this);
                                ** break;
                            }
lbl13:
                            // 3 sources

                        } else {
                            this.goAwaySent = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, "close");
                            this.closed = CloseState.CLOSING;
                            goAwayFrame = this.goAwaySent;
                            this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onGoAway$4(org.eclipse.jetty.http2.frames.GoAwayFrame org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)goAwayFrame, (GoAwayFrame)frame);
                            tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                            failStreams = true;
                            ** break;
                        }
lbl21:
                        // 1 sources

                        break;
                    }
                    case 2: {
                        this.goAwayRecv = frame;
                        if (frame.isGraceful()) {
                            if (HTTP2Session.LOG.isDebugEnabled()) {
                                HTTP2Session.LOG.debug("Waiting non-graceful GOAWAY for {}", (Object)HTTP2Session.this);
                                ** break;
                            }
lbl28:
                            // 3 sources

                        } else {
                            this.closed = CloseState.CLOSING;
                            if (this.goAwaySent.isGraceful()) {
                                goAwayFrame = this.goAwaySent = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, "close");
                                this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onGoAway$5(org.eclipse.jetty.http2.frames.GoAwayFrame org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)goAwayFrame, (GoAwayFrame)frame);
                                tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                                ** break;
lbl35:
                                // 1 sources

                            } else {
                                this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onGoAway$6(org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)frame);
                                tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                                failStreams = true;
                                ** break;
                            }
                        }
lbl40:
                        // 1 sources

                        break;
                    }
                    case 3: {
                        if (frame.isGraceful()) {
                            if (HTTP2Session.LOG.isDebugEnabled()) {
                                HTTP2Session.LOG.debug("Already received, ignoring GOAWAY for {}", (Object)HTTP2Session.this);
                                ** break;
                            }
lbl46:
                            // 3 sources

                        } else {
                            this.goAwayRecv = frame;
                            this.closed = CloseState.CLOSING;
                            if (this.goAwaySent == null || this.goAwaySent.isGraceful()) {
                                goAwayFrame = this.goAwaySent = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, "close");
                                this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onGoAway$7(org.eclipse.jetty.http2.frames.GoAwayFrame org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)goAwayFrame, (GoAwayFrame)frame);
                            } else {
                                this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onGoAway$8(org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)frame);
                            }
                            tryRunZeroStreamsAction = this.streamCount.get() == 0L;
                            failStreams = true;
                            ** break;
                        }
lbl57:
                        // 1 sources

                        break;
                    }
                    default: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already closed, ignored {} for {}", (Object)frame, (Object)HTTP2Session.this);
                        }
                        break;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            HTTP2Session.this.notifyGoAway(HTTP2Session.this, frame);
            if (failStreams) {
                failIf = (Predicate<IStream>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$onGoAway$9(org.eclipse.jetty.http2.frames.GoAwayFrame org.eclipse.jetty.http2.IStream ), (Lorg/eclipse/jetty/http2/IStream;)Z)((GoAwayFrame)frame);
                HTTP2Session.this.failStreams(failIf, "closing", false);
            }
            if (tryRunZeroStreamsAction) {
                this.tryRunZeroStreamsAction();
            }
        }

        /*
         * Unable to fully structure code
         */
        private void onShutdown() {
            reason = "input_shutdown";
            cause = null;
            failStreams = false;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: 
                    case 2: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Unexpected ISHUT for {}", (Object)HTTP2Session.this);
                        }
                        this.closed = CloseState.CLOSING;
                        cause = new ClosedChannelException();
                        this.failure = cause;
                        ** break;
lbl14:
                        // 1 sources

                        break;
                    }
                    case 3: {
                        this.closed = CloseState.CLOSING;
                        goAwayFrame = HTTP2Session.this.newGoAwayFrame(0, ErrorCode.NO_ERROR.code, reason);
                        this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onShutdown$10(org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)goAwayFrame);
                        this.failure = new ClosedChannelException();
                        failStreams = true;
                        ** break;
lbl22:
                        // 1 sources

                        break;
                    }
                    case 4: {
                        if (this.failure == null) {
                            this.failure = new ClosedChannelException();
                        }
                        failStreams = true;
                        ** break;
lbl28:
                        // 1 sources

                        break;
                    }
                    default: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already closed, ignoring ISHUT for {}", (Object)HTTP2Session.this);
                        }
                        return;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (failStreams) {
                failIf = (Predicate<IStream>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$onShutdown$11(org.eclipse.jetty.http2.IStream ), (Lorg/eclipse/jetty/http2/IStream;)Z)();
                HTTP2Session.this.failStreams(failIf, reason, false);
                this.tryRunZeroStreamsAction();
            } else {
                goAwayFrame = HTTP2Session.this.newGoAwayFrame(0, ErrorCode.NO_ERROR.code, reason);
                HTTP2Session.this.abort(reason, cause, Callback.from((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onShutdown$12(org.eclipse.jetty.http2.frames.GoAwayFrame ), ()V)((StreamsState)this, (GoAwayFrame)goAwayFrame)));
            }
        }

        /*
         * Unable to fully structure code
         */
        private boolean onIdleTimeout() {
            reason = "idle_timeout";
            notify = false;
            sendGoAway = false;
            goAwayFrame = null;
            cause = null;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: {
                        elapsed = NanoTime.millisSince(this.idleNanoTime);
                        if (elapsed < HTTP2Session.this.endPoint.getIdleTimeout()) {
                            var9_11 = false;
                            return var9_11;
                        }
                        notify = true;
                        ** break;
lbl16:
                        // 1 sources

                        break;
                    }
                    case 2: {
                        if (this.goAwaySent.isGraceful()) {
                            this.goAwaySent = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, reason);
                            sendGoAway = true;
                        }
                        goAwayFrame = this.goAwaySent;
                        this.closed = CloseState.CLOSING;
                        this.zeroStreamsAction = null;
                        cause = new TimeoutException("Session idle timeout expired");
                        this.failure = cause;
                        ** break;
lbl27:
                        // 1 sources

                        break;
                    }
                    case 3: {
                        this.goAwaySent = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, reason);
                        sendGoAway = true;
                        goAwayFrame = this.goAwaySent;
                        this.closed = CloseState.CLOSING;
                        this.zeroStreamsAction = null;
                        cause = new TimeoutException("Session idle timeout expired");
                        this.failure = cause;
                        ** break;
lbl37:
                        // 1 sources

                        break;
                    }
                    default: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already closed, ignored idle timeout for {}", (Object)HTTP2Session.this);
                        }
                        var7_9 = false;
                        return var7_9;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (notify) {
                confirmed = HTTP2Session.this.notifyIdleTimeout(HTTP2Session.this);
                if (HTTP2Session.LOG.isDebugEnabled()) {
                    HTTP2Session.LOG.debug("Idle timeout {} for {}", (Object)(confirmed != false ? "confirmed" : "ignored"), (Object)HTTP2Session.this);
                }
                if (confirmed) {
                    this.halt(reason);
                }
                return false;
            }
            HTTP2Session.this.failStreams((Predicate<IStream>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$onIdleTimeout$13(org.eclipse.jetty.http2.IStream ), (Lorg/eclipse/jetty/http2/IStream;)Z)(), reason, true);
            if (sendGoAway) {
                this.sendGoAway(goAwayFrame, Callback.NOOP);
            }
            HTTP2Session.this.notifyFailure(HTTP2Session.this, cause, Callback.NOOP);
            this.terminate(goAwayFrame);
            return false;
        }

        /*
         * Unable to fully structure code
         */
        private void onSessionFailure(int error, String reason, Callback callback) {
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: {
                        this.goAwaySent = goAwayFrame = HTTP2Session.this.newGoAwayFrame(error, reason);
                        this.closed = CloseState.CLOSING;
                        this.zeroStreamsAction = null;
                        this.failure = cause = HTTP2Session.this.toFailure(error, reason);
                        ** break;
lbl10:
                        // 1 sources

                        break;
                    }
                    default: {
                        if (HTTP2Session.LOG.isDebugEnabled()) {
                            HTTP2Session.LOG.debug("Already closed, ignored session failure {}", (Object)HTTP2Session.this, (Object)this.failure);
                        }
                        callback.succeeded();
                        return;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (HTTP2Session.LOG.isDebugEnabled()) {
                HTTP2Session.LOG.debug("Session failure {}", (Object)HTTP2Session.this, (Object)cause);
            }
            HTTP2Session.this.failStreams((Predicate<IStream>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$onSessionFailure$14(org.eclipse.jetty.http2.IStream ), (Lorg/eclipse/jetty/http2/IStream;)Z)(), reason, true);
            this.sendGoAway(goAwayFrame, Callback.NOOP);
            HTTP2Session.this.notifyFailure(HTTP2Session.this, cause, Callback.NOOP);
            this.terminate(goAwayFrame);
        }

        /*
         * Unable to fully structure code
         */
        private void onWriteFailure(Throwable x) {
            reason = "write_failure";
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: 
                    case 2: 
                    case 3: {
                        this.closed = CloseState.CLOSING;
                        this.zeroStreamsAction = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$onWriteFailure$15(java.lang.String ), ()V)((StreamsState)this, (String)reason);
                        this.failure = x;
                        ** break;
lbl10:
                        // 1 sources

                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            HTTP2Session.this.abort(reason, x, Callback.from((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, tryRunZeroStreamsAction(), ()V)((StreamsState)this)));
        }

        private void sendGoAwayAndTerminate(GoAwayFrame frame, GoAwayFrame eventFrame) {
            this.sendGoAway(frame, Callback.from(Callback.NOOP, () -> this.terminate(eventFrame)));
        }

        private void sendGoAway(GoAwayFrame frame, Callback callback) {
            HTTP2Session.this.control(null, callback, frame);
        }

        private void onStreamCreated() {
            this.streamCount.incrementAndGet();
        }

        private void onStreamDestroyed() {
            long count = this.streamCount.decrementAndGet();
            if (count == 0L) {
                this.tryRunZeroStreamsAction();
            }
        }

        /*
         * Unable to fully structure code
         */
        private void tryRunZeroStreamsAction() {
            action = null;
            ignored = this.lock.lock();
            try {
                count = this.streamCount.get();
                if (count > 0L) {
                    if (HTTP2Session.LOG.isDebugEnabled()) {
                        HTTP2Session.LOG.debug("Deferred closing action, {} pending streams on {}", (Object)count, (Object)HTTP2Session.this);
                    }
                    return;
                }
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 2: {
                        if (!this.goAwaySent.isGraceful()) ** break;
                        action = this.zeroStreamsAction;
                        this.zeroStreamsAction = null;
                        ** break;
lbl15:
                        // 1 sources

                        break;
                    }
                    case 3: {
                        if (this.goAwaySent != null && this.goAwaySent.isGraceful()) {
                            action = this.zeroStreamsAction;
                            this.zeroStreamsAction = null;
                            ** break;
                        }
lbl21:
                        // 3 sources

                        break;
                    }
                    case 4: {
                        this.closed = CloseState.CLOSED;
                        action = this.zeroStreamsAction;
                        this.zeroStreamsAction = null;
                        ** break;
lbl27:
                        // 1 sources

                        break;
                    }
                    ** default:
lbl29:
                    // 1 sources

                    break;
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (action != null) {
                if (HTTP2Session.LOG.isDebugEnabled()) {
                    HTTP2Session.LOG.debug("Executing zero streams action on {}", (Object)HTTP2Session.this);
                }
                action.run();
            }
        }

        private void terminate(GoAwayFrame frame) {
            CompletableFuture<Void> completable;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Terminating {}", (Object)HTTP2Session.this);
            }
            try (AutoLock ignored = this.lock.lock();){
                completable = this.shutdownCallback;
            }
            if (completable != null) {
                completable.complete(null);
            }
            HTTP2Session.this.terminate(this.failure);
            HTTP2Session.this.notifyClose(HTTP2Session.this, frame, Callback.NOOP);
        }

        private int priority(PriorityFrame frame, Callback callback) {
            Slot slot = new Slot();
            int currentStreamId = frame.getStreamId();
            int streamId = this.reserveSlot(slot, currentStreamId, callback::failed);
            if (streamId > 0) {
                if (currentStreamId <= 0) {
                    frame = frame.withStreamId(streamId);
                }
                slot.entries = List.of(HTTP2Session.this.newEntry(frame, null, Callback.from(callback::succeeded, x -> {
                    HTTP2Session.this.onStreamDestroyed(streamId);
                    callback.failed((Throwable)x);
                })));
                this.flush();
            }
            return streamId;
        }

        private void newLocalStream(IStream.FrameList frameList, Promise<Stream> promise, Stream.Listener listener) {
            Slot slot = new Slot();
            int currentStreamId = frameList.getStreamId();
            int streamId = this.reserveSlot(slot, currentStreamId, promise::failed);
            if (streamId > 0) {
                List<StreamFrame> frames = frameList.getFrames();
                if (currentStreamId <= 0) {
                    frames = frames.stream().map(frame -> frame.withStreamId(streamId)).collect(Collectors.toList());
                }
                if (this.createLocalStream(slot, frames, promise, listener, streamId)) {
                    return;
                }
                this.freeSlot(slot, streamId);
            }
        }

        private Stream newUpgradeStream(HeadersFrame frame, Stream.Listener listener, Consumer<Throwable> failFn) {
            int streamId = HTTP2Session.this.localStreamIds.getAndAdd(2);
            HTTP2Session.this.onStreamCreated(streamId);
            IStream stream = HTTP2Session.this.createLocalStream(streamId, (MetaData.Request)frame.getMetaData(), x -> {
                HTTP2Session.this.onStreamDestroyed(streamId);
                failFn.accept((Throwable)x);
            });
            if (stream != null) {
                stream.setListener(listener);
                stream.updateClose(frame.isEndStream(), CloseState.Event.AFTER_SEND);
            }
            return stream;
        }

        /*
         * Unable to fully structure code
         */
        private boolean newRemoteStream(int streamId) {
            created = false;
            ignored = this.lock.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$http2$CloseState[this.closed.ordinal()]) {
                    case 1: {
                        created = true;
                        ** break;
lbl8:
                        // 1 sources

                        break;
                    }
                    case 2: {
                        if (streamId > this.goAwaySent.getLastStreamId()) ** break;
                        created = true;
                        ** break;
lbl13:
                        // 1 sources

                        break;
                    }
                    ** default:
lbl15:
                    // 1 sources

                    break;
                }
            }
            finally {
                if (ignored != null) {
                    ignored.close();
                }
            }
            if (created) {
                HTTP2Session.this.onStreamCreated(streamId);
            }
            return created;
        }

        private void push(PushPromiseFrame frame, Promise<Stream> promise, Stream.Listener listener) {
            Slot slot = new Slot();
            int streamId = this.reserveSlot(slot, 0, promise::failed);
            if (streamId > 0) {
                if (this.createLocalStream(slot, Collections.singletonList(frame = frame.withStreamId(streamId)), promise, listener, streamId)) {
                    return;
                }
                this.freeSlot(slot, streamId);
            }
        }

        private boolean createLocalStream(Slot slot, List<StreamFrame> frames, Promise<Stream> promise, Stream.Listener listener, int streamId) {
            MetaData.Request request = this.extractMetaDataRequest(frames.get(0));
            if (request == null) {
                return false;
            }
            IStream stream = HTTP2Session.this.createLocalStream(streamId, request, promise::failed);
            if (stream == null) {
                return false;
            }
            stream.setListener(listener);
            stream.process(new PrefaceFrame(), Callback.NOOP);
            Callback streamCallback = Callback.from(Invocable.InvocationType.NON_BLOCKING, () -> promise.succeeded(stream), x -> {
                HTTP2Session.this.onStreamDestroyed(streamId);
                promise.failed((Throwable)x);
            });
            int count = frames.size();
            if (count == 1) {
                slot.entries = List.of(HTTP2Session.this.newEntry(frames.get(0), stream, streamCallback));
            } else {
                CountingCallback callback = new CountingCallback(streamCallback, count);
                slot.entries = frames.stream().map(frame -> HTTP2Session.this.newEntry((Frame)frame, stream, callback)).collect(Collectors.toList());
            }
            this.flush();
            return true;
        }

        private MetaData.Request extractMetaDataRequest(StreamFrame frame) {
            if (frame instanceof HeadersFrame) {
                return (MetaData.Request)((HeadersFrame)frame).getMetaData();
            }
            if (frame instanceof PushPromiseFrame) {
                return ((PushPromiseFrame)frame).getMetaData();
            }
            return null;
        }

        private int reserveSlot(Slot slot, int streamId, Consumer<Throwable> fail) {
            Throwable failure = null;
            boolean reserved = false;
            try (AutoLock ignored = this.lock.lock();){
                if (this.closed == CloseState.NOT_CLOSED) {
                    if (streamId <= 0) {
                        streamId = HTTP2Session.this.localStreamIds.getAndAdd(2);
                        reserved = true;
                    }
                    this.slots.offer(slot);
                } else {
                    failure = this.failure;
                    if (failure == null) {
                        failure = new IllegalStateException("session closed");
                    }
                }
            }
            if (failure == null) {
                if (reserved) {
                    HTTP2Session.this.onStreamCreated(streamId);
                }
                return streamId;
            }
            fail.accept(failure);
            return 0;
        }

        private void freeSlot(Slot slot, int streamId) {
            try (AutoLock ignored = this.lock.lock();){
                this.slots.remove(slot);
            }
            HTTP2Session.this.onStreamDestroyed(streamId);
            this.flush();
        }

        private void flush() {
            Thread thread2 = Thread.currentThread();
            boolean queued = false;
            while (true) {
                List<HTTP2Flusher.Entry> entries;
                try (AutoLock ignored = this.lock.lock();){
                    if (this.flushing == null) {
                        this.flushing = thread2;
                    } else if (this.flushing != thread2) {
                        return;
                    }
                    Slot slot = this.slots.peek();
                    List<HTTP2Flusher.Entry> list = entries = slot == null ? null : slot.entries;
                    if (entries == null) {
                        this.flushing = null;
                        break;
                    }
                    this.slots.poll();
                }
                queued |= HTTP2Session.this.flusher.append(entries);
            }
            if (queued) {
                HTTP2Session.this.flusher.iterate();
            }
        }

        public String toString() {
            try (AutoLock ignored = this.lock.lock();){
                String string = String.format("state=[streams=%d,%s,goAwayRecv=%s,goAwaySent=%s,failure=%s]", new Object[]{this.streamCount.get(), this.closed, this.goAwayRecv, this.goAwaySent, this.failure});
                return string;
            }
        }

        private /* synthetic */ void lambda$onWriteFailure$15(String reason) {
            GoAwayFrame goAwayFrame = HTTP2Session.this.newGoAwayFrame(0, ErrorCode.NO_ERROR.code, reason);
            this.terminate(goAwayFrame);
        }

        private static /* synthetic */ boolean lambda$onSessionFailure$14(IStream stream) {
            return true;
        }

        private static /* synthetic */ boolean lambda$onIdleTimeout$13(IStream stream) {
            return true;
        }

        private /* synthetic */ void lambda$onShutdown$12(GoAwayFrame goAwayFrame) {
            this.terminate(goAwayFrame);
        }

        private static /* synthetic */ boolean lambda$onShutdown$11(IStream stream) {
            return !stream.isRemotelyClosed();
        }

        private /* synthetic */ void lambda$onShutdown$10(GoAwayFrame goAwayFrame) {
            this.terminate(goAwayFrame);
        }

        private static /* synthetic */ boolean lambda$onGoAway$9(GoAwayFrame frame, IStream stream) {
            return stream.isLocal() && stream.getId() > frame.getLastStreamId();
        }

        private /* synthetic */ void lambda$onGoAway$8(GoAwayFrame frame) {
            this.terminate(frame);
        }

        private /* synthetic */ void lambda$onGoAway$7(GoAwayFrame goAwayFrame, GoAwayFrame frame) {
            this.sendGoAwayAndTerminate(goAwayFrame, frame);
        }

        private /* synthetic */ void lambda$onGoAway$6(GoAwayFrame frame) {
            this.terminate(frame);
        }

        private /* synthetic */ void lambda$onGoAway$5(GoAwayFrame goAwayFrame, GoAwayFrame frame) {
            this.sendGoAwayAndTerminate(goAwayFrame, frame);
        }

        private /* synthetic */ void lambda$onGoAway$4(GoAwayFrame goAwayFrame, GoAwayFrame frame) {
            this.sendGoAwayAndTerminate(goAwayFrame, frame);
        }

        private static /* synthetic */ boolean lambda$halt$3(IStream stream) {
            return true;
        }

        private /* synthetic */ void lambda$goAway$2(GoAwayFrame frame) {
            this.terminate(frame);
        }

        private /* synthetic */ void lambda$goAway$1() {
            GoAwayFrame goAwayFrame = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, "close");
            this.goAway(goAwayFrame, Callback.NOOP);
        }

        private /* synthetic */ void lambda$goAway$0() {
            GoAwayFrame goAwayFrame = HTTP2Session.this.newGoAwayFrame(ErrorCode.NO_ERROR.code, "close");
            this.goAway(goAwayFrame, Callback.NOOP);
        }

        private class Slot {
            private volatile List<HTTP2Flusher.Entry> entries;

            private Slot() {
            }
        }
    }

    private class StreamTimeouts
    extends CyclicTimeouts<HTTP2Stream> {
        private StreamTimeouts(Scheduler scheduler) {
            super(scheduler);
        }

        @Override
        protected Iterator<HTTP2Stream> iterator() {
            return HTTP2Session.this.streams.values().stream().map(HTTP2Stream.class::cast).iterator();
        }

        @Override
        protected boolean onExpired(HTTP2Stream stream) {
            stream.onIdleExpired(new TimeoutException("Idle timeout " + stream.getIdleTimeout() + " ms elapsed"));
            return false;
        }
    }

    private class DataCallback
    extends Callback.Nested {
        private final IStream stream;
        private final int flowControlLength;

        public DataCallback(Callback callback, IStream stream, int flowControlLength) {
            super(callback);
            this.stream = stream;
            this.flowControlLength = flowControlLength;
        }

        @Override
        public void succeeded() {
            this.complete();
            super.succeeded();
        }

        @Override
        public void failed(Throwable x) {
            this.complete();
            super.failed(x);
        }

        private void complete() {
            HTTP2Session.this.notIdle();
            this.stream.notIdle();
            HTTP2Session.this.flowControl.onDataConsumed(HTTP2Session.this, this.stream, this.flowControlLength);
        }
    }

    private class OnResetCallback
    implements Callback {
        private OnResetCallback() {
        }

        @Override
        public void succeeded() {
            this.complete();
        }

        @Override
        public void failed(Throwable x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("OnReset failed", x);
            }
            this.complete();
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.NON_BLOCKING;
        }

        private void complete() {
            HTTP2Session.this.flusher.iterate();
        }
    }

    private class DataEntry
    extends HTTP2Flusher.Entry {
        private int frameBytes;
        private int frameRemaining;
        private int dataBytes;
        private int dataRemaining;

        private DataEntry(DataFrame frame, IStream stream, Callback callback) {
            super(frame, stream, callback);
            this.dataRemaining = frame.remaining();
        }

        @Override
        public int getFrameBytesGenerated() {
            return this.frameBytes;
        }

        @Override
        public int getDataBytesRemaining() {
            return this.dataRemaining;
        }

        @Override
        protected boolean generate(ByteBufferPool.Lease lease) {
            int dataRemaining = this.getDataBytesRemaining();
            int sessionSendWindow = HTTP2Session.this.getSendWindow();
            int streamSendWindow = this.stream.updateSendWindow(0);
            int window = Math.min(streamSendWindow, sessionSendWindow);
            if (window <= 0 && dataRemaining > 0) {
                return false;
            }
            int length = Math.min(dataRemaining, window);
            DataFrame dataFrame = (DataFrame)this.frame;
            int frameBytes = HTTP2Session.this.generator.data(lease, dataFrame, length);
            this.frameBytes += frameBytes;
            this.frameRemaining += frameBytes;
            int dataBytes = frameBytes - 9;
            this.dataBytes += dataBytes;
            this.dataRemaining -= dataBytes;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Generated {}, length/window/data={}/{}/{}", dataFrame, dataBytes, window, dataRemaining);
            }
            HTTP2Session.this.flowControl.onDataSending(this.stream, dataBytes);
            this.stream.updateClose(dataFrame.isEndStream(), CloseState.Event.BEFORE_SEND);
            return true;
        }

        @Override
        public long onFlushed(long bytes) throws IOException {
            long flushed = Math.min((long)this.frameRemaining, bytes);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Flushed {}/{} frame bytes for {}", flushed, bytes, this);
            }
            this.frameRemaining = (int)((long)this.frameRemaining - flushed);
            Object channel = this.stream.getAttachment();
            if (channel instanceof WriteFlusher.Listener) {
                ((WriteFlusher.Listener)channel).onFlushed(flushed);
            }
            return bytes - flushed;
        }

        @Override
        public void succeeded() {
            HTTP2Session.this.bytesWritten.addAndGet(this.frameBytes);
            this.frameBytes = 0;
            this.frameRemaining = 0;
            HTTP2Session.this.flowControl.onDataSent(this.stream, this.dataBytes);
            this.dataBytes = 0;
            DataFrame dataFrame = (DataFrame)this.frame;
            if (this.getDataBytesRemaining() == 0) {
                if (this.stream.updateClose(dataFrame.isEndStream(), CloseState.Event.AFTER_SEND)) {
                    HTTP2Session.this.removeStream(this.stream);
                }
                super.succeeded();
            }
        }
    }

    private class ControlEntry
    extends HTTP2Flusher.Entry {
        private int frameBytes;

        private ControlEntry(Frame frame, IStream stream, Callback callback) {
            super(frame, stream, callback);
        }

        @Override
        public int getFrameBytesGenerated() {
            return this.frameBytes;
        }

        @Override
        protected boolean generate(ByteBufferPool.Lease lease) throws HpackException {
            this.frameBytes = HTTP2Session.this.generator.control(lease, this.frame);
            this.beforeSend();
            return true;
        }

        @Override
        public long onFlushed(long bytes) {
            long flushed = Math.min((long)this.frameBytes, bytes);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Flushed {}/{} frame bytes for {}", flushed, bytes, this);
            }
            this.frameBytes = (int)((long)this.frameBytes - flushed);
            return bytes - flushed;
        }

        private void beforeSend() {
            switch (this.frame.getType()) {
                case HEADERS: {
                    HeadersFrame headersFrame = (HeadersFrame)this.frame;
                    this.stream.updateClose(headersFrame.isEndStream(), CloseState.Event.BEFORE_SEND);
                    break;
                }
                case SETTINGS: {
                    SettingsFrame settingsFrame = (SettingsFrame)this.frame;
                    Integer initialWindow = settingsFrame.getSettings().get(4);
                    if (initialWindow == null) break;
                    HTTP2Session.this.flowControl.updateInitialStreamWindow(HTTP2Session.this, initialWindow, true);
                    break;
                }
            }
        }

        @Override
        boolean hasHighPriority() {
            return this.frame.getType() == FrameType.PING;
        }

        @Override
        public void succeeded() {
            this.commit();
            HTTP2Session.this.bytesWritten.addAndGet(this.frameBytes);
            this.frameBytes = 0;
            switch (this.frame.getType()) {
                case HEADERS: {
                    HeadersFrame headersFrame = (HeadersFrame)this.frame;
                    if (headersFrame.getMetaData().isRequest()) {
                        HTTP2Session.this.onStreamOpened(this.stream);
                    }
                    if (!this.stream.updateClose(headersFrame.isEndStream(), CloseState.Event.AFTER_SEND)) break;
                    HTTP2Session.this.removeStream(this.stream);
                    break;
                }
                case WINDOW_UPDATE: {
                    HTTP2Session.this.flowControl.windowUpdate(HTTP2Session.this, this.stream, (WindowUpdateFrame)this.frame);
                    break;
                }
            }
            super.succeeded();
        }
    }
}

