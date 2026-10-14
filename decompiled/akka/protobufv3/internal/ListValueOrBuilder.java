/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.Value;
import akka.protobufv3.internal.ValueOrBuilder;
import java.util.List;

public interface ListValueOrBuilder
extends MessageOrBuilder {
    public List<Value> getValuesList();

    public Value getValues(int var1);

    public int getValuesCount();

    public List<? extends ValueOrBuilder> getValuesOrBuilderList();

    public ValueOrBuilder getValuesOrBuilder(int var1);
}

