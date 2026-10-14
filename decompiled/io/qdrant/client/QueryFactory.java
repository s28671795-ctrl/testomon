/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.VectorInputFactory;
import io.qdrant.client.grpc.Points;
import java.util.List;
import java.util.UUID;

public final class QueryFactory {
    private QueryFactory() {
    }

    public static Points.Query recommend(Points.RecommendInput input) {
        return Points.Query.newBuilder().setRecommend(input).build();
    }

    public static Points.Query discover(Points.DiscoverInput input) {
        return Points.Query.newBuilder().setDiscover(input).build();
    }

    public static Points.Query context(Points.ContextInput input) {
        return Points.Query.newBuilder().setContext(input).build();
    }

    public static Points.Query fusion(Points.Fusion fusion) {
        return Points.Query.newBuilder().setFusion(fusion).build();
    }

    public static Points.Query orderBy(String key) {
        Points.OrderBy orderBy = Points.OrderBy.newBuilder().setKey(key).build();
        return Points.Query.newBuilder().setOrderBy(orderBy).build();
    }

    public static Points.Query orderBy(Points.OrderBy orderBy) {
        return Points.Query.newBuilder().setOrderBy(orderBy).build();
    }

    public static Points.Query nearest(Points.VectorInput input) {
        return Points.Query.newBuilder().setNearest(input).build();
    }

    public static Points.Query nearest(List<Float> values2) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(values2)).build();
    }

    public static Points.Query nearest(float ... values2) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(values2)).build();
    }

    public static Points.Query nearest(List<Float> values2, List<Integer> indices) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(values2, indices)).build();
    }

    public static Points.Query nearest(float[][] vectors) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.multiVectorInput(vectors)).build();
    }

    public static Points.Query nearest(long id) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(id)).build();
    }

    public static Points.Query nearest(UUID id) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(id)).build();
    }

    public static Points.Query nearest(Points.PointId id) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.vectorInput(id)).build();
    }

    public static Points.Query nearestMultiVector(List<List<Float>> vectors) {
        return Points.Query.newBuilder().setNearest(VectorInputFactory.multiVectorInput(vectors)).build();
    }

    public static Points.Query sample(Points.Sample sample) {
        return Points.Query.newBuilder().setSample(sample).build();
    }
}

