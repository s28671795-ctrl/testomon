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
import io.qdrant.client.grpc.ShardSnapshotsService;
import io.qdrant.client.grpc.SnapshotsService;

@GrpcGenerated
public final class ShardSnapshotsGrpc {
    public static final String SERVICE_NAME = "qdrant.ShardSnapshots";
    private static volatile MethodDescriptor<ShardSnapshotsService.CreateShardSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod;
    private static volatile MethodDescriptor<ShardSnapshotsService.ListShardSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod;
    private static volatile MethodDescriptor<ShardSnapshotsService.DeleteShardSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod;
    private static volatile MethodDescriptor<ShardSnapshotsService.RecoverShardSnapshotRequest, ShardSnapshotsService.RecoverSnapshotResponse> getRecoverMethod;
    private static final int METHODID_CREATE = 0;
    private static final int METHODID_LIST = 1;
    private static final int METHODID_DELETE = 2;
    private static final int METHODID_RECOVER = 3;
    private static volatile ServiceDescriptor serviceDescriptor;

    private ShardSnapshotsGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.ShardSnapshots/Create", requestType=ShardSnapshotsService.CreateShardSnapshotRequest.class, responseType=SnapshotsService.CreateSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<ShardSnapshotsService.CreateShardSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod() {
        MethodDescriptor<ShardSnapshotsService.CreateShardSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod = ShardSnapshotsGrpc.getCreateMethod;
        if (getCreateMethod != null) return getCreateMethod;
        Class<ShardSnapshotsGrpc> clazz = ShardSnapshotsGrpc.class;
        synchronized (ShardSnapshotsGrpc.class) {
            getCreateMethod = ShardSnapshotsGrpc.getCreateMethod;
            if (getCreateMethod != null) return getCreateMethod;
            ShardSnapshotsGrpc.getCreateMethod = getCreateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Create")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(ShardSnapshotsService.CreateShardSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.CreateSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new ShardSnapshotsMethodDescriptorSupplier("Create")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.ShardSnapshots/List", requestType=ShardSnapshotsService.ListShardSnapshotsRequest.class, responseType=SnapshotsService.ListSnapshotsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<ShardSnapshotsService.ListShardSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod() {
        MethodDescriptor<ShardSnapshotsService.ListShardSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod = ShardSnapshotsGrpc.getListMethod;
        if (getListMethod != null) return getListMethod;
        Class<ShardSnapshotsGrpc> clazz = ShardSnapshotsGrpc.class;
        synchronized (ShardSnapshotsGrpc.class) {
            getListMethod = ShardSnapshotsGrpc.getListMethod;
            if (getListMethod != null) return getListMethod;
            ShardSnapshotsGrpc.getListMethod = getListMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "List")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(ShardSnapshotsService.ListShardSnapshotsRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.ListSnapshotsResponse.getDefaultInstance())).setSchemaDescriptor(new ShardSnapshotsMethodDescriptorSupplier("List")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.ShardSnapshots/Delete", requestType=ShardSnapshotsService.DeleteShardSnapshotRequest.class, responseType=SnapshotsService.DeleteSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<ShardSnapshotsService.DeleteShardSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod() {
        MethodDescriptor<ShardSnapshotsService.DeleteShardSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod = ShardSnapshotsGrpc.getDeleteMethod;
        if (getDeleteMethod != null) return getDeleteMethod;
        Class<ShardSnapshotsGrpc> clazz = ShardSnapshotsGrpc.class;
        synchronized (ShardSnapshotsGrpc.class) {
            getDeleteMethod = ShardSnapshotsGrpc.getDeleteMethod;
            if (getDeleteMethod != null) return getDeleteMethod;
            ShardSnapshotsGrpc.getDeleteMethod = getDeleteMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(ShardSnapshotsService.DeleteShardSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.DeleteSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new ShardSnapshotsMethodDescriptorSupplier("Delete")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.ShardSnapshots/Recover", requestType=ShardSnapshotsService.RecoverShardSnapshotRequest.class, responseType=ShardSnapshotsService.RecoverSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<ShardSnapshotsService.RecoverShardSnapshotRequest, ShardSnapshotsService.RecoverSnapshotResponse> getRecoverMethod() {
        MethodDescriptor<ShardSnapshotsService.RecoverShardSnapshotRequest, ShardSnapshotsService.RecoverSnapshotResponse> getRecoverMethod = ShardSnapshotsGrpc.getRecoverMethod;
        if (getRecoverMethod != null) return getRecoverMethod;
        Class<ShardSnapshotsGrpc> clazz = ShardSnapshotsGrpc.class;
        synchronized (ShardSnapshotsGrpc.class) {
            getRecoverMethod = ShardSnapshotsGrpc.getRecoverMethod;
            if (getRecoverMethod != null) return getRecoverMethod;
            ShardSnapshotsGrpc.getRecoverMethod = getRecoverMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Recover")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(ShardSnapshotsService.RecoverShardSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(ShardSnapshotsService.RecoverSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new ShardSnapshotsMethodDescriptorSupplier("Recover")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getRecoverMethod;
        }
    }

    public static ShardSnapshotsStub newStub(Channel channel) {
        AbstractStub.StubFactory<ShardSnapshotsStub> factory2 = new AbstractStub.StubFactory<ShardSnapshotsStub>(){

            @Override
            public ShardSnapshotsStub newStub(Channel channel, CallOptions callOptions) {
                return new ShardSnapshotsStub(channel, callOptions);
            }
        };
        return ShardSnapshotsStub.newStub(factory2, channel);
    }

    public static ShardSnapshotsBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<ShardSnapshotsBlockingStub> factory2 = new AbstractStub.StubFactory<ShardSnapshotsBlockingStub>(){

            @Override
            public ShardSnapshotsBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new ShardSnapshotsBlockingStub(channel, callOptions);
            }
        };
        return ShardSnapshotsBlockingStub.newStub(factory2, channel);
    }

    public static ShardSnapshotsFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<ShardSnapshotsFutureStub> factory2 = new AbstractStub.StubFactory<ShardSnapshotsFutureStub>(){

            @Override
            public ShardSnapshotsFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new ShardSnapshotsFutureStub(channel, callOptions);
            }
        };
        return ShardSnapshotsFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(ShardSnapshotsGrpc.getServiceDescriptor()).addMethod(ShardSnapshotsGrpc.getCreateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(ShardSnapshotsGrpc.getListMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(ShardSnapshotsGrpc.getDeleteMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(ShardSnapshotsGrpc.getRecoverMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<ShardSnapshotsGrpc> clazz = ShardSnapshotsGrpc.class;
        synchronized (ShardSnapshotsGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new ShardSnapshotsFileDescriptorSupplier()).addMethod(ShardSnapshotsGrpc.getCreateMethod()).addMethod(ShardSnapshotsGrpc.getListMethod()).addMethod(ShardSnapshotsGrpc.getDeleteMethod()).addMethod(ShardSnapshotsGrpc.getRecoverMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class ShardSnapshotsMethodDescriptorSupplier
    extends ShardSnapshotsBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        ShardSnapshotsMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class ShardSnapshotsStub
    extends AbstractAsyncStub<ShardSnapshotsStub> {
        private ShardSnapshotsStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ShardSnapshotsStub build(Channel channel, CallOptions callOptions) {
            return new ShardSnapshotsStub(channel, callOptions);
        }

        public void create(ShardSnapshotsService.CreateShardSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getCreateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void list(ShardSnapshotsService.ListShardSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getListMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void delete(ShardSnapshotsService.DeleteShardSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getDeleteMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void recover(ShardSnapshotsService.RecoverShardSnapshotRequest request, StreamObserver<ShardSnapshotsService.RecoverSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getRecoverMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class ShardSnapshotsBlockingStub
    extends AbstractBlockingStub<ShardSnapshotsBlockingStub> {
        private ShardSnapshotsBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ShardSnapshotsBlockingStub build(Channel channel, CallOptions callOptions) {
            return new ShardSnapshotsBlockingStub(channel, callOptions);
        }

        public SnapshotsService.CreateSnapshotResponse create(ShardSnapshotsService.CreateShardSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), ShardSnapshotsGrpc.getCreateMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.ListSnapshotsResponse list(ShardSnapshotsService.ListShardSnapshotsRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), ShardSnapshotsGrpc.getListMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.DeleteSnapshotResponse delete(ShardSnapshotsService.DeleteShardSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), ShardSnapshotsGrpc.getDeleteMethod(), this.getCallOptions(), request);
        }

        public ShardSnapshotsService.RecoverSnapshotResponse recover(ShardSnapshotsService.RecoverShardSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), ShardSnapshotsGrpc.getRecoverMethod(), this.getCallOptions(), request);
        }
    }

    public static final class ShardSnapshotsFutureStub
    extends AbstractFutureStub<ShardSnapshotsFutureStub> {
        private ShardSnapshotsFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected ShardSnapshotsFutureStub build(Channel channel, CallOptions callOptions) {
            return new ShardSnapshotsFutureStub(channel, callOptions);
        }

        public ListenableFuture<SnapshotsService.CreateSnapshotResponse> create(ShardSnapshotsService.CreateShardSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getCreateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.ListSnapshotsResponse> list(ShardSnapshotsService.ListShardSnapshotsRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getListMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> delete(ShardSnapshotsService.DeleteShardSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getDeleteMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<ShardSnapshotsService.RecoverSnapshotResponse> recover(ShardSnapshotsService.RecoverShardSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(ShardSnapshotsGrpc.getRecoverMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.create((ShardSnapshotsService.CreateShardSnapshotRequest)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.list((ShardSnapshotsService.ListShardSnapshotsRequest)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.delete((ShardSnapshotsService.DeleteShardSnapshotRequest)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.recover((ShardSnapshotsService.RecoverShardSnapshotRequest)request, responseObserver);
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
        default public void create(ShardSnapshotsService.CreateShardSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(ShardSnapshotsGrpc.getCreateMethod(), responseObserver);
        }

        default public void list(ShardSnapshotsService.ListShardSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(ShardSnapshotsGrpc.getListMethod(), responseObserver);
        }

        default public void delete(ShardSnapshotsService.DeleteShardSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(ShardSnapshotsGrpc.getDeleteMethod(), responseObserver);
        }

        default public void recover(ShardSnapshotsService.RecoverShardSnapshotRequest request, StreamObserver<ShardSnapshotsService.RecoverSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(ShardSnapshotsGrpc.getRecoverMethod(), responseObserver);
        }
    }

    private static final class ShardSnapshotsFileDescriptorSupplier
    extends ShardSnapshotsBaseDescriptorSupplier {
        ShardSnapshotsFileDescriptorSupplier() {
        }
    }

    private static abstract class ShardSnapshotsBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        ShardSnapshotsBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return ShardSnapshotsService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("ShardSnapshots");
        }
    }

    public static abstract class ShardSnapshotsImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return ShardSnapshotsGrpc.bindService(this);
        }
    }
}

