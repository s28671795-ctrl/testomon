/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientStreamTracer;
import io.grpc.Context;
import io.grpc.InternalConfigSelector;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ClientCallImpl;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.FailingClientTransport;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InternalSubchannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

final class SubchannelChannel
extends Channel {
    @VisibleForTesting
    static final Status NOT_READY_ERROR = Status.UNAVAILABLE.withDescription("Subchannel is NOT READY");
    @VisibleForTesting
    static final Status WAIT_FOR_READY_ERROR = Status.UNAVAILABLE.withDescription("wait-for-ready RPC is not supported on Subchannel.asChannel()");
    private static final FailingClientTransport notReadyTransport = new FailingClientTransport(NOT_READY_ERROR, ClientStreamListener.RpcProgress.MISCARRIED);
    private final InternalSubchannel subchannel;
    private final Executor executor;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    private final CallTracer callsTracer;
    private final AtomicReference<InternalConfigSelector> configSelector;
    private final ClientCallImpl.ClientStreamProvider transportProvider = new ClientCallImpl.ClientStreamProvider(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ClientStream newStream(MethodDescriptor<?, ?> method2, CallOptions callOptions, Metadata headers, Context context) {
            ClientTransport transport = SubchannelChannel.this.subchannel.getTransport();
            if (transport == null) {
                transport = notReadyTransport;
            }
            ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(callOptions, headers, 0, false);
            Context origContext = context.attach();
            try {
                ClientStream clientStream = transport.newStream(method2, headers, callOptions, tracers);
                return clientStream;
            }
            finally {
                context.detach(origContext);
            }
        }
    };

    SubchannelChannel(InternalSubchannel subchannel, Executor executor2, ScheduledExecutorService deadlineCancellationExecutor, CallTracer callsTracer, AtomicReference<InternalConfigSelector> configSelector) {
        this.subchannel = Preconditions.checkNotNull(subchannel, "subchannel");
        this.executor = Preconditions.checkNotNull(executor2, "executor");
        this.deadlineCancellationExecutor = Preconditions.checkNotNull(deadlineCancellationExecutor, "deadlineCancellationExecutor");
        this.callsTracer = Preconditions.checkNotNull(callsTracer, "callsTracer");
        this.configSelector = Preconditions.checkNotNull(configSelector, "configSelector");
    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        Executor effectiveExecutor;
        Executor executor2 = effectiveExecutor = callOptions.getExecutor() == null ? this.executor : callOptions.getExecutor();
        if (callOptions.isWaitForReady()) {
            return new ClientCall<RequestT, ResponseT>(){

                @Override
                public void start(final ClientCall.Listener<ResponseT> listener, Metadata headers) {
                    effectiveExecutor.execute(new Runnable(){

                        @Override
                        public void run() {
                            listener.onClose(WAIT_FOR_READY_ERROR, new Metadata());
                        }
                    });
                }

                @Override
                public void request(int numMessages) {
                }

                @Override
                public void cancel(String message, Throwable cause) {
                }

                @Override
                public void halfClose() {
                }

                @Override
                public void sendMessage(RequestT message) {
                }
            };
        }
        return new ClientCallImpl<RequestT, ResponseT>(methodDescriptor, effectiveExecutor, callOptions.withOption(GrpcUtil.CALL_OPTIONS_RPC_OWNED_BY_BALANCER, Boolean.TRUE), this.transportProvider, this.deadlineCancellationExecutor, this.callsTracer, this.configSelector.get());
    }

    @Override
    public String authority() {
        return this.subchannel.getAuthority();
    }
}

