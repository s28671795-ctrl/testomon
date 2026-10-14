/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.Clock;
import akka.util.NanoClock;
import akka.util.RecencyList;

@InternalApi
public final class RecencyList$ {
    public static final RecencyList$ MODULE$ = new RecencyList$();

    public <A> RecencyList<A> emptyWithNanoClock() {
        return this.apply(new NanoClock());
    }

    public <A> RecencyList<A> apply(Clock clock) {
        return new RecencyList(clock);
    }

    private RecencyList$() {
    }
}

