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
import io.qdrant.client.grpc.PointsService;

@GrpcGenerated
public final class PointsGrpc {
    public static final String SERVICE_NAME = "qdrant.Points";
    private static volatile MethodDescriptor<Points.UpsertPoints, Points.PointsOperationResponse> getUpsertMethod;
    private static volatile MethodDescriptor<Points.DeletePoints, Points.PointsOperationResponse> getDeleteMethod;
    private static volatile MethodDescriptor<Points.GetPoints, Points.GetResponse> getGetMethod;
    private static volatile MethodDescriptor<Points.UpdatePointVectors, Points.PointsOperationResponse> getUpdateVectorsMethod;
    private static volatile MethodDescriptor<Points.DeletePointVectors, Points.PointsOperationResponse> getDeleteVectorsMethod;
    private static volatile MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getSetPayloadMethod;
    private static volatile MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getOverwritePayloadMethod;
    private static volatile MethodDescriptor<Points.DeletePayloadPoints, Points.PointsOperationResponse> getDeletePayloadMethod;
    private static volatile MethodDescriptor<Points.ClearPayloadPoints, Points.PointsOperationResponse> getClearPayloadMethod;
    private static volatile MethodDescriptor<Points.CreateFieldIndexCollection, Points.PointsOperationResponse> getCreateFieldIndexMethod;
    private static volatile MethodDescriptor<Points.DeleteFieldIndexCollection, Points.PointsOperationResponse> getDeleteFieldIndexMethod;
    private static volatile MethodDescriptor<Points.SearchPoints, Points.SearchResponse> getSearchMethod;
    private static volatile MethodDescriptor<Points.SearchBatchPoints, Points.SearchBatchResponse> getSearchBatchMethod;
    private static volatile MethodDescriptor<Points.SearchPointGroups, Points.SearchGroupsResponse> getSearchGroupsMethod;
    private static volatile MethodDescriptor<Points.ScrollPoints, Points.ScrollResponse> getScrollMethod;
    private static volatile MethodDescriptor<Points.RecommendPoints, Points.RecommendResponse> getRecommendMethod;
    private static volatile MethodDescriptor<Points.RecommendBatchPoints, Points.RecommendBatchResponse> getRecommendBatchMethod;
    private static volatile MethodDescriptor<Points.RecommendPointGroups, Points.RecommendGroupsResponse> getRecommendGroupsMethod;
    private static volatile MethodDescriptor<Points.DiscoverPoints, Points.DiscoverResponse> getDiscoverMethod;
    private static volatile MethodDescriptor<Points.DiscoverBatchPoints, Points.DiscoverBatchResponse> getDiscoverBatchMethod;
    private static volatile MethodDescriptor<Points.CountPoints, Points.CountResponse> getCountMethod;
    private static volatile MethodDescriptor<Points.UpdateBatchPoints, Points.UpdateBatchResponse> getUpdateBatchMethod;
    private static volatile MethodDescriptor<Points.QueryPoints, Points.QueryResponse> getQueryMethod;
    private static volatile MethodDescriptor<Points.QueryBatchPoints, Points.QueryBatchResponse> getQueryBatchMethod;
    private static volatile MethodDescriptor<Points.QueryPointGroups, Points.QueryGroupsResponse> getQueryGroupsMethod;
    private static final int METHODID_UPSERT = 0;
    private static final int METHODID_DELETE = 1;
    private static final int METHODID_GET = 2;
    private static final int METHODID_UPDATE_VECTORS = 3;
    private static final int METHODID_DELETE_VECTORS = 4;
    private static final int METHODID_SET_PAYLOAD = 5;
    private static final int METHODID_OVERWRITE_PAYLOAD = 6;
    private static final int METHODID_DELETE_PAYLOAD = 7;
    private static final int METHODID_CLEAR_PAYLOAD = 8;
    private static final int METHODID_CREATE_FIELD_INDEX = 9;
    private static final int METHODID_DELETE_FIELD_INDEX = 10;
    private static final int METHODID_SEARCH = 11;
    private static final int METHODID_SEARCH_BATCH = 12;
    private static final int METHODID_SEARCH_GROUPS = 13;
    private static final int METHODID_SCROLL = 14;
    private static final int METHODID_RECOMMEND = 15;
    private static final int METHODID_RECOMMEND_BATCH = 16;
    private static final int METHODID_RECOMMEND_GROUPS = 17;
    private static final int METHODID_DISCOVER = 18;
    private static final int METHODID_DISCOVER_BATCH = 19;
    private static final int METHODID_COUNT = 20;
    private static final int METHODID_UPDATE_BATCH = 21;
    private static final int METHODID_QUERY = 22;
    private static final int METHODID_QUERY_BATCH = 23;
    private static final int METHODID_QUERY_GROUPS = 24;
    private static volatile ServiceDescriptor serviceDescriptor;

