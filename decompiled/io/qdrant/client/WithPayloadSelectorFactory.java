/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.Points;
import java.util.List;

public final class WithPayloadSelectorFactory {
    private WithPayloadSelectorFactory() {
    }

    public static Points.WithPayloadSelector enable(boolean enable) {
        return Points.WithPayloadSelector.newBuilder().setEnable(enable).build();
    }

    public static Points.WithPayloadSelector include(List<String> fields) {
        return Points.WithPayloadSelector.newBuilder().setInclude(Points.PayloadIncludeSelector.newBuilder().addAllFields(fields).build()).build();
    }

    public static Points.WithPayloadSelector exclude(List<String> fields) {
        return Points.WithPayloadSelector.newBuilder().setExclude(Points.PayloadExcludeSelector.newBuilder().addAllFields(fields).build()).build();
    }
}

