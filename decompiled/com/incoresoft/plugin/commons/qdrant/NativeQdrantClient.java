/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant;

import io.qdrant.client.PointIdFactory;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.VectorsFactory;
import io.qdrant.client.WithPayloadSelectorFactory;
import io.qdrant.client.WithVectorsSelectorFactory;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NativeQdrantClient {
    private final String collectionName;
    private final QdrantClient qdrantClient;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private final Queue<Points.CreateFieldIndexCollection> indexes = new ConcurrentLinkedQueue<Points.CreateFieldIndexCollection>();

    public NativeQdrantClient(@NotNull String host2, @NotNull String collectionName) {
        this.collectionName = Objects.requireNonNull(collectionName);
        URI server = URI.create(host2);
        this.qdrantClient = new QdrantClient(QdrantGrpcClient.newBuilder(server.getHost(), server.getPort(), false).build());
    }

    public void init() {
        if (this.initialized.compareAndSet(false, true)) {
            this.createCollectionIfNotExists();
            this.createIndexes();
        }
    }

    public void createCollectionIfNotExists() {
        if (!this.collectionExists()) {
            this.createCollection();
        }
    }

    public boolean collectionExists() {
        try {
            return (Boolean)this.qdrantClient.collectionExistsAsync(this.getCollectionName()).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCollection() {
        try {
            Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder().setDistance(Collections.Distance.Dot).setSize(512L).setOnDisk(true).build();
            Collections.OptimizersConfigDiff optimizersConfigDiff = Collections.OptimizersConfigDiff.newBuilder().setMemmapThreshold(100000L).build();
            Collections.CreateCollection createCollectionRequest = Collections.CreateCollection.newBuilder().setCollectionName(this.getCollectionName()).setVectorsConfig(Collections.VectorsConfig.newBuilder().setParams(vectorParams).build()).setOptimizersConfig(optimizersConfigDiff).build();
            this.qdrantClient.createCollectionAsync(createCollectionRequest).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(long id, @NotNull float[] vector) {
        this.insert(id, vector, Collections.emptyMap());
    }

    public void insert(long id, @NotNull float[] vector, @NotNull Map<String, JsonWithInt.Value> payload) {
        Points.PointStruct pointStructure = Points.PointStruct.newBuilder().setId(PointIdFactory.id(id)).setVectors(VectorsFactory.vectors(vector)).putAllPayload(payload).build();
        this.insert(Collections.singletonList(pointStructure));
    }

    public void insert(List<Points.PointStruct> points) {
        try {
            this.qdrantClient.upsertAsync(this.getCollectionName(), points).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Points.ScoredPoint> searchByIds(Collection<Long> ids, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Points.Filter filter2, @Nullable Points.SearchParams searchParams) {
        List<List<Float>> vectors = this.get(ids, false, true).stream().map(it -> it.getVectors().getVector().getDataList()).toList();
        return this.searchByVectors(vectors, confidence, limit, offset, filter2, searchParams);
    }

    public List<Points.ScoredPoint> searchByVectors(List<List<Float>> vectors, float confidence, @Nullable Integer limit, @Nullable Integer offset, @Nullable Points.Filter filter2, @Nullable Points.SearchParams searchParams) {
        Points.SearchPoints.Builder builder = Points.SearchPoints.newBuilder().setCollectionName(this.getCollectionName()).setScoreThreshold(confidence).setWithPayload(WithPayloadSelectorFactory.enable(true)).setWithVectors(WithVectorsSelectorFactory.enable(false)).setParams(Optional.ofNullable(searchParams).orElseGet(NativeQdrantClient::defaultSearchParams)).setFilter(filter2);
        for (List<Float> vector : vectors) {
            builder.addAllVector(vector);
        }
        if (limit != null) {
            builder.setLimit(limit.intValue());
        }
        if (offset != null) {
            builder.setOffset(offset.intValue());
        }
        try {
            return (List)this.qdrantClient.searchAsync(builder.build()).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static Points.SearchParams defaultSearchParams() {
        return Points.SearchParams.newBuilder().setExact(false).build();
    }

    public Points.ScrollResponse get(@Nullable Points.Filter filter2, @Nullable Integer limit, @Nullable Points.PointId nextPageOffset, boolean withPayload, boolean withVector) {
        Points.ScrollPoints.Builder builder = Points.ScrollPoints.newBuilder().setCollectionName(this.getCollectionName()).setFilter(filter2).setWithVectors(WithVectorsSelectorFactory.enable(withVector)).setWithPayload(WithPayloadSelectorFactory.enable(withPayload));
        if (limit != null) {
            builder.setLimit(limit);
        }
        if (nextPageOffset != null) {
            builder.setOffset(nextPageOffset);
        }
        try {
            return (Points.ScrollResponse)this.qdrantClient.scrollAsync(builder.build()).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Points.RetrievedPoint> get(Collection<Long> ids, boolean withPayload, boolean withVector) {
        try {
            List<Points.PointId> pointIds = ids.stream().map(PointIdFactory::id).toList();
            return (List)this.qdrantClient.retrieveAsync(this.getCollectionName(), pointIds, withPayload, withVector, null).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void addIndex(Points.CreateFieldIndexCollection index) {
        this.indexes.add(index);
        if (this.initialized.get()) {
            this.createIndex(index);
        }
    }

    public void createIndexes() {
        for (Points.CreateFieldIndexCollection index : this.indexes) {
            this.createIndex(index);
        }
    }

    private void createIndex(Points.CreateFieldIndexCollection index) {
        try {
            this.qdrantClient.createPayloadIndexAsync(index, null).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void recreateCollection() {
        this.deleteCollection();
        this.init();
    }

    public void deleteCollection() {
        try {
            this.qdrantClient.deleteCollectionAsync(this.getCollectionName()).get();
            this.initialized.set(false);
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Collection<Long> ids) {
        try {
            List<Points.PointId> pointIds = ids.stream().map(PointIdFactory::id).toList();
            this.qdrantClient.deleteAsync(this.getCollectionName(), pointIds).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    protected Points.CreateFieldIndexCollection integerIndex(String fieldName) {
        return Points.CreateFieldIndexCollection.newBuilder().setCollectionName(this.getCollectionName()).setFieldName(fieldName).setFieldType(Points.FieldType.FieldTypeInteger).build();
    }

    @NotNull
    protected static Map<String, Long> convertPayload(Map<String, JsonWithInt.Value> map) {
        return map.entrySet().stream().map(e -> Map.entry((String)e.getKey(), ((JsonWithInt.Value)e.getValue()).getIntegerValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String getCollectionName() {
        return this.collectionName;
    }
}

