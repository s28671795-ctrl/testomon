/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ThrowingRunnable<E extends Throwable> {
    public void run() throws E;
}

