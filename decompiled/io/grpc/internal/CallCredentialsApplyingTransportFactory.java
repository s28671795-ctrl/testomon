/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.CompositeCallCredentials;
import io.grpc.InternalMayRequireSpecificExecutor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.FailingClientStream;
import io.grpc.internal.ForwardingConnectionClientTransport;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.MetadataApplierImpl;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

final class CallCredentialsApplyingTransportFactory
implements ClientTransportFactory {
    private final ClientTransportFactory delegate;
    private final CallCredentials channelCallCredentials;
    private final Executor appExecutor;

    CallCredentialsApplyingTransportFactory(ClientTransportFactory delegate, CallCredentials channelCallCredentials, Executor appExecutor) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        this.channelCallCredentials = channelCallCredentials;
        this.appExecutor = Preconditions.checkNotNull(appExecutor, "appExecutor");
    }

    @Override
    public ConnectionClientTransport newClientTransport(SocketAddress serverAddress, ClientTransportFactory.ClientTransportOptions options, ChannelLogger channelLogger) {
        return new CallCredentialsApplyingTransport(this.delegate.newClientTransport(serverAddress, options, channelLogger), options.getAuthority());
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.delegate.getScheduledExecutorService();
    }

    @Override
    public ClientTransportFactory.SwapChannelCredentialsResult swapChannelCredentials(ChannelCredentials channelCreds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        this.delegate.close();
    }

    @Override
    public Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
        return this.delegate.getSupportedSocketAddressTypes();
    }

    private class CallCredentialsApplyingTransport
    extends ForwardingConnectionClientTransport {
        private final ConnectionClientTransport delegate;
        private final String authority;
        private final AtomicInteger pendingApplier = new AtomicInteger(-2147483647);
        private volatile Status shutdownStatus;
        @GuardedBy(value="this")
        private Status savedShutdownStatus;
        @GuardedBy(value="this")
        private Status savedShutdownNowStatus;
        private final MetadataApplierImpl.MetadataApplierListener applierListener = new MetadataApplierImpl.MetadataApplierListener(){

            @Override
            public void onComplete() {
                if (CallCredentialsApplyingTransport.this.pendingApplier.decrementAndGet() == 0) {
                    CallCredentialsApplyingTransport.this.maybeShutdown();
                }
            }
        };

        CallCredentialsApplyingTransport(ConnectionClientTransport delegate, String authority) {
            this.delegate = Preconditions.checkNotNull(delegate, "delegate");
            this.authority = Preconditions.checkNotNull(authority, "authority");
        }

        @Override
        protected ConnectionClientTransport delegate() {
            return this.delegate;
        }

        @Override
        public ClientStream newStream(final MethodDescriptor<?, ?> method2, Metadata headers, final CallOptions callOptions, ClientStreamTracer[] tracers) {
            CallCredentials creds = callOptions.getCredentials();
            if (creds == null) {
                creds = CallCredentialsApplyingTransportFactory.this.channelCallCredentials;
            } else if (CallCredentialsApplyingTransportFactory.this.channelCallCredentials != null) {
                creds = new CompositeCallCredentials(CallCredentialsApplyingTransportFactory.this.channelCallCredentials, creds);
            }
            if (creds != null) {
                MetadataApplierImpl applier = new MetadataApplierImpl(this.delegate, method2, headers, callOptions, this.applierListener, tracers);
                if (this.pendingApplier.incrementAndGet() > 0) {
                    this.applierListener.onComplete();
                    return new FailingClientStream(this.shutdownStatus, tracers);
                }
                CallCredentials.RequestInfo requestInfo = new CallCredentials.RequestInfo(){

                    @Override
                    public MethodDescriptor<?, ?> getMethodDescriptor() {
                        return method2;
                    }

                    @Override
                    public CallOptions getCallOptions() {
                        return callOptions;
                    }

                    @Override
                    public SecurityLevel getSecurityLevel() {
                        return MoreObjects.firstNonNull(CallCredentialsApplyingTransport.this.delegate.getAttributes().get(GrpcAttributes.ATTR_SECURITY_LEVEL), SecurityLevel.NONE);
                    }

                    @Override
                    public String getAuthority() {
                        return MoreObjects.firstNonNull(callOptions.getAuthority(), CallCredentialsApplyingTransport.this.authority);
                    }

                    @Override
                    public Attributes getTransportAttrs() {
                        return CallCredentialsApplyingTransport.this.delegate.getAttributes();
                    }
                };
                try {
                    Executor executor2 = creds instanceof InternalMayRequireSpecificExecutor && ((InternalMayRequireSpecificExecutor)((Object)creds)).isSpecificExecutorRequired() && callOptions.getExecutor() != null ? callOptions.getExecutor() : CallCredentialsApplyingTransportFactory.this.appExecutor;
                    creds.applyRequestMetadata(requestInfo, executor2, applier);
                }
                catch (Throwable t2) {
                    applier.fail(Status.UNAUTHENTICATED.withDescription("Credentials should use fail() instead of throwing exceptions").withCause(t2));
                }
                return applier.returnStream();
            }
            if (this.pendingApplier.get() >= 0) {
                return new FailingClientStream(this.shutdownStatus, tracers);
            }
            return this.delegate.newStream(method2, headers, callOptions, tracers);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void shutdown(Status status) {
            Preconditions.checkNotNull(status, "status");
            CallCredentialsApplyingTransport callCredentialsApplyingTransport = this;
            synchronized (callCredentialsApplyingTransport) {
                if (this.pendingApplier.get() >= 0) {
                    return;
                }
                this.shutdownStatus = status;
                this.pendingApplier.addAndGet(Integer.MAX_VALUE);
                if (this.pendingApplier.get() != 0) {
                    this.savedShutdownStatus = status;
                    return;
                }
            }
            super.shutdown(status);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void shutdownNow(Status status) {
            Preconditions.checkNotNull(status, "status");
            CallCredentialsApplyingTransport callCredentialsApplyingTransport = this;
            synchronized (callCredentialsApplyingTransport) {
                if (this.pendingApplier.get() < 0) {
                    this.shutdownStatus = status;
                    this.pendingApplier.addAndGet(Integer.MAX_VALUE);
                } else if (this.savedShutdownNowStatus != null) {
                    return;
                }
                if (this.pendingApplier.get() != 0) {
                    this.savedShutdownNowStatus = status;
                    return;
                }
            }
            super.shutdownNow(status);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void maybeShutdown() {
            Status maybeShutdownNow;
            Status maybeShutdown;
            CallCredentialsApplyingTransport callCredentialsApplyingTransport = this;
            synchronized (callCredentialsApplyingTransport) {
                if (this.pendingApplier.get() != 0) {
                    return;
                }
                maybeShutdown = this.savedShutdownStatus;
                maybeShutdownNow = this.savedShutdownNowStatus;
                this.savedShutdownStatus = null;
                this.savedShutdownNowStatus = null;
            }
            if (maybeShutdown != null) {
                super.shutdown(maybeShutdown);
            }
            if (maybeShutdownNow != null) {
                super.shutdownNow(maybeShutdownNow);
            }
        }
    }
}

