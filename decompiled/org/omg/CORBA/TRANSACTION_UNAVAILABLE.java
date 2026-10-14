/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class TRANSACTION_UNAVAILABLE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public TRANSACTION_UNAVAILABLE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_UNAVAILABLE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public TRANSACTION_UNAVAILABLE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public TRANSACTION_UNAVAILABLE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

