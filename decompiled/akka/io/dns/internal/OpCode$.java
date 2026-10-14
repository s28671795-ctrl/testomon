/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import scala.Enumeration;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class OpCode$
extends Enumeration {
    public static final OpCode$ MODULE$ = new OpCode$();
    private static final Enumeration.Value QUERY = MODULE$.Value(0);
    private static final Enumeration.Value IQUERY = MODULE$.Value(1);
    private static final Enumeration.Value STATUS = MODULE$.Value(2);

    public Enumeration.Value QUERY() {
        return QUERY;
    }

    public Enumeration.Value IQUERY() {
        return IQUERY;
    }

    public Enumeration.Value STATUS() {
        return STATUS;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(OpCode$.class);
    }

    private OpCode$() {
    }
}

