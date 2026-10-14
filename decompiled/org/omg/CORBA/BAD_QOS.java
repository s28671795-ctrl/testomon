/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class BAD_QOS
extends SystemException {
    private static final long serialVersionUID = 1L;

    public BAD_QOS() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_QOS(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public BAD_QOS(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public BAD_QOS(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

