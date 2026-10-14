/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Collections;

public final class ShardKeyFactory {
    private ShardKeyFactory() {
    }

    public static Collections.ShardKey shardKey(String keyword) {
        return Collections.ShardKey.newBuilder().setKeyword(keyword).build();
    }

    public static Collections.ShardKey shardKey(long number) {
        return Collections.ShardKey.newBuilder().setNumber(number).build();
    }
}

