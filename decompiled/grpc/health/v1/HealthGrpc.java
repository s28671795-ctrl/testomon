/*
 * Decompiled with CFR 0.152.
 */
package grpc.health.v1;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Descriptors;
import grpc.health.v1.HealthCheck;
import io.grpc.BindableService;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.protobuf.ProtoFileDescriptorSupplier;
import io.grpc.protobuf.ProtoMethodDescriptorSupplier;
import io.grpc.protobuf.ProtoServiceDescriptorSupplier;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.AbstractAsyncStub;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractFutureStub;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.annotations.GrpcGenerated;
import io.grpc.stub.annotations.RpcMethod;

@GrpcGenerated
public final class HealthGrpc {
    public static final String SERVICE_NAME = "grpc.health.v1.Health";
    private static volatile MethodDescriptor<HealthCheck.HealthCheckRequest, HealthCheck.HealthCheckResponse> getCheckMethod;
    private static final int METHODID_CHECK = 0;
    private static volatile ServiceDescriptor serviceDescriptor;

    private HealthGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="grpc.health.v1.Health/Check", requestType=HealthCheck.HealthCheckRequest.class, responseType=HealthCheck.HealthCheckResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<HealthCheck.HealthCheckRequest, HealthCheck.HealthCheckResponse> getCheckMethod() {
        MethodDescriptor<HealthCheck.HealthCheckRequest, HealthCheck.HealthCheckResponse> getCheckMethod = HealthGrpc.getCheckMethod;
        if (getCheckMethod != null) return getCheckMethod;
        Class<HealthGrpc> clazz = HealthGrpc.class;
        synchronized (HealthGrpc.class) {
            getCheckMethod = HealthGrpc.getCheckMethod;
            if (getCheckMethod != null) return getCheckMethod;
            HealthGrpc.getCheckMethod = getCheckMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Check")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(HealthCheck.HealthCheckRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(HealthCheck.HealthCheckResponse.getDefaultInstance())).setSchemaDescriptor(new HealthMethodDescriptorSupplier("Check")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCheckMethod;
        }
    }

    public static HealthStub newStub(Channel channel) {
        AbstractStub.StubFactory<HealthStub> factory2 = new AbstractStub.StubFactory<HealthStub>(){

            @Override
            public HealthStub newStub(Channel channel, CallOptions callOptions) {
                return new HealthStub(channel, callOptions);
            }
        };
        return HealthStub.newStub(factory2, channel);
    }

    public static HealthBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<HealthBlockingStub> factory2 = new AbstractStub.StubFactory<HealthBlockingStub>(){

            @Override
            public HealthBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new HealthBlockingStub(channel, callOptions);
            }
        };
        return HealthBlockingStub.newStub(factory2, channel);
    }

    public static HealthFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<HealthFutureStub> factory2 = new AbstractStub.StubFactory<HealthFutureStub>(){

            @Override
            public HealthFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new HealthFutureStub(channel, callOptions);
            }
        };
        return HealthFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(HealthGrpc.getServiceDescriptor()).addMethod(HealthGrpc.getCheckMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<HealthGrpc> clazz = HealthGrpc.class;
        synchronized (HealthGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new HealthFileDescriptorSupplier()).addMethod(HealthGrpc.getCheckMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class HealthMethodDescriptorSupplier
    extends HealthBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        HealthMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class HealthStub
    extends AbstractAsyncStub<HealthStub> {
        private HealthStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected HealthStub build(Channel channel, CallOptions callOptions) {
            return new HealthStub(channel, callOptions);
        }

        public void check(HealthCheck.HealthCheckRequest request, StreamObserver<HealthCheck.HealthCheckResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(HealthGrpc.getCheckMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class HealthBlockingStub
    extends AbstractBlockingStub<HealthBlockingStub> {
        private HealthBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected HealthBlockingStub build(Channel channel, CallOptions callOptions) {
            return new HealthBlockingStub(channel, callOptions);
        }

        public HealthCheck.HealthCheckResponse check(HealthCheck.HealthCheckRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), HealthGrpc.getCheckMethod(), this.getCallOptions(), request);
        }
    }

    public static final class HealthFutureStub
    extends AbstractFutureStub<HealthFutureStub> {
        private HealthFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected HealthFutureStub build(Channel channel, CallOptions callOptions) {
            return new HealthFutureStub(channel, callOptions);
        }

        public ListenableFuture<HealthCheck.HealthCheckResponse> check(HealthCheck.HealthCheckRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(HealthGrpc.getCheckMethod(), this.getCallOptions()), request);
        }
    }

    private static final class MethodHandlers<Req, Resp>
    implements ServerCalls.UnaryMethod<Req, Resp>,
    ServerCalls.ServerStreamingMethod<Req, Resp>,
    ServerCalls.ClientStreamingMethod<Req, Resp>,
    ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final AsyncService serviceImpl;
        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @Override
        public void invoke(Req request, StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                case 0: {
                    this.serviceImpl.check((HealthCheck.HealthCheckRequest)request, responseObserver);
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }

        @Override
        public StreamObserver<Req> invoke(StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                default: 
            }
            throw new AssertionError();
        }
    }

    public static interface AsyncService {
        default public void check(HealthCheck.HealthCheckRequest request, StreamObserver<HealthCheck.HealthCheckResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(HealthGrpc.getCheckMethod(), responseObserver);
        }
    }

    private static final class HealthFileDescriptorSupplier
    extends HealthBaseDescriptorSupplier {
        HealthFileDescriptorSupplier() {
        }
    }

    private static abstract class HealthBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        HealthBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return HealthCheck.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Health");
        }
    }

    public static abstract class HealthImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return HealthGrpc.bindService(this);
        }
    }
}

