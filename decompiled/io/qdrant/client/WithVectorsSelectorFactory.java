/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Points;
import java.util.List;

public final class WithVectorsSelectorFactory {
    private WithVectorsSelectorFactory() {
    }

    public static Points.WithVectorsSelector enable(boolean enable) {
        return Points.WithVectorsSelector.newBuilder().setEnable(enable).build();
    }

    public static Points.WithVectorsSelector include(List<String> names) {
        return Points.WithVectorsSelector.newBuilder().setInclude(Points.VectorsSelector.newBuilder().addAllNames(names)).build();
    }
}

