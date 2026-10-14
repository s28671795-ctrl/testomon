/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Points;
import java.util.UUID;

public final class PointIdFactory {
    private PointIdFactory() {
    }

    public static Points.PointId id(long id) {
        return Points.PointId.newBuilder().setNum(id).build();
    }

    public static Points.PointId id(UUID id) {
        return Points.PointId.newBuilder().setUuid(id.toString()).build();
    }
}

