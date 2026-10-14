/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageOrBuilder;

public interface TimestampOrBuilder
extends MessageOrBuilder {
    public long getSeconds();

    public int getNanos();
}

