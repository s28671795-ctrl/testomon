/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public final class OBJECT_NOT_EXIST
extends SystemException {
    private static final long serialVersionUID = 1L;

    public OBJECT_NOT_EXIST() {
        super("", 0, CompletionStatus.COMPLETED_NO);
    }

    public OBJECT_NOT_EXIST(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public OBJECT_NOT_EXIST(int minor, CompletionStatus completed2) {
        super("", minor, completed2);
    }

    public OBJECT_NOT_EXIST(String reason, int minor, CompletionStatus completed2) {
        super(reason, minor, completed2);
    }
}

