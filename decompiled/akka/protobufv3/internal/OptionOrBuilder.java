/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Any;
import akka.protobufv3.internal.AnyOrBuilder;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;

public interface OptionOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public boolean hasValue();

    public Any getValue();

    public AnyOrBuilder getValueOrBuilder();
}

