/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Value;
import java.util.Map;

public interface StructOrBuilder
extends MessageOrBuilder {
    public int getFieldsCount();

    public boolean containsFields(String var1);

    @Deprecated
    public Map<String, Value> getFields();

    public Map<String, Value> getFieldsMap();

    public Value getFieldsOrDefault(String var1, Value var2);

    public Value getFieldsOrThrow(String var1);
}

