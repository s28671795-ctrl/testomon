/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Outlet;

public final class Outlet$ {
    public static final Outlet$ MODULE$ = new Outlet$();

    public <T> Outlet<T> apply(String name) {
        return new Outlet(name);
    }

    public <T> Outlet<T> create(String name) {
        return this.apply(name);
    }

    private Outlet$() {
    }
}

