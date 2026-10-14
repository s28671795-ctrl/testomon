/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.SystemException;

public class IndirectionException
extends SystemException {
    public int offset;

    public IndirectionException(int offset) {
        super("", 0, CompletionStatus.COMPLETED_MAYBE);
        this.offset = offset;
    }
}

