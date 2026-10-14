/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;

public interface MixinOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getRoot();

    public ByteString getRootBytes();
}