    private PointsGrpc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Upsert", requestType=Points.UpsertPoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.UpsertPoints, Points.PointsOperationResponse> getUpsertMethod() {
        MethodDescriptor<Points.UpsertPoints, Points.PointsOperationResponse> getUpsertMethod = PointsGrpc.getUpsertMethod;
        if (getUpsertMethod != null) return getUpsertMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getUpsertMethod = PointsGrpc.getUpsertMethod;
            if (getUpsertMethod != null) return getUpsertMethod;
            PointsGrpc.getUpsertMethod = getUpsertMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Upsert")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.UpsertPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Upsert")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpsertMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Delete", requestType=Points.DeletePoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DeletePoints, Points.PointsOperationResponse> getDeleteMethod() {
        MethodDescriptor<Points.DeletePoints, Points.PointsOperationResponse> getDeleteMethod = PointsGrpc.getDeleteMethod;
        if (getDeleteMethod != null) return getDeleteMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDeleteMethod = PointsGrpc.getDeleteMethod;
            if (getDeleteMethod != null) return getDeleteMethod;
            PointsGrpc.getDeleteMethod = getDeleteMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DeletePoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Delete")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Get", requestType=Points.GetPoints.class, responseType=Points.GetResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.GetPoints, Points.GetResponse> getGetMethod() {
        MethodDescriptor<Points.GetPoints, Points.GetResponse> getGetMethod = PointsGrpc.getGetMethod;
        if (getGetMethod != null) return getGetMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getGetMethod = PointsGrpc.getGetMethod;
            if (getGetMethod != null) return getGetMethod;
            PointsGrpc.getGetMethod = getGetMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Get")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.GetPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.GetResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Get")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getGetMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/UpdateVectors", requestType=Points.UpdatePointVectors.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.UpdatePointVectors, Points.PointsOperationResponse> getUpdateVectorsMethod() {
        MethodDescriptor<Points.UpdatePointVectors, Points.PointsOperationResponse> getUpdateVectorsMethod = PointsGrpc.getUpdateVectorsMethod;
        if (getUpdateVectorsMethod != null) return getUpdateVectorsMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getUpdateVectorsMethod = PointsGrpc.getUpdateVectorsMethod;
            if (getUpdateVectorsMethod != null) return getUpdateVectorsMethod;
            PointsGrpc.getUpdateVectorsMethod = getUpdateVectorsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateVectors")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.UpdatePointVectors.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("UpdateVectors")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateVectorsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/DeleteVectors", requestType=Points.DeletePointVectors.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DeletePointVectors, Points.PointsOperationResponse> getDeleteVectorsMethod() {
        MethodDescriptor<Points.DeletePointVectors, Points.PointsOperationResponse> getDeleteVectorsMethod = PointsGrpc.getDeleteVectorsMethod;
        if (getDeleteVectorsMethod != null) return getDeleteVectorsMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDeleteVectorsMethod = PointsGrpc.getDeleteVectorsMethod;
            if (getDeleteVectorsMethod != null) return getDeleteVectorsMethod;
            PointsGrpc.getDeleteVectorsMethod = getDeleteVectorsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteVectors")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DeletePointVectors.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("DeleteVectors")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteVectorsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/SetPayload", requestType=Points.SetPayloadPoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getSetPayloadMethod() {
        MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getSetPayloadMethod = PointsGrpc.getSetPayloadMethod;
        if (getSetPayloadMethod != null) return getSetPayloadMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getSetPayloadMethod = PointsGrpc.getSetPayloadMethod;
            if (getSetPayloadMethod != null) return getSetPayloadMethod;
            PointsGrpc.getSetPayloadMethod = getSetPayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "SetPayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.SetPayloadPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("SetPayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSetPayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/OverwritePayload", requestType=Points.SetPayloadPoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getOverwritePayloadMethod() {
        MethodDescriptor<Points.SetPayloadPoints, Points.PointsOperationResponse> getOverwritePayloadMethod = PointsGrpc.getOverwritePayloadMethod;
        if (getOverwritePayloadMethod != null) return getOverwritePayloadMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getOverwritePayloadMethod = PointsGrpc.getOverwritePayloadMethod;
            if (getOverwritePayloadMethod != null) return getOverwritePayloadMethod;
            PointsGrpc.getOverwritePayloadMethod = getOverwritePayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "OverwritePayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.SetPayloadPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("OverwritePayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getOverwritePayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/DeletePayload", requestType=Points.DeletePayloadPoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DeletePayloadPoints, Points.PointsOperationResponse> getDeletePayloadMethod() {
        MethodDescriptor<Points.DeletePayloadPoints, Points.PointsOperationResponse> getDeletePayloadMethod = PointsGrpc.getDeletePayloadMethod;
        if (getDeletePayloadMethod != null) return getDeletePayloadMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDeletePayloadMethod = PointsGrpc.getDeletePayloadMethod;
            if (getDeletePayloadMethod != null) return getDeletePayloadMethod;
            PointsGrpc.getDeletePayloadMethod = getDeletePayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeletePayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DeletePayloadPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("DeletePayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeletePayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/ClearPayload", requestType=Points.ClearPayloadPoints.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.ClearPayloadPoints, Points.PointsOperationResponse> getClearPayloadMethod() {
        MethodDescriptor<Points.ClearPayloadPoints, Points.PointsOperationResponse> getClearPayloadMethod = PointsGrpc.getClearPayloadMethod;
        if (getClearPayloadMethod != null) return getClearPayloadMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getClearPayloadMethod = PointsGrpc.getClearPayloadMethod;
            if (getClearPayloadMethod != null) return getClearPayloadMethod;
            PointsGrpc.getClearPayloadMethod = getClearPayloadMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ClearPayload")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.ClearPayloadPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("ClearPayload")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getClearPayloadMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/CreateFieldIndex", requestType=Points.CreateFieldIndexCollection.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.CreateFieldIndexCollection, Points.PointsOperationResponse> getCreateFieldIndexMethod() {
        MethodDescriptor<Points.CreateFieldIndexCollection, Points.PointsOperationResponse> getCreateFieldIndexMethod = PointsGrpc.getCreateFieldIndexMethod;
        if (getCreateFieldIndexMethod != null) return getCreateFieldIndexMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getCreateFieldIndexMethod = PointsGrpc.getCreateFieldIndexMethod;
            if (getCreateFieldIndexMethod != null) return getCreateFieldIndexMethod;
            PointsGrpc.getCreateFieldIndexMethod = getCreateFieldIndexMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CreateFieldIndex")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.CreateFieldIndexCollection.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("CreateFieldIndex")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCreateFieldIndexMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/DeleteFieldIndex", requestType=Points.DeleteFieldIndexCollection.class, responseType=Points.PointsOperationResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DeleteFieldIndexCollection, Points.PointsOperationResponse> getDeleteFieldIndexMethod() {
        MethodDescriptor<Points.DeleteFieldIndexCollection, Points.PointsOperationResponse> getDeleteFieldIndexMethod = PointsGrpc.getDeleteFieldIndexMethod;
        if (getDeleteFieldIndexMethod != null) return getDeleteFieldIndexMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDeleteFieldIndexMethod = PointsGrpc.getDeleteFieldIndexMethod;
            if (getDeleteFieldIndexMethod != null) return getDeleteFieldIndexMethod;
            PointsGrpc.getDeleteFieldIndexMethod = getDeleteFieldIndexMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteFieldIndex")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DeleteFieldIndexCollection.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.PointsOperationResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("DeleteFieldIndex")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDeleteFieldIndexMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Search", requestType=Points.SearchPoints.class, responseType=Points.SearchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.SearchPoints, Points.SearchResponse> getSearchMethod() {
        MethodDescriptor<Points.SearchPoints, Points.SearchResponse> getSearchMethod = PointsGrpc.getSearchMethod;
        if (getSearchMethod != null) return getSearchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getSearchMethod = PointsGrpc.getSearchMethod;
            if (getSearchMethod != null) return getSearchMethod;
            PointsGrpc.getSearchMethod = getSearchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Search")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.SearchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.SearchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Search")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSearchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/SearchBatch", requestType=Points.SearchBatchPoints.class, responseType=Points.SearchBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.SearchBatchPoints, Points.SearchBatchResponse> getSearchBatchMethod() {
        MethodDescriptor<Points.SearchBatchPoints, Points.SearchBatchResponse> getSearchBatchMethod = PointsGrpc.getSearchBatchMethod;
        if (getSearchBatchMethod != null) return getSearchBatchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getSearchBatchMethod = PointsGrpc.getSearchBatchMethod;
            if (getSearchBatchMethod != null) return getSearchBatchMethod;
            PointsGrpc.getSearchBatchMethod = getSearchBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "SearchBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.SearchBatchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.SearchBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("SearchBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSearchBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/SearchGroups", requestType=Points.SearchPointGroups.class, responseType=Points.SearchGroupsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.SearchPointGroups, Points.SearchGroupsResponse> getSearchGroupsMethod() {
        MethodDescriptor<Points.SearchPointGroups, Points.SearchGroupsResponse> getSearchGroupsMethod = PointsGrpc.getSearchGroupsMethod;
        if (getSearchGroupsMethod != null) return getSearchGroupsMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getSearchGroupsMethod = PointsGrpc.getSearchGroupsMethod;
            if (getSearchGroupsMethod != null) return getSearchGroupsMethod;
            PointsGrpc.getSearchGroupsMethod = getSearchGroupsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "SearchGroups")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.SearchPointGroups.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.SearchGroupsResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("SearchGroups")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getSearchGroupsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Scroll", requestType=Points.ScrollPoints.class, responseType=Points.ScrollResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.ScrollPoints, Points.ScrollResponse> getScrollMethod() {
        MethodDescriptor<Points.ScrollPoints, Points.ScrollResponse> getScrollMethod = PointsGrpc.getScrollMethod;
        if (getScrollMethod != null) return getScrollMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getScrollMethod = PointsGrpc.getScrollMethod;
            if (getScrollMethod != null) return getScrollMethod;
            PointsGrpc.getScrollMethod = getScrollMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Scroll")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.ScrollPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.ScrollResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Scroll")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getScrollMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Recommend", requestType=Points.RecommendPoints.class, responseType=Points.RecommendResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.RecommendPoints, Points.RecommendResponse> getRecommendMethod() {
        MethodDescriptor<Points.RecommendPoints, Points.RecommendResponse> getRecommendMethod = PointsGrpc.getRecommendMethod;
        if (getRecommendMethod != null) return getRecommendMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getRecommendMethod = PointsGrpc.getRecommendMethod;
            if (getRecommendMethod != null) return getRecommendMethod;
            PointsGrpc.getRecommendMethod = getRecommendMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Recommend")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.RecommendPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.RecommendResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Recommend")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getRecommendMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/RecommendBatch", requestType=Points.RecommendBatchPoints.class, responseType=Points.RecommendBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.RecommendBatchPoints, Points.RecommendBatchResponse> getRecommendBatchMethod() {
        MethodDescriptor<Points.RecommendBatchPoints, Points.RecommendBatchResponse> getRecommendBatchMethod = PointsGrpc.getRecommendBatchMethod;
        if (getRecommendBatchMethod != null) return getRecommendBatchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getRecommendBatchMethod = PointsGrpc.getRecommendBatchMethod;
            if (getRecommendBatchMethod != null) return getRecommendBatchMethod;
            PointsGrpc.getRecommendBatchMethod = getRecommendBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "RecommendBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.RecommendBatchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.RecommendBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("RecommendBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getRecommendBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/RecommendGroups", requestType=Points.RecommendPointGroups.class, responseType=Points.RecommendGroupsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.RecommendPointGroups, Points.RecommendGroupsResponse> getRecommendGroupsMethod() {
        MethodDescriptor<Points.RecommendPointGroups, Points.RecommendGroupsResponse> getRecommendGroupsMethod = PointsGrpc.getRecommendGroupsMethod;
        if (getRecommendGroupsMethod != null) return getRecommendGroupsMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getRecommendGroupsMethod = PointsGrpc.getRecommendGroupsMethod;
            if (getRecommendGroupsMethod != null) return getRecommendGroupsMethod;
            PointsGrpc.getRecommendGroupsMethod = getRecommendGroupsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "RecommendGroups")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.RecommendPointGroups.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.RecommendGroupsResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("RecommendGroups")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getRecommendGroupsMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Discover", requestType=Points.DiscoverPoints.class, responseType=Points.DiscoverResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DiscoverPoints, Points.DiscoverResponse> getDiscoverMethod() {
        MethodDescriptor<Points.DiscoverPoints, Points.DiscoverResponse> getDiscoverMethod = PointsGrpc.getDiscoverMethod;
        if (getDiscoverMethod != null) return getDiscoverMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDiscoverMethod = PointsGrpc.getDiscoverMethod;
            if (getDiscoverMethod != null) return getDiscoverMethod;
            PointsGrpc.getDiscoverMethod = getDiscoverMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Discover")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DiscoverPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.DiscoverResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Discover")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDiscoverMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/DiscoverBatch", requestType=Points.DiscoverBatchPoints.class, responseType=Points.DiscoverBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.DiscoverBatchPoints, Points.DiscoverBatchResponse> getDiscoverBatchMethod() {
        MethodDescriptor<Points.DiscoverBatchPoints, Points.DiscoverBatchResponse> getDiscoverBatchMethod = PointsGrpc.getDiscoverBatchMethod;
        if (getDiscoverBatchMethod != null) return getDiscoverBatchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getDiscoverBatchMethod = PointsGrpc.getDiscoverBatchMethod;
            if (getDiscoverBatchMethod != null) return getDiscoverBatchMethod;
            PointsGrpc.getDiscoverBatchMethod = getDiscoverBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DiscoverBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.DiscoverBatchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.DiscoverBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("DiscoverBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getDiscoverBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Count", requestType=Points.CountPoints.class, responseType=Points.CountResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.CountPoints, Points.CountResponse> getCountMethod() {
        MethodDescriptor<Points.CountPoints, Points.CountResponse> getCountMethod = PointsGrpc.getCountMethod;
        if (getCountMethod != null) return getCountMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getCountMethod = PointsGrpc.getCountMethod;
            if (getCountMethod != null) return getCountMethod;
            PointsGrpc.getCountMethod = getCountMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Count")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.CountPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.CountResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Count")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getCountMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/UpdateBatch", requestType=Points.UpdateBatchPoints.class, responseType=Points.UpdateBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.UpdateBatchPoints, Points.UpdateBatchResponse> getUpdateBatchMethod() {
        MethodDescriptor<Points.UpdateBatchPoints, Points.UpdateBatchResponse> getUpdateBatchMethod = PointsGrpc.getUpdateBatchMethod;
        if (getUpdateBatchMethod != null) return getUpdateBatchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getUpdateBatchMethod = PointsGrpc.getUpdateBatchMethod;
            if (getUpdateBatchMethod != null) return getUpdateBatchMethod;
            PointsGrpc.getUpdateBatchMethod = getUpdateBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.UpdateBatchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.UpdateBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("UpdateBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getUpdateBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/Query", requestType=Points.QueryPoints.class, responseType=Points.QueryResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.QueryPoints, Points.QueryResponse> getQueryMethod() {
        MethodDescriptor<Points.QueryPoints, Points.QueryResponse> getQueryMethod = PointsGrpc.getQueryMethod;
        if (getQueryMethod != null) return getQueryMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getQueryMethod = PointsGrpc.getQueryMethod;
            if (getQueryMethod != null) return getQueryMethod;
            PointsGrpc.getQueryMethod = getQueryMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Query")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.QueryPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.QueryResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("Query")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getQueryMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/QueryBatch", requestType=Points.QueryBatchPoints.class, responseType=Points.QueryBatchResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.QueryBatchPoints, Points.QueryBatchResponse> getQueryBatchMethod() {
        MethodDescriptor<Points.QueryBatchPoints, Points.QueryBatchResponse> getQueryBatchMethod = PointsGrpc.getQueryBatchMethod;
        if (getQueryBatchMethod != null) return getQueryBatchMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getQueryBatchMethod = PointsGrpc.getQueryBatchMethod;
            if (getQueryBatchMethod != null) return getQueryBatchMethod;
            PointsGrpc.getQueryBatchMethod = getQueryBatchMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "QueryBatch")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.QueryBatchPoints.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.QueryBatchResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("QueryBatch")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getQueryBatchMethod;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RpcMethod(fullMethodName="qdrant.Points/QueryGroups", requestType=Points.QueryPointGroups.class, responseType=Points.QueryGroupsResponse.class, methodType=MethodDescriptor.MethodType.UNARY)
    public static MethodDescriptor<Points.QueryPointGroups, Points.QueryGroupsResponse> getQueryGroupsMethod() {
        MethodDescriptor<Points.QueryPointGroups, Points.QueryGroupsResponse> getQueryGroupsMethod = PointsGrpc.getQueryGroupsMethod;
        if (getQueryGroupsMethod != null) return getQueryGroupsMethod;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            getQueryGroupsMethod = PointsGrpc.getQueryGroupsMethod;
            if (getQueryGroupsMethod != null) return getQueryGroupsMethod;
            PointsGrpc.getQueryGroupsMethod = getQueryGroupsMethod = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "QueryGroups")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(Points.QueryPointGroups.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(Points.QueryGroupsResponse.getDefaultInstance())).setSchemaDescriptor(new PointsMethodDescriptorSupplier("QueryGroups")).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return getQueryGroupsMethod;
        }
    }

    public static PointsStub newStub(Channel channel) {
        AbstractStub.StubFactory<PointsStub> factory2 = new AbstractStub.StubFactory<PointsStub>(){

            @Override
            public PointsStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsStub(channel, callOptions);
            }
        };
        return PointsStub.newStub(factory2, channel);
    }

    public static PointsBlockingStub newBlockingStub(Channel channel) {
        AbstractStub.StubFactory<PointsBlockingStub> factory2 = new AbstractStub.StubFactory<PointsBlockingStub>(){

            @Override
            public PointsBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsBlockingStub(channel, callOptions);
            }
        };
        return PointsBlockingStub.newStub(factory2, channel);
    }

    public static PointsFutureStub newFutureStub(Channel channel) {
        AbstractStub.StubFactory<PointsFutureStub> factory2 = new AbstractStub.StubFactory<PointsFutureStub>(){

            @Override
            public PointsFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new PointsFutureStub(channel, callOptions);
            }
        };
        return PointsFutureStub.newStub(factory2, channel);
    }

    public static final ServerServiceDefinition bindService(AsyncService service2) {
        return ServerServiceDefinition.builder(PointsGrpc.getServiceDescriptor()).addMethod(PointsGrpc.getUpsertMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 0))).addMethod(PointsGrpc.getDeleteMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 1))).addMethod(PointsGrpc.getGetMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 2))).addMethod(PointsGrpc.getUpdateVectorsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 3))).addMethod(PointsGrpc.getDeleteVectorsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 4))).addMethod(PointsGrpc.getSetPayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 5))).addMethod(PointsGrpc.getOverwritePayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 6))).addMethod(PointsGrpc.getDeletePayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 7))).addMethod(PointsGrpc.getClearPayloadMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 8))).addMethod(PointsGrpc.getCreateFieldIndexMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 9))).addMethod(PointsGrpc.getDeleteFieldIndexMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 10))).addMethod(PointsGrpc.getSearchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 11))).addMethod(PointsGrpc.getSearchBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 12))).addMethod(PointsGrpc.getSearchGroupsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 13))).addMethod(PointsGrpc.getScrollMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 14))).addMethod(PointsGrpc.getRecommendMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 15))).addMethod(PointsGrpc.getRecommendBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 16))).addMethod(PointsGrpc.getRecommendGroupsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 17))).addMethod(PointsGrpc.getDiscoverMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 18))).addMethod(PointsGrpc.getDiscoverBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 19))).addMethod(PointsGrpc.getCountMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 20))).addMethod(PointsGrpc.getUpdateBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 21))).addMethod(PointsGrpc.getQueryMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 22))).addMethod(PointsGrpc.getQueryBatchMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 23))).addMethod(PointsGrpc.getQueryGroupsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service2, 24))).build();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result != null) return result;
        Class<PointsGrpc> clazz = PointsGrpc.class;
        synchronized (PointsGrpc.class) {
            result = serviceDescriptor;
            if (result != null) return result;
            serviceDescriptor = result = ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new PointsFileDescriptorSupplier()).addMethod(PointsGrpc.getUpsertMethod()).addMethod(PointsGrpc.getDeleteMethod()).addMethod(PointsGrpc.getGetMethod()).addMethod(PointsGrpc.getUpdateVectorsMethod()).addMethod(PointsGrpc.getDeleteVectorsMethod()).addMethod(PointsGrpc.getSetPayloadMethod()).addMethod(PointsGrpc.getOverwritePayloadMethod()).addMethod(PointsGrpc.getDeletePayloadMethod()).addMethod(PointsGrpc.getClearPayloadMethod()).addMethod(PointsGrpc.getCreateFieldIndexMethod()).addMethod(PointsGrpc.getDeleteFieldIndexMethod()).addMethod(PointsGrpc.getSearchMethod()).addMethod(PointsGrpc.getSearchBatchMethod()).addMethod(PointsGrpc.getSearchGroupsMethod()).addMethod(PointsGrpc.getScrollMethod()).addMethod(PointsGrpc.getRecommendMethod()).addMethod(PointsGrpc.getRecommendBatchMethod()).addMethod(PointsGrpc.getRecommendGroupsMethod()).addMethod(PointsGrpc.getDiscoverMethod()).addMethod(PointsGrpc.getDiscoverBatchMethod()).addMethod(PointsGrpc.getCountMethod()).addMethod(PointsGrpc.getUpdateBatchMethod()).addMethod(PointsGrpc.getQueryMethod()).addMethod(PointsGrpc.getQueryBatchMethod()).addMethod(PointsGrpc.getQueryGroupsMethod()).build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return result;
        }
    }

    private static final class PointsMethodDescriptorSupplier
    extends PointsBaseDescriptorSupplier
    implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        PointsMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    public static final class PointsStub
    extends AbstractAsyncStub<PointsStub> {
        private PointsStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsStub build(Channel channel, CallOptions callOptions) {
            return new PointsStub(channel, callOptions);
        }

        public void upsert(Points.UpsertPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getUpsertMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void delete(Points.DeletePoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void get(Points.GetPoints request, StreamObserver<Points.GetResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getGetMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateVectors(Points.UpdatePointVectors request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getUpdateVectorsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteVectors(Points.DeletePointVectors request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteVectorsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void setPayload(Points.SetPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getSetPayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void overwritePayload(Points.SetPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getOverwritePayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deletePayload(Points.DeletePayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDeletePayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void clearPayload(Points.ClearPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getClearPayloadMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void createFieldIndex(Points.CreateFieldIndexCollection request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getCreateFieldIndexMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void deleteFieldIndex(Points.DeleteFieldIndexCollection request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteFieldIndexMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void search(Points.SearchPoints request, StreamObserver<Points.SearchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void searchBatch(Points.SearchBatchPoints request, StreamObserver<Points.SearchBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void searchGroups(Points.SearchPointGroups request, StreamObserver<Points.SearchGroupsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchGroupsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void scroll(Points.ScrollPoints request, StreamObserver<Points.ScrollResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getScrollMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void recommend(Points.RecommendPoints request, StreamObserver<Points.RecommendResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void recommendBatch(Points.RecommendBatchPoints request, StreamObserver<Points.RecommendBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void recommendGroups(Points.RecommendPointGroups request, StreamObserver<Points.RecommendGroupsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendGroupsMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void discover(Points.DiscoverPoints request, StreamObserver<Points.DiscoverResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDiscoverMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void discoverBatch(Points.DiscoverBatchPoints request, StreamObserver<Points.DiscoverBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getDiscoverBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void count(Points.CountPoints request, StreamObserver<Points.CountResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getCountMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void updateBatch(Points.UpdateBatchPoints request, StreamObserver<Points.UpdateBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getUpdateBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void query(Points.QueryPoints request, StreamObserver<Points.QueryResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void queryBatch(Points.QueryBatchPoints request, StreamObserver<Points.QueryBatchResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryBatchMethod(), this.getCallOptions()), request, responseObserver);
        }

        public void queryGroups(Points.QueryPointGroups request, StreamObserver<Points.QueryGroupsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryGroupsMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public static final class PointsBlockingStub
    extends AbstractBlockingStub<PointsBlockingStub> {
        private PointsBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsBlockingStub build(Channel channel, CallOptions callOptions) {
            return new PointsBlockingStub(channel, callOptions);
        }

        public Points.PointsOperationResponse upsert(Points.UpsertPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getUpsertMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse delete(Points.DeletePoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDeleteMethod(), this.getCallOptions(), request);
        }

        public Points.GetResponse get(Points.GetPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getGetMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse updateVectors(Points.UpdatePointVectors request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getUpdateVectorsMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse deleteVectors(Points.DeletePointVectors request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDeleteVectorsMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse setPayload(Points.SetPayloadPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getSetPayloadMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse overwritePayload(Points.SetPayloadPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getOverwritePayloadMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse deletePayload(Points.DeletePayloadPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDeletePayloadMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse clearPayload(Points.ClearPayloadPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getClearPayloadMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse createFieldIndex(Points.CreateFieldIndexCollection request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getCreateFieldIndexMethod(), this.getCallOptions(), request);
        }

        public Points.PointsOperationResponse deleteFieldIndex(Points.DeleteFieldIndexCollection request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDeleteFieldIndexMethod(), this.getCallOptions(), request);
        }

        public Points.SearchResponse search(Points.SearchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getSearchMethod(), this.getCallOptions(), request);
        }

        public Points.SearchBatchResponse searchBatch(Points.SearchBatchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getSearchBatchMethod(), this.getCallOptions(), request);
        }

        public Points.SearchGroupsResponse searchGroups(Points.SearchPointGroups request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getSearchGroupsMethod(), this.getCallOptions(), request);
        }

        public Points.ScrollResponse scroll(Points.ScrollPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getScrollMethod(), this.getCallOptions(), request);
        }

        public Points.RecommendResponse recommend(Points.RecommendPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getRecommendMethod(), this.getCallOptions(), request);
        }

        public Points.RecommendBatchResponse recommendBatch(Points.RecommendBatchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getRecommendBatchMethod(), this.getCallOptions(), request);
        }

        public Points.RecommendGroupsResponse recommendGroups(Points.RecommendPointGroups request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getRecommendGroupsMethod(), this.getCallOptions(), request);
        }

        public Points.DiscoverResponse discover(Points.DiscoverPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDiscoverMethod(), this.getCallOptions(), request);
        }

        public Points.DiscoverBatchResponse discoverBatch(Points.DiscoverBatchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getDiscoverBatchMethod(), this.getCallOptions(), request);
        }

        public Points.CountResponse count(Points.CountPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getCountMethod(), this.getCallOptions(), request);
        }

        public Points.UpdateBatchResponse updateBatch(Points.UpdateBatchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getUpdateBatchMethod(), this.getCallOptions(), request);
        }

        public Points.QueryResponse query(Points.QueryPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getQueryMethod(), this.getCallOptions(), request);
        }

        public Points.QueryBatchResponse queryBatch(Points.QueryBatchPoints request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getQueryBatchMethod(), this.getCallOptions(), request);
        }

        public Points.QueryGroupsResponse queryGroups(Points.QueryPointGroups request) {
            return ClientCalls.blockingUnaryCall(this.getChannel(), PointsGrpc.getQueryGroupsMethod(), this.getCallOptions(), request);
        }
    }

    public static final class PointsFutureStub
    extends AbstractFutureStub<PointsFutureStub> {
        private PointsFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected PointsFutureStub build(Channel channel, CallOptions callOptions) {
            return new PointsFutureStub(channel, callOptions);
        }

        public ListenableFuture<Points.PointsOperationResponse> upsert(Points.UpsertPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getUpsertMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> delete(Points.DeletePoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.GetResponse> get(Points.GetPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getGetMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> updateVectors(Points.UpdatePointVectors request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getUpdateVectorsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> deleteVectors(Points.DeletePointVectors request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteVectorsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> setPayload(Points.SetPayloadPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getSetPayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> overwritePayload(Points.SetPayloadPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getOverwritePayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> deletePayload(Points.DeletePayloadPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDeletePayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> clearPayload(Points.ClearPayloadPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getClearPayloadMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> createFieldIndex(Points.CreateFieldIndexCollection request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getCreateFieldIndexMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.PointsOperationResponse> deleteFieldIndex(Points.DeleteFieldIndexCollection request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDeleteFieldIndexMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.SearchResponse> search(Points.SearchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.SearchBatchResponse> searchBatch(Points.SearchBatchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.SearchGroupsResponse> searchGroups(Points.SearchPointGroups request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getSearchGroupsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.ScrollResponse> scroll(Points.ScrollPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getScrollMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.RecommendResponse> recommend(Points.RecommendPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.RecommendBatchResponse> recommendBatch(Points.RecommendBatchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.RecommendGroupsResponse> recommendGroups(Points.RecommendPointGroups request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getRecommendGroupsMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.DiscoverResponse> discover(Points.DiscoverPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDiscoverMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.DiscoverBatchResponse> discoverBatch(Points.DiscoverBatchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getDiscoverBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.CountResponse> count(Points.CountPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getCountMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.UpdateBatchResponse> updateBatch(Points.UpdateBatchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getUpdateBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.QueryResponse> query(Points.QueryPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.QueryBatchResponse> queryBatch(Points.QueryBatchPoints request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryBatchMethod(), this.getCallOptions()), request);
        }

        public ListenableFuture<Points.QueryGroupsResponse> queryGroups(Points.QueryPointGroups request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(PointsGrpc.getQueryGroupsMethod(), this.getCallOptions()), request);
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
                    this.serviceImpl.upsert((Points.UpsertPoints)request, responseObserver);
                    break;
                }
                case 1: {
                    this.serviceImpl.delete((Points.DeletePoints)request, responseObserver);
                    break;
                }
                case 2: {
                    this.serviceImpl.get((Points.GetPoints)request, responseObserver);
                    break;
                }
                case 3: {
                    this.serviceImpl.updateVectors((Points.UpdatePointVectors)request, responseObserver);
                    break;
                }
                case 4: {
                    this.serviceImpl.deleteVectors((Points.DeletePointVectors)request, responseObserver);
                    break;
                }
                case 5: {
                    this.serviceImpl.setPayload((Points.SetPayloadPoints)request, responseObserver);
                    break;
                }
                case 6: {
                    this.serviceImpl.overwritePayload((Points.SetPayloadPoints)request, responseObserver);
                    break;
                }
                case 7: {
                    this.serviceImpl.deletePayload((Points.DeletePayloadPoints)request, responseObserver);
                    break;
                }
                case 8: {
                    this.serviceImpl.clearPayload((Points.ClearPayloadPoints)request, responseObserver);
                    break;
                }
                case 9: {
                    this.serviceImpl.createFieldIndex((Points.CreateFieldIndexCollection)request, responseObserver);
                    break;
                }
                case 10: {
                    this.serviceImpl.deleteFieldIndex((Points.DeleteFieldIndexCollection)request, responseObserver);
                    break;
                }
                case 11: {
                    this.serviceImpl.search((Points.SearchPoints)request, responseObserver);
                    break;
                }
                case 12: {
                    this.serviceImpl.searchBatch((Points.SearchBatchPoints)request, responseObserver);
                    break;
                }
                case 13: {
                    this.serviceImpl.searchGroups((Points.SearchPointGroups)request, responseObserver);
                    break;
                }
                case 14: {
                    this.serviceImpl.scroll((Points.ScrollPoints)request, responseObserver);
                    break;
                }
                case 15: {
                    this.serviceImpl.recommend((Points.RecommendPoints)request, responseObserver);
                    break;
                }
                case 16: {
                    this.serviceImpl.recommendBatch((Points.RecommendBatchPoints)request, responseObserver);
                    break;
                }
                case 17: {
                    this.serviceImpl.recommendGroups((Points.RecommendPointGroups)request, responseObserver);
                    break;
                }
                case 18: {
                    this.serviceImpl.discover((Points.DiscoverPoints)request, responseObserver);
                    break;
                }
                case 19: {
                    this.serviceImpl.discoverBatch((Points.DiscoverBatchPoints)request, responseObserver);
                    break;
                }
                case 20: {
                    this.serviceImpl.count((Points.CountPoints)request, responseObserver);
                    break;
                }
                case 21: {
                    this.serviceImpl.updateBatch((Points.UpdateBatchPoints)request, responseObserver);
                    break;
                }
                case 22: {
                    this.serviceImpl.query((Points.QueryPoints)request, responseObserver);
                    break;
                }
                case 23: {
                    this.serviceImpl.queryBatch((Points.QueryBatchPoints)request, responseObserver);
                    break;
                }
                case 24: {
                    this.serviceImpl.queryGroups((Points.QueryPointGroups)request, responseObserver);
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
        default public void upsert(Points.UpsertPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getUpsertMethod(), responseObserver);
        }

        default public void delete(Points.DeletePoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDeleteMethod(), responseObserver);
        }

        default public void get(Points.GetPoints request, StreamObserver<Points.GetResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getGetMethod(), responseObserver);
        }

        default public void updateVectors(Points.UpdatePointVectors request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getUpdateVectorsMethod(), responseObserver);
        }

        default public void deleteVectors(Points.DeletePointVectors request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDeleteVectorsMethod(), responseObserver);
        }

        default public void setPayload(Points.SetPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getSetPayloadMethod(), responseObserver);
        }

        default public void overwritePayload(Points.SetPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getOverwritePayloadMethod(), responseObserver);
        }

        default public void deletePayload(Points.DeletePayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDeletePayloadMethod(), responseObserver);
        }

        default public void clearPayload(Points.ClearPayloadPoints request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getClearPayloadMethod(), responseObserver);
        }

        default public void createFieldIndex(Points.CreateFieldIndexCollection request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getCreateFieldIndexMethod(), responseObserver);
        }

        default public void deleteFieldIndex(Points.DeleteFieldIndexCollection request, StreamObserver<Points.PointsOperationResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDeleteFieldIndexMethod(), responseObserver);
        }

        default public void search(Points.SearchPoints request, StreamObserver<Points.SearchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getSearchMethod(), responseObserver);
        }

        default public void searchBatch(Points.SearchBatchPoints request, StreamObserver<Points.SearchBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getSearchBatchMethod(), responseObserver);
        }

        default public void searchGroups(Points.SearchPointGroups request, StreamObserver<Points.SearchGroupsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getSearchGroupsMethod(), responseObserver);
        }

        default public void scroll(Points.ScrollPoints request, StreamObserver<Points.ScrollResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getScrollMethod(), responseObserver);
        }

        default public void recommend(Points.RecommendPoints request, StreamObserver<Points.RecommendResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getRecommendMethod(), responseObserver);
        }

        default public void recommendBatch(Points.RecommendBatchPoints request, StreamObserver<Points.RecommendBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getRecommendBatchMethod(), responseObserver);
        }

        default public void recommendGroups(Points.RecommendPointGroups request, StreamObserver<Points.RecommendGroupsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getRecommendGroupsMethod(), responseObserver);
        }

        default public void discover(Points.DiscoverPoints request, StreamObserver<Points.DiscoverResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDiscoverMethod(), responseObserver);
        }

        default public void discoverBatch(Points.DiscoverBatchPoints request, StreamObserver<Points.DiscoverBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getDiscoverBatchMethod(), responseObserver);
        }

        default public void count(Points.CountPoints request, StreamObserver<Points.CountResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getCountMethod(), responseObserver);
        }

        default public void updateBatch(Points.UpdateBatchPoints request, StreamObserver<Points.UpdateBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getUpdateBatchMethod(), responseObserver);
        }

        default public void query(Points.QueryPoints request, StreamObserver<Points.QueryResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getQueryMethod(), responseObserver);
        }

        default public void queryBatch(Points.QueryBatchPoints request, StreamObserver<Points.QueryBatchResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getQueryBatchMethod(), responseObserver);
        }

        default public void queryGroups(Points.QueryPointGroups request, StreamObserver<Points.QueryGroupsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(PointsGrpc.getQueryGroupsMethod(), responseObserver);
        }
    }

    private static final class PointsFileDescriptorSupplier
    extends PointsBaseDescriptorSupplier {
        PointsFileDescriptorSupplier() {
        }
    }

    private static abstract class PointsBaseDescriptorSupplier
    implements ProtoFileDescriptorSupplier,
    ProtoServiceDescriptorSupplier {
        PointsBaseDescriptorSupplier() {
        }

        @Override
        public Descriptors.FileDescriptor getFileDescriptor() {
            return PointsService.getDescriptor();
        }

        @Override
        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Points");
        }
    }

    public static abstract class PointsImplBase
    implements BindableService,
    AsyncService {
        @Override
        public final ServerServiceDefinition bindService() {
            return PointsGrpc.bindService(this);
        }
    }
}

