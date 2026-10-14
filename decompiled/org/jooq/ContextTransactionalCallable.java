/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

@FunctionalInterface
public interface ContextTransactionalCallable<T> {
    public T run() throws Throwable;
}

