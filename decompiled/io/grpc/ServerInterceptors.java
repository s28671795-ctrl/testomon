/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.BindableService;
import io.grpc.ExperimentalApi;
import io.grpc.KnownLength;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.PartialForwardingServerCall;
import io.grpc.PartialForwardingServerCallListener;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ServerInterceptors {
    private ServerInterceptors() {
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serviceDef, ServerInterceptor ... interceptors) {
        return ServerInterceptors.interceptForward(serviceDef, Arrays.asList(interceptors));
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, ServerInterceptor ... interceptors) {
        return ServerInterceptors.interceptForward(bindableService.bindService(), Arrays.asList(interceptors));
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serviceDef, List<? extends ServerInterceptor> interceptors) {
        ArrayList<? extends ServerInterceptor> copy = new ArrayList<ServerInterceptor>(interceptors);
        Collections.reverse(copy);
        return ServerInterceptors.intercept(serviceDef, copy);
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, List<? extends ServerInterceptor> interceptors) {
        return ServerInterceptors.interceptForward(bindableService.bindService(), interceptors);
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serviceDef, ServerInterceptor ... interceptors) {
        return ServerInterceptors.intercept(serviceDef, Arrays.asList(interceptors));
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, ServerInterceptor ... interceptors) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return ServerInterceptors.intercept(bindableService.bindService(), Arrays.asList(interceptors));
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serviceDef, List<? extends ServerInterceptor> interceptors) {
        Preconditions.checkNotNull(serviceDef, "serviceDef");
        if (interceptors.isEmpty()) {
            return serviceDef;
        }
        ServerServiceDefinition.Builder serviceDefBuilder = ServerServiceDefinition.builder(serviceDef.getServiceDescriptor());
        for (ServerMethodDefinition<?, ?> method2 : serviceDef.getMethods()) {
            ServerInterceptors.wrapAndAddMethod(serviceDefBuilder, method2, interceptors);
        }
        return serviceDefBuilder.build();
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, List<? extends ServerInterceptor> interceptors) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return ServerInterceptors.intercept(bindableService.bindService(), interceptors);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1712")
    public static ServerServiceDefinition useInputStreamMessages(ServerServiceDefinition serviceDef) {
        MethodDescriptor.Marshaller<InputStream> marshaller = new MethodDescriptor.Marshaller<InputStream>(){

            @Override
            public InputStream stream(InputStream value) {
                return value;
            }

            @Override
            public InputStream parse(InputStream stream) {
                if (stream.markSupported()) {
                    return stream;
                }
                if (stream instanceof KnownLength) {
                    return new KnownLengthBufferedInputStream(stream);
                }
                return new BufferedInputStream(stream);
            }
        };
        return ServerInterceptors.useMarshalledMessages(serviceDef, marshaller);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1712")
    public static <T> ServerServiceDefinition useMarshalledMessages(ServerServiceDefinition serviceDef, MethodDescriptor.Marshaller<T> marshaller) {
        return ServerInterceptors.useMarshalledMessages(serviceDef, marshaller, marshaller);
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/9870")
    public static <ReqT, RespT> ServerServiceDefinition useMarshalledMessages(ServerServiceDefinition serviceDef, MethodDescriptor.Marshaller<ReqT> requestMarshaller, MethodDescriptor.Marshaller<RespT> responseMarshaller) {
        ArrayList<ServerMethodDefinition<ReqT, RespT>> wrappedMethods = new ArrayList<ServerMethodDefinition<ReqT, RespT>>();
        ArrayList<MethodDescriptor<ReqT, RespT>> wrappedDescriptors = new ArrayList<MethodDescriptor<ReqT, RespT>>();
        for (ServerMethodDefinition<?, ?> serverMethodDefinition : serviceDef.getMethods()) {
            MethodDescriptor<?, ?> methodDescriptor = serverMethodDefinition.getMethodDescriptor();
            MethodDescriptor<ReqT, RespT> methodDescriptor2 = methodDescriptor.toBuilder(requestMarshaller, responseMarshaller).build();
            wrappedDescriptors.add(methodDescriptor2);
            wrappedMethods.add(ServerInterceptors.wrapMethod(serverMethodDefinition, methodDescriptor2));
        }
        ServiceDescriptor.Builder serviceDescriptorBuilder = ServiceDescriptor.newBuilder(serviceDef.getServiceDescriptor().getName()).setSchemaDescriptor(serviceDef.getServiceDescriptor().getSchemaDescriptor());
        for (MethodDescriptor methodDescriptor : wrappedDescriptors) {
            serviceDescriptorBuilder.addMethod(methodDescriptor);
        }
        ServerServiceDefinition.Builder builder = ServerServiceDefinition.builder(serviceDescriptorBuilder.build());
        for (ServerMethodDefinition serverMethodDefinition : wrappedMethods) {
            builder.addMethod(serverMethodDefinition);
        }
        return builder.build();
    }

    private static <ReqT, RespT> void wrapAndAddMethod(ServerServiceDefinition.Builder serviceDefBuilder, ServerMethodDefinition<ReqT, RespT> method2, List<? extends ServerInterceptor> interceptors) {
        ServerCallHandler<ReqT, RespT> callHandler = method2.getServerCallHandler();
        for (ServerInterceptor serverInterceptor : interceptors) {
            callHandler = InterceptCallHandler.create(serverInterceptor, callHandler);
        }
        serviceDefBuilder.addMethod(method2.withServerCallHandler(callHandler));
    }

    static <OReqT, ORespT, WReqT, WRespT> ServerMethodDefinition<WReqT, WRespT> wrapMethod(ServerMethodDefinition<OReqT, ORespT> definition, MethodDescriptor<WReqT, WRespT> wrappedMethod) {
        ServerCallHandler<WReqT, WRespT> wrappedHandler = ServerInterceptors.wrapHandler(definition.getServerCallHandler(), definition.getMethodDescriptor(), wrappedMethod);
        return ServerMethodDefinition.create(wrappedMethod, wrappedHandler);
    }

    private static <OReqT, ORespT, WReqT, WRespT> ServerCallHandler<WReqT, WRespT> wrapHandler(final ServerCallHandler<OReqT, ORespT> originalHandler, final MethodDescriptor<OReqT, ORespT> originalMethod, final MethodDescriptor<WReqT, WRespT> wrappedMethod) {
        return new ServerCallHandler<WReqT, WRespT>(){

            @Override
            public ServerCall.Listener<WReqT> startCall(final ServerCall<WReqT, WRespT> call, Metadata headers) {
                PartialForwardingServerCall unwrappedCall = new PartialForwardingServerCall<OReqT, ORespT>(){

                    @Override
                    protected ServerCall<WReqT, WRespT> delegate() {
                        return call;
                    }

                    @Override
                    public void sendMessage(ORespT message) {
                        InputStream is = originalMethod.streamResponse(message);
                        Object wrappedMessage = wrappedMethod.parseResponse(is);
                        this.delegate().sendMessage(wrappedMessage);
                    }

                    @Override
                    public MethodDescriptor<OReqT, ORespT> getMethodDescriptor() {
                        return originalMethod;
                    }
                };
                final ServerCall.Listener originalListener = originalHandler.startCall(unwrappedCall, headers);
                return new PartialForwardingServerCallListener<WReqT>(){

                    @Override
                    protected ServerCall.Listener<OReqT> delegate() {
                        return originalListener;
                    }

                    @Override
                    public void onMessage(WReqT message) {
                        InputStream is = wrappedMethod.streamRequest(message);
                        Object originalMessage = originalMethod.parseRequest(is);
                        this.delegate().onMessage(originalMessage);
                    }
                };
            }
        };
    }

    static final class InterceptCallHandler<ReqT, RespT>
    implements ServerCallHandler<ReqT, RespT> {
        private final ServerInterceptor interceptor;
        private final ServerCallHandler<ReqT, RespT> callHandler;

        public static <ReqT, RespT> InterceptCallHandler<ReqT, RespT> create(ServerInterceptor interceptor, ServerCallHandler<ReqT, RespT> callHandler) {
            return new InterceptCallHandler<ReqT, RespT>(interceptor, callHandler);
        }

        private InterceptCallHandler(ServerInterceptor interceptor, ServerCallHandler<ReqT, RespT> callHandler) {
            this.interceptor = Preconditions.checkNotNull(interceptor, "interceptor");
            this.callHandler = callHandler;
        }

        @Override
        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> call, Metadata headers) {
            return this.interceptor.interceptCall(call, headers, this.callHandler);
        }
    }

    private static final class KnownLengthBufferedInputStream
    extends BufferedInputStream
    implements KnownLength {
        KnownLengthBufferedInputStream(InputStream in) {
            super(in);
        }
    }
}

