/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Inlet;

public final class Inlet$ {
    public static final Inlet$ MODULE$ = new Inlet$();

    public <T> Inlet<T> apply(String name) {
        return new Inlet(name);
    }

    public <T> Inlet<T> create(String name) {
        return this.apply(name);
    }

    private Inlet$() {
    }
}

