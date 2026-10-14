/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class FREE_MEM
extends SystemException {
    private static final long serialVersionUID = 1L;

    public FREE_MEM() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public FREE_MEM(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public FREE_MEM(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public FREE_MEM(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

