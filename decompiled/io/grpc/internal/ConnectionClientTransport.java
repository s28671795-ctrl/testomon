/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.internal.ManagedClientTransport;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface ConnectionClientTransport
extends ManagedClientTransport {
    public Attributes getAttributes();
}

