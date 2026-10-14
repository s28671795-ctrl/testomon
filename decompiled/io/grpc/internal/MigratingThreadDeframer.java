/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.Decompressor;
import io.grpc.internal.ApplicationThreadDeframerListener;
import io.grpc.internal.ForwardingDeframerListener;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.GzipInflatingBuffer;
import io.grpc.internal.MessageDeframer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.SquelchLateMessagesAvailableDeframerListener;
import io.grpc.internal.StreamListener;
import io.grpc.internal.ThreadOptimizedDeframer;
import io.perfmark.Link;
import io.perfmark.PerfMark;
import io.perfmark.TaskCloseable;
import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

final class MigratingThreadDeframer
implements ThreadOptimizedDeframer {
    private final MessageDeframer.Listener transportListener;
    private final ApplicationThreadDeframerListener appListener;
    private final MigratingDeframerListener migratingListener;
    private final ApplicationThreadDeframerListener.TransportExecutor transportExecutor;
    private final MessageDeframer deframer;
    private final DeframeMessageProducer messageProducer = new DeframeMessageProducer();
    private final Object lock = new Object();
    @GuardedBy(value="lock")
    private boolean deframerOnTransportThread;
    @GuardedBy(value="lock")
    private final Queue<Op> opQueue = new ArrayDeque<Op>();
    @GuardedBy(value="lock")
    private boolean messageProducerEnqueued;

    public MigratingThreadDeframer(MessageDeframer.Listener listener, ApplicationThreadDeframerListener.TransportExecutor transportExecutor, MessageDeframer deframer) {
        this.transportListener = new SquelchLateMessagesAvailableDeframerListener(Preconditions.checkNotNull(listener, "listener"));
        this.transportExecutor = Preconditions.checkNotNull(transportExecutor, "transportExecutor");
        this.appListener = new ApplicationThreadDeframerListener(this.transportListener, transportExecutor);
        this.migratingListener = new MigratingDeframerListener(this.appListener);
        deframer.setListener(this.migratingListener);
        this.deframer = deframer;
    }

    @Override
    public void setMaxInboundMessageSize(int messageSize) {
        this.deframer.setMaxInboundMessageSize(messageSize);
    }

    @Override
    public void setDecompressor(Decompressor decompressor) {
        this.deframer.setDecompressor(decompressor);
    }

    @Override
    public void setFullStreamDecompressor(GzipInflatingBuffer fullStreamDecompressor) {
        this.deframer.setFullStreamDecompressor(fullStreamDecompressor);
    }

    private boolean runWhereAppropriate(Op op) {
        return this.runWhereAppropriate(op, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean runWhereAppropriate(Op op, boolean currentThreadIsTransportThread) {
        boolean alreadyEnqueued;
        boolean deframerOnTransportThreadCopy;
        Object object = this.lock;
        synchronized (object) {
            deframerOnTransportThreadCopy = this.deframerOnTransportThread;
            alreadyEnqueued = this.messageProducerEnqueued;
            if (!deframerOnTransportThreadCopy) {
                this.opQueue.offer(op);
                this.messageProducerEnqueued = true;
            }
        }
        if (deframerOnTransportThreadCopy) {
            op.run(true);
            return true;
        }
        if (!alreadyEnqueued) {
            if (currentThreadIsTransportThread) {
                try (TaskCloseable ignore = PerfMark.traceTask("MigratingThreadDeframer.messageAvailable");){
                    this.transportListener.messagesAvailable(this.messageProducer);
                }
            } else {
                final Link link = PerfMark.linkOut();
                this.transportExecutor.runOnTransportThread(new Runnable(){

                    @Override
                    public void run() {
                        try (TaskCloseable ignore = PerfMark.traceTask("MigratingThreadDeframer.messageAvailable");){
                            PerfMark.linkIn(link);
                            MigratingThreadDeframer.this.transportListener.messagesAvailable(MigratingThreadDeframer.this.messageProducer);
                        }
                    }
                });
            }
        }
        return false;
    }

    @Override
    public void request(final int numMessages) {
        class RequestOp
        implements Op {
            RequestOp() {
            }

            @Override
            public void run(boolean isDeframerOnTransportThread) {
                if (isDeframerOnTransportThread) {
                    final Link link = PerfMark.linkOut();
                    MigratingThreadDeframer.this.transportExecutor.runOnTransportThread(new Runnable(){

                        @Override
                        public void run() {
                            try (TaskCloseable ignore = PerfMark.traceTask("MigratingThreadDeframer.request");){
                                PerfMark.linkIn(link);
                                MigratingThreadDeframer.this.requestFromTransportThread(numMessages);
                            }
                        }
                    });
                    return;
                }
                try (TaskCloseable ignore = PerfMark.traceTask("MigratingThreadDeframer.request");){
                    MigratingThreadDeframer.this.deframer.request(numMessages);
                }
                catch (Throwable t2) {
                    MigratingThreadDeframer.this.appListener.deframeFailed(t2);
                    MigratingThreadDeframer.this.deframer.close();
                }
            }
        }
        this.runWhereAppropriate(new RequestOp(), false);
    }

    private void requestFromTransportThread(final int numMessages) {
        class RequestAgainOp
        implements Op {
            RequestAgainOp() {
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run(boolean isDeframerOnTransportThread) {
                if (!isDeframerOnTransportThread) {
                    MigratingThreadDeframer.this.request(numMessages);
                    return;
                }
                try {
                    MigratingThreadDeframer.this.deframer.request(numMessages);
                }
                catch (Throwable t2) {
                    MigratingThreadDeframer.this.appListener.deframeFailed(t2);
                    MigratingThreadDeframer.this.deframer.close();
                }
                if (!MigratingThreadDeframer.this.deframer.hasPendingDeliveries()) {
                    Object object = MigratingThreadDeframer.this.lock;
                    synchronized (object) {
                        PerfMark.event("MigratingThreadDeframer.deframerOnApplicationThread");
                        MigratingThreadDeframer.this.migratingListener.setDelegate(MigratingThreadDeframer.this.appListener);
                        MigratingThreadDeframer.this.deframerOnTransportThread = false;
                    }
                }
            }
        }
        this.runWhereAppropriate(new RequestAgainOp());
    }

    @Override
    public void deframe(final ReadableBuffer data) {
        class DeframeOp
        implements Op,
        Closeable {
            DeframeOp() {
            }

            @Override
            public void run(boolean isDeframerOnTransportThread) {
                try (TaskCloseable ignore = PerfMark.traceTask("MigratingThreadDeframer.deframe");){
                    if (isDeframerOnTransportThread) {
                        MigratingThreadDeframer.this.deframer.deframe(data);
                        return;
                    }
                    try {
                        MigratingThreadDeframer.this.deframer.deframe(data);
                    }
                    catch (Throwable t2) {
                        MigratingThreadDeframer.this.appListener.deframeFailed(t2);
                        MigratingThreadDeframer.this.deframer.close();
                    }
                }
            }

            @Override
            public void close() {
                data.close();
            }
        }
        this.runWhereAppropriate(new DeframeOp());
    }

    @Override
    public void closeWhenComplete() {
        class CloseWhenCompleteOp
        implements Op {
            CloseWhenCompleteOp() {
            }

            @Override
            public void run(boolean isDeframerOnTransportThread) {
                MigratingThreadDeframer.this.deframer.closeWhenComplete();
            }
        }
        this.runWhereAppropriate(new CloseWhenCompleteOp());
    }

    @Override
    public void close() {
        class CloseOp
        implements Op {
            CloseOp() {
            }

            @Override
            public void run(boolean isDeframerOnTransportThread) {
                MigratingThreadDeframer.this.deframer.close();
            }
        }
        if (!this.runWhereAppropriate(new CloseOp())) {
            this.deframer.stopDelivery();
        }
    }

    static class MigratingDeframerListener
    extends ForwardingDeframerListener {
        private MessageDeframer.Listener delegate;

        public MigratingDeframerListener(MessageDeframer.Listener delegate) {
            this.setDelegate(delegate);
        }

        @Override
        protected MessageDeframer.Listener delegate() {
            return this.delegate;
        }

        public void setDelegate(MessageDeframer.Listener delegate) {
            this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        }
    }

    class DeframeMessageProducer
    implements StreamListener.MessageProducer,
    Closeable {
        DeframeMessageProducer() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public InputStream next() {
            InputStream is;
            while ((is = MigratingThreadDeframer.this.appListener.messageReadQueuePoll()) == null) {
                Op op;
                Object object = MigratingThreadDeframer.this.lock;
                synchronized (object) {
                    op = (Op)MigratingThreadDeframer.this.opQueue.poll();
                    if (op == null) {
                        if (MigratingThreadDeframer.this.deframer.hasPendingDeliveries()) {
                            PerfMark.event("MigratingThreadDeframer.deframerOnTransportThread");
                            MigratingThreadDeframer.this.migratingListener.setDelegate(MigratingThreadDeframer.this.transportListener);
                            MigratingThreadDeframer.this.deframerOnTransportThread = true;
                        }
                        MigratingThreadDeframer.this.messageProducerEnqueued = false;
                        return null;
                    }
                }
                op.run(false);
            }
            return is;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void close() {
            while (true) {
                Op op;
                Object object = MigratingThreadDeframer.this.lock;
                synchronized (object) {
                    while ((op = (Op)MigratingThreadDeframer.this.opQueue.poll()) != null && !(op instanceof Closeable)) {
                    }
                    if (op == null) {
                        MigratingThreadDeframer.this.messageProducerEnqueued = false;
                        return;
                    }
                }
                GrpcUtil.closeQuietly((Closeable)((Object)op));
            }
        }
    }

    private static interface Op {
        public void run(boolean var1);
    }
}

