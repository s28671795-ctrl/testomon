/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import org.apache.cxf.interceptor.FaultOutInterceptor;

public class FaultInfoException
extends Exception
implements FaultOutInterceptor.FaultInfoException {
    private static final long serialVersionUID = 1392427062502667841L;

    public FaultInfoException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public FaultInfoException(String arg0) {
        super(arg0);
    }

    public FaultInfoException(Throwable arg0) {
        super(arg0);
    }

    public FaultInfoException() {
    }
}

