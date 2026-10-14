/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class INV_OBJREF
extends SystemException {
    private static final long serialVersionUID = 1L;

    public INV_OBJREF() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_OBJREF(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_OBJREF(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public INV_OBJREF(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

