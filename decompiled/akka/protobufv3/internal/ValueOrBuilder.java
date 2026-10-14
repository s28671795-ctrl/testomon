/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.ListValue;
import akka.protobufv3.internal.ListValueOrBuilder;
import akka.protobufv3.internal.MessageOrBuilder;
import akka.protobufv3.internal.NullValue;
import akka.protobufv3.internal.Struct;
import akka.protobufv3.internal.StructOrBuilder;
import akka.protobufv3.internal.Value;

public interface ValueOrBuilder
extends MessageOrBuilder {
    public boolean hasNullValue();

    public int getNullValueValue();

    public NullValue getNullValue();

    public boolean hasNumberValue();

    public double getNumberValue();

    public boolean hasStringValue();

    public String getStringValue();

    public ByteString getStringValueBytes();

    public boolean hasBoolValue();

    public boolean getBoolValue();

    public boolean hasStructValue();

    public Struct getStructValue();

    public StructOrBuilder getStructValueOrBuilder();

    public boolean hasListValue();

    public ListValue getListValue();

    public ListValueOrBuilder getListValueOrBuilder();

    public Value.KindCase getKindCase();
}

