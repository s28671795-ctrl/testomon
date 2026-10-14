/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Configuration;

@FunctionalInterface
public interface BatchedCallable<T> {
    public T run(Configuration var1) throws Throwable;
}

