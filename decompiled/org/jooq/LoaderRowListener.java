/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.LoaderContext;

@FunctionalInterface
public interface LoaderRowListener {
    public void row(LoaderContext var1);
}

