/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import org.eclipse.jetty.http2.FlowControlStrategy;
import org.eclipse.jetty.http2.HTTP2Session;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2Flusher
extends IteratingCallback
implements Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2Flusher.class);
    private static final ByteBuffer[] EMPTY_BYTE_BUFFERS = new ByteBuffer[0];
    private final AutoLock lock = new AutoLock();
    private final Queue<WindowEntry> windows = new ArrayDeque<WindowEntry>();
    private final Deque<Entry> entries = new ArrayDeque<Entry>();
    private final Queue<Entry> pendingEntries = new ArrayDeque<Entry>();
    private final Collection<Entry> processedEntries = new ArrayList<Entry>();
    private final HTTP2Session session;
    private final ByteBufferPool.Lease lease;
    private Invocable.InvocationType invocationType = Invocable.InvocationType.NON_BLOCKING;
    private Throwable terminated;
    private Entry stalledEntry;

    public HTTP2Flusher(HTTP2Session session) {
        this.session = session;
        this.lease = new ByteBufferPool.Lease(session.getGenerator().getByteBufferPool());
    }

    @Override
    public Invocable.InvocationType getInvocationType() {
        return this.invocationType;
    }

    public void window(IStream stream, WindowUpdateFrame frame) {
        Throwable closed;
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            if (closed == null) {
                this.windows.offer(new WindowEntry(stream, frame));
            }
        }
        if (closed == null) {
            this.iterate();
        }
    }

    public boolean prepend(Entry entry) {
        Throwable closed;
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            if (closed == null) {
                this.entries.offerFirst(entry);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Prepended {}, entries={}", (Object)entry, (Object)this.entries.size());
                }
            }
        }
        if (closed == null) {
            return true;
        }
        this.closed(entry, closed);
        return false;
    }

    public boolean append(Entry entry) {
        Throwable closed;
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            if (closed == null) {
                this.entries.offer(entry);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Appended {}, entries={}", (Object)entry, (Object)this.entries.size());
                }
            }
        }
        if (closed == null) {
            return true;
        }
        this.closed(entry, closed);
        return false;
    }

    public boolean append(List<Entry> list) {
        Throwable closed;
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            if (closed == null) {
                list.forEach(this.entries::offer);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Appended {}, entries={}", (Object)list, (Object)this.entries.size());
                }
            }
        }
        if (closed == null) {
            return true;
        }
        list.forEach(entry -> this.closed((Entry)entry, closed));
        return false;
    }

    private int getWindowQueueSize() {
        try (AutoLock l = this.lock.lock();){
            int n = this.windows.size();
            return n;
        }
    }

    public int getFrameQueueSize() {
        try (AutoLock l = this.lock.lock();){
            int n = this.entries.size();
            return n;
        }
    }

    @Override
    protected IteratingCallback.Action process() throws Throwable {
        block29: {
            int writeThreshold;
            Entry entry;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Flushing {}", (Object)this.session);
            }
            try (AutoLock l = this.lock.lock();){
                WindowEntry windowEntry;
                if (this.terminated != null) {
                    throw this.terminated;
                }
                while ((windowEntry = this.windows.poll()) != null) {
                    windowEntry.perform();
                }
                while ((entry = this.entries.poll()) != null) {
                    this.pendingEntries.offer(entry);
                }
            }
            if (this.pendingEntries.isEmpty()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Flushed {}", (Object)this.session);
                }
                return IteratingCallback.Action.IDLE;
            }
            do {
                boolean progress = false;
                if (this.pendingEntries.isEmpty()) break block29;
                Iterator pending = this.pendingEntries.iterator();
                while (pending.hasNext()) {
                    entry = (Entry)pending.next();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Processing {}", (Object)entry);
                    }
                    if (entry.shouldBeDropped()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Dropped {}", (Object)entry);
                        }
                        entry.failed(new EofException("dropped"));
                        pending.remove();
                        continue;
                    }
                    try {
                        if (entry.generate(this.lease)) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Generated {} frame bytes for {}", (Object)entry.getFrameBytesGenerated(), (Object)entry);
                            }
                            progress = true;
                            if (!this.processedEntries.contains(entry)) {
                                this.processedEntries.add(entry);
                                this.invocationType = Invocable.combine(this.invocationType, Invocable.getInvocationType(entry.getCallback()));
                            }
                            if (entry.getDataBytesRemaining() != 0) continue;
                            pending.remove();
                            continue;
                        }
                        if (this.session.getSendWindow() > 0 || this.stalledEntry != null) continue;
                        this.stalledEntry = entry;
                        if (!LOG.isDebugEnabled()) continue;
                        LOG.debug("Flow control stalled at {}", (Object)entry);
                    }
                    catch (HpackException.StreamException failure) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Failure generating {}", (Object)entry, (Object)failure);
                        }
                        entry.failed(failure);
                        pending.remove();
                    }
                    catch (Throwable failure) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Failure generating {}", (Object)entry, (Object)failure);
                        }
                        this.failed(failure);
                        return IteratingCallback.Action.SUCCEEDED;
                    }
                }
                if (!progress || this.stalledEntry != null) break block29;
                writeThreshold = this.session.getWriteThreshold();
            } while (this.lease.getTotalLength() < (long)writeThreshold);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Write threshold {} exceeded", (Object)writeThreshold);
            }
        }
        List<ByteBuffer> byteBuffers = this.lease.getByteBuffers();
        if (byteBuffers.isEmpty()) {
            this.finish();
            return IteratingCallback.Action.IDLE;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Writing {} buffers ({} bytes) - entries processed/pending {}/{}: {}/{}", byteBuffers.size(), this.lease.getTotalLength(), this.processedEntries.size(), this.pendingEntries.size(), this.processedEntries, this.pendingEntries);
        }
        this.session.getEndPoint().write(this, byteBuffers.toArray(EMPTY_BYTE_BUFFERS));
        return IteratingCallback.Action.SCHEDULED;
    }

    void onFlushed(long bytes) throws IOException {
        for (Entry entry : this.processedEntries) {
            bytes = entry.onFlushed(bytes);
        }
    }

    @Override
    public void succeeded() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Written {} buffers - entries processed/pending {}/{}: {}/{}", this.lease.getByteBuffers().size(), this.processedEntries.size(), this.pendingEntries.size(), this.processedEntries, this.pendingEntries);
        }
        this.finish();
        super.succeeded();
    }

    private void finish() {
        this.lease.recycle();
        this.processedEntries.forEach(Callback.Nested::succeeded);
        this.processedEntries.clear();
        this.invocationType = Invocable.InvocationType.NON_BLOCKING;
        if (this.stalledEntry != null) {
            Entry entry;
            int size = this.pendingEntries.size();
            for (int i = 0; i < size && (entry = this.pendingEntries.peek()) != this.stalledEntry; ++i) {
                this.pendingEntries.poll();
                this.pendingEntries.offer(entry);
            }
            this.stalledEntry = null;
        }
    }

    @Override
    protected void onCompleteSuccess() {
        throw new IllegalStateException();
    }

    @Override
    protected void onCompleteFailure(Throwable x) {
        HashSet<Entry> allEntries;
        Throwable closed;
        this.lease.recycle();
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            this.terminated = x;
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("%s, entries processed/pending/queued=%d/%d/%d", closed != null ? "Closing" : "Failing", this.processedEntries.size(), this.pendingEntries.size(), this.entries.size()), x);
            }
            allEntries = new HashSet<Entry>(this.entries);
            this.entries.clear();
        }
        allEntries.addAll(this.processedEntries);
        this.processedEntries.clear();
        allEntries.addAll(this.pendingEntries);
        this.pendingEntries.clear();
        allEntries.forEach(entry -> entry.failed(x));
        if (closed == null) {
            this.session.onWriteFailure(x);
        }
    }

    void terminate(Throwable cause) {
        Throwable closed;
        try (AutoLock l = this.lock.lock();){
            closed = this.terminated;
            this.terminated = cause;
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} {}", (Object)(closed != null ? "Terminated" : "Terminating"), (Object)this);
            }
        }
        if (closed == null) {
            this.iterate();
        }
    }

    private void closed(Entry entry, Throwable failure) {
        entry.failed(failure);
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        out.append(this.toString()).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return String.format("%s[window_queue=%d,frame_queue=%d,processed/pending=%d/%d]", super.toString(), this.getWindowQueueSize(), this.getFrameQueueSize(), this.processedEntries.size(), this.pendingEntries.size());
    }

    private class WindowEntry {
        private final IStream stream;
        private final WindowUpdateFrame frame;

        public WindowEntry(IStream stream, WindowUpdateFrame frame) {
            this.stream = stream;
            this.frame = frame;
        }

        public void perform() {
            FlowControlStrategy flowControl = HTTP2Flusher.this.session.getFlowControlStrategy();
            flowControl.onWindowUpdate(HTTP2Flusher.this.session, this.stream, this.frame);
        }
    }

    public static abstract class Entry
    extends Callback.Nested {
        protected final Frame frame;
        protected final IStream stream;

        protected Entry(Frame frame, IStream stream, Callback callback) {
            super(callback);
            this.frame = frame;
            this.stream = stream;
        }

        public abstract int getFrameBytesGenerated();

        public int getDataBytesRemaining() {
            return 0;
        }

        protected abstract boolean generate(ByteBufferPool.Lease var1) throws HpackException;

        public abstract long onFlushed(long var1) throws IOException;

        boolean hasHighPriority() {
            return false;
        }

        @Override
        public void failed(Throwable x) {
            if (this.stream != null) {
                this.stream.close();
                this.stream.getSession().removeStream(this.stream);
            }
            super.failed(x);
        }

        private boolean shouldBeDropped() {
            switch (this.frame.getType()) {
                case PRIORITY: 
                case SETTINGS: 
                case PING: 
                case GO_AWAY: 
                case WINDOW_UPDATE: 
                case PREFACE: 
                case DISCONNECT: {
                    return false;
                }
                case DATA: 
                case HEADERS: 
                case PUSH_PROMISE: 
                case CONTINUATION: 
                case RST_STREAM: {
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            if (this.frame.getType() == FrameType.RST_STREAM) {
                return this.stream != null && this.stream.isLocal() && !this.stream.isCommitted();
            }
            if (this.stream == null) {
                return true;
            }
            return this.stream.isResetOrFailed();
        }

        void commit() {
            if (this.stream != null) {
                this.stream.commit();
            }
        }

        public String toString() {
            return this.frame.toString();
        }
    }
}

