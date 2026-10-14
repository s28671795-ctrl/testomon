/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Client$;
import akka.stream.Server$;
import akka.stream.TLSRole;

public final class TLSRole$ {
    public static final TLSRole$ MODULE$ = new TLSRole$();

    public TLSRole client() {
        return Client$.MODULE$;
    }

    public TLSRole server() {
        return Server$.MODULE$;
    }

    private TLSRole$() {
    }
}

