/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.WritePendingException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.CloseState;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.HTTP2Session;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.FailureFrame;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.http2.frames.ResetFrame;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.io.CyclicTimeouts;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.MathUtils;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.Promise;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2Stream
implements IStream,
Callback,
Dumpable,
CyclicTimeouts.Expirable {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2Stream.class);
    private final AutoLock lock = new AutoLock();
    private Deque<DataEntry> dataQueue;
    private final AtomicReference<Object> attachment = new AtomicReference();
    private final AtomicReference<ConcurrentMap<String, Object>> attributes = new AtomicReference();
    private final AtomicReference<CloseState> closeState = new AtomicReference<CloseState>(CloseState.NOT_CLOSED);
    private final AtomicInteger sendWindow = new AtomicInteger();
    private final AtomicInteger recvWindow = new AtomicInteger();
    private final long creationNanoTime = NanoTime.now();
    private final ISession session;
    private final int streamId;
    private final MetaData.Request request;
    private final boolean local;
    private Callback sendCallback;
    private Throwable failure;
    private boolean localReset;
    private boolean remoteReset;
    private Stream.Listener listener;
    private long dataLength;
    private long dataDemand;
    private boolean dataInitial;
    private boolean dataProcess;
    private boolean committed;
    private long idleTimeout;
    private long expireNanoTime = Long.MAX_VALUE;

    public HTTP2Stream(ISession session, int streamId, MetaData.Request request, boolean local) {
        this.session = session;
        this.streamId = streamId;
        this.request = request;
        this.local = local;
        this.dataLength = Long.MIN_VALUE;
        this.dataInitial = true;
    }

    @Deprecated
    public HTTP2Stream(Scheduler scheduler, ISession session, int streamId, MetaData.Request request, boolean local) {
        this(session, streamId, request, local);
    }

    @Override
    public int getId() {
        return this.streamId;
    }

    @Override
    public Object getAttachment() {
        return this.attachment.get();
    }

    @Override
    public void setAttachment(Object attachment) {
        this.attachment.set(attachment);
    }

    @Override
    public boolean isLocal() {
        return this.local;
    }

    @Override
    public ISession getSession() {
        return this.session;
    }

    @Override
    public void headers(HeadersFrame frame, Callback callback) {
        this.send(new IStream.FrameList(frame), callback);
    }

    @Override
    public void send(IStream.FrameList frameList, Callback callback) {
        if (this.startWrite(callback)) {
            this.session.frames(this, frameList.getFrames(), this);
        }
    }

    @Override
    public void push(PushPromiseFrame frame, Promise<Stream> promise, Stream.Listener listener) {
        this.session.push(this, promise, frame, listener);
    }

    @Override
    public void data(DataFrame frame, Callback callback) {
        if (this.startWrite(callback)) {
            this.session.data(this, this, frame);
        }
    }

    @Override
    public void reset(ResetFrame frame, Callback callback) {
        Throwable resetFailure = null;
        try (AutoLock l = this.lock.lock();){
            if (this.isReset()) {
                resetFailure = this.failure;
            } else {
                this.localReset = true;
                this.failure = new EOFException("reset");
            }
        }
        if (resetFailure != null) {
            callback.failed(resetFailure);
        } else {
            ((HTTP2Session)this.session).reset(this, frame, callback);
        }
    }

    private boolean startWrite(Callback callback) {
        Throwable failure;
        try (AutoLock l = this.lock.lock();){
            failure = this.failure;
            if (failure == null && this.sendCallback == null) {
                this.sendCallback = callback;
                boolean bl = true;
                return bl;
            }
        }
        if (failure == null) {
            failure = new WritePendingException();
        }
        callback.failed(failure);
        return false;
    }

    @Override
    public Object getAttribute(String key) {
        return this.attributes().get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.attributes().put(key, value);
    }

    @Override
    public Object removeAttribute(String key) {
        return this.attributes().remove(key);
    }

    @Override
    public boolean isReset() {
        try (AutoLock l = this.lock.lock();){
            boolean bl = this.localReset || this.remoteReset;
            return bl;
        }
    }

    private boolean isFailed() {
        try (AutoLock l = this.lock.lock();){
            boolean bl = this.failure != null;
            return bl;
        }
    }

    @Override
    public boolean isResetOrFailed() {
        try (AutoLock l = this.lock.lock();){
            boolean bl = this.isReset() || this.isFailed();
            return bl;
        }
    }

    @Override
    public boolean isClosed() {
        return this.closeState.get() == CloseState.CLOSED;
    }

    @Override
    public boolean isRemotelyClosed() {
        CloseState state = this.closeState.get();
        return state == CloseState.REMOTELY_CLOSED || state == CloseState.CLOSING || state == CloseState.CLOSED;
    }

    @Override
    public boolean failAllData(Throwable x) {
        Deque<DataEntry> copy;
        try (AutoLock l = this.lock.lock();){
            this.dataDemand = 0L;
            copy = this.dataQueue;
            this.dataQueue = null;
        }
        DataEntry lastDataEntry = null;
        if (copy != null) {
            copy.forEach(dataEntry -> dataEntry.callback.failed(x));
            DataEntry dataEntry2 = lastDataEntry = copy.isEmpty() ? null : copy.peekLast();
        }
        if (lastDataEntry == null) {
            return this.isRemotelyClosed();
        }
        return lastDataEntry.frame.isEndStream();
    }

    public boolean isLocallyClosed() {
        return this.closeState.get() == CloseState.LOCALLY_CLOSED;
    }

    @Override
    public void commit() {
        this.committed = true;
    }

    @Override
    public boolean isCommitted() {
        return this.committed;
    }

    @Override
    public int dataSize() {
        try (AutoLock l = this.lock.lock();){
            int n = this.dataQueue == null ? 0 : this.dataQueue.size();
            return n;
        }
    }

    public boolean isOpen() {
        return !this.isClosed();
    }

    @Override
    public void notIdle() {
        long idleTimeout = this.getIdleTimeout();
        if (idleTimeout > 0L) {
            this.expireNanoTime = NanoTime.now() + TimeUnit.MILLISECONDS.toNanos(idleTimeout);
        }
    }

    @Override
    public long getExpireNanoTime() {
        return this.expireNanoTime;
    }

    @Override
    public long getIdleTimeout() {
        return this.idleTimeout;
    }

    @Override
    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
        this.notIdle();
        ((HTTP2Session)this.session).scheduleTimeout(this);
    }

    protected void onIdleExpired(TimeoutException timeout2) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Idle timeout {}ms expired on {}", (Object)this.getIdleTimeout(), (Object)this);
        }
        if (this.notifyIdleTimeout(this, timeout2)) {
            this.reset(new ResetFrame(this.getId(), ErrorCode.CANCEL_STREAM_ERROR.code), Callback.NOOP);
        }
    }

    private ConcurrentMap<String, Object> attributes() {
        ConcurrentMap<String, Object> map = this.attributes.get();
        if (map == null && !this.attributes.compareAndSet(null, map = new ConcurrentHashMap<String, Object>())) {
            map = this.attributes.get();
        }
        return map;
    }

    @Override
    public Stream.Listener getListener() {
        return this.listener;
    }

    @Override
    public void setListener(Stream.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void process(Frame frame, Callback callback) {
        this.notIdle();
        switch (frame.getType()) {
            case PREFACE: {
                this.onNewStream(callback);
                break;
            }
            case HEADERS: {
                this.onHeaders((HeadersFrame)frame, callback);
                break;
            }
            case DATA: {
                this.onData((DataFrame)frame, callback);
                break;
            }
            case RST_STREAM: {
                this.onReset((ResetFrame)frame, callback);
                break;
            }
            case PUSH_PROMISE: {
                this.onPush((PushPromiseFrame)frame, callback);
                break;
            }
            case WINDOW_UPDATE: {
                this.onWindowUpdate((WindowUpdateFrame)frame, callback);
                break;
            }
            case FAILURE: {
                this.onFailure((FailureFrame)frame, callback);
                break;
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }
    }

    private void onNewStream(Callback callback) {
        this.notifyNewStream(this);
        callback.succeeded();
    }

    private void onHeaders(HeadersFrame frame, Callback callback) {
        MetaData metaData = frame.getMetaData();
        if (metaData.isRequest() || metaData.isResponse()) {
            HttpFields fields = metaData.getFields();
            long length = -1L;
            if (fields != null && !HttpMethod.CONNECT.is(this.request.getMethod())) {
                length = fields.getLongField(HttpHeader.CONTENT_LENGTH);
            }
            this.dataLength = length >= 0L ? length : Long.MIN_VALUE;
        }
        callback.succeeded();
    }

    private void onData(DataFrame frame, Callback callback) {
        boolean initial;
        if (this.isRemotelyClosed()) {
            this.reset(new ResetFrame(this.streamId, ErrorCode.STREAM_CLOSED_ERROR.code), Callback.NOOP);
            callback.failed(new EOFException("stream_closed"));
            return;
        }
        if (this.isReset()) {
            callback.failed(new IOException("stream_reset"));
            return;
        }
        if (this.dataLength != Long.MIN_VALUE) {
            this.dataLength -= (long)frame.remaining();
            if (this.dataLength < 0L || frame.isEndStream() && this.dataLength != 0L) {
                this.reset(new ResetFrame(this.streamId, ErrorCode.PROTOCOL_ERROR.code), Callback.NOOP);
                callback.failed(new IOException("invalid_data_length"));
                return;
            }
        }
        boolean proceed = false;
        DataEntry entry = new DataEntry(frame, callback);
        try (AutoLock l = this.lock.lock();){
            if (this.dataQueue == null) {
                this.dataQueue = new ArrayDeque<DataEntry>();
            }
            this.dataQueue.offer(entry);
            initial = this.dataInitial;
            if (initial) {
                this.dataInitial = false;
                this.dataProcess = true;
            } else if (!this.dataProcess) {
                proceed = this.dataDemand > 0L;
                this.dataProcess = proceed;
            }
        }
        if (initial) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Starting data processing of {} for {}", (Object)frame, (Object)this);
            }
            this.notifyBeforeData(this);
            l = this.lock.lock();
            try {
                proceed = this.dataDemand > 0L;
                this.dataProcess = proceed;
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} data processing of {} for {}", proceed ? "Proceeding" : "Stalling", frame, this);
        }
        if (proceed) {
            this.processData();
        }
    }

    @Override
    public void demand(long n) {
        long demand;
        if (n <= 0L) {
            throw new IllegalArgumentException("Invalid demand " + n);
        }
        boolean proceed = false;
        try (AutoLock l = this.lock.lock();){
            demand = this.dataDemand = MathUtils.cappedAdd(this.dataDemand, n);
            if (!this.dataProcess) {
                proceed = this.dataQueue != null && !this.dataQueue.isEmpty();
                this.dataProcess = proceed;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Demand {}/{}, {} data processing for {}", n, demand, proceed ? "proceeding" : "stalling", this);
        }
        if (proceed) {
            this.processData();
        }
    }

    private void processData() {
        while (true) {
            DataEntry dataEntry;
            try (AutoLock l = this.lock.lock();){
                if (this.dataQueue == null || this.dataQueue.isEmpty() || this.dataDemand == 0L) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Stalling data processing for {}", (Object)this);
                    }
                    this.dataProcess = false;
                    return;
                }
                --this.dataDemand;
                dataEntry = this.dataQueue.poll();
            }
            DataFrame frame = dataEntry.frame;
            boolean closed = this.updateClose(frame.isEndStream(), CloseState.Event.RECEIVED);
            this.notifyDataDemanded(this, frame, dataEntry.callback);
            if (!closed) continue;
            this.session.removeStream(this);
        }
    }

    private long demand() {
        try (AutoLock l = this.lock.lock();){
            long l2 = this.dataDemand;
            return l2;
        }
    }

    private void onReset(ResetFrame frame, Callback callback) {
        try (AutoLock l = this.lock.lock();){
            this.remoteReset = true;
            this.failure = new EofException("reset");
        }
        this.close();
        if (this.session.removeStream(this)) {
            this.notifyReset(this, frame, callback);
        } else {
            callback.succeeded();
        }
    }

    private void onPush(PushPromiseFrame frame, Callback callback) {
        this.updateClose(true, CloseState.Event.AFTER_SEND);
        callback.succeeded();
    }

    private void onWindowUpdate(WindowUpdateFrame frame, Callback callback) {
        callback.succeeded();
    }

    private void onFailure(FailureFrame frame, Callback callback) {
        try (AutoLock l = this.lock.lock();){
            this.failure = frame.getFailure();
        }
        this.close();
        if (this.session.removeStream(this)) {
            this.notifyFailure(this, frame, callback);
        } else {
            callback.succeeded();
        }
    }

    @Override
    public boolean updateClose(boolean update, CloseState.Event event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Update close for {} update={} event={}", new Object[]{this, update, event});
        }
        if (!update) {
            return false;
        }
        switch (event) {
            case RECEIVED: {
                return this.updateCloseAfterReceived();
            }
            case BEFORE_SEND: {
                return this.updateCloseBeforeSend();
            }
            case AFTER_SEND: {
                return this.updateCloseAfterSend();
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    private boolean updateCloseAfterReceived() {
        block5: while (true) {
            current = this.closeState.get();
            switch (1.$SwitchMap$org$eclipse$jetty$http2$CloseState[current.ordinal()]) {
                case 1: {
                    if (!this.closeState.compareAndSet(current, CloseState.REMOTELY_CLOSED)) continue block5;
                    return false;
                }
                case 2: {
                    if (this.closeState.compareAndSet(current, CloseState.CLOSING)) ** break;
                    continue block5;
                    this.updateStreamCount(0, 1);
                    return false;
                }
                case 3: {
                    this.close();
                    return true;
                }
            }
            break;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    private boolean updateCloseBeforeSend() {
        block4: while (true) {
            current = this.closeState.get();
            switch (1.$SwitchMap$org$eclipse$jetty$http2$CloseState[current.ordinal()]) {
                case 1: {
                    if (!this.closeState.compareAndSet(current, CloseState.LOCALLY_CLOSING)) continue block4;
                    return false;
                }
                case 4: {
                    if (this.closeState.compareAndSet(current, CloseState.CLOSING)) ** break;
                    continue block4;
                    this.updateStreamCount(0, 1);
                    return false;
                }
            }
            break;
        }
        return false;
    }

    private boolean updateCloseAfterSend() {
        block4: while (true) {
            CloseState current = this.closeState.get();
            switch (current) {
                case NOT_CLOSED: 
                case LOCALLY_CLOSING: {
                    if (!this.closeState.compareAndSet(current, CloseState.LOCALLY_CLOSED)) continue block4;
                    return false;
                }
                case REMOTELY_CLOSED: 
                case CLOSING: {
                    this.close();
                    return true;
                }
            }
            break;
        }
        return false;
    }

    public int getSendWindow() {
        return this.sendWindow.get();
    }

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
    public void close() {
        CloseState oldState = this.closeState.getAndSet(CloseState.CLOSED);
        if (oldState != CloseState.CLOSED) {
            int deltaClosing = oldState == CloseState.CLOSING ? -1 : 0;
            this.updateStreamCount(-1, deltaClosing);
            this.onClose();
        }
    }

    public void onClose() {
        this.notifyClosed(this);
    }

    private void updateStreamCount(int deltaStream, int deltaClosing) {
        ((HTTP2Session)this.session).updateStreamCount(this.isLocal(), deltaStream, deltaClosing);
    }

    @Override
    public void succeeded() {
        Callback callback = this.endWrite();
        if (callback != null) {
            callback.succeeded();
        }
    }

    @Override
    public void failed(Throwable x) {
        Callback callback = this.endWrite();
        if (callback != null) {
            callback.failed(x);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Invocable.InvocationType getInvocationType() {
        HTTP2Stream hTTP2Stream = this;
        synchronized (hTTP2Stream) {
            return this.sendCallback != null ? this.sendCallback.getInvocationType() : Callback.super.getInvocationType();
        }
    }

    private Callback endWrite() {
        try (AutoLock l = this.lock.lock();){
            Callback callback = this.sendCallback;
            this.sendCallback = null;
            Callback callback2 = callback;
            return callback2;
        }
    }

    private void notifyNewStream(Stream stream) {
        Stream.Listener listener = this.listener;
        if (listener != null) {
            try {
                listener.onNewStream(stream);
            }
            catch (Throwable x) {
                LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
            }
        }
    }

    private void notifyBeforeData(Stream stream) {
        Stream.Listener listener = this.listener;
        if (listener != null) {
            try {
                listener.onBeforeData(stream);
            }
            catch (Throwable x) {
                LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
            }
        } else {
            stream.demand(1L);
        }
    }

    private void notifyDataDemanded(Stream stream, DataFrame frame, Callback callback) {
        Stream.Listener listener = this.listener;
        if (listener != null) {
            try {
                listener.onDataDemanded(stream, frame, callback);
            }
            catch (Throwable x) {
                LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
                callback.failed(x);
            }
        } else {
            callback.succeeded();
            stream.demand(1L);
        }
    }

    private void notifyReset(Stream stream, ResetFrame frame, Callback callback) {
        Stream.Listener listener = this.listener;
        if (listener != null) {
            try {
                listener.onReset(stream, frame, callback);
            }
            catch (Throwable x) {
                LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
                callback.failed(x);
            }
        } else {
            callback.succeeded();
        }
    }

    private boolean notifyIdleTimeout(Stream stream, Throwable failure) {
        Stream.Listener listener = this.listener;
        if (listener == null) {
            return true;
        }
        try {
            return listener.onIdleTimeout(stream, failure);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
            return true;
        }
    }

    private void notifyFailure(Stream stream, FailureFrame frame, Callback callback) {
        Stream.Listener listener = this.listener;
        if (listener != null) {
            try {
                listener.onFailure(stream, frame.getError(), frame.getReason(), frame.getFailure(), callback);
            }
            catch (Throwable x) {
                LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
                callback.failed(x);
            }
        } else {
            callback.succeeded();
        }
    }

    private void notifyClosed(Stream stream) {
        Stream.Listener listener = this.listener;
        if (listener == null) {
            return;
        }
        try {
            listener.onClosed(stream);
        }
        catch (Throwable x) {
            LOG.info("Failure while notifying listener {}", (Object)listener, (Object)x);
        }
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        out.append(this.toString()).append(System.lineSeparator());
    }

    public String toString() {
        return String.format("%s@%x#%d@%x{sendWindow=%s,recvWindow=%s,queue=%d,demand=%d,reset=%b/%b,%s,age=%d,attachment=%s}", this.getClass().getSimpleName(), this.hashCode(), this.getId(), this.session.hashCode(), this.sendWindow, this.recvWindow, this.dataSize(), this.demand(), this.localReset, this.remoteReset, this.closeState, NanoTime.millisSince(this.creationNanoTime), this.attachment);
    }

    private static class DataEntry {
        private final DataFrame frame;
        private final Callback callback;

        private DataEntry(DataFrame frame, Callback callback) {
            this.frame = frame;
            this.callback = callback;
        }
    }
}

