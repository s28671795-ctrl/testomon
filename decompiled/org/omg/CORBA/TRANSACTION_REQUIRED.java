/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class TRANSACTION_REQUIRED
extends SystemException {
    private static final long serialVersionUID = 1L;

    public TRANSACTION_REQUIRED() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_REQUIRED(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_REQUIRED(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public TRANSACTION_REQUIRED(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

