/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.ShardKeyFactory;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.Points;
import java.util.Arrays;

public class ShardKeySelectorFactory {
    private ShardKeySelectorFactory() {
    }

    public static Points.ShardKeySelector shardKeySelector(Collections.ShardKey ... shardKeys) {
        return Points.ShardKeySelector.newBuilder().addAllShardKeys(Arrays.asList(shardKeys)).build();
    }

    public static Points.ShardKeySelector shardKeySelector(String ... keywords) {
        Points.ShardKeySelector.Builder builder = Points.ShardKeySelector.newBuilder();
        for (String keyword : keywords) {
            builder.addShardKeys(ShardKeyFactory.shardKey(keyword));
        }
        return builder.build();
    }

    public static Points.ShardKeySelector shardKeySelector(long ... numbers) {
        Points.ShardKeySelector.Builder builder = Points.ShardKeySelector.newBuilder();
        for (long number : numbers) {
            builder.addShardKeys(ShardKeyFactory.shardKey(number));
        }
        return builder.build();
    }
}

