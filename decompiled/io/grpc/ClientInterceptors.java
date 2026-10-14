/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.PartialForwardingClientCall;
import io.grpc.PartialForwardingClientCallListener;
import io.grpc.Status;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientInterceptors {
    private static final ClientCall<Object, Object> NOOP_CALL = new ClientCall<Object, Object>(){

        @Override
        public void start(ClientCall.Listener<Object> responseListener, Metadata headers) {
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
        public void sendMessage(Object message) {
        }

        @Override
        public boolean isReady() {
            return false;
        }
    };

    private ClientInterceptors() {
    }

    public static Channel interceptForward(Channel channel, ClientInterceptor ... interceptors) {
        return ClientInterceptors.interceptForward(channel, Arrays.asList(interceptors));
    }

    public static Channel interceptForward(Channel channel, List<? extends ClientInterceptor> interceptors) {
        ArrayList<? extends ClientInterceptor> copy = new ArrayList<ClientInterceptor>(interceptors);
        Collections.reverse(copy);
        return ClientInterceptors.intercept(channel, copy);
    }

    public static Channel intercept(Channel channel, ClientInterceptor ... interceptors) {
        return ClientInterceptors.intercept(channel, Arrays.asList(interceptors));
    }

    public static Channel intercept(Channel channel, List<? extends ClientInterceptor> interceptors) {
        Preconditions.checkNotNull(channel, "channel");
        for (ClientInterceptor clientInterceptor : interceptors) {
            channel = new InterceptorChannel(channel, clientInterceptor);
        }
        return channel;
    }

    static <WReqT, WRespT> ClientInterceptor wrapClientInterceptor(final ClientInterceptor interceptor, final MethodDescriptor.Marshaller<WReqT> reqMarshaller, final MethodDescriptor.Marshaller<WRespT> respMarshaller) {
        return new ClientInterceptor(){

            @Override
            public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(final MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions, Channel next) {
                MethodDescriptor wrappedMethod = method2.toBuilder(reqMarshaller, respMarshaller).build();
                final ClientCall wrappedCall = interceptor.interceptCall(wrappedMethod, callOptions, next);
                return new PartialForwardingClientCall<ReqT, RespT>(){

                    @Override
                    public void start(final ClientCall.Listener<RespT> responseListener, Metadata headers) {
                        wrappedCall.start(new PartialForwardingClientCallListener<WRespT>(){

                            @Override
                            public void onMessage(WRespT wMessage) {
                                InputStream bytes = respMarshaller.stream(wMessage);
                                Object message = method2.getResponseMarshaller().parse(bytes);
                                responseListener.onMessage(message);
                            }

                            @Override
                            protected ClientCall.Listener<?> delegate() {
                                return responseListener;
                            }
                        }, headers);
                    }

                    @Override
                    public void sendMessage(ReqT message) {
                        InputStream bytes = method2.getRequestMarshaller().stream(message);
                        Object wReq = reqMarshaller.parse(bytes);
                        wrappedCall.sendMessage(wReq);
                    }

                    @Override
                    protected ClientCall<?, ?> delegate() {
                        return wrappedCall;
                    }
                };
            }
        };
    }

    public static abstract class CheckedForwardingClientCall<ReqT, RespT>
    extends ForwardingClientCall<ReqT, RespT> {
        private ClientCall<ReqT, RespT> delegate;

        protected abstract void checkedStart(ClientCall.Listener<RespT> var1, Metadata var2) throws Exception;

        protected CheckedForwardingClientCall(ClientCall<ReqT, RespT> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected final ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }

        @Override
        public final void start(ClientCall.Listener<RespT> responseListener, Metadata headers) {
            try {
                this.checkedStart(responseListener, headers);
            }
            catch (Exception e) {
                this.delegate = NOOP_CALL;
                Metadata trailers = Status.trailersFromThrowable(e);
                responseListener.onClose(Status.fromThrowable(e), trailers != null ? trailers : new Metadata());
            }
        }
    }

    private static class InterceptorChannel
    extends Channel {
        private final Channel channel;
        private final ClientInterceptor interceptor;

        private InterceptorChannel(Channel channel, ClientInterceptor interceptor) {
            this.channel = channel;
            this.interceptor = Preconditions.checkNotNull(interceptor, "interceptor");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions) {
            return this.interceptor.interceptCall(method2, callOptions, this.channel);
        }

        @Override
        public String authority() {
            return this.channel.authority();
        }
    }
}

