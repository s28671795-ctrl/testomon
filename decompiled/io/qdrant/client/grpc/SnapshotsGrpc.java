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
import io.qdrant.client.grpc.SnapshotsService;

@GrpcGenerated
public final class SnapshotsGrpc {
    public static final String SERVICE_NAME = "qdrant.Snapshots";
    private static volatile MethodDescriptor<SnapshotsService.CreateSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod;
    private static volatile MethodDescriptor<SnapshotsService.ListSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod;
    private static volatile MethodDescriptor<SnapshotsService.DeleteSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod;
    private static volatile MethodDescriptor<SnapshotsService.CreateFullSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateFullMethod;
    private static volatile MethodDescriptor<SnapshotsService.ListFullSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListFullMethod;
    private static volatile MethodDescriptor<SnapshotsService.DeleteFullSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteFullMethod;
    private static final int METHODID_CREATE = 0;
    private static final int METHODID_LIST = 1;
    private static final int METHODID_DELETE = 2;
    private static final int METHODID_CREATE_FULL = 3;
    private static final int METHODID_LIST_FULL = 4;
    private static final int METHODID_DELETE_FULL = 5;
    private static volatile ServiceDescriptor serviceDescriptor;

    private SnapshotsGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/Create", requestType=SnapshotsService.CreateSnapshotRequest.class, responseType=SnapshotsService.CreateSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.CreateSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod() {
        MethodDescriptor<SnapshotsService.CreateSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateMethod = SnapshotsGrpc.getCreateMethod;
        if (getCreateMethod != null) return getCreateMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getCreateMethod = SnapshotsGrpc.getCreateMethod;
            if (getCreateMethod != null) return getCreateMethod;
            SnapshotsGrpc.getCreateMethod = getCreateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Create")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.CreateSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.CreateSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("Create")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/List", requestType=SnapshotsService.ListSnapshotsRequest.class, responseType=SnapshotsService.ListSnapshotsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.ListSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod() {
        MethodDescriptor<SnapshotsService.ListSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListMethod = SnapshotsGrpc.getListMethod;
        if (getListMethod != null) return getListMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getListMethod = SnapshotsGrpc.getListMethod;
            if (getListMethod != null) return getListMethod;
            SnapshotsGrpc.getListMethod = getListMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "List")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.ListSnapshotsRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.ListSnapshotsResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("List")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/Delete", requestType=SnapshotsService.DeleteSnapshotRequest.class, responseType=SnapshotsService.DeleteSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.DeleteSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod() {
        MethodDescriptor<SnapshotsService.DeleteSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteMethod = SnapshotsGrpc.getDeleteMethod;
        if (getDeleteMethod != null) return getDeleteMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getDeleteMethod = SnapshotsGrpc.getDeleteMethod;
            if (getDeleteMethod != null) return getDeleteMethod;
            SnapshotsGrpc.getDeleteMethod = getDeleteMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.DeleteSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.DeleteSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("Delete")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/CreateFull", requestType=SnapshotsService.CreateFullSnapshotRequest.class, responseType=SnapshotsService.CreateSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.CreateFullSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateFullMethod() {
        MethodDescriptor<SnapshotsService.CreateFullSnapshotRequest, SnapshotsService.CreateSnapshotResponse> getCreateFullMethod = SnapshotsGrpc.getCreateFullMethod;
        if (getCreateFullMethod != null) return getCreateFullMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getCreateFullMethod = SnapshotsGrpc.getCreateFullMethod;
            if (getCreateFullMethod != null) return getCreateFullMethod;
            SnapshotsGrpc.getCreateFullMethod = getCreateFullMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CreateFull")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.CreateFullSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.CreateSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("CreateFull")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateFullMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/ListFull", requestType=SnapshotsService.ListFullSnapshotsRequest.class, responseType=SnapshotsService.ListSnapshotsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.ListFullSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListFullMethod() {
        MethodDescriptor<SnapshotsService.ListFullSnapshotsRequest, SnapshotsService.ListSnapshotsResponse> getListFullMethod = SnapshotsGrpc.getListFullMethod;
        if (getListFullMethod != null) return getListFullMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getListFullMethod = SnapshotsGrpc.getListFullMethod;
            if (getListFullMethod != null) return getListFullMethod;
            SnapshotsGrpc.getListFullMethod = getListFullMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ListFull")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.ListFullSnapshotsRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.ListSnapshotsResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("ListFull")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListFullMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Snapshots/DeleteFull", requestType=SnapshotsService.DeleteFullSnapshotRequest.class, responseType=SnapshotsService.DeleteSnapshotResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<SnapshotsService.DeleteFullSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteFullMethod() {
        MethodDescriptor<SnapshotsService.DeleteFullSnapshotRequest, SnapshotsService.DeleteSnapshotResponse> getDeleteFullMethod = SnapshotsGrpc.getDeleteFullMethod;
        if (getDeleteFullMethod != null) return getDeleteFullMethod;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            getDeleteFullMethod = SnapshotsGrpc.getDeleteFullMethod;
            if (getDeleteFullMethod != null) return getDeleteFullMethod;
            SnapshotsGrpc.getDeleteFullMethod = getDeleteFullMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteFull")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(SnapshotsService.DeleteFullSnapshotRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(SnapshotsService.DeleteSnapshotResponse.getDefaultInstance())).setSchemaDescriptor(new SnapshotsMethodDescriptorSupplier("DeleteFull")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteFullMethod;
        }
    }

