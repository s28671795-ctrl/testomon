/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.VectorFactory;
import io.qdrant.client.grpc.Points;
import java.util.List;
import java.util.Map;

public final class VectorsFactory {
    private VectorsFactory() {
    }

    public static Points.Vectors namedVectors(Map<String, Points.Vector> values2) {
        return Points.Vectors.newBuilder().setVectors(Points.NamedVectors.newBuilder().putAllVectors(values2)).build();
    }

    public static Points.Vectors vectors(List<Float> values2) {
        return Points.Vectors.newBuilder().setVector(VectorFactory.vector(values2)).build();
    }

    public static Points.Vectors vectors(float ... values2) {
        return Points.Vectors.newBuilder().setVector(VectorFactory.vector(values2)).build();
    }

    public static Points.Vectors vectors(Points.Vector vector) {
        return Points.Vectors.newBuilder().setVector(vector).build();
    }
}

