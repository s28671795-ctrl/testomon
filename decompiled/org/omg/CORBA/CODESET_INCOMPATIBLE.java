/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class CODESET_INCOMPATIBLE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public CODESET_INCOMPATIBLE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public CODESET_INCOMPATIBLE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public CODESET_INCOMPATIBLE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public CODESET_INCOMPATIBLE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

