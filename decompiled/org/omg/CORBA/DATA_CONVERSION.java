/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class DATA_CONVERSION
extends SystemException {
    private static final long serialVersionUID = 1L;

    public DATA_CONVERSION() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public DATA_CONVERSION(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public DATA_CONVERSION(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public DATA_CONVERSION(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

