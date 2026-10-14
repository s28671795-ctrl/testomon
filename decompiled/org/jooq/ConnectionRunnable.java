/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionRunnable {
    public void run(Connection var1) throws Throwable;
}

