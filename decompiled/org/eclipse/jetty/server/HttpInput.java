/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;
import org.eclipse.jetty.server.AsyncContentProducer;
import org.eclipse.jetty.server.BlockingContentProducer;
import org.eclipse.jetty.server.ContentProducer;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.component.Destroyable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInput
extends ServletInputStream
implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(HttpInput.class);
    private final byte[] _oneByteBuffer = new byte[1];
    private final BlockingContentProducer _blockingContentProducer;
    private final AsyncContentProducer _asyncContentProducer;
    private final HttpChannelState _channelState;
    private final LongAdder _contentConsumed = new LongAdder();
    private volatile ContentProducer _contentProducer;
    private volatile boolean _consumedEof;
    private volatile ReadListener _readListener;

    public HttpInput(HttpChannelState state) {
        this._channelState = state;
        this._asyncContentProducer = new AsyncContentProducer(state.getHttpChannel());
        this._blockingContentProducer = new BlockingContentProducer(this._asyncContentProducer);
        this._contentProducer = this._blockingContentProducer;
    }

    public void recycle() {
        try (AutoLock lock = this._contentProducer.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("recycle {}", (Object)this);
            }
            this._blockingContentProducer.recycle();
        }
    }

    public void reopen() {
        try (AutoLock lock = this._contentProducer.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("reopen {}", (Object)this);
            }
            this._blockingContentProducer.reopen();
            this._contentProducer = this._blockingContentProducer;
            this._consumedEof = false;
            this._readListener = null;
            this._contentConsumed.reset();
        }
    }

    public Interceptor getInterceptor() {
        try (AutoLock lock = this._contentProducer.lock();){
            Interceptor interceptor = this._contentProducer.getInterceptor();
            return interceptor;
        }
    }

    public void setInterceptor(Interceptor interceptor) {
        try (AutoLock lock = this._contentProducer.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("setting interceptor to {} on {}", (Object)interceptor, (Object)this);
            }
            this._contentProducer.setInterceptor(interceptor);
        }
    }

    public void addInterceptor(Interceptor interceptor) {
        try (AutoLock lock = this._contentProducer.lock();){
            Interceptor currentInterceptor = this._contentProducer.getInterceptor();
            if (currentInterceptor == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("adding single interceptor: {} on {}", (Object)interceptor, (Object)this);
                }
                this._contentProducer.setInterceptor(interceptor);
            } else {
                ChainedInterceptor chainedInterceptor = new ChainedInterceptor(currentInterceptor, interceptor);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("adding chained interceptor: {} on {}", (Object)chainedInterceptor, (Object)this);
                }
                this._contentProducer.setInterceptor(chainedInterceptor);
            }
        }
    }

    private int get(Content content, byte[] bytes, int offset, int length) {
        int consumed = content.get(bytes, offset, length);
        this._contentConsumed.add(consumed);
        return consumed;
    }

    private int get(Content content, ByteBuffer des) {
        int capacity = des.remaining();
        ByteBuffer src = content.getByteBuffer();
        if (src.remaining() > capacity) {
            int limit = src.limit();
            src.limit(src.position() + capacity);
            des.put(src);
            src.limit(limit);
        } else {
            des.put(src);
        }
        int consumed = capacity - des.remaining();
        this._contentConsumed.add(consumed);
        return consumed;
    }

    public long getContentConsumed() {
        return this._contentConsumed.sum();
    }

    public long getContentReceived() {
        try (AutoLock lock = this._contentProducer.lock();){
            long l = this._contentProducer.getRawContentArrived();
            return l;
        }
    }

    public boolean consumeAll() {
        try (AutoLock lock = this._contentProducer.lock();){
            boolean atEof;
            if (LOG.isDebugEnabled()) {
                LOG.debug("consumeAll {}", (Object)this);
            }
            if (atEof = this._contentProducer.consumeAll()) {
                this._consumedEof = true;
            }
            if (this.isFinished()) {
                boolean bl = !this.isError();
                return bl;
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean isError() {
        try (AutoLock lock = this._contentProducer.lock();){
            boolean error = this._contentProducer.isError();
            if (LOG.isDebugEnabled()) {
                LOG.debug("isError={} {}", (Object)error, (Object)this);
            }
            boolean bl = error;
            return bl;
        }
    }

    public boolean isAsync() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("isAsync read listener {} {}", (Object)this._readListener, (Object)this);
        }
        return this._readListener != null;
    }

    @Override
    public boolean isFinished() {
        boolean finished = this._consumedEof;
        if (LOG.isDebugEnabled()) {
            LOG.debug("isFinished={} {}", (Object)finished, (Object)this);
        }
        return finished;
    }

    @Override
    public boolean isReady() {
        try (AutoLock lock = this._contentProducer.lock();){
            boolean ready2 = this._contentProducer.isReady();
            if (LOG.isDebugEnabled()) {
                LOG.debug("isReady={} {}", (Object)ready2, (Object)this);
            }
            boolean bl = ready2;
            return bl;
        }
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setting read listener to {} {}", (Object)readListener, (Object)this);
        }
        if (this._readListener != null) {
            throw new IllegalStateException("ReadListener already set");
        }
        if (!this._channelState.isAsyncStarted()) {
            throw new IllegalStateException("Async not started");
        }
        this._readListener = Objects.requireNonNull(readListener);
        this._contentProducer = this._asyncContentProducer;
        if (this.isReady() && this._channelState.onReadEof()) {
            this.scheduleReadListenerNotification();
        }
    }

    public boolean onContentProducible() {
        try (AutoLock lock = this._contentProducer.lock();){
            boolean bl = this._contentProducer.onContentProducible();
            return bl;
        }
    }

    @Override
    public int read() throws IOException {
        try (AutoLock lock = this._contentProducer.lock();){
            int read = this.read(this._oneByteBuffer, 0, 1);
            if (read == 0) {
                throw new IOException("unready read=0");
            }
            int n = read < 0 ? -1 : this._oneByteBuffer[0] & 0xFF;
            return n;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.read(null, b, off, len);
    }

    public int read(ByteBuffer buffer) throws IOException {
        return this.read(buffer, null, -1, -1);
    }

    private int read(ByteBuffer buffer, byte[] b, int off, int len) throws IOException {
        try (AutoLock lock = this._contentProducer.lock();){
            this._contentProducer.checkMinDataRate();
            Content content = this._contentProducer.nextContent();
            if (content == null) {
                throw new IllegalStateException("read on unready input");
            }
            if (!content.isSpecial()) {
                int read;
                int n = read = buffer == null ? this.get(content, b, off, len) : this.get(content, buffer);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("read produced {} byte(s) {}", (Object)read, (Object)this);
                }
                if (content.isEmpty()) {
                    this._contentProducer.reclaim(content);
                }
                int n2 = read;
                return n2;
            }
            Throwable error = content.getError();
            if (LOG.isDebugEnabled()) {
                LOG.debug("read error={} {}", (Object)error, (Object)this);
            }
            if (error != null) {
                if (error instanceof IOException) {
                    throw (IOException)error;
                }
                throw new IOException(error);
            }
            if (content.isEof()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("read at EOF, setting consumed EOF to true {}", (Object)this);
                }
                this._consumedEof = true;
                if (this.onContentProducible()) {
                    this.scheduleReadListenerNotification();
                }
                int n = -1;
                return n;
            }
            throw new AssertionError((Object)"no data, no error and not EOF");
        }
    }

    private void scheduleReadListenerNotification() {
        HttpChannel channel = this._channelState.getHttpChannel();
        channel.execute(channel);
    }

    public boolean hasContent() {
        try (AutoLock lock = this._contentProducer.lock();){
            boolean hasContent = this._contentProducer.hasContent();
            if (LOG.isDebugEnabled()) {
                LOG.debug("hasContent={} {}", (Object)hasContent, (Object)this);
            }
            boolean bl = hasContent;
            return bl;
        }
    }

    @Override
    public int available() {
        try (AutoLock lock = this._contentProducer.lock();){
            int available = this._contentProducer.available();
            if (LOG.isDebugEnabled()) {
                LOG.debug("available={} {}", (Object)available, (Object)this);
            }
            int n = available;
            return n;
        }
    }

    @Override
    public void run() {
        Content content;
        try (AutoLock lock = this._contentProducer.lock();){
            if (!this._contentProducer.isReady()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("running but not ready {}", (Object)this);
                }
                return;
            }
            content = this._contentProducer.nextContent();
            if (LOG.isDebugEnabled()) {
                LOG.debug("running on content {} {}", (Object)content, (Object)this);
            }
        }
        if (this._readListener == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("running without a read listener {}", (Object)this);
            }
            this.onContentProducible();
            return;
        }
        if (content.isSpecial()) {
            Throwable error = content.getError();
            if (error != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("running error={} {}", (Object)error, (Object)this);
                }
                this._channelState.getHttpChannel().getResponse().getHttpFields().add(HttpConnection.CONNECTION_CLOSE);
                this._readListener.onError(error);
            } else if (content.isEof()) {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("running at EOF {}", (Object)this);
                    }
                    this._readListener.onAllDataRead();
                }
                catch (Throwable x) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("running failed onAllDataRead {}", (Object)this, (Object)x);
                    }
                    this._readListener.onError(x);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("running has content {}", (Object)this);
            }
            try {
                this._readListener.onDataAvailable();
            }
            catch (Throwable x) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("running failed onDataAvailable {}", (Object)this, (Object)x);
                }
                this._readListener.onError(x);
            }
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + "@" + this.hashCode() + " cs=" + this._channelState + " cp=" + this._contentProducer + " eof=" + this._consumedEof;
    }

    public static interface Interceptor {
        public Content readFrom(Content var1);
    }

    private static class ChainedInterceptor
    implements Interceptor,
    Destroyable {
        private final Interceptor _prev;
        private final Interceptor _next;

        ChainedInterceptor(Interceptor prev, Interceptor next) {
            this._prev = prev;
            this._next = next;
        }

        Interceptor getPrev() {
            return this._prev;
        }

        Interceptor getNext() {
            return this._next;
        }

        @Override
        public Content readFrom(Content content) {
            Content c = this.getPrev().readFrom(content);
            if (c == null) {
                return null;
            }
            return this.getNext().readFrom(c);
        }

        @Override
        public void destroy() {
            if (this._prev instanceof Destroyable) {
                ((Destroyable)((Object)this._prev)).destroy();
            }
            if (this._next instanceof Destroyable) {
                ((Destroyable)((Object)this._next)).destroy();
            }
        }

        public String toString() {
            return this.getClass().getSimpleName() + "@" + this.hashCode() + " [p=" + this._prev + ",n=" + this._next + "]";
        }
    }

    public static class Content
    implements Callback {
        protected final ByteBuffer _content;

        public Content(ByteBuffer content) {
            this._content = content;
        }

        public ByteBuffer getByteBuffer() {
            return this._content;
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.NON_BLOCKING;
        }

        public int get(byte[] buffer, int offset, int length) {
            length = Math.min(this._content.remaining(), length);
            this._content.get(buffer, offset, length);
            return length;
        }

        public int skip(int length) {
            length = Math.min(this._content.remaining(), length);
            this._content.position(this._content.position() + length);
            return length;
        }

        public boolean hasContent() {
            return this._content.hasRemaining();
        }

        public int remaining() {
            return this._content.remaining();
        }

        public boolean isEmpty() {
            return !this._content.hasRemaining();
        }

        public boolean isSpecial() {
            return false;
        }

        public boolean isEof() {
            return false;
        }

        public Throwable getError() {
            return null;
        }

        public String toString() {
            return String.format("%s@%x{%s,spc=%s,eof=%s,err=%s}", this.getClass().getSimpleName(), this.hashCode(), BufferUtil.toDetailString(this._content), this.isSpecial(), this.isEof(), this.getError());
        }
    }

    public static final class ErrorContent
    extends SpecialContent {
        private final Throwable _error;

        public ErrorContent(Throwable error) {
            this._error = error;
        }

        @Override
        public Throwable getError() {
            return this._error;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " [" + this._error + "]";
        }
    }

    public static final class EofContent
    extends SpecialContent {
        @Override
        public boolean isEof() {
            return true;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    public static abstract class SpecialContent
    extends Content {
        public SpecialContent() {
            super(null);
        }

        @Override
        public final ByteBuffer getByteBuffer() {
            throw new IllegalStateException(this + " has no buffer");
        }

        @Override
        public final int get(byte[] buffer, int offset, int length) {
            throw new IllegalStateException(this + " has no buffer");
        }

        @Override
        public final int skip(int length) {
            return 0;
        }

        @Override
        public final boolean hasContent() {
            return false;
        }

        @Override
        public final int remaining() {
            return 0;
        }

        @Override
        public final boolean isEmpty() {
            return true;
        }

        @Override
        public final boolean isSpecial() {
            return true;
        }
    }

    public static class WrappingContent
    extends Content {
        private final Content _delegate;
        private final boolean _eof;

        public WrappingContent(Content delegate, boolean eof) {
            super(delegate.getByteBuffer());
            this._delegate = delegate;
            this._eof = eof;
        }

        @Override
        public boolean isEof() {
            return this._eof;
        }

        @Override
        public void succeeded() {
            this._delegate.succeeded();
        }

        @Override
        public void failed(Throwable x) {
            this._delegate.failed(x);
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return this._delegate.getInvocationType();
        }
    }
}

