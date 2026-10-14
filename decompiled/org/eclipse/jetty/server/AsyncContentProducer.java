/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.server.ContentProducer;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.StaticException;
import org.eclipse.jetty.util.component.Destroyable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AsyncContentProducer
implements ContentProducer {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncContentProducer.class);
    private static final HttpInput.ErrorContent RECYCLED_ERROR_CONTENT = new HttpInput.ErrorContent(new StaticException("ContentProducer has been recycled"));
    private static final Throwable UNCONSUMED_CONTENT_EXCEPTION = new StaticException("Unconsumed content");
    private final AutoLock _lock = new AutoLock();
    private final HttpChannel _httpChannel;
    private HttpInput.Interceptor _interceptor;
    private HttpInput.Content _rawContent;
    private HttpInput.Content _transformedContent;
    private boolean _error;
    private long _firstByteNanoTime = Long.MIN_VALUE;
    private long _rawContentArrived;

    AsyncContentProducer(HttpChannel httpChannel) {
        this._httpChannel = httpChannel;
    }

    @Override
    public AutoLock lock() {
        return this._lock.lock();
    }

    @Override
    public void recycle() {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("recycling {}", (Object)this);
        }
        if (this._rawContent == null) {
            this._rawContent = RECYCLED_ERROR_CONTENT;
        } else if (!this._rawContent.isSpecial()) {
            throw new IllegalStateException("ContentProducer with unconsumed content cannot be recycled");
        }
        if (this._transformedContent == null) {
            this._transformedContent = RECYCLED_ERROR_CONTENT;
        } else if (!this._transformedContent.isSpecial()) {
            throw new IllegalStateException("ContentProducer with unconsumed content cannot be recycled");
        }
        if (this._interceptor instanceof Destroyable) {
            ((Destroyable)((Object)this._interceptor)).destroy();
        }
        this._interceptor = null;
    }

    @Override
    public void reopen() {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("reopening {}", (Object)this);
        }
        this._rawContent = null;
        this._transformedContent = null;
        this._error = false;
        this._firstByteNanoTime = Long.MIN_VALUE;
        this._rawContentArrived = 0L;
    }

    @Override
    public HttpInput.Interceptor getInterceptor() {
        this.assertLocked();
        return this._interceptor;
    }

    @Override
    public void setInterceptor(HttpInput.Interceptor interceptor) {
        this.assertLocked();
        this._interceptor = interceptor;
    }

    @Override
    public int available() {
        int available;
        this.assertLocked();
        HttpInput.Content content = this.nextTransformedContent();
        int n = available = content == null ? 0 : content.remaining();
        if (LOG.isDebugEnabled()) {
            LOG.debug("available = {} {}", (Object)available, (Object)this);
        }
        return available;
    }

    @Override
    public boolean hasContent() {
        boolean hasContent;
        this.assertLocked();
        boolean bl = hasContent = this._rawContent != null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("hasContent = {} {}", (Object)hasContent, (Object)this);
        }
        return hasContent;
    }

    @Override
    public boolean isError() {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("isError = {} {}", (Object)this._error, (Object)this);
        }
        return this._error;
    }

    @Override
    public void checkMinDataRate() {
        long period;
        this.assertLocked();
        long minRequestDataRate = this._httpChannel.getHttpConfiguration().getMinRequestDataRate();
        if (LOG.isDebugEnabled()) {
            LOG.debug("checkMinDataRate [m={},t={}] {}", minRequestDataRate, this._firstByteNanoTime, this);
        }
        if (minRequestDataRate > 0L && this._firstByteNanoTime != Long.MIN_VALUE && (period = NanoTime.since(this._firstByteNanoTime)) > 0L) {
            long minimumData = minRequestDataRate * TimeUnit.NANOSECONDS.toMillis(period) / TimeUnit.SECONDS.toMillis(1L);
            if (this.getRawContentArrived() < minimumData) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("checkMinDataRate check failed {}", (Object)this);
                }
                BadMessageException bad = new BadMessageException(408, String.format("Request content data rate < %d B/s", minRequestDataRate));
                if (this._httpChannel.getState().isResponseCommitted()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("checkMinDataRate aborting channel {}", (Object)this);
                    }
                    this._httpChannel.abort(bad);
                }
                this.failCurrentContent(bad);
                throw bad;
            }
        }
    }

    @Override
    public long getRawContentArrived() {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("getRawContentArrived = {} {}", (Object)this._rawContentArrived, (Object)this);
        }
        return this._rawContentArrived;
    }

    @Override
    public boolean consumeAll() {
        this.assertLocked();
        Throwable x = UNCONSUMED_CONTENT_EXCEPTION;
        if (LOG.isTraceEnabled()) {
            x = new StaticException("Unconsumed content", true);
            LOG.trace("consumeAll {}", (Object)this, (Object)x);
        }
        this.failCurrentContent(x);
        boolean atEof = this._httpChannel.failAllContent(x);
        if (LOG.isDebugEnabled()) {
            LOG.debug("failed all content of http channel EOF={} {}", (Object)atEof, (Object)this);
        }
        return atEof;
    }

    private void failCurrentContent(Throwable x) {
        if (this._transformedContent != null && !this._transformedContent.isSpecial()) {
            if (this._transformedContent != this._rawContent) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("failing currently held transformed content {} {}", (Object)x, (Object)this);
                }
                this._transformedContent.skip(this._transformedContent.remaining());
                this._transformedContent.failed(x);
            }
            this._transformedContent = null;
        }
        if (this._rawContent != null && !this._rawContent.isSpecial()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("failing currently held raw content {} {}", (Object)x, (Object)this);
            }
            this._rawContent.skip(this._rawContent.remaining());
            this._rawContent.failed(x);
            this._rawContent = null;
        }
        HttpInput.ErrorContent errorContent = new HttpInput.ErrorContent(x);
        this._transformedContent = errorContent;
        this._rawContent = errorContent;
    }

    @Override
    public boolean onContentProducible() {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("onContentProducible {}", (Object)this);
        }
        return this._httpChannel.getState().onReadReady();
    }

    @Override
    public HttpInput.Content nextContent() {
        this.assertLocked();
        HttpInput.Content content = this.nextTransformedContent();
        if (LOG.isDebugEnabled()) {
            LOG.debug("nextContent = {} {}", (Object)content, (Object)this);
        }
        if (content != null) {
            this._httpChannel.getState().onReadIdle();
        }
        return content;
    }

    @Override
    public void reclaim(HttpInput.Content content) {
        this.assertLocked();
        if (LOG.isDebugEnabled()) {
            LOG.debug("reclaim {} {}", (Object)content, (Object)this);
        }
        if (this._transformedContent == content) {
            content.succeeded();
            if (this._transformedContent == this._rawContent) {
                this._rawContent = null;
            }
            this._transformedContent = null;
        }
    }

    @Override
    public boolean isReady() {
        this.assertLocked();
        HttpInput.Content content = this.nextTransformedContent();
        if (content != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("isReady(), got transformed content {} {}", (Object)content, (Object)this);
            }
            return true;
        }
        this._httpChannel.getState().onReadUnready();
        while (this._httpChannel.needContent()) {
            content = this.nextTransformedContent();
            if (LOG.isDebugEnabled()) {
                LOG.debug("isReady(), got transformed content after needContent retry {} {}", (Object)content, (Object)this);
            }
            if (content != null) {
                this._httpChannel.getState().onContentAdded();
                return true;
            }
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug("isReady(), could not transform content after needContent retry {}", (Object)this);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("isReady(), no content for needContent retry {}", (Object)this);
        }
        return false;
    }

    boolean isUnready() {
        return this._httpChannel.getState().isInputUnready();
    }

    private HttpInput.Content nextTransformedContent() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("nextTransformedContent {}", (Object)this);
        }
        while (true) {
            if (this._transformedContent != null) {
                if (this._transformedContent.isSpecial() || !this._transformedContent.isEmpty()) {
                    if (this._transformedContent.getError() != null && !this._error) {
                        HttpInput.Content refreshedRawContent = this.produceRawContent();
                        if (refreshedRawContent != null) {
                            this._rawContent = this._transformedContent = refreshedRawContent;
                        }
                        boolean bl = this._error = this._rawContent.getError() != null;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("refreshed raw content: {} {}", (Object)this._rawContent, (Object)this);
                        }
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("transformed content not yet depleted, returning it {}", (Object)this);
                    }
                    return this._transformedContent;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("current transformed content depleted {}", (Object)this);
                }
                this._transformedContent.succeeded();
                this._transformedContent = null;
            }
            if (this._rawContent == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("producing new raw content {}", (Object)this);
                }
                this._rawContent = this.produceRawContent();
                if (this._rawContent == null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("channel has no new raw content {}", (Object)this);
                    }
                    return null;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("transforming raw content {}", (Object)this);
            }
            this.transformRawContent();
        }
    }

    private void transformRawContent() {
        if (this._interceptor != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("intercepting raw content {}", (Object)this);
            }
            this._transformedContent = this.intercept();
            if (this._transformedContent != null && this._transformedContent.isSpecial() && this._transformedContent != this._rawContent) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("interceptor generated a special content, _rawContent must become that special content {}", (Object)this);
                }
                this._rawContent.succeeded();
                this._rawContent = this._transformedContent;
                return;
            }
            if (this._transformedContent == null && this._rawContent.isEmpty() && !this._rawContent.isSpecial()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("interceptor generated a null content, recycling the empty raw content now {}", (Object)this);
                }
                this._rawContent.succeeded();
                this._rawContent = null;
                return;
            }
            if (this._transformedContent == this._rawContent && this._rawContent.isEmpty() && !this._rawContent.isSpecial()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("interceptor returned the raw content, recycle the empty raw content now {}", (Object)this);
                }
                this._rawContent.succeeded();
                this._transformedContent = null;
                this._rawContent = null;
            }
        } else {
            if (this._rawContent.isEmpty() && !this._rawContent.isSpecial()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("recycling the empty raw content now {}", (Object)this);
                }
                this._rawContent.succeeded();
                this._rawContent = null;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("no interceptor, transformed content is raw content {}", (Object)this);
            }
            this._transformedContent = this._rawContent;
        }
    }

    private HttpInput.Content intercept() {
        try {
            HttpInput.Content content = this._interceptor.readFrom(this._rawContent);
            if (content != null && content.isSpecial() && !this._rawContent.isSpecial()) {
                Throwable error = content.getError();
                if (error != null) {
                    this._error = true;
                    if (this._httpChannel.getResponse().isCommitted()) {
                        this._httpChannel.abort(error);
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("interceptor generated special content {}", (Object)this);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("intercepted raw content {}", (Object)this);
            }
            return content;
        }
        catch (Throwable x) {
            IOException failure = new IOException("Bad content", x);
            this.failCurrentContent(failure);
            this._error = true;
            Response response = this._httpChannel.getResponse();
            if (response.isCommitted()) {
                this._httpChannel.abort(failure);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("interceptor threw exception {}", (Object)this, (Object)x);
            }
            return this._transformedContent;
        }
    }

    private HttpInput.Content produceRawContent() {
        HttpInput.Content content = this._httpChannel.produceContent();
        if (content != null) {
            this._rawContentArrived += (long)content.remaining();
            if (this._firstByteNanoTime == Long.MIN_VALUE) {
                this._firstByteNanoTime = NanoTime.now();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("produceRawContent updated rawContentArrived to {} and firstByteTimeStamp to {} {}", this._rawContentArrived, this._firstByteNanoTime, this);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("produceRawContent produced {} {}", (Object)content, (Object)this);
        }
        return content;
    }

    private void assertLocked() {
        if (!this._lock.isHeldByCurrentThread()) {
            throw new IllegalStateException("ContentProducer must be called within lock scope");
        }
    }

    public String toString() {
        return String.format("%s@%x[r=%s,t=%s,i=%s,error=%b,c=%s]", this.getClass().getSimpleName(), this.hashCode(), this._rawContent, this._transformedContent, this._interceptor, this._error, this._httpChannel);
    }

    LockedSemaphore newLockedSemaphore() {
        return new LockedSemaphore();
    }

    class LockedSemaphore {
        private final Condition _condition;
        private int _permits;

        private LockedSemaphore() {
            this._condition = AsyncContentProducer.this._lock.newCondition();
        }

        void assertLocked() {
            if (!AsyncContentProducer.this._lock.isHeldByCurrentThread()) {
                throw new IllegalStateException("LockedSemaphore must be called within lock scope");
            }
        }

        void drainPermits() {
            this._permits = 0;
        }

        void acquire() throws InterruptedException {
            while (this._permits == 0) {
                this._condition.await();
            }
            --this._permits;
        }

        void release() {
            ++this._permits;
            this._condition.signal();
        }

        public String toString() {
            return this.getClass().getSimpleName() + " permits=" + this._permits;
        }
    }
}

