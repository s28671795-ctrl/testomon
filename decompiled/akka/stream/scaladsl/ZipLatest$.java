/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ZipLatest;

public final class ZipLatest$ {
    public static final ZipLatest$ MODULE$ = new ZipLatest$();

    public <A, B> ZipLatest<A, B> apply() {
        return new ZipLatest();
    }

    public <A, B> ZipLatest<A, B> apply(boolean eagerComplete) {
        return new ZipLatest(eagerComplete);
    }

    private ZipLatest$() {
    }
}

