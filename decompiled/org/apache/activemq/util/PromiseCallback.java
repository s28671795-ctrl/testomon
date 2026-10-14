/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public abstract class PromiseCallback<T> {
    public abstract void onComplete(T var1, Throwable var2);
}

