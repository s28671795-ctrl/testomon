/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import com.google.common.primitives.Floats;
import io.qdrant.client.PointIdFactory;
import io.qdrant.client.grpc.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class VectorInputFactory {
    private VectorInputFactory() {
    }

    public static Points.VectorInput vectorInput(List<Float> values2) {
        return Points.VectorInput.newBuilder().setDense(Points.DenseVector.newBuilder().addAllData(values2)).build();
    }

    public static Points.VectorInput vectorInput(float ... values2) {
        return Points.VectorInput.newBuilder().setDense(Points.DenseVector.newBuilder().addAllData(Floats.asList(values2))).build();
    }

    public static Points.VectorInput vectorInput(List<Float> vector, List<Integer> indices) {
        return Points.VectorInput.newBuilder().setSparse(Points.SparseVector.newBuilder().addAllValues(vector).addAllIndices(indices).build()).build();
    }

    public static Points.VectorInput multiVectorInput(List<List<Float>> vectors) {
        List denseVectors = vectors.stream().map(v -> Points.DenseVector.newBuilder().addAllData((Iterable<? extends Float>)v).build()).collect(Collectors.toList());
        return Points.VectorInput.newBuilder().setMultiDense(Points.MultiDenseVector.newBuilder().addAllVectors(denseVectors).build()).build();
    }

    public static Points.VectorInput multiVectorInput(float[][] vectors) {
        ArrayList<Points.DenseVector> denseVectors = new ArrayList<Points.DenseVector>();
        for (float[] vector : vectors) {
            denseVectors.add(Points.DenseVector.newBuilder().addAllData(Floats.asList(vector)).build());
        }
        return Points.VectorInput.newBuilder().setMultiDense(Points.MultiDenseVector.newBuilder().addAllVectors(denseVectors).build()).build();
    }

    public static Points.VectorInput vectorInput(long id) {
        return Points.VectorInput.newBuilder().setId(PointIdFactory.id(id)).build();
    }

    public static Points.VectorInput vectorInput(UUID id) {
        return Points.VectorInput.newBuilder().setId(PointIdFactory.id(id)).build();
    }

    public static Points.VectorInput vectorInput(Points.PointId id) {
        return Points.VectorInput.newBuilder().setId(id).build();
    }
}

