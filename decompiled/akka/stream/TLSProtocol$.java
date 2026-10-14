/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TLSProtocol;
import akka.stream.TLSProtocol$NegotiateNewSession$;

public final class TLSProtocol$ {
    public static final TLSProtocol$ MODULE$ = new TLSProtocol$();

    public TLSProtocol.NegotiateNewSession negotiateNewSession() {
        return TLSProtocol$NegotiateNewSession$.MODULE$;
    }

    private TLSProtocol$() {
    }
}

