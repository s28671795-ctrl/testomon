/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;

public interface AnyOrBuilder
extends MessageOrBuilder {
    public String getTypeUrl();

    public ByteString getTypeUrlBytes();

    public ByteString getValue();
}

