/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ClientStreamTracer;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.AbstractSubchannel;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ChannelTracer;
import io.grpc.internal.ClientCallImpl;
import io.grpc.internal.ClientStream;
import io.grpc.internal.DelayedClientTransport;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InternalSubchannel;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.TimeProvider;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class OobChannel
extends ManagedChannel
implements InternalInstrumented<InternalChannelz.ChannelStats> {
    private static final Logger log = Logger.getLogger(OobChannel.class.getName());
    private InternalSubchannel subchannel;
    private AbstractSubchannel subchannelImpl;
    private LoadBalancer.SubchannelPicker subchannelPicker;
    private final InternalLogId logId;
    private final String authority;
    private final DelayedClientTransport delayedTransport;
    private final InternalChannelz channelz;
    private final ObjectPool<? extends Executor> executorPool;
    private final Executor executor;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    private volatile boolean shutdown;
    private final CallTracer channelCallsTracer;
    private final ChannelTracer channelTracer;
    private final TimeProvider timeProvider;
    private final ClientCallImpl.ClientStreamProvider transportProvider = new ClientCallImpl.ClientStreamProvider(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ClientStream newStream(MethodDescriptor<?, ?> method2, CallOptions callOptions, Metadata headers, Context context) {
            ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(callOptions, headers, 0, false);
            Context origContext = context.attach();
            try {
                ClientStream clientStream = OobChannel.this.delayedTransport.newStream(method2, headers, callOptions, tracers);
                return clientStream;
            }
            finally {
                context.detach(origContext);
            }
        }
    };

    OobChannel(String authority, ObjectPool<? extends Executor> executorPool, ScheduledExecutorService deadlineCancellationExecutor, SynchronizationContext syncContext, CallTracer callsTracer, ChannelTracer channelTracer, InternalChannelz channelz, TimeProvider timeProvider) {
        this.authority = Preconditions.checkNotNull(authority, "authority");
        this.logId = InternalLogId.allocate(this.getClass(), authority);
        this.executorPool = Preconditions.checkNotNull(executorPool, "executorPool");
        this.executor = Preconditions.checkNotNull(executorPool.getObject(), "executor");
        this.deadlineCancellationExecutor = Preconditions.checkNotNull(deadlineCancellationExecutor, "deadlineCancellationExecutor");
        this.delayedTransport = new DelayedClientTransport(this.executor, syncContext);
        this.channelz = Preconditions.checkNotNull(channelz);
        this.delayedTransport.start(new ManagedClientTransport.Listener(){

            @Override
            public void transportShutdown(Status s2) {
            }

            @Override
            public void transportTerminated() {
                OobChannel.this.subchannelImpl.shutdown();
            }

            @Override
            public void transportReady() {
            }

            @Override
            public Attributes filterTransport(Attributes attributes) {
                return attributes;
            }

            @Override
            public void transportInUse(boolean inUse) {
            }
        });
        this.channelCallsTracer = callsTracer;
        this.channelTracer = Preconditions.checkNotNull(channelTracer, "channelTracer");
        this.timeProvider = Preconditions.checkNotNull(timeProvider, "timeProvider");
    }

    void setSubchannel(final InternalSubchannel subchannel) {
        log.log(Level.FINE, "[{0}] Created with [{1}]", new Object[]{this, subchannel});
        this.subchannel = subchannel;
        this.subchannelImpl = new AbstractSubchannel(){

            @Override
            public void shutdown() {
                subchannel.shutdown(Status.UNAVAILABLE.withDescription("OobChannel is shutdown"));
            }

            @Override
            InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel() {
                return subchannel;
            }

            @Override
            public void requestConnection() {
                subchannel.obtainActiveTransport();
            }

            @Override
            public List<EquivalentAddressGroup> getAllAddresses() {
                return subchannel.getAddressGroups();
            }

            @Override
            public Attributes getAttributes() {
                return Attributes.EMPTY;
            }

            @Override
            public Object getInternalSubchannel() {
                return subchannel;
            }
        };
        final class OobSubchannelPicker
        extends LoadBalancer.SubchannelPicker {
            final LoadBalancer.PickResult result;

            OobSubchannelPicker() {
                this.result = LoadBalancer.PickResult.withSubchannel(OobChannel.this.subchannelImpl);
            }

            @Override
            public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
                return this.result;
            }

            public String toString() {
                return MoreObjects.toStringHelper(OobSubchannelPicker.class).add("result", this.result).toString();
            }
        }
        this.subchannelPicker = new OobSubchannelPicker();
        this.delayedTransport.reprocess(this.subchannelPicker);
    }

    void updateAddresses(List<EquivalentAddressGroup> eag) {
        this.subchannel.updateAddresses(eag);
    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return new ClientCallImpl<RequestT, ResponseT>(methodDescriptor, callOptions.getExecutor() == null ? this.executor : callOptions.getExecutor(), callOptions, this.transportProvider, this.deadlineCancellationExecutor, this.channelCallsTracer, null);
    }

    @Override
    public String authority() {
        return this.authority;
    }

    @Override
    public boolean isTerminated() {
        return this.terminatedLatch.getCount() == 0L;
    }

    @Override
    public boolean awaitTermination(long time, TimeUnit unit) throws InterruptedException {
        return this.terminatedLatch.await(time, unit);
    }

    @Override
    public ConnectivityState getState(boolean requestConnectionIgnored) {
        if (this.subchannel == null) {
            return ConnectivityState.IDLE;
        }
        return this.subchannel.getState();
    }

    @Override
    public ManagedChannel shutdown() {
        this.shutdown = true;
        this.delayedTransport.shutdown(Status.UNAVAILABLE.withDescription("OobChannel.shutdown() called"));
        return this;
    }

    @Override
    public boolean isShutdown() {
        return this.shutdown;
    }

    @Override
    public ManagedChannel shutdownNow() {
        this.shutdown = true;
        this.delayedTransport.shutdownNow(Status.UNAVAILABLE.withDescription("OobChannel.shutdownNow() called"));
        return this;
    }

    void handleSubchannelStateChange(final ConnectivityStateInfo newState) {
        this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Entering " + (Object)((Object)newState.getState()) + " state").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
        switch (newState.getState()) {
            case READY: 
            case IDLE: {
                this.delayedTransport.reprocess(this.subchannelPicker);
                break;
            }
            case TRANSIENT_FAILURE: {
                final class OobErrorPicker
                extends LoadBalancer.SubchannelPicker {
                    final LoadBalancer.PickResult errorResult;

                    OobErrorPicker() {
                        this.errorResult = LoadBalancer.PickResult.withError(newState.getStatus());
                    }

                    @Override
                    public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args2) {
                        return this.errorResult;
                    }

                    public String toString() {
                        return MoreObjects.toStringHelper(OobErrorPicker.class).add("errorResult", this.errorResult).toString();
                    }
                }
                this.delayedTransport.reprocess(new OobErrorPicker());
                break;
            }
        }
    }

    void handleSubchannelTerminated() {
        this.channelz.removeSubchannel(this);
        this.executorPool.returnObject(this.executor);
        this.terminatedLatch.countDown();
    }

    @VisibleForTesting
    LoadBalancer.Subchannel getSubchannel() {
        return this.subchannelImpl;
    }

    InternalSubchannel getInternalSubchannel() {
        return this.subchannel;
    }

    @Override
    public ListenableFuture<InternalChannelz.ChannelStats> getStats() {
        SettableFuture<InternalChannelz.ChannelStats> ret = SettableFuture.create();
        InternalChannelz.ChannelStats.Builder builder = new InternalChannelz.ChannelStats.Builder();
        this.channelCallsTracer.updateBuilder(builder);
        this.channelTracer.updateBuilder(builder);
        builder.setTarget(this.authority).setState(this.subchannel.getState()).setSubchannels(Collections.singletonList(this.subchannel));
        ret.set(builder.build());
        return ret;
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("authority", this.authority).toString();
    }

    @Override
    public void resetConnectBackoff() {
        this.subchannel.resetConnectBackoff();
    }
}

