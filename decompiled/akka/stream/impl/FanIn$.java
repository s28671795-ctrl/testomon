/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;

@InternalApi
public final class FanIn$ {
    public static final FanIn$ MODULE$ = new FanIn$();

    public final int Marked() {
        return 1;
    }

    public final int Pending() {
        return 2;
    }

    public final int Depleted() {
        return 4;
    }

    public final int Completed() {
        return 8;
    }

    public final int Cancelled() {
        return 16;
    }

    private FanIn$() {
    }
}

