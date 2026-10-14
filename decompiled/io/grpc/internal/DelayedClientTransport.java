/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer;
import io.grpc.Context;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.DelayedStream;
import io.grpc.internal.FailingClientStream;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InsightBuilder;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.PickDetailsConsumerImpl;
import io.grpc.internal.PickSubchannelArgsImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class DelayedClientTransport
implements ManagedClientTransport {
    private final InternalLogId logId = InternalLogId.allocate(DelayedClientTransport.class, null);
    private final Object lock = new Object();
    private final Executor defaultAppExecutor;
    private final SynchronizationContext syncContext;
    private Runnable reportTransportInUse;
    private Runnable reportTransportNotInUse;
    private Runnable reportTransportTerminated;
    private ManagedClientTransport.Listener listener;
    @Nonnull
    @GuardedBy(value="lock")
    private Collection<PendingStream> pendingStreams = new LinkedHashSet<PendingStream>();
    private volatile PickerState pickerState = new PickerState(null, null);

    DelayedClientTransport(Executor defaultAppExecutor, SynchronizationContext syncContext) {
        this.defaultAppExecutor = defaultAppExecutor;
        this.syncContext = syncContext;
    }

    @Override
    public final Runnable start(final ManagedClientTransport.Listener listener) {
        this.listener = listener;
        this.reportTransportInUse = new Runnable(){

            @Override
            public void run() {
                listener.transportInUse(true);
            }
        };
        this.reportTransportNotInUse = new Runnable(){

            @Override
            public void run() {
                listener.transportInUse(false);
            }
        };
        this.reportTransportTerminated = new Runnable(){

            @Override
            public void run() {
                listener.transportTerminated();
            }
        };
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final ClientStream newStream(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, ClientStreamTracer[] tracers) {
        try {
            PickSubchannelArgsImpl args2 = new PickSubchannelArgsImpl(method2, headers, callOptions, new PickDetailsConsumerImpl(tracers));
            PickerState state = this.pickerState;
            while (true) {
                LoadBalancer.PickResult pickResult;
                ClientTransport transport;
                Object object;
                if (state.shutdownStatus != null) {
                    object = new FailingClientStream(state.shutdownStatus, tracers);
                    return object;
                }
                if (state.lastPicker != null && (transport = GrpcUtil.getTransportFromPickResult(pickResult = state.lastPicker.pickSubchannel(args2), callOptions.isWaitForReady())) != null) {
                    ClientStream clientStream = transport.newStream(((LoadBalancer.PickSubchannelArgs)args2).getMethodDescriptor(), ((LoadBalancer.PickSubchannelArgs)args2).getHeaders(), ((LoadBalancer.PickSubchannelArgs)args2).getCallOptions(), tracers);
                    return clientStream;
                }
                object = this.lock;
                synchronized (object) {
                    PickerState newerState = this.pickerState;
                    if (state == newerState) {
                        PendingStream pendingStream = this.createPendingStream(args2, tracers);
                        return pendingStream;
                    }
                    state = newerState;
                }
            }
        }
        finally {
            this.syncContext.drain();
        }
    }

    @GuardedBy(value="lock")
    private PendingStream createPendingStream(LoadBalancer.PickSubchannelArgs args2, ClientStreamTracer[] tracers) {
        PendingStream pendingStream = new PendingStream(args2, tracers);
        this.pendingStreams.add(pendingStream);
        if (this.getPendingStreamsCount() == 1) {
            this.syncContext.executeLater(this.reportTransportInUse);
        }
        for (ClientStreamTracer streamTracer : tracers) {
            streamTracer.createPendingStream();
        }
        return pendingStream;
    }

    @Override
    public final void ping(ClientTransport.PingCallback callback, Executor executor2) {
        throw new UnsupportedOperationException("This method is not expected to be called");
    }

    @Override
    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        SettableFuture<InternalChannelz.SocketStats> ret = SettableFuture.create();
        ret.set(null);
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void shutdown(final Status status) {
        Object object = this.lock;
        synchronized (object) {
            if (this.pickerState.shutdownStatus != null) {
                return;
            }
            this.pickerState = this.pickerState.withShutdownStatus(status);
            this.syncContext.executeLater(new Runnable(){

                @Override
                public void run() {
                    DelayedClientTransport.this.listener.transportShutdown(status);
                }
            });
            if (!this.hasPendingStreams() && this.reportTransportTerminated != null) {
                this.syncContext.executeLater(this.reportTransportTerminated);
                this.reportTransportTerminated = null;
            }
        }
        this.syncContext.drain();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void shutdownNow(Status status) {
        Runnable savedReportTransportTerminated;
        Collection<PendingStream> savedPendingStreams;
        this.shutdown(status);
        Iterator<PendingStream> iterator2 = this.lock;
        synchronized (iterator2) {
            savedPendingStreams = this.pendingStreams;
            savedReportTransportTerminated = this.reportTransportTerminated;
            this.reportTransportTerminated = null;
            if (!this.pendingStreams.isEmpty()) {
                this.pendingStreams = Collections.emptyList();
            }
        }
        if (savedReportTransportTerminated != null) {
            for (PendingStream stream : savedPendingStreams) {
                Runnable runnable2 = stream.setStream(new FailingClientStream(status, ClientStreamListener.RpcProgress.REFUSED, stream.tracers));
                if (runnable2 == null) continue;
                runnable2.run();
            }
            this.syncContext.execute(savedReportTransportTerminated);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean hasPendingStreams() {
        Object object = this.lock;
        synchronized (object) {
            return !this.pendingStreams.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @VisibleForTesting
    final int getPendingStreamsCount() {
        Object object = this.lock;
        synchronized (object) {
            return this.pendingStreams.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void reprocess(@Nullable LoadBalancer.SubchannelPicker picker) {
        ArrayList<PendingStream> toProcess;
        Object object = this.lock;
        synchronized (object) {
            this.pickerState = this.pickerState.withPicker(picker);
            if (picker == null || !this.hasPendingStreams()) {
                return;
            }
            toProcess = new ArrayList<PendingStream>(this.pendingStreams);
        }
        ArrayList<PendingStream> toRemove = new ArrayList<PendingStream>();
        for (PendingStream stream : toProcess) {
            Runnable runnable2;
            CallOptions callOptions;
            LoadBalancer.PickResult pickResult = picker.pickSubchannel(stream.args);
            ClientTransport transport = GrpcUtil.getTransportFromPickResult(pickResult, (callOptions = stream.args.getCallOptions()).isWaitForReady());
            if (transport == null) continue;
            Executor executor2 = this.defaultAppExecutor;
            if (callOptions.getExecutor() != null) {
                executor2 = callOptions.getExecutor();
            }
            if ((runnable2 = stream.createRealStream(transport)) != null) {
                executor2.execute(runnable2);
            }
            toRemove.add(stream);
        }
        Object object2 = this.lock;
        synchronized (object2) {
            if (!this.hasPendingStreams()) {
                return;
            }
            this.pendingStreams.removeAll(toRemove);
            if (this.pendingStreams.isEmpty()) {
                this.pendingStreams = new LinkedHashSet<PendingStream>();
            }
            if (!this.hasPendingStreams()) {
                this.syncContext.executeLater(this.reportTransportNotInUse);
                if (this.pickerState.shutdownStatus != null && this.reportTransportTerminated != null) {
                    this.syncContext.executeLater(this.reportTransportTerminated);
                    this.reportTransportTerminated = null;
                }
            }
        }
        this.syncContext.drain();
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    static final class PickerState {
        @Nullable
        final LoadBalancer.SubchannelPicker lastPicker;
        @Nullable
        final Status shutdownStatus;

        private PickerState(LoadBalancer.SubchannelPicker lastPicker, Status shutdownStatus) {
            this.lastPicker = lastPicker;
            this.shutdownStatus = shutdownStatus;
        }

        public PickerState withPicker(LoadBalancer.SubchannelPicker newPicker) {
            return new PickerState(newPicker, this.shutdownStatus);
        }

        public PickerState withShutdownStatus(Status newShutdownStatus) {
            return new PickerState(this.lastPicker, newShutdownStatus);
        }
    }

    private class PendingStream
    extends DelayedStream {
        private final LoadBalancer.PickSubchannelArgs args;
        private final Context context = Context.current();
        private final ClientStreamTracer[] tracers;

        private PendingStream(LoadBalancer.PickSubchannelArgs args2, ClientStreamTracer[] tracers) {
            this.args = args2;
            this.tracers = tracers;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Runnable createRealStream(ClientTransport transport) {
            ClientStream realStream;
            Context origContext = this.context.attach();
            try {
                realStream = transport.newStream(this.args.getMethodDescriptor(), this.args.getHeaders(), this.args.getCallOptions(), this.tracers);
            }
            finally {
                this.context.detach(origContext);
            }
            return this.setStream(realStream);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel(Status reason) {
            super.cancel(reason);
            Object object = DelayedClientTransport.this.lock;
            synchronized (object) {
                if (DelayedClientTransport.this.reportTransportTerminated != null) {
                    boolean justRemovedAnElement = DelayedClientTransport.this.pendingStreams.remove(this);
                    if (!DelayedClientTransport.this.hasPendingStreams() && justRemovedAnElement) {
                        DelayedClientTransport.this.syncContext.executeLater(DelayedClientTransport.this.reportTransportNotInUse);
                        if (((DelayedClientTransport)DelayedClientTransport.this).pickerState.shutdownStatus != null) {
                            DelayedClientTransport.this.syncContext.executeLater(DelayedClientTransport.this.reportTransportTerminated);
                            DelayedClientTransport.this.reportTransportTerminated = null;
                        }
                    }
                }
            }
            DelayedClientTransport.this.syncContext.drain();
        }

        @Override
        protected void onEarlyCancellation(Status reason) {
            for (ClientStreamTracer tracer : this.tracers) {
                tracer.streamClosed(reason);
            }
        }

        @Override
        public void appendTimeoutInsight(InsightBuilder insight) {
            if (this.args.getCallOptions().isWaitForReady()) {
                insight.append("wait_for_ready");
            }
            super.appendTimeoutInsight(insight);
        }
    }
}

