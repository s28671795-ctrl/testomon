/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Supplier;

@FunctionalInterface
public interface Function0<R>
extends Supplier<R> {
    @Override
    default public R get() {
        return this.apply();
    }

    public R apply();
}

