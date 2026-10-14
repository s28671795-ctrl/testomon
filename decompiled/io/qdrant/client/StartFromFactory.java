/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import com.google.protobuf.Timestamp;
import io.qdrant.client.grpc.Points;
import java.time.Instant;

public final class StartFromFactory {
    private StartFromFactory() {
    }

    public static Points.StartFrom startFrom(float value) {
        return Points.StartFrom.newBuilder().setFloat(value).build();
    }

    public static Points.StartFrom startFrom(int value) {
        return Points.StartFrom.newBuilder().setInteger(value).build();
    }

    public static Points.StartFrom startFrom(String value) {
        return Points.StartFrom.newBuilder().setDatetime(value).build();
    }

    public static Points.StartFrom startFrom(Timestamp value) {
        return Points.StartFrom.newBuilder().setTimestamp(value).build();
    }

    public static Points.StartFrom startFrom(Instant value) {
        return Points.StartFrom.newBuilder().setTimestamp(Timestamp.newBuilder().setSeconds(value.getEpochSecond())).build();
    }
}

