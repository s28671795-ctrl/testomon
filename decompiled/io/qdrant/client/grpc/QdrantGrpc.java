/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Descriptors;
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
import io.qdrant.client.grpc.QdrantOuterClass;

@GrpcGenerated
public final class QdrantGrpc {
    public static final String SERVICE_NAME = "qdrant.Qdrant";
    private static volatile MethodDescriptor<QdrantOuterClass.HealthCheckRequest, QdrantOuterClass.HealthCheckReply> getHealthCheckMethod;
    private static final int METHODID_HEALTH_CHECK = 0;
    private static volatile ServiceDescriptor serviceDescriptor;

    private QdrantGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Qdrant/HealthCheck", requestType=QdrantOuterClass.HealthCheckRequest.class, responseType=QdrantOuterClass.HealthCheckReply.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<QdrantOuterClass.HealthCheckRequest, QdrantOuterClass.HealthCheckReply> getHealthCheckMethod() {
        MethodDescriptor<QdrantOuterClass.HealthCheckRequest, QdrantOuterClass.HealthCheckReply> getHealthCheckMethod = QdrantGrpc.getHealthCheckMethod;
        if (getHealthCheckMethod != null) return getHealthCheckMethod;
        Class<QdrantGrpc> clazz = QdrantGrpc.class;
        synchronized (QdrantGrpc.class) {
            getHealthCheckMethod = QdrantGrpc.getHealthCheckMethod;
            if (getHealthCheckMethod != null) return getHealthCheckMethod;
            QdrantGrpc.getHealthCheckMethod = getHealthCheckMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "HealthCheck")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(QdrantOuterClass.HealthCheckRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(QdrantOuterClass.HealthCheckReply.getDefaultInstance())).setSchemaDescriptor(new QdrantMethodDescriptorSupplier("HealthCheck")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getHealthCheckMethod;
        }
    }

    public static QdrantStub newStub(Channel channel) {
        AbstractStub.StubFactory<QdrantStub> factory2 = new AbstractStub.StubFactory<QdrantStub>(){

            @Override
            public QdrantStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantStub(channel, callOptions);
            }
        };
        return QdrantStub.newStub(factory2, channel);
    }

    public static QdrantBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<QdrantBlockingStub> factory2 = new AbstractStub.StubFactory<QdrantBlockingStub>(){

            @Override
            public QdrantBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantBlockingStub(channel, callOptions);
            }
        };
        return QdrantBlockingStub.newStub(factory2, channel);
    }

    public static QdrantFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<QdrantFutureStub> factory2 = new AbstractStub.StubFactory<QdrantFutureStub>(){

            @Override
            public QdrantFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantFutureStub(channel, callOptions);
            }
        };
        return QdrantFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(QdrantGrpc.getServiceDescriptor()).addMethod(QdrantGrpc.getHealthCheckMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<QdrantGrpc> clazz = QdrantGrpc.class;
        synchronized (QdrantGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new QdrantFileDescriptorSupplier()).addMethod(QdrantGrpc.getHealthCheckMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class QdrantMethodDescriptorSupplier
    extends QdrantBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        QdrantMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class QdrantStub
    extends AbstractAsyncStub<QdrantStub> {
        private QdrantStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantStub build(Channel channel, CallOptions callOptions) {
            return new QdrantStub(channel, callOptions);
        }

        public void healthCheck(QdrantOuterClass.HealthCheckRequest request, StreamObserver<QdrantOuterClass.HealthCheckReply> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(QdrantGrpc.getHealthCheckMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class QdrantBlockingStub
    extends AbstractBlockingStub<QdrantBlockingStub> {
        private QdrantBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantBlockingStub build(Channel channel, CallOptions callOptions) {
            return new QdrantBlockingStub(channel, callOptions);
        }

        public QdrantOuterClass.HealthCheckReply healthCheck(QdrantOuterClass.HealthCheckRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), QdrantGrpc.getHealthCheckMethod(), this.getCallOptions(), request);
        }
    }

    public static final class QdrantFutureStub
    extends AbstractFutureStub<QdrantFutureStub> {
        private QdrantFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantFutureStub build(Channel channel, CallOptions callOptions) {
            return new QdrantFutureStub(channel, callOptions);
        }

        public ListenableFuture<QdrantOuterClass.HealthCheckReply> healthCheck(QdrantOuterClass.HealthCheckRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(QdrantGrpc.getHealthCheckMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.healthCheck((QdrantOuterClass.HealthCheckRequest)request, responseObserver);
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
        default public void healthCheck(QdrantOuterClass.HealthCheckRequest request, StreamObserver<QdrantOuterClass.HealthCheckReply> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(QdrantGrpc.getHealthCheckMethod(), responseObserver);
        }
    }

    private static final class QdrantFileDescriptorSupplier
    extends QdrantBaseDescriptorSupplier {
        QdrantFileDescriptorSupplier() {
        }
    }

    private static abstract class QdrantBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        QdrantBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return QdrantOuterClass.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Qdrant");
        }
    }

    public static abstract class QdrantImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return QdrantGrpc.bindService(this);
        }
    }
}

