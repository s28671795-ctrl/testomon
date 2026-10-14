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
import io.qdrant.client.grpc.QdrantInternalService;

@GrpcGenerated
public final class QdrantInternalGrpc {
    public static final String SERVICE_NAME = "qdrant.QdrantInternal";
    private static volatile MethodDescriptor<QdrantInternalService.GetConsensusCommitRequest, QdrantInternalService.GetConsensusCommitResponse> getGetConsensusCommitMethod;
    private static volatile MethodDescriptor<QdrantInternalService.WaitOnConsensusCommitRequest, QdrantInternalService.WaitOnConsensusCommitResponse> getWaitOnConsensusCommitMethod;
    private static final int METHODID_GET_CONSENSUS_COMMIT = 0;
    private static final int METHODID_WAIT_ON_CONSENSUS_COMMIT = 1;
    private static volatile ServiceDescriptor serviceDescriptor;

    private QdrantInternalGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.QdrantInternal/GetConsensusCommit", requestType=QdrantInternalService.GetConsensusCommitRequest.class, responseType=QdrantInternalService.GetConsensusCommitResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<QdrantInternalService.GetConsensusCommitRequest, QdrantInternalService.GetConsensusCommitResponse> getGetConsensusCommitMethod() {
        MethodDescriptor<QdrantInternalService.GetConsensusCommitRequest, QdrantInternalService.GetConsensusCommitResponse> getGetConsensusCommitMethod = QdrantInternalGrpc.getGetConsensusCommitMethod;
        if (getGetConsensusCommitMethod != null) return getGetConsensusCommitMethod;
        Class<QdrantInternalGrpc> clazz = QdrantInternalGrpc.class;
        synchronized (QdrantInternalGrpc.class) {
            getGetConsensusCommitMethod = QdrantInternalGrpc.getGetConsensusCommitMethod;
            if (getGetConsensusCommitMethod != null) return getGetConsensusCommitMethod;
            QdrantInternalGrpc.getGetConsensusCommitMethod = getGetConsensusCommitMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "GetConsensusCommit")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(QdrantInternalService.GetConsensusCommitRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(QdrantInternalService.GetConsensusCommitResponse.getDefaultInstance())).setSchemaDescriptor(new QdrantInternalMethodDescriptorSupplier("GetConsensusCommit")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetConsensusCommitMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.QdrantInternal/WaitOnConsensusCommit", requestType=QdrantInternalService.WaitOnConsensusCommitRequest.class, responseType=QdrantInternalService.WaitOnConsensusCommitResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<QdrantInternalService.WaitOnConsensusCommitRequest, QdrantInternalService.WaitOnConsensusCommitResponse> getWaitOnConsensusCommitMethod() {
        MethodDescriptor<QdrantInternalService.WaitOnConsensusCommitRequest, QdrantInternalService.WaitOnConsensusCommitResponse> getWaitOnConsensusCommitMethod = QdrantInternalGrpc.getWaitOnConsensusCommitMethod;
        if (getWaitOnConsensusCommitMethod != null) return getWaitOnConsensusCommitMethod;
        Class<QdrantInternalGrpc> clazz = QdrantInternalGrpc.class;
        synchronized (QdrantInternalGrpc.class) {
            getWaitOnConsensusCommitMethod = QdrantInternalGrpc.getWaitOnConsensusCommitMethod;
            if (getWaitOnConsensusCommitMethod != null) return getWaitOnConsensusCommitMethod;
            QdrantInternalGrpc.getWaitOnConsensusCommitMethod = getWaitOnConsensusCommitMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "WaitOnConsensusCommit")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(QdrantInternalService.WaitOnConsensusCommitRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(QdrantInternalService.WaitOnConsensusCommitResponse.getDefaultInstance())).setSchemaDescriptor(new QdrantInternalMethodDescriptorSupplier("WaitOnConsensusCommit")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getWaitOnConsensusCommitMethod;
        }
    }

