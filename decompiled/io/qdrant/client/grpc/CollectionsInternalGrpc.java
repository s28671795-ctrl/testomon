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
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.CollectionsInternalService;

@GrpcGenerated
public final class CollectionsInternalGrpc {
    public static final String SERVICE_NAME = "qdrant.CollectionsInternal";
    private static volatile MethodDescriptor<CollectionsInternalService.GetCollectionInfoRequestInternal, Collections.GetCollectionInfoResponse> getGetMethod;
    private static volatile MethodDescriptor<CollectionsInternalService.InitiateShardTransferRequest, Collections.CollectionOperationResponse> getInitiateMethod;
    private static volatile MethodDescriptor<CollectionsInternalService.WaitForShardStateRequest, Collections.CollectionOperationResponse> getWaitForShardStateMethod;
    private static volatile MethodDescriptor<CollectionsInternalService.GetShardRecoveryPointRequest, CollectionsInternalService.GetShardRecoveryPointResponse> getGetShardRecoveryPointMethod;
    private static volatile MethodDescriptor<CollectionsInternalService.UpdateShardCutoffPointRequest, Collections.CollectionOperationResponse> getUpdateShardCutoffPointMethod;
    private static final int METHODID_GET = 0;
    private static final int METHODID_INITIATE = 1;
    private static final int METHODID_WAIT_FOR_SHARD_STATE = 2;
    private static final int METHODID_GET_SHARD_RECOVERY_POINT = 3;
    private static final int METHODID_UPDATE_SHARD_CUTOFF_POINT = 4;
    private static volatile ServiceDescriptor serviceDescriptor;

