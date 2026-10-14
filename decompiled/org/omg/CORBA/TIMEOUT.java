/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class TIMEOUT
extends SystemException {
    private static final long serialVersionUID = 1L;

    public TIMEOUT() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public TIMEOUT(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public TIMEOUT(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public TIMEOUT(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

