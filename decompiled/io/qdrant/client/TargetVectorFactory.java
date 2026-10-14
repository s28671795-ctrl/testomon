/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Points;

public class TargetVectorFactory {
    private TargetVectorFactory() {
    }

    public static Points.TargetVector targetVector(Points.PointId id) {
        return Points.TargetVector.newBuilder().setSingle(Points.VectorExample.newBuilder().setId(id)).build();
    }

    public static Points.TargetVector targetVector(Points.Vector vector) {
        return Points.TargetVector.newBuilder().setSingle(Points.VectorExample.newBuilder().setVector(vector)).build();
    }
}

