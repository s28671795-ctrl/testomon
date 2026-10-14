/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Unzip;

public final class Unzip$ {
    public static final Unzip$ MODULE$ = new Unzip$();

    public <A, B> Unzip<A, B> apply() {
        return new Unzip();
    }

    private Unzip$() {
    }
}

