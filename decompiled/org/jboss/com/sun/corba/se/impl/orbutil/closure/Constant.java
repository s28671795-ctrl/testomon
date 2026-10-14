/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.closure;

import org.jboss.com.sun.corba.se.spi.orbutil.closure.Closure;

public class Constant
implements Closure {
    private Object value;

    public Constant(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate() {
        return this.value;
    }
}

