/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class TRANSACTION_MODE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public TRANSACTION_MODE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_MODE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_MODE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public TRANSACTION_MODE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

