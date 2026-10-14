/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.EagerClose$;
import akka.stream.IgnoreBoth$;
import akka.stream.IgnoreCancel$;
import akka.stream.IgnoreComplete$;
import akka.stream.TLSClosing;

public final class TLSClosing$ {
    public static final TLSClosing$ MODULE$ = new TLSClosing$();

    public TLSClosing eagerClose() {
        return EagerClose$.MODULE$;
    }

    public TLSClosing ignoreCancel() {
        return IgnoreCancel$.MODULE$;
    }

    public TLSClosing ignoreComplete() {
        return IgnoreComplete$.MODULE$;
    }

    public TLSClosing ignoreBoth() {
        return IgnoreBoth$.MODULE$;
    }

    private TLSClosing$() {
    }
}

