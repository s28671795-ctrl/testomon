/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class NO_RESPONSE
extends SystemException {
    private static final long serialVersionUID = 1L;

    public NO_RESPONSE() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public NO_RESPONSE(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public NO_RESPONSE(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public NO_RESPONSE(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

