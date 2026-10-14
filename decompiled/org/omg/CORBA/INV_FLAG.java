/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class INV_FLAG
extends SystemException {
    private static final long serialVersionUID = 1L;

    public INV_FLAG() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_FLAG(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public INV_FLAG(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public INV_FLAG(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

