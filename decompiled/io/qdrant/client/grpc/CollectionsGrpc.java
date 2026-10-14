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
import io.qdrant.client.grpc.CollectionsService;

@GrpcGenerated
public final class CollectionsGrpc {
    public static final String SERVICE_NAME = "qdrant.Collections";
    private static volatile MethodDescriptor<Collections.GetCollectionInfoRequest, Collections.GetCollectionInfoResponse> getGetMethod;
    private static volatile MethodDescriptor<Collections.ListCollectionsRequest, Collections.ListCollectionsResponse> getListMethod;
    private static volatile MethodDescriptor<Collections.CreateCollection, Collections.CollectionOperationResponse> getCreateMethod;
    private static volatile MethodDescriptor<Collections.UpdateCollection, Collections.CollectionOperationResponse> getUpdateMethod;
    private static volatile MethodDescriptor<Collections.DeleteCollection, Collections.CollectionOperationResponse> getDeleteMethod;
    private static volatile MethodDescriptor<Collections.ChangeAliases, Collections.CollectionOperationResponse> getUpdateAliasesMethod;
    private static volatile MethodDescriptor<Collections.ListCollectionAliasesRequest, Collections.ListAliasesResponse> getListCollectionAliasesMethod;
    private static volatile MethodDescriptor<Collections.ListAliasesRequest, Collections.ListAliasesResponse> getListAliasesMethod;
    private static volatile MethodDescriptor<Collections.CollectionClusterInfoRequest, Collections.CollectionClusterInfoResponse> getCollectionClusterInfoMethod;
    private static volatile MethodDescriptor<Collections.CollectionExistsRequest, Collections.CollectionExistsResponse> getCollectionExistsMethod;
    private static volatile MethodDescriptor<Collections.UpdateCollectionClusterSetupRequest, Collections.UpdateCollectionClusterSetupResponse> getUpdateCollectionClusterSetupMethod;
    private static volatile MethodDescriptor<Collections.CreateShardKeyRequest, Collections.CreateShardKeyResponse> getCreateShardKeyMethod;
    private static volatile MethodDescriptor<Collections.DeleteShardKeyRequest, Collections.DeleteShardKeyResponse> getDeleteShardKeyMethod;
    private static final int METHODID_GET = 0;
    private static final int METHODID_LIST = 1;
    private static final int METHODID_CREATE = 2;
    private static final int METHODID_UPDATE = 3;
    private static final int METHODID_DELETE = 4;
    private static final int METHODID_UPDATE_ALIASES = 5;
    private static final int METHODID_LIST_COLLECTION_ALIASES = 6;
    private static final int METHODID_LIST_ALIASES = 7;
    private static final int METHODID_COLLECTION_CLUSTER_INFO = 8;
    private static final int METHODID_COLLECTION_EXISTS = 9;
    private static final int METHODID_UPDATE_COLLECTION_CLUSTER_SETUP = 10;
    private static final int METHODID_CREATE_SHARD_KEY = 11;
    private static final int METHODID_DELETE_SHARD_KEY = 12;
    private static volatile ServiceDescriptor serviceDescriptor;

