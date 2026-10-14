/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.io;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.LeakDetector;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class LeakTrackingByteBufferPool
extends ContainerLifeCycle
implements ByteBufferPool {
    private static final Logger LOG = LoggerFactory.getLogger(LeakTrackingByteBufferPool.class);
    private final LeakDetector<ByteBuffer> leakDetector = new LeakDetector<ByteBuffer>(){

        @Override
        public String id(ByteBuffer resource) {
            return BufferUtil.toIDString(resource);
        }

        @Override
        protected void leaked(LeakDetector.LeakInfo leakInfo) {
            LeakTrackingByteBufferPool.this.leaked.incrementAndGet();
            LeakTrackingByteBufferPool.this.leaked(leakInfo);
        }
    };
    private final AtomicLong leakedAcquires = new AtomicLong(0L);
    private final AtomicLong leakedReleases = new AtomicLong(0L);
    private final AtomicLong leakedRemoves = new AtomicLong(0L);
    private final AtomicLong leaked = new AtomicLong(0L);
    private final ByteBufferPool delegate;

    public LeakTrackingByteBufferPool(ByteBufferPool delegate) {
        this.delegate = delegate;
        this.addBean(this.leakDetector);
        this.addBean(delegate);
    }

    @Override
    public RetainableByteBufferPool asRetainableByteBufferPool() {
        return this.delegate.asRetainableByteBufferPool();
    }

    @Override
    public ByteBuffer acquire(int size, boolean direct) {
        ByteBuffer buffer = this.delegate.acquire(size, direct);
        boolean acquired = this.leakDetector.acquired(buffer);
        if (!acquired) {
            this.leakedAcquires.incrementAndGet();
            if (LOG.isDebugEnabled()) {
                LOG.debug("ByteBuffer leaked acquire for id {}", (Object)this.leakDetector.id(buffer), (Object)new Throwable("acquire"));
            }
        }
        return buffer;
    }

    @Override
    public void release(ByteBuffer buffer) {
        if (buffer == null) {
            return;
        }
        boolean released = this.leakDetector.released(buffer);
        if (!released) {
            this.leakedReleases.incrementAndGet();
            if (LOG.isDebugEnabled()) {
                LOG.debug("ByteBuffer leaked release for id {}", (Object)this.leakDetector.id(buffer), (Object)new Throwable("release"));
            }
        }
        this.delegate.release(buffer);
    }

    @Override
    public void remove(ByteBuffer buffer) {
        if (buffer == null) {
            return;
        }
        boolean released = this.leakDetector.released(buffer);
        if (!released) {
            this.leakedRemoves.incrementAndGet();
            if (LOG.isDebugEnabled()) {
                LOG.debug("ByteBuffer leaked remove for id {}", (Object)this.leakDetector.id(buffer), (Object)new Throwable("remove"));
            }
        }
        this.delegate.remove(buffer);
    }

    @ManagedAttribute(value="Clears the tracking data")
    public void clearTracking() {
        this.leakedAcquires.set(0L);
        this.leakedReleases.set(0L);
    }

    @ManagedAttribute(value="The number of acquires that produced a leak")
    public long getLeakedAcquires() {
        return this.leakedAcquires.get();
    }

    @ManagedAttribute(value="The number of releases that produced a leak")
    public long getLeakedReleases() {
        return this.leakedReleases.get();
    }

    @ManagedAttribute(value="The number of removes that produced a leak")
    public long getLeakedRemoves() {
        return this.leakedRemoves.get();
    }

    @ManagedAttribute(value="The number of resources that were leaked")
    public long getLeakedResources() {
        return this.leaked.get();
    }

    protected void leaked(LeakDetector.LeakInfo leakInfo) {
        LOG.warn("ByteBuffer {} leaked at: {}", (Object)leakInfo.getResourceDescription(), (Object)leakInfo.getStackFrames());
    }
}

