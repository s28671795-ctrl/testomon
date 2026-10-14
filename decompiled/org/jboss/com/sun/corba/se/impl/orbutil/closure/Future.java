/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.closure;

import org.jboss.com.sun.corba.se.spi.orbutil.closure.Closure;

public class Future
implements Closure {
    private boolean evaluated = false;
    private Closure closure;
    private Object value;

    public Future(Closure value) {
        this.closure = value;
        this.value = null;
    }

    @Override
    public synchronized Object evaluate() {
        if (!this.evaluated) {
            this.evaluated = true;
            this.value = this.closure.evaluate();
        }
        return this.value;
    }
}