    private CollectionsGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/Get", requestType=Collections.GetCollectionInfoRequest.class, responseType=Collections.GetCollectionInfoResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.GetCollectionInfoRequest, Collections.GetCollectionInfoResponse> getGetMethod() {
        MethodDescriptor<Collections.GetCollectionInfoRequest, Collections.GetCollectionInfoResponse> getGetMethod = CollectionsGrpc.getGetMethod;
        if (getGetMethod != null) return getGetMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getGetMethod = CollectionsGrpc.getGetMethod;
            if (getGetMethod != null) return getGetMethod;
            CollectionsGrpc.getGetMethod = getGetMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Get")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.GetCollectionInfoRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.GetCollectionInfoResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("Get")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/List", requestType=Collections.ListCollectionsRequest.class, responseType=Collections.ListCollectionsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.ListCollectionsRequest, Collections.ListCollectionsResponse> getListMethod() {
        MethodDescriptor<Collections.ListCollectionsRequest, Collections.ListCollectionsResponse> getListMethod = CollectionsGrpc.getListMethod;
        if (getListMethod != null) return getListMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getListMethod = CollectionsGrpc.getListMethod;
            if (getListMethod != null) return getListMethod;
            CollectionsGrpc.getListMethod = getListMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "List")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.ListCollectionsRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.ListCollectionsResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("List")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/Create", requestType=Collections.CreateCollection.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.CreateCollection, Collections.CollectionOperationResponse> getCreateMethod() {
        MethodDescriptor<Collections.CreateCollection, Collections.CollectionOperationResponse> getCreateMethod = CollectionsGrpc.getCreateMethod;
        if (getCreateMethod != null) return getCreateMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getCreateMethod = CollectionsGrpc.getCreateMethod;
            if (getCreateMethod != null) return getCreateMethod;
            CollectionsGrpc.getCreateMethod = getCreateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Create")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.CreateCollection.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("Create")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/Update", requestType=Collections.UpdateCollection.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.UpdateCollection, Collections.CollectionOperationResponse> getUpdateMethod() {
        MethodDescriptor<Collections.UpdateCollection, Collections.CollectionOperationResponse> getUpdateMethod = CollectionsGrpc.getUpdateMethod;
        if (getUpdateMethod != null) return getUpdateMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getUpdateMethod = CollectionsGrpc.getUpdateMethod;
            if (getUpdateMethod != null) return getUpdateMethod;
            CollectionsGrpc.getUpdateMethod = getUpdateMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Update")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.UpdateCollection.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("Update")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/Delete", requestType=Collections.DeleteCollection.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.DeleteCollection, Collections.CollectionOperationResponse> getDeleteMethod() {
        MethodDescriptor<Collections.DeleteCollection, Collections.CollectionOperationResponse> getDeleteMethod = CollectionsGrpc.getDeleteMethod;
        if (getDeleteMethod != null) return getDeleteMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getDeleteMethod = CollectionsGrpc.getDeleteMethod;
            if (getDeleteMethod != null) return getDeleteMethod;
            CollectionsGrpc.getDeleteMethod = getDeleteMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.DeleteCollection.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("Delete")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/UpdateAliases", requestType=Collections.ChangeAliases.class, responseType=Collections.CollectionOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.ChangeAliases, Collections.CollectionOperationResponse> getUpdateAliasesMethod() {
        MethodDescriptor<Collections.ChangeAliases, Collections.CollectionOperationResponse> getUpdateAliasesMethod = CollectionsGrpc.getUpdateAliasesMethod;
        if (getUpdateAliasesMethod != null) return getUpdateAliasesMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getUpdateAliasesMethod = CollectionsGrpc.getUpdateAliasesMethod;
            if (getUpdateAliasesMethod != null) return getUpdateAliasesMethod;
            CollectionsGrpc.getUpdateAliasesMethod = getUpdateAliasesMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateAliases")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.ChangeAliases.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionOperationResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("UpdateAliases")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateAliasesMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/ListCollectionAliases", requestType=Collections.ListCollectionAliasesRequest.class, responseType=Collections.ListAliasesResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.ListCollectionAliasesRequest, Collections.ListAliasesResponse> getListCollectionAliasesMethod() {
        MethodDescriptor<Collections.ListCollectionAliasesRequest, Collections.ListAliasesResponse> getListCollectionAliasesMethod = CollectionsGrpc.getListCollectionAliasesMethod;
        if (getListCollectionAliasesMethod != null) return getListCollectionAliasesMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getListCollectionAliasesMethod = CollectionsGrpc.getListCollectionAliasesMethod;
            if (getListCollectionAliasesMethod != null) return getListCollectionAliasesMethod;
            CollectionsGrpc.getListCollectionAliasesMethod = getListCollectionAliasesMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ListCollectionAliases")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.ListCollectionAliasesRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.ListAliasesResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("ListCollectionAliases")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListCollectionAliasesMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/ListAliases", requestType=Collections.ListAliasesRequest.class, responseType=Collections.ListAliasesResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.ListAliasesRequest, Collections.ListAliasesResponse> getListAliasesMethod() {
        MethodDescriptor<Collections.ListAliasesRequest, Collections.ListAliasesResponse> getListAliasesMethod = CollectionsGrpc.getListAliasesMethod;
        if (getListAliasesMethod != null) return getListAliasesMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getListAliasesMethod = CollectionsGrpc.getListAliasesMethod;
            if (getListAliasesMethod != null) return getListAliasesMethod;
            CollectionsGrpc.getListAliasesMethod = getListAliasesMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ListAliases")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.ListAliasesRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.ListAliasesResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("ListAliases")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getListAliasesMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/CollectionClusterInfo", requestType=Collections.CollectionClusterInfoRequest.class, responseType=Collections.CollectionClusterInfoResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.CollectionClusterInfoRequest, Collections.CollectionClusterInfoResponse> getCollectionClusterInfoMethod() {
        MethodDescriptor<Collections.CollectionClusterInfoRequest, Collections.CollectionClusterInfoResponse> getCollectionClusterInfoMethod = CollectionsGrpc.getCollectionClusterInfoMethod;
        if (getCollectionClusterInfoMethod != null) return getCollectionClusterInfoMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getCollectionClusterInfoMethod = CollectionsGrpc.getCollectionClusterInfoMethod;
            if (getCollectionClusterInfoMethod != null) return getCollectionClusterInfoMethod;
            CollectionsGrpc.getCollectionClusterInfoMethod = getCollectionClusterInfoMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CollectionClusterInfo")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.CollectionClusterInfoRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionClusterInfoResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("CollectionClusterInfo")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCollectionClusterInfoMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/CollectionExists", requestType=Collections.CollectionExistsRequest.class, responseType=Collections.CollectionExistsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.CollectionExistsRequest, Collections.CollectionExistsResponse> getCollectionExistsMethod() {
        MethodDescriptor<Collections.CollectionExistsRequest, Collections.CollectionExistsResponse> getCollectionExistsMethod = CollectionsGrpc.getCollectionExistsMethod;
        if (getCollectionExistsMethod != null) return getCollectionExistsMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getCollectionExistsMethod = CollectionsGrpc.getCollectionExistsMethod;
            if (getCollectionExistsMethod != null) return getCollectionExistsMethod;
            CollectionsGrpc.getCollectionExistsMethod = getCollectionExistsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CollectionExists")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.CollectionExistsRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CollectionExistsResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("CollectionExists")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCollectionExistsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/UpdateCollectionClusterSetup", requestType=Collections.UpdateCollectionClusterSetupRequest.class, responseType=Collections.UpdateCollectionClusterSetupResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.UpdateCollectionClusterSetupRequest, Collections.UpdateCollectionClusterSetupResponse> getUpdateCollectionClusterSetupMethod() {
        MethodDescriptor<Collections.UpdateCollectionClusterSetupRequest, Collections.UpdateCollectionClusterSetupResponse> getUpdateCollectionClusterSetupMethod = CollectionsGrpc.getUpdateCollectionClusterSetupMethod;
        if (getUpdateCollectionClusterSetupMethod != null) return getUpdateCollectionClusterSetupMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getUpdateCollectionClusterSetupMethod = CollectionsGrpc.getUpdateCollectionClusterSetupMethod;
            if (getUpdateCollectionClusterSetupMethod != null) return getUpdateCollectionClusterSetupMethod;
            CollectionsGrpc.getUpdateCollectionClusterSetupMethod = getUpdateCollectionClusterSetupMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateCollectionClusterSetup")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.UpdateCollectionClusterSetupRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.UpdateCollectionClusterSetupResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("UpdateCollectionClusterSetup")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateCollectionClusterSetupMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/CreateShardKey", requestType=Collections.CreateShardKeyRequest.class, responseType=Collections.CreateShardKeyResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.CreateShardKeyRequest, Collections.CreateShardKeyResponse> getCreateShardKeyMethod() {
        MethodDescriptor<Collections.CreateShardKeyRequest, Collections.CreateShardKeyResponse> getCreateShardKeyMethod = CollectionsGrpc.getCreateShardKeyMethod;
        if (getCreateShardKeyMethod != null) return getCreateShardKeyMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getCreateShardKeyMethod = CollectionsGrpc.getCreateShardKeyMethod;
            if (getCreateShardKeyMethod != null) return getCreateShardKeyMethod;
            CollectionsGrpc.getCreateShardKeyMethod = getCreateShardKeyMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CreateShardKey")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.CreateShardKeyRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.CreateShardKeyResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("CreateShardKey")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateShardKeyMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Collections/DeleteShardKey", requestType=Collections.DeleteShardKeyRequest.class, responseType=Collections.DeleteShardKeyResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Collections.DeleteShardKeyRequest, Collections.DeleteShardKeyResponse> getDeleteShardKeyMethod() {
        MethodDescriptor<Collections.DeleteShardKeyRequest, Collections.DeleteShardKeyResponse> getDeleteShardKeyMethod = CollectionsGrpc.getDeleteShardKeyMethod;
        if (getDeleteShardKeyMethod != null) return getDeleteShardKeyMethod;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            getDeleteShardKeyMethod = CollectionsGrpc.getDeleteShardKeyMethod;
            if (getDeleteShardKeyMethod != null) return getDeleteShardKeyMethod;
            CollectionsGrpc.getDeleteShardKeyMethod = getDeleteShardKeyMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteShardKey")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Collections.DeleteShardKeyRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Collections.DeleteShardKeyResponse.getDefaultInstance())).setSchemaDescriptor(new CollectionsMethodDescriptorSupplier("DeleteShardKey")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteShardKeyMethod;
        }
    }

    public static CollectionsStub newStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsStub> factory2 = new AbstractStub.StubFactory<CollectionsStub>(){

            @Override
            public CollectionsStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsStub(channel, callOptions);
            }
        };
        return CollectionsStub.newStub(factory2, channel);
    }

    public static CollectionsBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsBlockingStub> factory2 = new AbstractStub.StubFactory<CollectionsBlockingStub>(){

            @Override
            public CollectionsBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsBlockingStub(channel, callOptions);
            }
        };
        return CollectionsBlockingStub.newStub(factory2, channel);
    }

    public static CollectionsFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<CollectionsFutureStub> factory2 = new AbstractStub.StubFactory<CollectionsFutureStub>(){

            @Override
            public CollectionsFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new CollectionsFutureStub(channel, callOptions);
            }
        };
        return CollectionsFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(CollectionsGrpc.getServiceDescriptor()).addMethod(CollectionsGrpc.getGetMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(CollectionsGrpc.getListMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(CollectionsGrpc.getCreateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(CollectionsGrpc.getUpdateMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).addMethod(CollectionsGrpc.getDeleteMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 4))).addMethod(CollectionsGrpc.getUpdateAliasesMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 5))).addMethod(CollectionsGrpc.getListCollectionAliasesMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 6))).addMethod(CollectionsGrpc.getListAliasesMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 7))).addMethod(CollectionsGrpc.getCollectionClusterInfoMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 8))).addMethod(CollectionsGrpc.getCollectionExistsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 9))).addMethod(CollectionsGrpc.getUpdateCollectionClusterSetupMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 10))).addMethod(CollectionsGrpc.getCreateShardKeyMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 11))).addMethod(CollectionsGrpc.getDeleteShardKeyMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 12))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<CollectionsGrpc> clazz = CollectionsGrpc.class;
        synchronized (CollectionsGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new CollectionsFileDescriptorSupplier()).addMethod(CollectionsGrpc.getGetMethod()).addMethod(CollectionsGrpc.getListMethod()).addMethod(CollectionsGrpc.getCreateMethod()).addMethod(CollectionsGrpc.getUpdateMethod()).addMethod(CollectionsGrpc.getDeleteMethod()).addMethod(CollectionsGrpc.getUpdateAliasesMethod()).addMethod(CollectionsGrpc.getListCollectionAliasesMethod()).addMethod(CollectionsGrpc.getListAliasesMethod()).addMethod(CollectionsGrpc.getCollectionClusterInfoMethod()).addMethod(CollectionsGrpc.getCollectionExistsMethod()).addMethod(CollectionsGrpc.getUpdateCollectionClusterSetupMethod()).addMethod(CollectionsGrpc.getCreateShardKeyMethod()).addMethod(CollectionsGrpc.getDeleteShardKeyMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class CollectionsMethodDescriptorSupplier
    extends CollectionsBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        CollectionsMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class CollectionsStub
    extends AbstractAsyncStub<CollectionsStub> {
        private CollectionsStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsStub(channel, callOptions);
        }

        public void get(Collections.GetCollectionInfoRequest request, StreamObserver<Collections.GetCollectionInfoResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getGetMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void list(Collections.ListCollectionsRequest request, StreamObserver<Collections.ListCollectionsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void create(Collections.CreateCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCreateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void update(Collections.UpdateCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void delete(Collections.DeleteCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getDeleteMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateAliases(Collections.ChangeAliases request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateAliasesMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void listCollectionAliases(Collections.ListCollectionAliasesRequest request, StreamObserver<Collections.ListAliasesResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListCollectionAliasesMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void listAliases(Collections.ListAliasesRequest request, StreamObserver<Collections.ListAliasesResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListAliasesMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void collectionClusterInfo(Collections.CollectionClusterInfoRequest request, StreamObserver<Collections.CollectionClusterInfoResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCollectionClusterInfoMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void collectionExists(Collections.CollectionExistsRequest request, StreamObserver<Collections.CollectionExistsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCollectionExistsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateCollectionClusterSetup(Collections.UpdateCollectionClusterSetupRequest request, StreamObserver<Collections.UpdateCollectionClusterSetupResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateCollectionClusterSetupMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void createShardKey(Collections.CreateShardKeyRequest request, StreamObserver<Collections.CreateShardKeyResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCreateShardKeyMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteShardKey(Collections.DeleteShardKeyRequest request, StreamObserver<Collections.DeleteShardKeyResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(CollectionsGrpc.getDeleteShardKeyMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class CollectionsBlockingStub
    extends AbstractBlockingStub<CollectionsBlockingStub> {
        private CollectionsBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsBlockingStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsBlockingStub(channel, callOptions);
        }

        public Collections.GetCollectionInfoResponse get(Collections.GetCollectionInfoRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getGetMethod(), this.getCallOptions(), request);
        }

        public Collections.ListCollectionsResponse list(Collections.ListCollectionsRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getListMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse create(Collections.CreateCollection request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getCreateMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse update(Collections.UpdateCollection request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getUpdateMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse delete(Collections.DeleteCollection request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getDeleteMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionOperationResponse updateAliases(Collections.ChangeAliases request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getUpdateAliasesMethod(), this.getCallOptions(), request);
        }

        public Collections.ListAliasesResponse listCollectionAliases(Collections.ListCollectionAliasesRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getListCollectionAliasesMethod(), this.getCallOptions(), request);
        }

        public Collections.ListAliasesResponse listAliases(Collections.ListAliasesRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getListAliasesMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionClusterInfoResponse collectionClusterInfo(Collections.CollectionClusterInfoRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getCollectionClusterInfoMethod(), this.getCallOptions(), request);
        }

        public Collections.CollectionExistsResponse collectionExists(Collections.CollectionExistsRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getCollectionExistsMethod(), this.getCallOptions(), request);
        }

        public Collections.UpdateCollectionClusterSetupResponse updateCollectionClusterSetup(Collections.UpdateCollectionClusterSetupRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getUpdateCollectionClusterSetupMethod(), this.getCallOptions(), request);
        }

        public Collections.CreateShardKeyResponse createShardKey(Collections.CreateShardKeyRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getCreateShardKeyMethod(), this.getCallOptions(), request);
        }

        public Collections.DeleteShardKeyResponse deleteShardKey(Collections.DeleteShardKeyRequest request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), CollectionsGrpc.getDeleteShardKeyMethod(), this.getCallOptions(), request);
        }
    }

    public static final class CollectionsFutureStub
    extends AbstractFutureStub<CollectionsFutureStub> {
        private CollectionsFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected CollectionsFutureStub build(Channel channel, CallOptions callOptions) {
            return new CollectionsFutureStub(channel, callOptions);
        }

        public ListenableFuture<Collections.GetCollectionInfoResponse> get(Collections.GetCollectionInfoRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getGetMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.ListCollectionsResponse> list(Collections.ListCollectionsRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> create(Collections.CreateCollection request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCreateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> update(Collections.UpdateCollection request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> delete(Collections.DeleteCollection request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getDeleteMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionOperationResponse> updateAliases(Collections.ChangeAliases request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateAliasesMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.ListAliasesResponse> listCollectionAliases(Collections.ListCollectionAliasesRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListCollectionAliasesMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.ListAliasesResponse> listAliases(Collections.ListAliasesRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getListAliasesMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionClusterInfoResponse> collectionClusterInfo(Collections.CollectionClusterInfoRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCollectionClusterInfoMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CollectionExistsResponse> collectionExists(Collections.CollectionExistsRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCollectionExistsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.UpdateCollectionClusterSetupResponse> updateCollectionClusterSetup(Collections.UpdateCollectionClusterSetupRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getUpdateCollectionClusterSetupMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.CreateShardKeyResponse> createShardKey(Collections.CreateShardKeyRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getCreateShardKeyMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Collections.DeleteShardKeyResponse> deleteShardKey(Collections.DeleteShardKeyRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(CollectionsGrpc.getDeleteShardKeyMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.get((Collections.GetCollectionInfoRequest)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.list((Collections.ListCollectionsRequest)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.create((Collections.CreateCollection)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.update((Collections.UpdateCollection)request, responseObserver);
                    break;
                }
                case 4: {
                    this.serviceImpl.delete((Collections.DeleteCollection)request, responseObserver);
                    break;
                }
                case 5: {
                    this.serviceImpl.updateAliases((Collections.ChangeAliases)request, responseObserver);
                    break;
                }
                case 6: {
                    this.serviceImpl.listCollectionAliases((Collections.ListCollectionAliasesRequest)request, responseObserver);
                    break;
                }
                case 7: {
                    this.serviceImpl.listAliases((Collections.ListAliasesRequest)request, responseObserver);
                    break;
                }
                case 8: {
                    this.serviceImpl.collectionClusterInfo((Collections.CollectionClusterInfoRequest)request, responseObserver);
                    break;
                }
                case 9: {
                    this.serviceImpl.collectionExists((Collections.CollectionExistsRequest)request, responseObserver);
                    break;
                }
                case 10: {
                    this.serviceImpl.updateCollectionClusterSetup((Collections.UpdateCollectionClusterSetupRequest)request, responseObserver);
                    break;
                }
                case 11: {
                    this.serviceImpl.createShardKey((Collections.CreateShardKeyRequest)request, responseObserver);
                    break;
                }
                case 12: {
                    this.serviceImpl.deleteShardKey((Collections.DeleteShardKeyRequest)request, responseObserver);
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
        default public void get(Collections.GetCollectionInfoRequest request, StreamObserver<Collections.GetCollectionInfoResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getGetMethod(), responseObserver);
        }

        default public void list(Collections.ListCollectionsRequest request, StreamObserver<Collections.ListCollectionsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getListMethod(), responseObserver);
        }

        default public void create(Collections.CreateCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getCreateMethod(), responseObserver);
        }

        default public void update(Collections.UpdateCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getUpdateMethod(), responseObserver);
        }

        default public void delete(Collections.DeleteCollection request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getDeleteMethod(), responseObserver);
        }

        default public void updateAliases(Collections.ChangeAliases request, StreamObserver<Collections.CollectionOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getUpdateAliasesMethod(), responseObserver);
        }

        default public void listCollectionAliases(Collections.ListCollectionAliasesRequest request, StreamObserver<Collections.ListAliasesResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getListCollectionAliasesMethod(), responseObserver);
        }

        default public void listAliases(Collections.ListAliasesRequest request, StreamObserver<Collections.ListAliasesResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getListAliasesMethod(), responseObserver);
        }

        default public void collectionClusterInfo(Collections.CollectionClusterInfoRequest request, StreamObserver<Collections.CollectionClusterInfoResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getCollectionClusterInfoMethod(), responseObserver);
        }

        default public void collectionExists(Collections.CollectionExistsRequest request, StreamObserver<Collections.CollectionExistsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getCollectionExistsMethod(), responseObserver);
        }

        default public void updateCollectionClusterSetup(Collections.UpdateCollectionClusterSetupRequest request, StreamObserver<Collections.UpdateCollectionClusterSetupResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getUpdateCollectionClusterSetupMethod(), responseObserver);
        }

        default public void createShardKey(Collections.CreateShardKeyRequest request, StreamObserver<Collections.CreateShardKeyResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getCreateShardKeyMethod(), responseObserver);
        }

        default public void deleteShardKey(Collections.DeleteShardKeyRequest request, StreamObserver<Collections.DeleteShardKeyResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(CollectionsGrpc.getDeleteShardKeyMethod(), responseObserver);
        }
    }

    private static final class CollectionsFileDescriptorSupplier
    extends CollectionsBaseDescriptorSupplier {
        CollectionsFileDescriptorSupplier() {
        }
    }

    private static abstract class CollectionsBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        CollectionsBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return CollectionsService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Collections");
        }
    }

    public static abstract class CollectionsImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return CollectionsGrpc.bindService(this);
        }
    }
}

