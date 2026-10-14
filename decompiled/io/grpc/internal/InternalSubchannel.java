/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.errorprone.annotations.ForOverride;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.ClientTransportFilter;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.HttpConnectProxiedSocketAddress;
import io.grpc.InternalChannelz;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalWithLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.BackoffPolicy;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ChannelLoggerImpl;
import io.grpc.internal.ChannelTracer;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.ForwardingClientStream;
import io.grpc.internal.ForwardingClientStreamListener;
import io.grpc.internal.ForwardingConnectionClientTransport;
import io.grpc.internal.InUseStateAggregator;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.TransportProvider;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class InternalSubchannel
implements InternalInstrumented<InternalChannelz.ChannelStats>,
TransportProvider {
    private final InternalLogId logId;
    private final String authority;
    private final String userAgent;
    private final BackoffPolicy.Provider backoffPolicyProvider;
    private final Callback callback;
    private final ClientTransportFactory transportFactory;
    private final ScheduledExecutorService scheduledExecutor;
    private final InternalChannelz channelz;
    private final CallTracer callsTracer;
    private final ChannelTracer channelTracer;
    private final ChannelLogger channelLogger;
    private final List<ClientTransportFilter> transportFilters;
    private final SynchronizationContext syncContext;
    private final Index addressIndex;
    private volatile List<EquivalentAddressGroup> addressGroups;
    private BackoffPolicy reconnectPolicy;
    private final Stopwatch connectingTimer;
    @Nullable
    private SynchronizationContext.ScheduledHandle reconnectTask;
    @Nullable
    private SynchronizationContext.ScheduledHandle shutdownDueToUpdateTask;
    @Nullable
    private ManagedClientTransport shutdownDueToUpdateTransport;
    private final Collection<ConnectionClientTransport> transports = new ArrayList<ConnectionClientTransport>();
    private final InUseStateAggregator<ConnectionClientTransport> inUseStateAggregator = new InUseStateAggregator<ConnectionClientTransport>(){

        @Override
        protected void handleInUse() {
            InternalSubchannel.this.callback.onInUse(InternalSubchannel.this);
        }

        @Override
        protected void handleNotInUse() {
            InternalSubchannel.this.callback.onNotInUse(InternalSubchannel.this);
        }
    };
    @Nullable
    private ConnectionClientTransport pendingTransport;
    @Nullable
    private volatile ManagedClientTransport activeTransport;
    private volatile ConnectivityStateInfo state = ConnectivityStateInfo.forNonError(ConnectivityState.IDLE);
    private Status shutdownReason;

    InternalSubchannel(List<EquivalentAddressGroup> addressGroups, String authority, String userAgent, BackoffPolicy.Provider backoffPolicyProvider, ClientTransportFactory transportFactory, ScheduledExecutorService scheduledExecutor, Supplier<Stopwatch> stopwatchSupplier, SynchronizationContext syncContext, Callback callback, InternalChannelz channelz, CallTracer callsTracer, ChannelTracer channelTracer, InternalLogId logId, ChannelLogger channelLogger, List<ClientTransportFilter> transportFilters) {
        Preconditions.checkNotNull(addressGroups, "addressGroups");
        Preconditions.checkArgument(!addressGroups.isEmpty(), "addressGroups is empty");
        InternalSubchannel.checkListHasNoNulls(addressGroups, "addressGroups contains null entry");
        List<EquivalentAddressGroup> unmodifiableAddressGroups = Collections.unmodifiableList(new ArrayList<EquivalentAddressGroup>(addressGroups));
        this.addressGroups = unmodifiableAddressGroups;
        this.addressIndex = new Index(unmodifiableAddressGroups);
        this.authority = authority;
        this.userAgent = userAgent;
        this.backoffPolicyProvider = backoffPolicyProvider;
        this.transportFactory = transportFactory;
        this.scheduledExecutor = scheduledExecutor;
        this.connectingTimer = stopwatchSupplier.get();
        this.syncContext = syncContext;
        this.callback = callback;
        this.channelz = channelz;
        this.callsTracer = callsTracer;
        this.channelTracer = Preconditions.checkNotNull(channelTracer, "channelTracer");
        this.logId = Preconditions.checkNotNull(logId, "logId");
        this.channelLogger = Preconditions.checkNotNull(channelLogger, "channelLogger");
        this.transportFilters = transportFilters;
    }

    ChannelLogger getChannelLogger() {
        return this.channelLogger;
    }

    @Override
    public ClientTransport obtainActiveTransport() {
        ManagedClientTransport savedTransport = this.activeTransport;
        if (savedTransport != null) {
            return savedTransport;
        }
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                if (InternalSubchannel.this.state.getState() == ConnectivityState.IDLE) {
                    InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "CONNECTING as requested");
                    InternalSubchannel.this.gotoNonErrorState(ConnectivityState.CONNECTING);
                    InternalSubchannel.this.startNewTransport();
                }
            }
        });
        return null;
    }

    @Nullable
    ClientTransport getTransport() {
        return this.activeTransport;
    }

    String getAuthority() {
        return this.authority;
    }

    private void startNewTransport() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        Preconditions.checkState(this.reconnectTask == null, "Should have no reconnectTask scheduled");
        if (this.addressIndex.isAtBeginning()) {
            this.connectingTimer.reset().start();
        }
        SocketAddress address = this.addressIndex.getCurrentAddress();
        HttpConnectProxiedSocketAddress proxiedAddr = null;
        if (address instanceof HttpConnectProxiedSocketAddress) {
            proxiedAddr = (HttpConnectProxiedSocketAddress)address;
            address = proxiedAddr.getTargetAddress();
        }
        Attributes currentEagAttributes = this.addressIndex.getCurrentEagAttributes();
        String eagChannelAuthority = currentEagAttributes.get(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE);
        ClientTransportFactory.ClientTransportOptions options = new ClientTransportFactory.ClientTransportOptions().setAuthority(eagChannelAuthority != null ? eagChannelAuthority : this.authority).setEagAttributes(currentEagAttributes).setUserAgent(this.userAgent).setHttpConnectProxiedSocketAddress(proxiedAddr);
        TransportLogger transportLogger = new TransportLogger();
        transportLogger.logId = this.getLogId();
        CallTracingTransport transport = new CallTracingTransport(this.transportFactory.newClientTransport(address, options, transportLogger), this.callsTracer);
        transportLogger.logId = transport.getLogId();
        this.channelz.addClientSocket(transport);
        this.pendingTransport = transport;
        this.transports.add(transport);
        Runnable runnable2 = transport.start(new TransportListener(transport));
        if (runnable2 != null) {
            this.syncContext.executeLater(runnable2);
        }
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Started transport {0}", transportLogger.logId);
    }

    private void scheduleBackoff(Status status) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        this.gotoState(ConnectivityStateInfo.forTransientFailure(status));
        if (this.reconnectPolicy == null) {
            this.reconnectPolicy = this.backoffPolicyProvider.get();
        }
        long delayNanos = this.reconnectPolicy.nextBackoffNanos() - this.connectingTimer.elapsed(TimeUnit.NANOSECONDS);
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "TRANSIENT_FAILURE ({0}). Will reconnect after {1} ns", this.printShortStatus(status), delayNanos);
        Preconditions.checkState(this.reconnectTask == null, "previous reconnectTask is not done");
        class EndOfCurrentBackoff
        implements Runnable {
            EndOfCurrentBackoff() {
            }

            @Override
            public void run() {
                InternalSubchannel.this.reconnectTask = null;
                InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "CONNECTING after backoff");
                InternalSubchannel.this.gotoNonErrorState(ConnectivityState.CONNECTING);
                InternalSubchannel.this.startNewTransport();
            }
        }
        this.reconnectTask = this.syncContext.schedule(new EndOfCurrentBackoff(), delayNanos, TimeUnit.NANOSECONDS, this.scheduledExecutor);
    }

    void resetConnectBackoff() {
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                if (InternalSubchannel.this.state.getState() != ConnectivityState.TRANSIENT_FAILURE) {
                    return;
                }
                InternalSubchannel.this.cancelReconnectTask();
                InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "CONNECTING; backoff interrupted");
                InternalSubchannel.this.gotoNonErrorState(ConnectivityState.CONNECTING);
                InternalSubchannel.this.startNewTransport();
            }
        });
    }

    private void gotoNonErrorState(ConnectivityState newState) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        this.gotoState(ConnectivityStateInfo.forNonError(newState));
    }

    private void gotoState(ConnectivityStateInfo newState) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.state.getState() != newState.getState()) {
            Preconditions.checkState(this.state.getState() != ConnectivityState.SHUTDOWN, "Cannot transition out of SHUTDOWN to " + newState);
            this.state = newState;
            this.callback.onStateChange(this, newState);
        }
    }

    public void updateAddresses(List<EquivalentAddressGroup> newAddressGroups) {
        Preconditions.checkNotNull(newAddressGroups, "newAddressGroups");
        InternalSubchannel.checkListHasNoNulls(newAddressGroups, "newAddressGroups contains null entry");
        Preconditions.checkArgument(!newAddressGroups.isEmpty(), "newAddressGroups is empty");
        final List<EquivalentAddressGroup> newImmutableAddressGroups = Collections.unmodifiableList(new ArrayList<EquivalentAddressGroup>(newAddressGroups));
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                ManagedClientTransport savedTransport = null;
                SocketAddress previousAddress = InternalSubchannel.this.addressIndex.getCurrentAddress();
                InternalSubchannel.this.addressIndex.updateGroups(newImmutableAddressGroups);
                InternalSubchannel.this.addressGroups = newImmutableAddressGroups;
                if (!(InternalSubchannel.this.state.getState() != ConnectivityState.READY && InternalSubchannel.this.state.getState() != ConnectivityState.CONNECTING || InternalSubchannel.this.addressIndex.seekTo(previousAddress))) {
                    if (InternalSubchannel.this.state.getState() == ConnectivityState.READY) {
                        savedTransport = InternalSubchannel.this.activeTransport;
                        InternalSubchannel.this.activeTransport = null;
                        InternalSubchannel.this.addressIndex.reset();
                        InternalSubchannel.this.gotoNonErrorState(ConnectivityState.IDLE);
                    } else {
                        InternalSubchannel.this.pendingTransport.shutdown(Status.UNAVAILABLE.withDescription("InternalSubchannel closed pending transport due to address change"));
                        InternalSubchannel.this.pendingTransport = null;
                        InternalSubchannel.this.addressIndex.reset();
                        InternalSubchannel.this.startNewTransport();
                    }
                }
                if (savedTransport != null) {
                    if (InternalSubchannel.this.shutdownDueToUpdateTask != null) {
                        InternalSubchannel.this.shutdownDueToUpdateTransport.shutdown(Status.UNAVAILABLE.withDescription("InternalSubchannel closed transport early due to address change"));
                        InternalSubchannel.this.shutdownDueToUpdateTask.cancel();
                        InternalSubchannel.this.shutdownDueToUpdateTask = null;
                        InternalSubchannel.this.shutdownDueToUpdateTransport = null;
                    }
                    InternalSubchannel.this.shutdownDueToUpdateTransport = savedTransport;
                    InternalSubchannel.this.shutdownDueToUpdateTask = InternalSubchannel.this.syncContext.schedule(new Runnable(){

                        @Override
                        public void run() {
                            ManagedClientTransport transport = InternalSubchannel.this.shutdownDueToUpdateTransport;
                            InternalSubchannel.this.shutdownDueToUpdateTask = null;
                            InternalSubchannel.this.shutdownDueToUpdateTransport = null;
                            transport.shutdown(Status.UNAVAILABLE.withDescription("InternalSubchannel closed transport due to address change"));
                        }
                    }, 5L, TimeUnit.SECONDS, InternalSubchannel.this.scheduledExecutor);
                }
            }
        });
    }

    public void shutdown(final Status reason) {
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                if (InternalSubchannel.this.state.getState() == ConnectivityState.SHUTDOWN) {
                    return;
                }
                InternalSubchannel.this.shutdownReason = reason;
                ManagedClientTransport savedActiveTransport = InternalSubchannel.this.activeTransport;
                ConnectionClientTransport savedPendingTransport = InternalSubchannel.this.pendingTransport;
                InternalSubchannel.this.activeTransport = null;
                InternalSubchannel.this.pendingTransport = null;
                InternalSubchannel.this.gotoNonErrorState(ConnectivityState.SHUTDOWN);
                InternalSubchannel.this.addressIndex.reset();
                if (InternalSubchannel.this.transports.isEmpty()) {
                    InternalSubchannel.this.handleTermination();
                }
                InternalSubchannel.this.cancelReconnectTask();
                if (InternalSubchannel.this.shutdownDueToUpdateTask != null) {
                    InternalSubchannel.this.shutdownDueToUpdateTask.cancel();
                    InternalSubchannel.this.shutdownDueToUpdateTransport.shutdown(reason);
                    InternalSubchannel.this.shutdownDueToUpdateTask = null;
                    InternalSubchannel.this.shutdownDueToUpdateTransport = null;
                }
                if (savedActiveTransport != null) {
                    savedActiveTransport.shutdown(reason);
                }
                if (savedPendingTransport != null) {
                    savedPendingTransport.shutdown(reason);
                }
            }
        });
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("logId", this.logId.getId()).add("addressGroups", this.addressGroups).toString();
    }

    private void handleTermination() {
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Terminated");
                InternalSubchannel.this.callback.onTerminated(InternalSubchannel.this);
            }
        });
    }

    private void handleTransportInUseState(final ConnectionClientTransport transport, final boolean inUse) {
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                InternalSubchannel.this.inUseStateAggregator.updateObjectInUse(transport, inUse);
            }
        });
    }

    void shutdownNow(final Status reason) {
        this.shutdown(reason);
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                ArrayList transportsCopy = new ArrayList(InternalSubchannel.this.transports);
                for (ManagedClientTransport transport : transportsCopy) {
                    transport.shutdownNow(reason);
                }
            }
        });
    }

    List<EquivalentAddressGroup> getAddressGroups() {
        return this.addressGroups;
    }

    private void cancelReconnectTask() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.reconnectTask != null) {
            this.reconnectTask.cancel();
            this.reconnectTask = null;
            this.reconnectPolicy = null;
        }
    }

    @Override
    public InternalLogId getLogId() {
        return this.logId;
    }

    @Override
    public ListenableFuture<InternalChannelz.ChannelStats> getStats() {
        final SettableFuture<InternalChannelz.ChannelStats> channelStatsFuture = SettableFuture.create();
        this.syncContext.execute(new Runnable(){

            @Override
            public void run() {
                InternalChannelz.ChannelStats.Builder builder = new InternalChannelz.ChannelStats.Builder();
                List<EquivalentAddressGroup> addressGroupsSnapshot = InternalSubchannel.this.addressIndex.getGroups();
                ArrayList<InternalWithLogId> transportsSnapshot = new ArrayList<InternalWithLogId>(InternalSubchannel.this.transports);
                builder.setTarget(addressGroupsSnapshot.toString()).setState(InternalSubchannel.this.getState());
                builder.setSockets(transportsSnapshot);
                InternalSubchannel.this.callsTracer.updateBuilder(builder);
                InternalSubchannel.this.channelTracer.updateBuilder(builder);
                channelStatsFuture.set(builder.build());
            }
        });
        return channelStatsFuture;
    }

    ConnectivityState getState() {
        return this.state.getState();
    }

    private static void checkListHasNoNulls(List<?> list, String msg) {
        for (Object item : list) {
            Preconditions.checkNotNull(item, msg);
        }
    }

    private String printShortStatus(Status status) {
        StringBuilder buffer = new StringBuilder();
        buffer.append((Object)status.getCode());
        if (status.getDescription() != null) {
            buffer.append("(").append(status.getDescription()).append(")");
        }
        if (status.getCause() != null) {
            buffer.append("[").append(status.getCause()).append("]");
        }
        return buffer.toString();
    }

    @VisibleForTesting
    static final class TransportLogger
    extends ChannelLogger {
        InternalLogId logId;

        TransportLogger() {
        }

        @Override
        public void log(ChannelLogger.ChannelLogLevel level, String message) {
            ChannelLoggerImpl.logOnly(this.logId, level, message);
        }

        @Override
        public void log(ChannelLogger.ChannelLogLevel level, String messageFormat, Object ... args2) {
            ChannelLoggerImpl.logOnly(this.logId, level, messageFormat, args2);
        }
    }

    @VisibleForTesting
    static final class Index {
        private List<EquivalentAddressGroup> addressGroups;
        private int groupIndex;
        private int addressIndex;

        public Index(List<EquivalentAddressGroup> groups2) {
            this.addressGroups = groups2;
        }

        public boolean isValid() {
            return this.groupIndex < this.addressGroups.size();
        }

        public boolean isAtBeginning() {
            return this.groupIndex == 0 && this.addressIndex == 0;
        }

        public void increment() {
            EquivalentAddressGroup group = this.addressGroups.get(this.groupIndex);
            ++this.addressIndex;
            if (this.addressIndex >= group.getAddresses().size()) {
                ++this.groupIndex;
                this.addressIndex = 0;
            }
        }

        public void reset() {
            this.groupIndex = 0;
            this.addressIndex = 0;
        }

        public SocketAddress getCurrentAddress() {
            return this.addressGroups.get(this.groupIndex).getAddresses().get(this.addressIndex);
        }

        public Attributes getCurrentEagAttributes() {
            return this.addressGroups.get(this.groupIndex).getAttributes();
        }

        public List<EquivalentAddressGroup> getGroups() {
            return this.addressGroups;
        }

        public void updateGroups(List<EquivalentAddressGroup> newGroups) {
            this.addressGroups = newGroups;
            this.reset();
        }

        public boolean seekTo(SocketAddress needle) {
            for (int i = 0; i < this.addressGroups.size(); ++i) {
                EquivalentAddressGroup group = this.addressGroups.get(i);
                int j = group.getAddresses().indexOf(needle);
                if (j == -1) continue;
                this.groupIndex = i;
                this.addressIndex = j;
                return true;
            }
            return false;
        }
    }

    @VisibleForTesting
    static final class CallTracingTransport
    extends ForwardingConnectionClientTransport {
        private final ConnectionClientTransport delegate;
        private final CallTracer callTracer;

        private CallTracingTransport(ConnectionClientTransport delegate, CallTracer callTracer) {
            this.delegate = delegate;
            this.callTracer = callTracer;
        }

        @Override
        protected ConnectionClientTransport delegate() {
            return this.delegate;
        }

        @Override
        public ClientStream newStream(MethodDescriptor<?, ?> method2, Metadata headers, CallOptions callOptions, ClientStreamTracer[] tracers) {
            final ClientStream streamDelegate = super.newStream(method2, headers, callOptions, tracers);
            return new ForwardingClientStream(){

                @Override
                protected ClientStream delegate() {
                    return streamDelegate;
                }

                @Override
                public void start(final ClientStreamListener listener) {
                    callTracer.reportCallStarted();
                    super.start(new ForwardingClientStreamListener(){

                        @Override
                        protected ClientStreamListener delegate() {
                            return listener;
                        }

                        @Override
                        public void closed(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
                            callTracer.reportCallEnded(status.isOk());
                            super.closed(status, rpcProgress, trailers);
                        }
                    });
                }
            };
        }
    }

    static abstract class Callback {
        Callback() {
        }

        @ForOverride
        void onTerminated(InternalSubchannel is) {
        }

        @ForOverride
        void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
        }

        @ForOverride
        void onInUse(InternalSubchannel is) {
        }

        @ForOverride
        void onNotInUse(InternalSubchannel is) {
        }
    }

    private class TransportListener
    implements ManagedClientTransport.Listener {
        final ConnectionClientTransport transport;
        boolean shutdownInitiated = false;

        TransportListener(ConnectionClientTransport transport) {
            this.transport = transport;
        }

        @Override
        public Attributes filterTransport(Attributes attributes) {
            for (ClientTransportFilter filter2 : InternalSubchannel.this.transportFilters) {
                attributes = Preconditions.checkNotNull(filter2.transportReady(attributes), "Filter %s returned null", (Object)filter2);
            }
            return attributes;
        }

        @Override
        public void transportReady() {
            InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "READY");
            InternalSubchannel.this.syncContext.execute(new Runnable(){

                @Override
                public void run() {
                    InternalSubchannel.this.reconnectPolicy = null;
                    if (InternalSubchannel.this.shutdownReason != null) {
                        Preconditions.checkState(InternalSubchannel.this.activeTransport == null, "Unexpected non-null activeTransport");
                        TransportListener.this.transport.shutdown(InternalSubchannel.this.shutdownReason);
                    } else if (InternalSubchannel.this.pendingTransport == TransportListener.this.transport) {
                        InternalSubchannel.this.activeTransport = TransportListener.this.transport;
                        InternalSubchannel.this.pendingTransport = null;
                        InternalSubchannel.this.gotoNonErrorState(ConnectivityState.READY);
                    }
                }
            });
        }

        @Override
        public void transportInUse(boolean inUse) {
            InternalSubchannel.this.handleTransportInUseState(this.transport, inUse);
        }

        @Override
        public void transportShutdown(final Status s2) {
            InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "{0} SHUTDOWN with {1}", this.transport.getLogId(), InternalSubchannel.this.printShortStatus(s2));
            this.shutdownInitiated = true;
            InternalSubchannel.this.syncContext.execute(new Runnable(){

                @Override
                public void run() {
                    if (InternalSubchannel.this.state.getState() == ConnectivityState.SHUTDOWN) {
                        return;
                    }
                    if (InternalSubchannel.this.activeTransport == TransportListener.this.transport) {
                        InternalSubchannel.this.activeTransport = null;
                        InternalSubchannel.this.addressIndex.reset();
                        InternalSubchannel.this.gotoNonErrorState(ConnectivityState.IDLE);
                    } else if (InternalSubchannel.this.pendingTransport == TransportListener.this.transport) {
                        Preconditions.checkState(InternalSubchannel.this.state.getState() == ConnectivityState.CONNECTING, "Expected state is CONNECTING, actual state is %s", (Object)InternalSubchannel.this.state.getState());
                        InternalSubchannel.this.addressIndex.increment();
                        if (!InternalSubchannel.this.addressIndex.isValid()) {
                            InternalSubchannel.this.pendingTransport = null;
                            InternalSubchannel.this.addressIndex.reset();
                            InternalSubchannel.this.scheduleBackoff(s2);
                        } else {
                            InternalSubchannel.this.startNewTransport();
                        }
                    }
                }
            });
        }

        @Override
        public void transportTerminated() {
            Preconditions.checkState(this.shutdownInitiated, "transportShutdown() must be called before transportTerminated().");
            InternalSubchannel.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "{0} Terminated", this.transport.getLogId());
            InternalSubchannel.this.channelz.removeClientSocket(this.transport);
            InternalSubchannel.this.handleTransportInUseState(this.transport, false);
            for (ClientTransportFilter filter2 : InternalSubchannel.this.transportFilters) {
                filter2.transportTerminated(this.transport.getAttributes());
            }
            InternalSubchannel.this.syncContext.execute(new Runnable(){

                @Override
                public void run() {
                    InternalSubchannel.this.transports.remove(TransportListener.this.transport);
                    if (InternalSubchannel.this.state.getState() == ConnectivityState.SHUTDOWN && InternalSubchannel.this.transports.isEmpty()) {
                        InternalSubchannel.this.handleTermination();
                    }
                }
            });
        }
    }
}

