/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class OBJ_ADAPTER
extends SystemException {
    private static final long serialVersionUID = 1L;

    public OBJ_ADAPTER() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public OBJ_ADAPTER(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public OBJ_ADAPTER(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public OBJ_ADAPTER(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

