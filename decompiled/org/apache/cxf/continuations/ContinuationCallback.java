/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.continuations;

public interface ContinuationCallback {
    public void onComplete();

    public void onError(Throwable var1);

    public void onDisconnect();
}

