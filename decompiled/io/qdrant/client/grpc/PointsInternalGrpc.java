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
import io.qdrant.client.grpc.Points;
import io.qdrant.client.grpc.PointsInternalService;

@GrpcGenerated
public final class PointsInternalGrpc {
    public static final String SERVICE_NAME = "qdrant.PointsInternal";
    private static volatile MethodDescriptor<PointsInternalService.UpsertPointsInternal, PointsInternalService.PointsOperationResponseInternal> getUpsertMethod;
    private static volatile MethodDescriptor<PointsInternalService.SyncPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSyncMethod;
    private static volatile MethodDescriptor<PointsInternalService.DeletePointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteMethod;
    private static volatile MethodDescriptor<PointsInternalService.UpdateVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getUpdateVectorsMethod;
    private static volatile MethodDescriptor<PointsInternalService.DeleteVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteVectorsMethod;
    private static volatile MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSetPayloadMethod;
    private static volatile MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getOverwritePayloadMethod;
    private static volatile MethodDescriptor<PointsInternalService.DeletePayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeletePayloadMethod;
    private static volatile MethodDescriptor<PointsInternalService.ClearPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getClearPayloadMethod;
    private static volatile MethodDescriptor<PointsInternalService.CreateFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getCreateFieldIndexMethod;
    private static volatile MethodDescriptor<PointsInternalService.DeleteFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteFieldIndexMethod;
    private static volatile MethodDescriptor<PointsInternalService.CoreSearchBatchPointsInternal, Points.SearchBatchResponse> getCoreSearchBatchMethod;
    private static volatile MethodDescriptor<PointsInternalService.ScrollPointsInternal, Points.ScrollResponse> getScrollMethod;
    private static volatile MethodDescriptor<PointsInternalService.CountPointsInternal, Points.CountResponse> getCountMethod;
    private static volatile MethodDescriptor<PointsInternalService.RecommendPointsInternal, Points.RecommendResponse> getRecommendMethod;
    private static volatile MethodDescriptor<PointsInternalService.GetPointsInternal, Points.GetResponse> getGetMethod;
    private static volatile MethodDescriptor<PointsInternalService.QueryBatchPointsInternal, PointsInternalService.QueryBatchResponseInternal> getQueryBatchMethod;
    private static volatile MethodDescriptor<PointsInternalService.FacetCountsInternal, PointsInternalService.FacetResponseInternal> getFacetMethod;
    private static final int METHODID_UPSERT = 0;
    private static final int METHODID_SYNC = 1;
    private static final int METHODID_DELETE = 2;
    private static final int METHODID_UPDATE_VECTORS = 3;
    private static final int METHODID_DELETE_VECTORS = 4;
    private static final int METHODID_SET_PAYLOAD = 5;
    private static final int METHODID_OVERWRITE_PAYLOAD = 6;
    private static final int METHODID_DELETE_PAYLOAD = 7;
    private static final int METHODID_CLEAR_PAYLOAD = 8;
    private static final int METHODID_CREATE_FIELD_INDEX = 9;
    private static final int METHODID_DELETE_FIELD_INDEX = 10;
    private static final int METHODID_CORE_SEARCH_BATCH = 11;
    private static final int METHODID_SCROLL = 12;
    private static final int METHODID_COUNT = 13;
    private static final int METHODID_RECOMMEND = 14;
    private static final int METHODID_GET = 15;
    private static final int METHODID_QUERY_BATCH = 16;
    private static final int METHODID_FACET = 17;
    private static volatile ServiceDescriptor serviceDescriptor;

