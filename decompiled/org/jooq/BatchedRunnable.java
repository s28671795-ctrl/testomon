/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Configuration;

@FunctionalInterface
public interface BatchedRunnable {
    public void run(Configuration var1) throws Throwable;
}

