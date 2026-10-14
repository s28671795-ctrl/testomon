/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;

public interface StringValueOrBuilder
extends MessageOrBuilder {
    public String getValue();

    public ByteString getValueBytes();
}

