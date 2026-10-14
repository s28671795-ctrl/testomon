/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class INV_IDENT
extends SystemException {
    private static final long serialVersionUID = 1L;

    public INV_IDENT() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_IDENT(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_IDENT(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public INV_IDENT(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

