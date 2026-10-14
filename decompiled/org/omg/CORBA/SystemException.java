/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;

public abstract class SystemException
extends RuntimeException {
    public int minor;
    public CompletionStatus completed;

    protected SystemException(String reason, int minor, CompletionStatus completed2) {
        super(reason);
        this.minor = minor;
        this.completed = completed2;
    }
}