    private PointsInternalGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Upsert", requestType=PointsInternalService.UpsertPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.UpsertPointsInternal, PointsInternalService.PointsOperationResponseInternal> getUpsertMethod() {
        MethodDescriptor<PointsInternalService.UpsertPointsInternal, PointsInternalService.PointsOperationResponseInternal> getUpsertMethod = PointsInternalGrpc.getUpsertMethod;
        if (getUpsertMethod != null) return getUpsertMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getUpsertMethod = PointsInternalGrpc.getUpsertMethod;
            if (getUpsertMethod != null) return getUpsertMethod;
            PointsInternalGrpc.getUpsertMethod = getUpsertMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Upsert")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.UpsertPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Upsert")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpsertMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Sync", requestType=PointsInternalService.SyncPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.SyncPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSyncMethod() {
        MethodDescriptor<PointsInternalService.SyncPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSyncMethod = PointsInternalGrpc.getSyncMethod;
        if (getSyncMethod != null) return getSyncMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getSyncMethod = PointsInternalGrpc.getSyncMethod;
            if (getSyncMethod != null) return getSyncMethod;
            PointsInternalGrpc.getSyncMethod = getSyncMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Sync")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.SyncPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Sync")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSyncMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Delete", requestType=PointsInternalService.DeletePointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.DeletePointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteMethod() {
        MethodDescriptor<PointsInternalService.DeletePointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteMethod = PointsInternalGrpc.getDeleteMethod;
        if (getDeleteMethod != null) return getDeleteMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getDeleteMethod = PointsInternalGrpc.getDeleteMethod;
            if (getDeleteMethod != null) return getDeleteMethod;
            PointsInternalGrpc.getDeleteMethod = getDeleteMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.DeletePointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Delete")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/UpdateVectors", requestType=PointsInternalService.UpdateVectorsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.UpdateVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getUpdateVectorsMethod() {
        MethodDescriptor<PointsInternalService.UpdateVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getUpdateVectorsMethod = PointsInternalGrpc.getUpdateVectorsMethod;
        if (getUpdateVectorsMethod != null) return getUpdateVectorsMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getUpdateVectorsMethod = PointsInternalGrpc.getUpdateVectorsMethod;
            if (getUpdateVectorsMethod != null) return getUpdateVectorsMethod;
            PointsInternalGrpc.getUpdateVectorsMethod = getUpdateVectorsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateVectors")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.UpdateVectorsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("UpdateVectors")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateVectorsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/DeleteVectors", requestType=PointsInternalService.DeleteVectorsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.DeleteVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteVectorsMethod() {
        MethodDescriptor<PointsInternalService.DeleteVectorsInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteVectorsMethod = PointsInternalGrpc.getDeleteVectorsMethod;
        if (getDeleteVectorsMethod != null) return getDeleteVectorsMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getDeleteVectorsMethod = PointsInternalGrpc.getDeleteVectorsMethod;
            if (getDeleteVectorsMethod != null) return getDeleteVectorsMethod;
            PointsInternalGrpc.getDeleteVectorsMethod = getDeleteVectorsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteVectors")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.DeleteVectorsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("DeleteVectors")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteVectorsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/SetPayload", requestType=PointsInternalService.SetPayloadPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSetPayloadMethod() {
        MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getSetPayloadMethod = PointsInternalGrpc.getSetPayloadMethod;
        if (getSetPayloadMethod != null) return getSetPayloadMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getSetPayloadMethod = PointsInternalGrpc.getSetPayloadMethod;
            if (getSetPayloadMethod != null) return getSetPayloadMethod;
            PointsInternalGrpc.getSetPayloadMethod = getSetPayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "SetPayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.SetPayloadPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("SetPayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSetPayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/OverwritePayload", requestType=PointsInternalService.SetPayloadPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getOverwritePayloadMethod() {
        MethodDescriptor<PointsInternalService.SetPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getOverwritePayloadMethod = PointsInternalGrpc.getOverwritePayloadMethod;
        if (getOverwritePayloadMethod != null) return getOverwritePayloadMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getOverwritePayloadMethod = PointsInternalGrpc.getOverwritePayloadMethod;
            if (getOverwritePayloadMethod != null) return getOverwritePayloadMethod;
            PointsInternalGrpc.getOverwritePayloadMethod = getOverwritePayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "OverwritePayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.SetPayloadPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("OverwritePayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getOverwritePayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/DeletePayload", requestType=PointsInternalService.DeletePayloadPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.DeletePayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeletePayloadMethod() {
        MethodDescriptor<PointsInternalService.DeletePayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getDeletePayloadMethod = PointsInternalGrpc.getDeletePayloadMethod;
        if (getDeletePayloadMethod != null) return getDeletePayloadMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getDeletePayloadMethod = PointsInternalGrpc.getDeletePayloadMethod;
            if (getDeletePayloadMethod != null) return getDeletePayloadMethod;
            PointsInternalGrpc.getDeletePayloadMethod = getDeletePayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeletePayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.DeletePayloadPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("DeletePayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeletePayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/ClearPayload", requestType=PointsInternalService.ClearPayloadPointsInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.ClearPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getClearPayloadMethod() {
        MethodDescriptor<PointsInternalService.ClearPayloadPointsInternal, PointsInternalService.PointsOperationResponseInternal> getClearPayloadMethod = PointsInternalGrpc.getClearPayloadMethod;
        if (getClearPayloadMethod != null) return getClearPayloadMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getClearPayloadMethod = PointsInternalGrpc.getClearPayloadMethod;
            if (getClearPayloadMethod != null) return getClearPayloadMethod;
            PointsInternalGrpc.getClearPayloadMethod = getClearPayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ClearPayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.ClearPayloadPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("ClearPayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getClearPayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/CreateFieldIndex", requestType=PointsInternalService.CreateFieldIndexCollectionInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.CreateFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getCreateFieldIndexMethod() {
        MethodDescriptor<PointsInternalService.CreateFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getCreateFieldIndexMethod = PointsInternalGrpc.getCreateFieldIndexMethod;
        if (getCreateFieldIndexMethod != null) return getCreateFieldIndexMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getCreateFieldIndexMethod = PointsInternalGrpc.getCreateFieldIndexMethod;
            if (getCreateFieldIndexMethod != null) return getCreateFieldIndexMethod;
            PointsInternalGrpc.getCreateFieldIndexMethod = getCreateFieldIndexMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CreateFieldIndex")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.CreateFieldIndexCollectionInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("CreateFieldIndex")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateFieldIndexMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/DeleteFieldIndex", requestType=PointsInternalService.DeleteFieldIndexCollectionInternal.class, responseType=PointsInternalService.PointsOperationResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.DeleteFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteFieldIndexMethod() {
        MethodDescriptor<PointsInternalService.DeleteFieldIndexCollectionInternal, PointsInternalService.PointsOperationResponseInternal> getDeleteFieldIndexMethod = PointsInternalGrpc.getDeleteFieldIndexMethod;
        if (getDeleteFieldIndexMethod != null) return getDeleteFieldIndexMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getDeleteFieldIndexMethod = PointsInternalGrpc.getDeleteFieldIndexMethod;
            if (getDeleteFieldIndexMethod != null) return getDeleteFieldIndexMethod;
            PointsInternalGrpc.getDeleteFieldIndexMethod = getDeleteFieldIndexMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteFieldIndex")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.DeleteFieldIndexCollectionInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.PointsOperationResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("DeleteFieldIndex")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteFieldIndexMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/CoreSearchBatch", requestType=PointsInternalService.CoreSearchBatchPointsInternal.class, responseType=Points.SearchBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.CoreSearchBatchPointsInternal, Points.SearchBatchResponse> getCoreSearchBatchMethod() {
        MethodDescriptor<PointsInternalService.CoreSearchBatchPointsInternal, Points.SearchBatchResponse> getCoreSearchBatchMethod = PointsInternalGrpc.getCoreSearchBatchMethod;
        if (getCoreSearchBatchMethod != null) return getCoreSearchBatchMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getCoreSearchBatchMethod = PointsInternalGrpc.getCoreSearchBatchMethod;
            if (getCoreSearchBatchMethod != null) return getCoreSearchBatchMethod;
            PointsInternalGrpc.getCoreSearchBatchMethod = getCoreSearchBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CoreSearchBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.CoreSearchBatchPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.SearchBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("CoreSearchBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCoreSearchBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Scroll", requestType=PointsInternalService.ScrollPointsInternal.class, responseType=Points.ScrollResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.ScrollPointsInternal, Points.ScrollResponse> getScrollMethod() {
        MethodDescriptor<PointsInternalService.ScrollPointsInternal, Points.ScrollResponse> getScrollMethod = PointsInternalGrpc.getScrollMethod;
        if (getScrollMethod != null) return getScrollMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getScrollMethod = PointsInternalGrpc.getScrollMethod;
            if (getScrollMethod != null) return getScrollMethod;
            PointsInternalGrpc.getScrollMethod = getScrollMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Scroll")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.ScrollPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.ScrollResponse.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Scroll")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getScrollMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Count", requestType=PointsInternalService.CountPointsInternal.class, responseType=Points.CountResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.CountPointsInternal, Points.CountResponse> getCountMethod() {
        MethodDescriptor<PointsInternalService.CountPointsInternal, Points.CountResponse> getCountMethod = PointsInternalGrpc.getCountMethod;
        if (getCountMethod != null) return getCountMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getCountMethod = PointsInternalGrpc.getCountMethod;
            if (getCountMethod != null) return getCountMethod;
            PointsInternalGrpc.getCountMethod = getCountMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Count")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.CountPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.CountResponse.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Count")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCountMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Recommend", requestType=PointsInternalService.RecommendPointsInternal.class, responseType=Points.RecommendResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.RecommendPointsInternal, Points.RecommendResponse> getRecommendMethod() {
        MethodDescriptor<PointsInternalService.RecommendPointsInternal, Points.RecommendResponse> getRecommendMethod = PointsInternalGrpc.getRecommendMethod;
        if (getRecommendMethod != null) return getRecommendMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getRecommendMethod = PointsInternalGrpc.getRecommendMethod;
            if (getRecommendMethod != null) return getRecommendMethod;
            PointsInternalGrpc.getRecommendMethod = getRecommendMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Recommend")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.RecommendPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.RecommendResponse.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Recommend")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getRecommendMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Get", requestType=PointsInternalService.GetPointsInternal.class, responseType=Points.GetResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.GetPointsInternal, Points.GetResponse> getGetMethod() {
        MethodDescriptor<PointsInternalService.GetPointsInternal, Points.GetResponse> getGetMethod = PointsInternalGrpc.getGetMethod;
        if (getGetMethod != null) return getGetMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getGetMethod = PointsInternalGrpc.getGetMethod;
            if (getGetMethod != null) return getGetMethod;
            PointsInternalGrpc.getGetMethod = getGetMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Get")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.GetPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.GetResponse.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Get")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/QueryBatch", requestType=PointsInternalService.QueryBatchPointsInternal.class, responseType=PointsInternalService.QueryBatchResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.QueryBatchPointsInternal, PointsInternalService.QueryBatchResponseInternal> getQueryBatchMethod() {
        MethodDescriptor<PointsInternalService.QueryBatchPointsInternal, PointsInternalService.QueryBatchResponseInternal> getQueryBatchMethod = PointsInternalGrpc.getQueryBatchMethod;
        if (getQueryBatchMethod != null) return getQueryBatchMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getQueryBatchMethod = PointsInternalGrpc.getQueryBatchMethod;
            if (getQueryBatchMethod != null) return getQueryBatchMethod;
            PointsInternalGrpc.getQueryBatchMethod = getQueryBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "QueryBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.QueryBatchPointsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.QueryBatchResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("QueryBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getQueryBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.PointsInternal/Facet", requestType=PointsInternalService.FacetCountsInternal.class, responseType=PointsInternalService.FacetResponseInternal.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<PointsInternalService.FacetCountsInternal, PointsInternalService.FacetResponseInternal> getFacetMethod() {
        MethodDescriptor<PointsInternalService.FacetCountsInternal, PointsInternalService.FacetResponseInternal> getFacetMethod = PointsInternalGrpc.getFacetMethod;
        if (getFacetMethod != null) return getFacetMethod;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            getFacetMethod = PointsInternalGrpc.getFacetMethod;
            if (getFacetMethod != null) return getFacetMethod;
            PointsInternalGrpc.getFacetMethod = getFacetMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Facet")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(PointsInternalService.FacetCountsInternal.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(PointsInternalService.FacetResponseInternal.getDefaultInstance())).setSchemaDescriptor(new PointsInternalMethodDescriptorSupplier("Facet")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getFacetMethod;
        }
    }

    public static PointsInternalStub newStub(Channel channel) {
        AbstractStub.StubFactory<PointsInternalStub> factory2 = new AbstractStub.StubFactory<PointsInternalStub>(){

            @Override
            public PointsInternalStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsInternalStub(channel, callOptions);
            }
        };
        return PointsInternalStub.newStub(factory2, channel);
    }

    public static PointsInternalBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<PointsInternalBlockingStub> factory2 = new AbstractStub.StubFactory<PointsInternalBlockingStub>(){

            @Override
            public PointsInternalBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsInternalBlockingStub(channel, callOptions);
            }
        };
        return PointsInternalBlockingStub.newStub(factory2, channel);
    }

    public static PointsInternalFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<PointsInternalFutureStub> factory2 = new AbstractStub.StubFactory<PointsInternalFutureStub>(){

            @Override
            public PointsInternalFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsInternalFutureStub(channel, callOptions);
            }
        };
        return PointsInternalFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(PointsInternalGrpc.getServiceDescriptor()).addMethod(PointsInternalGrpc.getUpsertMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(PointsInternalGrpc.getSyncMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(PointsInternalGrpc.getDeleteMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(PointsInternalGrpc.getUpdateVectorsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).addMethod(PointsInternalGrpc.getDeleteVectorsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 4))).addMethod(PointsInternalGrpc.getSetPayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 5))).addMethod(PointsInternalGrpc.getOverwritePayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 6))).addMethod(PointsInternalGrpc.getDeletePayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 7))).addMethod(PointsInternalGrpc.getClearPayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 8))).addMethod(PointsInternalGrpc.getCreateFieldIndexMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 9))).addMethod(PointsInternalGrpc.getDeleteFieldIndexMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 10))).addMethod(PointsInternalGrpc.getCoreSearchBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 11))).addMethod(PointsInternalGrpc.getScrollMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 12))).addMethod(PointsInternalGrpc.getCountMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 13))).addMethod(PointsInternalGrpc.getRecommendMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 14))).addMethod(PointsInternalGrpc.getGetMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 15))).addMethod(PointsInternalGrpc.getQueryBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 16))).addMethod(PointsInternalGrpc.getFacetMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 17))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<PointsInternalGrpc> clazz = PointsInternalGrpc.class;
        synchronized (PointsInternalGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new PointsInternalFileDescriptorSupplier()).addMethod(PointsInternalGrpc.getUpsertMethod()).addMethod(PointsInternalGrpc.getSyncMethod()).addMethod(PointsInternalGrpc.getDeleteMethod()).addMethod(PointsInternalGrpc.getUpdateVectorsMethod()).addMethod(PointsInternalGrpc.getDeleteVectorsMethod()).addMethod(PointsInternalGrpc.getSetPayloadMethod()).addMethod(PointsInternalGrpc.getOverwritePayloadMethod()).addMethod(PointsInternalGrpc.getDeletePayloadMethod()).addMethod(PointsInternalGrpc.getClearPayloadMethod()).addMethod(PointsInternalGrpc.getCreateFieldIndexMethod()).addMethod(PointsInternalGrpc.getDeleteFieldIndexMethod()).addMethod(PointsInternalGrpc.getCoreSearchBatchMethod()).addMethod(PointsInternalGrpc.getScrollMethod()).addMethod(PointsInternalGrpc.getCountMethod()).addMethod(PointsInternalGrpc.getRecommendMethod()).addMethod(PointsInternalGrpc.getGetMethod()).addMethod(PointsInternalGrpc.getQueryBatchMethod()).addMethod(PointsInternalGrpc.getFacetMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class PointsInternalMethodDescriptorSupplier
    extends PointsInternalBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        PointsInternalMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class PointsInternalStub
    extends AbstractAsyncStub<PointsInternalStub> {
        private PointsInternalStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsInternalStub build(Channel channel, CallOptions callOptions) {
            return new PointsInternalStub(channel, callOptions);
        }

        public void upsert(PointsInternalService.UpsertPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getUpsertMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void sync(PointsInternalService.SyncPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getSyncMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void delete(PointsInternalService.DeletePointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateVectors(PointsInternalService.UpdateVectorsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getUpdateVectorsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteVectors(PointsInternalService.DeleteVectorsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteVectorsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void setPayload(PointsInternalService.SetPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getSetPayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void overwritePayload(PointsInternalService.SetPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getOverwritePayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deletePayload(PointsInternalService.DeletePayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeletePayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void clearPayload(PointsInternalService.ClearPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getClearPayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void createFieldIndex(PointsInternalService.CreateFieldIndexCollectionInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCreateFieldIndexMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteFieldIndex(PointsInternalService.DeleteFieldIndexCollectionInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteFieldIndexMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void coreSearchBatch(PointsInternalService.CoreSearchBatchPointsInternal request, StreamObserver<Points.SearchBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCoreSearchBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void scroll(PointsInternalService.ScrollPointsInternal request, StreamObserver<Points.ScrollResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getScrollMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void count(PointsInternalService.CountPointsInternal request, StreamObserver<Points.CountResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCountMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void recommend(PointsInternalService.RecommendPointsInternal request, StreamObserver<Points.RecommendResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getRecommendMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void get(PointsInternalService.GetPointsInternal request, StreamObserver<Points.GetResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getGetMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void queryBatch(PointsInternalService.QueryBatchPointsInternal request, StreamObserver<PointsInternalService.QueryBatchResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getQueryBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void facet(PointsInternalService.FacetCountsInternal request, StreamObserver<PointsInternalService.FacetResponseInternal> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getFacetMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class PointsInternalBlockingStub
    extends AbstractBlockingStub<PointsInternalBlockingStub> {
        private PointsInternalBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsInternalBlockingStub build(Channel channel, CallOptions callOptions) {
            return new PointsInternalBlockingStub(channel, callOptions);
        }

        public PointsInternalService.PointsOperationResponseInternal upsert(PointsInternalService.UpsertPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getUpsertMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal sync(PointsInternalService.SyncPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getSyncMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal delete(PointsInternalService.DeletePointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getDeleteMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal updateVectors(PointsInternalService.UpdateVectorsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getUpdateVectorsMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal deleteVectors(PointsInternalService.DeleteVectorsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getDeleteVectorsMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal setPayload(PointsInternalService.SetPayloadPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getSetPayloadMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal overwritePayload(PointsInternalService.SetPayloadPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getOverwritePayloadMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal deletePayload(PointsInternalService.DeletePayloadPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getDeletePayloadMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal clearPayload(PointsInternalService.ClearPayloadPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getClearPayloadMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal createFieldIndex(PointsInternalService.CreateFieldIndexCollectionInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getCreateFieldIndexMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.PointsOperationResponseInternal deleteFieldIndex(PointsInternalService.DeleteFieldIndexCollectionInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getDeleteFieldIndexMethod(), this.getCallOptions(), request);
        }

        public Points.SearchBatchResponse coreSearchBatch(PointsInternalService.CoreSearchBatchPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getCoreSearchBatchMethod(), this.getCallOptions(), request);
        }

        public Points.ScrollResponse scroll(PointsInternalService.ScrollPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getScrollMethod(), this.getCallOptions(), request);
        }

        public Points.CountResponse count(PointsInternalService.CountPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getCountMethod(), this.getCallOptions(), request);
        }

        public Points.RecommendResponse recommend(PointsInternalService.RecommendPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getRecommendMethod(), this.getCallOptions(), request);
        }

        public Points.GetResponse get(PointsInternalService.GetPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getGetMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.QueryBatchResponseInternal queryBatch(PointsInternalService.QueryBatchPointsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getQueryBatchMethod(), this.getCallOptions(), request);
        }

        public PointsInternalService.FacetResponseInternal facet(PointsInternalService.FacetCountsInternal request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsInternalGrpc.getFacetMethod(), this.getCallOptions(), request);
        }
    }

    public static final class PointsInternalFutureStub
    extends AbstractFutureStub<PointsInternalFutureStub> {
        private PointsInternalFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsInternalFutureStub build(Channel channel, CallOptions callOptions) {
            return new PointsInternalFutureStub(channel, callOptions);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> upsert(PointsInternalService.UpsertPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getUpsertMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> sync(PointsInternalService.SyncPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getSyncMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> delete(PointsInternalService.DeletePointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> updateVectors(PointsInternalService.UpdateVectorsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getUpdateVectorsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> deleteVectors(PointsInternalService.DeleteVectorsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteVectorsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> setPayload(PointsInternalService.SetPayloadPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getSetPayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> overwritePayload(PointsInternalService.SetPayloadPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getOverwritePayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> deletePayload(PointsInternalService.DeletePayloadPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeletePayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> clearPayload(PointsInternalService.ClearPayloadPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getClearPayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> createFieldIndex(PointsInternalService.CreateFieldIndexCollectionInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCreateFieldIndexMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.PointsOperationResponseInternal> deleteFieldIndex(PointsInternalService.DeleteFieldIndexCollectionInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getDeleteFieldIndexMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.SearchBatchResponse> coreSearchBatch(PointsInternalService.CoreSearchBatchPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCoreSearchBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.ScrollResponse> scroll(PointsInternalService.ScrollPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getScrollMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.CountResponse> count(PointsInternalService.CountPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getCountMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.RecommendResponse> recommend(PointsInternalService.RecommendPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getRecommendMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.GetResponse> get(PointsInternalService.GetPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getGetMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.QueryBatchResponseInternal> queryBatch(PointsInternalService.QueryBatchPointsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getQueryBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<PointsInternalService.FacetResponseInternal> facet(PointsInternalService.FacetCountsInternal request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsInternalGrpc.getFacetMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.upsert((PointsInternalService.UpsertPointsInternal)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.sync((PointsInternalService.SyncPointsInternal)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.delete((PointsInternalService.DeletePointsInternal)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.updateVectors((PointsInternalService.UpdateVectorsInternal)request, responseObserver);
                    break;
                }
                case 4: {
                    this.serviceImpl.deleteVectors((PointsInternalService.DeleteVectorsInternal)request, responseObserver);
                    break;
                }
                case 5: {
                    this.serviceImpl.setPayload((PointsInternalService.SetPayloadPointsInternal)request, responseObserver);
                    break;
                }
                case 6: {
                    this.serviceImpl.overwritePayload((PointsInternalService.SetPayloadPointsInternal)request, responseObserver);
                    break;
                }
                case 7: {
                    this.serviceImpl.deletePayload((PointsInternalService.DeletePayloadPointsInternal)request, responseObserver);
                    break;
                }
                case 8: {
                    this.serviceImpl.clearPayload((PointsInternalService.ClearPayloadPointsInternal)request, responseObserver);
                    break;
                }
                case 9: {
                    this.serviceImpl.createFieldIndex((PointsInternalService.CreateFieldIndexCollectionInternal)request, responseObserver);
                    break;
                }
                case 10: {
                    this.serviceImpl.deleteFieldIndex((PointsInternalService.DeleteFieldIndexCollectionInternal)request, responseObserver);
                    break;
                }
                case 11: {
                    this.serviceImpl.coreSearchBatch((PointsInternalService.CoreSearchBatchPointsInternal)request, responseObserver);
                    break;
                }
                case 12: {
                    this.serviceImpl.scroll((PointsInternalService.ScrollPointsInternal)request, responseObserver);
                    break;
                }
                case 13: {
                    this.serviceImpl.count((PointsInternalService.CountPointsInternal)request, responseObserver);
                    break;
                }
                case 14: {
                    this.serviceImpl.recommend((PointsInternalService.RecommendPointsInternal)request, responseObserver);
                    break;
                }
                case 15: {
                    this.serviceImpl.get((PointsInternalService.GetPointsInternal)request, responseObserver);
                    break;
                }
                case 16: {
                    this.serviceImpl.queryBatch((PointsInternalService.QueryBatchPointsInternal)request, responseObserver);
                    break;
                }
                case 17: {
                    this.serviceImpl.facet((PointsInternalService.FacetCountsInternal)request, responseObserver);
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
        default public void upsert(PointsInternalService.UpsertPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getUpsertMethod(), responseObserver);
        }

        default public void sync(PointsInternalService.SyncPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getSyncMethod(), responseObserver);
        }

        default public void delete(PointsInternalService.DeletePointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getDeleteMethod(), responseObserver);
        }

        default public void updateVectors(PointsInternalService.UpdateVectorsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getUpdateVectorsMethod(), responseObserver);
        }

        default public void deleteVectors(PointsInternalService.DeleteVectorsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getDeleteVectorsMethod(), responseObserver);
        }

        default public void setPayload(PointsInternalService.SetPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getSetPayloadMethod(), responseObserver);
        }

        default public void overwritePayload(PointsInternalService.SetPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getOverwritePayloadMethod(), responseObserver);
        }

        default public void deletePayload(PointsInternalService.DeletePayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getDeletePayloadMethod(), responseObserver);
        }

        default public void clearPayload(PointsInternalService.ClearPayloadPointsInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getClearPayloadMethod(), responseObserver);
        }

        default public void createFieldIndex(PointsInternalService.CreateFieldIndexCollectionInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getCreateFieldIndexMethod(), responseObserver);
        }

        default public void deleteFieldIndex(PointsInternalService.DeleteFieldIndexCollectionInternal request, StreamObserver<PointsInternalService.PointsOperationResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getDeleteFieldIndexMethod(), responseObserver);
        }

        default public void coreSearchBatch(PointsInternalService.CoreSearchBatchPointsInternal request, StreamObserver<Points.SearchBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getCoreSearchBatchMethod(), responseObserver);
        }

        default public void scroll(PointsInternalService.ScrollPointsInternal request, StreamObserver<Points.ScrollResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getScrollMethod(), responseObserver);
        }

        default public void count(PointsInternalService.CountPointsInternal request, StreamObserver<Points.CountResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getCountMethod(), responseObserver);
        }

        default public void recommend(PointsInternalService.RecommendPointsInternal request, StreamObserver<Points.RecommendResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getRecommendMethod(), responseObserver);
        }

        default public void get(PointsInternalService.GetPointsInternal request, StreamObserver<Points.GetResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getGetMethod(), responseObserver);
        }

        default public void queryBatch(PointsInternalService.QueryBatchPointsInternal request, StreamObserver<PointsInternalService.QueryBatchResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getQueryBatchMethod(), responseObserver);
        }

        default public void facet(PointsInternalService.FacetCountsInternal request, StreamObserver<PointsInternalService.FacetResponseInternal> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsInternalGrpc.getFacetMethod(), responseObserver);
        }
    }

    private static final class PointsInternalFileDescriptorSupplier
    extends PointsInternalBaseDescriptorSupplier {
        PointsInternalFileDescriptorSupplier() {
        }
    }

    private static abstract class PointsInternalBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        PointsInternalBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return PointsInternalService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("PointsInternal");
        }
    }

    public static abstract class PointsInternalImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return PointsInternalGrpc.bindService(this);
        }
    }
}

