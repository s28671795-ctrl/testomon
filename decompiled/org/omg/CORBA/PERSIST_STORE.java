/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class PERSIST_STORE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public PERSIST_STORE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public PERSIST_STORE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public PERSIST_STORE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public PERSIST_STORE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

