/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import org.eclipse.jetty.server.AsyncContentProducer;
import org.eclipse.jetty.server.ContentProducer;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BlockingContentProducer
implements ContentProducer {
    private static final Logger LOG = LoggerFactory.getLogger(BlockingContentProducer.class);
    private final AsyncContentProducer _asyncContentProducer;
    private final AsyncContentProducer.LockedSemaphore _semaphore;

    BlockingContentProducer(AsyncContentProducer delegate) {
        this._asyncContentProducer = delegate;
        this._semaphore = this._asyncContentProducer.newLockedSemaphore();
    }

    @Override
    public AutoLock lock() {
        return this._asyncContentProducer.lock();
    }

    @Override
    public void recycle() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("recycling {}", (Object)this);
        }
        this._asyncContentProducer.recycle();
    }

    @Override
    public void reopen() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("reopening {}", (Object)this);
        }
        this._asyncContentProducer.reopen();
        this._semaphore.drainPermits();
    }

    @Override
    public int available() {
        return this._asyncContentProducer.available();
    }

    @Override
    public boolean hasContent() {
        return this._asyncContentProducer.hasContent();
    }

    @Override
    public boolean isError() {
        return this._asyncContentProducer.isError();
    }

    @Override
    public void checkMinDataRate() {
        this._asyncContentProducer.checkMinDataRate();
    }

    @Override
    public long getRawContentArrived() {
        return this._asyncContentProducer.getRawContentArrived();
    }

    @Override
    public boolean consumeAll() {
        boolean eof = this._asyncContentProducer.consumeAll();
        this._semaphore.release();
        return eof;
    }

    @Override
    public HttpInput.Content nextContent() {
        while (true) {
            HttpInput.Content content = this._asyncContentProducer.nextContent();
            if (LOG.isDebugEnabled()) {
                LOG.debug("nextContent async producer returned {}", (Object)content);
            }
            if (content != null) {
                return content;
            }
            if (this._asyncContentProducer.isReady()) {
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("nextContent async producer is ready, retrying");
                continue;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("nextContent async producer is not ready, waiting on semaphore {}", (Object)this._semaphore);
            }
            try {
                this._semaphore.acquire();
            }
            catch (InterruptedException e) {
                return new HttpInput.ErrorContent(e);
            }
        }
    }

    @Override
    public void reclaim(HttpInput.Content content) {
        this._asyncContentProducer.reclaim(content);
    }

    @Override
    public boolean isReady() {
        boolean ready2;
        boolean bl = ready2 = this.available() > 0;
        if (LOG.isDebugEnabled()) {
            LOG.debug("isReady = {}", (Object)ready2);
        }
        return ready2;
    }

    @Override
    public HttpInput.Interceptor getInterceptor() {
        return this._asyncContentProducer.getInterceptor();
    }

    @Override
    public void setInterceptor(HttpInput.Interceptor interceptor) {
        this._asyncContentProducer.setInterceptor(interceptor);
    }

    @Override
    public boolean onContentProducible() {
        this._semaphore.assertLocked();
        boolean unready = this._asyncContentProducer.isUnready();
        if (LOG.isDebugEnabled()) {
            LOG.debug("onContentProducible releasing semaphore {} unready={}", (Object)this._semaphore, (Object)unready);
        }
        if (unready) {
            this._semaphore.release();
        }
        return false;
    }
}

