/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.ThrottleMode$Enforcing$;
import akka.stream.ThrottleMode$Shaping$;

public final class ThrottleMode$ {
    public static final ThrottleMode$ MODULE$ = new ThrottleMode$();

    public ThrottleMode$Shaping$ shaping() {
        return ThrottleMode$Shaping$.MODULE$;
    }

    public ThrottleMode$Enforcing$ enforcing() {
        return ThrottleMode$Enforcing$.MODULE$;
    }

    private ThrottleMode$() {
    }
}

