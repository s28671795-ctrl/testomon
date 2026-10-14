/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Merge;

public final class Merge$ {
    public static final Merge$ MODULE$ = new Merge$();

    public <T> Merge<T> apply(int inputPorts, boolean eagerComplete) {
        return new Merge(inputPorts, eagerComplete);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private Merge$() {
    }
}

