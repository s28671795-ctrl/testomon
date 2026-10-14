/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TLSClientAuth;
import akka.stream.TLSClientAuth$Need$;
import akka.stream.TLSClientAuth$None$;
import akka.stream.TLSClientAuth$Want$;

public final class TLSClientAuth$ {
    public static final TLSClientAuth$ MODULE$ = new TLSClientAuth$();

    public TLSClientAuth none() {
        return TLSClientAuth$None$.MODULE$;
    }

    public TLSClientAuth want() {
        return TLSClientAuth$Want$.MODULE$;
    }

    public TLSClientAuth need() {
        return TLSClientAuth$Need$.MODULE$;
    }

    private TLSClientAuth$() {
    }
}

