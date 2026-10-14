/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;

@InternalApi
public final class TakeWithin$ {
    public static final TakeWithin$ MODULE$ = new TakeWithin$();
    private static final String takeWithinTimer = "TakeWithinTimer";

    public String takeWithinTimer() {
        return takeWithinTimer;
    }

    private TakeWithin$() {
    }
}

