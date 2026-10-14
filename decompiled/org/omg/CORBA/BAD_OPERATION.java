/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class BAD_OPERATION
extends SystemException {
    private static final long serialVersionUID = 1L;

    public BAD_OPERATION() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_OPERATION(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_OPERATION(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public BAD_OPERATION(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

