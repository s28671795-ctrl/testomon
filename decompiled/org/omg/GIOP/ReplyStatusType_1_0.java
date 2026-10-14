/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class ReplyStatusType_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _NO_EXCEPTION = 0;
    public static final ReplyStatusType_1_0 NO_EXCEPTION = new ReplyStatusType_1_0(0);
    public static final int _USER_EXCEPTION = 1;
    public static final ReplyStatusType_1_0 USER_EXCEPTION = new ReplyStatusType_1_0(1);
    public static final int _SYSTEM_EXCEPTION = 2;
    public static final ReplyStatusType_1_0 SYSTEM_EXCEPTION = new ReplyStatusType_1_0(2);
    public static final int _LOCATION_FORWARD = 3;
    public static final ReplyStatusType_1_0 LOCATION_FORWARD = new ReplyStatusType_1_0(3);

    public int value() {
        return this.value;
    }

    public static ReplyStatusType_1_0 from_int(int value) {
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
            case 3: {
                return LOCATION_FORWARD;
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
            case 3: {
                return "LOCATION_FORWARD";
            }
        }
        throw new BAD_PARAM();
    }

    protected ReplyStatusType_1_0(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return ReplyStatusType_1_0.from_int(this.value());
    }
}

