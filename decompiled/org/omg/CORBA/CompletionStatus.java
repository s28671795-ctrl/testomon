/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class CompletionStatus
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _COMPLETED_YES = 0;
    public static final CompletionStatus COMPLETED_YES = new CompletionStatus(0);
    public static final int _COMPLETED_NO = 1;
    public static final CompletionStatus COMPLETED_NO = new CompletionStatus(1);
    public static final int _COMPLETED_MAYBE = 2;
    public static final CompletionStatus COMPLETED_MAYBE = new CompletionStatus(2);

    public int value() {
        return this.value;
    }

    public static CompletionStatus from_int(int value) {
        switch (value) {
            case 0: {
                return COMPLETED_YES;
            }
            case 1: {
                return COMPLETED_NO;
            }
            case 2: {
                return COMPLETED_MAYBE;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "COMPLETED_YES";
            }
            case 1: {
                return "COMPLETED_NO";
            }
            case 2: {
                return "COMPLETED_MAYBE";
            }
        }
        throw new BAD_PARAM();
    }

    protected CompletionStatus(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return CompletionStatus.from_int(this.value());
    }
}

