/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class INTERNAL
extends SystemException {
    private static final long serialVersionUID = 1L;

    public INTERNAL() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public INTERNAL(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public INTERNAL(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public INTERNAL(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

