/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.orbutil.closure;

import org.jboss.com.sun.corba.se.impl.orbutil.closure.Constant;
import org.jboss.com.sun.corba.se.impl.orbutil.closure.Future;
import org.jboss.com.sun.corba.se.spi.orbutil.closure.Closure;

public abstract class ClosureFactory {
    private ClosureFactory() {
    }

    public static Closure makeConstant(Object value) {
        return new Constant(value);
    }

    public static Closure makeFuture(Closure value) {
        return new Future(value);
    }
}

