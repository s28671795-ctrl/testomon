/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.util.concurrent.Future;

public interface ListenableFuture<T>
extends Future<T> {
    public void addListener(Runnable var1);
}

