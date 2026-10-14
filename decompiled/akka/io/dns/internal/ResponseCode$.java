/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import scala.Enumeration;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class ResponseCode$
extends Enumeration {
    public static final ResponseCode$ MODULE$ = new ResponseCode$();
    private static final Enumeration.Value SUCCESS = MODULE$.Value(0);
    private static final Enumeration.Value FORMAT_ERROR = MODULE$.Value(1);
    private static final Enumeration.Value SERVER_FAILURE = MODULE$.Value(2);
    private static final Enumeration.Value NAME_ERROR = MODULE$.Value(3);
    private static final Enumeration.Value NOT_IMPLEMENTED = MODULE$.Value(4);
    private static final Enumeration.Value REFUSED = MODULE$.Value(5);

    public Enumeration.Value SUCCESS() {
        return SUCCESS;
    }

    public Enumeration.Value FORMAT_ERROR() {
        return FORMAT_ERROR;
    }

    public Enumeration.Value SERVER_FAILURE() {
        return SERVER_FAILURE;
    }

    public Enumeration.Value NAME_ERROR() {
        return NAME_ERROR;
    }

    public Enumeration.Value NOT_IMPLEMENTED() {
        return NOT_IMPLEMENTED;
    }

    public Enumeration.Value REFUSED() {
        return REFUSED;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ResponseCode$.class);
    }

    private ResponseCode$() {
    }
}

