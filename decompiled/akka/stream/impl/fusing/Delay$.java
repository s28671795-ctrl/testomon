/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;

@InternalApi
public final class Delay$ {
    public static final Delay$ MODULE$ = new Delay$();
    private static final String akka$stream$impl$fusing$Delay$$TimerName = "DelayedTimer";
    private static final int akka$stream$impl$fusing$Delay$$DelayPrecisionMS = 10;

    public String akka$stream$impl$fusing$Delay$$TimerName() {
        return akka$stream$impl$fusing$Delay$$TimerName;
    }

    public int akka$stream$impl$fusing$Delay$$DelayPrecisionMS() {
        return akka$stream$impl$fusing$Delay$$DelayPrecisionMS;
    }

    private Delay$() {
    }
}