    public static SnapshotsStub newStub(Channel channel) {
        AbstractStub.StubFactory<SnapshotsStub> factory2 = new AbstractStub.StubFactory<SnapshotsStub>(){

            @Override
            public SnapshotsStub newStub(Channel channel, CallOptions callOptions) {
                return new SnapshotsStub(channel, callOptions);
            }
        };
        return SnapshotsStub.newStub(factory2, channel);
    }

    public static SnapshotsBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<SnapshotsBlockingStub> factory2 = new AbstractStub.StubFactory<SnapshotsBlockingStub>(){

            @Override
            public SnapshotsBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new SnapshotsBlockingStub(channel, callOptions);
            }
        };
        return SnapshotsBlockingStub.newStub(factory2, channel);
    }

    public static SnapshotsFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<SnapshotsFutureStub> factory2 = new AbstractStub.StubFactory<SnapshotsFutureStub>(){

            @Override
            public SnapshotsFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new SnapshotsFutureStub(channel, callOptions);
            }
        };
        return SnapshotsFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(SnapshotsGrpc.getServiceDescriptor()).addMethod(SnapshotsGrpc.getCreateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(SnapshotsGrpc.getListMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(SnapshotsGrpc.getDeleteMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(SnapshotsGrpc.getCreateFullMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).addMethod(SnapshotsGrpc.getListFullMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 4))).addMethod(SnapshotsGrpc.getDeleteFullMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 5))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<SnapshotsGrpc> clazz = SnapshotsGrpc.class;
        synchronized (SnapshotsGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new SnapshotsFileDescriptorSupplier()).addMethod(SnapshotsGrpc.getCreateMethod()).addMethod(SnapshotsGrpc.getListMethod()).addMethod(SnapshotsGrpc.getDeleteMethod()).addMethod(SnapshotsGrpc.getCreateFullMethod()).addMethod(SnapshotsGrpc.getListFullMethod()).addMethod(SnapshotsGrpc.getDeleteFullMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class SnapshotsMethodDescriptorSupplier
    extends SnapshotsBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        SnapshotsMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class SnapshotsStub
    extends AbstractAsyncStub<SnapshotsStub> {
        private SnapshotsStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected SnapshotsStub build(Channel channel, CallOptions callOptions) {
            return new SnapshotsStub(channel, callOptions);
        }

        public void create(SnapshotsService.CreateSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getCreateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void list(SnapshotsService.ListSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getListMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void delete(SnapshotsService.DeleteSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getDeleteMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void createFull(SnapshotsService.CreateFullSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getCreateFullMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void listFull(SnapshotsService.ListFullSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getListFullMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteFull(SnapshotsService.DeleteFullSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getDeleteFullMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class SnapshotsBlockingStub
    extends AbstractBlockingStub<SnapshotsBlockingStub> {
        private SnapshotsBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected SnapshotsBlockingStub build(Channel channel, CallOptions callOptions) {
            return new SnapshotsBlockingStub(channel, callOptions);
        }

        public SnapshotsService.CreateSnapshotResponse create(SnapshotsService.CreateSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getCreateMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.ListSnapshotsResponse list(SnapshotsService.ListSnapshotsRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getListMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.DeleteSnapshotResponse delete(SnapshotsService.DeleteSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getDeleteMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.CreateSnapshotResponse createFull(SnapshotsService.CreateFullSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getCreateFullMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.ListSnapshotsResponse listFull(SnapshotsService.ListFullSnapshotsRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getListFullMethod(), this.getCallOptions(), request);
        }

        public SnapshotsService.DeleteSnapshotResponse deleteFull(SnapshotsService.DeleteFullSnapshotRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), SnapshotsGrpc.getDeleteFullMethod(), this.getCallOptions(), request);
        }
    }

    public static final class SnapshotsFutureStub
    extends AbstractFutureStub<SnapshotsFutureStub> {
        private SnapshotsFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected SnapshotsFutureStub build(Channel channel, CallOptions callOptions) {
            return new SnapshotsFutureStub(channel, callOptions);
        }

        public ListenableFuture<SnapshotsService.CreateSnapshotResponse> create(SnapshotsService.CreateSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getCreateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.ListSnapshotsResponse> list(SnapshotsService.ListSnapshotsRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getListMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> delete(SnapshotsService.DeleteSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getDeleteMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.CreateSnapshotResponse> createFull(SnapshotsService.CreateFullSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getCreateFullMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.ListSnapshotsResponse> listFull(SnapshotsService.ListFullSnapshotsRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getListFullMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> deleteFull(SnapshotsService.DeleteFullSnapshotRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(SnapshotsGrpc.getDeleteFullMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.create((SnapshotsService.CreateSnapshotRequest)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.list((SnapshotsService.ListSnapshotsRequest)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.delete((SnapshotsService.DeleteSnapshotRequest)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.createFull((SnapshotsService.CreateFullSnapshotRequest)request, responseObserver);
                    break;
                }
                case 4: {
                    this.serviceImpl.listFull((SnapshotsService.ListFullSnapshotsRequest)request, responseObserver);
                    break;
                }
                case 5: {
                    this.serviceImpl.deleteFull((SnapshotsService.DeleteFullSnapshotRequest)request, responseObserver);
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
        default public void create(SnapshotsService.CreateSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getCreateMethod(), responseObserver);
        }

        default public void list(SnapshotsService.ListSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getListMethod(), responseObserver);
        }

        default public void delete(SnapshotsService.DeleteSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getDeleteMethod(), responseObserver);
        }

        default public void createFull(SnapshotsService.CreateFullSnapshotRequest request, StreamObserver<SnapshotsService.CreateSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getCreateFullMethod(), responseObserver);
        }

        default public void listFull(SnapshotsService.ListFullSnapshotsRequest request, StreamObserver<SnapshotsService.ListSnapshotsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getListFullMethod(), responseObserver);
        }

        default public void deleteFull(SnapshotsService.DeleteFullSnapshotRequest request, StreamObserver<SnapshotsService.DeleteSnapshotResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(SnapshotsGrpc.getDeleteFullMethod(), responseObserver);
        }
    }

    private static final class SnapshotsFileDescriptorSupplier
    extends SnapshotsBaseDescriptorSupplier {
        SnapshotsFileDescriptorSupplier() {
        }
    }

    private static abstract class SnapshotsBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        SnapshotsBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return SnapshotsService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Snapshots");
        }
    }

    public static abstract class SnapshotsImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return SnapshotsGrpc.bindService(this);
        }
    }
}

