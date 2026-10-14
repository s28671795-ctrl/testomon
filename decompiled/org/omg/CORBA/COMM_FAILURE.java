/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class COMM_FAILURE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public COMM_FAILURE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public COMM_FAILURE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public COMM_FAILURE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public COMM_FAILURE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

