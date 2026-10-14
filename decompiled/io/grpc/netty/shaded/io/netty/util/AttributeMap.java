/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.Attribute;
import io.grpc.netty.shaded.io.netty.util.AttributeKey;

public interface AttributeMap {
    public <T> Attribute<T> attr(AttributeKey<T> var1);

    public <T> boolean hasAttr(AttributeKey<T> var1);
}

