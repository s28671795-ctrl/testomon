/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class BAD_CONTEXT
extends SystemException {
    private static final long serialVersionUID = 1L;

    public BAD_CONTEXT() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_CONTEXT(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_CONTEXT(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public BAD_CONTEXT(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

