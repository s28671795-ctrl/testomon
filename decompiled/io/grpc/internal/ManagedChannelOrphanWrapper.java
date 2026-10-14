/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.ManagedChannel;
import io.grpc.internal.ForwardingManagedChannel;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

final class ManagedChannelOrphanWrapper
extends ForwardingManagedChannel {
    private static final ReferenceQueue<ManagedChannelOrphanWrapper> refqueue = new ReferenceQueue();
    private static final ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs = new ConcurrentHashMap<ManagedChannelReference, ManagedChannelReference>();
    private static final Logger logger = Logger.getLogger(ManagedChannelOrphanWrapper.class.getName());
    private final ManagedChannelReference phantom;

    ManagedChannelOrphanWrapper(ManagedChannel delegate) {
        this(delegate, refqueue, refs);
    }

    @VisibleForTesting
    ManagedChannelOrphanWrapper(ManagedChannel delegate, ReferenceQueue<ManagedChannelOrphanWrapper> refqueue, ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs) {
        super(delegate);
        this.phantom = new ManagedChannelReference(this, delegate, refqueue, refs);
    }

    @Override
    public ManagedChannel shutdown() {
        this.phantom.clearSafely();
        return super.shutdown();
    }

    @Override
    public ManagedChannel shutdownNow() {
        this.phantom.clearSafely();
        return super.shutdownNow();
    }

    @VisibleForTesting
    static final class ManagedChannelReference
    extends WeakReference<ManagedChannelOrphanWrapper> {
        private static final String ALLOCATION_SITE_PROPERTY_NAME = "io.grpc.ManagedChannel.enableAllocationTracking";
        private static final boolean ENABLE_ALLOCATION_TRACKING = Boolean.parseBoolean(System.getProperty("io.grpc.ManagedChannel.enableAllocationTracking", "true"));
        private static final RuntimeException missingCallSite = ManagedChannelReference.missingCallSite();
        private final ReferenceQueue<ManagedChannelOrphanWrapper> refqueue;
        private final ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs;
        private final String channelStr;
        private final Reference<RuntimeException> allocationSite;
        private final AtomicBoolean shutdown = new AtomicBoolean();

        ManagedChannelReference(ManagedChannelOrphanWrapper orphanable, ManagedChannel channel, ReferenceQueue<ManagedChannelOrphanWrapper> refqueue, ConcurrentMap<ManagedChannelReference, ManagedChannelReference> refs) {
            super(orphanable, refqueue);
            this.allocationSite = new SoftReference<RuntimeException>(ENABLE_ALLOCATION_TRACKING ? new RuntimeException("ManagedChannel allocation site") : missingCallSite);
            this.channelStr = channel.toString();
            this.refqueue = refqueue;
            this.refs = refs;
            this.refs.put(this, this);
            ManagedChannelReference.cleanQueue(refqueue);
        }

        @Override
        public void clear() {
            this.clearInternal();
            ManagedChannelReference.cleanQueue(this.refqueue);
        }

        private void clearSafely() {
            if (!this.shutdown.getAndSet(true)) {
                this.clear();
            }
        }

        private void clearInternal() {
            super.clear();
            this.refs.remove(this);
            this.allocationSite.clear();
        }

        private static RuntimeException missingCallSite() {
            RuntimeException e = new RuntimeException("ManagedChannel allocation site not recorded.  Set -Dio.grpc.ManagedChannel.enableAllocationTracking=true to enable it");
            e.setStackTrace(new StackTraceElement[0]);
            return e;
        }

        @VisibleForTesting
        static int cleanQueue(ReferenceQueue<ManagedChannelOrphanWrapper> refqueue) {
            ManagedChannelReference ref;
            int orphanedChannels = 0;
            while ((ref = (ManagedChannelReference)refqueue.poll()) != null) {
                RuntimeException maybeAllocationSite = ref.allocationSite.get();
                ref.clearInternal();
                if (ref.shutdown.get()) continue;
                ++orphanedChannels;
                Level level = Level.SEVERE;
                if (!logger.isLoggable(level)) continue;
                String fmt = "*~*~*~ Previous channel {0} was garbage collected without being shut down! ~*~*~*" + System.getProperty("line.separator") + "    Make sure to call shutdown()/shutdownNow()";
                LogRecord lr = new LogRecord(level, fmt);
                lr.setLoggerName(logger.getName());
                lr.setParameters(new Object[]{ref.channelStr});
                lr.setThrown(maybeAllocationSite);
                logger.log(lr);
            }
            return orphanedChannels;
        }
    }
}

