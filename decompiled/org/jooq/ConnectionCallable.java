/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionCallable<T> {
    public T run(Connection var1) throws Throwable;
}

