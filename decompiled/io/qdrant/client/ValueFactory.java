/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client;

import io.qdrant.client.grpc.JsonWithInt;
import java.util.List;
import java.util.Map;

public final class ValueFactory {
    private ValueFactory() {
    }

    public static JsonWithInt.Value value(String value) {
        return JsonWithInt.Value.newBuilder().setStringValue(value).build();
    }

    public static JsonWithInt.Value value(long value) {
        return JsonWithInt.Value.newBuilder().setIntegerValue(value).build();
    }

    public static JsonWithInt.Value value(double value) {
        return JsonWithInt.Value.newBuilder().setDoubleValue(value).build();
    }

    public static JsonWithInt.Value value(boolean value) {
        return JsonWithInt.Value.newBuilder().setBoolValue(value).build();
    }

    public static JsonWithInt.Value nullValue() {
        return JsonWithInt.Value.newBuilder().setNullValue(JsonWithInt.NullValue.NULL_VALUE).build();
    }

    public static JsonWithInt.Value list(List<JsonWithInt.Value> values2) {
        return JsonWithInt.Value.newBuilder().setListValue(JsonWithInt.ListValue.newBuilder().addAllValues(values2).build()).build();
    }

    public static JsonWithInt.Value value(List<JsonWithInt.Value> values2) {
        return ValueFactory.list(values2);
    }

    public static JsonWithInt.Value value(Map<String, JsonWithInt.Value> values2) {
        return JsonWithInt.Value.newBuilder().setStructValue(JsonWithInt.Struct.newBuilder().putAllFields(values2).build()).build();
    }
}

