/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class exception_type
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _NO_EXCEPTION = 0;
    public static final exception_type NO_EXCEPTION = new exception_type(0);
    public static final int _USER_EXCEPTION = 1;
    public static final exception_type USER_EXCEPTION = new exception_type(1);
    public static final int _SYSTEM_EXCEPTION = 2;
    public static final exception_type SYSTEM_EXCEPTION = new exception_type(2);

    public int value() {
        return this.value;
    }

    public static exception_type from_int(int value) {
        switch (value) {
            case 0: {
                return NO_EXCEPTION;
            }
            case 1: {
                return USER_EXCEPTION;
            }
            case 2: {
                return SYSTEM_EXCEPTION;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "NO_EXCEPTION";
            }
            case 1: {
                return "USER_EXCEPTION";
            }
            case 2: {
                return "SYSTEM_EXCEPTION";
            }
        }
        throw new BAD_PARAM();
    }

    protected exception_type(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return exception_type.from_int(this.value());
    }
}

