/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class UNKNOWN
extends SystemException {
    private static final long serialVersionUID = 1L;

    public UNKNOWN() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public UNKNOWN(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public UNKNOWN(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public UNKNOWN(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

