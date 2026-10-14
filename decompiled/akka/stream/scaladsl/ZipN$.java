/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ZipN;

public final class ZipN$ {
    public static final ZipN$ MODULE$ = new ZipN$();

    public <A> ZipN<A> apply(int n) {
        return new ZipN(n);
    }

    private ZipN$() {
    }
}