    public static QdrantInternalStub newStub(Channel channel) {
        AbstractStub.StubFactory<QdrantInternalStub> factory2 = new AbstractStub.StubFactory<QdrantInternalStub>(){

            @Override
            public QdrantInternalStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantInternalStub(channel, callOptions);
            }
        };
        return QdrantInternalStub.newStub(factory2, channel);
    }

    public static QdrantInternalBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<QdrantInternalBlockingStub> factory2 = new AbstractStub.StubFactory<QdrantInternalBlockingStub>(){

            @Override
            public QdrantInternalBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantInternalBlockingStub(channel, callOptions);
            }
        };
        return QdrantInternalBlockingStub.newStub(factory2, channel);
    }

    public static QdrantInternalFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<QdrantInternalFutureStub> factory2 = new AbstractStub.StubFactory<QdrantInternalFutureStub>(){

            @Override
            public QdrantInternalFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new QdrantInternalFutureStub(channel, callOptions);
            }
        };
        return QdrantInternalFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(QdrantInternalGrpc.getServiceDescriptor()).addMethod(QdrantInternalGrpc.getGetConsensusCommitMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(QdrantInternalGrpc.getWaitOnConsensusCommitMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<QdrantInternalGrpc> clazz = QdrantInternalGrpc.class;
        synchronized (QdrantInternalGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new QdrantInternalFileDescriptorSupplier()).addMethod(QdrantInternalGrpc.getGetConsensusCommitMethod()).addMethod(QdrantInternalGrpc.getWaitOnConsensusCommitMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class QdrantInternalMethodDescriptorSupplier
    extends QdrantInternalBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        QdrantInternalMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class QdrantInternalStub
    extends AbstractAsyncStub<QdrantInternalStub> {
        private QdrantInternalStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantInternalStub build(Channel channel, CallOptions callOptions) {
            return new QdrantInternalStub(channel, callOptions);
        }

        public void getConsensusCommit(QdrantInternalService.GetConsensusCommitRequest request, StreamObserver<QdrantInternalService.GetConsensusCommitResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(QdrantInternalGrpc.getGetConsensusCommitMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void waitOnConsensusCommit(QdrantInternalService.WaitOnConsensusCommitRequest request, StreamObserver<QdrantInternalService.WaitOnConsensusCommitResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(QdrantInternalGrpc.getWaitOnConsensusCommitMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class QdrantInternalBlockingStub
    extends AbstractBlockingStub<QdrantInternalBlockingStub> {
        private QdrantInternalBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantInternalBlockingStub build(Channel channel, CallOptions callOptions) {
            return new QdrantInternalBlockingStub(channel, callOptions);
        }

        public QdrantInternalService.GetConsensusCommitResponse getConsensusCommit(QdrantInternalService.GetConsensusCommitRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), QdrantInternalGrpc.getGetConsensusCommitMethod(), this.getCallOptions(), request);
        }

        public QdrantInternalService.WaitOnConsensusCommitResponse waitOnConsensusCommit(QdrantInternalService.WaitOnConsensusCommitRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), QdrantInternalGrpc.getWaitOnConsensusCommitMethod(), this.getCallOptions(), request);
        }
    }

    public static final class QdrantInternalFutureStub
    extends AbstractFutureStub<QdrantInternalFutureStub> {
        private QdrantInternalFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected QdrantInternalFutureStub build(Channel channel, CallOptions callOptions) {
            return new QdrantInternalFutureStub(channel, callOptions);
        }

        public ListenableFuture<QdrantInternalService.GetConsensusCommitResponse> getConsensusCommit(QdrantInternalService.GetConsensusCommitRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(QdrantInternalGrpc.getGetConsensusCommitMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<QdrantInternalService.WaitOnConsensusCommitResponse> waitOnConsensusCommit(QdrantInternalService.WaitOnConsensusCommitRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(QdrantInternalGrpc.getWaitOnConsensusCommitMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.getConsensusCommit((QdrantInternalService.GetConsensusCommitRequest)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.waitOnConsensusCommit((QdrantInternalService.WaitOnConsensusCommitRequest)request, responseObserver);
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
        default public void getConsensusCommit(QdrantInternalService.GetConsensusCommitRequest request, StreamObserver<QdrantInternalService.GetConsensusCommitResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(QdrantInternalGrpc.getGetConsensusCommitMethod(), responseObserver);
        }

        default public void waitOnConsensusCommit(QdrantInternalService.WaitOnConsensusCommitRequest request, StreamObserver<QdrantInternalService.WaitOnConsensusCommitResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(QdrantInternalGrpc.getWaitOnConsensusCommitMethod(), responseObserver);
        }
    }

    private static final class QdrantInternalFileDescriptorSupplier
    extends QdrantInternalBaseDescriptorSupplier {
        QdrantInternalFileDescriptorSupplier() {
        }
    }

    private static abstract class QdrantInternalBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        QdrantInternalBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return QdrantInternalService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("QdrantInternal");
        }
    }

    public static abstract class QdrantInternalImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return QdrantInternalGrpc.bindService(this);
        }
    }
}

