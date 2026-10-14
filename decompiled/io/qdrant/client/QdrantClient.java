/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import io.qdrant.client.QdrantException;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.WithPayloadSelectorFactory;
import io.qdrant.client.WithVectorsSelectorFactory;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.CollectionsGrpc;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import io.qdrant.client.grpc.PointsGrpc;
import io.qdrant.client.grpc.QdrantGrpc;
import io.qdrant.client.grpc.QdrantOuterClass;
import io.qdrant.client.grpc.SnapshotsGrpc;
import io.qdrant.client.grpc.SnapshotsService;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QdrantClient
implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(QdrantClient.class);
    private final QdrantGrpcClient grpcClient;

    public QdrantClient(QdrantGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    public QdrantGrpcClient grpcClient() {
        return this.grpcClient;
    }

    public ListenableFuture<QdrantOuterClass.HealthCheckReply> healthCheckAsync() {
        return this.healthCheckAsync(null);
    }

    public ListenableFuture<QdrantOuterClass.HealthCheckReply> healthCheckAsync(@Nullable Duration timeout2) {
        QdrantGrpc.QdrantFutureStub qdrant = timeout2 != null ? (QdrantGrpc.QdrantFutureStub)this.grpcClient.qdrant().withDeadlineAfter(timeout2.toMillis(), TimeUnit.MILLISECONDS) : this.grpcClient.qdrant();
        return qdrant.healthCheck(QdrantOuterClass.HealthCheckRequest.getDefaultInstance());
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(String collectionName, Collections.VectorParams vectorParams) {
        return this.createCollectionAsync(collectionName, vectorParams, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(String collectionName, Collections.VectorParams vectorParams, @Nullable Duration timeout2) {
        return this.createCollectionAsync(Collections.CreateCollection.newBuilder().setCollectionName(collectionName).setVectorsConfig(Collections.VectorsConfig.newBuilder().setParams(vectorParams).build()).build(), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(String collectionName, Map<String, Collections.VectorParams> namedVectorParams) {
        return this.createCollectionAsync(collectionName, namedVectorParams, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(String collectionName, Map<String, Collections.VectorParams> namedVectorParams, @Nullable Duration timeout2) {
        return this.createCollectionAsync(Collections.CreateCollection.newBuilder().setCollectionName(collectionName).setVectorsConfig(Collections.VectorsConfig.newBuilder().setParamsMap(Collections.VectorParamsMap.newBuilder().putAllMap(namedVectorParams).build()).build()).build(), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(Collections.CreateCollection createCollection) {
        return this.createCollectionAsync(createCollection, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createCollectionAsync(Collections.CreateCollection createCollection, @Nullable Duration timeout2) {
        String collectionName = createCollection.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Create collection '{}'", (Object)collectionName);
        ListenableFuture<Collections.CollectionOperationResponse> future = this.getCollections(timeout2).create(createCollection);
        this.addLogFailureCallback(future, "Create collection");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Collection '{}' could not be created", (Object)collectionName);
                throw new QdrantException("Collection '" + collectionName + "' could not be created");
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(String collectionName, Collections.VectorParams vectorParams) {
        return this.recreateCollectionAsync(collectionName, vectorParams, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(String collectionName, Collections.VectorParams vectorParams, @Nullable Duration timeout2) {
        return this.recreateCollectionAsync(Collections.CreateCollection.newBuilder().setCollectionName(collectionName).setVectorsConfig(Collections.VectorsConfig.newBuilder().setParams(vectorParams).build()).build(), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(String collectionName, Map<String, Collections.VectorParams> namedVectorParams) {
        return this.recreateCollectionAsync(collectionName, namedVectorParams, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(String collectionName, Map<String, Collections.VectorParams> namedVectorParams, @Nullable Duration timeout2) {
        return this.recreateCollectionAsync(Collections.CreateCollection.newBuilder().setCollectionName(collectionName).setVectorsConfig(Collections.VectorsConfig.newBuilder().setParamsMap(Collections.VectorParamsMap.newBuilder().putAllMap(namedVectorParams).build()).build()).build(), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(Collections.CreateCollection createCollection) {
        return this.recreateCollectionAsync(createCollection, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> recreateCollectionAsync(Collections.CreateCollection createCollection, @Nullable Duration timeout2) {
        return Futures.transformAsync(this.deleteCollectionAsync(createCollection.getCollectionName(), timeout2), input -> this.createCollectionAsync(createCollection, timeout2), MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CollectionInfo> getCollectionInfoAsync(String collectionName) {
        return this.getCollectionInfoAsync(collectionName, null);
    }

    public ListenableFuture<Collections.CollectionInfo> getCollectionInfoAsync(String collectionName, @Nullable Duration timeout2) {
        logger.debug("Get collection info for '{}'", (Object)collectionName);
        Collections.GetCollectionInfoRequest request = Collections.GetCollectionInfoRequest.newBuilder().setCollectionName(collectionName).build();
        ListenableFuture<Collections.GetCollectionInfoResponse> future = this.getCollections(timeout2).get(request);
        this.addLogFailureCallback(future, "Get collection info");
        return Futures.transform(future, Collections.GetCollectionInfoResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CollectionOperationResponse> deleteCollectionAsync(String collectionName) {
        return this.deleteCollectionAsync(collectionName, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> deleteCollectionAsync(String collectionName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Delete collection '{}'", (Object)collectionName);
        Collections.DeleteCollection deleteCollection = Collections.DeleteCollection.newBuilder().setCollectionName(collectionName).build();
        ListenableFuture<Collections.CollectionOperationResponse> future = this.getCollections(timeout2).delete(deleteCollection);
        this.addLogFailureCallback(future, "Delete collection");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Collection '{}' could not be deleted", (Object)collectionName);
                throw new QdrantException("Collection '" + collectionName + "' could not be deleted");
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<String>> listCollectionsAsync() {
        return this.listCollectionsAsync(null);
    }

    public ListenableFuture<List<String>> listCollectionsAsync(@Nullable Duration timeout2) {
        logger.debug("List collections");
        ListenableFuture<Collections.ListCollectionsResponse> future = this.getCollections(timeout2).list(Collections.ListCollectionsRequest.getDefaultInstance());
        this.addLogFailureCallback(future, "List collection");
        return Futures.transform(future, response -> response.getCollectionsList().stream().map(Collections.CollectionDescription::getName).collect(Collectors.toList()), MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CollectionOperationResponse> updateCollectionAsync(Collections.UpdateCollection updateCollection) {
        return this.updateCollectionAsync(updateCollection, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> updateCollectionAsync(Collections.UpdateCollection updateCollection, @Nullable Duration timeout2) {
        String collectionName = updateCollection.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Update collection '{}'", (Object)collectionName);
        ListenableFuture<Collections.CollectionOperationResponse> future = this.getCollections(timeout2).update(updateCollection);
        this.addLogFailureCallback(future, "Update collection");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Collection '{}' could not be updated", (Object)collectionName);
                throw new QdrantException("Collection '" + collectionName + "' could not be updated");
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Boolean> collectionExistsAsync(String collectionName) {
        return this.collectionExistsAsync(collectionName, null);
    }

    public ListenableFuture<Boolean> collectionExistsAsync(String collectionName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Collection exists '{}'", (Object)collectionName);
        ListenableFuture<Collections.CollectionExistsResponse> future = this.getCollections(timeout2).collectionExists(Collections.CollectionExistsRequest.newBuilder().setCollectionName(collectionName).build());
        this.addLogFailureCallback(future, "Collection exists");
        return Futures.transform(future, response -> response.getResult().getExists(), MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createAliasAsync(String aliasName, String collectionName) {
        return this.createAliasAsync(aliasName, collectionName, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> createAliasAsync(String aliasName, String collectionName, @Nullable Duration timeout2) {
        return this.updateAliasesAsync(ImmutableList.of(Collections.AliasOperations.newBuilder().setCreateAlias(Collections.CreateAlias.newBuilder().setAliasName(aliasName).setCollectionName(collectionName).build()).build()), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> renameAliasAsync(String oldAliasName, String newAliasName) {
        return this.renameAliasAsync(oldAliasName, newAliasName, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> renameAliasAsync(String oldAliasName, String newAliasName, @Nullable Duration timeout2) {
        return this.updateAliasesAsync(ImmutableList.of(Collections.AliasOperations.newBuilder().setRenameAlias(Collections.RenameAlias.newBuilder().setOldAliasName(oldAliasName).setNewAliasName(newAliasName).build()).build()), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> deleteAliasAsync(String aliasName) {
        return this.deleteAliasAsync(aliasName, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> deleteAliasAsync(String aliasName, @Nullable Duration timeout2) {
        return this.updateAliasesAsync(ImmutableList.of(Collections.AliasOperations.newBuilder().setDeleteAlias(Collections.DeleteAlias.newBuilder().setAliasName(aliasName).build()).build()), timeout2);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> updateAliasesAsync(List<Collections.AliasOperations> aliasOperations) {
        return this.updateAliasesAsync(aliasOperations, null);
    }

    public ListenableFuture<Collections.CollectionOperationResponse> updateAliasesAsync(List<Collections.AliasOperations> aliasOperations, @Nullable Duration timeout2) {
        Collections.ChangeAliases request = Collections.ChangeAliases.newBuilder().addAllActions(aliasOperations).build();
        if (logger.isDebugEnabled()) {
            for (Collections.AliasOperations aliasOperation : aliasOperations) {
                switch (aliasOperation.getActionCase()) {
                    case CREATE_ALIAS: {
                        Collections.CreateAlias createAlias = aliasOperation.getCreateAlias();
                        logger.debug("Create alias '{}' for collection '{}'", (Object)createAlias.getAliasName(), (Object)createAlias.getCollectionName());
                        break;
                    }
                    case RENAME_ALIAS: {
                        Collections.RenameAlias renameAlias = aliasOperation.getRenameAlias();
                        logger.debug("Rename alias '{}' to '{}'", (Object)renameAlias.getOldAliasName(), (Object)renameAlias.getNewAliasName());
                        break;
                    }
                    case DELETE_ALIAS: {
                        Collections.DeleteAlias deleteAlias = aliasOperation.getDeleteAlias();
                        logger.debug("Delete alias '{}'", (Object)deleteAlias.getAliasName());
                        break;
                    }
                }
            }
        }
        ListenableFuture<Collections.CollectionOperationResponse> future = this.getCollections(timeout2).updateAliases(request);
        this.addLogFailureCallback(future, "Update aliases");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Alias update operation could not be performed");
                throw new QdrantException("Alias update could not be performed");
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<String>> listCollectionAliasesAsync(String collectionName) {
        return this.listCollectionAliasesAsync(collectionName, null);
    }

    public ListenableFuture<List<String>> listCollectionAliasesAsync(String collectionName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("List aliases for collection '{}'", (Object)collectionName);
        Collections.ListCollectionAliasesRequest request = Collections.ListCollectionAliasesRequest.newBuilder().setCollectionName(collectionName).build();
        ListenableFuture<Collections.ListAliasesResponse> future = this.getCollections(timeout2).listCollectionAliases(request);
        this.addLogFailureCallback(future, "List collection aliases");
        return Futures.transform(future, response -> response.getAliasesList().stream().map(Collections.AliasDescription::getAliasName).collect(Collectors.toList()), MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Collections.AliasDescription>> listAliasesAsync() {
        return this.listAliasesAsync(null);
    }

    public ListenableFuture<List<Collections.AliasDescription>> listAliasesAsync(@Nullable Duration timeout2) {
        logger.debug("List all aliases");
        ListenableFuture<Collections.ListAliasesResponse> future = this.getCollections(timeout2).listAliases(Collections.ListAliasesRequest.getDefaultInstance());
        this.addLogFailureCallback(future, "List aliases");
        return Futures.transform(future, Collections.ListAliasesResponse::getAliasesList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.CreateShardKeyResponse> createShardKeyAsync(Collections.CreateShardKeyRequest createShardKey) {
        return this.createShardKeyAsync(createShardKey, null);
    }

    public ListenableFuture<Collections.CreateShardKeyResponse> createShardKeyAsync(Collections.CreateShardKeyRequest createShardKey, @Nullable Duration timeout2) {
        String collectionName = createShardKey.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        Collections.ShardKey shardKey = createShardKey.getRequest().getShardKey();
        logger.debug("Create shard key '{}' for '{}'", (Object)shardKey, (Object)collectionName);
        ListenableFuture<Collections.CreateShardKeyResponse> future = this.getCollections(timeout2).createShardKey(createShardKey);
        this.addLogFailureCallback(future, "Create shard key");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Shard key could not be created for '{}'", (Object)collectionName);
                throw new QdrantException("Shard key " + shardKey + " could not be created for " + collectionName);
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Collections.DeleteShardKeyResponse> deleteShardKeyAsync(Collections.DeleteShardKeyRequest deleteShardKey) {
        return this.deleteShardKeyAsync(deleteShardKey, null);
    }

    public ListenableFuture<Collections.DeleteShardKeyResponse> deleteShardKeyAsync(Collections.DeleteShardKeyRequest deleteShardKey, @Nullable Duration timeout2) {
        String collectionName = deleteShardKey.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        Collections.ShardKey shardKey = deleteShardKey.getRequest().getShardKey();
        logger.debug("Delete shard key '{}' for '{}'", (Object)shardKey, (Object)collectionName);
        ListenableFuture<Collections.DeleteShardKeyResponse> future = this.getCollections(timeout2).deleteShardKey(deleteShardKey);
        this.addLogFailureCallback(future, "Delete shard key");
        return Futures.transform(future, response -> {
            if (!response.getResult()) {
                logger.error("Shard key '{}' could not be deleted for '{}'", (Object)shardKey, (Object)collectionName);
                throw new QdrantException("Shard key " + shardKey + " could not be created for " + collectionName);
            }
            return response;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> upsertAsync(String collectionName, List<Points.PointStruct> points) {
        return this.upsertAsync(collectionName, points, null);
    }

    public ListenableFuture<Points.UpdateResult> upsertAsync(String collectionName, List<Points.PointStruct> points, @Nullable Duration timeout2) {
        return this.upsertAsync(Points.UpsertPoints.newBuilder().setCollectionName(collectionName).addAllPoints(points).setWait(true).build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> upsertAsync(Points.UpsertPoints request) {
        return this.upsertAsync(request, null);
    }

    public ListenableFuture<Points.UpdateResult> upsertAsync(Points.UpsertPoints request, @Nullable Duration timeout2) {
        String collectionName = request.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Upsert {} points into '{}'", (Object)request.getPointsList().size(), (Object)collectionName);
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).upsert(request);
        this.addLogFailureCallback(future, "Upsert");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(String collectionName, List<Points.PointId> ids) {
        return this.deleteAsync(collectionName, ids, null);
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(String collectionName, List<Points.PointId> ids, @Nullable Duration timeout2) {
        return this.deleteAsync(Points.DeletePoints.newBuilder().setCollectionName(collectionName).setPoints(Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build()).setWait(true).build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(String collectionName, Points.Filter filter2) {
        return this.deleteAsync(collectionName, filter2, null);
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(String collectionName, Points.Filter filter2, @Nullable Duration timeout2) {
        return this.deleteAsync(Points.DeletePoints.newBuilder().setCollectionName(collectionName).setPoints(Points.PointsSelector.newBuilder().setFilter(filter2).build()).setWait(true).build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(Points.DeletePoints request) {
        return this.deleteAsync(request, null);
    }

    public ListenableFuture<Points.UpdateResult> deleteAsync(Points.DeletePoints request, @Nullable Duration timeout2) {
        String collectionName = request.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Delete from '{}'", (Object)collectionName);
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).delete(request);
        this.addLogFailureCallback(future, "Delete");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, Points.PointId id, @Nullable Points.ReadConsistency readConsistency) {
        return this.retrieveAsync(collectionName, id, true, false, readConsistency);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, Points.PointId id, boolean withPayload, boolean withVectors, @Nullable Points.ReadConsistency readConsistency) {
        return this.retrieveAsync(collectionName, ImmutableList.of(id), WithPayloadSelectorFactory.enable(withPayload), WithVectorsSelectorFactory.enable(withVectors), readConsistency);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, List<Points.PointId> ids, @Nullable Points.ReadConsistency readConsistency) {
        return this.retrieveAsync(collectionName, ids, true, false, readConsistency);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, List<Points.PointId> ids, boolean withPayload, boolean withVectors, @Nullable Points.ReadConsistency readConsistency) {
        return this.retrieveAsync(collectionName, ids, WithPayloadSelectorFactory.enable(withPayload), WithVectorsSelectorFactory.enable(withVectors), readConsistency);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, List<Points.PointId> ids, Points.WithPayloadSelector payloadSelector, Points.WithVectorsSelector vectorsSelector, @Nullable Points.ReadConsistency readConsistency) {
        return this.retrieveAsync(collectionName, ids, payloadSelector, vectorsSelector, readConsistency, null);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(String collectionName, List<Points.PointId> ids, Points.WithPayloadSelector payloadSelector, Points.WithVectorsSelector vectorsSelector, @Nullable Points.ReadConsistency readConsistency, @Nullable Duration timeout2) {
        logger.debug("Retrieve points from '{}'", (Object)collectionName);
        Points.GetPoints.Builder requestBuilder = Points.GetPoints.newBuilder().setCollectionName(collectionName).addAllIds(ids).setWithPayload(payloadSelector).setWithVectors(vectorsSelector);
        if (readConsistency != null) {
            requestBuilder.setReadConsistency(readConsistency);
        }
        return this.retrieveAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<List<Points.RetrievedPoint>> retrieveAsync(Points.GetPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Retrieve points from '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.GetResponse> future = this.getPoints(timeout2).get(request);
        this.addLogFailureCallback(future, "Retrieve");
        return Futures.transform(future, Points.GetResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> updateVectorsAsync(String collectionName, List<Points.PointVectors> points) {
        return this.updateVectorsAsync(collectionName, points, null, null, null);
    }

    public ListenableFuture<Points.UpdateResult> updateVectorsAsync(String collectionName, List<Points.PointVectors> points, @Nullable Duration timeout2) {
        return this.updateVectorsAsync(collectionName, points, null, null, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> updateVectorsAsync(String collectionName, List<Points.PointVectors> points, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        logger.debug("Update vectors in '{}'", (Object)collectionName);
        Points.UpdatePointVectors.Builder requestBuilder = Points.UpdatePointVectors.newBuilder().setCollectionName(collectionName).addAllPoints(points).setWait(wait == null || wait != false);
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        return this.updateVectorsAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> updateVectorsAsync(Points.UpdatePointVectors request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Update vectors in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).updateVectors(request);
        this.addLogFailureCallback(future, "Update vectors");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, Points.Filter filter2) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setFilter(filter2).build(), null, null, null);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, Points.Filter filter2, @Nullable Duration timeout2) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setFilter(filter2).build(), null, null, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, Points.Filter filter2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setFilter(filter2).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, List<Points.PointId> ids) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), null, null, null);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, List<Points.PointId> ids, @Nullable Duration timeout2) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), null, null, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, List<Points.PointId> ids, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deleteVectorsAsync(collectionName, vectors, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(String collectionName, List<String> vectors, Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        logger.debug("Delete vectors in '{}'", (Object)collectionName);
        Points.DeletePointVectors.Builder requestBuilder = Points.DeletePointVectors.newBuilder().setCollectionName(collectionName).setVectors(Points.VectorsSelector.newBuilder().addAllNames(vectors).build()).setPointsSelector(pointsSelector).setWait(wait == null || wait != false);
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        return this.deleteVectorsAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deleteVectorsAsync(Points.DeletePointVectors request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Delete vectors in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).deleteVectors(request);
        this.addLogFailureCallback(future, "Delete vectors");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.setPayloadAsync(collectionName, payload, (Points.PointsSelector)null, wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, Points.PointId id, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.setPayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addIds(id).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, List<Points.PointId> ids, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.setPayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, Points.Filter filter2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.setPayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setFilter(filter2).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.setPayloadAsync(collectionName, payload, pointsSelector, wait, null, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable String key, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.SetPayloadPoints.Builder requestBuilder = Points.SetPayloadPoints.newBuilder().setCollectionName(collectionName).setWait(wait == null || wait != false).putAllPayload(payload);
        if (pointsSelector != null) {
            requestBuilder.setPointsSelector(pointsSelector);
        }
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        if (key != null) {
            requestBuilder.setKey(key);
        }
        return this.setPayloadAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> setPayloadAsync(Points.SetPayloadPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Set payload in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).setPayload(request);
        this.addLogFailureCallback(future, "Set payload");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.overwritePayloadAsync(collectionName, payload, (Points.PointsSelector)null, wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, Points.PointId id, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.overwritePayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addIds(id).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, List<Points.PointId> ids, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.overwritePayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, Points.Filter filter2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.overwritePayloadAsync(collectionName, payload, Points.PointsSelector.newBuilder().setFilter(filter2).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.overwritePayloadAsync(collectionName, payload, pointsSelector, wait, null, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(String collectionName, Map<String, JsonWithInt.Value> payload, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable String key, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.SetPayloadPoints.Builder requestBuilder = Points.SetPayloadPoints.newBuilder().setCollectionName(collectionName).setWait(wait == null || wait != false).putAllPayload(payload);
        if (pointsSelector != null) {
            requestBuilder.setPointsSelector(pointsSelector);
        }
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        if (key != null) {
            requestBuilder.setKey(key);
        }
        return this.overwritePayloadAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> overwritePayloadAsync(Points.SetPayloadPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Set payload in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).overwritePayload(request);
        this.addLogFailureCallback(future, "Overwrite payload");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(String collectionName, List<String> keys2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deletePayloadAsync(collectionName, keys2, (Points.PointsSelector)null, wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(String collectionName, List<String> keys2, Points.PointId id, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deletePayloadAsync(collectionName, keys2, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addIds(id).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(String collectionName, List<String> keys2, List<Points.PointId> ids, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deletePayloadAsync(collectionName, keys2, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(String collectionName, List<String> keys2, Points.Filter filter2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.deletePayloadAsync(collectionName, keys2, Points.PointsSelector.newBuilder().setFilter(filter2).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(String collectionName, List<String> keys2, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.DeletePayloadPoints.Builder requestBuilder = Points.DeletePayloadPoints.newBuilder().setCollectionName(collectionName).setWait(wait == null || wait != false).addAllKeys(keys2);
        if (pointsSelector != null) {
            requestBuilder.setPointsSelector(pointsSelector);
        }
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        return this.deletePayloadAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadAsync(Points.DeletePayloadPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Delete payload in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).deletePayload(request);
        this.addLogFailureCallback(future, "Delete payload");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(String collectionName, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.clearPayloadAsync(collectionName, (Points.PointsSelector)null, wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(String collectionName, Points.PointId id, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.clearPayloadAsync(collectionName, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addIds(id).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(String collectionName, List<Points.PointId> ids, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.clearPayloadAsync(collectionName, Points.PointsSelector.newBuilder().setPoints(Points.PointsIdsList.newBuilder().addAllIds(ids).build()).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(String collectionName, Points.Filter filter2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        return this.clearPayloadAsync(collectionName, Points.PointsSelector.newBuilder().setFilter(filter2).build(), wait, ordering, timeout2);
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(String collectionName, @Nullable Points.PointsSelector pointsSelector, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.ClearPayloadPoints.Builder requestBuilder = Points.ClearPayloadPoints.newBuilder().setCollectionName(collectionName).setWait(wait == null || wait != false);
        if (pointsSelector != null) {
            requestBuilder.setPoints(pointsSelector);
        }
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        return this.clearPayloadAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> clearPayloadAsync(Points.ClearPayloadPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Clear payload in '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).clearPayload(request);
        this.addLogFailureCallback(future, "Clear payload");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> createPayloadIndexAsync(String collectionName, String field2, Collections.PayloadSchemaType schemaType, @Nullable Collections.PayloadIndexParams indexParams, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.CreateFieldIndexCollection.Builder requestBuilder = Points.CreateFieldIndexCollection.newBuilder().setCollectionName(collectionName).setFieldName(field2).setWait(wait == null || wait != false);
        switch (schemaType) {
            case Keyword: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeKeyword);
                break;
            }
            case Integer: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeInteger);
                break;
            }
            case Float: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeFloat);
                break;
            }
            case Geo: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeGeo);
                break;
            }
            case Text: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeText);
                break;
            }
            case Bool: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeBool);
                break;
            }
            case Datetime: {
                requestBuilder.setFieldType(Points.FieldType.FieldTypeDatetime);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid schemaType: '" + schemaType + "'");
            }
        }
        if (indexParams != null) {
            requestBuilder.setFieldIndexParams(indexParams);
        }
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        return this.createPayloadIndexAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<Points.UpdateResult> createPayloadIndexAsync(Points.CreateFieldIndexCollection request, @Nullable Duration timeout2) {
        logger.debug("Create payload field index for '{}' in '{}'", (Object)request.getFieldName(), (Object)request.getCollectionName());
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).createFieldIndex(request);
        this.addLogFailureCallback(future, "Create payload field index");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.UpdateResult> deletePayloadIndexAsync(String collectionName, String field2, @Nullable Boolean wait, @Nullable Points.WriteOrderingType ordering, @Nullable Duration timeout2) {
        Points.DeleteFieldIndexCollection.Builder requestBuilder = Points.DeleteFieldIndexCollection.newBuilder().setCollectionName(collectionName).setFieldName(field2).setWait(wait == null || wait != false);
        if (ordering != null) {
            requestBuilder.setOrdering(Points.WriteOrdering.newBuilder().setType(ordering).build());
        }
        logger.debug("Delete payload field index for '{}' in '{}'", (Object)field2, (Object)collectionName);
        ListenableFuture<Points.PointsOperationResponse> future = this.getPoints(timeout2).deleteFieldIndex(requestBuilder.build());
        this.addLogFailureCallback(future, "Delete payload field index");
        return Futures.transform(future, Points.PointsOperationResponse::getResult, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.ScoredPoint>> searchAsync(Points.SearchPoints request) {
        return this.searchAsync(request, null);
    }

    public ListenableFuture<List<Points.ScoredPoint>> searchAsync(Points.SearchPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        Preconditions.checkArgument(!request.getVectorList().isEmpty(), "Vector must not be empty");
        logger.debug("Search on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.SearchResponse> future = this.getPoints(timeout2).search(request);
        this.addLogFailureCallback(future, "Search");
        return Futures.transform(future, Points.SearchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.BatchResult>> searchBatchAsync(String collectionName, List<Points.SearchPoints> searches, @Nullable Points.ReadConsistency readConsistency) {
        return this.searchBatchAsync(collectionName, searches, readConsistency, null);
    }

    public ListenableFuture<List<Points.BatchResult>> searchBatchAsync(String collectionName, List<Points.SearchPoints> searches, @Nullable Points.ReadConsistency readConsistency, @Nullable Duration timeout2) {
        searches = Lists.transform(searches, searchPoints -> searchPoints.toBuilder().setCollectionName(collectionName).build());
        Points.SearchBatchPoints.Builder requestBuilder = Points.SearchBatchPoints.newBuilder().setCollectionName(collectionName).addAllSearchPoints(searches);
        if (readConsistency != null) {
            requestBuilder.setReadConsistency(readConsistency);
        }
        logger.debug("Search batch on '{}'", (Object)collectionName);
        ListenableFuture<Points.SearchBatchResponse> future = this.getPoints(timeout2).searchBatch(requestBuilder.build());
        this.addLogFailureCallback(future, "Search batch");
        return Futures.transform(future, Points.SearchBatchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.PointGroup>> searchGroupsAsync(Points.SearchPointGroups request) {
        return this.searchGroupsAsync(request, null);
    }

    public ListenableFuture<List<Points.PointGroup>> searchGroupsAsync(Points.SearchPointGroups request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Search groups on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.SearchGroupsResponse> future = this.getPoints(timeout2).searchGroups(request);
        this.addLogFailureCallback(future, "Search groups");
        return Futures.transform(future, response -> response.getResult().getGroupsList(), MoreExecutors.directExecutor());
    }

    public ListenableFuture<Points.ScrollResponse> scrollAsync(Points.ScrollPoints request) {
        return this.scrollAsync(request, null);
    }

    public ListenableFuture<Points.ScrollResponse> scrollAsync(Points.ScrollPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Scroll on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.ScrollResponse> future = this.getPoints(timeout2).scroll(request);
        this.addLogFailureCallback(future, "Scroll");
        return future;
    }

    public ListenableFuture<List<Points.ScoredPoint>> recommendAsync(Points.RecommendPoints request) {
        return this.recommendAsync(request, null);
    }

    public ListenableFuture<List<Points.ScoredPoint>> recommendAsync(Points.RecommendPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Recommend on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.RecommendResponse> future = this.getPoints(timeout2).recommend(request);
        this.addLogFailureCallback(future, "Recommend");
        return Futures.transform(future, Points.RecommendResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.BatchResult>> recommendBatchAsync(String collectionName, List<Points.RecommendPoints> recommendSearches, @Nullable Points.ReadConsistency readConsistency) {
        return this.recommendBatchAsync(collectionName, recommendSearches, readConsistency, null);
    }

    public ListenableFuture<List<Points.BatchResult>> recommendBatchAsync(String collectionName, List<Points.RecommendPoints> recommendSearches, @Nullable Points.ReadConsistency readConsistency, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        recommendSearches = Lists.transform(recommendSearches, recommendPoints -> recommendPoints.toBuilder().setCollectionName(collectionName).build());
        Points.RecommendBatchPoints.Builder requestBuilder = Points.RecommendBatchPoints.newBuilder().setCollectionName(collectionName).addAllRecommendPoints(recommendSearches);
        if (readConsistency != null) {
            requestBuilder.setReadConsistency(readConsistency);
        }
        logger.debug("Recommend batch on '{}'", (Object)collectionName);
        ListenableFuture<Points.RecommendBatchResponse> future = this.getPoints(timeout2).recommendBatch(requestBuilder.build());
        this.addLogFailureCallback(future, "Recommend batch");
        return Futures.transform(future, Points.RecommendBatchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.UpdateResult>> batchUpdateAsync(String collectionName, List<Points.PointsUpdateOperation> operations) {
        return this.batchUpdateAsync(collectionName, operations, null, null, null);
    }

    public ListenableFuture<List<Points.UpdateResult>> batchUpdateAsync(String collectionName, List<Points.PointsUpdateOperation> operations, @Nullable Boolean wait, @Nullable Points.WriteOrdering ordering, @Nullable Duration timeout2) {
        Points.UpdateBatchPoints.Builder requestBuilder = Points.UpdateBatchPoints.newBuilder().setCollectionName(collectionName).addAllOperations(operations).setWait(wait == null || wait != false);
        if (ordering != null) {
            requestBuilder.setOrdering(ordering);
        }
        return this.batchUpdateAsync(requestBuilder.build(), timeout2);
    }

    public ListenableFuture<List<Points.UpdateResult>> batchUpdateAsync(Points.UpdateBatchPoints request, @Nullable Duration timeout2) {
        String collectionName = request.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Batch update points on '{}'", (Object)collectionName);
        ListenableFuture<Points.UpdateBatchResponse> future = this.getPoints(timeout2).updateBatch(request);
        this.addLogFailureCallback(future, "Batch update points");
        return Futures.transform(future, Points.UpdateBatchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.PointGroup>> recommendGroupsAsync(Points.RecommendPointGroups request) {
        return this.recommendGroupsAsync(request, null);
    }

    public ListenableFuture<List<Points.PointGroup>> recommendGroupsAsync(Points.RecommendPointGroups request, @Nullable Duration timeout2) {
        String collectionName = request.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Recommend groups on '{}'", (Object)collectionName);
        ListenableFuture<Points.RecommendGroupsResponse> future = this.getPoints(timeout2).recommendGroups(request);
        this.addLogFailureCallback(future, "Recommend groups");
        return Futures.transform(future, response -> response.getResult().getGroupsList(), MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.ScoredPoint>> discoverAsync(Points.DiscoverPoints request) {
        return this.discoverAsync(request, null);
    }

    public ListenableFuture<List<Points.ScoredPoint>> discoverAsync(Points.DiscoverPoints request, @Nullable Duration timeout2) {
        String collectionName = request.getCollectionName();
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Discover on '{}'", (Object)collectionName);
        ListenableFuture<Points.DiscoverResponse> future = this.getPoints(timeout2).discover(request);
        this.addLogFailureCallback(future, "Discover");
        return Futures.transform(future, Points.DiscoverResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.BatchResult>> discoverBatchAsync(String collectionName, List<Points.DiscoverPoints> discoverSearches, @Nullable Points.ReadConsistency readConsistency) {
        return this.discoverBatchAsync(collectionName, discoverSearches, readConsistency, null);
    }

    public ListenableFuture<List<Points.BatchResult>> discoverBatchAsync(String collectionName, List<Points.DiscoverPoints> discoverSearches, @Nullable Points.ReadConsistency readConsistency, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        Points.DiscoverBatchPoints.Builder requestBuilder = Points.DiscoverBatchPoints.newBuilder().setCollectionName(collectionName).addAllDiscoverPoints(discoverSearches);
        if (readConsistency != null) {
            requestBuilder.setReadConsistency(readConsistency);
        }
        logger.debug("Discover batch on '{}'", (Object)collectionName);
        ListenableFuture<Points.DiscoverBatchResponse> future = this.getPoints(timeout2).discoverBatch(requestBuilder.build());
        this.addLogFailureCallback(future, "Discover batch");
        return Futures.transform(future, Points.DiscoverBatchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Long> countAsync(String collectionName) {
        return this.countAsync(collectionName, null, null, null);
    }

    public ListenableFuture<Long> countAsync(String collectionName, @Nullable Duration timeout2) {
        return this.countAsync(collectionName, null, null, timeout2);
    }

    public ListenableFuture<Long> countAsync(String collectionName, @Nullable Points.Filter filter2, @Nullable Boolean exact) {
        return this.countAsync(collectionName, filter2, exact, null);
    }

    public ListenableFuture<Long> countAsync(String collectionName, @Nullable Points.Filter filter2, @Nullable Boolean exact, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        Points.CountPoints.Builder requestBuilder = Points.CountPoints.newBuilder().setCollectionName(collectionName).setExact(exact == null || exact != false);
        if (filter2 != null) {
            requestBuilder.setFilter(filter2);
        }
        logger.debug("Count on '{}'", (Object)collectionName);
        ListenableFuture<Points.CountResponse> future = this.getPoints(timeout2).count(requestBuilder.build());
        this.addLogFailureCallback(future, "Count");
        return Futures.transform(future, response -> response.getResult().getCount(), MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.ScoredPoint>> queryAsync(Points.QueryPoints request) {
        return this.queryAsync(request, null);
    }

    public ListenableFuture<List<Points.ScoredPoint>> queryAsync(Points.QueryPoints request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Query on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.QueryResponse> future = this.getPoints(timeout2).query(request);
        this.addLogFailureCallback(future, "Query");
        return Futures.transform(future, Points.QueryResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.BatchResult>> queryBatchAsync(String collectionName, List<Points.QueryPoints> queries) {
        return this.queryBatchAsync(collectionName, queries, null, null);
    }

    public ListenableFuture<List<Points.BatchResult>> queryBatchAsync(String collectionName, List<Points.QueryPoints> queries, @Nullable Points.ReadConsistency readConsistency) {
        return this.queryBatchAsync(collectionName, queries, readConsistency, null);
    }

    public ListenableFuture<List<Points.BatchResult>> queryBatchAsync(String collectionName, List<Points.QueryPoints> queries, @Nullable Points.ReadConsistency readConsistency, @Nullable Duration timeout2) {
        Points.QueryBatchPoints.Builder requestBuilder = Points.QueryBatchPoints.newBuilder().setCollectionName(collectionName).addAllQueryPoints(queries);
        if (readConsistency != null) {
            requestBuilder.setReadConsistency(readConsistency);
        }
        logger.debug("Query batch on '{}'", (Object)collectionName);
        ListenableFuture<Points.QueryBatchResponse> future = this.getPoints(timeout2).queryBatch(requestBuilder.build());
        this.addLogFailureCallback(future, "Query batch");
        return Futures.transform(future, Points.QueryBatchResponse::getResultList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<Points.PointGroup>> queryGroupsAsync(Points.QueryPointGroups request) {
        return this.queryGroupsAsync(request, null);
    }

    public ListenableFuture<List<Points.PointGroup>> queryGroupsAsync(Points.QueryPointGroups request, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!request.getCollectionName().isEmpty(), "Collection name must not be empty");
        logger.debug("Query groups on '{}'", (Object)request.getCollectionName());
        ListenableFuture<Points.QueryGroupsResponse> future = this.getPoints(timeout2).queryGroups(request);
        this.addLogFailureCallback(future, "Query groups");
        return Futures.transform(future, response -> response.getResult().getGroupsList(), MoreExecutors.directExecutor());
    }

    public ListenableFuture<SnapshotsService.SnapshotDescription> createSnapshotAsync(String collectionName) {
        return this.createSnapshotAsync(collectionName, null);
    }

    public ListenableFuture<SnapshotsService.SnapshotDescription> createSnapshotAsync(String collectionName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("Create snapshot of '{}'", (Object)collectionName);
        ListenableFuture<SnapshotsService.CreateSnapshotResponse> future = this.getSnapshots(timeout2).create(SnapshotsService.CreateSnapshotRequest.newBuilder().setCollectionName(collectionName).build());
        this.addLogFailureCallback(future, "Create snapshot");
        return Futures.transform(future, SnapshotsService.CreateSnapshotResponse::getSnapshotDescription, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<SnapshotsService.SnapshotDescription>> listSnapshotAsync(String collectionName) {
        return this.listSnapshotAsync(collectionName, null);
    }

    public ListenableFuture<List<SnapshotsService.SnapshotDescription>> listSnapshotAsync(String collectionName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        logger.debug("List snapshots of '{}'", (Object)collectionName);
        ListenableFuture<SnapshotsService.ListSnapshotsResponse> future = this.getSnapshots(timeout2).list(SnapshotsService.ListSnapshotsRequest.newBuilder().setCollectionName(collectionName).build());
        this.addLogFailureCallback(future, "List snapshots");
        return Futures.transform(future, SnapshotsService.ListSnapshotsResponse::getSnapshotDescriptionsList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> deleteSnapshotAsync(String collectionName, String snapshotName) {
        return this.deleteSnapshotAsync(collectionName, snapshotName, null);
    }

    public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> deleteSnapshotAsync(String collectionName, String snapshotName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!collectionName.isEmpty(), "Collection name must not be empty");
        Preconditions.checkArgument(!snapshotName.isEmpty(), "Snapshot name must not be empty");
        logger.debug("Delete snapshot '{}' of '{}'", (Object)snapshotName, (Object)collectionName);
        ListenableFuture<SnapshotsService.DeleteSnapshotResponse> future = this.getSnapshots(timeout2).delete(SnapshotsService.DeleteSnapshotRequest.newBuilder().setCollectionName(collectionName).setSnapshotName(snapshotName).build());
        this.addLogFailureCallback(future, "Delete snapshot");
        return future;
    }

    public ListenableFuture<SnapshotsService.SnapshotDescription> createFullSnapshotAsync() {
        return this.createFullSnapshotAsync(null);
    }

    public ListenableFuture<SnapshotsService.SnapshotDescription> createFullSnapshotAsync(@Nullable Duration timeout2) {
        logger.debug("Create full snapshot for a whole storage");
        ListenableFuture<SnapshotsService.CreateSnapshotResponse> future = this.getSnapshots(timeout2).createFull(SnapshotsService.CreateFullSnapshotRequest.getDefaultInstance());
        this.addLogFailureCallback(future, "Create full snapshot");
        return Futures.transform(future, SnapshotsService.CreateSnapshotResponse::getSnapshotDescription, MoreExecutors.directExecutor());
    }

    public ListenableFuture<List<SnapshotsService.SnapshotDescription>> listFullSnapshotAsync() {
        return this.listFullSnapshotAsync(null);
    }

    public ListenableFuture<List<SnapshotsService.SnapshotDescription>> listFullSnapshotAsync(@Nullable Duration timeout2) {
        logger.debug("List full snapshots for a whole storage");
        ListenableFuture<SnapshotsService.ListSnapshotsResponse> future = this.getSnapshots(timeout2).listFull(SnapshotsService.ListFullSnapshotsRequest.getDefaultInstance());
        this.addLogFailureCallback(future, "List full snapshots");
        return Futures.transform(future, SnapshotsService.ListSnapshotsResponse::getSnapshotDescriptionsList, MoreExecutors.directExecutor());
    }

    public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> deleteFullSnapshotAsync(String snapshotName) {
        return this.deleteFullSnapshotAsync(snapshotName, null);
    }

    public ListenableFuture<SnapshotsService.DeleteSnapshotResponse> deleteFullSnapshotAsync(String snapshotName, @Nullable Duration timeout2) {
        Preconditions.checkArgument(!snapshotName.isEmpty(), "Snapshot name must not be empty");
        logger.debug("Delete full snapshot '{}'", (Object)snapshotName);
        ListenableFuture<SnapshotsService.DeleteSnapshotResponse> future = this.getSnapshots(timeout2).deleteFull(SnapshotsService.DeleteFullSnapshotRequest.newBuilder().setSnapshotName(snapshotName).build());
        this.addLogFailureCallback(future, "Delete full snapshot");
        return future;
    }

    @Override
    public void close() {
        this.grpcClient.close();
    }

    private <V> void addLogFailureCallback(ListenableFuture<V> future, final String message) {
        Futures.addCallback(future, new FutureCallback<V>(){

            @Override
            public void onSuccess(V result) {
            }

            @Override
            public void onFailure(Throwable t2) {
                logger.error(message + " operation failed", t2);
            }
        }, MoreExecutors.directExecutor());
    }

    private CollectionsGrpc.CollectionsFutureStub getCollections(@Nullable Duration timeout2) {
        return timeout2 != null ? (CollectionsGrpc.CollectionsFutureStub)this.grpcClient.collections().withDeadlineAfter(timeout2.toMillis(), TimeUnit.MILLISECONDS) : this.grpcClient.collections();
    }

    private PointsGrpc.PointsFutureStub getPoints(@Nullable Duration timeout2) {
        return timeout2 != null ? (PointsGrpc.PointsFutureStub)this.grpcClient.points().withDeadlineAfter(timeout2.toMillis(), TimeUnit.MILLISECONDS) : this.grpcClient.points();
    }

    private SnapshotsGrpc.SnapshotsFutureStub getSnapshots(@Nullable Duration timeout2) {
        return timeout2 != null ? (SnapshotsGrpc.SnapshotsFutureStub)this.grpcClient.snapshots().withDeadlineAfter(timeout2.toMillis(), TimeUnit.MILLISECONDS) : this.grpcClient.snapshots();
    }
}

