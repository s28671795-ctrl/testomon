/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.AlwaysIncreasingClock;
import akka.util.WallClock;

public final class WallClock$ {
    public static final WallClock$ MODULE$ = new WallClock$();
    private static final WallClock AlwaysIncreasingClock = new AlwaysIncreasingClock();

    public WallClock AlwaysIncreasingClock() {
        return AlwaysIncreasingClock;
    }

    private WallClock$() {
    }
}