    private CollectionsInternalGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.CollectionsInternal/Get", requestType=CollectionsInternalService.GetCollectionInfoRequestInternal.class, responseType=Collections.GetCollectionInfoResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<CollectionsInternalService.GetCollectionInfoRequestInternal, Collections.GetCollectionInfoResponse> getGetMethod() {
        MethodDescriptor<CollectionsInternalService.GetCollectionInfoRequestInternal, Collections.GetCollectionInfoResponse> getGetMethod = CollectionsInternalGrpc.getGetMethod;
        if (getGetMethod != null) return getGetMethod;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            getGetMethod = CollectionsInternalGrpc.getGetMethod;
            if (getGetMethod != null) return getGetMethod;
            CollectionsInternalGrpc.getGetMethod = getGetMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Get")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(CollectionsInternalService.GetCollectionInfoRequestInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.GetCollectionInfoResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsInternalMethodDescriptorSupplier("Get")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.CollectionsInternal/Initiate", requestType=CollectionsInternalService.InitiateShardTransferRequest.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<CollectionsInternalService.InitiateShardTransferRequest, Collections.CollectionOperationResponse> getInitiateMethod() {
        MethodDescriptor<CollectionsInternalService.InitiateShardTransferRequest, Collections.CollectionOperationResponse> getInitiateMethod = CollectionsInternalGrpc.getInitiateMethod;
        if (getInitiateMethod != null) return getInitiateMethod;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            getInitiateMethod = CollectionsInternalGrpc.getInitiateMethod;
            if (getInitiateMethod != null) return getInitiateMethod;
            CollectionsInternalGrpc.getInitiateMethod = getInitiateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Initiate")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(CollectionsInternalService.InitiateShardTransferRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsInternalMethodDescriptorSupplier("Initiate")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getInitiateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.CollectionsInternal/WaitForShardState", requestType=CollectionsInternalService.WaitForShardStateRequest.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<CollectionsInternalService.WaitForShardStateRequest, Collections.CollectionOperationResponse> getWaitForShardStateMethod() {
        MethodDescriptor<CollectionsInternalService.WaitForShardStateRequest, Collections.CollectionOperationResponse> getWaitForShardStateMethod = CollectionsInternalGrpc.getWaitForShardStateMethod;
        if (getWaitForShardStateMethod != null) return getWaitForShardStateMethod;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            getWaitForShardStateMethod = CollectionsInternalGrpc.getWaitForShardStateMethod;
            if (getWaitForShardStateMethod != null) return getWaitForShardStateMethod;
            CollectionsInternalGrpc.getWaitForShardStateMethod = getWaitForShardStateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "WaitForShardState")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(CollectionsInternalService.WaitForShardStateRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsInternalMethodDescriptorSupplier("WaitForShardState")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getWaitForShardStateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.CollectionsInternal/GetShardRecoveryPoint", requestType=CollectionsInternalService.GetShardRecoveryPointRequest.class, responseType=CollectionsInternalService.GetShardRecoveryPointResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<CollectionsInternalService.GetShardRecoveryPointRequest, CollectionsInternalService.GetShardRecoveryPointResponse> getGetShardRecoveryPointMethod() {
        MethodDescriptor<CollectionsInternalService.GetShardRecoveryPointRequest, CollectionsInternalService.GetShardRecoveryPointResponse> getGetShardRecoveryPointMethod = CollectionsInternalGrpc.getGetShardRecoveryPointMethod;
        if (getGetShardRecoveryPointMethod != null) return getGetShardRecoveryPointMethod;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            getGetShardRecoveryPointMethod = CollectionsInternalGrpc.getGetShardRecoveryPointMethod;
            if (getGetShardRecoveryPointMethod != null) return getGetShardRecoveryPointMethod;
            CollectionsInternalGrpc.getGetShardRecoveryPointMethod = getGetShardRecoveryPointMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "GetShardRecoveryPoint")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(CollectionsInternalService.GetShardRecoveryPointRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(CollectionsInternalService.GetShardRecoveryPointResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsInternalMethodDescriptorSupplier("GetShardRecoveryPoint")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetShardRecoveryPointMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.CollectionsInternal/UpdateShardCutoffPoint", requestType=CollectionsInternalService.UpdateShardCutoffPointRequest.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<CollectionsInternalService.UpdateShardCutoffPointRequest, Collections.CollectionOperationResponse> getUpdateShardCutoffPointMethod() {
        MethodDescriptor<CollectionsInternalService.UpdateShardCutoffPointRequest, Collections.CollectionOperationResponse> getUpdateShardCutoffPointMethod = CollectionsInternalGrpc.getUpdateShardCutoffPointMethod;
        if (getUpdateShardCutoffPointMethod != null) return getUpdateShardCutoffPointMethod;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            getUpdateShardCutoffPointMethod = CollectionsInternalGrpc.getUpdateShardCutoffPointMethod;
            if (getUpdateShardCutoffPointMethod != null) return getUpdateShardCutoffPointMethod;
            CollectionsInternalGrpc.getUpdateShardCutoffPointMethod = getUpdateShardCutoffPointMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateShardCutoffPoint")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(CollectionsInternalService.UpdateShardCutoffPointRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsInternalMethodDescriptorSupplier("UpdateShardCutoffPoint")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateShardCutoffPointMethod;
        }
    }

    public static CollectionsInternalStub newStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsInternalStub> factory2 = new AbstractStub.StubFactory<CollectionsInternalStub>(){

            @Override
            public CollectionsInternalStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsInternalStub(channel, callOptions);
            }
        };
        return CollectionsInternalStub.newStub(factory2, channel);
    }

    public static CollectionsInternalBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsInternalBlockingStub> factory2 = new AbstractStub.StubFactory<CollectionsInternalBlockingStub>(){

            @Override
            public CollectionsInternalBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsInternalBlockingStub(channel, callOptions);
            }
        };
        return CollectionsInternalBlockingStub.newStub(factory2, channel);
    }

    public static CollectionsInternalFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsInternalFutureStub> factory2 = new AbstractStub.StubFactory<CollectionsInternalFutureStub>(){

            @Override
            public CollectionsInternalFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsInternalFutureStub(channel, callOptions);
            }
        };
        return CollectionsInternalFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(CollectionsInternalGrpc.getServiceDescriptor()).addMethod(CollectionsInternalGrpc.getGetMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(CollectionsInternalGrpc.getInitiateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(CollectionsInternalGrpc.getWaitForShardStateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(CollectionsInternalGrpc.getGetShardRecoveryPointMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).addMethod(CollectionsInternalGrpc.getUpdateShardCutoffPointMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 4))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<CollectionsInternalGrpc> clazz = CollectionsInternalGrpc.class;
        synchronized (CollectionsInternalGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new CollectionsInternalFileDescriptorSupplier()).addMethod(CollectionsInternalGrpc.getGetMethod()).addMethod(CollectionsInternalGrpc.getInitiateMethod()).addMethod(CollectionsInternalGrpc.getWaitForShardStateMethod()).addMethod(CollectionsInternalGrpc.getGetShardRecoveryPointMethod()).addMethod(CollectionsInternalGrpc.getUpdateShardCutoffPointMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class CollectionsInternalMethodDescriptorSupplier
    extends CollectionsInternalBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        CollectionsInternalMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class CollectionsInternalStub
    extends AbstractAsyncStub<CollectionsInternalStub> {
        private CollectionsInternalStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsInternalStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsInternalStub(channel, callOptions);
        }

        public void get(CollectionsInternalService.GetCollectionInfoRequestInternal request, StreamObserver<Collections.GetCollectionInfoResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getGetMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void initiate(CollectionsInternalService.InitiateShardTransferRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getInitiateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void waitForShardState(CollectionsInternalService.WaitForShardStateRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getWaitForShardStateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void getShardRecoveryPoint(CollectionsInternalService.GetShardRecoveryPointRequest request, StreamObserver<CollectionsInternalService.GetShardRecoveryPointResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getGetShardRecoveryPointMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateShardCutoffPoint(CollectionsInternalService.UpdateShardCutoffPointRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getUpdateShardCutoffPointMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class CollectionsInternalBlockingStub
    extends AbstractBlockingStub<CollectionsInternalBlockingStub> {
        private CollectionsInternalBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsInternalBlockingStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsInternalBlockingStub(channel, callOptions);
        }

        public Collections.GetCollectionInfoResponse get(CollectionsInternalService.GetCollectionInfoRequestInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsInternalGrpc.getGetMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse initiate(CollectionsInternalService.InitiateShardTransferRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsInternalGrpc.getInitiateMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse waitForShardState(CollectionsInternalService.WaitForShardStateRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsInternalGrpc.getWaitForShardStateMethod(), this.getCallOptions(), request);
        }

        public CollectionsInternalService.GetShardRecoveryPointResponse getShardRecoveryPoint(CollectionsInternalService.GetShardRecoveryPointRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsInternalGrpc.getGetShardRecoveryPointMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse updateShardCutoffPoint(CollectionsInternalService.UpdateShardCutoffPointRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsInternalGrpc.getUpdateShardCutoffPointMethod(), this.getCallOptions(), request);
        }
    }

    public static final class CollectionsInternalFutureStub
    extends AbstractFutureStub<CollectionsInternalFutureStub> {
        private CollectionsInternalFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsInternalFutureStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsInternalFutureStub(channel, callOptions);
        }

        public ListenableFuture<Collections.GetCollectionInfoResponse> get(CollectionsInternalService.GetCollectionInfoRequestInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getGetMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> initiate(CollectionsInternalService.InitiateShardTransferRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getInitiateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> waitForShardState(CollectionsInternalService.WaitForShardStateRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getWaitForShardStateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<CollectionsInternalService.GetShardRecoveryPointResponse> getShardRecoveryPoint(CollectionsInternalService.GetShardRecoveryPointRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getGetShardRecoveryPointMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> updateShardCutoffPoint(CollectionsInternalService.UpdateShardCutoffPointRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsInternalGrpc.getUpdateShardCutoffPointMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.get((CollectionsInternalService.GetCollectionInfoRequestInternal)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.initiate((CollectionsInternalService.InitiateShardTransferRequest)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.waitForShardState((CollectionsInternalService.WaitForShardStateRequest)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.getShardRecoveryPoint((CollectionsInternalService.GetShardRecoveryPointRequest)request, responseObserver);
                    break;
                }
                case 4: {
                    this.serviceImpl.updateShardCutoffPoint((CollectionsInternalService.UpdateShardCutoffPointRequest)request, responseObserver);
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
        default public void get(CollectionsInternalService.GetCollectionInfoRequestInternal request, StreamObserver<Collections.GetCollectionInfoResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsInternalGrpc.getGetMethod(), responseObserver);
        }

        default public void initiate(CollectionsInternalService.InitiateShardTransferRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsInternalGrpc.getInitiateMethod(), responseObserver);
        }

        default public void waitForShardState(CollectionsInternalService.WaitForShardStateRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsInternalGrpc.getWaitForShardStateMethod(), responseObserver);
        }

        default public void getShardRecoveryPoint(CollectionsInternalService.GetShardRecoveryPointRequest request, StreamObserver<CollectionsInternalService.GetShardRecoveryPointResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsInternalGrpc.getGetShardRecoveryPointMethod(), responseObserver);
        }

        default public void updateShardCutoffPoint(CollectionsInternalService.UpdateShardCutoffPointRequest request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsInternalGrpc.getUpdateShardCutoffPointMethod(), responseObserver);
        }
    }

    private static final class CollectionsInternalFileDescriptorSupplier
    extends CollectionsInternalBaseDescriptorSupplier {
        CollectionsInternalFileDescriptorSupplier() {
        }
    }

    private static abstract class CollectionsInternalBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        CollectionsInternalBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return CollectionsInternalService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("CollectionsInternal");
        }
    }

    public static abstract class CollectionsInternalImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return CollectionsInternalGrpc.bindService(this);
        }
    }
}

