/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import org.glassfish.hk2.api.MultiException;

public class Collector {
    private LinkedHashSet<Throwable> throwables;

    public void addMultiException(MultiException me) {
        if (me == null) {
            return;
        }
        if (this.throwables == null) {
            this.throwables = new LinkedHashSet();
        }
        this.throwables.addAll(me.getErrors());
    }

    public void addThrowable(Throwable th) {
        if (th == null) {
            return;
        }
        if (this.throwables == null) {
            this.throwables = new LinkedHashSet();
        }
        if (th instanceof MultiException) {
            this.throwables.addAll(((MultiException)th).getErrors());
        } else {
            this.throwables.add(th);
        }
    }

    public void throwIfErrors() throws MultiException {
        if (this.throwables == null || this.throwables.isEmpty()) {
            return;
        }
        throw new MultiException(new LinkedList<Throwable>(this.throwables));
    }

    public boolean hasErrors() {
        return this.throwables != null && !this.throwables.isEmpty();
    }
}

