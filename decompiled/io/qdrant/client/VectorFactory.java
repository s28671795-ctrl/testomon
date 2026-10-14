/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import com.google.common.primitives.Floats;
import io.qdrant.client.grpc.Points;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class VectorFactory {
    private VectorFactory() {
    }

    public static Points.Vector vector(List<Float> values2) {
        return Points.Vector.newBuilder().addAllData(values2).build();
    }

    public static Points.Vector vector(float ... values2) {
        return Points.Vector.newBuilder().addAllData(Floats.asList(values2)).build();
    }

    public static Points.Vector vector(List<Float> vector, List<Integer> indices) {
        return Points.Vector.newBuilder().addAllData(vector).setIndices(Points.SparseIndices.newBuilder().addAllData(indices).build()).build();
    }

    public static Points.Vector multiVector(List<List<Float>> vectors) {
        int vectorSize = vectors.size();
        List flatVector = vectors.stream().flatMap(Collection::stream).collect(Collectors.toList());
        return Points.Vector.newBuilder().addAllData(flatVector).setVectorsCount(vectorSize).build();
    }

    public static Points.Vector multiVector(float[][] vectors) {
        int vectorSize = vectors.length;
        ArrayList<Float> flatVector = new ArrayList<Float>();
        float[][] fArray = vectors;
        int n = fArray.length;
        for (int i = 0; i < n; ++i) {
            float[] vector;
            for (float value : vector = fArray[i]) {
                flatVector.add(Float.valueOf(value));
            }
        }
        return Points.Vector.newBuilder().addAllData(flatVector).setVectorsCount(vectorSize).build();
    }
}

