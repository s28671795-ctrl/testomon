/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

@FunctionalInterface
interface ObjIntFunction<T, R> {
    public R apply(T var1, int var2);

    default public <V> ObjIntFunction<T, V> andThen(ObjIntFunction<? super R, ? extends V> after) {
        return (t2, i) -> after.apply((R)this.apply(t2, i), i);
    }
}

