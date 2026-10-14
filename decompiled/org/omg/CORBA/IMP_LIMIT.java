/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class IMP_LIMIT
extends SystemException {
    private static final long serialVersionUID = 1L;

    public IMP_LIMIT() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public IMP_LIMIT(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public IMP_LIMIT(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public IMP_LIMIT(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

