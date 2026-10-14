/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public class UnknownException
extends SystemException {
    public Throwable originalEx;

    public UnknownException(Throwable ex) {
        super("", 0, CompletionStatus.COMPLETED_MAYBE);
        this.originalEx = ex;
    }

    UnknownException(Throwable orig, int minor_code, CompletionStatus status) {
        super("", minor_code, status);
        this.originalEx = orig;
    }

    UnknownException(Throwable orig, String message) {
        super(message, 0, CompletionStatus.COMPLETED_MAYBE);
        this.originalEx = orig;
    }

    UnknownException(Throwable orig, String message, int minor_code, CompletionStatus status) {
        super(message, minor_code, status);
        this.originalEx = orig;
    }
}

